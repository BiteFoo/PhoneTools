package com.collection.tools.entity;

/**
 * Created by John.Lu on 2017/6/14.
 */

public class TransMessage {
    private  String token="";
    private String transkey="";
    public TransMessage(String mtoken,String mtranskey)
    {
        token =mtoken;
        transkey = mtranskey;
    }

    public String getToken() {
        return token;
    }

    public String getTranskey() {
        return transkey;
    }

    @Override
    public String toString() {
        return "TransMessage{" +
                "token='" + token + '\'' +
                ", transkey='" + transkey + '\'' +
                '}';
    }
}
