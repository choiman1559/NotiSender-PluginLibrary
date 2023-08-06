package com.noti.plugin.listener;

import java.util.ArrayList;

public class ToggleStatusListener {
    public static ArrayList<onReceivedListener> m_onReceivedListener = new ArrayList<>();

    public interface onReceivedListener {
        void onReceive(boolean isRunning);
    }

    public synchronized static void addOnDataReceivedListener(onReceivedListener mOnReceivedListener) {
        if(!m_onReceivedListener.contains(mOnReceivedListener)) m_onReceivedListener.add(mOnReceivedListener);
    }

    public static void callOnDataReceived(boolean isRunning) {
        if(m_onReceivedListener != null) {
            for (onReceivedListener listener : m_onReceivedListener) {
                listener.onReceive(isRunning);
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
