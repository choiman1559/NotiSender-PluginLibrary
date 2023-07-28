package com.noti.plugin;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import androidx.annotation.Nullable;

import com.noti.plugin.data.PluginConst;
import com.noti.plugin.listener.PluginResponse;

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
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo info = pm.getApplicationInfo(PluginConst.SENDER_PACKAGE_NAME, PackageManager.GET_META_DATA);
            return info != null;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
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
