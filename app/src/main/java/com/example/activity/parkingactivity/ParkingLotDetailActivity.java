package com.example.activity.parkingactivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.pojo.parkingparam.ParkingLotDetailParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;

import java.net.HttpURLConnection;

/**
 * @author kkk
 */
public class ParkingLotDetailActivity extends BaseActivity {

    private TextView parkingAddress, parkingName, parkingDistance, parkingOpen, parkingPriceCaps, parkingVacancy, parkingRates, parkingTatolName;
    private ImageView finishBtn;
    private ImageView parkingImage;
    private Toolbar parkingToolbar;

    @Override
    protected int initLayout() {
        return R.layout.activity_parking_lot_detail;
    }

    @Override
    protected void initView() {
        parkingAddress = findViewById(R.id.parking_lot_detail_address);
        parkingName = findViewById(R.id.parking_lot_detail_name);
        parkingDistance = findViewById(R.id.parking_lot_detail_distance);
        parkingImage = findViewById(R.id.parking_detail_image);
        parkingOpen = findViewById(R.id.parking_lot_detail_open);
        parkingPriceCaps = findViewById(R.id.parking_lot_detail_price_caps);
        parkingVacancy = findViewById(R.id.parking_lot_detail_vacancy);
        parkingRates = findViewById(R.id.parking_lot_detail_rates);
        parkingTatolName = findViewById(R.id.parking_detail_name);
        finishBtn = findViewById(R.id.finish_btn);
        parkingToolbar = findViewById(R.id.parking_toolbar);
    }

    @Override
    protected void initData() {
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });
        parkingToolbar.setNavigationOnClickListener(view -> {
            finish();
        });
        Bundle extras = getIntent().getExtras();
        Object parkingId = extras.get("parkingId");
        String name = (String) extras.get("parkingName");
        parkingTatolName.setText(name);

        Api.config(Constant.NetWork.PARK_LOT_DETAIL, null, this).getRestfulRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                ParkingLotDetailParam lotDetailParam = gson.fromJson(result, ParkingLotDetailParam.class);
                if (lotDetailParam.getCode() == HttpURLConnection.HTTP_OK) {
                    ParkingLotDetailParam.DataDTO detailParamData = lotDetailParam.getData();
                    runOnUiThread(() -> {
                        parkingAddress.setText(detailParamData.getAddress());
                        parkingDistance.setText(detailParamData.getDistance() + "米");
                        parkingName.setText(detailParamData.getParkName());
                        parkingOpen.setText("Y".equals(detailParamData.getOpen()) ? "是" : "否");
                        parkingVacancy.setText(detailParamData.getVacancy());
                        parkingRates.setText(detailParamData.getRates() + " 元 / 小时");
                        parkingPriceCaps.setText(detailParamData.getPriceCaps() + "元");
                        Glide.with(ParkingLotDetailActivity.this)
                                .load(Constant.BASE_API + detailParamData.getImgUrl())
                                .transform(new RoundedCorners(16))
                                .error(Constant.BASE_API + "/prod-api/profile/upload/image/2021/11/22/b25dcd6c-2628-479d-a3f2-f32bf2cb0c74.jpg")
                                .into(parkingImage);
                    });
                } else {
                    showSyncToast(lotDetailParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        }, parkingId);
    }
}