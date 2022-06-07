package com.example.activity.trafficactivity;

import android.content.Intent;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.adapter.trafficadapter.TrafficCheckApplyListAdapter;
import com.example.pojo.trafficparam.TrafficCheckApplyParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.util.List;

/**
 * @author kkk
 */
public class TrafficCheckApplyActivity extends BaseActivity {

    private TrafficCheckApplyListAdapter trafficCheckApplyListAdapter;
    private ImageView finishBtn;
    private Toolbar trafficToolbar;
    private RecyclerView applyRecycler;

    @Override
    protected int initLayout() {
        return R.layout.activity_traffic_check_apply;
    }

    @Override
    protected void initView() {
        finishBtn = findViewById(R.id.finish_btn);
        trafficToolbar = findViewById(R.id.traffic_toolbar);
        applyRecycler = findViewById(R.id.traffic_check_apply_recycler);
        trafficCheckApplyListAdapter = new TrafficCheckApplyListAdapter();
    }

    @Override
    protected void initData() {
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });
        trafficToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        applyRecycler.setAdapter(trafficCheckApplyListAdapter);
        applyRecycler.setLayoutManager(new LinearLayoutManager(this));
        applyRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        Api.config(Constant.NetWork.TRAFFIC_APPLY_LIST, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                TrafficCheckApplyParam trafficCheckApplyParam = gson.fromJson(result, TrafficCheckApplyParam.class);
                if (trafficCheckApplyParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<TrafficCheckApplyParam.RowsDTO> trafficCheckApplyParamRows = trafficCheckApplyParam.getRows();
                    runOnUiThread(() -> {
                        trafficCheckApplyListAdapter.setData(trafficCheckApplyParamRows);
                    });
                } else {
                    showSyncToast(trafficCheckApplyParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
    }
}