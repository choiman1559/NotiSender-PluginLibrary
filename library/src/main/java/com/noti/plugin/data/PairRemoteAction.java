package com.noti.plugin.data;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.noti.plugin.Plugin;

public class PairRemoteAction implements Parcelable {

    String pluginPackageName;
    int uniqueCode;

    String actionType;
    String actionDescription;
    Bitmap actionIcon;
    Class<?> actionClass;
    String actionClassName;
    String[] targetDeviceTypeScope;

    private PairRemoteAction() {
        Plugin plugin = Plugin.getInstance();
        this.pluginPackageName = plugin.getAppPackageName();
    }

    private PairRemoteAction(Parcel in) {
        super();
        this.pluginPackageName = in.readString();
        this.uniqueCode = in.readInt();
        this.actionType = in.readString();
        this.actionDescription = in.readString();
        this.actionClassName = in.readString();
        this.targetDeviceTypeScope = in.createStringArray();

        String actionIconCache = in.readString();
        this.actionIcon = BitmapUtils.getBitmapFromString(actionIconCache == null || actionIconCache.isEmpty() ? null : actionIconCache);
    }

    public static class Builder {

        PairRemoteAction remoteAction;

        public Builder() {
            remoteAction = new PairRemoteAction();
        }

        public Builder setActionType(String actionType) {
            remoteAction.actionType = actionType;
            return this;
        }

        public Builder setActionDescription(String actionDescription) {
            remoteAction.actionDescription = actionDescription;
            return this;
        }

        public Builder setActionIcon(Bitmap actionIcon) {
            remoteAction.actionIcon = actionIcon;
            return this;
        }

        public Builder setActionClass(Class<?> actionClass) {
            remoteAction.actionClass = actionClass;
            remoteAction.actionClassName = actionClass.getName();
            return this;
        }

        public Builder setTargetDeviceTypeScope(@PairDeviceType.DeviceType String... targetDeviceTypeScope) {
            if(targetDeviceTypeScope == null || targetDeviceTypeScope.length < 1) {
                remoteAction.targetDeviceTypeScope = new String[0];
            } else remoteAction.targetDeviceTypeScope = targetDeviceTypeScope;

            return this;
        }

        public PairRemoteAction build() {
            remoteAction.uniqueCode = remoteAction.hashCode();
            return remoteAction;
        }
    }

    public static final Creator<PairRemoteAction> CREATOR = new Creator<>() {
        @Override
        public PairRemoteAction createFromParcel(Parcel in) {
            return new PairRemoteAction(in);
        }

        @Override
        public PairRemoteAction[] newArray(int size) {
            return new PairRemoteAction[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(pluginPackageName);
        dest.writeInt(uniqueCode);
        dest.writeString(actionType);
        dest.writeString(actionDescription);
        dest.writeString(actionClassName);
        dest.writeStringArray(targetDeviceTypeScope);
        dest.writeString(actionIcon == null ? "" : BitmapUtils.getStringFromBitmap(actionIcon));
    }

    public String getActionType() {
        return actionType;
    }

    public String getActionDescription() {
        return actionDescription;
    }

    public String[] getTargetDeviceTypeScope() {
        return targetDeviceTypeScope;
    }

    public Bitmap getActionIcon() {
        return actionIcon;
    }

    public Class<?> getActionClass() {
        return actionClass;
    }

    public int getUniqueCode() {
        return uniqueCode;
    }
}
