package com.example.yang.pikachu.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


public class NotiReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        Intent real = intent.getParcelableExtra("real");
//        Intent real = intent;
        String type = intent.getStringExtra("type");
        String name = intent.getStringExtra("name");
        Toast.makeText(context, "我是"+type+"，我是你的"+name, Toast.LENGTH_LONG).show();
    }
}
