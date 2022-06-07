package com.example.activity.expenseactivity;

import android.content.Intent;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.adapter.expensesadapter.ExpensesReCordListAdapter;
import com.example.pojo.expenses.ExpensesPayReCordListParam;
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
public class ExpensesReCordActivity extends BaseActivity {


    private Toolbar expensesToolbar;
    private ImageView finishBtn;
    private RecyclerView recordRecycler;
    private ExpensesReCordListAdapter reCordListAdapter;
    private Map<String, Object> param = new HashMap<>();
    private Integer currCategoryId = 2;

    @Override
    protected int initLayout() {
        return R.layout.activity_expenses_re_cord;
    }

    @Override
    protected void initView() {
        expensesToolbar = findViewById(R.id.expenses_toolbar);
        finishBtn = findViewById(R.id.finish_btn);
        recordRecycler = findViewById(R.id.expenses_record_recycler);
        reCordListAdapter = new ExpensesReCordListAdapter();
    }

    @Override
    protected void initData() {
        expensesToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });

        recordRecycler.setLayoutManager(new LinearLayoutManager(this));
        recordRecycler.setAdapter(reCordListAdapter);

        getReCordList();
    }

    private void getReCordList() {
        param.put("categoryId", currCategoryId);

        Api.config(Constant.NetWork.LIVING_RECHARGE_LIST, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                ExpensesPayReCordListParam payReCordListParam = gson.fromJson(result, ExpensesPayReCordListParam.class);
                if (payReCordListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<ExpensesPayReCordListParam.RowsDTO> reCordListParamRows = payReCordListParam.getRows();
                    runOnUiThread(() -> {
                        reCordListAdapter.setData(reCordListParamRows);
                    });
                } else {
                    showSyncToast(payReCordListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
    }
}