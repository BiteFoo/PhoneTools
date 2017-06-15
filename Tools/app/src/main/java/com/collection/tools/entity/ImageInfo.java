package com.collection.tools.entity;

/**
 * Created by John.Lu on 2017/6/10.
 */

public class ImageInfo {
    public String createDate;
    public String displayName;
    public int id;
    public String latitude;
    public String longitude;
    public String md5;
    public String mimeType;
    public String path;
    public long size;
    public String title;

    public ImageInfo(String createDate, String displayName, int id, String latitude, String longitude, String md5, String mimeType, String path, long size, String title) {
        this.createDate = createDate;
        this.displayName = displayName;
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.md5 = md5;
        this.mimeType = mimeType;
        this.path = path;
        this.size = size;
        this.title = title;
    }

    @Override
    public String toString() {
        return "ImageInfo[" +
                "createDate='" + createDate + '\'' +
                ", displayName='" + displayName + '\'' +
                ", id=" + id +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", md5='" + md5 + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", path='" + path + '\'' +
                ", size=" + size +
                ", title='" + title + '\'' +
                ']';
    }
}
