package com.noti.plugin.process;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.noti.plugin.Plugin;
import com.noti.plugin.data.NotificationData;
import com.noti.plugin.data.PairDeviceInfo;
import com.noti.plugin.data.PairDeviceType;
import com.noti.plugin.data.PluginConst;
import com.noti.plugin.listener.DeviceListListener;
import com.noti.plugin.listener.PrefsDataListener;
import com.noti.plugin.listener.RemoteDataListener;
import com.noti.plugin.listener.SelfInfoListener;
import com.noti.plugin.listener.ServiceStatusListener;
import com.noti.plugin.listener.ToggleStatusListener;

import java.util.ArrayList;

public class DataReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Plugin instance = Plugin.getInstanceAllowNull();

        if (intent.getAction().equals(PluginConst.RECEIVER_ACTION_NAME)) {
            Bundle rawData = intent.getExtras();
            String dataType = rawData.getString(PluginConst.DATA_KEY_TYPE);
            Object extra_data_obj = rawData.get(PluginConst.DATA_KEY_EXTRA_DATA);
            String extra_data = "";

            if(extra_data_obj instanceof String) {
                extra_data = (String) extra_data_obj;
            }

            switch (dataType) {
                case PluginConst.ACTION_REQUEST_INFO:
                    PluginAction.responseInformation(context);
                    break;

                case PluginConst.ACTION_REQUEST_REMOTE_ACTION:
                    if (instance == null) {
                        throw new NullPointerException("Plugin instance is null");
                    } else {
                        PairDeviceInfo device = getDeviceInfo(rawData.getString(PluginConst.DATA_KEY_REMOTE_TARGET_DEVICE));
                        instance.getPluginResponse().onReceiveRemoteActionRequest(context, device, rawData.getString(PluginConst.DATA_KEY_REMOTE_ACTION_NAME), extra_data);
                    }
                    break;

                case PluginConst.ACTION_REQUEST_REMOTE_DATA:
                    if (instance == null) {
                        throw new NullPointerException("Plugin instance is null");
                    } else {
                        PairDeviceInfo device = getDeviceInfo(rawData.getString(PluginConst.DATA_KEY_REMOTE_TARGET_DEVICE));
                        instance.getPluginResponse().onReceiveRemoteDataRequest(context, device, rawData.getString(PluginConst.DATA_KEY_REMOTE_ACTION_NAME));
                    }
                    break;

                case PluginConst.ACTION_PUSH_EXCEPTION:
                    if (instance == null) {
                        throw new NullPointerException("Plugin instance is null");
                    } else instance.getPluginResponse().onReceiveException(context, (Exception) rawData.getSerializable(PluginConst.DATA_KEY_EXCEPTION));
                    break;

                case PluginConst.ACTION_RESPONSE_DEVICE_LIST:
                    String[] deviceList = rawData.getStringArray(PluginConst.DATA_KEY_DEVICE_LIST);
                    ArrayList<PairDeviceInfo> deviceInfo = new ArrayList<>();

                    for(String device : deviceList) {
                        deviceInfo.add(getDeviceInfo(device));
                    }

                    if(DeviceListListener.isListenerAvailable()) DeviceListListener.callOnDataReceived(deviceInfo);
                    break;

                case PluginConst.ACTION_RESPONSE_SELF_DEVICE_INFO:
                    String deviceString = rawData.getString(PluginConst.DATA_KEY_DEVICE_LIST);
                    if(SelfInfoListener.isListenerAvailable()) SelfInfoListener.callOnDataReceived(getDeviceInfo(deviceString));
                    break;

                case PluginConst.ACTION_RESPONSE_REMOTE_DATA:
                    if(RemoteDataListener.isListenerAvailable()) RemoteDataListener.callOnDataReceived(rawData.getString(PluginConst.DATA_KEY_REMOTE_ACTION_NAME), extra_data);
                    break;

                case PluginConst.ACTION_RESPONSE_SERVICE_STATUS:
                    if(ServiceStatusListener.isListenerAvailable()) ServiceStatusListener.callOnDataReceived(rawData.getString(PluginConst.DATA_KEY_IS_SERVICE_RUNNING).equals("true"));
                    break;

                case PluginConst.ACTION_RESPONSE_PLUGIN_TOGGLE:
                    if(ToggleStatusListener.isListenerAvailable()) ToggleStatusListener.callOnDataReceived(rawData.getString(PluginConst.DATA_KEY_IS_SERVICE_RUNNING).equals("true"));
                    break;

                case PluginConst.ACTION_RESPONSE_PREFS:
                    if(PrefsDataListener.isListenerAvailable()) PrefsDataListener.callOnDataReceived(PluginConst.DATA_KEY_REMOTE_ACTION_NAME, extra_data);
                    break;

                case PluginConst.ACTION_PUSH_NOTIFICATION:
                    if (instance == null) {
                        throw new NullPointerException("Plugin instance is null");
                    } else {
                        NotificationData data;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                            data = (NotificationData) intent.getParcelableExtra(PluginConst.DATA_KEY_EXTRA_DATA, NotificationData.class);
                        } else {
                            data = (NotificationData) intent.getParcelableExtra(PluginConst.DATA_KEY_EXTRA_DATA);
                        }

                        instance.getPluginResponse().onNotificationReceived(context, data);
                    }
                    break;

                default:
                    new IllegalAccessException("Specific plugin PluginAction type is not supported: " + dataType).printStackTrace();
                    break;
            }
        }
    }

    PairDeviceInfo getDeviceInfo(String rawData) {
        String[] info = rawData.split("\\|");
        PairDeviceInfo deviceInfo = new PairDeviceInfo(info[0], info[1]);
        if(info.length > 2) {
            deviceInfo.setDeviceType(new PairDeviceType(info[2]));
        }

        return deviceInfo;
    }
}
