package com.collection.tools.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by John.Lu on 2017/6/10.
 */

public class FileHelper {

    private Context mContext;
    private  String path;
    private File mFile;
    private String fileName =null;
    public  FileHelper(Context context){
            mContext =context;
            path = getSdcardPath();
            fileName = "test.txt";
    }

    public  static String getFileMd5(String filePath){
        String md5 ="";
        int len =-1;
        File file = new File(filePath);
        if(!file.isFile())
        {
            return  md5;
        }
        byte buf[] = new byte[1024];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            while (true)
            {
                len = fileInputStream.read(buf);
                if(len == -1)
                {
                    break;
                }
                messageDigest.update(buf);
            }
            fileInputStream.close();
            md5 = new BigInteger(1,messageDigest.digest()).toString(32);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  md5;
    }


    private    String getSdcardPath()
    {
        if(Environment.getExternalStorageState().equals("mounted"))
        {
            return  Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        else
        {
            return mContext.getFilesDir().getAbsolutePath();
        }
    }

    public  void writeFile(String data)
    {
        if(TextUtils.isEmpty(data))
        {
            return;
        }
         mFile = new File(path,fileName);
        Log.d("++++",mFile.getAbsolutePath());
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(mFile);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
