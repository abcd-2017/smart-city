package com.example.activity.parkingactivity;

import android.content.Intent;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.R;
import com.example.activity.BaseActivity;
import com.example.adapter.parkingadapter.ParkingLotListAdapter;
import com.example.pojo.parkingparam.ParkingLotListParam;
import com.example.pojo.parkingparam.ParkingRotationListParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kkk
 */
public class ParkingActivity extends BaseActivity {

    private RecyclerView parkingListRecycler;
    private ImageView finishBtn;
    private ImageView moreBtn;
    private Gson gson = new Gson();
    private Banner<ParkingRotationListParam.RowsDTO, BannerImageAdapter<ParkingRotationListParam.RowsDTO>> parkingBanner;
    private SmartRefreshLayout smartRefresh;
    private Integer pageNum = 1;
    private List<ParkingLotListParam.RowsDTO> list = new ArrayList<>();
    private ParkingLotListAdapter parkingLotListAdapter;
    private boolean hasNext = true;

    @Override
    protected int initLayout() {
        return R.layout.activity_parking;
    }

    @Override
    protected void initView() {
        parkingBanner = findViewById(R.id.parking_rotation_banner);
        parkingListRecycler = findViewById(R.id.parking_list_recycler);
        finishBtn = findViewById(R.id.finish_btn);
        moreBtn = findViewById(R.id.parking_more_list);
        smartRefresh = findViewById(R.id.parking_list_refresh);
        parkingLotListAdapter = new ParkingLotListAdapter();
    }

    @Override
    protected void initData() {
        finishBtn.setOnClickListener(view -> {
            finish();
        });

        parkingListRecycler.setLayoutManager(new LinearLayoutManager(this));
        parkingListRecycler.setAdapter(parkingLotListAdapter);

        Api.config(Constant.NetWork.PARK_ROTATION_LIST, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                ParkingRotationListParam rotationListParam = gson.fromJson(result, ParkingRotationListParam.class);
                if (rotationListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<ParkingRotationListParam.RowsDTO> listParamRows = rotationListParam.getRows();
                    runOnUiThread(() -> {
                        setRotationBanner(listParamRows);
                    });
                    getParkLotList();
                } else {
                    showSyncToast(rotationListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });

        smartRefresh.setEnableRefresh(false);
        smartRefresh.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Translate));
        smartRefresh.setOnLoadMoreListener(refreshLayout -> {
            pageNum++;
            refreshLayout.finishLoadMore(2000);
            getParkLotList();
        });

        parkingLotListAdapter.setParkingItemClickListener(id -> {
            ParkingLotListParam.RowsDTO rowsDTO = list.get(id);
            jumpPageToIntent(new Intent(this, ParkingLotDetailActivity.class).putExtra("parkingId", rowsDTO.getId()).putExtra("parkingName", rowsDTO.getParkName()));
        });

        moreBtn.setOnClickListener(view -> {
            new XPopup.Builder(this)
                    .hasShadowBg(false)
                    .isClickThrough(false)
                    .atView(moreBtn)
                    .asAttachList(
                            new String[]{"历史", "我的"},
                            new int[0],
                            new OnSelectListener() {
                                @Override
                                public void onSelect(int position, String text) {
                                    if (position == 0) {
                                        jumpPage(ParkingRecordsActivity.class);
                                    } else if (position == 1) {
                                        jumpPage(ParkingMyActivity.class);
                                    }
                                }
                            })
                    .show();
        });
    }

    private void setRotationBanner(List<ParkingRotationListParam.RowsDTO> list) {
        parkingBanner.setAdapter(new BannerImageAdapter<ParkingRotationListParam.RowsDTO>(list) {
            @Override
            public void onBindView(BannerImageHolder holder, ParkingRotationListParam.RowsDTO data, int position, int size) {
                holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                Glide.with(holder.itemView)
                        .load(Constant.BASE_API + data.getAdvImg())
                        .transform(new RoundedCorners(16))
                        .into(holder.imageView);
            }
        }).addBannerLifecycleObserver(this)
                .setIndicator(new CircleIndicator(this));
    }

    private void getParkLotList() {
        Map<String, Object> param = new HashMap<>();
        param.put("pageSize", Constant.pageSize_6);
        param.put("pageNum", pageNum);
        Api.config(Constant.NetWork.PARK_LOT_LIST, param, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                ParkingLotListParam lotListParam = gson.fromJson(result, ParkingLotListParam.class);
                if (lotListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<ParkingLotListParam.RowsDTO> lotListParamRows = lotListParam.getRows();
                    if (lotListParam.getRows().size() != 0 && hasNext) {
                        try {
                            if (list.size() > 0) Thread.sleep(1500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        list.addAll(lotListParamRows);
                        smartRefresh.finishLoadMore(true);
                        runOnUiThread(() -> {
                            parkingLotListAdapter.setData(list);
                        });
                    } else {
                        showSyncToast("没有更多了...");
                        smartRefresh.finishLoadMore(true);
                    }
                    hasNext = list.size() < lotListParam.getTotal();
                } else {
                    showSyncToast(lotListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
                smartRefresh.finishLoadMore(true);
            }
        });
    }
}