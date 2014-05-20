package com.bayhill.dialapi;

import android.net.Uri;
import android.util.Log;

import com.bayhill.dialapi.util.DialConstants;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by Emil on 2014-05-19.
 */
public class DialFinder {
    private final String TAG = DialFinder.class.getSimpleName();

    private final DialApi dialApi;
    private DatagramSocket socket;

    public DialFinder(final DialApi dialApi){
        this.dialApi = dialApi;
        try {
            this.socket = new DatagramSocket();
            this.socket.setBroadcast(true);
        }catch (SocketException se){
            Log.e(TAG, "Something went wrong while creating the socket");
        }
    }

    public void start(){
        final int searchTimeout = dialApi.getSearchTimeout();
        final Thread broadcastThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int currentSearchTimer = 0;
                /**
                 * Start sending thread
                 */
                Thread listeningThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        /**
                         * Do listening magic in here
                         */
                        byte[] buffer = new byte[4096];
                        DatagramPacket incomingPacket = new DatagramPacket(buffer, buffer.length);
                        try {
                            socket.receive(incomingPacket);
                            parseResponse(incomingPacket);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                listeningThread.start();

                while(currentSearchTimer < searchTimeout){
                    /**
                     * Do sending magic in here
                     */
                    sendBroadcast();
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException iioe){
                        Log.i(TAG, "Thread was interrupted");
                    }
                    currentSearchTimer+=1;
                }
                listeningThread.interrupt();
            }

            private void sendBroadcast(){
                byte[] buffer = DialConstants.M_SEARCH.getBytes();
                DatagramPacket packet = null;
                try {
                    packet = new DatagramPacket(buffer, buffer.length,
                            InetAddress.getByName(DialConstants.BROADCAST_SERVER_ADDRESS), DialConstants.BROADCAST_SERVER_PORT);
                    socket.send(packet);
                } catch (UnknownHostException uhe) {
                    Log.e(TAG, "Address lookup failed", uhe);
                } catch(IOException ioe){
                    Log.e(TAG, "Error occured while sending", ioe);
                }
            }
        });
        broadcastThread.start();
    }

    private void parseResponse(DatagramPacket incomingPacket){
        byte[] buffer = incomingPacket.getData();
        boolean foundDialDevice = false;
        String response = new String(buffer, 0, incomingPacket.getLength());
        String location = null;
        String[] responseArray = response.trim().split("\\r");
        for(String str: responseArray){
            if(str.startsWith(DialConstants.HEADER_LOCATION)){
                location = str.substring(10).trim();
            }else if(str.contains(DialConstants.ST_DIAL)){
                foundDialDevice = true;
            }
        }
        if(!foundDialDevice && location == null){
            // No DIAL device was found
            Log.d(TAG, "Device found was no DIAL device or sent malformed response");
            return;
        }
        Uri locationUri = Uri.parse(location);
        try {
            InetAddress ip = InetAddress.getByName(locationUri.getHost());
            int port = locationUri.getPort();
            dialApi.getDeviceCallback().onDeviceFound(new DialDevice(ip, port));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
