package com.example.yang.pikachu.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yang.pikachu.R;

import java.util.Calendar;

/**
 * Created by Yang on 2018/7/1.
 * Setting activity for pet's property.
 */

public class SettingActivity extends Activity implements OnClickListener{
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ImageView setImg;
    EditText set_et_name,set_et_birthday,set_et_character;
    Button titlebar_bt_return,set_bt_birthday;
    TextView titlebar_bt_save;
    String flag;//标志位

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //set layout view
        setContentView(R.layout.setting);
        init();
    }

    private void init(){
        //get widgets
        //image
        setImg = (ImageView)findViewById(R.id.set_img);
        //editor
        set_et_name = (EditText)findViewById(R.id.set_et_name);
        set_et_birthday = (EditText)findViewById(R.id.set_et_birthday);
        set_et_character = (EditText)findViewById(R.id.set_et_character);
        //return and save
        titlebar_bt_return = (Button)findViewById(R.id.titlebar_bt_return);
        titlebar_bt_save = (TextView)findViewById(R.id.titlebar_bt_save);
        set_bt_birthday = (Button)findViewById(R.id.set_bt_birthday);

        sharedPreferences = getSharedPreferences("pet", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //为按钮绑定监听器
        titlebar_bt_return.setOnClickListener(this);
        titlebar_bt_save.setOnClickListener(this);
        set_bt_birthday.setOnClickListener(this);

        //解码Bundle
        Bundle bundle = this.getIntent().getExtras();
        flag = bundle.getString("flag");
        //根据标志位获取图片来源
        if(flag.equals("1")){
            setImg.setImageResource(R.drawable.pika);
        }
        if(flag.equals("2")){
            setImg.setImageResource(R.drawable.kong);
        }
        if(flag.equals("3")){
            setImg.setImageResource(R.drawable.elk);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        readFromSharedPreferences(flag);
    }

    @Override
    public void onClick(View button) {
        // TODO Auto-generated method stub
        switch(button.getId()){
            //return back
            case R.id.titlebar_bt_return:
                SettingActivity.this.finish();
                break;
            //save action
            case R.id.titlebar_bt_save:
                editor.putString("name"+flag,set_et_name.getText().toString());
                editor.putString("birthday"+flag,set_et_birthday.getText().toString());
                editor.putString("character"+flag,set_et_character.getText().toString());
                editor.commit();
                SettingActivity.this.finish();
                break;
            //set birthday
            case R.id.set_bt_birthday:
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(SettingActivity.this,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0, int year, int month, int day) {
                        // TODO Auto-generated method stub
                        set_et_birthday.setText(year+"/"+(month+1)+"/"+day);

                    }
                    //设置初始日期
                }
                        ,c.get(Calendar.YEAR)
                        ,c.get(Calendar.MONTH)
                        ,c.get(Calendar.DAY_OF_MONTH)).show();
                break;
            default:break;
        }
    }

    public void readFromSharedPreferences(String i){
        //get the setting
        String name = sharedPreferences.getString("name" + i, null);
        String birthday = sharedPreferences.getString("birthday" + i, null);
        String character = sharedPreferences.getString("character" + i, null);
        //them set to show them
        set_et_name.setText(name);
        set_et_birthday.setText(birthday);
        set_et_character.setText(character);
    }
}
