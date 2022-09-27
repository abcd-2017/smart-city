package com.example.activity.hospitalactivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.pojo.BaseParam;
import com.example.pojo.hospitalparam.HospitalPatientListParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kkk
 */
public class AddHospitalCardActivity extends BaseActivity {

    private static final String TAG = "AddHospitalCardActivity";
    private Toolbar toolbar;
    private ImageView finishBtn;
    private EditText cardName;
    private EditText cardPhone;
    private EditText cardNum;
    private TextView cardTime;
    private EditText cardAddress;
    private RadioGroup radioGroup;
    private Button addCard;
    private Integer currSex = 0;
    private RadioButton radioWoman;
    private RadioButton radioMan;
    Map<String, Object> param = new HashMap<>();
    private TextView cardTitle;

    @Override
    protected int initLayout() {
        return R.layout.activity_add_hospital_card;
    }

    @Override
    protected void initView() {
        toolbar = findViewById(R.id.hospital_toolbar);
        finishBtn = findViewById(R.id.finish_btn);
        cardTitle = findViewById(R.id.hospital_card_title);
        cardName = findViewById(R.id.hospital_add_card_name);
        cardPhone = findViewById(R.id.hospital_add_card_phone);
        cardNum = findViewById(R.id.hospital_add_card_num);
        cardTime = findViewById(R.id.hospital_add_card_time);
        cardAddress = findViewById(R.id.hospital_add_card_address);
        radioGroup = findViewById(R.id.sex_radio_group);
        addCard = findViewById(R.id.add_hospital_card);
        radioWoman = findViewById(R.id.sex_radio_woman);
        radioMan = findViewById(R.id.sex_radio_man);
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
        Integer status = (Integer) extras.get("status");

        //初始化日期控件
        CalendarView calendarView = new CalendarView(this);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (status == -1) {
            calendarView.setDate(System.currentTimeMillis());
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.add(Calendar.MONTH, 1);
            cardTime.setText(dateFormat.format(calendar.getTime()));
        } else {
            addCard.setText("修改就诊卡");
            cardTitle.setText("修改就诊卡");
            addCard.setBackgroundResource(R.drawable.shape_red_background);
            String json = (String) extras.get("cardDetail");
            Gson gson = new Gson();
            HospitalPatientListParam.RowsDTO rowsDTO = gson.fromJson(json, HospitalPatientListParam.RowsDTO.class);

            cardName.setText(rowsDTO.getName());
            cardPhone.setText(rowsDTO.getTel());
            cardAddress.setText(rowsDTO.getAddress());
            cardNum.setText(rowsDTO.getCardId());
            String sex = rowsDTO.getSex();
            if ("0".equals(sex)) {
                radioMan.setChecked(true);
            } else {
                radioWoman.setChecked(true);
            }
            cardTime.setText(rowsDTO.getBirthday());
        }


        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(calendarView).create();

        cardTime.setOnClickListener(view -> {
            alertDialog.show();
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                cardTime.setText(String.format("%d-%d-%d", year, month + 1, dayOfMonth));
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == radioWoman.getId()) {
                    currSex = 1;
                } else {
                    currSex = 0;
                }
            }
        });

        addCard.setOnClickListener(view -> {
            if (!inspect()) {
                return;
            }

            if (status == -1) {
                Api.config(Constant.NetWork.HOSPITAL_PATIENT, param, this).postRequest(new RequestCallback() {
                    @Override
                    public void success(String result) {
                        Gson gson = new Gson();
                        BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                        if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                            showSyncToast("添加成功");
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
            } else {
                Api.config(Constant.NetWork.HOSPITAL_PATIENT, param, this).putRequest(new RequestCallback() {
                    @Override
                    public void success(String result) {
                        Gson gson = new Gson();
                        BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                        if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                            showSyncToast("修改成功");
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
            }
        });
    }

    private boolean inspect() {
        String cardNameText = cardName.getText().toString();
        String cardPhoneText = cardPhone.getText().toString();
        String cardCardIdText = cardNum.getText().toString();
        String cardAddressText = cardAddress.getText().toString();
        String cardTimeText = cardTime.getText().toString();

        if (TextUtils.isEmpty(cardNameText)) {
            showSyncToast("姓名不能为空");
            return false;
        }
        if (TextUtils.isEmpty(cardPhoneText)) {
            showSyncToast("电话号码不能为空");
            return false;
        }
        if (TextUtils.isEmpty(cardCardIdText)) {
            showSyncToast("身份证号码不能为空");
            return false;
        }
        if (TextUtils.isEmpty(cardAddressText)) {
            showSyncToast("住址不能为空");
            return false;
        }

        param.clear();
        param.put("address", cardAddressText);
        param.put("birthday", cardTimeText);
        param.put("cardId", cardCardIdText);
        param.put("name", cardNameText);
        param.put("sex", currSex);
        param.put("tel", cardPhoneText);

        return true;
    }
}