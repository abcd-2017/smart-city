package com.example.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.R;
import com.example.pojo.RotationImageParam;
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
public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private TextView homeText;
    private View[] views = new View[5];
    private String[] homeTitle = new String[]{"欢迎来到智慧城市", "我们的目的是服务民众", "让民众的城市生活更加美好", "提供一站式服务"};
    private Button setPort, goToHome, cancelBtn, okBtn;
    private ConstraintLayout setNetWorkLayout;
    private EditText portInput, ipInput;
    private ConstraintLayout mainLayout;

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        homeText = findViewById(R.id.home_text);
        views[0] = findViewById(R.id.page1);
        views[1] = findViewById(R.id.page2);
        views[2] = findViewById(R.id.page3);
        views[3] = findViewById(R.id.page4);
        views[4] = findViewById(R.id.page5);
        setPort = findViewById(R.id.set_port);
        goToHome = findViewById(R.id.go_to_home);
        setNetWorkLayout = findViewById(R.id.set_network_layout);
        cancelBtn = findViewById(R.id.set_network_cancel);
        okBtn = findViewById(R.id.set_network_ok);
        portInput = findViewById(R.id.set_network_port_input);
        ipInput = findViewById(R.id.set_network_ip_input);
        mainLayout = findViewById(R.id.main_layout);
        changeHomeRound(0, false);
    }

    @Override
    protected void initData() {
        Map<String, Object> param = new HashMap<>();
        param.put("type", 1);
        Api.config(Constant.NetWork.ROTATION_IMAGE, param, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                System.out.println(result);
                RotationImageParam rotationImageParam = gson.fromJson(result, RotationImageParam.class);
                Log.d(TAG, "success: param => " + rotationImageParam);
                if (rotationImageParam.getCode() == HttpURLConnection.HTTP_OK && rotationImageParam.getRows().size() > 0) {
                    Glide.with(MainActivity.this).load(Constant.BASE_API + rotationImageParam.getRows().get(0).getAdvImg()).into(new CustomTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            runOnUiThread(() -> {
                                mainLayout.setBackground(resource);
                            });
                            Log.d(TAG, "onResourceReady: " + resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                            runOnUiThread(() -> {
                                mainLayout.setBackground(placeholder);
                            });
                            Log.d(TAG, "onResourceReady: " + placeholder);
                        }
                    });
                } else {
                    showSyncToast(rotationImageParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });

        for (View view : views) {
            view.setOnClickListener(this);
        }

        goToHome.setOnClickListener(view -> {
            String saveIp = getStringToSP("ip");
            String savePort = getStringToSP("port");
            Log.d(TAG, "initData: ip => " + saveIp);
            Log.d(TAG, "initData: port => " + savePort);
            if (!TextUtils.isEmpty(savePort)) {
                Constant.NetWork.PORT = savePort;
                Constant.NetWork.IP = saveIp;
            }
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        });

        setPort.setOnClickListener(view -> {
            ipInput.setText("");
            portInput.setText("");
            setNetWorkLayout.setVisibility(View.VISIBLE);
        });

        cancelBtn.setOnClickListener(view -> {
            setNetWorkLayout.setVisibility(View.GONE);
        });

        okBtn.setOnClickListener(view -> {
            String ip = ipInput.getText().toString();
            String port = portInput.getText().toString();
            Log.d(TAG, "initData: ip => " + ip);
            Log.d(TAG, "initData: port => " + port);
            if (TextUtils.isEmpty(ip)) {
                showSyncToast("请输入ip地址");
                return;
            }
            if (TextUtils.isEmpty(port)) {
                showSyncToast("请输入端口");
                return;
            }
            setStringToSP("ip", ip);
            setStringToSP("port", port);
            Constant.NetWork.IP = ip;
            Constant.NetWork.PORT = port;
            showSyncToast("设置成功!!");
            setNetWorkLayout.setVisibility(View.GONE);
        });

        ipInput.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                InputMethodManager service = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                service.hideSoftInputFromWindow(ipInput.getWindowToken(), 0);
            } else {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            }
        });
        portInput.setOnFocusChangeListener((v, hasFocus) -> {
            Log.d(TAG, "initData: flag => " + hasFocus);
            if (!hasFocus) {
                InputMethodManager service = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                service.hideSoftInputFromWindow(portInput.getWindowToken(), 0);
            } else {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (views[0].equals(v)) {
            Log.d(TAG, "onClick: 1");
            changeHomeRound(0, false);
        } else if (views[1].equals(v)) {
            Log.d(TAG, "onClick: 2");
            changeHomeRound(1, false);
        } else if (views[2].equals(v)) {
            Log.d(TAG, "onClick: 3");
            changeHomeRound(2, false);
        } else if (views[3].equals(v)) {
            Log.d(TAG, "onClick: 4");
            changeHomeRound(3, false);
        } else if (views[4].equals(v)) {
            Log.d(TAG, "onClick: 5");
            changeHomeRound(4, true);
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void changeHomeRound(int num, boolean flag) {
        for (View view : views) {
            view.setBackground(getResources().getDrawable(R.drawable.shape_home_round, null));
        }
        views[num].setBackground(getResources().getDrawable(R.drawable.shape_home_round_select, null));
        if (num != homeTitle.length) {
            homeText.setText(homeTitle[num]);
        }
        if (flag) {
            homeText.setVisibility(View.GONE);
            goToHome.setVisibility(View.VISIBLE);
            setPort.setVisibility(View.VISIBLE);
        } else {
            homeText.setVisibility(View.VISIBLE);
            goToHome.setVisibility(View.GONE);
            setPort.setVisibility(View.GONE);
            setNetWorkLayout.setVisibility(View.GONE);
        }
    }
}