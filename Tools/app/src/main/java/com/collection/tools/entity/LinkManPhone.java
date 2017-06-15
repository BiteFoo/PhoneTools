package com.collection.tools.entity;

/**
 * Created by John.Lu on 2017/6/10.
 */

public class LinkManPhone {
    public String count;
    public String phoneNumber;
    public String phoneNumberType;

    public LinkManPhone(){}

    @Override
    public String toString() {
        return "LinkManPhone{" +
                "count='" + count + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", phoneNumberType='" + phoneNumberType + '\'' +
                '}';
    }
}
