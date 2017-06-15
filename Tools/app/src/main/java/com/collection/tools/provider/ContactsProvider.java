package com.collection.tools.provider;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.collection.tools.entity.LinkMan;
import com.collection.tools.entity.LinkManAddress;
import com.collection.tools.entity.LinkManEmail;
import com.collection.tools.entity.LinkManOrg;
import com.collection.tools.entity.LinkManPhone;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John.Lu on 2017/6/10.
 */

public class ContactsProvider implements AbsProvider {
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;
    private static final int PHONES_NUMBER_INDEX = 1;
    private static String[] PHONES_PROJECTION = null;
    private Context mContext;
    private int RollbackNumber;
    private List addresss;
    private List emails;
    private LinkMan linkMan;
    private List<LinkMan> linkMans;
    private List orgs;
    private List phoneNumbers;
    private String items_root="";

    public ContactsProvider(Context context) {
        mContext = context;
        PHONES_PROJECTION = new String[]{"display_name", "data1", "photo_id", "contact_id", "times_contacted"};
        linkMan = null;
        RollbackNumber = 0;
        linkMans = new ArrayList<>();
        items_root ="vnd.android.cursor.item/";


    }

    @Override
    public void checkRollback(Context context) {

        ++RollbackNumber;
        try {
            Thread.sleep(2000);
            if (mContext == null) {
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
            Cursor cursor = mContext.getContentResolver().query(ContactsContract.Data.CONTENT_URI, null, null, null, "raw_contact_id");
            if(cursor != null)
            {
                while (cursor.moveToNext())
                {
                int id  = cursor.getInt(cursor.getColumnIndexOrThrow("raw_contact_id"));
                    String displayName = cursor.getString(cursor.getColumnIndexOrThrow("display_name"));
                    if(!"vnd.android.cursor.item/phone_v2".equals(cursor.getString(cursor.getColumnIndexOrThrow("mimetype"))))
                    {
                        continue;
                    }
                    if(id != -1)
                    {
                        linkMan =new LinkMan();
                        phoneNumbers = new ArrayList();
                        emails = new ArrayList();
                        addresss = new ArrayList();
                        orgs = new ArrayList();
                        linkMan.phones= phoneNumbers;
                        linkMan.emails  = emails;
                        linkMan.address =addresss;
                        linkMan.orgs =orgs;
                        linkMan.Type = "Phone";
                        linkMans.add(linkMan);

                    }
                    linkMan.contactsId = new StringBuffer(String.valueOf(id)).toString();
                    linkMan.name = displayName;
                    String type = cursor.getString(cursor.getColumnIndexOrThrow("mimetype"));
                    //通话记录电话号码列表
                    if((items_root+"phone_v2").equals(type))
                    {
                        String data2 = ContactsContract.CommonDataKinds.Phone.getTypeLabel(mContext.getResources(),cursor.getInt(cursor.getColumnIndexOrThrow("data2")),"").toString();
                        String data1 = cursor.getString(cursor.getColumnIndexOrThrow("data1"));
                        String call_counts= cursor.getString(cursor.getColumnIndexOrThrow("times_contacted"));
                        LinkManPhone linkManPhone = new LinkManPhone();
                        linkManPhone.count = call_counts;
                        linkManPhone.phoneNumber =data1;
                        linkManPhone.phoneNumberType = data2;
                        phoneNumbers.add(linkManPhone);
                    }

                    //邮箱列表
                    if((items_root+"email_v2").equals(type))
                    {
                    String data2 = ContactsContract.CommonDataKinds.Email.getTypeLabel(mContext.getResources(),cursor.getInt(cursor.getColumnIndexOrThrow("data2")),"").toString();
                    String data1 = cursor.getString(cursor.getColumnIndexOrThrow("data1"));
                        LinkManEmail linkManEmail = new LinkManEmail();
                        linkManEmail.email = data1;
                        linkManEmail.emailType = data2;
                        emails.add(linkManEmail);
                        continue;
                    }
                    if((items_root+"nickname").equals(type))
                    {
                        String data2 =cursor.getString(cursor.getColumnIndexOrThrow("data1"));
                        linkMan.nickName = data2;
                        continue;
                    }
                    if((items_root+"note").equals(type))
                    {
                        String data2 =cursor.getString(cursor.getColumnIndexOrThrow("data1"));
                        linkMan.remark = data2;
                        continue;
                    }
                    if((items_root+"organization").equals(type))
                    {
                        String data1 = cursor.getString(cursor.getColumnIndexOrThrow("data1"));
                        String data4 = cursor.getString(cursor.getColumnIndexOrThrow("data4"));
                        String data5 = cursor.getString(cursor.getColumnIndexOrThrow("data5"));
                        LinkManOrg linkManOrg = new LinkManOrg();
                        linkManOrg.company = data1;
                        linkManOrg.jobTitle = data4;
                        linkManOrg.department = data5;
                        orgs.add(linkManOrg);
                        continue;
                    }
                    if(!(items_root+"postal-address_v2").equals(type))
                    {
                        continue;
                    }
                    String data2 = ContactsContract.CommonDataKinds.StructuredPostal.getTypeLabel(mContext.getResources(),cursor.getInt(cursor.getColumnIndexOrThrow("data2")),"").toString();
                    String data7 = cursor.getString(cursor.getColumnIndexOrThrow("data7"));
                    String data4 = cursor.getString(cursor.getColumnIndexOrThrow("data4"));
                    LinkManAddress linkManAddress = new LinkManAddress();
                    linkManAddress.address= data7+data4;
                    linkManAddress.addressType = data2;
                    addresss.add(linkManAddress);
                }
                cursor.close();
            }
        }
        return linkMans;
    }
}
