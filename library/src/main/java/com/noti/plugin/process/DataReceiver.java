package com.noti.plugin.process;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.noti.plugin.Plugin;
import com.noti.plugin.data.PairDeviceInfo;
import com.noti.plugin.data.PairDeviceType;
import com.noti.plugin.data.PluginConst;
import com.noti.plugin.listener.DeviceListListener;
import com.noti.plugin.listener.PrefsDataListener;
import com.noti.plugin.listener.RemoteDataListener;
import com.noti.plugin.listener.ServiceStatusListener;

import java.util.ArrayList;

public class DataReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Plugin instance = Plugin.getInstanceAllowNull();

        if (intent.getAction().equals(PluginConst.RECEIVER_ACTION_NAME)) {
            Bundle rawData = intent.getExtras();
            String dataType = rawData.getString(PluginConst.DATA_KEY_TYPE);
            String extra_data = rawData.getString(PluginConst.DATA_KEY_EXTRA_DATA);

            switch (dataType) {
                case PluginConst.ACTION_REQUEST_INFO:
                    PluginAction.responseInformation(context);
                    break;

                case PluginConst.ACTION_REQUEST_REMOTE_ACTION:
                    if (instance == null) {
                        throw new NullPointerException("Plugin instance is null");
                    } else instance.getPluginResponse().onReceiveRemoteAction(context, rawData.getString(PluginConst.DATA_KEY_REMOTE_ACTION_NAME), extra_data);
                    break;

                case PluginConst.ACTION_REQUEST_REMOTE_DATA:
                    if (instance == null) {
                        throw new NullPointerException("Plugin instance is null");
                    } else instance.getPluginResponse().onReceiveRemoteData(context, rawData.getString(PluginConst.DATA_KEY_REMOTE_ACTION_NAME));
                    break;

                case PluginConst.ACTION_PUSH_EXCEPTION:
                    if (instance == null) {
                        throw new NullPointerException("Plugin instance is null");
                    } else instance.getPluginResponse().onReceiveException(context, (Exception) rawData.getSerializable(PluginConst.DATA_KEY_EXTRA_DATA));
                    break;

                case PluginConst.ACTION_RESPONSE_DEVICE_LIST:
                    String[] deviceList = rawData.getStringArray(PluginConst.DATA_KEY_DEVICE_LIST);
                    ArrayList<PairDeviceInfo> deviceInfo = new ArrayList<>();

                    for(String device : deviceList) {
                        String[] info = device.split("\\|");
                        PairDeviceInfo pairInfo = new PairDeviceInfo(info[0], info[1]);
                        pairInfo.setDeviceType(new PairDeviceType(info[2]));
                        deviceInfo.add(pairInfo);
                    }

                    if(DeviceListListener.isListenerAvailable()) DeviceListListener.callOnDataReceived(deviceInfo);
                    break;

                case PluginConst.ACTION_RESPONSE_REMOTE_DATA:
                    if(RemoteDataListener.isListenerAvailable()) RemoteDataListener.callOnDataReceived(PluginConst.DATA_KEY_REMOTE_ACTION_NAME, extra_data);
                    break;

                case PluginConst.ACTION_RESPONSE_SERVICE_STATUS:
                    if(ServiceStatusListener.isListenerAvailable()) ServiceStatusListener.callOnDataReceived(rawData.getBoolean(PluginConst.DATA_KEY_IS_SERVICE_RUNNING));
                    break;

                case PluginConst.ACTION_RESPONSE_PREFS:
                    if(PrefsDataListener.isListenerAvailable()) PrefsDataListener.callOnDataReceived(PluginConst.DATA_KEY_REMOTE_ACTION_NAME, extra_data);
                    break;

                default:
                    new IllegalAccessException("Specific plugin PluginAction type is not supported: " + dataType).printStackTrace();
                    break;
            }
        }
    }
}
