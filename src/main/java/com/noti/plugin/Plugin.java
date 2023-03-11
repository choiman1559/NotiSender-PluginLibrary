package com.noti.plugin;

public class Plugin {
    private static Plugin instance;

    private String pluginDescription = "";
    private String appPackageName = "";
    private String settingClass;
    private boolean isPluginReady = false;

    public static Plugin getInstance() {
        if(instance == null) throw new RuntimeException("Plugin instance is null");
        else return instance;
    }

    public static Plugin init() {
        if(instance == null) instance = new Plugin();
        return instance;
    }

    public boolean isPluginReady() {
        return isPluginReady;
    }

    public boolean isServiceRunning() {
        return true;
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
