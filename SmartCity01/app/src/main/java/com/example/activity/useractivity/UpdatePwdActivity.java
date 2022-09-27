package com.example.activity.useractivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
public class UpdatePwdActivity extends BaseActivity {

    private static final String TAG = "UpdatePwdActivity";
    private Button updateBtn;
    private EditText oldPwd;
    private EditText newPwd;
    private EditText rePwd;
    private AlertDialog alertDialog;
    private Toolbar toolbar;

    @Override
    protected int initLayout() {
        return R.layout.activity_update_pwd;
    }

    @Override
    protected void initView() {
        updateBtn = findViewById(R.id.update_pwd_btn);
        oldPwd = findViewById(R.id.update_pwd_old_password);
        newPwd = findViewById(R.id.update_pwd_new_password);
        rePwd = findViewById(R.id.update_pwd_re_new_password);
        toolbar = findViewById(R.id.update_pwd_toolbar);
    }

    @Override
    protected void initData() {
        alertDialog = new AlertDialog.Builder(UpdatePwdActivity.this)
                .setMessage("密码已修改,请用新密码重新登陆")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        jumpPageFlag(LoginActivity.class, Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    }
                }).create();
        updateBtn.setOnClickListener(view -> {
            String oldPwd = this.oldPwd.getText().toString(), newPwd = this.newPwd.getText().toString(), rePwd = this.rePwd.getText().toString();
            if (TextUtils.isEmpty(oldPwd)) {
                showSyncToast("请输入旧密码");
                return;
            }
            if (TextUtils.isEmpty(newPwd)) {
                showSyncToast("请输入新密码");
                return;
            }
            if (TextUtils.isEmpty(rePwd)) {
                showSyncToast("请输入重复密码");
                return;
            }
            if (!newPwd.equals(rePwd)) {
                showSyncToast("两次输入的密码不一致");
                return;
            }
            Map<String, Object> param = new HashMap<>();
            param.put("newPassword", newPwd);
            param.put("oldPassword", oldPwd);
            Api.config(Constant.NetWork.RESET_PWD, param, this).putRequest(new RequestCallback() {
                @Override
                public void success(String result) {
                    Gson gson = new Gson();
                    BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                    Log.d(TAG, "success: baseParam => " + baseParam);
                    if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                        setStringToSP("token", "");
                        runOnUiThread(() -> {
                            alertDialog.show();
                        });
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

        toolbar.setNavigationOnClickListener(view -> {
            showSyncToast("修改成功");
            finish();
        });
    }
}