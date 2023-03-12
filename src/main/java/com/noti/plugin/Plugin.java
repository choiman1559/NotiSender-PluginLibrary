package com.noti.plugin;

import androidx.annotation.Nullable;

import com.noti.plugin.listener.PluginResponse;

public class Plugin {
    private static Plugin instance;

    private String pluginDescription = "";
    private String appPackageName = "";
    private String settingClass;
    private boolean isPluginReady = false;
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

    public void setAppPackageName(String appPackageName) {
        this.appPackageName = appPackageName;
    }

    public void setPluginReady(boolean pluginReady) {
        isPluginReady = pluginReady;
    }

    public void setPluginDescription(String pluginDescription) {
        this.pluginDescription = pluginDescription;
    }

    public void setSettingClass(Class<?> settingClass) {
        this.settingClass = settingClass.getName();
    }
}
