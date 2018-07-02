package com.example.yang.pikachu.view;

import android.content.Context;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.yang.pikachu.R;

/**
 * Created by Yang on 2018/6/30.
 */

public class PetSelect extends RelativeLayout {

    private ImageView petImg;
    private ImageView editImg ;
    private TextView name;
    private Switch mSwitch;

    //property
    private TextView pet_name;
    private TextView pet_birthday;
    private TextView pet_charac;

    public PetSelect(Context context,AttributeSet attributeSet) {
        super(context, attributeSet);

        LayoutInflater.from(context).inflate(R.layout.pet_select, this, true);

        this.petImg = (ImageView)findViewById(R.id.select_img);
        this.editImg = (ImageView)findViewById(R.id.select_edit);
        this.name = (TextView)findViewById(R.id.select_name);
        this.mSwitch = (Switch)findViewById(R.id.select_switch);

        //字体加粗
        TextPaint tp = this.name.getPaint();
        tp.setFakeBoldText(true);
    }

    public void setenabled(boolean l){
        mSwitch.setEnabled(l);
        editImg.setEnabled(l);
    }

    public void setPetImg(int resourceID){
        petImg.setImageResource(resourceID);
    }

    public void setName(String s){
        name.setText(s);
    }

    public void setEditListener(OnClickListener l){
        editImg.setOnClickListener(l);
    }

    public void setSwitchListener(CompoundButton.OnCheckedChangeListener l){
        mSwitch.setOnCheckedChangeListener(l);
    }

    public void setCheck(boolean l){
        mSwitch.setChecked(l);
    }
}

