package com.collection.tools.entity;

/**
 * Created by John.Lu on 2017/6/10.
 */

public class CallRecordInfo {
   public String date;
    public String duration;
    public String name;
    public String number;
    public  String type;

    public  CallRecordInfo()
    {
    }

 @Override
 public String toString() {
  return "CallRecordInfo[" +
          "date='" + date + '\'' +
          ", duration='" + duration + '\'' +
          ", name='" + name + '\'' +
          ", number='" + number + '\'' +
          ", type='" + type + '\'' +
          ']';
 }
}
