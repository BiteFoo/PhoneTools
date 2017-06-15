package com.collection.tools.entity;

/**
 * Created by John.Lu on 2017/6/10.
 */

public class PhoneInfo {
    public String ICCID;
    public String IMEI1;
    public String IMEI2;
    public String IMSI1;
    public String IMSI2;
    public String LinkManNumber;
    public String PhoneNumber;
    public String SmsNumber;
    public String bluetoothMac;
    public String callRecordNumber;
    public String chipName;
    public int isRoot;
    public String wifiMac;

    public PhoneInfo(){}

    @Override
    public String toString() {
        return "PhoneInfo{" +
                "ICCID='" + ICCID + '\'' +
                ", IMEI1='" + IMEI1 + '\'' +
                ", IMEI2='" + IMEI2 + '\'' +
                ", IMSI1='" + IMSI1 + '\'' +
                ", IMSI2='" + IMSI2 + '\'' +
                ", LinkManNumber='" + LinkManNumber + '\'' +
                ", PhoneNumber='" + PhoneNumber + '\'' +
                ", SmsNumber='" + SmsNumber + '\'' +
                ", bluetoothMac='" + bluetoothMac + '\'' +
                ", callRecordNumber='" + callRecordNumber + '\'' +
                ", chipName='" + chipName + '\'' +
                ", isRoot=" + isRoot +
                ", wifiMac='" + wifiMac + '\'' +
                '}';
    }
}
