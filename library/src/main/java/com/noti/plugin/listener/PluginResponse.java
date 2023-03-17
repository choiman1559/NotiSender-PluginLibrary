package com.noti.plugin.listener;

import android.content.Context;

public interface PluginResponse {
    void onReceiveRemoteAction(Context context, String type, String args);
    void onReceiveRemoteData(Context context, String type);
    void onReceiveException(Context context, Exception e);
}
