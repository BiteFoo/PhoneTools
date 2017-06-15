package com.collection.tools.entity;

/**
 * Created by John.Lu on 2017/6/15.
 */

public class BluetoothInfo {
    public String bluetoothName="";
    public String bluetoothMac="";
    public String md5           ="";
    public String aliasName      ="";
    public BluetoothInfo(String name,String mac,String aliName,String md5Value)
    {
        bluetoothName =name;
        bluetoothMac =mac;
        aliasName =aliName;
        md5=md5Value;
    }





}
