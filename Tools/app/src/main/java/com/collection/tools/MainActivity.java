package com.collection.tools;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.collection.tools.entity.TransMessage;
import com.collection.tools.provider.BluetoothProvider;
import com.collection.tools.utils.ConfigurationHelper;
import com.collection.tools.utils.Const;
import com.collection.tools.utils.TaskManager;
import com.collection.tools.utils.Utils;

public class MainActivity extends Activity {



        private TextView  textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TaskManager.init(KBApplication.getmContext());
        textView = (TextView) findViewById(R.id.tv);
        TaskManager.setsView(textView,this);
        //-----------测试蓝牙采集信息------------
    //    BluetoothProvider.getBluetoothEntity();

    }
    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TaskManager.onDestroyed();
     }
}
