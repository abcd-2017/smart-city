package com.example.activity.metroactivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.text.Html;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.adapter.NoticeListAdapter;
import com.example.pojo.NoticeListParam;
import com.example.pojo.StatementParam;
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
public class AnnouncementActivity extends BaseActivity {

    private Toolbar metroToolbar;
    private ImageView finishBtn;
    private RecyclerView noticeRecycler;
    private CardView disclaimers, cardNotice, passengerNotice;
    private NoticeListAdapter noticeListAdapter;
    private AlertDialog alertDialog;
    private List<NoticeListParam.RowsDTO> noticeListParamRows;
    private String[] titles;

    @Override
    protected int initLayout() {
        return R.layout.activity_announcement;
    }

    @Override
    protected void initView() {
        metroToolbar = findViewById(R.id.metro_toolbar);
        finishBtn = findViewById(R.id.finish_btn);
        passengerNotice = findViewById(R.id.passenger_notice);
        cardNotice = findViewById(R.id.boarding_card_notice);
        disclaimers = findViewById(R.id.disclaimers);
        noticeRecycler = findViewById(R.id.notice_recycler);
        noticeListAdapter = new NoticeListAdapter();
        titles = new String[]{"乘车须知", "乘车卡通知", "免责声明"};
    }

    @Override
    protected void initData() {
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });
        metroToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        noticeRecycler.setLayoutManager(new LinearLayoutManager(this));
        noticeRecycler.setAdapter(noticeListAdapter);
        noticeRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        Api.config(Constant.NetWork.METRO_NOTICE_LIST, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                NoticeListParam noticeListParam = gson.fromJson(result, NoticeListParam.class);
                if (noticeListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    noticeListParamRows = noticeListParam.getRows();
                    runOnUiThread(() -> {
                        noticeListAdapter.setData(noticeListParamRows);
                    });
                } else {
                    showSyncToast(noticeListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });

        alertDialog = new AlertDialog.Builder(this)
                .setPositiveButton("确认", null).create();

        noticeListAdapter.setItemClickListener(id -> {
            NoticeListParam.RowsDTO rowsDTO = noticeListParamRows.get(id);
            alertDialog.setMessage(Html.fromHtml(rowsDTO.getContent(), Html.FROM_HTML_MODE_COMPACT));
            alertDialog.setTitle("公告");
            alertDialog.show();
        });

        passengerNotice.setOnClickListener(view -> {
            getStatement(1);
        });
        cardNotice.setOnClickListener(view -> {
            getStatement(2);
        });
        disclaimers.setOnClickListener(view -> {
            getStatement(3);
        });
    }

    private void getStatement(int num) {
        Map<String, Object> param = new HashMap<>();
        param.put("type", num);
        Api.config(Constant.NetWork.METRO_NOTICE_STATEMENT, param, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                StatementParam statementParam = gson.fromJson(result, StatementParam.class);
                if (statementParam.getCode() == HttpURLConnection.HTTP_OK) {
                    alertDialog.setMessage(Html.fromHtml(statementParam.getData().getContent(), Html.FROM_HTML_MODE_LEGACY));
                    alertDialog.setTitle(titles[num - 1]);
                    runOnUiThread(() -> {
                        alertDialog.show();
                    });
                } else {
                    showSyncToast(statementParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
    }
}