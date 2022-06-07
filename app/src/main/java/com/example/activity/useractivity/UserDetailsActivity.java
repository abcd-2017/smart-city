package com.example.activity.useractivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.R;
import com.example.activity.BaseActivity;
import com.example.pojo.BaseParam;
import com.example.pojo.User;
import com.example.pojo.UserInfoParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kkk
 */
public class UserDetailsActivity extends BaseActivity {

    private static final String TAG = "UserDetailsActivity";
    private ImageView userImage;
    private TextView userId, username, userNick, userEmail, userPhone, userSex, userCard, userBalance, userScore;
    private Button saveBtn;
    private Toolbar detailToolbar;
    private User user = null;
    private ConstraintLayout nickConstrain, emailConstrain, phoneConstrain, sexConstrain, cardConstrain;
    private BottomSheetDialog sheetDialog;
    private BottomSheetBehavior<View> sheetBehavior;
    private RadioGroup radioGroup;
    private RadioButton manRadio;
    private RadioButton womanRadio;
    private Button saveSexBtn;
    private AlertDialog alertDialog;
    private EditText input;

    @Override
    protected int initLayout() {
        return R.layout.activity_user_details;
    }

    @Override
    protected void initView() {
        detailToolbar = findViewById(R.id.update_pwd_toolbar);
        userImage = findViewById(R.id.user_detail__image);
        userId = findViewById(R.id.user_detail_userId);
        username = findViewById(R.id.user_detail_username);
        userNick = findViewById(R.id.user_detail_nickname);
        userEmail = findViewById(R.id.user_detail_email);
        userPhone = findViewById(R.id.user_detail_phone);
        userSex = findViewById(R.id.user_detail_sex);
        userCard = findViewById(R.id.user_detail_card);
        userBalance = findViewById(R.id.user_detail_balance);
        userScore = findViewById(R.id.user_detail_score);
        saveBtn = findViewById(R.id.save_user_detail);
        nickConstrain = findViewById(R.id.nick_constrain);
        emailConstrain = findViewById(R.id.email_constrain);
        phoneConstrain = findViewById(R.id.phone_constrain);
        sexConstrain = findViewById(R.id.sex_constrain);
        cardConstrain = findViewById(R.id.card_constrain);
    }

