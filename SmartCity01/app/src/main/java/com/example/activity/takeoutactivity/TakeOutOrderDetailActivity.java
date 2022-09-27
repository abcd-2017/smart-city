package com.example.activity.takeoutactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.adapter.takeoutadapter.TakeOutOrderDetailProductListAdapter;
import com.example.pojo.UserInfoParam;
import com.example.pojo.takeoutparam.TakeOutOrderDetailListParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.util.List;

/**
 * @author kkk
 */
public class TakeOutOrderDetailActivity extends BaseActivity {

    private Toolbar takeOutToolbar;
    private ImageView finishBtn, detailCover;
    private RecyclerView detailProductRecycler;
    private TextView sellerName, totalPrice, detailOrderNo, detailUserInfo, detailAddress, detailTime, detailPayType;
    private TakeOutOrderDetailProductListAdapter productListAdapter;
    private Gson gson = new Gson();

    @Override
    protected int initLayout() {
        return R.layout.activity_take_out_order_detail;
    }

    @Override
    protected void initView() {
        takeOutToolbar = findViewById(R.id.take_out_toolbar);
        finishBtn = findViewById(R.id.finish_btn);
        detailCover = findViewById(R.id.take_out_order_detail_cover);
        detailProductRecycler = findViewById(R.id.take_out_order_detail_recycler);
        sellerName = findViewById(R.id.take_out_order_detail_seller_name);
        totalPrice = findViewById(R.id.take_out_order_detail_total_price);
        detailOrderNo = findViewById(R.id.take_out_order_detail_order_po);
        detailUserInfo = findViewById(R.id.take_out_order_detail_user_info);
        detailAddress = findViewById(R.id.take_out_order_detail_address);
        detailTime = findViewById(R.id.take_out_order_detail_time);
        detailPayType = findViewById(R.id.take_out_order_detail_pay_type);
        productListAdapter = new TakeOutOrderDetailProductListAdapter();
    }

    @Override
    protected void initData() {
        takeOutToolbar.setNavigationOnClickListener(view -> {
            finish();
        });
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });

        Bundle extras = getIntent().getExtras();
        String orderNo = (String) extras.get("orderNo");

        detailProductRecycler.setLayoutManager(new LinearLayoutManager(this));
        detailProductRecycler.setAdapter(productListAdapter);

        Api.config(Constant.NetWork.TAKE_OUT_ORDER, null, this).getRestfulRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                TakeOutOrderDetailListParam detailListParam = gson.fromJson(result, TakeOutOrderDetailListParam.class);
                if (detailListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    TakeOutOrderDetailListParam.DataDTO.SellerInfoDTO sellerInfo = detailListParam.getData().getSellerInfo();
                    TakeOutOrderDetailListParam.DataDTO.OrderInfoDTO orderInfo = detailListParam.getData().getOrderInfo();
                    List<TakeOutOrderDetailListParam.DataDTO.OrderInfoDTO.OrderItemListDTO> orderItemList = orderInfo.getOrderItemList();

                    runOnUiThread(() -> {
                        productListAdapter.setData(orderItemList);

                        sellerName.setText(sellerInfo.getName());
                        Glide.with(TakeOutOrderDetailActivity.this)
                                .load(Constant.BASE_API + sellerInfo.getImgUrl())
                                .transform(new CircleCrop())
                                .into(detailCover);
                        totalPrice.setText(String.format("￥ %s", orderInfo.getAmount()));
                        detailOrderNo.setText(orderInfo.getOrderNo());
                        detailTime.setText(sellerInfo.getCreateTime());
                        detailPayType.setText(orderInfo.getPaymentType());
                        detailAddress.setText(sellerInfo.getAddress());
                        detailUserInfo.setText(String.format("%s %s", orderInfo.getReceiverName(), orderInfo.getReceiverPhone()));
                    });
                } else {
                    showSyncToast(detailListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        }, orderNo);
    }
}