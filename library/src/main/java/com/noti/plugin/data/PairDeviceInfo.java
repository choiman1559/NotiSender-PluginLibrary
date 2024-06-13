package com.noti.plugin.data;

import android.content.Intent;

import androidx.annotation.Nullable;

public class PairDeviceInfo {
    private final String Device_name;
    private final String Device_id;
    private PairDeviceType deviceType;

    public PairDeviceInfo(String Device_name, String Device_id) {
        this.Device_id = Device_id;
        this.Device_name = Device_name;
    }

    public String getDeviceId() {
        return Device_id;
    }

    public String getDeviceName() {
        return Device_name;
    }

    public PairDeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(PairDeviceType deviceType) {
        this.deviceType = deviceType;
    }

    @Nullable
    public static PairDeviceInfo fromIntent(Intent intent) {
        if(intent.hasExtra(PluginConst.DEVICE_NAME_KEY) && intent.hasExtra(PluginConst.DEVICE_ID_KEY)) {
            PairDeviceInfo deviceInfo = new PairDeviceInfo(
                    intent.getStringExtra(PluginConst.DEVICE_NAME_KEY),
                    intent.getStringExtra(PluginConst.DEVICE_ID_KEY));

            if (intent.hasExtra(PluginConst.DEVICE_TYPE_KEY)) {
                deviceInfo.setDeviceType(new PairDeviceType(intent.getStringExtra(PluginConst.DEVICE_TYPE_KEY)));
            }
            return deviceInfo;
        }
        return null;
    }
}
