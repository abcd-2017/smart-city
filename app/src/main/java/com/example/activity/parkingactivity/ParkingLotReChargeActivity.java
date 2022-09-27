package com.example.activity.parkingactivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.adapter.parkingadapter.ParkingReChargeListAdapter;
import com.example.pojo.BaseParam;
import com.example.pojo.parkingparam.ParkingReChargeListParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kkk
 */
public class ParkingLotReChargeActivity extends BaseActivity {

    private Toolbar parkingToolbar;
    private ImageView finishBtn;
    private CardView rechargeBtn;
    private RecyclerView rechargeRecycler;
    private Gson gson = new Gson();
    private TextView rechargeEmpty;
    private ParkingReChargeListAdapter chargeListAdapter;
    private String[] payType = {"电子支付", "微信", "支付宝"};
    private int selectType = -1;

    @Override
    protected int initLayout() {
        return R.layout.activity_parking_lot_re_charge;
    }

    @Override
    protected void initView() {
        parkingToolbar = findViewById(R.id.parking_toolbar);
        finishBtn = findViewById(R.id.finish_btn);
        rechargeBtn = findViewById(R.id.recharge_btn);
        rechargeRecycler = findViewById(R.id.recharge_list_recycler);
        rechargeEmpty = findViewById(R.id.parking_recharge_empty);
        chargeListAdapter = new ParkingReChargeListAdapter();
    }

    @Override
    protected void initData() {
        parkingToolbar.setNavigationOnClickListener(view -> {
            finish();
        });
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });

        rechargeRecycler.setLayoutManager(new LinearLayoutManager(this));
        rechargeRecycler.setAdapter(chargeListAdapter);

        getReChargeList();

        createAlertDialog();
    }

    private void getReChargeList() {
        Api.config(Constant.NetWork.PARK_RECHARGE_LIST, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                ParkingReChargeListParam chargeListParam = gson.fromJson(result, ParkingReChargeListParam.class);
                if (chargeListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<ParkingReChargeListParam.RowsDTO> listParamRows = chargeListParam.getRows();
                    runOnUiThread(() -> {
                        if (listParamRows.size() == 0) {
                            rechargeEmpty.setVisibility(View.VISIBLE);
                            rechargeRecycler.setVisibility(View.GONE);
                        } else {
                            rechargeEmpty.setVisibility(View.GONE);
                            rechargeRecycler.setVisibility(View.VISIBLE);
                            chargeListAdapter.setData(listParamRows);
                        }
                    });
                } else {
                    showSyncToast(chargeListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
    }

    private void createAlertDialog() {
        View alertView = LayoutInflater.from(this)
                .inflate(R.layout.alert_dialog_parking_recharge, null, false);

        EditText moneyNumber = alertView.findViewById(R.id.parking_car_plateNo);
        RadioGroup radioGroup = alertView.findViewById(R.id.car_radio_group);
        RadioButton rechargeAli = alertView.findViewById(R.id.recharge_ali);
        RadioButton rechargeWeChat = alertView.findViewById(R.id.car_type_gasoline_vehicle);
        RadioButton rechargeElectronic = alertView.findViewById(R.id.car_type_new_energy_vehicle);

        TextView titleView = new TextView(this);
        titleView.setText("充值");
        titleView.getPaint().setFakeBoldText(true);
        titleView.setTextColor(Color.BLACK);
        titleView.setTextSize(20);
        titleView.setGravity(Gravity.CENTER);

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(alertView)
                .setCustomTitle(titleView)
                .setPositiveButton("充值", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String payMoney = moneyNumber.getText().toString();
                        if (selectType == -1) {
                            showSyncToast("支付失败, 请选择支付方式");
                            return;
                        } else if (TextUtils.isEmpty(payMoney)) {
                            showSyncToast("支付失败, 请输入充值金额");
                            return;
                        }
                        Map<String, Object> param = new HashMap<>();
                        param.put("money", Integer.parseInt(payMoney));
                        param.put("payType", payType[selectType]);
                        Api.config(Constant.NetWork.PARK_RECHARGE_PAY, param, ParkingLotReChargeActivity.this).postRequest(new RequestCallback() {
                            @Override
                            public void success(String result) {
                                BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                                if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                                    showSyncToast("充值成功");
                                    getReChargeList();
                                } else {
                                    showSyncToast(baseParam.getMsg());
                                }
                            }

                            @Override
                            public void failure(Exception e) {
                                showSyncToast("网络异常");
                            }
                        });
                        moneyNumber.setText("");
                        radioGroup.clearCheck();
                        selectType = -1;
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        moneyNumber.setText("");
                        radioGroup.clearCheck();
                        selectType = -1;
                    }
                })
                .create();


        rechargeBtn.setOnClickListener(view -> {
            alertDialog.show();
            moneyNumber.setFocusable(true);
            moneyNumber.setFocusableInTouchMode(true);
            moneyNumber.requestFocus();
            alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == rechargeAli.getId()) {
                    selectType = 2;
                } else if (checkedId == rechargeWeChat.getId()) {
                    selectType = 1;
                } else {
                    selectType = 0;
                }
            }
        });
    }
}