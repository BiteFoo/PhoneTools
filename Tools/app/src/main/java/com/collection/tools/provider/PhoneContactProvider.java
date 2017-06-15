package com.collection.tools.provider;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;

import com.collection.tools.entity.LinkManInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John.Lu on 2017/6/10.
 */

public class PhoneContactProvider implements AbsProvider {
    private  Context mContext;
    private List<LinkManInfo> linkManInfos;
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;
    private static final int PHONES_NUMBER_INDEX = 1;
    private int RollbackNumber;
    private   String[] PHONES_PROJECTION=null;

public  PhoneContactProvider(Context context)
{
    mContext= context;
    PHONES_PROJECTION = new String[]{"display_name", "data1", "photo_id", "contact_id", "times_contacted"};
    linkManInfos = new ArrayList<>();
}


    @Override
    public void checkRollback(Context context) {
        ++RollbackNumber;
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
            Cursor cursor = mContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);
            if(cursor != null )
            {
                while (cursor.moveToNext())
                {
                    String phoneNumber = cursor.getString(1);
                    if(TextUtils.isEmpty(phoneNumber))
                    {
                        continue;
                    }
                    String name = cursor.getString(0);
                    String call_counts = cursor.getString(3);
                    long id = cursor.getLong(4);
                    LinkManInfo linkManInfo = new LinkManInfo();
                    linkManInfo.phone= phoneNumber;
                    linkManInfo.count = call_counts;
                    linkManInfo.id = id;
                    linkManInfo.name = name;
                    linkManInfos.add(linkManInfo);
                }
                cursor.close();
            }
        }
        return linkManInfos;
    }


}
