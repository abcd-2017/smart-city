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
import com.example.adapter.moiveadapter.MovieTicketListAdapter;
import com.example.pojo.movieparam.MovieTicketListParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.util.List;

/**
 * @author kkk
 */
public class MovieTicketActivity extends BaseActivity {

    private MovieTicketListAdapter ticketListAdapter;
    private ImageView finishBtn;
    private Toolbar movieToolbar;
    private RecyclerView ticketRecycler;
    private Gson gson = new Gson();
    private TextView emptyText;

    @Override
    protected int initLayout() {
        return R.layout.activity_movie_ticket;
    }

    @Override
    protected void initView() {
        finishBtn = findViewById(R.id.finish_btn);
        movieToolbar = findViewById(R.id.movie_toolbar);
        ticketRecycler = findViewById(R.id.movie_ticket_recycler);
        ticketListAdapter = new MovieTicketListAdapter();
        emptyText = findViewById(R.id.empty_text);
    }

    @Override
    protected void initData() {
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });
        movieToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        ticketRecycler.setLayoutManager(new LinearLayoutManager(this));
        ticketRecycler.setAdapter(ticketListAdapter);

        Api.config(Constant.NetWork.MOVIE_TICKET_LIST, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                MovieTicketListParam ticketListParam = gson.fromJson(result, MovieTicketListParam.class);
                if (ticketListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<MovieTicketListParam.RowsDTO> ticketListParamRows = ticketListParam.getRows();
                    runOnUiThread(() -> {
                        ticketListAdapter.setData(ticketListParamRows);
                        if (ticketListParamRows.size() == 0) {
                            emptyText.setVisibility(View.VISIBLE);
                            ticketRecycler.setVisibility(View.GONE);
                        }
                    });
                } else {
                    showSyncToast(ticketListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
    }
}