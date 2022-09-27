package com.example.activity.takeoutactivity;

import android.content.Intent;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.adapter.takeoutadapter.TakeOutCollectListAdapter;
import com.example.pojo.takeoutparam.TakeOutCollectListParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.util.List;

/**
 * @author kkk
 */
public class TakeOutCollectActivity extends BaseActivity {

    private TakeOutCollectListAdapter collectListAdapter;
    private ImageView finishBtn;
    private Toolbar takeOutToolbar;
    private RecyclerView collectRecycler;

    @Override
    protected int initLayout() {
        return R.layout.activity_take_out_collect;
    }

    @Override
    protected void initView() {
        finishBtn = findViewById(R.id.finish_btn);
        takeOutToolbar = findViewById(R.id.take_out_toolbar);
        collectRecycler = findViewById(R.id.take_out_collect_recycler);
        collectListAdapter = new TakeOutCollectListAdapter();
    }

    @Override
    protected void initData() {
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });
        takeOutToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        collectRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        collectRecycler.setAdapter(collectListAdapter);

        Api.config(Constant.NetWork.TAKE_OUT_COLLECT_LIST, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                TakeOutCollectListParam takeOutCollectListParam = gson.fromJson(result, TakeOutCollectListParam.class);
                if (takeOutCollectListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<TakeOutCollectListParam.RowsDTO> collectListParamRows = takeOutCollectListParam.getRows();
                    runOnUiThread(() -> {
                        collectListAdapter.setData(collectListParamRows);
                    });
                } else {
                    showSyncToast(takeOutCollectListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
    }
}