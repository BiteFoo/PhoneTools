package com.collection.tools;

import android.app.Application;
import android.content.Context;

import com.collection.tools.utils.TaskManager;

/**
 * Created by John.Lu on 2017/6/12.
 */

public class KBApplication extends Application {

    private  static Context mContext;


    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
        //TaskManager.init(mContext);
    }
    public  static  Context getmContext()
    {
        return  mContext;
    }

}
