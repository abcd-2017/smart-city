package com.example.activity.hospitalactivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.adapter.hospitaladapter.HospitalCategoryDetailListAdapter;
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
public class HospitalCategoryDetailActivity extends BaseActivity {

    private static final String TAG = "HospitalCategoryDetailActivity";
    private Toolbar toolbar;
    private ImageView finishBtn;
    private RecyclerView detailRecycler;
    private TextView categoryName, expertBtn, ordinaryBtn;
    private TextView categoryEmpty;
    private boolean isVip;
    private HospitalCategoryDetailListAdapter detailListAdapter;

    @Override
    protected int initLayout() {
        return R.layout.activity_hospital_category_detail;
    }

    @Override
    protected void initView() {
        toolbar = findViewById(R.id.hospital_toolbar);
        finishBtn = findViewById(R.id.finish_btn);
        detailRecycler = findViewById(R.id.category_detail_recycler);
        categoryName = findViewById(R.id.hospital_category_detail_name);
        ordinaryBtn = findViewById(R.id.ordinary_register);
        expertBtn = findViewById(R.id.expert_register);
        categoryEmpty = findViewById(R.id.category_empty);
        detailListAdapter = new HospitalCategoryDetailListAdapter();
    }

    @Override
    protected void initData() {
        toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });
        Bundle extras = getIntent().getExtras();
        String name = (String) extras.get("categoryName");
        isVip = (boolean) extras.get("isVip");

        categoryName.setText(name);

        showContent(false);

        ordinaryBtn.setOnClickListener(view -> {
            showContent(false);
        });

        expertBtn.setOnClickListener(view -> {
            showContent(true);
        });

        detailRecycler.setLayoutManager(new LinearLayoutManager(this));
        detailRecycler.setAdapter(detailListAdapter);
        detailListAdapter.setItemClickListener(time -> {
            Map<String, Object> param = new HashMap<>();
            param.put("categoryId", getStringToSP("categoryId"));
            param.put("money", getStringToSP("money"));
            param.put("patientName", getStringToSP("patientName"));
            param.put("reserveTime", time);
            param.put("type", isVip ? 2 : 1);
            Api.config(Constant.NetWork.HOSPITAL, param, this).postRequest(new RequestCallback() {
                @Override
                public void success(String result) {
                    Gson gson = new Gson();
                    BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                    if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                        showSyncToast("预约成功");
                        jumpPageToIntent(new
                                Intent(HospitalCategoryDetailActivity.this, HospitalReservationSuccessActivity.class)
                                .putExtra("time", time)
                                .putExtra("type", isVip)
                                .putExtra("categoryName", name)
                        );
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
    }

    private void showContent(boolean flag) {
        Log.d(TAG, "showContent: " + flag + " " + isVip);
        if (isVip == flag) {
            detailRecycler.setVisibility(View.VISIBLE);
            categoryEmpty.setVisibility(View.GONE);
        } else {
            detailRecycler.setVisibility(View.GONE);
            categoryEmpty.setVisibility(View.VISIBLE);
        }
        if (flag) {
            expertBtn.setBackgroundResource(R.color.base_color);
            expertBtn.setTextColor(Color.WHITE);
            ordinaryBtn.setBackground(null);
            ordinaryBtn.setTextColor(Color.rgb(182, 182, 182));
        } else {
            ordinaryBtn.setBackgroundResource(R.color.base_color);
            ordinaryBtn.setTextColor(Color.WHITE);
            expertBtn.setBackground(null);
            expertBtn.setTextColor(Color.rgb(182, 182, 182));
        }
    }
}