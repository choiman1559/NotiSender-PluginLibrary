package com.noti.plugin.process;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.noti.plugin.BuildConfig;
import com.noti.plugin.Plugin;
import com.noti.plugin.data.PairDeviceInfo;
import com.noti.plugin.data.PluginConst;
import com.noti.plugin.listener.PrefsDataListener;
import com.noti.plugin.listener.RemoteDataListener;
import com.noti.plugin.listener.ServiceStatusListener;

import org.jetbrains.annotations.TestOnly;

@SuppressWarnings("unused")
public class PluginAction {

    public static void requestDeviceList(Context context) {
        requestDeviceList(context, null);
    }

    public static void requestDeviceList(Context context, RemoteDataListener.onReceivedListener callback) {
        if (callback != null) {
            RemoteDataListener.addOnDataReceivedListener(callback);
        }

        Bundle extras = new Bundle();
        extras.putString(PluginConst.DATA_KEY_TYPE, PluginConst.ACTION_REQUEST_DEVICE_LIST);
        sendBroadcast(context, extras);
    }

    public static void requestRemoteAction(Context context, PairDeviceInfo device, String type, String data) {
        Bundle extras = new Bundle();
        extras.putString(PluginConst.DATA_KEY_TYPE, PluginConst.ACTION_REQUEST_REMOTE_ACTION);
        extras.putString(PluginConst.DATA_KEY_REMOTE_ACTION_NAME, type);
        extras.putString(PluginConst.DATA_KEY_EXTRA_DATA, parseDeviceData(device, data));
        sendBroadcast(context, extras);
    }

    public static void requestRemoteData(Context context, PairDeviceInfo device, String type) {
        requestRemoteData(context, device, type, null);
    }

    public static void requestRemoteData(Context context, PairDeviceInfo device, String type, @Nullable RemoteDataListener.onReceivedListener callback) {
        if (callback != null) {
            RemoteDataListener.addOnDataReceivedListener(callback);
        }

        Bundle extras = new Bundle();
        extras.putString(PluginConst.DATA_KEY_TYPE, PluginConst.ACTION_REQUEST_REMOTE_ACTION);
        extras.putString(PluginConst.DATA_KEY_REMOTE_ACTION_NAME, type);
        extras.putString(PluginConst.DATA_KEY_EXTRA_DATA, parseDeviceData(device));
        sendBroadcast(context, extras);
    }

    public static void requestPreferences(Context context, String key) {
        requestPreferences(context, key, null);
    }

    public static void requestPreferences(Context context, String key, @Nullable PrefsDataListener.onReceivedListener callback) {
        if (callback != null) {
            PrefsDataListener.addOnDataReceivedListener(callback);
        }

        Bundle extras = new Bundle();
        extras.putString(PluginConst.DATA_KEY_TYPE, PluginConst.ACTION_REQUEST_PREFS);
        extras.putString(PluginConst.DATA_KEY_REMOTE_ACTION_NAME, key);
        sendBroadcast(context, extras);
    }

    public static void requestServiceStatus(Context context) {
        requestServiceStatus(context, null);
    }

    public static void requestServiceStatus(Context context, @Nullable ServiceStatusListener.onReceivedListener callback) {
        if (callback != null) {
            ServiceStatusListener.addOnDataReceivedListener(callback);
        }

        Bundle extras = new Bundle();
        extras.putString(PluginConst.DATA_KEY_TYPE, PluginConst.ACTION_REQUEST_SERVICE_STATUS);
        sendBroadcast(context, extras);
    }

    public static void responseRemoteData(Context context, PairDeviceInfo device, String type, String data) {
        Bundle extras = new Bundle();
        extras.putString(PluginConst.DATA_KEY_TYPE, PluginConst.ACTION_RESPONSE_REMOTE_DATA);
        extras.putString(PluginConst.DATA_KEY_REMOTE_ACTION_NAME, type);
        extras.putString(PluginConst.DATA_KEY_EXTRA_DATA, parseDeviceData(device, data));
        sendBroadcast(context, extras);
    }

    @TestOnly
    public static void pushCallData(Context context, String address) {
        Bundle extras = new Bundle();
        extras.putString(PluginConst.DATA_KEY_TYPE, PluginConst.ACTION_PUSH_CALL_DATA);
        extras.putString(PluginConst.DATA_KEY_EXTRA_DATA, address);
        sendBroadcast(context, extras);
    }

    @TestOnly
    public static void pushMessageData(Context context, String address, String message) {
        Bundle extras = new Bundle();
        extras.putString(PluginConst.DATA_KEY_TYPE, PluginConst.ACTION_PUSH_MESSAGE_DATA);
        extras.putString(PluginConst.DATA_KEY_EXTRA_DATA, address + "|" + message);
        sendBroadcast(context, extras);
    }

    static void responseInformation(Context context) {
        Bundle extras = new Bundle();
        Plugin instance = Plugin.getInstance();

        extras.putString(PluginConst.DATA_KEY_TYPE, PluginConst.ACTION_RESPONSE_INFO);
        extras.putString(PluginConst.PLUGIN_TITLE, instance.getPluginTitle());
        extras.putString(PluginConst.PLUGIN_DESCRIPTION, instance.getPluginDescription());
        extras.putString(PluginConst.PLUGIN_SETTING_ACTIVITY, instance.getSettingClass());
        extras.putString(PluginConst.PLUGIN_REQUIRE_VERSION, instance.getRequireHostVersion());
        extras.putBoolean(PluginConst.PLUGIN_READY, instance.isPluginReady());
        extras.putBoolean(PluginConst.PLUGIN_REQUIRE_SENSITIVE_API, instance.isRequireSensitiveAPI());

        sendBroadcast(context, extras);
    }

    private static void sendBroadcast(Context context, Bundle extras) {
        Plugin instance = Plugin.getInstance();
        extras.putString(PluginConst.PLUGIN_PACKAGE_NAME, instance.getAppPackageName());

        final Intent intent = new Intent();
        intent.setAction(PluginConst.SENDER_ACTION_NAME);
        intent.putExtras(extras);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.setComponent(new ComponentName(PluginConst.SENDER_PACKAGE_NAME, "com.noti.main.receiver.plugin.PluginReceiver"));
        context.sendBroadcast(intent);
        if (BuildConfig.DEBUG) Log.d("sent", extras.getString(PluginConst.DATA_KEY_TYPE));
    }

    private static String parseDeviceData(PairDeviceInfo deviceInfo, String... extra) {
        StringBuilder str = new StringBuilder(deviceInfo.getDevice_name() + "|" + deviceInfo.getDevice_id());
        for (String s : extra) {
            str.append("|").append(s);
        }
        return str.toString();
    }
}
