package com.collection.tools.provider;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.collection.tools.entity.ImageInfo;
import com.collection.tools.utils.FileHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John.Lu on 2017/6/10.
 */

public class ImageProvider implements AbsProvider {

    private Context mContext;
    private boolean isMd5;
    private List<ImageInfo> imageInfos;

    public ImageProvider(Context context,boolean md5)
    {
        mContext = context;
        isMd5 = md5;
    }

    @Override
    public void checkRollback(Context context) {

    }

    @Override
    public List<?> getList() {
        if(mContext != null)
        {
            Cursor cursor = mContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
            if (cursor != null)
            {
                imageInfos = new ArrayList<>();
                while (cursor.moveToNext())
                {
                    int id  = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                    String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                    String path = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
                    String displayName = cursor.getString(cursor.getColumnIndexOrThrow("_display_name"));
                    String type = cursor.getString(cursor.getColumnIndexOrThrow("mime_type"));
                    long size = cursor.getLong(cursor.getColumnIndexOrThrow("_size"));
                    String createDate = cursor.getString(cursor.getColumnIndexOrThrow("date_added"));
                    String latitude = cursor.getString(cursor.getColumnIndexOrThrow("latitude"));
                    String longtitude = cursor.getString(cursor.getColumnIndexOrThrow("longitude"));
                    String md5 ="";
                    if(isMd5)
                    {
                        md5 = FileHelper.getFileMd5(path);
                    }

                    ImageInfo imageInfo = new ImageInfo(createDate,displayName,id,latitude,longtitude,md5,type,path,size,title);
                    imageInfos.add(imageInfo);
                }

            cursor.close();
            }


        }
        return imageInfos;
    }


}
