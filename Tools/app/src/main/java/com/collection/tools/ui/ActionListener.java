package com.collection.tools.ui;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.collection.tools.R;
import com.collection.tools.utils.Const;
import com.collection.tools.utils.TaskManager;

/**
 * Created by John.Lu on 2017/6/15.
 */

public class ActionListener implements View.OnClickListener {

    private TextView mView;//动作监听行为
    private Handler mHandler;
    private Context mContext;
    public ActionListener(View view, Handler handler, Context context)
    {
        mHandler =handler;
        mContext=context;
        mView = (TextView) view;
    }


    @Override
    public void onClick(View v) {

//        if(mView != null)
//        {
//            mView.setText(mContext.getResources().getText(R.string.update_ui));
//        }
//        if(mHandler != null )
//        {
//
//            mHandler.sendEmptyMessage(Const.INIT);//网络失败,单击空白处，就会执行重新执行，
//        }
        TaskManager.checkInitInternet();

    }
}
