package com.collection.tools.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.collection.tools.KBApplication;

/**
 * Created by John.Lu on 2017/5/23.
 *
 * 增加写一个配置文件位置
 */

public class SpfUtils {

    private static SharedPreferences sharedPreferences;
    public  static SharedPreferences.Editor  mEditor;

    public  static void initSharedPreference(Context context)
    {
        sharedPreferences =context.getSharedPreferences("config", Context.MODE_PRIVATE);
        mEditor = sharedPreferences.edit();
    }

    public  static  void setCount(String key,int value)
    {
        initSharedPreference(KBApplication.getmContext());
        mEditor.putInt(key,value);
        mEditor.commit();
    }

    public  static int getCout(String key)
    {
        initSharedPreference(KBApplication.getmContext());
        return  sharedPreferences.getInt(key,0);
    }



}
