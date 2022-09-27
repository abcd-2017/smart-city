package com.example.activity.takeoutactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.adapter.takeoutadapter.TakeOutSellerListAdapter;
import com.example.adapter.takeoutadapter.TakeOutSellerProductAdapter;
import com.example.pojo.takeoutparam.TakeOutSellerListParam;
import com.example.pojo.takeoutparam.TakeoutSellerProductListParam;
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
public class TakeOutSearchSellerActivity extends BaseActivity {

    private Toolbar takeOutToolbar;
    private ImageView finishBtn;
    private EditText searchSeller;
    private RecyclerView sellerRecycler;
    private TakeOutSellerListAdapter outSellerListAdapter;
    private Gson gson = new Gson();
    private Map<String, Object> param = new HashMap<>();
    private boolean searchType;
    private TextView emptyText;
    private String searchContent = null;
    private TakeOutSellerProductAdapter sellerProductListAdapter;

    @Override
    protected int initLayout() {
        return R.layout.activity_take_out_search_seller;
    }

    @Override
    protected void initView() {
        takeOutToolbar = findViewById(R.id.take_out_toolbar);
        finishBtn = findViewById(R.id.finish_btn);
        searchSeller = findViewById(R.id.take_out_search_seller);
        sellerRecycler = findViewById(R.id.take_out_seller_product_recycler);
        emptyText = findViewById(R.id.empty_text);
        sellerProductListAdapter = new TakeOutSellerProductAdapter();
        outSellerListAdapter = new TakeOutSellerListAdapter();
    }

    @Override
    protected void initData() {
        takeOutToolbar.setNavigationOnClickListener(view -> {
            finish();
        });
        finishBtn.setOnClickListener(view -> jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP));

        Bundle extras = getIntent().getExtras();
        searchType = (boolean) extras.get("searchType");

        param.put("pageSize", Constant.pageSize);

        //判读当前页面是否为搜索
        if (searchType) {
            emptyText.setVisibility(View.VISIBLE);
            sellerRecycler.setVisibility(View.GONE);
            sellerRecycler.setLayoutManager(new LinearLayoutManager(this));
            sellerRecycler.setAdapter(sellerProductListAdapter);

            searchSeller.setFocusable(true);
            searchSeller.requestFocus();
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

            sellerProductListAdapter.setProductItemClickListener(productListDTO -> {
                jumpPageToIntent(new Intent(this, TakeOutSellerDetailActivity.class)
                        .putExtra("sellerId", productListDTO.getSellerId())
                        .putExtra("categoryId", productListDTO.getCategoryId()));
            });
        } else {
            searchSeller.setHint("搜索店铺");
            Integer themeId = (Integer) extras.get("themeId");
            param.put("themeId", themeId);

            sellerRecycler.setLayoutManager(new LinearLayoutManager(this));
            sellerRecycler.setAdapter(outSellerListAdapter);

            searchListByTheme();

            outSellerListAdapter.setSellerItemClickListener(id -> {
                jumpPageToIntent(new Intent(this, TakeOutSellerDetailActivity.class)
                        .putExtra("sellerId", id));
            });
        }

        //输入框监听事件
        searchSeller.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                searchContent = searchSeller.getText().toString();
                if (searchType) {
                    searchList();
                } else {
                    searchListByTheme();
                }
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                return true;
            }
        });
    }

    //分类搜索
    private void searchListByTheme() {
        if (!TextUtils.isEmpty(searchContent)) {
            param.put("name", searchContent);
        }
        Api.config(Constant.NetWork.TAKE_OUT_SELLER_LIST, param, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                TakeOutSellerListParam outSellerListParam = gson.fromJson(result, TakeOutSellerListParam.class);
                if (outSellerListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<TakeOutSellerListParam.RowsDTO> sellerListParamRows = outSellerListParam.getRows();
                    runOnUiThread(() -> {
                        outSellerListAdapter.setData(sellerListParamRows);
                        if (sellerListParamRows.size() == 0) {
                            sellerRecycler.setVisibility(View.GONE);
                            emptyText.setVisibility(View.VISIBLE);
                        } else {
                            sellerRecycler.setVisibility(View.VISIBLE);
                            emptyText.setVisibility(View.GONE);
                        }
                    });
                } else {
                    showSyncToast(outSellerListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
    }

    //搜索
    private void searchList() {
        param.put("keyword", searchContent);
        Api.config(Constant.NetWork.TAKE_OUT_SEARCH, param, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                TakeoutSellerProductListParam sellerProductListParam = gson.fromJson(result, TakeoutSellerProductListParam.class);
                if (sellerProductListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<TakeoutSellerProductListParam.RowsDTO> productListParamRows = sellerProductListParam.getRows();
                    runOnUiThread(() -> {
                        sellerProductListAdapter.setData(productListParamRows);
                        if (productListParamRows.size() == 0) {
                            sellerRecycler.setVisibility(View.GONE);
                            emptyText.setVisibility(View.VISIBLE);
                        } else {
                            sellerRecycler.setVisibility(View.VISIBLE);
                            emptyText.setVisibility(View.GONE);
                        }
                    });
                } else {
                    showSyncToast(sellerProductListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
    }

}