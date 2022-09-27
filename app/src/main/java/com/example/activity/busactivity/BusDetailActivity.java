package com.example.activity.busactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.pojo.BaseParam;
import com.example.pojo.User;
import com.example.pojo.UserInfoParam;
import com.example.pojo.busparam.BusOrderParam;
import com.example.pojo.busparam.BusStopListParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kkk
 */
public class BusDetailActivity extends BaseActivity {

    private ImageView finishBtn, swapBtn;
    private Toolbar busToolbar;
    private TextView busStart, busEnd, busPassengName, busPassengPhone;
    private Spinner busDown, busPickUp;
    private CardView busSubmit;
    private Gson gson = new Gson();
    private Map<String, Object> param = new HashMap<>();
    private Integer id;
    private List<String> startSpinnerList = new ArrayList<>(), endSpinnerList = new ArrayList<>();
    private Integer startStopNum = 0, endStopNum = 0;
    private CardView orderCancel, orderOk;
    private RadioGroup payType;
    private RadioButton payTypeWeChat, payTypeAli;
    private Integer payTypeNum = -1;
    private View alertView;
    private String orderNum = null;
    private Integer animatorTime = 200;
    private AnimationSet endAnimationSet, startAnimationSet;
    private RotateAnimation iconRotationAnimator;
    private int n;

    @Override
    protected int initLayout() {
        return R.layout.activity_bus_detail;
    }

    @Override
    protected void initView() {
        finishBtn = findViewById(R.id.finish_btn);
        swapBtn = findViewById(R.id.bus_line_detail_swap);
        busToolbar = findViewById(R.id.bus_toolbar);
        busStart = findViewById(R.id.bus_line_detail_start);
        busEnd = findViewById(R.id.bus_line_detail_end);
        busPassengName = findViewById(R.id.bus_line_detail_name);
        busPassengPhone = findViewById(R.id.bus_line_detail_phone);
        busDown = findViewById(R.id.bus_line_detail_down);
        busPickUp = findViewById(R.id.bus_line_detail_pick_up);
        busSubmit = findViewById(R.id.bus_line_detail_submit);

        alertView = LayoutInflater.from(this)
                .inflate(R.layout.alert_bus_pay_order, null, false);

        orderCancel = alertView.findViewById(R.id.alert_dialog_cancel);
        orderOk = alertView.findViewById(R.id.alert_dialog_ok);
        payType = alertView.findViewById(R.id.bus_order_pay_type);
        payTypeWeChat = alertView.findViewById(R.id.bus_pay_type_wechat);
        payTypeAli = alertView.findViewById(R.id.bus_pay_type_ali);
    }

