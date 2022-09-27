package com.example.activity.useractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.pojo.LoginParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Response;

/**
 * @author kkk
 */
public class LoginActivity extends BaseActivity {

    private EditText password, username;
    private Button loginBtn;
    private TextView goToLRegister;

    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
        loginBtn = findViewById(R.id.user_login);
        goToLRegister = findViewById(R.id.go_to_register);
    }

    @Override
    protected void initData() {
        loginBtn.setOnClickListener(view -> {
            String uname = this.username.getText().toString();
            String pwd = this.password.getText().toString();
            if (TextUtils.isEmpty(uname)) {
                showSyncToast("用户名不能为空");
                return;
            }
            if (TextUtils.isEmpty(pwd)) {
                showSyncToast("密码不能为空");
                return;
            }
            Map<String, Object> map = new HashMap<>();
            map.put("username", uname);
            map.put("password", pwd);
            Api.config(Constant.NetWork.LOGIN, map, this).postRequest(new RequestCallback() {
                @Override
                public void success(String result) {
                    Gson gson = new Gson();
                    LoginParam loginParam = gson.fromJson(result, LoginParam.class);
                    if (loginParam.getCode() == HttpURLConnection.HTTP_OK) {
                        setStringToSP("token", loginParam.getToken());
                        setResult(RESULT_OK, new Intent().putExtra("loginResult", true));
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    } else {
                        showSyncToast("登陆失败 " + loginParam.getMsg());
                    }
                }


                @Override
                public void failure(Exception e) {
                    showSyncToast("网络异常!");
                }
            });
        });

        goToLRegister.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        });
    }
}