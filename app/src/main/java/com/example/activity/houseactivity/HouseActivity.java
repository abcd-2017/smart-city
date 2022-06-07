package com.example.activity.houseactivity;

import android.content.Intent;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.adapter.houseadapter.HouseListAdapter;
import com.example.pojo.houseparam.HouseListParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.util.List;

public class HouseActivity extends BaseActivity {

    private ImageView finishBtn;
    private RecyclerView houseListRecycler;
    private HouseListAdapter houseListAdapter;

    @Override
    protected int initLayout() {
        return R.layout.activity_house;
    }

    @Override
    protected void initView() {
        finishBtn = findViewById(R.id.finish_btn);
        houseListRecycler = findViewById(R.id.house_list_recycler);
        houseListAdapter = new HouseListAdapter();
    }

    @Override
    protected void initData() {
        finishBtn.setOnClickListener(view -> {
            finish();
        });

        houseListRecycler.setLayoutManager(new LinearLayoutManager(this));
        houseListRecycler.setAdapter(houseListAdapter);

        houseListAdapter.setHouseItemClickListener(id -> {
            jumpPageToIntent(new Intent(this, HouseDetailActivity.class).putExtra("houseId", id));
        });

        Api.config(Constant.NetWork.HOUSE_LIST, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                HouseListParam houseListParam = gson.fromJson(result, HouseListParam.class);
                if (houseListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<HouseListParam.RowsDTO> listParamRows = houseListParam.getRows();
                    runOnUiThread(() -> {
                        houseListAdapter.setData(listParamRows);
                    });
                } else {
                    showSyncToast(houseListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
    }
}