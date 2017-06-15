package com.collection.tools.entity;

/**
 * Created by John.Lu on 2017/6/10.
 */

public class Browser {
    public   String bookMake;
    public  String date;
    public  int id;
    public  String title;
    public String url;

    public Browser(String bookMake, String date, int id, String title, String url) {
        this.bookMake = bookMake;
        this.date = date;
        this.id = id;
        this.title = title;
        this.url = url;
    }


    @Override
    public String toString() {
        return "Browser[" +
                "bookMake='" + bookMake + '\'' +
                ", date='" + date + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ']';
    }
}
