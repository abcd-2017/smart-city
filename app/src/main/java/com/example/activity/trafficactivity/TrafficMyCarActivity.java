package com.example.activity.trafficactivity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.adapter.trafficadapter.TrafficCarListAdapter;
import com.example.pojo.BaseParam;
import com.example.pojo.trafficparam.TrafficCarListParam;
import com.example.util.AlertDialogUtils;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;
import com.lxj.xpopup.interfaces.OnSelectListener;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kkk
 */
public class TrafficMyCarActivity extends BaseActivity {

    private View alertView;
    private ImageView finishBtn;
    private Toolbar trafficToolbar;
    private RecyclerView carRecycler;
    private CardView addCarBtn, alertDialogOk, alertDialogCancel;
    private TextView alertTitle;
    private Spinner carSpinner;
    private EditText carPlateNo, carEngineNo;
    private TrafficCarListAdapter trafficCarListAdapter;
    private Gson gson = new Gson();
    private int selectPlate = 0;
    private List<TrafficCarListParam.RowsDTO> carListParamRows;
    private TextView emptyText;
    private boolean isAddCar = true;
    private Map<String, Object> param = new HashMap<>();

    @Override
    protected int initLayout() {
        return R.layout.activity_traffic_my_car;
    }

    @Override
    protected void initView() {
        finishBtn = findViewById(R.id.finish_btn);
        trafficToolbar = findViewById(R.id.traffic_toolbar);
        carRecycler = findViewById(R.id.traffic_add_car_recycler);
        addCarBtn = findViewById(R.id.traffic_add_car_btn);
        emptyText = findViewById(R.id.empty_text);

        alertView = LayoutInflater.from(this)
                .inflate(R.layout.alert_dialog_traffic_add_car, null, false);
        alertTitle = alertView.findViewById(R.id.alert_dialog_title);
        carSpinner = alertView.findViewById(R.id.traffic_add_car_spinner);
        carPlateNo = alertView.findViewById(R.id.traffic_add_car_plate_no);
        carEngineNo = alertView.findViewById(R.id.traffic_add_car_engine_no);
        alertDialogOk = alertView.findViewById(R.id.alert_dialog_ok);
        alertDialogCancel = alertView.findViewById(R.id.alert_dialog_cancel);

        trafficCarListAdapter = new TrafficCarListAdapter();
    }

    @Override
    protected void initData() {
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });
        trafficToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        carRecycler.setLayoutManager(new LinearLayoutManager(this));
        carRecycler.setAdapter(trafficCarListAdapter);

        getCarList();

        carSpinner.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_parking_item, Constant.TRAFFIC_PLATE_TYPE));
        carSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectPlate = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        carSpinner.setSelection(0);

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(alertView)
                .create();
        alertDialog.getWindow().setBackgroundDrawable(AppCompatResources.getDrawable(this, R.drawable.alert_bg_translate));
        alertDialog.setCancelable(false);

        alertDialogOk.setOnClickListener(view -> {
            param.clear();
            param.put("engineNo", carEngineNo.getText().toString());
            param.put("plateNo", carPlateNo.getText().toString());
            param.put("type", Constant.TRAFFIC_PLATE_TYPE.get(selectPlate));

            if (isAddCar) {
                Api.config(Constant.NetWork.TRAFFIC_CAR, param, this).postRequest(new RequestCallback() {
                    @Override
                    public void success(String result) {
                        BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                        if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                            showSyncToast("添加成功");
                            runOnUiThread(() -> getCarList());
                        } else {
                            showSyncToast(baseParam.getMsg());
                        }
                    }

                    @Override
                    public void failure(Exception e) {
                        showSyncToast("网络异常");
                    }
                });
            } else {
                Api.config(Constant.NetWork.TRAFFIC_CAR, param, this).putRequest(new RequestCallback() {
                    @Override
                    public void success(String result) {
                        BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                        if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                            showSyncToast("修改成功");
                            runOnUiThread(() -> getCarList());
                        } else {
                            showSyncToast(baseParam.getMsg());
                        }
                    }

                    @Override
                    public void failure(Exception e) {
                        showSyncToast("网络异常");
                    }
                });
            }

            alertDialog.hide();
            selectPlate = 0;
        });
        alertDialogCancel.setOnClickListener(view -> {
            alertDialog.hide();
            selectPlate = 0;
        });

        addCarBtn.setOnClickListener(view -> {
            alertTitle.setText("添加车辆");
            isAddCar = true;
            alertDialog.show();
        });

        trafficCarListAdapter.setCarListOnLongListener((num, builder) -> {
            builder.asAttachList(new String[]{"修改", "删除"}, new int[0], new OnSelectListener() {
                @Override
                public void onSelect(int position, String text) {
                    if (position == 0) {
                        alertTitle.setText("修改车辆");
                        isAddCar = false;
                        TrafficCarListParam.RowsDTO rowsDTO = carListParamRows.get(num);
                        carPlateNo.setText(rowsDTO.getPlateNo());
                        carEngineNo.setText(rowsDTO.getEngineNo());
                        for (int i = 0; i < Constant.TRAFFIC_PLATE_TYPE.size(); i++) {
                            if (Constant.TRAFFIC_PLATE_TYPE.get(i).equals(rowsDTO.getType())) {
                                carSpinner.setSelection(i);
                                selectPlate = i;
                                break;
                            }
                        }
                        alertDialog.show();
                    } else {
                        AlertDialogUtils.getInstance();
                        AlertDialogUtils.showConfirmDialog(TrafficMyCarActivity.this, "提示", "确认要删除车辆信息？", new AlertDialogUtils.OnDialogButtonClickListener() {
                            @Override
                            public void clickOk() {
                                Api.config(Constant.NetWork.TRAFFIC_CAR, null, TrafficMyCarActivity.this).deleteRestfulRequest(new RequestCallback() {
                                    @Override
                                    public void success(String result) {
                                        BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                                        if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                                            showSyncToast("删除成功");
                                            runOnUiThread(TrafficMyCarActivity.this::getCarList);
                                        } else {
                                            showSyncToast(baseParam.getMsg());
                                        }
                                    }

                                    @Override
                                    public void failure(Exception e) {
                                        showSyncToast("网络异常");
                                    }
                                }, carListParamRows.get(num).getId());
                            }

                            @Override
                            public void clickCancel() {

                            }
                        });
                    }
                }
            }).show();

            return true;
        });
    }

    private void getCarList() {
        Api.config(Constant.NetWork.TRAFFIC_CAR_LIST, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                TrafficCarListParam trafficCarListParam = gson.fromJson(result, TrafficCarListParam.class);
                if (trafficCarListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    carListParamRows = trafficCarListParam.getRows();
                    runOnUiThread(() -> {
                        if (carListParamRows.size() > 0) {
                            trafficCarListAdapter.setData(carListParamRows);
                            emptyText.setVisibility(View.GONE);
                            carRecycler.setVisibility(View.VISIBLE);
                        } else {
                            emptyText.setVisibility(View.VISIBLE);
                            carRecycler.setVisibility(View.GONE);
                        }
                    });
                } else {
                    showSyncToast(trafficCarListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
    }
}