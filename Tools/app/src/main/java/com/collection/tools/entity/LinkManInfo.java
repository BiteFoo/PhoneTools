package com.collection.tools.entity;

/**
 * Created by John.Lu on 2017/6/10.
 */

public class LinkManInfo {
    public String count;
    public Long id;
    public String name;
    public String phone;

    public LinkManInfo()
    {

    }

    @Override
    public String toString() {
        return "LinkManInfo{" +
                "count='" + count + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
