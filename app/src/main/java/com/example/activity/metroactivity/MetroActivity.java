package com.example.activity.metroactivity;

import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.R;
import com.example.activity.BaseActivity;
import com.example.adapter.metroadapter.MetroListAdapter;
import com.example.pojo.metroparam.MetroListParam;
import com.example.pojo.metroparam.MetroRotationImageParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kkk
 */
public class MetroActivity extends BaseActivity {

    private static final String TAG = "MetroActivity";
    private ImageView finishBtn;
    private Banner<MetroRotationImageParam.RowsDTO, BannerImageAdapter<MetroRotationImageParam.RowsDTO>> metroBanner;
    private List<MetroRotationImageParam.RowsDTO> imageParamRows;
    private RecyclerView metroListRecycler;
    private CardView metroLocationBtn, rideCodeBtn, loadAndFoundBtn, announcementBtn;
    private TextView stepName;
    private String currentName;
    private MetroListAdapter metroListAdapter;

    @Override
    protected int initLayout() {
        return R.layout.activity_metro;
    }

    @Override
    protected void initView() {
        finishBtn = findViewById(R.id.finish_btn);
        metroBanner = findViewById(R.id.metro_banner);
        metroLocationBtn = findViewById(R.id.metro_location);
        metroListRecycler = findViewById(R.id.metro_list_recycler);
        rideCodeBtn = findViewById(R.id.ride_code_btn);
        loadAndFoundBtn = findViewById(R.id.load_and_found_btn);
        announcementBtn = findViewById(R.id.metro_announcement_btn);
        stepName = findViewById(R.id.step_name);
        currentName = this.stepName.getText().toString();
        metroListAdapter = new MetroListAdapter();
    }

    @Override
    protected void initData() {
        metroListRecycler.setLayoutManager(new LinearLayoutManager(this));
        metroListRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        metroListRecycler.setAdapter(metroListAdapter);

        finishBtn.setOnClickListener(view -> {
            finish();
        });

        Api.config(Constant.NetWork.METRO_ROTATION, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                MetroRotationImageParam rotationImageParam = gson.fromJson(result, MetroRotationImageParam.class);
                if (rotationImageParam.getCode() == HttpURLConnection.HTTP_OK) {
                    imageParamRows = rotationImageParam.getRows();
                    runOnUiThread(() -> {
                        setBannerImage();
                    });
                } else {
                    showSyncToast(rotationImageParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });

        getAllMetroList();

        metroListAdapter.setItemClickListener(id -> {
            jumpPageToIntent(new Intent(this, MetroLineDetailsActivity.class).putExtra("metroId", id).putExtra("currMetro", currentName));
        });

        rideCodeBtn.setOnClickListener(view -> {
            jumpPage(RailCardActivity.class);
        });

        loadAndFoundBtn.setOnClickListener(view -> {
            jumpPage(LastAndFoundActivity.class);
        });

        announcementBtn.setOnClickListener(view -> {
            jumpPage(AnnouncementActivity.class);
        });
    }

    private void setBannerImage() {
        metroBanner.setAdapter(new BannerImageAdapter<MetroRotationImageParam.RowsDTO>(imageParamRows) {
            @Override
            public void onBindView(BannerImageHolder holder, MetroRotationImageParam.RowsDTO data, int position, int size) {
                Glide.with(holder.itemView)
                        .load(Constant.BASE_API + data.getAdvImg())
                        .transform(new RoundedCorners(20))
                        .into(holder.imageView);
            }
        }).addBannerLifecycleObserver(this)
                .setIndicator(new CircleIndicator(this));
    }

    private void getAllMetroList() {
        Map<String, Object> param = new HashMap<>();
        param.put("currentName", currentName);
        Log.d(TAG, "getAllMetroList: a" + currentName);
        Api.config(Constant.NetWork.METRO_LIST, param, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                MetroListParam metroListParam = gson.fromJson(result, MetroListParam.class);
                if (metroListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<MetroListParam.DataDTO> metroListParamData = metroListParam.getData();
                    Log.d(TAG, "success: " + metroListParamData);
                    runOnUiThread(() -> {
                        metroListAdapter.setData(metroListParamData);
                    });
                } else {
                    showSyncToast(metroListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
    }
}