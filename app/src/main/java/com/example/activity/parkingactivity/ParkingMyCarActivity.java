package com.example.activity.parkingactivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.adapter.parkingadapter.ParkingMyCarListAdapter;
import com.example.pojo.BaseParam;
import com.example.pojo.parkingparam.ParkingMyCarListParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kkk
 */
public class ParkingMyCarActivity extends BaseActivity {

    private Toolbar parkingToolbar;
    private RecyclerView carRecycler;
    private ImageView finishBtn;
    private ParkingMyCarListAdapter myCarListAdapter;
    private CardView addMyCar;
    private int selectType = -1;
    private Gson gson = new Gson();

    @Override
    protected int initLayout() {
        return R.layout.activity_parking_my_car;
    }

    @Override
    protected void initView() {
        parkingToolbar = findViewById(R.id.parking_toolbar);
        finishBtn = findViewById(R.id.finish_btn);
        carRecycler = findViewById(R.id.parking_my_car_recycler);
        addMyCar = findViewById(R.id.add_my_car);
        myCarListAdapter = new ParkingMyCarListAdapter();
    }

    @Override
    protected void initData() {
        parkingToolbar.setNavigationOnClickListener(view -> {
            finish();
        });
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });

        carRecycler.setLayoutManager(new LinearLayoutManager(this));
        carRecycler.setAdapter(myCarListAdapter);

        queryMyCarList();
        myCarListAdapter.setContext(this);

        addMyCar.setOnClickListener(view -> {
            View alertView = LayoutInflater.from(this).inflate(R.layout.alert_dialog_parking_add_car, null, false);
            EditText carPlateNo = alertView.findViewById(R.id.parking_car_plateNo);
            RadioGroup carRadioGroup = alertView.findViewById(R.id.car_radio_group);
            RadioButton gasolineVehicle = alertView.findViewById(R.id.car_type_gasoline_vehicle);
            RadioButton newEnergyVehicle = alertView.findViewById(R.id.car_type_new_energy_vehicle);

            TextView titleView = new TextView(this);
            titleView.setText("添加车辆信息");
            titleView.getPaint().setFakeBoldText(true);
            titleView.setTextColor(Color.BLACK);
            titleView.setTextSize(20);
            titleView.setGravity(Gravity.CENTER);

            carRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == gasolineVehicle.getId()) {
                        selectType = 1;
                    } else {
                        selectType = 0;
                    }
                }
            });

            new AlertDialog.Builder(this)
                    .setView(alertView)
                    .setCustomTitle(titleView)
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            selectType = -1;
                            carRadioGroup.clearCheck();
                            carPlateNo.setText("");
                        }
                    }).setPositiveButton("添加", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String carPlate = carPlateNo.getText().toString();
                    if (selectType == -1) {
                        showSyncToast("添加失败, 未选择车辆类型");
                        return;
                    } else if (TextUtils.isEmpty(carPlate)) {
                        showSyncToast("添加失败, 未填写车牌号");
                        return;
                    }
                    Map<String, Object> param = new HashMap<>();
                    param.put("plateNo", carPlate);
                    param.put("type", selectType == 1 ? "汽油车" : "新能源车");
                    Api.config(Constant.NetWork.PARK_CAR, param, ParkingMyCarActivity.this).postRequest(new RequestCallback() {
                        @Override
                        public void success(String result) {
                            BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                            if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                                queryMyCarList();
                                showSyncToast("添加成功");
                            } else {
                                showSyncToast(baseParam.getMsg());
                            }
                        }

                        @Override
                        public void failure(Exception e) {
                            showSyncToast("网络异常");
                        }
                    });
                    selectType = -1;
                    carRadioGroup.clearCheck();
                    carPlateNo.setText("");
                }
            }).show();
        });

        myCarListAdapter.setCarItemListener(new ParkingMyCarListAdapter.CarItemListener() {
            @Override
            public void click(String plateNo) {
                setStringToSP("plateNo", plateNo);
                jumpPage(ParkingCarConsumptionActivity.class);
            }

            @Override
            public void deleteCar(Integer id, SwipeMenuLayout carSwipe) {
                new AlertDialog.Builder(ParkingMyCarActivity.this)
                        .setMessage("确定要删除?")
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Api.config(Constant.NetWork.PARK_CAR, null, ParkingMyCarActivity.this).deleteRestfulRequest(new RequestCallback() {
                                    @Override
                                    public void success(String result) {
                                        BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                                        if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                                            queryMyCarList();
                                            showSyncToast("删除成功");
                                        } else {
                                            showSyncToast(baseParam.getMsg());
                                        }
                                    }

                                    @Override
                                    public void failure(Exception e) {
                                        showSyncToast("网络异常");
                                    }
                                }, id);
                                carSwipe.smoothClose();
                            }
                        }).create().show();
            }

            @Override
            public void onLongClick(ParkingMyCarListParam.RowsDTO carParam, XPopup.Builder xPopupBuilder) {
                xPopupBuilder.asAttachList(new String[]{"编辑"}, new int[0], new OnSelectListener() {
                    @Override
                    public void onSelect(int position, String text) {
                        View _alertView = LayoutInflater.from(ParkingMyCarActivity.this).inflate(R.layout.alert_dialog_parking_add_car, null, false);
                        EditText _carPlateNo = _alertView.findViewById(R.id.parking_car_plateNo);
                        RadioGroup _carRadioGroup = _alertView.findViewById(R.id.car_radio_group);
                        RadioButton _gasolineVehicle = _alertView.findViewById(R.id.car_type_gasoline_vehicle);
                        RadioButton _newEnergyVehicle = _alertView.findViewById(R.id.car_type_new_energy_vehicle);

                        String type = carParam.getType();
                        if ("汽油车".equals(type)) {
                            _gasolineVehicle.setChecked(true);
                        } else {
                            _newEnergyVehicle.setChecked(true);
                        }
                        _carPlateNo.setText(carParam.getPlateNo());

                        _carRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                if (checkedId == _gasolineVehicle.getId()) {
                                    selectType = 1;
                                } else {
                                    selectType = 0;
                                }
                            }
                        });

                        new AlertDialog.Builder(ParkingMyCarActivity.this)
                                .setMessage("修改车辆信息")
                                .setView(_alertView)
                                .setPositiveButton("修改", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String carPlate = _carPlateNo.getText().toString();
                                        if (TextUtils.isEmpty(carPlate)) {
                                            showSyncToast("添加失败, 未选择车辆类型");
                                            return;
                                        }
                                        Map<String, Object> param = new HashMap<>();
                                        param.put("plateNo", carPlate);
                                        param.put("type", selectType == 1 ? "汽油车" : "新能源车");
                                        param.put("id", carParam.getId());
                                        Api.config(Constant.NetWork.PARK_CAR, param, ParkingMyCarActivity.this).putRequest(new RequestCallback() {
                                            @Override
                                            public void success(String result) {
                                                BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                                                if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                                                    queryMyCarList();
                                                    showSyncToast("修改成功");
                                                } else {
                                                    showSyncToast(baseParam.getMsg());
                                                }
                                            }

                                            @Override
                                            public void failure(Exception e) {
                                                showSyncToast("网络异常");
                                            }
                                        });
                                        selectType = -1;
                                        _carRadioGroup.clearCheck();
                                        _carPlateNo.setText("");
                                    }
                                }).create().show();
                    }
                }).show();
            }
        });
    }

    private void queryMyCarList() {
        Api.config(Constant.NetWork.PARK_CAR_MY, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                ParkingMyCarListParam myCarListParam = gson.fromJson(result, ParkingMyCarListParam.class);
                if (myCarListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<ParkingMyCarListParam.RowsDTO> carListParamRows = myCarListParam.getRows();
                    runOnUiThread(() -> {
                        myCarListAdapter.setData(carListParamRows);
                    });
                } else {
                    showSyncToast(myCarListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
    }
}