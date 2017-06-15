package com.collection.tools.entity;

/**
 * Created by John.Lu on 2017/6/10.
 */

public class SmsInfo {
    public String SimId;
    public String date;
    public String id;
    public String name;
    public String phoneNumber;
    public String smsbody;
    public String type;

    @Override
    public String toString() {
        return "SmsInfo{" +
                "SimId='" + SimId + '\'' +
                ", date='" + date + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", smsbody='" + smsbody + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
