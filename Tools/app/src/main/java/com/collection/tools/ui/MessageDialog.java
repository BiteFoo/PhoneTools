package com.collection.tools.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AlertDialog;

import com.collection.tools.entity.TaskEntity;
import com.collection.tools.utils.Const;

/**
 * Created by John.Lu on 2017/6/14.
 *
 * 显示消息对话框，包括网络状态或是成功上传
 * 提示消息：
 * 网络状态异常，请检查，并且重试，重试选择使用单击页面之后，发送消息
 * 上传成功，删除应用，
 *
 * 上传成功。删除应用 ：无论点击确认或是取消，都会删除
 * 网络失败，提示检测网络，并点击重试按钮 ，无路点击确认或是取消，都会关闭对话框。提示点击重试按钮
 *
 */

public class MessageDialog implements  Runnable {

        private Handler mHandler;
        private  Context mContext;
         private TaskEntity mTaskEntity;
    public MessageDialog(Context context, Handler handler,TaskEntity taskEntity)
    {
        mContext =context;
        mHandler =handler;
        mTaskEntity =taskEntity;
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
               // mHandler.sendEmptyMessage(Const.UNKWNON);
                doAction();
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }

    private  void doAction()
    {
        if(mTaskEntity.getType() == Const.INTERNET_FAILED)
        {
            //网络失败，只提示信息，不提供任何操作。//更新ui
            mHandler.sendEmptyMessage(Const.UPDATE_UI);
        }
        else if(mTaskEntity.getType() == Const.UPLOAD_OK)
        {
            mHandler.sendEmptyMessage(Const.UNKWNON);
            //上传成功，点击确认或者是其他的行为，都会删除当前应用
        }

    }



}
