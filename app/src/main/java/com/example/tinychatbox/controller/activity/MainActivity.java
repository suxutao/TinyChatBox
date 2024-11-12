package com.example.tinychatbox.controller.activity;

import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.tinychatbox.R;
import com.example.tinychatbox.controller.fragment.ChatFragment;
import com.example.tinychatbox.controller.fragment.ContactListFragment;
import com.example.tinychatbox.controller.fragment.SettingFragment;

public class MainActivity extends FragmentActivity {


    private RadioGroup main_group;
    private ChatFragment chatFragment;
    private ContactListFragment contactListFragment;
    private SettingFragment settingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initView();
        initData();
        initListener();
    }

    private void initListener() {
        main_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //切换列表
                Fragment fragment=null;
                switch (checkedId){
                    case R.id.main_chat:
                        fragment=chatFragment;
                        break;
                    case R.id.main_contactor:
                        fragment=contactListFragment;
                        break;
                    case R.id.main_settings:
                        fragment=settingFragment;
                        break;
                }
                switchFragment(fragment);
            }
        });
        main_group.check(R.id.main_chat);
    }

    private void switchFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_group,fragment).commit();
    }

    private void initData() {
        chatFragment = new ChatFragment();
        contactListFragment = new ContactListFragment();
        settingFragment = new SettingFragment();
    }

    private void initView() {
        main_group = findViewById(R.id.main_group);
    }
}