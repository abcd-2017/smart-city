package com.example.activity.parkingactivity;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.pojo.BaseParam;
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
public class ParkingCorrectionActivity extends BaseActivity {

    private Toolbar parkingToolbar;
    private ImageView finishBtn;
    private Button correctionSubmit;
    private EditText correctionNum;
    private EditText correctionName;
    private EditText correctionDetail;
    private EditText correctionPhoto;
    private EditText correctionRemarks;

    @Override
    protected int initLayout() {
        return R.layout.activity_parking_correction;
    }

    @Override
    protected void initView() {
        parkingToolbar = findViewById(R.id.parking_toolbar);
        finishBtn = findViewById(R.id.finish_btn);
        correctionSubmit = findViewById(R.id.button_correction_submit);
        correctionNum = findViewById(R.id.parking_correction_num);
        correctionName = findViewById(R.id.parking_correction_name);
        correctionDetail = findViewById(R.id.parking_correction_detail);
        correctionPhoto = findViewById(R.id.parking_correction_photo);
        correctionRemarks = findViewById(R.id.parking_correction_remarks);
    }

    @Override
    protected void initData() {
        parkingToolbar.setNavigationOnClickListener(view -> {
            finish();
        });
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });

        correctionSubmit.setOnClickListener(view -> {
            String detail = correctionDetail.getText().toString();
            String name = correctionName.getText().toString();
            String photo = correctionPhoto.getText().toString();
            String remark = correctionRemarks.getText().toString();
            String num = correctionNum.getText().toString();
            if (TextUtils.isEmpty(detail)) {
                showSyncToast("问题描述不能为空");
                return;
            } else if (TextUtils.isEmpty(name)) {
                showSyncToast("停车场名称不能为空");
                return;
            } else if (TextUtils.isEmpty(photo)) {
                showSyncToast("纠错照片路径不能为空");
                return;
            } else if (TextUtils.isEmpty(remark)) {
                showSyncToast("备注不能为空");
                return;
            } else if (TextUtils.isEmpty(num)) {
                showSyncToast("停车位数不能为空");
                return;
            }

            Map<String, Object> param = new HashMap<>();
            param.put("content", detail);
            param.put("name", name);
            param.put("photo", photo);
            param.put("remark", remark);
            param.put("spotCount", num);

            Api.config(Constant.NetWork.PARK_CORRECT, param, this).postRequest(new RequestCallback() {
                @Override
                public void success(String result) {
                    Gson gson = new Gson();
                    BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                    if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                        showSyncToast("反馈成功");
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
    }
}