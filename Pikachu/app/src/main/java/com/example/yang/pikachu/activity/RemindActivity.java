package com.example.yang.pikachu.activity;

import android.app.Activity;
import android.app.Service;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;

import com.example.yang.pikachu.R;
import com.example.yang.pikachu.dialog.SimpleDialog;

/**
 * Created by Yang on 2018/7/2.
 */

public class RemindActivity extends Activity {
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        //闹钟提醒对话框
        String message = this.getIntent().getStringExtra("msg");
        showDialogInBroadcastReceiver(message);
    }

    private void showDialogInBroadcastReceiver(String message) {
        //响铃
        mediaPlayer = MediaPlayer.create(this, R.raw.in_call_alarm);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        //震动
        //数组参数意义：第一个参数为等待指定时间后开始震动，震动时间为第二个参数。后边的参数依次为等待震动和震动的时间
        //第二个参数为重复次数，-1为不重复，0为一直震动
        vibrator = (Vibrator) this.getSystemService(Service.VIBRATOR_SERVICE);
        vibrator.vibrate(new long[]{100, 10, 100, 600}, 0);

        final SimpleDialog dialog = new SimpleDialog(this, R.style.Theme_dialog);
        dialog.show();
        dialog.setTitle("闹钟提醒");
        dialog.setMessage(message);
        dialog.setClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //停止闹钟
                if (dialog.bt_confirm == v || dialog.bt_cancel == v) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    vibrator.cancel();
                    dialog.dismiss();
                    finish();
                }
            }
        });
    }

}
