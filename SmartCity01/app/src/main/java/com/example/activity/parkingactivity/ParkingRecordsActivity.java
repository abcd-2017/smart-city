package com.example.activity.parkingactivity;

import android.content.Intent;
import android.text.TextUtils;
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
import com.example.adapter.parkingadapter.ParkingReCardListAdapter;
import com.example.dbhelper.ServiceListDBHelper;
import com.example.dbhelper.parking.ParkingLotDBHelper;
import com.example.pojo.parkingparam.ParkingLotListParam;
import com.example.pojo.parkingparam.ParkingReCordParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kkk
 */
public class ParkingRecordsActivity extends BaseActivity {

    private Toolbar parkingToolbar;
    private ImageView finishBtn;
    private TextView searchOutTime;
    private TextView searchEntryTime;
    private CardView searchBtn;
    private EditText searchPlateNo;
    private Spinner searchName;
    private RecyclerView consumptionRecycler;
    private ParkingReCardListAdapter reCardListAdapter;
    private String searchParkingName, entryTime, outTime;
    private Map<String, Object> param = new HashMap<>();
    private Gson gson = new Gson();
    private TextView emptyText;
    private ParkingLotDBHelper parkingLotDBHelper;
    private List<String> searchNameSpinnerList = new ArrayList<>();
    private List<ParkingLotListParam.RowsDTO> rowsDTOList;


    @Override
    protected int initLayout() {
        return R.layout.activity_parking_records;
    }

    @Override
    protected void initView() {
        parkingToolbar = findViewById(R.id.parking_toolbar);
        finishBtn = findViewById(R.id.finish_btn);
        searchOutTime = findViewById(R.id.parking_search_out_time);
        searchEntryTime = findViewById(R.id.parking_search_entry_time);
        searchBtn = findViewById(R.id.parking_search_btn);
        searchPlateNo = findViewById(R.id.parking_search_plate_no);
        searchName = findViewById(R.id.parking_search_parking_name);
        consumptionRecycler = findViewById(R.id.consumption_recycler);
        emptyText = findViewById(R.id.empty_text);
        reCardListAdapter = new ParkingReCardListAdapter();
    }

    @Override
    protected void initData() {
        //创建数据库助手类
        parkingLotDBHelper = new ParkingLotDBHelper(this);
        ServiceListDBHelper listDBHelper = new ServiceListDBHelper(this);

        parkingToolbar.setNavigationOnClickListener(view -> {
            finish();
        });
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });
        consumptionRecycler.setLayoutManager(new LinearLayoutManager(this));
        consumptionRecycler.setAdapter(reCardListAdapter);

        searchBtn.setOnClickListener(view -> {
            getParkingReCardList();
        });

        getParkingReCardList();

        long count = parkingLotDBHelper.queryCount();

        if (count > 0) {
            rowsDTOList = parkingLotDBHelper.query();
            initSpinner();
        } else {
            Api.config(Constant.NetWork.PARK_LOT_LIST, null, this).getRequest(new RequestCallback() {
                @Override
                public void success(String result) {
                    ParkingLotListParam lotListParam = gson.fromJson(result, ParkingLotListParam.class);
                    rowsDTOList = lotListParam.getRows();
                    parkingLotDBHelper.insert(rowsDTOList);
                    runOnUiThread(() -> {
                        initSpinner();
                    });
                }

                @Override
                public void failure(Exception e) {
                    showSyncToast("网络异常");
                }
            });
        }

        CalendarView calendarEntry = new CalendarView(this);
        calendarEntry.setDate(System.currentTimeMillis());
        CalendarView calendarOut = new CalendarView(this);
        calendarOut.setDate(System.currentTimeMillis());

        calendarEntry.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                entryTime = String.format("%d-%d-%d", year, month + 1, dayOfMonth);
                searchEntryTime.setText(entryTime);
            }
        });

        calendarOut.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                outTime = String.format("%d-%d-%d", year, month + 1, dayOfMonth);
                searchOutTime.setText(outTime);
            }
        });

        AlertDialog alertEntry = new AlertDialog.Builder(this)
                .setView(calendarEntry)
                .create();

        AlertDialog alertOut = new AlertDialog.Builder(this)
                .setView(calendarOut)
                .create();

        searchEntryTime.setOnClickListener(view -> {
            alertEntry.show();
        });
        searchOutTime.setOnClickListener(view -> {
            alertOut.show();
        });
    }

    private void getParkingReCardList() {
        String searchPlateNo = this.searchPlateNo.getText().toString();

        param.clear();
        param.put("entryTime", entryTime);
        param.put("outTime", outTime);
        if (!TextUtils.isEmpty(searchPlateNo)) {
            param.put("plateNumber", searchPlateNo);
        }
        if (!TextUtils.isEmpty(searchParkingName)) {
            param.put("parkName", searchParkingName);
        }

        Api.config(Constant.NetWork.PARK_RECORD_LIST, param, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                ParkingReCordParam reCordParam = gson.fromJson(result, ParkingReCordParam.class);
                if (reCordParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<ParkingReCordParam.RowsDTO> cordParamRows = reCordParam.getRows();
                    runOnUiThread(() -> {
                        if (cordParamRows.size() > 0) {
                            emptyText.setVisibility(View.GONE);
                            consumptionRecycler.setVisibility(View.VISIBLE);
                        } else {
                            emptyText.setVisibility(View.VISIBLE);
                            consumptionRecycler.setVisibility(View.GONE);
                        }
                        reCardListAdapter.setData(cordParamRows);
                    });
                } else {
                    showSyncToast(reCordParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
    }

    private void initSpinner() {
        for (ParkingLotListParam.RowsDTO rowsDTO : rowsDTOList) {
            searchNameSpinnerList.add(rowsDTO.getParkName());
        }
        searchNameSpinnerList.add("请选择停车场名");

        //spinner 设置适配器
        searchName.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_parking_item, searchNameSpinnerList));
        searchName.setSelection(searchNameSpinnerList.size() - 1);

        searchName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position < searchNameSpinnerList.size() - 1) {
                    searchParkingName = searchNameSpinnerList.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}