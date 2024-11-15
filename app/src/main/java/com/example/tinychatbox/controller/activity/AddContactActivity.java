package com.example.tinychatbox.controller.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tinychatbox.R;
import com.example.tinychatbox.model.Model;
import com.example.tinychatbox.model.bean.UserInfo;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

public class AddContactActivity extends AppCompatActivity {

    private TextView add_chazhao;
    private TextView add_name;
    private RelativeLayout add_rlayout;
    private EditText add_edit;
    private Button add_button;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_contact);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initView();
        initListener();
    }

    private void initListener() {
        //查找按钮
        add_chazhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                find();
            }
        });
        //添加按钮
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
    }

    private void find(){

        String name=add_edit.getText().toString();

        if (TextUtils.isEmpty(name)){
            Toast.makeText(AddContactActivity.this,"输入用户名不能为空",Toast.LENGTH_SHORT).show();
            return;
        }

        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                userInfo = new UserInfo(name);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        add_rlayout.setVisibility(View.VISIBLE);
                        add_name.setText(userInfo.getName());
                    }
                });
            }
        });
    }

    private void add() {

        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().contactManager().addContact(userInfo.getName(),"添加好友");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AddContactActivity.this,"添加好友成功",Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (HyphenateException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AddContactActivity.this,"添加好友失败"+e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    });
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void initView() {
        add_chazhao=findViewById(R.id.add_chazhao);
        add_name=findViewById(R.id.add_name);
        add_rlayout=findViewById(R.id.add_rlayout);
        add_button=findViewById(R.id.add_button);
        add_edit=findViewById(R.id.add_edit);
    }
}