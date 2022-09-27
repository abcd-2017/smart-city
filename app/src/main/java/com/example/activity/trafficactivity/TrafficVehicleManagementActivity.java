package com.example.activity.trafficactivity;

import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.adapter.trafficadapter.TrafficCheckCarListAdapter;
import com.example.pojo.BaseParam;
import com.example.pojo.trafficparam.TrafficCarListParam;
import com.example.pojo.trafficparam.TrafficCheckCarGrtParam;
import com.example.pojo.trafficparam.TrafficCheckCarPlateListParam;
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
public class TrafficVehicleManagementActivity extends BaseActivity {

    private ImageView finishBtn;
    private Toolbar trafficToolbar;
    private RecyclerView trafficCheckListRecycler;
    private LinearLayout vehicleNotice;
    private LinearLayout vehicleMyAppointment;
    private LinearLayout vehicleMyCar;
    private TrafficCheckCarListAdapter checkCarListAdapter;
    private Gson gson = new Gson();
    private AlertDialog.Builder alertDialog;
    private List<String> myCarPlateNoList = new ArrayList<>();
    private Integer selectPlateNo = 0;
    private View inflate;
    private Spinner checkCarSpinner;
    private CardView dialogCancel;
    private CardView dialogOk;
    private List<TrafficCarListParam.RowsDTO> carListRows;

    @Override
    protected int initLayout() {
        return R.layout.activity_traffic_vehicle_management;
    }

    @Override
    protected void initView() {
        finishBtn = findViewById(R.id.finish_btn);
        trafficToolbar = findViewById(R.id.traffic_toolbar);
        trafficCheckListRecycler = findViewById(R.id.traffic_vehicle_list_recycler);
        vehicleNotice = findViewById(R.id.traffic_vehicle_notice);
        vehicleMyAppointment = findViewById(R.id.traffic_vehicle_my_appointment);
        vehicleMyCar = findViewById(R.id.traffic_vehicle_my_car);
        checkCarListAdapter = new TrafficCheckCarListAdapter();

        inflate = LayoutInflater.from(this)
                .inflate(R.layout.alert_dialog_traffic_check_car, null, false);
        checkCarSpinner = inflate.findViewById(R.id.traffic_check_car_spinner);
        dialogCancel = inflate.findViewById(R.id.alert_dialog_cancel);
        dialogOk = inflate.findViewById(R.id.alert_dialog_ok);
    }

    @Override
    protected void initData() {
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });
        trafficToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        trafficCheckListRecycler.setLayoutManager(new LinearLayoutManager(this));
        trafficCheckListRecycler.setAdapter(checkCarListAdapter);

        vehicleMyAppointment.setOnClickListener(view -> {
            jumpPage(TrafficCheckApplyActivity.class);
        });
        vehicleMyCar.setOnClickListener(view -> {
            jumpPage(TrafficMyCarActivity.class);
        });

        getCarList();

        //乘车须知
        Api.config(Constant.NetWork.TRAFFIC_CHECK_CAT_GRT, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                TrafficCheckCarGrtParam checkCarGrtParam = gson.fromJson(result, TrafficCheckCarGrtParam.class);
                if (checkCarGrtParam.getCode() == HttpURLConnection.HTTP_OK) {
                    TrafficCheckCarGrtParam.DataDTO checkCarGrtParamData = checkCarGrtParam.getData();
                    alertDialog = new AlertDialog.Builder(TrafficVehicleManagementActivity.this)
                            .setTitle(checkCarGrtParamData.getTitle())
                            .setMessage(Html.fromHtml(checkCarGrtParamData.getNotice(), Html.FROM_HTML_MODE_COMPACT));
                } else {
                    showSyncToast(checkCarGrtParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });

        vehicleNotice.setOnClickListener(view -> {
            alertDialog.create().show();
        });

        //检车列表
        Api.config(Constant.NetWork.TRAFFIC_CHECK_CAR_PLACE_LIST, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                TrafficCheckCarPlateListParam checkCarPlateListParam = gson.fromJson(result, TrafficCheckCarPlateListParam.class);
                if (checkCarPlateListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<TrafficCheckCarPlateListParam.RowsDTO> carPlateListParamRows = checkCarPlateListParam.getRows();
                    runOnUiThread(() -> {
                        checkCarListAdapter.setData(carPlateListParamRows);
                    });
                } else {
                    showSyncToast(checkCarPlateListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });

        //弹出框内 view事件
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(inflate)
                .create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable(AppCompatResources.getDrawable(this, R.drawable.alert_bg_translate));

        dialogCancel.setOnClickListener(view -> {
            selectPlateNo = 0;
            alertDialog.hide();
        });

        checkCarListAdapter.setItemBtnClickListener(id -> {
            alertDialog.show();

            dialogOk.setOnClickListener(view -> {
                if (myCarPlateNoList.size() == 0) {
                    alertDialog.hide();
                    jumpPage(TrafficMyCarActivity.class);
                    showSyncToast("暂无车辆，请先添加车辆");
                    return;
                }
                Map<String, Object> param = new HashMap<>();
                param.put("userId", getStringToSP("id"));
                param.put("cardId", carListRows.get(selectPlateNo).getId());
                param.put("addressId", id);
                Api.config(Constant.NetWork.APPLY_TRAFFIC_CHECK_CAR, param, this).postRequest(new RequestCallback() {
                    @Override
                    public void success(String result) {
                        BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                        if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                            showSyncToast("预约成功");
                            runOnUiThread(alertDialog::hide);
                        } else {
                            showSyncToast(baseParam.getMsg());
                        }
                    }

                    @Override
                    public void failure(Exception e) {
                        showSyncToast("网络异常");
                    }
                });
            });
        });
    }

    private void getCarList() {
        Api.config(Constant.NetWork.TRAFFIC_CAR_LIST, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                TrafficCarListParam trafficCarList = gson.fromJson(result, TrafficCarListParam.class);
                if (trafficCarList.getCode() == HttpURLConnection.HTTP_OK) {
                    carListRows = trafficCarList.getRows();
                    myCarPlateNoList.clear();
                    for (TrafficCarListParam.RowsDTO carListRow : carListRows) {
                        myCarPlateNoList.add(carListRow.getPlateNo());
                    }
                    runOnUiThread(() -> {
                        checkCarSpinner.setAdapter(new ArrayAdapter<String>(TrafficVehicleManagementActivity.this, R.layout.spinner_parking_item, myCarPlateNoList));
                        checkCarSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                selectPlateNo = position;
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        checkCarSpinner.setSelection(0);
                    });
                } else {
                    showSyncToast(trafficCarList.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCarList();
    }
}