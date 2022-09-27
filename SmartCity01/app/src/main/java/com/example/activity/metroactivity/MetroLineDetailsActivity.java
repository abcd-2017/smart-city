package com.example.activity.metroactivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.adapter.metroadapter.MetroDetailsListAdapter;
import com.example.pojo.metroparam.MetroDetailsParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;

import java.net.HttpURLConnection;

/**
 * @author kkk
 */
public class MetroLineDetailsActivity extends BaseActivity {

    private static final String TAG = "MetroLineDetailsActivity";
    private Toolbar metroToolbar;
    private ImageView finishBtn;
    private RecyclerView metroDetailsRecycler;
    private TextView lineName, metroStart, metroEnd, currentMetro, metroTime, metroStep, startTime, endTime;
    private String currMetroName;
    private Integer metroDetailsId;
    private MetroDetailsListAdapter metroDetailsListAdapter;

    @Override
    protected int initLayout() {
        return R.layout.activity_metro_line_details;
    }

    @Override
    protected void initView() {
        metroToolbar = findViewById(R.id.metro_toolbar);
        finishBtn = findViewById(R.id.finish_btn);
        lineName = findViewById(R.id.metro_line_name);
        metroStart = findViewById(R.id.metro_start);
        metroEnd = findViewById(R.id.metro_end);
        currentMetro = findViewById(R.id.current_metro);
        metroTime = findViewById(R.id.metro_details_time);
        metroStep = findViewById(R.id.metro_details_step);
        metroDetailsRecycler = findViewById(R.id.metro_details_recycler);
        startTime = findViewById(R.id.start_time);
        endTime = findViewById(R.id.end_time);
        metroDetailsListAdapter = new MetroDetailsListAdapter();
    }

    @Override
    protected void initData() {
        Bundle extras = getIntent().getExtras();
        metroDetailsId = (Integer) extras.get("metroId");
        currMetroName = (String) extras.get("currMetro");
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });
        metroToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        metroDetailsRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        metroDetailsRecycler.setAdapter(metroDetailsListAdapter);

        Api.config(Constant.NetWork.METRO_LINE, null, this).getRestfulRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                MetroDetailsParam metroDetailsParam = gson.fromJson(result, MetroDetailsParam.class);
                Log.d(TAG, "success: result => " + metroDetailsParam);
                if (metroDetailsParam.getCode() == HttpURLConnection.HTTP_OK) {
                    MetroDetailsParam.DataDTO detailsParamData = metroDetailsParam.getData();
                    runOnUiThread(() -> {
                        lineName.setText(detailsParamData.getName());
                        metroStart.setText(detailsParamData.getFirst());
                        metroEnd.setText(detailsParamData.getEnd());
                        currentMetro.setText(detailsParamData.getRunStationsName());
                        startTime.setText(detailsParamData.getStartTime());
                        endTime.setText(detailsParamData.getEndTime());
                        currentMetro.setText(currMetroName);
                        metroDetailsListAdapter.setData(detailsParamData.getMetroStepList(), detailsParamData.getRunStationsName());
                    });

                } else {
                    showSyncToast(metroDetailsParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        }, metroDetailsId);
    }
}