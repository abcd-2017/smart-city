package com.example.activity.busactivity;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
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
import com.example.adapter.busadapter.BusOrderListAdapter;
import com.example.pojo.BaseParam;
import com.example.pojo.busparam.BusOrderListParam;
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
public class BusOrderActivity extends BaseActivity {

    private ImageView finishBtn;
    private Toolbar busToolbar;
    private RecyclerView orderRecycler;
    private TextView orderPaid;
    private TextView orderUnPaid;
    private BusOrderListAdapter orderListAdapter;
    private Gson gson = new Gson();
    private List<BusOrderListParam.RowsDTO> paidList = new ArrayList<>(), unPaidList = new ArrayList<>();
    private boolean orderStatus = false;
    private TextView emptyText;
    private RadioGroup payType;
    private RadioButton payTypeWeChat, payTypeAli;
    private Integer payTypeNum = -1;
    private CardView orderCancel, orderOk;
    private View alertView;
    private String orderNum = null;

    @Override
    protected int initLayout() {
        return R.layout.activity_bus_order;
    }

    @Override
    protected void initView() {
        finishBtn = findViewById(R.id.finish_btn);
        busToolbar = findViewById(R.id.bus_toolbar);
        orderRecycler = findViewById(R.id.bus_order_list_recycler);
        orderPaid = findViewById(R.id.bus_order_paid);
        orderUnPaid = findViewById(R.id.bus_order_unpaid);
        emptyText = findViewById(R.id.empty_text);
        orderListAdapter = new BusOrderListAdapter();

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

        orderRecycler.setLayoutManager(new LinearLayoutManager(this));
        orderRecycler.setAdapter(orderListAdapter);

        getOrderList();

        orderUnPaid.setOnClickListener(view -> {
            orderStatus = false;
            setPaidStatus();
        });
        orderPaid.setOnClickListener(view -> {
            orderStatus = true;
            setPaidStatus();
        });

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(alertView).create();
        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.alert_bg_translate);
        alertDialog.setCancelable(false);

        //取消支付
        orderCancel.setOnClickListener(view -> {
            alertDialog.hide();
        });
        //确认支付
        orderOk.setOnClickListener(view -> {
            if (orderNum != null) {
                if (payTypeNum == -1) {
                    showSyncToast("未选择支付方式，支付失败");
                    return;
                }

                Map<String, Object> param = new HashMap<>();
                param.put("orderNum", orderNum);
                param.put("paymentType", Constant.PAY_TYPE[payTypeNum]);

                Api.config(Constant.NetWork.BUS_PAY, param, this).postRequest(new RequestCallback() {
                    @Override
                    public void success(String result) {
                        BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                        if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                            showSyncToast("支付成功");
                            getOrderList();
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

        orderListAdapter.setBusItemClickListener(rowsDTO -> {
            orderNum = rowsDTO.getOrderNum();
            alertDialog.show();
        });

        payType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == payTypeAli.getId()) {
                    payTypeNum = 0;
                } else if (checkedId == payTypeWeChat.getId()) {
                    payTypeNum = 1;
                } else {
                    payTypeNum = 3;
                }
            }
        });
    }

    //获取订单列表
    private void getOrderList() {
        Api.config(Constant.NetWork.BUS_ORDER_LIST, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                BusOrderListParam busOrderListParam = gson.fromJson(result, BusOrderListParam.class);
                if (busOrderListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<BusOrderListParam.RowsDTO> orderListParamRows = busOrderListParam.getRows();
                    paidList.clear();
                    unPaidList.clear();
                    for (BusOrderListParam.RowsDTO orderListParamRow : orderListParamRows) {
                        if (orderListParamRow.getStatus() == 0) {
                            paidList.add(orderListParamRow);
                        } else {
                            unPaidList.add(orderListParamRow);
                        }
                    }
                    runOnUiThread(() -> {
                        setPaidStatus();
                    });
                } else {
                    showSyncToast(busOrderListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
    }

    //设置订单状态样式
    private void setPaidStatus() {
        if (orderStatus) {
            changeTextStyle(orderUnPaid, orderPaid, paidList);
        } else {
            changeTextStyle(orderPaid, orderUnPaid, unPaidList);
        }
    }

    private void changeTextStyle(TextView orderPaid, TextView orderUnPaid, List<BusOrderListParam.RowsDTO> list) {
        orderUnPaid.setBackgroundColor(Color.rgb(251, 153, 104));
        orderUnPaid.setTextSize(16);
        orderUnPaid.setTextColor(Color.WHITE);
        orderUnPaid.getPaint().setFakeBoldText(true);

        orderPaid.setBackgroundColor(Color.WHITE);
        orderPaid.setTextSize(14);
        orderPaid.setTextColor(Color.GRAY);
        orderPaid.getPaint().setFakeBoldText(false);

        orderListAdapter.setData(list);

        if (list.size() > 0) {
            orderRecycler.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.GONE);
        } else {
            orderRecycler.setVisibility(View.GONE);
            emptyText.setVisibility(View.VISIBLE);
        }
    }
}