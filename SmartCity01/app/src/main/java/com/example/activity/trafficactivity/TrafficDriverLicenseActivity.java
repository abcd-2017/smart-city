package com.example.activity.trafficactivity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.pojo.User;
import com.example.pojo.trafficparam.TrafficDriverLicenseParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;

import java.net.HttpURLConnection;

/**
 * @author kkk
 */
public class TrafficDriverLicenseActivity extends BaseActivity {

    private ImageView finishBtn;
    private Toolbar trafficToolbar;
    private CardView addLicenseBtn, licenseCardBtn;
    private TextView licenseName, licenseLevel, licenseApplyDate, licenseScore, licenseLifeNo, licenseVerifyDate, licenseAuditOffice, licenseContact, licenseIdCard;
    private Gson gson = new Gson();

    @Override
    protected int initLayout() {
        return R.layout.activity_traffic_driver_license;
    }

    @Override
    protected void initView() {
        finishBtn = findViewById(R.id.finish_btn);
        trafficToolbar = findViewById(R.id.traffic_toolbar);
        addLicenseBtn = findViewById(R.id.traffic_add_license);
        licenseCardBtn = findViewById(R.id.traffic_driver_license_card);
        licenseName = findViewById(R.id.traffic_driver_license_name);
        licenseLevel = findViewById(R.id.traffic_driver_license_level);
        licenseApplyDate = findViewById(R.id.traffic_driver_license_apply_date);
        licenseScore = findViewById(R.id.traffic_driver_license_score);
        licenseLifeNo = findViewById(R.id.traffic_driver_license_file_no);
        licenseVerifyDate = findViewById(R.id.traffic_driver_license_verify_date);
        licenseAuditOffice = findViewById(R.id.traffic_driver_license_audit_office);
        licenseContact = findViewById(R.id.traffic_driver_license_contact);
        licenseIdCard = findViewById(R.id.traffic_driver_license_id_card);
    }

    @Override
    protected void initData() {
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });
        trafficToolbar.setNavigationOnClickListener(view -> {
            finish();
        });
    }

    private void getData() {
        String hasLicence = getStringToSP("traffic_has_driver_licence");
        if ("true".equals(hasLicence)) {
            licenseCardBtn.setVisibility(View.VISIBLE);
            addLicenseBtn.setVisibility(View.GONE);
            Api.config(Constant.NetWork.TRAFFIC_USER_LICENSE, null, this).getRequest(new RequestCallback() {
                @Override
                public void success(String result) {
                    TrafficDriverLicenseParam driverLicenseParam = gson.fromJson(result, TrafficDriverLicenseParam.class);
                    if (driverLicenseParam.getCode() == HttpURLConnection.HTTP_OK) {
                        TrafficDriverLicenseParam.DataDTO driverLicenseParamData = driverLicenseParam.getData().get(0);
                        runOnUiThread(() -> {
                            setCardValue(driverLicenseParamData);
                        });
                    } else {
                        showSyncToast(driverLicenseParam.getMsg());
                    }
                }

                @Override
                public void failure(Exception e) {
                    showSyncToast("网络异常");
                }
            });

            licenseCardBtn.setOnLongClickListener(view -> {
                jumpPageToIntent(new Intent(this, TrafficAddDriverLicenseActivity.class)
                        .putExtra("addTrafficType", "update"));
                return true;
            });
        } else {
            addLicenseBtn.setVisibility(View.VISIBLE);
            licenseCardBtn.setVisibility(View.GONE);

            addLicenseBtn.setOnClickListener(view -> {
                jumpPageToIntent(new Intent(this, TrafficAddDriverLicenseActivity.class)
                        .putExtra("addTrafficType", "add"));
            });
        }
    }

    private void setCardValue(TrafficDriverLicenseParam.DataDTO driverLicenseParamData) {
        licenseApplyDate.setText(driverLicenseParamData.getApplyDate());
        licenseContact.setText(driverLicenseParamData.getContact());
        licenseAuditOffice.setText(driverLicenseParamData.getAuditOffice());
        licenseLevel.setText(driverLicenseParamData.getLicenseLevel());
        licenseIdCard.setText(driverLicenseParamData.getIdCard());
        licenseLifeNo.setText(driverLicenseParamData.getLicenseNo());

        User user = gson.fromJson(getStringToSP("userInfo"), User.class);
        licenseName.setText(user.getUserName());

        licenseScore.setText(String.valueOf(driverLicenseParamData.getScore()));
        licenseVerifyDate.setText(driverLicenseParamData.getVerifyDate());
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }
}