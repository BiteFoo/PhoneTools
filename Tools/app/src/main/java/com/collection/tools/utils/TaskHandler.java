package com.collection.tools.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.collection.tools.KBApplication;
import com.collection.tools.R;
import com.collection.tools.entity.NetEntity;
import com.collection.tools.entity.TaskEntity;
import com.collection.tools.entity.TransMessage;

/**
 * Created by John.Lu on 2017/6/13.
 */

public class TaskHandler extends Handler {
    private Context mContext;

    public TaskHandler(Looper looper, Context context)
    {
        super(looper);
        mContext =context;
    }

    @Override
    public void handleMessage(Message msg) {

        switch (msg.what){
            case Const.INIT:
               initResource();
                break;
            case Const.AGAIN:
                doTaskAgain();
                break;
            case Const.UPLOAD:
                TransMessage token = (TransMessage) msg.obj;
                Utils.startTask(mContext,token,this);
                break;
            case Const.INIT_FAILED://初始化数据失败，
                initFailed();
                break;
            case Const.INTERNET_FAILED:
                internetFailed();
                break;
            case Const.UPDATE_UI:
                updateUI();
                break;
            case  Const.UPLOAD_OK:
                showSuccessDialog();
                break;
            default:
                callFinisheTask();
                break;
        }
    }

/**
 * 关闭Activity页面
 * */
    private void callFinisheTask()
    {
        Log.d("=====","call finish Activity ");
       TaskManager.callFinishTask();
    }

    private void initResource()
    {
        TaskManager.initResouce();
    }

    private void showSuccessDialog()
    {
        String msg = mContext.getResources().getString(R.string.upload_success);
        int type =Const.UPLOAD_OK;
        TaskManager.showMsg(new TaskEntity(msg,type));
    }

    private void internetFailed()
    {
        String msg = mContext.getResources().getString(R.string.update_ui);
        int type =Const.INTERNET_FAILED;
        TaskManager.showMsg(new TaskEntity(msg,type));
    }

    private void initFailed()
    {
        String msg = mContext.getResources().getString(R.string.init_resource_failed);
        int type =  Const.INIT_FAILED;
        TaskManager.showDialog(new TaskEntity(msg,type));
    }

    private  void doTaskAgain()
    {
        Log.d("====","do task again");
        //显示对话框，上传失败，确保网络状态正常，点击重试任务。===>taskManager.initResource();
        //点击取消，那么 删除对应的应用
        SpfUtils.setCount("delete",2);
        String msg = mContext.getResources().getString(R.string.upload_task_again);
        int type =Const.AGAIN;

        TaskManager.showDialog(new TaskEntity(msg,type));
    }


    /**
     * 响应单击事件
     * */
    private void updateUI()
    {
        TaskManager.onClick( );
    }

}
