package com.collection.tools.entity;

/**
 * Created by John.Lu on 2017/6/10.
 */

public class Wifi {
    public String Channel;
    public String Id;
    public String LastJoin;
    public String Password;
    public String SSID;
    public String SecurityMode;
    public int SecurityModeID;

    public Wifi(){}

    public Wifi(String channel, String id, String lastJoin, String password, String SSID, String securityMode, int securityModeID) {
        Channel = channel;
        Id = id;
        LastJoin = lastJoin;
        Password = password;
        this.SSID = SSID;
        SecurityMode = securityMode;
        SecurityModeID = securityModeID;
    }
    public String toString() {
        return "Wifi [Id=" + this.Id + ", SSID=" + this.SSID + ", Password=" + this.Password + ", LastJoin=" + this.LastJoin + ", Channel=" + this.Channel + ", SecurityMode=" + this.SecurityMode + ", SecurityModeID=" + this.SecurityModeID + "]";
    }
}
