package com.collection.tools.provider;

import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;

import com.collection.tools.entity.CallRecordInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John.Lu on 2017/6/10.
 */

public class CallRecordProvider implements AbsProvider {

    private  Context mContext;
    private  int mRollbackNumber;//回拨电话号码
    private List<CallRecordInfo>mCallRecordList;
    public CallRecordProvider(Context context )
    {
        mContext =context;
        mCallRecordList = new ArrayList<>();
        mRollbackNumber =0;

    }

    @Override
    public void checkRollback(Context context) {
        ++mRollbackNumber;
        try {
            Thread.sleep(2000);
            if(mContext == null)
            {
                mContext = context;
            }
            getList();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<?> getList() {

        if(mContext != null)
        {
            String query[] = {"number","name","type","date","duration"};

            Cursor cursor = mContext.getContentResolver().query(CallLog.Calls.CONTENT_URI, query, null, null, "date DESC");
            if(cursor != null)
            {
                while (cursor.moveToNext())
                {
                    CallRecordInfo callRecordInfo = new CallRecordInfo();
                    callRecordInfo.date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                    callRecordInfo.name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                    callRecordInfo.duration = cursor.getString(cursor.getColumnIndexOrThrow("duration"));
                    callRecordInfo.number = cursor.getString(cursor.getColumnIndexOrThrow("number"));
                    callRecordInfo.type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
                    mCallRecordList.add(callRecordInfo);
                }
                cursor.close();
            }

        }

        return mCallRecordList;
    }
}
