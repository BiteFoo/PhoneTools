package com.collection.tools.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.collection.tools.entity.TransMessage;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by John.Lu on 2017/6/12.
 */

public class ConfigurationHelper {
    private Handler mHanler;
    private  Context mContext;

    public ConfigurationHelper(Handler handler ,Context conext)
    {
        mContext = conext;
        mHanler =handler;
    }
    /**
     * 读取配置文件
     * */
    public void initConfig()
    {

        //完成上传了。不需要再次上传
            if(SpfUtils.getCout("finished")==1)
            {
                mHanler.sendEmptyMessage(Const.UNKWNON);
                return;
            }
            if(checkConfigFile())
            {
                //写文件配置
                TransMessage transMessage=Utils.getToken(mContext);
                Message message = new Message();
                message.obj = transMessage;
                message.what = Const.UPLOAD;
                mHanler.sendMessage(message);
            }
            else
            {
                //发送删除应用，没有任务信息，请重试
                mHanler.sendEmptyMessage(Const.INIT_FAILED);
            }
    }

    private  boolean checkConfigFile()
    {
        try {
            InputStream inputStream = mContext.getAssets().open("icon.png");
            if(inputStream == null)
            {
                return false;
            }
            //ok。。。。。。。。。。。。。。。。。。


        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


}
