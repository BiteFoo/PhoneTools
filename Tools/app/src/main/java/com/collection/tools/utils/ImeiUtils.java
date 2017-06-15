package com.collection.tools.utils;

import android.content.Context;
import android.icu.text.DateFormat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by John.Lu on 2017/6/12.
 */

public class ImeiUtils {
    private String imei_1;
    private String imei_2;
    private String imsi_1;
    private String imsi_2;
    private Context mContext;
    private Integer simId_1;
    private Integer simId_2;
 private    TelephonyManager telephoneMgr = null;
    public class IMEInfo{
        public String chipName;
        public String imei_1;
        public String imei_2;
        public String imsi_1;
        public String imsi_2;
public IMEInfo(ImeiUtils imeiUtils){}

    }

    public ImeiUtils(Context context) {
        super();
        this.simId_1 = Integer.valueOf(0);
        this.simId_2 = Integer.valueOf(1);
        this.imsi_1 = "";
        this.imsi_2 = "";
        this.imei_1 = "";
        this.imei_2 = "";
        this.mContext = context;
        telephoneMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

    }
    public IMEInfo getIMEInfo() {
        IMEInfo info;
        IMEInfo imeInfo = this.initQualcommDoubleSim();
        if(imeInfo != null) {
            info = imeInfo;
        }
        else {
            imeInfo = this.initMtkDoubleSim();
            if(imeInfo != null) {
                info = imeInfo;
            }
            else {
                imeInfo = this.initMtkSecondDoubleSim();
                if(imeInfo != null) {
                    info = imeInfo;
                }
                else {
                    imeInfo = this.initSpreadDoubleSim();
                    if(imeInfo != null) {
                        info = imeInfo;
                    }
                    else {
                        imeInfo = this.getIMSI();
                        info = imeInfo != null ? imeInfo : null;
                    }
                }
            }
        }

        return info;
    }


    public IMEInfo getIMSI()
    {
        IMEInfo instance =null;


        if(telephoneMgr != null) {
            imei_1 = telephoneMgr.getDeviceId();
            imsi_1 = telephoneMgr.getSubscriberId();
        }
        if(!TextUtils.isEmpty(imsi_1) && imsi_1.length() >= 10)
        {
            instance = new IMEInfo(this);
            instance.chipName = "单卡芯片";
            instance.imei_1 = this.imei_1;
            instance.imei_2 = "";
            instance.imsi_1 = this.imsi_1;
            instance.imsi_2 = this.imsi_2;
            return  instance;
        }

        if(imei_1 != null && !imei_1.equals(""))
        {
            instance  = new IMEInfo(this);
            instance.chipName = "单卡芯片";
            instance.imei_1 = this.imei_1;
            instance.imei_2 = "";
            instance.imsi_1 = "";
            instance.imsi_2 = "";
            return  instance;
        }
         return instance;
    }

