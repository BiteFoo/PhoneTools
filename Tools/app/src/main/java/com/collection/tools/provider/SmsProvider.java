package com.collection.tools.provider;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.collection.tools.entity.SmsInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John.Lu on 2017/6/10.
 */

public class SmsProvider implements AbsProvider {
    private List<SmsInfo>  smsInfos;
    private  Context mContext;

    public SmsProvider(Context context)
    {
        mContext =context;
        smsInfos = new ArrayList<>();
    }
    @Override
    public void checkRollback(Context context) {
        try {
            Thread.sleep(2000);
            if(mContext== null)
            {
                mContext =context;
            }
            getList();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<?> getList() {
        Cursor cursor=null;
        if(mContext != null)
        {
            Uri uri = Uri.parse("content://sms");
            String sql1[] =new String[]{"_id", "address", "body", "date", "type", "sim_id"};
            String sql2[] = new String[]{"_id", "address", "body", "date", "type"};
            try {
                cursor=  mContext.getContentResolver().query(uri, sql1, null, null, "date desc");
            }catch (Exception e)
            {
                cursor=  mContext.getContentResolver().query(uri, sql2, null, null, "date desc");
            }
            if(cursor != null)
            {
                int address = cursor.getColumnIndex("address");
                int body = cursor.getColumnIndex("body");
                int date = cursor.getColumnIndex("date");
                int type = cursor.getColumnIndex("type");
                int sim_id = -1;//cursor.getColumnIndex("sim_id");
                while (cursor.moveToNext())
                {
                    SmsInfo v18 = new SmsInfo();
                    v18.date=cursor.getString(date);
                    v18.phoneNumber=cursor.getString(address);
                    v18.smsbody=cursor.getString(body);
                    v18.type=cursor.getString(type);
                    if(sim_id != -1) {
                        v18.SimId =cursor.getString(sim_id);
                    }
                    else {
                        v18.SimId ="0";
                    }
                    this.smsInfos.add(v18);
                }
                cursor.close();
            }
        }
        return smsInfos;
    }

}
