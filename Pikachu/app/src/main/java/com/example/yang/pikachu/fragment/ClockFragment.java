package com.example.yang.pikachu.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yang.pikachu.R;
import com.example.yang.pikachu.activity.AlarmActivity;
import com.example.yang.pikachu.activity.AlarmReceiver;
import com.example.yang.pikachu.database.DatabaseHelper;
import com.example.yang.pikachu.view.ToolbarView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yang on 2018/7/1.
 */

public class ClockFragment extends Fragment {
    public ToolbarView clock_toolbarview;
    List<String> alarm_task = new ArrayList<>();
    List<String> alarm_date = new ArrayList<>();
    List<String> alarm_time = new ArrayList<>();
    List<String> alarm_beizhu = new ArrayList<>();
    List<Boolean> alaram_selected = new ArrayList<>();
    List<CheckBox> c = new ArrayList<>();
    List<Integer> Deleted_NUM = new ArrayList<>();

    ListView listview;
    CheckBox list_checkbox;
    //store the alarm
    private DatabaseHelper dbhelper;
    MyAdapter myadapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_clock, container, false);
        clock_toolbarview = (ToolbarView) rootView.findViewById(R.id.clock_toolbarview);
        dbhelper = new DatabaseHelper(getActivity(), "alarm_task_manager1", null, 1);
        clock_toolbarview.setToolbar_text("闹钟设置");
        setMoreclick();

        //添加闹钟例程activity
        clock_toolbarview.setrelativeclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AlarmActivity.class);
                startActivity(intent);
            }
        });

        listview = (ListView) rootView.findViewById(R.id.activity_main_listview);

        //获取数据库消息显示信息
        String GET_ALL = "select * from alarm_task";
        Cursor cursor = dbhelper.getReadableDatabase().rawQuery(GET_ALL, null);
        while (cursor.moveToNext()) {
            alarm_task.add(cursor.getString(1));
            alarm_date.add(cursor.getString(2));
            alarm_time.add(cursor.getString(3));
            alarm_beizhu.add(cursor.getString(4));
            alaram_selected.add(false);
        }

        //listView的适配器
        myadapter = new MyAdapter();
        listview.setAdapter(myadapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (alaram_selected.get(i).toString().equals("true")) {
                    c.get(i).setSelected(false);
                    alaram_selected.set(i, false);
                } else {
                    c.get(i).setSelected(true);
                    alaram_selected.set(i, true);
                }
                myadapter.notifyDataSetChanged();
            }
        });
        return rootView;
    }

    private void setMoreclick() {
        //"+"监听
        clock_toolbarview.setmoreclick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //popup menu
                PopupMenu popup = new PopupMenu(getActivity(), clock_toolbarview.toolbar_more);
                popup.getMenuInflater().inflate(R.menu.clock_popup, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.delete://删除
                                int num = 0;
                                Deleted_NUM.clear();
                                for (int i = 0; i < alaram_selected.size(); i++) {
                                    if (alaram_selected.get(i).toString().equals("true")) {
                                        Deleted_NUM.add(i);
                                        num++;
                                    }
                                }
                                if (num == 0)
                                    Toast.makeText(getActivity(), "请选择删除对象", Toast.LENGTH_SHORT).show();
                                else {
                                    for (int j = 0; j < Deleted_NUM.size(); j++) {
                                        int n = Deleted_NUM.get(j);
                                        String task = alarm_task.get(n);
                                        String date = alarm_date.get(n);
                                        String time = alarm_time.get(n);
                                        String beizhu = alarm_beizhu.get(n);
                                        alarm_task.remove(n);
                                        alarm_date.remove(n);
                                        alarm_time.remove(n);
                                        alarm_beizhu.remove(n);
                                        alaram_selected.remove(n);
                                        c.remove(n);
                                        //根据闹钟id现在数据出里面查找出对应闹钟的PendingIntent编号,然后再在数据库里面删除对应闹钟记录
                                        // String SELECT_SQL="select count from alarm_task where _id="+n;
                                        Cursor cursor =
                                                dbhelper.getReadableDatabase()
                                                        .rawQuery(
                                                                "select _id,count from alarm_task where task=? and addition=? and date1=? and time=?"
                                                                , new String[]{task, beizhu, date, time});
                                        final int index1 = cursor.getColumnIndex("_id");
                                        final int index2 = cursor.getColumnIndex("count");
                                        int pid = 0, id = 0;
                                        if (cursor.moveToFirst()) {
                                            id = cursor.getInt(index1);
                                            pid = cursor.getInt(index2);
                                        }
                                        String DELETE_SQL = "delete from alarm_task where _id=" + id;
                                        dbhelper.getReadableDatabase().execSQL(DELETE_SQL);
                                        //无携带数据的Intent对象
                                        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
                                        //创建PendingIntent对象
                                        PendingIntent pi = PendingIntent.getBroadcast(getActivity(), pid, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                        //获取系统闹钟服务
                                        AlarmManager am = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                                        //删除指定pi对应的闹钟时间
                                        am.cancel(pi);
                                        Toast.makeText(getActivity(), "闹钟删除成功！", Toast.LENGTH_SHORT).show();
                                        cursor.close();
                                        myadapter.notifyDataSetChanged();
                                    }
                                }
                                break;

                            case R.id.edit:
                                int num1 = 0;
                                for (int i = 0; i < alaram_selected.size(); i++) {
                                    if (alaram_selected.get(i).toString().equals("true")) num1++;
                                }
                                if (num1 == 0)
                                    Toast.makeText(getActivity(), "请选择编辑对象", Toast.LENGTH_SHORT).show();
                                else if (num1 > 1)
                                    Toast.makeText(getActivity(), "一次只能编辑一个对象", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        String GET_ALL = "select * from alarm_task";
        Cursor cursor = dbhelper.getReadableDatabase().rawQuery(GET_ALL, null);
        alarm_task.clear();
        alarm_date.clear();
        alarm_time.clear();
        alarm_beizhu.clear();
        alaram_selected.clear();
        while (cursor.moveToNext()) {
            alarm_task.add(cursor.getString(1));
            alarm_date.add(cursor.getString(2));
            alarm_time.add(cursor.getString(3));
            alarm_beizhu.add(cursor.getString(4));
            alaram_selected.add(false);
        }
        myadapter = new MyAdapter();
        listview.setAdapter(myadapter);
    }

    public class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return alarm_task.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View livew = inflater.inflate(R.layout.list_clock, null);
            //get views
            TextView list_up_text = (TextView) livew.findViewById(R.id.list_up_text);
            TextView list_up_center_text = (TextView) livew.findViewById(R.id.list_up_center_text);
            TextView list_down_left_text = (TextView) livew.findViewById(R.id.list_down_left_text);
            TextView list_down_right_text = (TextView) livew.findViewById(R.id.list_down_right_text);
            list_checkbox = (CheckBox) livew.findViewById(R.id.list_checkbox);
            c.add(list_checkbox);

            //显示一个闹钟例程条目
            list_up_text.setText(alarm_task.get(position));
            list_down_left_text.setText(alarm_date.get(position));
            list_down_right_text.setText(alarm_time.get(position));
            list_up_center_text.setText(alarm_beizhu.get(position));
            list_checkbox.setChecked(alaram_selected.get(position));
            return livew;
        }
    }
}
