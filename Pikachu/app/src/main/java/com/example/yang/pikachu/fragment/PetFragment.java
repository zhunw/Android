package com.example.yang.pikachu.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yang.pikachu.R;
import com.example.yang.pikachu.activity.SettingActivity;
import com.example.yang.pikachu.service.BackService;
import com.example.yang.pikachu.view.PetSelect;
import com.example.yang.pikachu.view.ToolbarView;

/**
 * Created by Yang on 2018/6/30.
 */

public class PetFragment extends Fragment{
    ToolbarView pet_toolbarview;
    private PetSelect first,second,third,fourth;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pet,container, false);
        //get these widgets
        pet_toolbarview = (ToolbarView)rootView.findViewById(R.id.pet_toolbarview);
        first = (PetSelect)rootView.findViewById(R.id.select_1);
        second = (PetSelect)rootView.findViewById(R.id.select_2);
        third = (PetSelect)rootView.findViewById(R.id.select_3);
        fourth = (PetSelect)rootView.findViewById(R.id.select_4);
        first.setFlag(1);
        second.setFlag(2);
        third.setFlag(3);
        init();
        setListener();
        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();

        first.setName(sharedPreferences.getString("name1", "Pikachu"));
        first.setCheck(sharedPreferences.getBoolean("isFirstOn", false));

        second.setName(sharedPreferences.getString("name2", "Crocodile"));
        second.setCheck(sharedPreferences.getBoolean("isSecondOn", false));

        third.setName(sharedPreferences.getString("name3", "Elk"));
        third.setCheck(sharedPreferences.getBoolean("isThirdOn", false));

        first.setProperties(sharedPreferences);
        second.setProperties(sharedPreferences);
        third.setProperties(sharedPreferences);
    }

    private void init(){
        sharedPreferences = getActivity().getSharedPreferences("pet", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //third.setenabled(false);
        fourth.setenabled(false);

        //set image
        first.setPetImg(R.drawable.pika);
        second.setPetImg(R.drawable.kong);
        third.setPetImg(R.drawable.elk);
        fourth.setPetImg(R.drawable.v_unlock);

        //set properties
        first.setProperties(sharedPreferences);
        second.setProperties(sharedPreferences);
        third.setProperties(sharedPreferences);
    }

    private void setListener(){
        first.setEditListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("flag", "1");
                intent.putExtras(bundle);
                intent.setClass(getActivity(), SettingActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "over", Toast.LENGTH_SHORT).show();
            }
        });

        second.setEditListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("flag", "2");
                intent.putExtras(bundle);
                intent.setClass(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });

        third.setEditListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("flag", "3");
                intent.putExtras(bundle);
                intent.setClass(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });

        pet_toolbarview.setrelativeclick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getActivity(), LoginActivity.class);
                //startActivity(intent);
            }
        });

        pet_toolbarview.setmoreclick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PopupMenu popup = new PopupMenu(getActivity(), pet_toolbarview.toolbar_more);
//                popup.getMenuInflater().inflate(R.menu.popup_item_other, popup.getMenu());
//
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        switch (item.getItemId()){
//                            case R.id.about:
//                                Intent aboutIntent = new Intent(getActivity(), AboutAppActivity.class);
//                                startActivity(aboutIntent);
//                                break;
//                            case R.id.login:
//                                Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
//                                loginIntent.putExtra("relogin", true);
//                                startActivity(loginIntent);
//                                break;
//                        }
//                        return true;
//                    }
//                });
//                popup.show();
            }
        });
    }

    //要获取Activity中的资源，就必须等Acitivity创建完成以后，所以必须放在onActivityCreated()回调函数中
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        first.setSwitchListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    second.setCheck(false);
                    third.setCheck(false);
                    if (!sharedPreferences.getBoolean("isFirstOn", false)) {
                        editor.putBoolean("isFirstOn", true);
                        editor.putBoolean("isSecondOn", false);
                        editor.putBoolean("isThirdOn", false);
                        editor.commit();
                        //Intent intent = new Intent(getActivity(), FloatWindowService.class);
                        //getActivity().startService(intent);
                        //getActivity().finish();
                        Intent intent = new Intent(getActivity(), BackService.class);
                        getActivity().startService(intent);
                    }
                }
                else {
                    editor.putBoolean("isFirstOn", false);
                    editor.commit();
                    //Intent intent = new Intent(getActivity(), FloatWindowService.class);
                    //getActivity().stopService(intent);
                    Intent intent = new Intent(getActivity(), BackService.class);
                    getActivity().stopService(intent);
                }
            }
        });

        second.setSwitchListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    first.setCheck(false);
                    third.setCheck(false);
                    if (!sharedPreferences.getBoolean("isSecondOn", false)) {
                        editor.putBoolean("isFirstOn", false);
                        editor.putBoolean("isSecondOn", true);
                        editor.putBoolean("isThirdOn", false);
                        editor.commit();
                        //Intent intent = new Intent(getActivity(), FloatWindowService.class);
                        //getActivity().startService(intent);
                        //getActivity().finish();
                    }
                }
                else{
                    editor.putBoolean("isSecondOn",false);
                    editor.commit();
                    //Intent intent = new Intent(getActivity(), FloatWindowService.class);
                    //getActivity().stopService(intent);
                }
            }
        });

        third.setSwitchListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    first.setCheck(false);
                    second.setCheck(false);
                    if (!sharedPreferences.getBoolean("isSecondOn", false)) {
                        editor.putBoolean("isFirstOn", false);
                        editor.putBoolean("isSecondOn", false);
                        editor.putBoolean("isThirdOn", true);
                        editor.commit();
                        //Intent intent = new Intent(getActivity(), FloatWindowService.class);
                        //getActivity().startService(intent);
                        //getActivity().finish();
                    }
                }
                else{
                    editor.putBoolean("isSecondOn",false);
                    editor.commit();
                    //Intent intent = new Intent(getActivity(), FloatWindowService.class);
                    //getActivity().stopService(intent);
                }
            }
        });
    }
}
