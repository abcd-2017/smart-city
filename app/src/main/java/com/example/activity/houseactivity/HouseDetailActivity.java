package com.example.activity.houseactivity;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.adapter.houseadapter.HouseDetailParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;

import java.net.HttpURLConnection;

/**
 * @author kkk
 */
public class HouseDetailActivity extends BaseActivity {

    private Toolbar houseToolbar;
    private ImageView finishBtn;
    private TextView detailAddress;
    private TextView detailArea;
    private TextView detailPhone;
    private TextView detailSellingPrice;
    private TextView detailContent;
    private ImageView detailImage;
    private TextView detailType;
    private TextView detailTitle;

    @Override
    protected int initLayout() {
        return R.layout.activity_house_detail;
    }

    @Override
    protected void initView() {
        houseToolbar = findViewById(R.id.house_toolbar);
        finishBtn = findViewById(R.id.finish_btn);
        detailAddress = findViewById(R.id.house_detail_address);
        detailArea = findViewById(R.id.house_detail_area);
        detailPhone = findViewById(R.id.house_detail_phone);
        detailSellingPrice = findViewById(R.id.house_detail_selling_price);
        detailContent = findViewById(R.id.house_detail_content);
        detailImage = findViewById(R.id.house_detail_image);
        detailType = findViewById(R.id.house_detail_type);
        detailTitle = findViewById(R.id.house_detail_title);
    }

    @Override
    protected void initData() {
        houseToolbar.setNavigationOnClickListener(view -> {
            finish();
        });
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });

        Integer houseId = (Integer) getIntent().getExtras().get("houseId");

        Api.config(Constant.NetWork.HOUSE_LIST_DETAIL, null, this).getRestfulRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                HouseDetailParam rowsDTO = gson.fromJson(result, HouseDetailParam.class);
                if (rowsDTO.getCode() == HttpURLConnection.HTTP_OK) {
                    runOnUiThread(() -> {
                        setData(rowsDTO.getData());
                    });
                } else {
                    showSyncToast(rowsDTO.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        }, houseId);
    }

    public void setData(HouseDetailParam.DataDTO rowsDTO) {
        detailAddress.setText(rowsDTO.getAddress());
        detailArea.setText(rowsDTO.getAreaSize() + "㎡");
        detailTitle.setText(rowsDTO.getSourceName());
        detailContent.setText(rowsDTO.getDescription());
        detailPhone.setText(rowsDTO.getTel());
        detailSellingPrice.setText(rowsDTO.getPrice());
        detailType.setText(rowsDTO.getHouseType());

        Glide.with(this)
                .load(Constant.BASE_API + rowsDTO.getPic())
                .transform(new RoundedCorners(12))
                .into(detailImage);
    }
}