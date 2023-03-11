package com.noti.plugin;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class PluginAction {
    public static void responseInformation(Context context) {
        Bundle extras = new Bundle();
        Plugin instance = Plugin.getInstance();

        extras.putString(PluginConst.DATA_KEY_TYPE, PluginConst.ACTION_RESPONSE_INFO);
        extras.putString(PluginConst.DATA_KEY_PLUGIN_PACKAGE_NAME, instance.getAppPackageName());
        extras.putBoolean(PluginConst.DATA_KEY_PLUGIN_READY, instance.isPluginReady());
        extras.putString(PluginConst.DATA_KEY_PLUGIN_DESCRIPTION, instance.getPluginDescription());
        extras.putString(PluginConst.DATA_KEY_SETTING_ACTIVITY, instance.getSettingClass());

        sendBroadcast(context, extras);
    }

    private static void sendBroadcast(Context context, Bundle extras) {
        final Intent intent = new Intent();
        intent.setAction(PluginConst.SENDER_ACTION_NAME);
        intent.putExtras(extras);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        intent.setComponent(new ComponentName(PluginConst.SENDER_PACKAGE_NAME, "com.noti.main.receiver.plugin.PluginReceiver"));
        context.sendBroadcast(intent);
        Log.d("sent", extras.getString(PluginConst.DATA_KEY_TYPE));
    }
}
