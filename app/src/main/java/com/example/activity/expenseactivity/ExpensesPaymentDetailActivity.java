package com.example.activity.expenseactivity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.pojo.BaseParam;
import com.example.pojo.expenses.ExpensesPaymentDetailParam;
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
public class ExpensesPaymentDetailActivity extends BaseActivity {

    private Toolbar expensesToolbar;
    private ImageView finishBtn;
    private CardView paymentBtn;
    private TextView paymentDetailAddress, paymentDetailCompany, paymentDetailMoney, paymentDetailHostName, paymentDetailNum, paymentDetailStatus, paymentDetailTitle, paymentDetailType;
    private Gson gson = new Gson();
    private Map<String, Object> param = new HashMap<>();
    private View alertView;
    private RadioGroup payType;
    private RadioButton typeWeChat, typeAli;
    private CardView orderOkBtn, orderCancelBtn;
    private Integer selectType = -1;
    private String paramDataBillNo;

    @Override
    protected int initLayout() {
        return R.layout.activity_expenses_payment_detail;
    }

    @Override
    protected void initView() {
        expensesToolbar = findViewById(R.id.expenses_toolbar);
        finishBtn = findViewById(R.id.finish_btn);
        paymentBtn = findViewById(R.id.expenses_payment_detail_pay_btn);
        paymentDetailAddress = findViewById(R.id.expenses_payment_detail_address);
        paymentDetailCompany = findViewById(R.id.expenses_payment_detail_company);
        paymentDetailMoney = findViewById(R.id.expenses_payment_detail_money);
        paymentDetailHostName = findViewById(R.id.expenses_payment_detail_host_name);
        paymentDetailNum = findViewById(R.id.expenses_payment_detail_num);
        paymentDetailStatus = findViewById(R.id.expenses_payment_detail_status);
        paymentDetailTitle = findViewById(R.id.expenses_payment_detail_title);
        paymentDetailType = findViewById(R.id.expenses_payment_detail_type);
        alertView = LayoutInflater.from(this)
                .inflate(R.layout.alert_bus_pay_order, null, false);
        payType = alertView.findViewById(R.id.bus_order_pay_type);
        typeWeChat = alertView.findViewById(R.id.bus_pay_type_wechat);
        typeAli = alertView.findViewById(R.id.bus_pay_type_ali);
        orderOkBtn = alertView.findViewById(R.id.alert_dialog_ok);
        orderCancelBtn = alertView.findViewById(R.id.alert_dialog_cancel);
    }

    @Override
    protected void initData() {
        expensesToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });

        String paymentNo = (String) getIntent().getExtras().get("paymentNo");
        String categoryId = getStringToSP("expenses_category_id");

        setPaymentDetail(paymentNo, categoryId);

        //初始化自定义弹窗
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(alertView)
                .create();
        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.alert_bg_translate);

        payType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == typeWeChat.getId()) {
                    selectType = 1;
                } else if (checkedId == typeAli.getId()) {
                    selectType = 0;
                } else {
                    selectType = 2;
                }
            }
        });

        orderOkBtn.setOnClickListener(view -> {
            if (selectType == -1) {
                showSyncToast("支付失败，请选择支付方式");
                return;
            }
            param.clear();
            param.put("billNo", paramDataBillNo);
            param.put("paymentType", Constant.PAY_TYPE[selectType]);
            param.put("paymentNo", 15674939);

            Api.config(Constant.NetWork.LIVING_RECHARGE, param, this).postRequest(new RequestCallback() {
                @Override
                public void success(String result) {
                    BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                    if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                        showSyncToast("缴费成功");
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

        orderCancelBtn.setOnClickListener(view -> {
            alertDialog.hide();
        });

        paymentBtn.setOnClickListener(view -> {
            alertDialog.show();
        });
    }

    private void setPaymentDetail(String paymentNo, String categoryId) {
        param.clear();
//        param.put("paymentNo", paymentNo);
        param.put("paymentNo", 15674939);
        param.put("categoryId", categoryId);
        Api.config(Constant.NetWork.LIVING_BILL_LIST, param, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                ExpensesPaymentDetailParam paymentDetailParam = gson.fromJson(result, ExpensesPaymentDetailParam.class);
                if (paymentDetailParam.getCode() == HttpURLConnection.HTTP_OK) {
                    ExpensesPaymentDetailParam.DataDTO detailParamData = paymentDetailParam.getData();
                    runOnUiThread(() -> {
                        paymentDetailAddress.setText(detailParamData.getAddress());
                        paymentDetailMoney.setText("￥" + detailParamData.getAmount());
                        paymentDetailCompany.setText(detailParamData.getChargeUnit());
                        paymentDetailHostName.setText("admin");
                        paymentDetailNum.setText(detailParamData.getBillNo());
                        paymentDetailStatus.setText("0".equals(detailParamData.getPayStatus()) ? "未支付" : "已支付");
                        paymentDetailTitle.setText(detailParamData.getTitle());
                        paymentDetailType.setText(Constant.EXPENSES_PAY_TYPE[detailParamData.getCategoryId() - 1]);
                        paramDataBillNo = detailParamData.getBillNo();
                    });
                } else {
                    showSyncToast(paymentDetailParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
    }
}