    @Override
    protected void initData() {
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });
        busToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        Bundle extras = getIntent().getExtras();
        id = (Integer) extras.get("linesId");
        String lineName = (String) extras.get("linesName");
        Integer price = (Integer) extras.get("price");

        getUserInfo();

        getStopList();

        busDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                busEnd.setText(endSpinnerList.get(position));
                endStopNum = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        busPickUp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                busStart.setText(startSpinnerList.get(position));
                startStopNum = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(alertView).create();
        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.alert_bg_translate);

        payType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == payTypeWeChat.getId()) {
                    payTypeNum = 0;
                } else if (checkedId == payTypeAli.getId()) {
                    payTypeNum = 1;
                } else {
                    payTypeNum = 2;
                }
            }
        });

        //取消支付
        orderCancel.setOnClickListener(view -> {
            finish();
        });
        //确认支付
        orderOk.setOnClickListener(view -> {
            if (orderNum != null) {
                if (payTypeNum == -1) {
                    showSyncToast("未选择支付方式，支付失败");
                    return;
                }

                param.clear();
                param.put("orderNum", orderNum);
                param.put("paymentType", Constant.PAY_TYPE[payTypeNum]);

                Api.config(Constant.NetWork.BUS_PAY, param, this).postRequest(new RequestCallback() {
                    @Override
                    public void success(String result) {
                        BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                        if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                            showSyncToast("支付成功");
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

        busSubmit.setOnClickListener(view -> {
            param.clear();
            param.put("start", busStart.getText().toString());
            param.put("end", busEnd.getText().toString());
            param.put("price", price);
            param.put("path", lineName);
            param.put("status", 0);

            Api.config(Constant.NetWork.BUS_ORDER, param, this).postRequest(new RequestCallback() {
                @Override
                public void success(String result) {
                    BusOrderParam busOrderParam = gson.fromJson(result, BusOrderParam.class);
                    if (busOrderParam.getCode() == HttpURLConnection.HTTP_OK) {
                        runOnUiThread(alertDialog::show);
                        orderNum = busOrderParam.getOrderNum();
                    } else {
                        showSyncToast(busOrderParam.getMsg());
                    }
                }

                @Override
                public void failure(Exception e) {
                    showSyncToast("网络异常");
                }
            });
        });

        //初始化动画
        initAnimator();

        swapBtn.setOnClickListener(view -> {
            exchangeSite();
        });
    }

    //声明动画
    private void initAnimator() {
        //图标旋转
        iconRotationAnimator = new RotateAnimation(0, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        iconRotationAnimator.setDuration(animatorTime);
        iconRotationAnimator.setInterpolator(new LinearInterpolator());
        iconRotationAnimator.setFillAfter(true);

        //文字平移加变淡
        TranslateAnimation startTransAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0.7f, 0, 0, 0, 0);
        startTransAnimation.setDuration(animatorTime);
        startTransAnimation.setInterpolator(new BounceInterpolator());
        AlphaAnimation startAlphaAnimation = new AlphaAnimation(1f, 0);
        startAlphaAnimation.setDuration(animatorTime);
        startAlphaAnimation.setInterpolator(new BounceInterpolator());
        startAnimationSet = new AnimationSet(this, null);
        startAnimationSet.addAnimation(startTransAnimation);
        startAnimationSet.addAnimation(startAlphaAnimation);

        TranslateAnimation endTransAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -0.7f, 0, 0, 0, 0);
        endTransAnimation.setDuration(animatorTime);
        endTransAnimation.setInterpolator(new BounceInterpolator());
        busEnd.startAnimation(endTransAnimation);
        AlphaAnimation endAlphaAnimation = new AlphaAnimation(1f, 0);
        endAlphaAnimation.setDuration(animatorTime);
        endAlphaAnimation.setInterpolator(new BounceInterpolator());
        busEnd.startAnimation(endAlphaAnimation);
        endAnimationSet = new AnimationSet(this, null);
        endAnimationSet.addAnimation(endTransAnimation);
        endAnimationSet.addAnimation(endAlphaAnimation);

        endAlphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                String temp = busStart.getText().toString();
                busStart.setText(busEnd.getText());
                busEnd.setText(temp);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    //获取用户信息
    private void getUserInfo() {
        Api.config(Constant.NetWork.GETINFO, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                UserInfoParam userInfoParam = gson.fromJson(result, UserInfoParam.class);
                if (userInfoParam.getCode() == HttpURLConnection.HTTP_OK) {
                    User user = userInfoParam.getUser();
                    runOnUiThread(() -> {
                        busPassengName.setText(user.getUserName());
                        busPassengPhone.setText(user.getPhonenumber());
                    });
                } else {
                    showSyncToast(userInfoParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
    }

    //获取站点信息
    private void getStopList() {
        param.clear();
        param.put("linesId", id);

        Api.config(Constant.NetWork.BUS_STOP_LIST, param, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                BusStopListParam busStopListParam = gson.fromJson(result, BusStopListParam.class);
                if (busStopListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<BusStopListParam.RowsDTO> stopListParamRows = busStopListParam.getRows();
                    n = stopListParamRows.size();
                    for (int i = 0; i < n; i++) {
                        startSpinnerList.add(stopListParamRows.get(i).getName());
                        endSpinnerList.add(stopListParamRows.get(n - i - 1).getName());
                    }
                    runOnUiThread(() -> {
                        busDown.setAdapter(new ArrayAdapter<String>(BusDetailActivity.this, R.layout.spinner_parking_item, endSpinnerList));
                        busDown.setSelection(0);
                        busEnd.setText(endSpinnerList.get(0));
                        endStopNum = n - 1;

                        busPickUp.setAdapter(new ArrayAdapter<String>(BusDetailActivity.this, R.layout.spinner_parking_item, startSpinnerList));
                        busPickUp.setSelection(0);
                        busStart.setText(startSpinnerList.get(0));
                        startStopNum = 0;
                    });
                } else {
                    showSyncToast(busStopListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
    }

    //交换起点和终点 动画
    private void exchangeSite() {
        //启动动画
        busStart.startAnimation(startAnimationSet);
        busEnd.startAnimation(endAnimationSet);
        swapBtn.startAnimation(iconRotationAnimator);
    }
}