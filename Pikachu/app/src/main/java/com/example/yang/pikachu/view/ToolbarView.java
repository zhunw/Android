package com.example.yang.pikachu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yang.pikachu.R;

/**
 * Created by Yang on 2018/6/30.
 */

public class ToolbarView extends RelativeLayout {
    public TextView toolbar_text;
    public RelativeLayout toolbar_more;
    public RelativeLayout toolbar_relative;

    public ToolbarView(Context context,AttributeSet attributeSet) {
        super(context, attributeSet);
        LayoutInflater.from(context).inflate(R.layout.toolbar, this, true);
        this.toolbar_more = (RelativeLayout)findViewById(R.id.toolbar_more);
        this.toolbar_text = (TextView)findViewById(R.id.toolbar_text);
        this.toolbar_relative = (RelativeLayout)findViewById(R.id.toolbar_relative);
    }

    public void setToolbar_text(String text)
    {
        this.toolbar_text.setText(text);
    }

    public void settoolbar_more_Visibility(Integer s)
    {
        this.toolbar_more.setVisibility(s);
    }

    public void settoolbar_relative_Visibility(Integer s)
    {
        this.toolbar_relative.setVisibility(s);
    }

    public void setmoreclick(OnClickListener more)
    {
        this.toolbar_more.setOnClickListener(more);
    }

    public void setrelativeclick(OnClickListener relative)
    {
        this.toolbar_relative.setOnClickListener(relative);
    }
}