package com.noti.plugin.listener;

import com.noti.plugin.data.PairDeviceInfo;

import java.util.ArrayList;

public class DeviceListListener {

    public static ArrayList<onReceivedListener> m_onReceivedListener = new ArrayList<>();

    public interface onReceivedListener {
        void onReceive(ArrayList<PairDeviceInfo> data);
    }

    public synchronized static void addOnDataReceivedListener(onReceivedListener mOnReceivedListener) {
        if(!m_onReceivedListener.contains(mOnReceivedListener)) m_onReceivedListener.add(mOnReceivedListener);
    }

    public static void callOnDataReceived(ArrayList<PairDeviceInfo> data) {
        if(m_onReceivedListener != null) {
            for (onReceivedListener listener : m_onReceivedListener) {
                listener.onReceive(data);
            }
        }
    }

    public static void removeOnDataReceivedListener(int index) {
        removeOnDataReceivedListener(m_onReceivedListener.get(index));
    }

    public static void removeOnDataReceivedListener(onReceivedListener onReceivedListener) {
        m_onReceivedListener.remove(onReceivedListener);
    }

    public static void removeAllOnDataReceivedListener() {
        m_onReceivedListener.clear();
    }

    public static boolean isListenerAvailable() {
        return m_onReceivedListener != null && !m_onReceivedListener.isEmpty();
    }
}
