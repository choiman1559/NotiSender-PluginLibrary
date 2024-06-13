package com.noti.plugin;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import androidx.annotation.Nullable;

import com.noti.plugin.data.PairRemoteAction;
import com.noti.plugin.data.PluginConst;
import com.noti.plugin.listener.PluginHostInject;
import com.noti.plugin.listener.PluginResponse;
import com.noti.plugin.process.NetworkProvider;

import java.util.ArrayList;
import java.util.List;

public class Plugin {
    private static Plugin instance;
    private String pluginTitle = "";
    private String pluginDescription = "";
    private String appPackageName = "";
    private String settingClass;
    private boolean isPluginReady = false;
    private boolean isRequireSensitiveAPI = false;
    private boolean isSyncTAKCompatibility = false;
    private PluginResponse pluginResponse;
    private NetworkProvider networkProvider;
    private PluginHostInject pluginHostInject;
    private final ArrayList<PairRemoteAction> pairRemoteActions = new ArrayList<>();

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
        return !list.isEmpty();
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

    public boolean isSyncTAKCompatibility() {
        return isSyncTAKCompatibility;
    }

    public boolean hasNetworkProvider() {
        return networkProvider != null;
    }

    public NetworkProvider getNetworkProvider() {
        return networkProvider;
    }

    public PluginHostInject getPluginHostInject() {
        return pluginHostInject;
    }

    public ArrayList<PairRemoteAction> getPairRemoteActions() {
        return pairRemoteActions;
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

    public void setSyncTAKCompatibility(boolean syncTAKCompatibility) {
        isSyncTAKCompatibility = syncTAKCompatibility;
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

    public void setNetworkProvider(Class<?> networkProviderClass) throws IllegalAccessException, InstantiationException {
        this.networkProvider = (NetworkProvider) networkProviderClass.newInstance();
    }

    public void setPluginHostInject(Class<?> pluginHostInjectClass) throws IllegalAccessException, InstantiationException {
        this.pluginHostInject = (PluginHostInject) pluginHostInjectClass.newInstance();
    }

    public void addPairRemoteActions(PairRemoteAction pairRemoteAction) {
        this.pairRemoteActions.add(pairRemoteAction);
    }

    public void removePairRemoteActions(PairRemoteAction pairRemoteAction) {
        this.pairRemoteActions.remove(pairRemoteAction);
    }

    public void removeAllPairRemoteActions() {
        this.pairRemoteActions.clear();
    }
}
