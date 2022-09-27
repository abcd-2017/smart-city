package com.example.activity.expenseactivity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
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
import com.example.adapter.expensesadapter.ExpensesPaymentListAdapter;
import com.example.pojo.User;
import com.example.pojo.UserInfoParam;
import com.example.pojo.expenses.ExpensesPaymentListParam;
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
public class ExpensesPaymentListActivity extends BaseActivity {

    private Toolbar expensesToolbar;
    private ImageView finishBtn;
    private RecyclerView paymentListRecycler;
    private Gson gson = new Gson();
    private String userIdCard = "210113199808242137";
    private ExpensesPaymentListAdapter paymentListAdapter;
    private String categoryId;
    private TextView emptyText;
    private EditText searchIdCard;

    @Override
    protected int initLayout() {
        return R.layout.activity_expenses_payment_list;
    }

    @Override
    protected void initView() {
        expensesToolbar = findViewById(R.id.expenses_toolbar);
        finishBtn = findViewById(R.id.finish_btn);
        paymentListRecycler = findViewById(R.id.expenses_payment_list_recycler);
        searchIdCard = findViewById(R.id.expenses_payment_search_idCard);
        emptyText = findViewById(R.id.empty_text);
        paymentListAdapter = new ExpensesPaymentListAdapter();
    }

    @Override
    protected void initData() {
        expensesToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });

        paymentListRecycler.setLayoutManager(new LinearLayoutManager(this));
        paymentListRecycler.setAdapter(paymentListAdapter);

        categoryId = getStringToSP("expenses_category_id");

//        userIdCard = getStringToSP("user_idCard");
        if (TextUtils.isEmpty(userIdCard)) {
            getUserInfo();
        } else {
            getPaymentList(userIdCard);
        }

        searchIdCard.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String searchId = searchIdCard.getText().toString();
                if (!TextUtils.isEmpty(searchId)) {
                    getPaymentList(searchId);
                } else {
                    getPaymentList(userIdCard);
                }
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                return true;
            }
        });

        paymentListAdapter.setPaymentItemClickListener(paymentNo -> {
            jumpPageToIntent(new Intent(this, ExpensesPaymentDetailActivity.class)
                    .putExtra("paymentNo", paymentNo));
        });
    }

    private void getUserInfo() {
        Api.config(Constant.NetWork.GETINFO, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                UserInfoParam userInfoParam = gson.fromJson(result, UserInfoParam.class);
                if (userInfoParam.getCode() == HttpURLConnection.HTTP_OK) {
                    User user = userInfoParam.getUser();
                    userIdCard = user.getIdCard();
                    getPaymentList(userIdCard);
                } else {
                    showSyncToast(userInfoParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
    }

    private void getPaymentList(String userIdCard) {
        Map<String, Object> param = new HashMap<>();
        param.put("categoryId", Integer.parseInt(categoryId));
        param.put("idCard", userIdCard);

        Api.config(Constant.NetWork.LIVING_ACCOUNT_LIST, param, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                ExpensesPaymentListParam paymentListParam = gson.fromJson(result, ExpensesPaymentListParam.class);
                if (paymentListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<ExpensesPaymentListParam.DataDTO> listParamData = paymentListParam.getData();
                    runOnUiThread(() -> {
                        if (listParamData.size() > 0) {
                            paymentListAdapter.setData(listParamData);
                            emptyText.setVisibility(View.GONE);
                            paymentListRecycler.setVisibility(View.VISIBLE);
                        } else {
                            paymentListRecycler.setVisibility(View.GONE);
                            emptyText.setVisibility(View.VISIBLE);
                        }
                    });
                } else {
                     showSyncToast(paymentListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
    }
}