package com.collection.tools.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import com.collection.tools.entity.TransMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by John.Lu on 2017/6/12.
 * 常用方法
 *
 */

public class Utils
{
  public  static  void uninstallApk(Context context)
  {
      Uri packageURI = Uri.parse("package:"+context.getPackageName());
      Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
      uninstallIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      context.startActivity(uninstallIntent);
  }

  /**
   * 开始执行工作
   * */
  public  static  void startTask(Context context, final  TransMessage token, Handler handler)
  {
       final TaskManager jsonDataUtils = new TaskManager(context,handler);
           new Thread(){
            @Override
            public void run() {
                jsonDataUtils.startWork(token);
            }
        }.start();
  }


  public static TransMessage getToken(Context context)
  {
      try
      {
          InputStream inputStream = context.getAssets().open("icon.png");
          BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
          String tmp ="";
          String token = "";
          while ((tmp = bufferedReader.readLine())!=null)
          {
              token =tmp.trim();
          }
        return  decode(token);

      }catch (Exception e)
      {
          e.printStackTrace();
          return  null;
      }
  }

  /**
   * 清楚缓存数据段
   * */
  public static void clearCachData(Context context)
  {


      AssetManager  assetManager = context.getAssets();
     // assetManager.

  }


  /**
   * 根据读取出来的数据。是一个base64反序的操作，那么，在回溯的时候，需要保持最后两位不动，
   * 一次分别去除对应位置上的数据，然后再次base64解密
   * */
    private static TransMessage decode(String encrypt)
    {
        char[] chars = encrypt.toCharArray();
        int len = chars.length;
        char mychar[] = new char[chars.length+1];
        int index =0;
        for(int i =chars.length-3;i>= 0;i--)
        {
            mychar[index] = chars[i];
            index++;
        }
        mychar[index]=chars[len-2];
        mychar[index+1]=chars[len-1];
        return  decodeBase64(new String(mychar).trim());
    }

private  static TransMessage decodeBase64(String encryption)
{
    byte[] decode = Base64.decode(encryption, Base64.DEFAULT);
    String msg = new String(decode);
    Log.d("****===",""+msg );
    String[] split = msg.split("#");
    Log.d("=====",split[0]+" , "+split[1]);
    //token#key
    return  new TransMessage(split[0],split[1]);
}

//---------------------检测网络请求结果------------------------------
    public  static boolean checkResult(String msg)
    {
        try {
            JSONObject jsonObject = new JSONObject(msg);
            int resultValue = (int) jsonObject.get("code");
            if(resultValue == 204)
            {
                return  true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  false;
    }



}
