package com.example.yang.pikachu.fragment;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yang.pikachu.R;
import com.example.yang.pikachu.view.ToolbarView;

/**
 * Created by Yang on 2018/7/1.
 */

public class SettingFragment extends Fragment {
    ToolbarView setting_toolbarivew;
    Switch Switch_show,Switch_always,Switch_on,Switch_set,Switch_time;
    SharedPreferences preferences ;
    SharedPreferences.Editor editor;
    Boolean show,always,on,set,time;
    RelativeLayout Re6,Re7;
    private RelativeLayout Re1;
    TextView Re6_state;

    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    //private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;

    // Name of the connected device
    private String mConnectedDeviceName = null;

    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    // Member object for the chat services
    //private BluetoothChatService mChatService = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_setting,container, false);
        initview(rootView);
        Switch_show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean("show", b);
                editor.commit();
            }
        });
        Switch_always.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean("always", b);
                editor.commit();
            }
        });
        Switch_on.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean("on", b);
                editor.commit();
            }
        });
        Switch_set.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean("set", b);
                editor.commit();
            }
        });
        Switch_time.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean("time", b);
                editor.commit();
            }
        });



        return rootView;
    }
    public void initview(View rootView)
    {
        setting_toolbarivew = (ToolbarView)rootView.findViewById(R.id.third_toolbarview);
        setting_toolbarivew.setToolbar_text("桌面宠物");
        setting_toolbarivew.settoolbar_more_Visibility(View.GONE);
        setting_toolbarivew.settoolbar_relative_Visibility(View.GONE);

        Switch_show = (Switch)rootView.findViewById(R.id.Switch_show);
        Switch_always = (Switch)rootView.findViewById(R.id.Switch_always);
        Switch_on = (Switch)rootView.findViewById(R.id.Switch_on);
        Switch_set = (Switch)rootView.findViewById(R.id.Switch_set);
        Switch_time = (Switch)rootView.findViewById(R.id.Switch_time);

        Re1 = (RelativeLayout)rootView.findViewById(R.id.Re1);
        Re6 = (RelativeLayout)rootView.findViewById(R.id.Re6);
        Re7 = (RelativeLayout)rootView.findViewById(R.id.Re7);
        Re6_state = (TextView)rootView.findViewById(R.id.Re6_state);

        preferences = getActivity().getSharedPreferences("pet", Context.MODE_PRIVATE);
        editor = preferences.edit();

        show = preferences.getBoolean("show",true);
        always = preferences.getBoolean("always",true);
        on = preferences.getBoolean("on",false);
        set = preferences.getBoolean("set",true);
        time = preferences.getBoolean("time",false);

        Switch_show.setChecked(show);
        Switch_always.setChecked(always);
        Switch_on.setChecked(on);
        Switch_set.setChecked(set);
        Switch_time.setChecked(time);

    }
    private void setupChat() {

        // Initialize the BluetoothChatService to perform bluetooth connections
        //mChatService = new BluetoothChatService(getActivity(), mHandler);
    }
    // The Handler that gets information back from the BluetoothChatService
//    private final Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case MESSAGE_STATE_CHANGE:
//                    switch (msg.arg1) {
//                        case BluetoothChatService.STATE_CONNECTED:
//                            Re6_state.setText(getString(R.string.title_connected_to, mConnectedDeviceName));
//                            Petpair();
//                            break;
//                        case BluetoothChatService.STATE_CONNECTING:
//                            Re6_state.setText(R.string.title_connecting);
//                            break;
//                        case BluetoothChatService.STATE_LISTEN:
//                        case BluetoothChatService.STATE_NONE:
//                            Re6_state.setText(R.string.title_not_connected);
//                            break;
//                    }
//                    break;
//                case MESSAGE_DEVICE_NAME:
//                    // save the connected device's name
//                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
//                    Toast.makeText(getActivity().getApplicationContext(), "Connected to "
//                            + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
//                    break;
//                case MESSAGE_TOAST:
//                    Toast.makeText(getActivity().getApplicationContext(), msg.getData().getString(TOAST),
//                            Toast.LENGTH_SHORT).show();
//                    break;
//            }
//        }
//    };

    //用于宠物配对
    private void Petpair()
    {
        Toast toast = Toast.makeText(getActivity().getApplicationContext(), "宠物配对成功！", Toast.LENGTH_SHORT);
        toast.show();
        editor.putBoolean("isSecondUnlock",true);
        editor.commit();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE_SECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, true);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    setupChat();
                } else {
                    // User did not enable Bluetooth or an error occurred
                    Toast.makeText(getActivity(), R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void connectDevice(Intent data, boolean secure) {
//        // Get the device MAC address
//        String address = data.getExtras()
//                .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
//        // Get the BluetoothDevice object
//        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
//        // Attempt to connect to the device
//        mChatService.connect(device, secure);
    }
}
