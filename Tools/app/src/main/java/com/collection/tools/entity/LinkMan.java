package com.collection.tools.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John.Lu on 2017/6/10.
 */

public class LinkMan {
    public String Type;
    public  List address;
    public String contactsId;
    public  List emails;
    public String name;
    public  String nickName;
    public  List orgs;
    public  List phones;
    public  String remark;

    public LinkMan()
    {
        this.phones = new ArrayList();
        this.emails = new ArrayList();
        this.address = new ArrayList();
        this.orgs = new ArrayList();
    }

    @Override
    public String toString() {
        return "LinkMan{" +
                "Type='" + Type + '\'' +
                ", address=" + address +
                ", contactsId='" + contactsId + '\'' +
                ", emails=" + emails +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                ", orgs=" + orgs +
                ", phones=" + phones +
                ", remark='" + remark + '\'' +
                '}';
    }
}
