package com.noti.plugin.process;

import android.content.Context;
import android.os.Bundle;

import com.noti.plugin.data.NetPacket;
import com.noti.plugin.data.PluginConst;

import org.json.JSONObject;

import java.util.Map;

public class NetworkProvider {

    String providerName;

    public void onPostRequested(JSONObject data) {

    }

    public static void pushReceivedData(Context context, Map<String, String> map) {
        Bundle extras = new Bundle();
        extras.putString(PluginConst.DATA_KEY_TYPE, PluginConst.NET_PROVIDER_RECEIVED);
        extras.putSerializable(PluginConst.NET_PROVIDER_DATA, NetPacket.parseFrom(map));

        PluginAction.sendBroadcast(context, extras);
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderName() {
        return providerName;
    }
}
