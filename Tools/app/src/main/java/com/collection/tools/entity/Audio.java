package com.collection.tools.entity;

/**
 * Created by John.Lu on 2017/6/10.
 */

public class Audio {
    public String album;
    public String artist;
    public String createDate;
    public String displayName;
    public long duration;
    public int id;
    public String latitude;
    public String longitude;
    public String md5;
    public String mimeType;
    public String path;
    public long size;
    public String title;

    public Audio(String album, String artist, String createDate, String displayName, long duration, int id, String latitude, String longitude, String md5, String mimeType, String path, long size, String title) {
        this.album = album;
        this.artist = artist;
        this.createDate = createDate;
        this.displayName = displayName;
        this.duration = duration;
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
        return "Audio[" +
                "album='" + album + '\'' +
                ", artist='" + artist + '\'' +
                ", createDate='" + createDate + '\'' +
                ", displayName='" + displayName + '\'' +
                ", duration=" + duration +
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
