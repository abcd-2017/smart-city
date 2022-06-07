package com.example.activity.useractivity;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.adapter.AllOrderAdapter;
import com.example.pojo.AllOrderListParam;
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
public class AllOrderActivity extends BaseActivity {

    private Toolbar toolbar;
    private AllOrderAdapter orderAdapter;
    private RecyclerView orderRecycler;
    private SmartRefreshLayout orderRefresh;
    private Map<String, Object> param = new HashMap<>();
    private Gson gson = new Gson();
    private Integer pageNum = 1;
    private List<AllOrderListParam.RowsDTO> orderList = new ArrayList<>();
    private boolean hasNext = true;

    @Override
    protected int initLayout() {
        return R.layout.activity_all_order;
    }

    @Override
    protected void initView() {
        toolbar = findViewById(R.id.toolbar);
        orderRecycler = findViewById(R.id.all_order_recycler);
        orderRefresh = findViewById(R.id.all_order_refresh);
        orderAdapter = new AllOrderAdapter();
    }

    @Override
    protected void initData() {
        toolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        orderRecycler.setLayoutManager(new LinearLayoutManager(this));
        orderRecycler.setAdapter(orderAdapter);

        orderRefresh.setRefreshHeader(new BezierRadarHeader(this));
        orderRefresh.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Translate));

        orderRefresh.setOnRefreshListener(refreshLayout -> {
            pageNum = 1;
            orderList.clear();
            refreshLayout.autoRefresh(2000);
            hasNext = true;
            getOrderList(true);
        });

        orderRefresh.setOnLoadMoreListener(refreshLayout -> {
            pageNum++;
            refreshLayout.autoLoadMore(2000);
            getOrderList(false);
        });

        param.put("pageSize", Constant.pageSize);

        getOrderList(true);
    }

    private void getOrderList(boolean isRefresh) {
        param.put("pageNum", pageNum);
        Api.config(Constant.NetWork.ALL_ORDER, param, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                AllOrderListParam allOrderListParam = gson.fromJson(result, AllOrderListParam.class);
                if (allOrderListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<AllOrderListParam.RowsDTO> orderListParamRows = allOrderListParam.getRows();
                    if (orderListParamRows.size() > 0 && hasNext) {
                        if (isRefresh) {
                            orderRefresh.finishRefresh(1200);
                        } else {
                            orderRefresh.finishLoadMore(1200);
                        }
                        runOnUiThread(() -> {
                            new Thread(() -> {
                                try {
                                    Thread.sleep(1200);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            });
                            orderList.addAll(orderListParamRows);
                            orderAdapter.setData(orderList);
                        });
                    } else {
                        if (isRefresh) {
                            orderRefresh.finishRefresh(false);
                            showSyncToast("刷新失败");
                        } else {
                            orderRefresh.finishLoadMore(false);
                            showSyncToast("没有更多数据");
                        }
                    }
                    if (orderListParamRows.size() < Constant.pageSize) {
                        hasNext = false;
                    }
                } else {
                    showSyncToast(allOrderListParam.getMsg());
                    if (isRefresh) {
                        orderRefresh.finishRefresh(false);
                    } else {
                        orderRefresh.finishLoadMore(false);
                    }
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
                if (isRefresh) {
                    orderRefresh.finishRefresh(false);
                } else {
                    orderRefresh.finishLoadMore(false);
                }
            }
        });
    }
}