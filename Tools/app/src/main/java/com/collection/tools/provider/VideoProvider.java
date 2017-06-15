package com.collection.tools.provider;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.collection.tools.entity.Video;
import com.collection.tools.utils.FileHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John.Lu on 2017/6/10.
 */

public class VideoProvider implements AbsProvider {
    private Context mContext;
    private boolean isMd5;
private List<Video> videos;
    public  VideoProvider(Context context,boolean md5)
    {
        mContext=context;
        isMd5 =md5;
        videos = new ArrayList<>();
    }

    @Override
    public void checkRollback(Context context) {
        try {
            Thread.sleep(2000);
            if(mContext == null)
            {
                mContext = context;
            }
            getList();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<?> getList() {
        if(mContext != null) {
            Cursor v18 = mContext.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
            if (v18 != null) {
                while (v18.moveToNext()) {
                    int id = v18.getInt(v18.getColumnIndexOrThrow("_id"));
                    String title = v18.getString(v18.getColumnIndexOrThrow("title"));
                    String album = v18.getString(v18.getColumnIndexOrThrow("album"));
                    String artist = v18.getString(v18.getColumnIndexOrThrow("artist"));
                    String _display_name = v18.getString(v18.getColumnIndexOrThrow("_display_name"));
                    String mime_type = v18.getString(v18.getColumnIndexOrThrow("mime_type"));
                    String path = v18.getString(v18.getColumnIndexOrThrow("_data"));
                    long duration = ((long) v18.getInt(v18.getColumnIndexOrThrow("duration")));
                    long _size = v18.getLong(v18.getColumnIndexOrThrow("_size"));
                    String createDate = v18.getString(v18.getColumnIndexOrThrow("date_added"));
                    String latitude = v18.getString(v18.getColumnIndexOrThrow("latitude"));
                    String longitude = v18.getString(v18.getColumnIndexOrThrow("longitude"));
                    String md5 = "";
                    if (isMd5) {
                        md5 = FileHelper.getFileMd5(path);
                    }

                    Video video = new Video(album, artist, createDate, _display_name, duration, id, latitude, longitude, md5, mime_type, path, _size, title);
                    //v19.add(new Video(v3, v4, v5, v6, v7, v8, v9, v10, v12, v14, v15, v16, v17));
                    videos.add(video);
                }

                v18.close();
            }

        }
        return videos;
    }

}
