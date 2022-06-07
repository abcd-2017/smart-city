package com.example.activity.movieactivity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.adapter.moiveadapter.MovieTicketOrderListAdapter;
import com.example.pojo.movieparam.MovieOrderListParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;
import com.scwang.smart.refresh.footer.BallPulseFooter;
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
public class MovieTicketOrderActivity extends BaseActivity {

    private ImageView finishBtn;
    private Toolbar movieToolbar;
    private RecyclerView orderRecycler;
    private TextView emptyText;
    private MovieTicketOrderListAdapter ticketOrderListAdapter;
    private Gson gson = new Gson();
    private SmartRefreshLayout orderRefresh;
    private Integer pageNum = 0;
    private Map<String, Object> param = new HashMap<>();
    private boolean hasData = true;
    private List<MovieOrderListParam.RowsDTO> orderList = new ArrayList<>();

    @Override
    protected int initLayout() {
        return R.layout.activity_movie_ticket_order;
    }

    @Override
    protected void initView() {
        finishBtn = findViewById(R.id.finish_btn);
        movieToolbar = findViewById(R.id.movie_toolbar);
        orderRecycler = findViewById(R.id.movie_ticket_order_recycler);
        emptyText = findViewById(R.id.empty_text);
        orderRefresh = findViewById(R.id.movie_ticket_order_refresh);
        ticketOrderListAdapter = new MovieTicketOrderListAdapter();
    }

    @Override
    protected void initData() {
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });
        movieToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        orderRecycler.setLayoutManager(new LinearLayoutManager(this));
        orderRecycler.setAdapter(ticketOrderListAdapter);

        orderRefresh.setEnableRefresh(false);
        orderRefresh.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Translate));
        orderRefresh.setOnLoadMoreListener(refreshLayout -> {
            pageNum++;
            refreshLayout.autoLoadMore(2000);
            getOrderList();
        });


        ticketOrderListAdapter.setMovieOrderClickListener(id -> {

        });

        getOrderList();
    }

    private void getOrderList() {
        param.put("pageNum", pageNum);
        param.put("pageSize", Constant.pageSize);

        Api.config(Constant.NetWork.MOVIE_TICKET_ORDER_LIST, param, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                MovieOrderListParam movieOrderListParam = gson.fromJson(result, MovieOrderListParam.class);
                if (movieOrderListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<MovieOrderListParam.RowsDTO> orderListParamRows = movieOrderListParam.getRows();
                    runOnUiThread(() -> {
                        if (orderListParamRows.size() == 0 || !hasData) {
                            orderRefresh.finishLoadMore(true);
                            showSyncToast("没有更多数据了");
                        } else {
                            if (pageNum == movieOrderListParam.getTotal() / Constant.pageSize) {
                                pageNum--;
                                hasData = false;
                            }
                            if (orderList.size() > 0) {
                                new Thread(() -> {
                                    try {
                                        Thread.sleep(1200);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }).start();
                            }
                            orderList.addAll(orderListParamRows);
                            orderRefresh.finishLoadMore(true);
                        }
                        if (orderList.size() == 0) {
                            emptyText.setVisibility(View.VISIBLE);
                            orderRecycler.setVisibility(View.GONE);
                        } else {
                            ticketOrderListAdapter.setDate(orderList);
                        }
                    });
                } else {
                    showSyncToast(movieOrderListParam.getMsg());
                    orderRefresh.finishLoadMore(false);
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
                orderRefresh.finishLoadMore(false);
            }
        });
    }
}