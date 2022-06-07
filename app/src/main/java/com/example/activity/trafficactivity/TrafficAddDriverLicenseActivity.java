package com.example.activity.trafficactivity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.pojo.BaseParam;
import com.example.pojo.trafficparam.TrafficDriverLicenseParam;
import com.example.util.AlertDialogUtils;
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
public class TrafficAddDriverLicenseActivity extends BaseActivity {

    private ImageView finishBtn;
    private Toolbar trafficToolbar;
    private TextView licenseTitleText, licenseDate, licenseAddBtnText;
    private CardView licenseAddBtn, licenseDeleteBtn;
    private EditText licenseAuditOffice, licenseContact, licenseIdCard, licenseNo;
    private Spinner licenseLevel, licenseVerifyDate;
    private Gson gson = new Gson();
    private int selectLevel = -1, selectVerifyDate = -1;

    @Override
    protected int initLayout() {
        return R.layout.activity_traffic_add_driver_license;
    }

    @Override
    protected void initView() {
        finishBtn = findViewById(R.id.finish_btn);
        trafficToolbar = findViewById(R.id.traffic_toolbar);
        licenseTitleText = findViewById(R.id.traffic_add_license_title_text);
        licenseAddBtn = findViewById(R.id.traffic_add_license_add_btn);
        licenseDeleteBtn = findViewById(R.id.traffic_add_license_delete_btn);
        licenseDate = findViewById(R.id.traffic_add_license_date);
        licenseAuditOffice = findViewById(R.id.traffic_add_license_audit_office);
        licenseContact = findViewById(R.id.traffic_add_license_contact);
        licenseIdCard = findViewById(R.id.traffic_add_license_id_card);
        licenseNo = findViewById(R.id.traffic_add_license_no);
        licenseLevel = findViewById(R.id.traffic_add_license_level);
        licenseVerifyDate = findViewById(R.id.traffic_add_license_verify_date);
        licenseAddBtnText = findViewById(R.id.traffic_add_license_add_btn_text);
    }

    @Override
    protected void initData() {
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });
        trafficToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        licenseLevel.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_parking_item, Constant.TRAFFIC_LICENSE_TYPE));
        licenseVerifyDate.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_parking_item, Constant.TRAFFIC_LICENSE_VERIFY));

        String addTrafficType = (String) getIntent().getExtras().get("addTrafficType");

        if ("add".equals(addTrafficType)) {
            licenseDeleteBtn.setVisibility(View.GONE);
            licenseTitleText.setText("添加驾驶证");
            licenseAddBtnText.setText("添加");
            licenseAddBtn.setVisibility(View.VISIBLE);

            licenseAddBtn.setOnClickListener(view -> {
                Map<String, Object> param = new HashMap<>();
                param.put("applyDate", licenseDate.getText().toString());
                param.put("auditOffice", licenseAuditOffice.getText().toString());
                param.put("contact", licenseContact.getText().toString());
                param.put("licenseNo", licenseNo.getText().toString());
                param.put("idCard", licenseIdCard.getText().toString());
                if (selectLevel != -1)
                    param.put("licenseLevel", Constant.TRAFFIC_LICENSE_TYPE.get(selectLevel));
                param.put("verifyDate", Constant.TRAFFIC_LICENSE_VERIFY.get(selectVerifyDate));

                Api.config(Constant.NetWork.TRAFFIC_LICENSE, param, this).postRequest(new RequestCallback() {
                    @Override
                    public void success(String result) {
                        BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                        if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                            showSyncToast("添加成功");
                            setStringToSP("traffic_has_driver_licence", "true");
                            finish();
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

            //申领时间
            CalendarView startTime = new CalendarView(this);
            startTime.setDate(System.currentTimeMillis());
            startTime.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                    licenseDate.setText(String.format("%s-%s-%s", year, (month + 1), dayOfMonth));
                }
            });
            AlertDialog startTimDialog = new AlertDialog.Builder(this)
                    .setView(startTime).create();
            licenseDate.setOnClickListener(view -> {
                startTimDialog.show();
            });

            licenseLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectLevel = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            licenseVerifyDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectVerifyDate = position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        } else {
            licenseDeleteBtn.setVisibility(View.VISIBLE);
            licenseTitleText.setText("驾驶证详情");
            licenseAddBtn.setVisibility(View.GONE);

            Api.config(Constant.NetWork.TRAFFIC_USER_LICENSE, null, this).getRequest(new RequestCallback() {
                @Override
                public void success(String result) {
                    TrafficDriverLicenseParam trafficDriverLicenseParam = gson.fromJson(result, TrafficDriverLicenseParam.class);
                    if (trafficDriverLicenseParam.getCode() == HttpURLConnection.HTTP_OK) {
                        TrafficDriverLicenseParam.DataDTO driverLicenseParamData = trafficDriverLicenseParam.getData().get(0);
                        runOnUiThread(() -> {
                            setCardData(driverLicenseParamData);
                        });
                    } else {
                        showSyncToast(trafficDriverLicenseParam.getMsg());
                    }
                }

                @Override
                public void failure(Exception e) {
                    showSyncToast("网络异常");
                }
            });
        }
    }

    private void setCardData(TrafficDriverLicenseParam.DataDTO driverLicenseParamData) {
        licenseIdCard.setText(driverLicenseParamData.getIdCard());

        licenseDeleteBtn.setOnClickListener(view -> {
            AlertDialogUtils.getInstance();
            AlertDialogUtils.showConfirmDialog(this, "提示", "真的要删除吗？", new AlertDialogUtils.OnDialogButtonClickListener() {
                @Override
                public void clickOk() {
                    Api.config(Constant.NetWork.TRAFFIC_LICENSE, null, TrafficAddDriverLicenseActivity.this).deleteRestfulRequest(new RequestCallback() {
                        @Override
                        public void success(String result) {
                            BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                            if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                                showSyncToast("删除成功");
                                setStringToSP("traffic_has_driver_licence", "false");
                                finish();
                            } else {
                                showSyncToast(baseParam.getMsg());
                            }
                        }

                        @Override
                        public void failure(Exception e) {
                            showSyncToast("网络异常");
                        }
                    }, driverLicenseParamData.getId());
                }

                @Override
                public void clickCancel() {

                }
            });
        });
    }
}