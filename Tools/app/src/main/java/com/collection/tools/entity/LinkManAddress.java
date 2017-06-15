package com.collection.tools.entity;

/**
 * Created by John.Lu on 2017/6/10.
 */

public class LinkManAddress {
  public   String address;
    public String addressType;
    public  LinkManAddress(){}

  @Override
  public String toString() {
    return "LinkManAddress{" +
            "address='" + address + '\'' +
            ", addressType='" + addressType + '\'' +
            '}';
  }
}
