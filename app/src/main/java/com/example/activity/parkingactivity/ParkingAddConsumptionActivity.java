package com.example.activity.parkingactivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.pojo.BaseParam;
import com.example.pojo.parkingparam.ParkingCarConsumptionParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kkk
 */
public class ParkingAddConsumptionActivity extends BaseActivity {

    private Toolbar parkingToolbar;
    private ImageView finishBtn;
    private CardView addBtn;
    private TextView addTime;
    private EditText addTravelDistance;
    private EditText addGasFilling;
    private EditText addAmount;
    private String currentDate;
    private TextView consumptionTitle;
    private TextView btnText;
    private Map<String, Object> param = new HashMap<>();
    private Gson gson = new Gson();

    @Override
    protected int initLayout() {
        return R.layout.activity_parking_add_consumption;
    }

    @Override
    protected void initView() {
        parkingToolbar = findViewById(R.id.parking_toolbar);
        finishBtn = findViewById(R.id.finish_btn);
        addBtn = findViewById(R.id.consumption_add_btn);
        addTime = findViewById(R.id.consumption_add_time);
        addTravelDistance = findViewById(R.id.consumption_add_travel_distance);
        addGasFilling = findViewById(R.id.consumption_add_gas_filling);
        addAmount = findViewById(R.id.consumption_add_amount);
        consumptionTitle = findViewById(R.id.parking_consumption_title);
        btnText = findViewById(R.id.consumption_add_btn_text);
    }

    @Override
    protected void initData() {
        parkingToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });
        CalendarView calendarView = new CalendarView(this);

        Bundle extras = getIntent().getExtras();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        if (extras == null) {
            calendarView.setDate(System.currentTimeMillis());

            Calendar calendar = GregorianCalendar.getInstance();
            currentDate = simpleDateFormat.format(calendar.getTime());
            addTime.setText(currentDate);

            addBtn.setOnClickListener(view -> {
                param.clear();
                checkParam();

                Api.config(Constant.NetWork.PARK_CAR_CONSUMPTION, param, this).postRequest(new RequestCallback() {
                    @Override
                    public void success(String result) {
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
            });

            addTravelDistance.setFocusable(true);
            addTravelDistance.requestFocus();
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        } else {
            consumptionTitle.setText("修改里程");
            btnText.setText("修改");
            addBtn.setCardBackgroundColor(Color.rgb(245, 150, 170));

            String consumptionDetail = (String) extras.get("consumptionDetail");
            ParkingCarConsumptionParam.RowsDTO rowsDTO = gson.fromJson(consumptionDetail, ParkingCarConsumptionParam.RowsDTO.class);
            currentDate = rowsDTO.getTravelDate();
            addAmount.setText(String.valueOf(rowsDTO.getAmount()));
            addTime.setText(currentDate);
            addTravelDistance.setText(String.valueOf(rowsDTO.getTravelDistance()));
            addGasFilling.setText(String.valueOf(rowsDTO.getGasFilling()));
            try {
                Date parse = simpleDateFormat.parse(currentDate);
                calendarView.setDate(parse.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            addBtn.setOnClickListener(view -> {
                param.clear();
                checkParam();

                param.put("id", rowsDTO.getId());

                Api.config(Constant.NetWork.PARK_CAR_CONSUMPTION, param, this).putRequest(new RequestCallback() {
                    @Override
                    public void success(String result) {
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
            });
        }


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                currentDate = String.format("%d-%d-%d", year, month + 1, dayOfMonth);
                addTime.setText(currentDate);
            }
        });

        AlertDialog alertCalendar = new AlertDialog.Builder(this)
                .setView(calendarView)
                .create();

        addTime.setOnClickListener(view -> {
            alertCalendar.show();
        });
    }

    private void checkParam() {
        String amount = addAmount.getText().toString();
        String gasFilling = addGasFilling.getText().toString();
        String travelDistance = addTravelDistance.getText().toString();

        if (TextUtils.isEmpty(amount)) {
            showSyncToast("请输入加油金额");
            return;
        }
        if (TextUtils.isEmpty(gasFilling)) {
            showSyncToast("请输入加油量");
            return;
        }
        if (TextUtils.isEmpty(travelDistance)) {
            showSyncToast("请输入行驶距离");
            return;
        }
        param.put("amount", amount);
        param.put("gasFilling", gasFilling);
        param.put("plateNo", getStringToSP("plateNo"));
        param.put("travelDate", currentDate);
        param.put("travelDistance", travelDistance);
    }
}