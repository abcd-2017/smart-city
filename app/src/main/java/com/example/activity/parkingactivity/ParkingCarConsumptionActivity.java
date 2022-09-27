package com.example.activity.parkingactivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.adapter.parkingadapter.ParkingCarConsumptionAdapter;
import com.example.pojo.BaseParam;
import com.example.pojo.parkingparam.ParkingCarConsumptionParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kkk
 */
public class ParkingCarConsumptionActivity extends BaseActivity {

    private Toolbar parkingToolbar;
    private ImageView finishBtn;
    private Button addBtn;
    private TextView parkingPlateNo;
    private RecyclerView consumptionRecycler;
    private ParkingCarConsumptionAdapter carConsumptionAdapter;
    private Map<String, Object> param;
    private Gson gson = new Gson();
    private TextView emptyText;

    @Override
    protected int initLayout() {
        return R.layout.activity_parking_car_consumption;
    }

    @Override
    protected void initView() {
        parkingToolbar = findViewById(R.id.parking_toolbar);
        finishBtn = findViewById(R.id.finish_btn);
        addBtn = findViewById(R.id.parking_add_consumption_btn);
        parkingPlateNo = findViewById(R.id.parking_comsuption_plate_no);
        consumptionRecycler = findViewById(R.id.parking_car_consumption_recycler);
        emptyText = findViewById(R.id.empty_text);
        carConsumptionAdapter = new ParkingCarConsumptionAdapter();
    }

    @Override
    protected void initData() {
        parkingToolbar.setNavigationOnClickListener(view -> {
            finish();
        });
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });

        consumptionRecycler.setLayoutManager(new LinearLayoutManager(this));
        consumptionRecycler.setAdapter(carConsumptionAdapter);

        String carPlateNo = getStringToSP("plateNo");

        parkingPlateNo.setText(carPlateNo);

        param = new HashMap<>();
        param.put("plateNo", carPlateNo);

        addBtn.setOnClickListener(view -> {
            jumpPage(ParkingAddConsumptionActivity.class);
        });

        carConsumptionAdapter.setItemClickListener(new ParkingCarConsumptionAdapter.consumptionItemClickListener() {
            @Override
            public void click(ParkingCarConsumptionParam.RowsDTO rowsDTO) {
                String consumptionDetail = gson.toJson(rowsDTO);
                jumpPageToIntent(new Intent(ParkingCarConsumptionActivity.this, ParkingAddConsumptionActivity.class).putExtra("consumptionDetail", consumptionDetail));
            }

            @Override
            public void deleteItem(Integer id, XPopup.Builder xPopupBuilder) {
                AlertDialog alertDialog = new AlertDialog.Builder(ParkingCarConsumptionActivity.this)
                        .setMessage("确定要删除吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Api.config(Constant.NetWork.PARK_CAR_CONSUMPTION, null, ParkingCarConsumptionActivity.this).deleteRestfulRequest(new RequestCallback() {
                                    @Override
                                    public void success(String result) {
                                        BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                                        if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                                            showSyncToast("删除成功");
                                            getCarConsumptionList();
                                        } else {
                                            showSyncToast(baseParam.getMsg());
                                        }
                                    }

                                    @Override
                                    public void failure(Exception e) {
                                        showSyncToast("网络异常");
                                    }
                                }, id);
                            }
                        }).create();


                xPopupBuilder.asAttachList(new String[]{"删除"}, new int[0], new OnSelectListener() {
                    @Override
                    public void onSelect(int position, String text) {
                        alertDialog.show();
                    }
                }).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCarConsumptionList();
    }

    private void getCarConsumptionList() {
        Api.config(Constant.NetWork.PARK_CAR_CONSUMPTION, param, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                ParkingCarConsumptionParam carConsumptionParam = gson.fromJson(result, ParkingCarConsumptionParam.class);
                if (carConsumptionParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<ParkingCarConsumptionParam.RowsDTO> consumptionParamRows = carConsumptionParam.getRows();
                    runOnUiThread(() -> {
                        if (consumptionParamRows.size() > 0) {
                            emptyText.setVisibility(View.GONE);
                            consumptionRecycler.setVisibility(View.VISIBLE);
                        } else {
                            emptyText.setVisibility(View.VISIBLE);
                            consumptionRecycler.setVisibility(View.GONE);
                        }
                        carConsumptionAdapter.setData(consumptionParamRows);
                    });
                } else {
                    showSyncToast(carConsumptionParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
    }
}