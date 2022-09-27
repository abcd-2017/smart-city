package com.example.activity.workactivity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.adapter.workadapter.WorkDeliverListAdapter;
import com.example.pojo.workparam.WorkDeliverListParam;
import com.example.pojo.workparam.WorkProfessionListParam;
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
public class WorkDeliverActivity extends BaseActivity {

    private ImageView finishBtn;
    private Toolbar workToolbar;
    private RecyclerView deliverListRecycler;
    private WorkDeliverListAdapter workDeliverListAdapter;
    private EditText searchCompenyName;
    private TextView searchStartTime;
    private TextView searchEndTime;
    private Spinner searchProfession;
    private CardView searchBtn;
    private SmartRefreshLayout deliverRefresh;
    private Gson gson = new Gson();
    private Map<String, Object> param = new HashMap<>();
    private int pageNum = 1, professionId = -1;
    private List<WorkDeliverListParam.RowsDTO> deliverList = new ArrayList<>();

    @Override

    protected int initLayout() {
        return R.layout.activity_work_deliver;
    }

    @Override
    protected void initView() {
        finishBtn = findViewById(R.id.finish_btn);
        workToolbar = findViewById(R.id.work_toolbar);
        deliverListRecycler = findViewById(R.id.work_deliver_list_recycler);
        searchStartTime = findViewById(R.id.work_deliver_search_start_time);
        searchEndTime = findViewById(R.id.work_deliver_search_end_time);
        searchProfession = findViewById(R.id.work_deliver_search_profession);
        searchBtn = findViewById(R.id.work_deliver_search_btn);
        deliverRefresh = findViewById(R.id.work_deliver_refresh);
        workDeliverListAdapter = new WorkDeliverListAdapter();
    }

    @Override
    protected void initData() {
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });
        workToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        deliverListRecycler.setLayoutManager(new LinearLayoutManager(this));
        deliverListRecycler.setAdapter(workDeliverListAdapter);

        deliverRefresh.setEnableRefresh(false);
        deliverRefresh.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Translate));

        deliverRefresh.setOnLoadMoreListener(refreshLayout -> {
            pageNum++;
            refreshLayout.finishLoadMore(2000);
            getData();
        });

        getData();

        searchBtn.setOnClickListener(view -> {
            deliverList.clear();
            pageNum = 1;
            getData();
        });

        CalendarView startTimeView = new CalendarView(this);
        CalendarView endTimeView = new CalendarView(this);
        startTimeView.setDate(System.currentTimeMillis());
        endTimeView.setDate(System.currentTimeMillis());

        startTimeView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                searchStartTime.setText(String.format("%s-%s-%s", year, (month + 1), dayOfMonth));
            }
        });
        endTimeView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                searchEndTime.setText(String.format("%s-%s-%s", year, (month + 1), dayOfMonth));
            }
        });

        AlertDialog startDialog = new AlertDialog.Builder(this)
                .setView(startTimeView)
                .create();

        AlertDialog endDialog = new AlertDialog.Builder(this)
                .setView(endTimeView)
                .create();

        searchStartTime.setOnClickListener(view -> {
            startDialog.show();
        });
        searchEndTime.setOnClickListener(view -> {
            endDialog.show();
        });

        Api.config(Constant.NetWork.JOB_PROFESSION_LIST, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                WorkProfessionListParam professionListParam = gson.fromJson(result, WorkProfessionListParam.class);
                if (professionListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<WorkProfessionListParam.RowsDTO> professionListParamRows = professionListParam.getRows();
                    runOnUiThread(() -> {
                        initSpinner(professionListParamRows);
                    });
                } else {
                    showSyncToast(professionListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
    }

    private void getData() {
        param.clear();
        param.put("pageSize", Constant.pageSize);
        param.put("pageNum", pageNum);
        param.put("beginSatrTime", searchStartTime.getText());
        param.put("endSatrTime", searchEndTime.getText());
        if (professionId != -1) {
            param.put("postName", professionId);
        }
        System.out.println(param);
        Api.config(Constant.NetWork.JOB_DELIVER_LIST, param, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {

                WorkDeliverListParam deliverListParam = gson.fromJson(result, WorkDeliverListParam.class);
                if (deliverListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<WorkDeliverListParam.RowsDTO> deliverListParamRows = deliverListParam.getRows();
                    runOnUiThread(() -> {
                        if (deliverListParamRows.size() > 0) {
                            deliverRefresh.finishLoadMore(1000);
                            deliverList.addAll(deliverListParamRows);
                        } else {
                            deliverRefresh.finishLoadMore(true);
                            showSyncToast("没有更多数据了");
                        }
                        workDeliverListAdapter.setData(deliverList);
                    });
                } else {
                    showSyncToast(deliverListParam.getMsg());
                    deliverRefresh.finishLoadMore(true);
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
                deliverRefresh.finishLoadMore(true);
            }
        });
    }

    private void initSpinner(List<WorkProfessionListParam.RowsDTO> professionListParamRows) {
        List<String> professionList = new ArrayList<>();
        for (WorkProfessionListParam.RowsDTO listParamRow : professionListParamRows) {
            professionList.add(listParamRow.getProfessionName());
        }

        searchProfession.setAdapter(
                new ArrayAdapter<String>(WorkDeliverActivity.this, R.layout.spinner_parking_item, professionList));

        searchProfession.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                professionId = professionListParamRows.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}