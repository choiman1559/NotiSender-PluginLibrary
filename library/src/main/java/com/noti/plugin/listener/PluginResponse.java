package com.noti.plugin.listener;

import android.content.Context;

import com.noti.plugin.data.PairDeviceInfo;

public interface PluginResponse {
    void onReceiveRemoteActionRequest(Context context, PairDeviceInfo device, String type, String args);
    void onReceiveRemoteDataRequest(Context context, PairDeviceInfo device, String type);
    void onReceiveException(Context context, Exception e);
}
