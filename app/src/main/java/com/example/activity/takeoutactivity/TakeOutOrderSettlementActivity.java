package com.example.activity.takeoutactivity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.adapter.takeoutadapter.TakeOutSettlementAdapter;
import com.example.pojo.BaseParam;
import com.example.pojo.takeoutparam.TakeOutAddressListParam;
import com.example.pojo.takeoutparam.TakeOutOrderItemList;
import com.example.pojo.takeoutparam.TakeOutOrderResultParam;
import com.example.pojo.takeoutparam.TakeOutProductListParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kkk
 */
public class TakeOutOrderSettlementActivity extends BaseActivity {

    private Toolbar takeoutToolbar;
    private ImageView finishBtn;
    private CardView addAddressBtn, changeAddressBtn;
    private TextView addressDetail, addressName, addressPhone, sellerName, totalPrice;
    private RecyclerView settlementRecycler;
    private Button paymentBtn;
    private TakeOutAddressListParam.DataDTO address = null;
    private TakeOutSettlementAdapter settlementAdapter;
    private final Gson gson = new Gson();
    private View payView;
    private CardView dialogOkBtn, dialogCancelBtn;
    private RadioGroup payType;
    private RadioButton payTypeWechat, payTypeAli;
    private Integer selectPayType = -1;
    private Map<String, Object> param = new HashMap<>();
    private String orderNo;

    @Override

    protected int initLayout() {
        return R.layout.activity_take_out_order_settlement;
    }

    @Override
    protected void initView() {
        takeoutToolbar = findViewById(R.id.take_out_toolbar);
        finishBtn = findViewById(R.id.finish_btn);
        addAddressBtn = findViewById(R.id.take_out_settlement_add_address_btn);
        changeAddressBtn = findViewById(R.id.take_out_settlement_change_address_btn);
        addressDetail = findViewById(R.id.take_out_settlement_address_detail);
        addressName = findViewById(R.id.take_out_settlement_address_name);
        addressPhone = findViewById(R.id.take_out_settlement_address_phone);
        sellerName = findViewById(R.id.take_out_settlement_seller_name);
        totalPrice = findViewById(R.id.take_out_settlement_total_price);
        settlementRecycler = findViewById(R.id.take_out_settlement_recycler);
        paymentBtn = findViewById(R.id.take_out_settlement_payment_btn);
        settlementAdapter = new TakeOutSettlementAdapter();

        payView = LayoutInflater.from(this)
                .inflate(R.layout.alert_bus_pay_order, null, false);
        dialogOkBtn = payView.findViewById(R.id.alert_dialog_ok);
        dialogCancelBtn = payView.findViewById(R.id.alert_dialog_cancel);
        payType = payView.findViewById(R.id.bus_order_pay_type);
        payTypeAli = payView.findViewById(R.id.bus_pay_type_ali);
        payTypeWechat = payView.findViewById(R.id.bus_pay_type_wechat);
    }

