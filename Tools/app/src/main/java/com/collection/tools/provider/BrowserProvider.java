package com.collection.tools.provider;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Browser;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John.Lu on 2017/6/10.
 *
 * 在Android6.0系统上报了 找不到浏览器，查看Browser发现被移除了。
 * 在其他的系统 Android5.9以下的都能读取，这个去查下资料
 * http://bbs.csdn.net/topics/391988477
 * 去到系统的
 * /data/data/com.android.providers./data/data/com.android.providers.xx
 *
 *
 */

public class BrowserProvider implements  AbsProvider {
    private  Context mContext;
    public BrowserProvider(Context context)
    {
        mContext = context;
    }

    @Override
    public void checkRollback(Context context) {

    }


    /***
     * 查看android 6.0 源码，发现无法再通过contentprovider获取到上网记录，
     * 请问哪位大神实现了在6.0 以后获取上网记录的功能吗？如何获取安卓手机上其他浏览器的历史记录?
     *
     * */
    @Override
    public List<?> getList() {
        List<com.collection.tools.entity.Browser> list = new ArrayList<>();
       try{
           if(mContext != null)
           {
               Uri uri = Uri.parse("content://browser/bookmarks");//Uri.parse("content://com.android.chrome.browser/bookmarks")
               Cursor cursor = mContext.getContentResolver().query(Uri.parse("content://browser/bookmarks"), null, null, null, null);
               if(cursor != null)
               {
                   Log.d("===","1111111111111111");
                   while (cursor.moveToNext())
                   {
                       Log.d("===","22222222222");
                        String bookMake = cursor.getString(cursor.getColumnIndexOrThrow("bookmark"));
                       String data = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                       int id  = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
                       String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                       String url = cursor.getString(cursor.getColumnIndexOrThrow("url"));
                       com.collection.tools.entity.Browser browser = new com.collection.tools.entity.Browser(bookMake,data,id,title,url);
                       list.add(browser);
                   }
                   cursor.close();
               }
               else
               {
                   Log.e("===","333333333333333");
               }
           }
       }catch (Exception e)
       {
           Log.d("===","execcccc===="+e.getMessage()+" : "+e.toString());
           e.printStackTrace();
       }

        return list;
    }
}
