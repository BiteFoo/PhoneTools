package com.collection.tools;

import android.os.Bundle;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by John.Lu on 2017/6/14.
 */

public abstract class AbsActivity extends AppCompatActivity {

    protected HandlerThread  mHandlerThread;

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        mHandlerThread =new HandlerThread("taskThread");
        mHandlerThread.start();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mHandlerThread != null)
        {
            mHandlerThread.quit();
            mHandlerThread =null;
        }
    }




}
