package com.example.activity.hospitalactivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.pojo.hospitalparam.HospitalBannerListParam;
import com.example.pojo.hospitalparam.HospitalDetailParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kkk
 */
public class HospitalDetailActivity extends BaseActivity {


    private static final String TAG = "HospitalDetailActivity";
    private TextView hospitalName;
    private Banner<HospitalBannerListParam.DataDTO, BannerImageAdapter<HospitalBannerListParam.DataDTO>> hospitalBanner;
    private TextView describe;
    private Button registerBtn;
    private Integer hospitalId;
    private Gson gson = new Gson();
    private ImageView finishBtn;
    private Toolbar toolbar;

    @Override
    protected int initLayout() {
        return R.layout.activity_hospital_detail;
    }

    @Override
    protected void initView() {
        hospitalName = findViewById(R.id.hospital_name);
        hospitalBanner = findViewById(R.id.hospital_banner);
        describe = findViewById(R.id.hospital_describe);
        registerBtn = findViewById(R.id.hospital_register_btn);
        finishBtn = findViewById(R.id.finish_btn);
        toolbar = findViewById(R.id.hospital_toolbar);
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
        hospitalId = (Integer) extras.get("hospitalId");

        Api.config(Constant.NetWork.HOSPITAL_DETAILS, null, this).getRestfulRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                HospitalDetailParam hospitalDetailParam = gson.fromJson(result, HospitalDetailParam.class);
                if (hospitalDetailParam.getCode() == HttpURLConnection.HTTP_OK) {
                    runOnUiThread(() -> {
                        HospitalDetailParam.DataDTO detailParamData = hospitalDetailParam.getData();
                        hospitalName.setText(detailParamData.getHospitalName());
                        describe.setText(Html.fromHtml(detailParamData.getBrief(), Html.FROM_HTML_MODE_LEGACY));
                    });
                } else {
                    showSyncToast(hospitalDetailParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        }, hospitalId);

        Map<String, Object> param = new HashMap<>();
        param.put("hospitalId", hospitalId);
        Api.config(Constant.NetWork.HOSPITAL_BANNER, param, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                Log.d(TAG, "success: " + result);
                HospitalBannerListParam hospitalBannerListParam = gson.fromJson(result, HospitalBannerListParam.class);
                if (hospitalBannerListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    runOnUiThread(() -> {
                        setHospitalBanner(hospitalBannerListParam.getData());
                    });
                } else {
                    showSyncToast(hospitalBannerListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });

        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        });

        toolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        registerBtn.setOnClickListener(view -> {
            jumpPageToIntent(new Intent(this, HospitalCardActivity.class).putExtra("hospitalId", hospitalId));
        });
    }

    private void setHospitalBanner(List<HospitalBannerListParam.DataDTO> dataDTOS) {
        hospitalBanner.setAdapter(new BannerImageAdapter<HospitalBannerListParam.DataDTO>(dataDTOS) {
            @Override
            public void onBindView(BannerImageHolder holder, HospitalBannerListParam.DataDTO data, int position, int size) {
                holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                Glide.with(holder.itemView.getContext())
                        .load(Constant.BASE_API + data.getImgUrl())
                        .transform(new RoundedCorners(24))
                        .into(holder.imageView);
            }
        }).addBannerLifecycleObserver(this)
                .setIndicator(new CircleIndicator(this));
    }
}