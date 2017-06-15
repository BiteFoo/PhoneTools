package com.collection.tools.entity;

/**
 * Created by John.Lu on 2017/6/14.
 */

public class NetEntity {
    private boolean isWifi;//wifi
    private boolean isMoble;//移动数据流量

    public NetEntity(boolean wifi,boolean moble)
    {
        isWifi =wifi;
        isMoble=moble;
    }

    public boolean isWifi() {
        return isWifi;
    }

    public boolean isMoble() {
        return isMoble;
    }
}
