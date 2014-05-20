package com.bayhill.dialapi;

import java.net.InetAddress;

/**
 * Created by Emil on 2014-05-20.
 */
public class DialDevice {

    private final InetAddress ip;
    private final int port;

    public DialDevice(final InetAddress ip, final int port){
        this.ip = ip;
        this.port = port;
    }

    public InetAddress getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

}
