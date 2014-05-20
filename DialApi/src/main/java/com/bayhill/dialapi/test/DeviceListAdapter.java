package com.bayhill.dialapi.test;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bayhill.dialapi.DialDevice;
import com.bayhill.dialapi.R;

import java.util.List;


public class DeviceListAdapter extends ArrayAdapter<DialDevice> {

    Context mContext;
    int layoutResourceId;
    DialDevice devices[] = null;

    public DeviceListAdapter(Context mContext, int layoutResourceId, DialDevice[] devices) {

        super(mContext, layoutResourceId, devices);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.devices = devices;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            // inflate the layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }

        DialDevice dialDevice = devices[position];

        TextView textView = (TextView) convertView.findViewById(R.id.textViewItem);
        textView.setText(dialDevice.getIp().getHostName());

        return convertView;
    }

}