package com.collection.tools.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import com.collection.tools.R;

/**
 * Created by John.Lu on 2017/6/15.
 */

public class UpdateUI implements Runnable {
    private TextView mTextView;
    private Activity mContext;

    public UpdateUI(View view,Activity context)
    {
        mTextView = (TextView) view;
        mContext = (Activity) context;
        context.runOnUiThread(this);
    }
    @Override
    public void run() {

        if(mTextView!= null && mContext != null)
        {
            mTextView.setText(mContext.getResources().getText(R.string.update_ui));
        }
    }
}
