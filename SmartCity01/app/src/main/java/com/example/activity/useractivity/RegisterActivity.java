package com.example.activity.useractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.pojo.BaseParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kkk
 */
public class RegisterActivity extends BaseActivity {
    private static final String TAG = "RegisterActivity";
    private EditText username, phone, password;
    private RadioGroup radioGroup;
    private Button registerBtn;
    private RadioButton manRadio, womanRadio;
    private String selectSex = "0";
    private TextView goToLogin;

    @Override
    protected int initLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        username = findViewById(R.id.register_username);
        password = findViewById(R.id.register_password);
        phone = findViewById(R.id.register_phone);
        radioGroup = findViewById(R.id.register_sex);
        registerBtn = findViewById(R.id.user_register);
        manRadio = findViewById(R.id.user_sex_man);
        womanRadio = findViewById(R.id.user_sex_woman);
        goToLogin = findViewById(R.id.go_to_login);
    }

    @Override
    protected void initData() {
        registerBtn.setOnClickListener(view -> {
            String uname = username.getText().toString();
            String pwd = password.getText().toString();
            String phoneNum = phone.getText().toString();
            Log.d(TAG, "initData: selectSex => " + selectSex);

            if (TextUtils.isEmpty(uname)) {
                showSyncToast("用户名不能空");
                return;
            }
            if (TextUtils.isEmpty(pwd)) {
                showSyncToast("密码不能空");
                return;
            }
            if (TextUtils.isEmpty(phoneNum)) {
                showSyncToast("手机号不能空");
                return;
            }

            Map<String, Object> map = new HashMap<>();
            map.put("userName", uname);
            map.put("password", pwd);
            map.put("phonenumber", phoneNum);
            map.put("sex", selectSex);
            Api.config(Constant.NetWork.REGISTER, map, this).postRequest(new RequestCallback() {
                @Override
                public void success(String result) {
                    Gson gson = new Gson();
                    BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                    Log.d(TAG, "success: baseParam => " + baseParam);
                    if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        showSyncToast("注册成功");
                        finish();
                    } else {
                        showSyncToast(baseParam.getMsg());
                    }
                }

                @Override
                public void failure(Exception e) {
                    showSyncToast("网络异常");
                }
            });
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == manRadio.getId()) {
                    selectSex = "0";
                } else {
                    selectSex = "1";
                }
            }
        });
        goToLogin.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(),LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        });
    }
}