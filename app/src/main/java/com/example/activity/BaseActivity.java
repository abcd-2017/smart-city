package com.example.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.util.Constant;

/**
 * @author kkk
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayout());
        initView();
        initData();
    }

    protected abstract int initLayout();

    protected abstract void initView();

    protected abstract void initData();

    public void setStringToSP(String key, String value) {
        SharedPreferences preferences = getSharedPreferences(Constant.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public String getStringToSP(String key) {
        SharedPreferences sharedPreferences = getSharedPreferences(Constant.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }

    public void showSyncToast(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        runOnUiThread(() -> {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        });
    }

    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void jumpPage(Class clazz) {
        startActivity(new Intent(this, clazz));
    }

    public void jumpPageToIntent(Intent intent) {
        startActivity(intent);
    }

    public void jumpPageFlag(Class clazz, int flag) {
        Intent intent = new Intent(this, clazz);
        intent.setFlags(flag);
        startActivity(intent);
    }
}
