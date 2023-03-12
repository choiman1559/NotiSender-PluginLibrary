package com.noti.plugin.data;

public class PairDeviceInfo {
    private final String Device_name;
    private final String Device_id;
    private PairDeviceType deviceType;

    public PairDeviceInfo(String Device_name, String Device_id) {
        this.Device_id = Device_id;
        this.Device_name = Device_name;
    }

    public String getDevice_id() {
        return Device_id;
    }

    public String getDevice_name() {
        return Device_name;
    }

    public PairDeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(PairDeviceType deviceType) {
        this.deviceType = deviceType;
    }
}
