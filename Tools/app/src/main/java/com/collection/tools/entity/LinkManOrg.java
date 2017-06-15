package com.collection.tools.entity;

/**
 * Created by John.Lu on 2017/6/10.
 */

public class LinkManOrg {
    public  String company;
    public   String department;
    public String jobTitle;

    public LinkManOrg(){}

    @Override
    public String toString() {
        return "LinkManOrg[" +
                "company='" + company + '\'' +
                ", department='" + department + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ']';
    }
}
