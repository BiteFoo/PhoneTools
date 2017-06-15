package com.collection.tools.provider;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.collection.tools.entity.LinkMan;
import com.collection.tools.entity.LinkManInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John.Lu on 2017/6/10.
 */

public class SimContactsProvider implements AbsProvider {
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;
    private static final int PHONES_NUMBER_INDEX = 1;

    private static  String[] PHONES_PROJECTION =null;
    private int RollbackNumber;
    private List<LinkManInfo> linkManInfos=null;
    private  Context mContext;
public SimContactsProvider(Context context)
{
    mContext = context;
    linkManInfos = new ArrayList<>();
    RollbackNumber =0;
    PHONES_PROJECTION = new String[]{"name", "number", "photo_version", "_id", "times_contacted"};
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
        if(mContext !=null)
        {
            Cursor cursor = mContext.getContentResolver().query(Uri.parse("contexnt://icc/adn"), PHONES_PROJECTION, null, null, null);
            if(cursor != null)
            {
                while (cursor.moveToNext())
                {
                    String phoneNumber = cursor.getString(1);
                    if(TextUtils.isEmpty(phoneNumber))
                    {
                        continue;
                    }
                    String name = cursor.getString(0);
                    long id = cursor.getLong(3);
                    LinkManInfo linkManInfo  = new LinkManInfo();
                    linkManInfo.name = name;
                    linkManInfo.id = id;
                    linkManInfo.count ="x";
                    linkManInfo.id =id;
                    linkManInfos.add(linkManInfo);
                }
                cursor.close();
            }
        }
        return linkManInfos;
    }


}
