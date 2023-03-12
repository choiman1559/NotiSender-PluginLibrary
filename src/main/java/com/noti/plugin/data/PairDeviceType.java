package com.noti.plugin.data;

public class PairDeviceType {
    private final String THIS_DEVICE_TYPE;

    public static final String DEVICE_TYPE_UNKNOWN = "Unknown";
    public static final String DEVICE_TYPE_PHONE = "Phone";
    public static final String DEVICE_TYPE_TABLET = "Tablet";
    public static final String DEVICE_TYPE_TV = "Television";
    public static final String DEVICE_TYPE_DESKTOP = "Desktop";
    public static final String DEVICE_TYPE_LAPTOP = "Laptop";
    public static final String DEVICE_TYPE_WATCH = "Smartwatch";
    public static final String DEVICE_TYPE_IOT = "IOT_Device";
    public static final String DEVICE_TYPE_VR = "VR_Gear";
    public static final String DEVICE_TYPE_CAR = "Automobile";

    public PairDeviceType(String deviceType) {
        this.THIS_DEVICE_TYPE = deviceType;
    }

    public String getDeviceType() {
        return this.THIS_DEVICE_TYPE;
    }
}
