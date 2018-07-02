package com.example.yang.pikachu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.text.RelativeDateTimeFormatter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yang.pikachu.adapter.MyFragmentPagerAdapter;
import com.example.yang.pikachu.fragment.ClockFragment;
import com.example.yang.pikachu.fragment.PetFragment;
import com.example.yang.pikachu.fragment.SettingFragment;

import java.util.ArrayList;

//AppCompatActivity
public class MainActivity extends FragmentActivity implements View.OnClickListener,
        ViewPager.OnPageChangeListener {
    private ViewPager mPager;
    private FragmentManager fManager;
    private RelativeLayout pet_fragment;
    private RelativeLayout clock_fragment;
    private RelativeLayout setting_fragment;

    private PetFragment fg1;
    private ClockFragment fg2;
    private SettingFragment fg3;

    private ImageView image1;
    private ImageView image2;
    private ImageView image3;

    private TextView text1;
    private TextView text2;
    private TextView text3;

    private ArrayList<Fragment> fragmentsList;
    private MyFragmentPagerAdapter mAdapter;


    private int Gray = 0xFF999999;

    //共享参数
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fManager = getSupportFragmentManager();
        initViewPager();
        initViews();
        initSharedPreferences();
        //startIntent();
    }

//    public void startIntent() {
//        Intent intent = new Intent(this.getApplicationContext(), BackService.class);
//        this.startService(intent);
//    }

    private void initViewPager() {
        fg1 = new PetFragment();
        fg2 = new ClockFragment();
        fg3 = new SettingFragment();
        fragmentsList = new ArrayList<Fragment>();
        fragmentsList.add(fg1);
        fragmentsList.add(fg2);
        fragmentsList.add(fg3);
        mAdapter = new MyFragmentPagerAdapter(fManager,fragmentsList);
    }

    private void initViews() {
        mPager = (ViewPager) findViewById(R.id.mPager);
        pet_fragment = (RelativeLayout) findViewById(R.id.pet_fragment);
        clock_fragment = (RelativeLayout) findViewById(R.id.clock_fragment);
        setting_fragment = (RelativeLayout) findViewById(R.id.setting_fragment);

        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);
        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        text3 = (TextView) findViewById(R.id.text3);
        mPager.setAdapter(mAdapter);
        mPager.setOnPageChangeListener(this);
        pet_fragment.setOnClickListener(this);
        clock_fragment.setOnClickListener(this);
        setting_fragment.setOnClickListener(this);
    }

    private void initSharedPreferences(){
        sharedPreferences = getSharedPreferences("pet", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //第一次登陆时的初始化
        if(!sharedPreferences.contains("times")){
            editor.putInt("times",1);
            editor.putString("name1", "Pikachu");
            editor.putString("name2","Crocodile");
            editor.putString("name3","Elk");
            editor.putString("birthday1", "2018/7/1");
            editor.putString("birthday2","2018/7/2");
            editor.putString("birthday3","2018/7/3");
            editor.putString("character1", "很皮,懒惰");
            editor.putString("character2","欠扁,好吃");
            editor.putString("character3","同九年,何汝秀");

            editor.putBoolean("isSecondUnlock",true);
            editor.putBoolean("isFirstOn",false);
            editor.putBoolean("isSecondOn",false);
            editor.putBoolean("isThirdOn",false);
            //设置界面的初始化
            editor.putBoolean("show", true);
            editor.putBoolean("always", true);
            editor.putBoolean("on",false);
            editor.putBoolean("set",true);
            editor.putBoolean("time",false);
            editor.commit();
            editor.commit();
        }
    }

    public void clearChioce() {
        image1.setImageResource(R.drawable.home);
        image2.setImageResource(R.drawable.clock);
        image3.setImageResource(R.drawable.setting);
        text1.setTextColor(Gray);
        text2.setTextColor(Gray);
        text3.setTextColor(Gray);
    }

    public void setTab(int num) {
        //设置选中的菜单
        switch (num) {
            case R.id.pet_fragment:case 0:
                image1.setImageResource(R.drawable.home_focus);
                text1.setTextColor(Color.parseColor("#02a9f5"));
                mPager.setCurrentItem(0);
                break;
            case R.id.clock_fragment:case 1:
                image2.setImageResource(R.drawable.clock_focus);
                text2.setTextColor(Color.parseColor("#02a9f5"));
                mPager.setCurrentItem(1);
                break;
            case R.id.setting_fragment:case 2:
                image3.setImageResource(R.drawable.setting_focus);
                text3.setTextColor(Color.parseColor("#02a9f5"));
                mPager.setCurrentItem(2);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        clearChioce();
        //设置选中效果
        setTab(v.getId());
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

        if(arg0 == 2) {
            int i = mPager.getCurrentItem();
            clearChioce();
            setTab(i);
        }
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageSelected(int arg0) {
        // TODO Auto-generated method stub

    }
}
