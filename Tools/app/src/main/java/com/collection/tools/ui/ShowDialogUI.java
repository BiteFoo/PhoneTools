package com.collection.tools.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;

import com.collection.tools.entity.TaskEntity;
import com.collection.tools.utils.Const;

/**
 * Created by John.Lu on 2017/6/14.
 * 初始化失败
 * 二次上传
 * 点击取消都要删除
 *
 */

public class ShowDialogUI implements Runnable {
    private Context mContext;
    private Handler mHandler;
    private TaskEntity mTaskEntity;
    public ShowDialogUI(Context context, Handler handler, TaskEntity taskEntity)
    {
        mContext =context;
        mHandler =handler;
        mTaskEntity = taskEntity;
    }

    @Override
    public void run() {
        dialog();
    }


    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(mTaskEntity.getMsg());

        builder.setTitle("提示");

        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                doAction();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mHandler.sendEmptyMessage(Const.UNKWNON);
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }

    private void doAction()
    {
        if(mTaskEntity.getType() == Const.AGAIN)
        {

            action(Const.AGAIN);
            //如果是传输失败。那么点击确认重新上传，那么上传，点击取消，则删除
        }
        else if(mTaskEntity.getType() == Const.INIT_FAILED)
        {
            action(Const.UNKWNON);//未知异常，则删除应用
        }
    }

    private void action(int action)
    {
        if(mHandler != null)
        {
            mHandler.sendEmptyMessage(action);
        }
    }


}
