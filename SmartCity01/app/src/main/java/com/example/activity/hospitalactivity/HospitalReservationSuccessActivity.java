package com.example.activity.hospitalactivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;

/**
 * @author kkk
 */
public class HospitalReservationSuccessActivity extends BaseActivity {


    private TextView category;
    private TextView reserveType;
    private TextView reserveTime;
    private Button successBtn;
    private Toolbar hospitalToolbar;
    private ImageView finishBtn;

    @Override
    protected int initLayout() {
        return R.layout.activity_hospital_reservation_success;
    }

    @Override
    protected void initView() {
        successBtn = findViewById(R.id.reservation_success_btn);
        category = findViewById(R.id.category);
        reserveType = findViewById(R.id.type);
        reserveTime = findViewById(R.id.reserveTime);
        hospitalToolbar = findViewById(R.id.hospital_toolbar);
        finishBtn = findViewById(R.id.finish_btn);
    }

    @Override
    protected void initData() {
        hospitalToolbar.setNavigationOnClickListener(view -> {
            jumpPageFlag(HospitalActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });

        Bundle extras = getIntent().getExtras();
        String time = (String) extras.get("time");
        boolean type = (boolean) extras.get("type");
        String categoryName = (String) extras.get("categoryName");

        category.setText(categoryName);
        reserveTime.setText(time);
        reserveType.setText(type ? "普通号" : "专家号");

        successBtn.setOnClickListener(view -> {
            jumpPageFlag(HospitalActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        jumpPageFlag(HospitalActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }
}