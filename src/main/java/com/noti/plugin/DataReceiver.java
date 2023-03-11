package com.noti.plugin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class DataReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(PluginConst.RECEIVER_ACTION_NAME)) {
            Bundle rawData = intent.getExtras();
            String dataType = rawData.getString(PluginConst.DATA_KEY_TYPE);
            String extra_data = rawData.getString(PluginConst.DATA_KEY_EXTRA_DATA);
            String[] data = extra_data == null ? new String[0] : extra_data.split("\\|");

            switch (dataType) {
                case PluginConst.ACTION_REQUEST_INFO:
                    PluginAction.responseInformation(context);
                    break;

                case PluginConst.ACTION_RESPONSE_DEVICE_LIST:
                    break;

                case PluginConst.ACTION_RESPONSE_REMOTE_DATA:
                    break;

                case PluginConst.ACTION_RESPONSE_SERVICE_STATUS:
                    break;

                case PluginConst.ACTION_RESPONSE_PREFS:
                    break;

                case PluginConst.ACTION_PUSH_EXCEPTION:
                    break;

                default:
                    new IllegalAccessException("Specific plugin PluginAction type is not supported: " + dataType).printStackTrace();
                    break;
            }
        }
    }
}
