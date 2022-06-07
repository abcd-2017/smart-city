package com.example.activity.hospitalactivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.adapter.hospitaladapter.HospitalReservationListAdapter;
import com.example.pojo.hospitalparam.HospitalReservationListParam;
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
public class HospitalReservationListActivity extends BaseActivity {

    private static final String TAG = "HospitalReservationListActivity";
    private Toolbar toolbar;
    private ImageView finish;
    private SmartRefreshLayout smartRefresh;
    private RecyclerView reservationRecycler;
    private Integer pageNum = 1;
    private HospitalReservationListAdapter reservationListAdapter;
    private List<HospitalReservationListParam.RowsDTO> list = new ArrayList<>();
    private boolean hasNext = true;
    private Integer total;
    private AlertDialog alertDialog;
    private View alertView;
    private TextView patientName;
    private TextView patientOrderId;
    private TextView patientCategory;
    private TextView patientMoney;
    private TextView patientReserveTime;
    private TextView patientStatus;
    private TextView patientType;

    @Override
    protected int initLayout() {
        return R.layout.activity_hospital_reservation_list;
    }

    @Override
    protected void initView() {
        toolbar = findViewById(R.id.hospital_toolbar);
        finish = findViewById(R.id.finish_btn);
        smartRefresh = findViewById(R.id.hospital_reservation_refresh);
        reservationRecycler = findViewById(R.id.hospital_reservation_recycler);
        alertView = LayoutInflater.from(this)
                .inflate(R.layout.alert_dialog_reservation_detail, null, false);
        patientName = alertView.findViewById(R.id.patient_name);
        patientOrderId = alertView.findViewById(R.id.patient_order_id);
        patientCategory = alertView.findViewById(R.id.patient_category_name);
        patientMoney = alertView.findViewById(R.id.patient_money);
        patientReserveTime = alertView.findViewById(R.id.patient_reserve_time);
        patientStatus = alertView.findViewById(R.id.patient_status);
        patientType = alertView.findViewById(R.id.patient_type);
        reservationListAdapter = new HospitalReservationListAdapter();
    }

    @Override
    protected void initData() {
        reservationRecycler.setLayoutManager(new LinearLayoutManager(this));
        reservationRecycler.setAdapter(reservationListAdapter);
        reservationRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
        finish.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });
        smartRefresh.setRefreshHeader(new BezierRadarHeader(this).setEnableHorizontalDrag(true));
        smartRefresh.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Translate));

        smartRefresh.setOnRefreshListener(refreshLayout -> {
            pageNum = 1;
            smartRefresh.finishRefresh(2000);
            hasNext = true;
            loadList(true);
        });

        smartRefresh.setOnLoadMoreListener(refreshLayout -> {
            pageNum++;
            smartRefresh.finishLoadMore(2000);
            loadList(false);
        });

        loadList(true);


        alertDialog = new AlertDialog.Builder(this)
                .setTitle("预约单")
                .setView(alertView)
                .setPositiveButton("确定", null)
                .create();

        reservationListAdapter.setItemClickListener(id -> {
            HospitalReservationListParam.RowsDTO rowsDTO = list.get(id);
            patientName.setText(rowsDTO.getPatientName());
            patientCategory.setText(rowsDTO.getCategoryName());
            patientOrderId.setText(rowsDTO.getOrderNo());
            patientReserveTime.setText(rowsDTO.getReserveTime());
            patientType.setText("1".equals(rowsDTO.getType()) ? "普通诊" : "专家诊");
            patientMoney.setText(String.valueOf(rowsDTO.getMoney()));
            patientStatus.setText(rowsDTO.getStatus());
            alertDialog.show();
        });
    }

    private void loadList(boolean isRefresh) {
        Map<String, Object> param = new HashMap<>();
        param.put("pageSize", Constant.pageSize);
        param.put("pageNum", pageNum);

        Api.config(Constant.NetWork.HOSPITAL_RESERVATION_LIST, param, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                HospitalReservationListParam reservationListParam = gson.fromJson(result, HospitalReservationListParam.class);
                if (reservationListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    total = reservationListParam.getTotal();
                    List<HospitalReservationListParam.RowsDTO> listParamRows = reservationListParam.getRows();
                    Log.d(TAG, "success: " + listParamRows.size());
                    if (listParamRows.size() > 0 && hasNext) {
                        if (isRefresh) {
                            list.clear();
                        }
                        list.addAll(listParamRows);
                        runOnUiThread(() -> {
                            reservationListAdapter.setData(list);
                        });
                    } else {
                        if (isRefresh) {
                            smartRefresh.finishRefresh();
                            showSyncToast("刷新失败");
                        } else {
                            try {
                                Thread.sleep(1800);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            hasNext = false;
                            smartRefresh.finishLoadMore();
                            showSyncToast("没有数据了");
                        }
                    }
                    hasNext = list.size() < total && listParamRows.size() == Constant.pageSize;
                } else {
                    showSyncToast(reservationListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
                if (isRefresh) {
                    smartRefresh.finishRefresh();
                } else {
                    smartRefresh.finishLoadMore();
                }
            }
        });
    }
}