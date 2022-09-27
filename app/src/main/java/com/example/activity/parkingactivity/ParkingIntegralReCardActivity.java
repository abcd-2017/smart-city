package com.example.activity.parkingactivity;

import android.content.Intent;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.adapter.parkingadapter.ParkingIntegralReCardListAdapter;
import com.example.pojo.parkingparam.ParkingIntegralReCardListParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.util.List;

/**
 * @author kkk
 */
public class ParkingIntegralReCardActivity extends BaseActivity {

    private ParkingIntegralReCardListAdapter reCardListAdapter;
    private Toolbar parkingToolbar;
    private ImageView finishBtn;
    private RecyclerView reCardRecycler;

    @Override
    protected int initLayout() {
        return R.layout.activity_parking_integral_re_card;
    }

    @Override
    protected void initView() {
        parkingToolbar = findViewById(R.id.parking_toolbar);
        finishBtn = findViewById(R.id.finish_btn);
        reCardRecycler = findViewById(R.id.parking_integral_recard_recycler);
        reCardListAdapter = new ParkingIntegralReCardListAdapter();
    }

    @Override
    protected void initData() {
        parkingToolbar.setNavigationOnClickListener(view -> {
            finish();
        });
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });

        reCardRecycler.setLayoutManager(new LinearLayoutManager(this));
        reCardRecycler.setAdapter(reCardListAdapter);

        Api.config(Constant.NetWork.PARK_SCORE_LIST, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                ParkingIntegralReCardListParam reCardListParam = gson.fromJson(result, ParkingIntegralReCardListParam.class);
                if (reCardListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<ParkingIntegralReCardListParam.RowsDTO> listParamRows = reCardListParam.getRows();
                    runOnUiThread(() -> {
                        reCardListAdapter.setData(listParamRows);
                    });
                } else {
                    showSyncToast(reCardListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
    }
}