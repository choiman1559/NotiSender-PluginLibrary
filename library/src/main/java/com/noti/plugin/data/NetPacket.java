package com.noti.plugin.data;

import java.io.Serializable;
import java.util.Map;

public class NetPacket implements Serializable {
    String[] keyList;
    String [] valueList;

    private NetPacket(int length) {
        keyList = new String[length];
        valueList = new String[length];
    }

    public static NetPacket parseFrom(Map<String, String> map) {
        NetPacket netPacket = new NetPacket(map.size());

        int i = 0;
        for(Object key : map.keySet()) {
            netPacket.keyList[i] = key.toString();
            netPacket.valueList[i] = map.get(key.toString());
            i += 1;
        }

        return netPacket;
    }
}
