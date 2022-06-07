package com.example.activity.parkingactivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.adapter.parkingadapter.ParkingIntegralListAdapter;
import com.example.pojo.BaseParam;
import com.example.pojo.parkingparam.ParkingIntegralListParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.AttachListPopupView;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;

import java.net.HttpURLConnection;
import java.util.List;

/**
 * @author kkk
 */
public class ParkingIntegralActivity extends BaseActivity {

    private Toolbar parkingToolbar;
    private ImageView finishBtn;
    private ImageView parkingMore;
    private RecyclerView integralRecycler;
    private ParkingIntegralListAdapter integralListAdapter;
    private Gson gson = new Gson();

    @Override
    protected int initLayout() {
        return R.layout.activity_parking_integral;
    }

    @Override
    protected void initView() {
        parkingToolbar = findViewById(R.id.parking_toolbar);
        finishBtn = findViewById(R.id.finish_btn);
        parkingMore = findViewById(R.id.moreBtn);
        integralRecycler = findViewById(R.id.parking_integral_recycler);
        integralListAdapter = new ParkingIntegralListAdapter();
    }

    @Override
    protected void initData() {
        parkingToolbar.setNavigationOnClickListener(view -> {
            finish();
        });
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });

        integralRecycler.setLayoutManager(new LinearLayoutManager(this));
        integralRecycler.setAdapter(integralListAdapter);
        integralRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        AttachListPopupView listPopupView = new XPopup.Builder(this)
                .hasShadowBg(false)
                .atView(parkingMore)
                .isClickThrough(false)
                .asAttachList(
                        new String[]{"积分记录", "积分等级"},
                        new int[0],
                        new OnSelectListener() {
                            @Override
                            public void onSelect(int position, String text) {
                                if (position == 0) {
                                    jumpPage(ParkingIntegralReCardActivity.class);
                                } else {

                                }
                            }
                        });


        parkingMore.setOnClickListener(view -> {
            listPopupView.show();
        });

        getIntegralList();

        integralListAdapter.setListClickListener(id -> {
            new AlertDialog.Builder(this)
                    .setMessage("确认要兑换?")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            LoadingPopupView asLoading = new XPopup.Builder(ParkingIntegralActivity.this)
                                    .dismissOnBackPressed(false)
                                    .dismissOnTouchOutside(false)
                                    .isLightNavigationBar(true)
                                    .isViewMode(true)
                                    .asLoading("兑换中");
                            asLoading.show();

                            Api.config(Constant.NetWork.PARK_CONSUME, null, ParkingIntegralActivity.this).postRestfulRequest(new RequestCallback() {
                                @Override
                                public void success(String result) {
                                    BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                                    runOnUiThread(() -> {
                                        if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                                            try {
                                                Thread.sleep(1500);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            asLoading.dismissWith(() -> {
                                                showSyncToast("兑换成功!");
                                            });
                                        } else {
                                            asLoading.dismiss();
                                            showSyncToast(baseParam.getMsg());
                                        }
                                    });
                                }

                                @Override
                                public void failure(Exception e) {
                                    showSyncToast("网络异常");
                                }
                            }, id);
                        }
                    }).create().show();
        });
    }

    private void getIntegralList() {
        Api.config(Constant.NetWork.PARK_PRODUCT_LIST, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                ParkingIntegralListParam integralListParam = gson.fromJson(result, ParkingIntegralListParam.class);
                if (integralListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<ParkingIntegralListParam.RowsDTO> listParamRows = integralListParam.getRows();
                    runOnUiThread(() -> {
                        integralListAdapter.setData(listParamRows);
                    });
                } else {
                    showSyncToast(integralListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
    }
}