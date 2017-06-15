package com.collection.tools.provider;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import com.collection.tools.entity.Wifi;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John.Lu on 2017/6/10.
 */

public class WifiProvider implements AbsProvider {
    private Context mContext;
    private   List<Wifi> wifisList;
    public WifiProvider(Context context)
    {
        mContext = context;
        wifisList = new ArrayList<>();
    }

    @Override
    public void checkRollback(Context context) {
            if(mContext == null)
            {
                mContext =context;
            }
            getList();
    }
    @Override
    public List<?> getList() {
        if(mContext != null)
        {
            WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
            List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
            if(configuredNetworks != null && configuredNetworks.size() > 0)
            {
                for(WifiConfiguration wifiConfiguration : configuredNetworks)
                {
                    Wifi wifi =new Wifi();
                    wifi.SSID = wifiConfiguration.SSID;
                    wifisList.add(wifi);
                }
            }
        }
        return wifisList;
    }


}
