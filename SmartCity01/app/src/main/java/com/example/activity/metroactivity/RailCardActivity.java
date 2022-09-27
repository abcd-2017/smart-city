package com.example.activity.metroactivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.pojo.BaseParam;
import com.example.pojo.metroparam.MetroCardParam;
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
public class RailCardActivity extends BaseActivity {

    private static final String TAG = "RailCardActivity";
    private Toolbar metroBanner;
    private ImageView finishBtn;
    private TextView cardNum;
    private Button receiveCard;
    private Button cancelCard;
    private CardView metroCard;
    private Integer metroCardNum;
    private AlertDialog cancelCardDialog;
    private AlertDialog receiveCardAlert;
    private Gson gson;

    @Override
    protected int initLayout() {
        return R.layout.activity_rail_card;
    }

    @Override
    protected void initView() {
        metroBanner = findViewById(R.id.metro_toolbar);
        cardNum = findViewById(R.id.card_num);
        finishBtn = findViewById(R.id.finish_btn);
        receiveCard = findViewById(R.id.receive_card);
        cancelCard = findViewById(R.id.cancel_card);
        metroCard = findViewById(R.id.metro_card);
        getMetroCard();
    }

    @Override
    protected void initData() {
        metroBanner.setNavigationOnClickListener(view -> {
            finish();
        });

        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });


        receiveCard.setOnClickListener(view -> {
            receiveCardAlert.show();
        });
        cancelCard.setOnClickListener(view -> {
            cancelCardDialog.show();
        });

        View cardView = LayoutInflater.from(this).inflate(R.layout.alert_dialog_receiver_card, null);
        receiveCardAlert = new AlertDialog.Builder(this)
                .setView(cardView)
                .setTitle("领取乘车卡")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText cardEdit = cardView.findViewById(R.id.id_card_edit);
                        EditText numberEdit = cardView.findViewById(R.id.phone_number_edit);
                        EditText nameEdit = cardView.findViewById(R.id.user_name_edit);
                        if (TextUtils.isEmpty(cardEdit.getText().toString()) || TextUtils.isEmpty(nameEdit.getText().toString()) || TextUtils.isEmpty(numberEdit.getText().toString())) {
                            showSyncToast("参数错误,领取失败");
                            return;
                        }
                        Map<String, Object> param = new HashMap<>();
                        param.put("idCard", cardEdit.getText().toString());
                        param.put("phonenumber", numberEdit.getText().toString());
                        param.put("userName", nameEdit.getText().toString());
                        Api.config(Constant.NetWork.METRO_CARD, param, RailCardActivity.this).postRequest(new RequestCallback() {
                            @Override
                            public void success(String result) {
                                BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                                if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                                    showSyncToast("领取成功");
                                    getMetroCard();
                                } else {
                                    showSyncToast(baseParam.getMsg());
                                }
                            }

                            @Override
                            public void failure(Exception e) {
                                Log.d(TAG, "failure: 网络异常");
                            }
                        });
                    }
                }).setNegativeButton("取消", null)
                .create();

        cancelCardDialog = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("确定要注销乘车卡?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Api.config(Constant.NetWork.METRO_CARD, null, RailCardActivity.this).deleteRestfulRequest(new RequestCallback() {
                            @Override
                            public void success(String result) {
                                BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                                if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                                    showSyncToast("注销成功");
                                    getMetroCard();
                                } else {
                                    showSyncToast(baseParam.getMsg());
                                }
                            }

                            @Override
                            public void failure(Exception e) {
                                Log.d(TAG, "failure: 网络异常");
                            }
                        }, metroCardNum);
                    }
                }).setNegativeButton("取消", null)
                .create();
    }

    private void getMetroCard() {
        Api.config(Constant.NetWork.METRO_CARD, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                gson = new Gson();
                MetroCardParam metroCardParam = gson.fromJson(result, MetroCardParam.class);
                if (metroCardParam.getCode() == HttpURLConnection.HTTP_OK) {
                    MetroCardParam.DataDTO data = metroCardParam.getData();
                    metroCardNum = data.getId();
                    runOnUiThread(() -> {
                        RailCardActivity.this.cardNum.setText(data.getCardNum());
                        metroCard.setVisibility(View.VISIBLE);
                        cancelCard.setVisibility(View.VISIBLE);
                        receiveCard.setVisibility(View.INVISIBLE);
                    });
                } else {
                    showSyncToast(metroCardParam.getMsg());
                    runOnUiThread(() -> {
                        metroCard.setVisibility(View.INVISIBLE);
                        cancelCard.setVisibility(View.INVISIBLE);
                        receiveCard.setVisibility(View.VISIBLE);
                    });
                }
            }

            @Override
            public void failure(Exception e) {
                Log.d(TAG, "failure: 网络异常");
            }
        });

    }
}