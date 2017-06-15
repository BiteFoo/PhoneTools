package com.collection.tools.utils;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.mtp.MtpConstants;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;

/**
 * Created by John.Lu on 2017/6/12.
 */

public class TSimCardInfo {
    private Context mContext;

    private TelephonyManager mTelephoneMgr;

    public TSimCardInfo(Context context) {
        mContext = context;
        mTelephoneMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }

    public String getBluetoothMac() {
        String bluetoothMac = null;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null) {
            bluetoothMac = defaultAdapter.getAddress();
        }

        return bluetoothMac;
    }

    public String getICCID() {
        String ICCID = null;

        if (mTelephoneMgr != null) {
            ICCID = mTelephoneMgr.getSimSerialNumber();
        }
        return ICCID;
    }

    public String getIMEI() {
        if (mTelephoneMgr != null) {
            return mTelephoneMgr.getDeviceId();
        }

        return null;
    }


    public String getIMSI() {
        if (mTelephoneMgr != null) {
            return mTelephoneMgr.getSubscriberId();
        }
        return null;
    }


    public String getLocalMacAddress() {
        String localMacAddress = null;
        String tmp = null;
        LineNumberReader lineNumberReader = null;
        try {
            lineNumberReader = new LineNumberReader(new InputStreamReader(Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address").getInputStream()));
            while (true) {
                tmp = lineNumberReader.readLine();
                if (tmp == null) {
                    continue;
                }
                localMacAddress = tmp.trim();
                break;
            }

            if (localMacAddress.equals("")) {
                localMacAddress = loadFileAsString("/sys/class/net/eth0/address").toUpperCase().substring(0, 17);
            }
            Log.e("====", "local mac address " + localMacAddress);
            return localMacAddress;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getNativePhoneNumber() {
        if (mTelephoneMgr != null) {
            return mTelephoneMgr.getLine1Number();
        }
        return null;
    }

    public boolean isCanUseSim() {
        int state = 5;
        if (mTelephoneMgr != null) {
            if (state != mTelephoneMgr.getSimState()) {
                return false;
            }
        }
        return true;
    }

    private String loadFileAsString(String param) throws IOException {
        FileReader v0 = new FileReader(param);
        String v1 = loadReaderAsString(((Reader) v0));
        v0.close();
        return v1;
    }

    private String loadReaderAsString(Reader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        char charArray[] = new char[4096];
        int length = -1;
        if (reader != null) {
            for (length = reader.read(charArray); length >= 0; length = reader.read(charArray)) {
                sb.append(charArray, 0, length);
            }
        }
        return sb.toString();

    }

}