    @Override
    protected void initData() {
        takeoutToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });

        settlementRecycler.setLayoutManager(new LinearLayoutManager(this));
        settlementRecycler.setAdapter(settlementAdapter);

        //获取订单列表
        String productListJson = getStringToSP("take_out_product_car");
        List<TakeOutProductListParam.DataDTO> productList = new ArrayList<>();
        JsonArray jsonArray = JsonParser.parseString(productListJson).getAsJsonArray();
        jsonArray.forEach(item -> {
            productList.add(gson.fromJson(item, TakeOutProductListParam.DataDTO.class));
        });

        //店铺名
        String orderSellerName = getStringToSP("take_out_seller_name");
        sellerName.setText(orderSellerName);

        //计算总价
        Double sumPrice = 0d;
        for (TakeOutProductListParam.DataDTO dataDTO : productList) {
            sumPrice += (dataDTO.getPrice() * dataDTO.getCount());
        }
        totalPrice.setText(String.format("%.2f", sumPrice));

        //设置商品列表
        settlementAdapter.setData(productList);

        //选择地址回调函数
        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            String addressJson = (String) result.getData().getExtras().get("address");
            if ("null".equals(addressJson)) {
                address = null;
            } else {
                address = gson.fromJson(addressJson, TakeOutAddressListParam.DataDTO.class);
            }
            setAddress();
        });

        //更换地址点击事件
        changeAddressBtn.setOnClickListener(view -> {
            launcher.launch(new Intent(this, TakeOutAddressActivity.class)
                    .putExtra("addressType", "select"));
        });
        //添加地址
        addAddressBtn.setOnClickListener(view -> {
            launcher.launch(new Intent(this, TakeOutAddressActivity.class)
                    .putExtra("addressType", "select"));
        });

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(payView)
                .create();
        alertDialog.getWindow().setBackgroundDrawable(AppCompatResources.getDrawable(this, R.drawable.alert_bg_translate));
        alertDialog.setCancelable(false);

        //弹窗按钮监听事件
        dialogCancelBtn.setOnClickListener(view -> {
            alertDialog.hide();
        });
        dialogOkBtn.setOnClickListener(view -> {
            if (selectPayType == -1) {
                showSyncToast("请选择支付方式");
                return;
            }
            param.clear();
            param.put("orderNo", orderNo);
            param.put("paymentType", Constant.PAY_TYPE[selectPayType]);

            Api.config(Constant.NetWork.TAKE_OUT_PAY, param, TakeOutOrderSettlementActivity.this).postRequest(new RequestCallback() {
                @Override
                public void success(String result) {
                    BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                    if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                        alertDialog.dismiss();
                        jumpPageFlag(TakeOutActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        showSyncToast("支付成功");
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

        payType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == payTypeAli.getId()) {
                    selectPayType = 0;
                } else if (checkedId == payTypeWechat.getId()) {
                    selectPayType = 1;
                } else {
                    selectPayType = 2;
                }
            }
        });

        //结算
        Double finalSumPrice = sumPrice;
        paymentBtn.setOnClickListener(view -> {
            if (address == null) {
                showSyncToast("请选择收货地址");
                return;
            }
            List<TakeOutOrderItemList> orderList = new ArrayList<>();
            for (TakeOutProductListParam.DataDTO dataDTO : productList) {
                orderList.add(new TakeOutOrderItemList(dataDTO.getId(), dataDTO.getCount()));
            }
            param.put("addressDetail", address.getAddressDetail());
            param.put("label", address.getLabel());
            param.put("name", address.getName());
            param.put("phone", address.getPhone());
            param.put("amount", finalSumPrice);
            param.put("sellerId", getStringToSP("take_out_seller_id"));
            param.put("orderItemList", orderList);

            Api.config(Constant.NetWork.TAKE_OUT_ORDER_CREATE, param, this).postRequest(new RequestCallback() {
                @Override
                public void success(String result) {
                    TakeOutOrderResultParam orderResultParam = gson.fromJson(result, TakeOutOrderResultParam.class);
                    if (orderResultParam.getCode() == HttpURLConnection.HTTP_OK) {
                        orderNo = orderResultParam.getOrderNo();
                    } else {
                        showSyncToast(orderResultParam.getMsg());
                    }
                }

                @Override
                public void failure(Exception e) {
                    showSyncToast("网络异常");
                }
            });
            alertDialog.show();
        });
    }

    //设置页面地址信息
    private void setAddress() {
        if (address == null) {
            addAddressBtn.setVisibility(View.VISIBLE);
            changeAddressBtn.setVisibility(View.GONE);
        } else {
            addAddressBtn.setVisibility(View.GONE);
            changeAddressBtn.setVisibility(View.VISIBLE);
            addressDetail.setText(address.getAddressDetail());
            addressName.setText(address.getName());
            addressPhone.setText(address.getPhone());
        }
    }
}