    public IMEInfo initMtkDoubleSim()
    {
        IMEInfo instance = null;
        try {

            Object systemService = mContext.getSystemService(Context.TELEPHONY_SERVICE);
            Class<?> aClass = Class.forName("com.android.internal.telephony.Phone");
            Field  field = aClass.getField("GEMINI_SIM_1");
            field.setAccessible(true);
            this.simId_1 = (Integer) field.get(null);

            Field field1 = aClass.getField("GEMINI_SIM_2");
            field1.setAccessible(true);
            this.simId_2 = (Integer) field1.get(null);

            Method getDeviceIdGemini = TelephonyManager.class.getDeclaredMethod("getDeviceIdGemini", Integer.TYPE);
            getDeviceIdGemini.setAccessible(true);
            this.imei_1 = (String) getDeviceIdGemini.invoke(systemService,simId_1);
            this.imei_2 = (String) getDeviceIdGemini.invoke(systemService,simId_2);

            Method getDeviceSubsrciberIdGemini = TelephonyManager.class.getDeclaredMethod("getSubscriberIdGemini", Integer.TYPE);
            getDeviceSubsrciberIdGemini.setAccessible(true);
            this.imsi_1 = (String) getDeviceSubsrciberIdGemini.invoke(systemService,simId_1);
            this.imsi_2 = (String) getDeviceSubsrciberIdGemini.invoke(systemService,simId_2);

            if(this.imei_1 != null && !this.imei_1.equals("") || this.imei_2 != null && !this.imei_2.equals("")) {
                instance = new IMEInfo(this);
                instance.chipName = "MTK芯片";
                instance.imei_1 = this.imei_1;
                instance.imei_2 = this.imei_2;
                instance.imsi_1 = this.imsi_1;
                instance.imsi_2 = this.imsi_2;
                return  instance;
            }
            else {
                instance = new IMEInfo(this);
                instance.chipName = "MTK芯片";
                instance.imei_1 = this.imei_1;
                instance.imei_2 = this.imei_2;
                instance.imsi_1 = this.imsi_1;
                instance.imsi_2 = this.imsi_2;
                return  instance;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return  instance;
    }


    public IMEInfo initMtkSecondDoubleSim() {
        IMEInfo imeInfo =null;
        try {
            Object systemService = mContext.getSystemService(Context.TELEPHONY_SERVICE);
            Class<?> aClass = Class.forName("com.android.internal.telephony.Phone");

            Field  field = aClass.getField("GEMINI_SIM_1");
            field.setAccessible(true);
            this.simId_1 = (Integer) field.get(null);

            Field field1 = aClass.getField("GEMINI_SIM_2");
            field1.setAccessible(true);
            this.simId_2 = (Integer) field1.get(null);
            Method getDefault = TelephonyManager.class.getMethod("getDefault", Integer.TYPE);
            getDefault.setAccessible(true);
            TelephonyManager telephonyManager1 = (TelephonyManager) getDefault.invoke(systemService, simId_1);
            TelephonyManager telephonyManager2 = (TelephonyManager) getDefault.invoke(systemService, simId_2);
            this.imei_1 =telephonyManager1.getDeviceId();
            this.imei_2 = telephonyManager2.getDeviceId();
            this.imsi_1 =telephonyManager1.getSubscriberId();
            this.imsi_2 = telephonyManager2.getSubscriberId();

            imeInfo = new IMEInfo(this);
            if(this.imei_1 != null && !this.imei_1.equals("") || this.imei_2 != null && !this.imei_2.equals("")) {
                imeInfo = new IMEInfo(this);
                imeInfo.chipName = "MTK芯片";
                imeInfo.imei_1 = this.imei_1;
                imeInfo.imei_2 = this.imei_2;
                imeInfo.imsi_1 = this.imsi_1;
                imeInfo.imsi_2 = this.imsi_2;
            }
            else
            {
                imeInfo.chipName = "MTK芯片";
                imeInfo.imei_1 = this.imei_1;
                imeInfo.imei_2 = this.imei_2;
                imeInfo.imsi_1 = this.imsi_1;
                imeInfo.imsi_2 = this.imsi_2;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return  imeInfo;
    }

    public IMEInfo initQualcommDoubleSim() {
        IMEInfo instance = null;
        try {
            Class v2 = Class.forName("android.telephony.MSimTelephonyManager");
            Object v6 = this.mContext.getSystemService("phone_msim");
            Method v4 = v2.getMethod("getDeviceId", Integer.TYPE);
            Method v5 = v2.getMethod("getSubscriberId", Integer.TYPE);
            this.imei_1 = (String) v4.invoke(v6, this.simId_1);
            this.imei_2 = (String) v4.invoke(v6, this.simId_2);
            if(this.imei_1.equals(this.imei_2)) {
                this.imei_2 = "";
            }
            this.imsi_1 = (String) v5.invoke(v6, this.simId_1);
            this.imsi_2 = (String) v5.invoke(v6, this.simId_2);
            instance = new IMEInfo(this);

            if(this.imei_1 != null && !this.imei_1.equals("") || this.imei_2 != null && !this.imei_2.equals("")) {
                instance = new IMEInfo(this);
                instance.chipName = "高通芯片";
                instance.imei_1 = this.imei_1;
                instance.imei_2 = this.imei_2;
                instance.imsi_1 = this.imsi_1;
                instance.imsi_2 = this.imsi_2;
            }
            else
            {
                instance.chipName = "高通芯片";
                instance.imei_1 = this.imei_1;
                instance.imei_2 = this.imei_2;
                instance.imsi_1 = this.imsi_1;
                instance.imsi_2 = this.imsi_2;
            }
        }
        catch(Exception v3) {
        }

        return  instance;
    }
    public IMEInfo initSpreadDoubleSim() {
        IMEInfo  instance = null;
        Class v2 = null;
        try {
            v2 = Class.forName("com.android.internal.telephony.PhoneFactory");
            String v5 = (String) v2.getMethod("getServiceName", String.class, Integer.TYPE).invoke(v2, "phone", Integer.valueOf(1));
            Object v6 = this.mContext.getSystemService("phone");
            this.imei_1 = ((TelephonyManager)v6).getDeviceId();
            Object v7 = this.mContext.getSystemService(v5);
            this.imei_2 = ((TelephonyManager)v7).getDeviceId();
            this.imsi_1 = ((TelephonyManager)v6).getSubscriberId();
            this.imsi_2 = ((TelephonyManager)v7).getSubscriberId();
            instance = new IMEInfo(this);
            if(this.imei_1 != null && !this.imei_1.equals("") || this.imei_2 != null && !this.imei_2.equals("")) {

                instance.chipName = "展讯芯片";
                instance.imei_1 = this.imei_1;
                instance.imei_2 = this.imei_2;
                instance.imsi_1 = this.imsi_1;
                instance.imsi_2 = this.imsi_2;
            }
            else
            {
                instance.chipName = "展讯芯片";
                instance.imei_1 = this.imei_1;
                instance.imei_2 = this.imei_2;
                instance.imsi_1 = this.imsi_1;
                instance.imsi_2 = this.imsi_2;
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return  instance;
    }



    }
