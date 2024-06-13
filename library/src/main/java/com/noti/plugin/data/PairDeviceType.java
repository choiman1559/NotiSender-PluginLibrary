package com.noti.plugin.data;

import androidx.annotation.NonNull;
import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class PairDeviceType {
    private final String THIS_DEVICE_TYPE;

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            DEVICE_TYPE_UNKNOWN,
            DEVICE_TYPE_PHONE,
            DEVICE_TYPE_TABLET,
            DEVICE_TYPE_TV,
            DEVICE_TYPE_DESKTOP,
            DEVICE_TYPE_LAPTOP,
            DEVICE_TYPE_WATCH,
            DEVICE_TYPE_IOT,
            DEVICE_TYPE_VR,
            DEVICE_TYPE_CAR
    })
    public @interface DeviceType {
    }

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

    public PairDeviceType(@DeviceType String deviceType) {
        this.THIS_DEVICE_TYPE = deviceType;
    }

    @NonNull
    @Override
    public String toString() {
        return this.THIS_DEVICE_TYPE;
    }
}
