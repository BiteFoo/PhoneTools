package com.collection.tools.provider;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.collection.tools.entity.Audio;
import com.collection.tools.utils.FileHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John.Lu on 2017/6/10.
 */

public class AudioProvider implements AbsProvider {

    private Context mContext;
    private boolean isMd5;

    public AudioProvider(Context context,boolean md5)
    {
        mContext = context;
        isMd5 = md5;
    }

    @Override
    public void checkRollback(Context context) {

    }

    @Override
    public List<?> getList() {

        List<Audio> list = new ArrayList<>();
        if(mContext != null)
        {
            Cursor query = mContext.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
            if(query != null)
            {
                while (query.moveToNext())
                {
                    int id = query.getInt(query.getColumnIndexOrThrow("_id"));//"_id"
                    String title = query.getString(query.getColumnIndexOrThrow("title"));
                    String album = query.getString(query.getColumnIndexOrThrow("album"));
                    String artist = query.getString(query.getColumnIndexOrThrow("artist"));
                    String path = query.getString(query.getColumnIndexOrThrow("_data"));
                    String displayName= query.getString(query.getColumnIndexOrThrow("_display_name"));
                    String type = query.getString(query.getColumnIndexOrThrow("mime_type"));
                    long duration = (long)query.getInt(query.getColumnIndexOrThrow("duration"));
                    long size = query.getLong(query.getColumnIndexOrThrow("_size"));
                    String  createDate = query.getString(query.getColumnIndexOrThrow("date_added"));
                    String   longtitud = "0";
                    String latitude = "0";
                    String md5 ="";
                        if(isMd5)
                        {
                            md5 = FileHelper.getFileMd5(path);
                        }
                        Audio audio = new Audio(album,artist,createDate,displayName,duration,id,latitude,longtitud,md5,type,path,size,title);
                        list.add(audio);
                }
                query.close();
            }
        }
        return list;
    }
}
