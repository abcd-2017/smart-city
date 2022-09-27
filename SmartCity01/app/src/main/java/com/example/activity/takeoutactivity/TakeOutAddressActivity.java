package com.example.activity.takeoutactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.adapter.takeoutadapter.TakeOutAddressListAdapter;
import com.example.pojo.takeoutparam.TakeOutAddressListParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.util.List;

/**
 * @author kkk
 */
public class TakeOutAddressActivity extends BaseActivity {

    private Toolbar takeOutToolbar;
    private ImageView finishBtn;
    private RecyclerView addressRecycler;
    private LinearLayout addAddressBtn;
    private TextView emptyText;
    private TakeOutAddressListAdapter addressListAdapter;
    private Gson gson = new Gson();
    private List<TakeOutAddressListParam.DataDTO> addressListParamData;

    @Override
    protected int initLayout() {
        return R.layout.activity_take_out_address;
    }

    @Override
    protected void initView() {
        takeOutToolbar = findViewById(R.id.take_out_toolbar);
        finishBtn = findViewById(R.id.finish_btn);
        addressRecycler = findViewById(R.id.take_out_address_recycler);
        addAddressBtn = findViewById(R.id.take_out_address_add_address_btn);
        emptyText = findViewById(R.id.empty_text);
        addressListAdapter = new TakeOutAddressListAdapter();
    }

    @Override
    protected void initData() {
        takeOutToolbar.setNavigationOnClickListener(view -> {
            finish();
        });
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });

        addressRecycler.setLayoutManager(new LinearLayoutManager(this));
        addressRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        addressRecycler.setAdapter(addressListAdapter);

        //点击添加收货地址
        addAddressBtn.setOnClickListener(view -> {
            jumpPageToIntent(new Intent(this, TakeOutAddAddressActivity.class)
                    .putExtra("addressType", "add"));
        });

        getAddressList();

        Bundle extras = getIntent().getExtras();

        addressListAdapter.setListEditClickListener(new TakeOutAddressListAdapter.AddressListEditClickListener() {
            @Override
            public void editClick(Integer id) {
                jumpPageToIntent(new Intent(TakeOutAddressActivity.this, TakeOutAddAddressActivity.class)
                        .putExtra("addressType", "edit")
                        .putExtra("addressId", id));
            }

            @Override
            public void selectItem(TakeOutAddressListParam.DataDTO dataDTO) {
                if (extras == null) return;
                String addressType = (String) extras.get("addressType");
                if ("select".equals(addressType)) {
                    Intent addressIntent = new Intent(TakeOutAddressActivity.this, TakeOutOrderSettlementActivity.class)
                            .putExtra("address", gson.toJson(dataDTO));
                    setResult(RESULT_OK, addressIntent);
                    finish();
                }
            }
        });
    }

    private void getAddressList() {
        Api.config(Constant.NetWork.TAKE_OUT_ADDRESS_LIST, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                TakeOutAddressListParam addressListParam = gson.fromJson(result, TakeOutAddressListParam.class);
                if (addressListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    addressListParamData = addressListParam.getData();
                    runOnUiThread(() -> {
                        if (addressListParamData.size() > 0) {
                            addressListAdapter.setData(addressListParamData);
                            emptyText.setVisibility(View.GONE);
                            addressRecycler.setVisibility(View.VISIBLE);
                        } else {
                            emptyText.setVisibility(View.VISIBLE);
                            addressRecycler.setVisibility(View.GONE);
                        }
                    });
                } else {
                    showSyncToast(addressListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAddressList();
    }

    @Override
    public void finish() {
        super.finish();
        if (addressListParamData.size() == 0) {
            setResult(RESULT_OK, new Intent(this, TakeOutSellerDetailActivity.class)
                    .putExtra("address", "null"));
        }
    }
}