package com.noti.plugin;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import androidx.annotation.Nullable;

import com.noti.plugin.data.PluginConst;
import com.noti.plugin.listener.PluginResponse;

import java.util.List;

public class Plugin {
    private static Plugin instance;
    private String pluginTitle = "";
    private String pluginDescription = "";
    private String appPackageName = "";
    private String settingClass;
    private boolean isPluginReady = false;
    private boolean isRequireSensitiveAPI = false;
    private PluginResponse pluginResponse;

    public static Plugin getInstance() {
       Plugin instance = getInstanceAllowNull();
       if(instance == null) throw new NullPointerException("Plugin instance is null");
       else return instance;
    }

    @Nullable
    public static Plugin getInstanceAllowNull() {
        return instance;
    }

    public static Plugin init(PluginResponse pluginResponse) {
        if(instance == null) instance = new Plugin();
        instance.pluginResponse = pluginResponse;
        return instance;
    }

    public boolean isHostInstalled(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(PluginConst.SENDER_PACKAGE_NAME);
        if (intent == null) {
            return false;
        }
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    public boolean isPluginReady() {
        return isPluginReady;
    }

    public String getPluginDescription() {
        return pluginDescription;
    }

    public String getAppPackageName() {
        return appPackageName;
    }

    public String getSettingClass() {
        return settingClass;
    }

    public PluginResponse getPluginResponse() {
        return pluginResponse;
    }

    public String getPluginTitle() {
        return pluginTitle;
    }

    public boolean isRequireSensitiveAPI() {
        return isRequireSensitiveAPI;
    }

    public String getRequireHostVersion() {
        return PluginConst.REQUIRE_HOST_VERSION;
    }

    public void setAppPackageName(String appPackageName) {
        this.appPackageName = appPackageName;
    }

    public void setPluginReady(boolean pluginReady) {
        isPluginReady = pluginReady;
    }

    public void setPluginTitle(String pluginTitle) {
        this.pluginTitle = pluginTitle;
    }

    public void setPluginDescription(String pluginDescription) {
        this.pluginDescription = pluginDescription;
    }

    public void setSettingClass(Class<?> settingClass) {
        this.settingClass = settingClass.getName();
    }

    public void setRequireSensitiveAPI(boolean requireSensitiveAPI) {
        isRequireSensitiveAPI = requireSensitiveAPI;
    }
}
