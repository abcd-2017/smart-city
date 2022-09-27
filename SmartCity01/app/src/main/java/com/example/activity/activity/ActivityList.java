package com.example.activity.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.adapter.activityadapter.ActivityListAdapter;
import com.example.pojo.activityparam.ActivityListParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.header.BezierRadarHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kkk
 */
public class ActivityList extends BaseActivity {

    private static final String TAG = "ActivityList";
    private RecyclerView serviceRecycler;
    private EditText searchEdit;
    private Integer categoryId;
    private ActivityListAdapter listAdapter;
    private SmartRefreshLayout serviceRefresh;
    private Integer pageNum = 1;
    private Map<String, Object> param = new HashMap<>();
    private List<ActivityListParam.RowsDTO> list = new ArrayList<>();
    private boolean hasNext = true;
    private Toolbar activityToolbar;

    @Override
    protected int initLayout() {
        return R.layout.activity_list;
    }

    @Override
    protected void initView() {
        serviceRecycler = findViewById(R.id.all_activity_list_recycler);
        searchEdit = findViewById(R.id.search_activity_name);
        serviceRefresh = findViewById(R.id.all_service_refresh);
        activityToolbar = findViewById(R.id.all_activity_toolbar);
        listAdapter = new ActivityListAdapter();
    }

    @Override
    protected void initData() {
        Bundle extras = getIntent().getExtras();
        categoryId = (Integer) extras.get("categoryId");

        activityToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        serviceRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        serviceRecycler.setLayoutManager(new LinearLayoutManager(this));
        serviceRecycler.setAdapter(listAdapter);

        serviceRefresh.setRefreshHeader(new BezierRadarHeader(this).setEnableHorizontalDrag(true));
        serviceRefresh.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Translate));

        serviceRefresh.setOnRefreshListener(refresh -> {
            pageNum = 1;
            hasNext = true;
            getServiceList(true);
            refresh.finishRefresh(2000);
        });

        serviceRefresh.setOnLoadMoreListener(refresh -> {
            pageNum++;
            getServiceList(false);
            refresh.finishLoadMore(20000);
        });

        getServiceList(true);

        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                getServiceList(true);
                return false;
            }
        });

        listAdapter.setItemClickListener(id -> {
            jumpPageToIntent(new Intent(this, ActivityDetailsActivity.class).putExtra("ActivityId", id));
        });
    }

    public void getServiceList(boolean isRefresh) {
        param.clear();
        param.put("categoryId", categoryId);
        param.put("name", searchEdit.getText().toString());
        param.put("recommentd", "N");
        param.put("pageNum", pageNum);
        param.put("pageSize", Constant.pageSize);

        Log.d(TAG, "getServiceList: param => " + param);

        Api.config(Constant.NetWork.ACTIVITY_LIST, param, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                ActivityListParam activityListParam = gson.fromJson(result, ActivityListParam.class);
                if (activityListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<ActivityListParam.RowsDTO> listParamRows = activityListParam.getRows();
                    if (listParamRows.size() > 0 && hasNext) {
                        if (isRefresh) {
                            list.clear();
                        } else {
                            try {
                                Thread.sleep(1500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            serviceRefresh.finishLoadMore();
                        }
                        list.addAll(listParamRows);
                        runOnUiThread(() -> {
                            listAdapter.setData(list);
                        });
                    } else {
                        if (isRefresh) {
//                            showSyncToast("加载失败");
                        } else {
                            try {
                                Thread.sleep(1500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            serviceRefresh.finishLoadMore();
                            showSyncToast("没有更多数据了");
                        }
                    }
                    if (listParamRows.size() < Constant.pageSize) {
                        hasNext = false;
                    }
                } else {
                    showSyncToast(activityListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
                if (isRefresh) {
                    serviceRefresh.finishRefresh(true);
                } else {
                    serviceRefresh.finishLoadMore(true);
                }
            }
        });
    }
}