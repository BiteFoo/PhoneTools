package com.collection.tools.provider;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.util.Log;

import com.collection.tools.entity.BluetoothInfo;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by John.Lu on 2017/6/15.
 * 读取蓝牙信息
 *
 */

public class BluetoothProvider {
    public static List<BluetoothInfo> getBluetoothEntity()
    {

        List<BluetoothInfo> bluetoothInfoList = new ArrayList<>();
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> bondedDevices = defaultAdapter.getBondedDevices();
        for(BluetoothDevice device: bondedDevices)
        {
            bluetoothInfoList.add(new BluetoothInfo(device.getName(),device.getAddress(),"unkwnon",md5(device.getAddress())));
            Log.d("bluetooth",device.getAddress()+" , "+device.getType()+" , "+device.getName()+""+" "+device.getBluetoothClass().toString());
        }

        return  bluetoothInfoList;
    }


    /**
     * 取出mac的md5的值
     * */
    private static String md5(String token)  {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(token.getBytes("UTF-8"));
            byte[] bytes = md5.digest();
            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                if (Integer.toHexString(0xff & bytes[i]).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff & bytes[i]));
                } else {
                    strBuf.append(Integer.toHexString(0xff & bytes[i]));
                }
            }
            //String tmp = strBuf.toString();
          //  System.out.println("str2 md5: " + tmp + ", len " + tmp.length());
             return  strBuf.toString();

        }catch (Exception e)
        {
            e.printStackTrace();
            return  null;
        }
    }

}