    @Override
    protected void initData() {
        detailToolbar.setNavigationOnClickListener(view -> {
            finish();
        });
        Api.config(Constant.NetWork.GETINFO, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                UserInfoParam userInfoParam = gson.fromJson(result, UserInfoParam.class);
                if (userInfoParam.getCode() == HttpURLConnection.HTTP_OK) {
                    user = userInfoParam.getUser();
                    runOnUiThread(() -> {
                        initUserMessage();
                    });
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
        sexConstrain.setOnClickListener(view -> {
            final String[] sex = {"男"};
            if (sheetDialog == null) {
                View inflate = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_sex_select, null, false);
                sheetDialog = new BottomSheetDialog(this, com.google.android.material.R.style.Theme_Design_BottomSheetDialog);

                radioGroup = inflate.findViewById(R.id.user_detail_sex_group);
                manRadio = inflate.findViewById(R.id.user_detail_man);
                womanRadio = inflate.findViewById(R.id.user_sex_woman);
                saveSexBtn = inflate.findViewById(R.id.saveBtn);

                //设置点击dialog外部不消失
                sheetDialog.setCanceledOnTouchOutside(true);
                sheetDialog.setContentView(inflate);

                Log.d(TAG, "initData: sex => " + userSex.getText());
                if ("男".equals(userSex.getText())) {
                    manRadio.setChecked(true);
                } else {
                    womanRadio.setChecked(true);
                }
            }
            sheetDialog.show();

            radioGroup.setOnCheckedChangeListener((group, id) -> {
                if (id == manRadio.getId()) {
                    sex[0] = "男";
                } else {
                    sex[0] = "女";
                }
            });

            saveSexBtn.setOnClickListener(v -> {
                userSex.setText(sex[0]);
            });
        });

        nickConstrain.setOnClickListener(view -> {
            alertInput("昵称", false, 0, userNick);
        });

        emailConstrain.setOnClickListener(view -> {
            alertInput("邮箱", false, 0, userEmail);
        });

        phoneConstrain.setOnClickListener(view -> {
            alertInput("电话号码", true, 11, userPhone);
        });

        cardConstrain.setOnClickListener(view -> {
            alertInput("身份证", true, 18, userCard);
        });

        saveBtn.setOnClickListener(view -> {
            String nick = userNick.getText().toString();
            if (TextUtils.isEmpty(nick)) {
                showSyncToast("请输入昵称");
                return;
            }
            Map<String, Object> map = new HashMap<>();
            map.put("nick", nick);
            String email = userEmail.getText().toString();
            String phone = userPhone.getText().toString();
            String card = userCard.getText().toString();
            if (!TextUtils.isEmpty(email)) {
                map.put("email", email);
            }
            if (!TextUtils.isEmpty(phone)) {
                map.put("phone", phone);
            }
            if (!TextUtils.isEmpty(card)) {
                map.put("card", card);
            }
            map.put("sex", "男".equals(userSex.getText().toString()) ? "0" : "1");
            Api.config(Constant.NetWork.UPDATE_USER, map, this).putRequest(new RequestCallback() {
                @Override
                public void success(String result) {
                    Gson gson = new Gson();
                    BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                    Log.d(TAG, "success: baseParam => " + baseParam);
                    if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                        showSyncToast("修改成功");
                        finish();
                    } else {
                        showSyncToast("修改失败 " + baseParam.getMsg());
                    }
                }

                @Override
                public void failure(Exception e) {
                    showSyncToast("网络异常");
                }
            });
        });
    }

    @SuppressLint("SetTextI18n")
    private void alertInput(String title, boolean isNumber, int len, TextView view) {
        View inflate = LayoutInflater.from(this).inflate(R.layout.alert_dialog_message, null, false);

        input = inflate.findViewById(R.id.alert_input);
        TextView titleView = inflate.findViewById(R.id.alert_title);
        titleView.setText("请输入" + title);

        if (isNumber) {
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            input.setInputType(InputType.TYPE_CLASS_TEXT);
        }

        String msg = null;

        alertDialog = new AlertDialog.Builder(this)
                .setView(inflate)
                .setNegativeButton("取消", null)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String msg = input.getText().toString();
                        Log.d(TAG, "onClick: s => " + msg);
                        if (TextUtils.isEmpty(msg)) {
                            showSyncToast("请输入内容!");
                            return;
                        }
                        Log.d(TAG, "onClick: " + len + " " + msg.length());
                        if (isNumber && msg.length() != len) {
                            showSyncToast("输入有误!");
                            return;
                        }
                        view.setText(msg);
                    }
                }).create();
        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.shape_input_background);
        alertDialog.show();
    }

    @SuppressLint("CheckResult")
    private void initUserMessage() {
        username.setText(user.getUserName());
        userNick.setText(user.getNickName());
        userId.setText(String.valueOf(user.getUserId()));
        userEmail.setText(user.getEmail());
        userPhone.setText(user.getPhonenumber());
        userSex.setText("0".equals(user.getSex()) ? "男" : "女");
        userCard.setText(release(user.getIdCard()));
        userBalance.setText(String.valueOf(user.getBalance()));
        userScore.setText(String.valueOf(user.getScore()));
        if (!TextUtils.isEmpty(user.getAvatar())) {
            Glide.with(this).load(user.getAvatar()).load(userImage);
        }
    }

    private String release(String card) {
        if (TextUtils.isEmpty(card)) {
            return null;
        }
        char[] chars = card.toCharArray();
        for (int i = 2; i < chars.length - 4; i++) {
            chars[i] = '*';
        }
        return new String(chars);
    }
}