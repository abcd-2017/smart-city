package com.example.activity.trafficactivity;

import android.widget.ImageView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.R;
import com.example.activity.BaseActivity;
import com.example.pojo.trafficparam.TrafficDriverLicenseParam;
import com.example.pojo.trafficparam.TrafficRotationListParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.BaseIndicator;

import java.net.HttpURLConnection;
import java.util.List;

/**
 * @author kkk
 */
public class TrafficActivity extends BaseActivity {

    private ImageView finishBtn;
    private Banner<TrafficRotationListParam.RowsDTO, BannerImageAdapter<TrafficRotationListParam.RowsDTO>> trafficRotation;
    private CardView illegalHandling;
    private CardView myDriverLicense;
    private CardView vehicleManagement;
    private Gson gson = new Gson();

    @Override
    protected int initLayout() {
        return R.layout.activity_traffic;
    }

    @Override
    protected void initView() {
        finishBtn = findViewById(R.id.finish_btn);
        trafficRotation = findViewById(R.id.traffic_rotation);
        illegalHandling = findViewById(R.id.traffic_Illegal_handling);
        myDriverLicense = findViewById(R.id.traffic_my_driver_license);
        vehicleManagement = findViewById(R.id.traffic_vehicle_management);
    }

    @Override
    protected void initData() {
        finishBtn.setOnClickListener(view -> {
            finish();
        });

        illegalHandling.setOnClickListener(view -> {
            showSyncToast("暂未开通");
        });
        myDriverLicense.setOnClickListener(view -> {
            jumpPage(TrafficDriverLicenseActivity.class);
        });
        vehicleManagement.setOnClickListener(view -> {
            jumpPage(TrafficVehicleManagementActivity.class);
        });

        Api.config(Constant.NetWork.TRAFFIC_ROTATION_LIST, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                TrafficRotationListParam rotationListParam = gson.fromJson(result, TrafficRotationListParam.class);
                if (rotationListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<TrafficRotationListParam.RowsDTO> rotationListParamRows = rotationListParam.getRows();
                    runOnUiThread(() -> {
                        setRotationImage(rotationListParamRows);
                    });
                } else {
                    showSyncToast(rotationListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });

        hasDriverLicense();
    }

    private void setRotationImage(List<TrafficRotationListParam.RowsDTO> rotationListParamRows) {
        trafficRotation.setAdapter(new BannerImageAdapter<TrafficRotationListParam.RowsDTO>(rotationListParamRows) {
            @Override
            public void onBindView(BannerImageHolder holder, TrafficRotationListParam.RowsDTO data, int position, int size) {
                holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                Glide.with(holder.itemView.getContext())
                        .load(Constant.BASE_API + data.getAdvImg())
                        .transform(new RoundedCorners(14))
                        .into(holder.imageView);
            }
        }).setIndicator(new BaseIndicator(this))
                .addBannerLifecycleObserver(this);
    }

    private void hasDriverLicense() {
        Api.config(Constant.NetWork.TRAFFIC_USER_LICENSE, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                TrafficDriverLicenseParam driverLicenseParam = gson.fromJson(result, TrafficDriverLicenseParam.class);
                if (driverLicenseParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<TrafficDriverLicenseParam.DataDTO> licenseParamData = driverLicenseParam.getData();
                    setStringToSP("traffic_has_driver_licence", String.valueOf(licenseParamData.size() > 0));
                } else {
                    showSyncToast(driverLicenseParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
    }
}