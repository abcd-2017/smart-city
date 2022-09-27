package com.example.activity.busactivity;

import android.content.Intent;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.adapter.busadapter.BusListAdapter;
import com.example.dbhelper.bus.BusListDBHelper;
import com.example.pojo.busparam.BusListParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.AttachListPopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;

import java.net.HttpURLConnection;
import java.util.List;

/**
 * @author kkk
 */
public class BusActivity extends BaseActivity {

    private ImageView moteList;
    private ImageView finishBtn;
    private RecyclerView transitRecycler;
    private BusListAdapter busListAdapter;
    private BusListDBHelper busListDBHelper;

    @Override
    protected int initLayout() {
        return R.layout.activity_transit;
    }

    @Override
    protected void initView() {
        moteList = findViewById(R.id.transit_more_list);
        finishBtn = findViewById(R.id.finish_btn);
        transitRecycler = findViewById(R.id.transit_list_recycler);
        busListAdapter = new BusListAdapter();
        busListDBHelper = new BusListDBHelper(this);
    }

    @Override
    protected void initData() {
        finishBtn.setOnClickListener(view -> {
            finish();
        });

        transitRecycler.setLayoutManager(new LinearLayoutManager(this));
        transitRecycler.setAdapter(busListAdapter);

        busListAdapter.setBusItemClickListener(rowsDTO -> {
            jumpPageToIntent(new Intent(this, BusDetailActivity.class)
                    .putExtra("linesId", rowsDTO.getId())
                    .putExtra("linesName", rowsDTO.getName())
                    .putExtra("price", rowsDTO.getPrice()));
        });

        getBusList();

        AttachListPopupView listPopupView = new XPopup.Builder(this)
                .hasShadowBg(false)
                .atView(moteList)
                .asAttachList(new String[]{"我的订单"}, new int[0],
                        new OnSelectListener() {
                            @Override
                            public void onSelect(int position, String text) {
                                jumpPage(BusOrderActivity.class);
                            }
                        });

        moteList.setOnClickListener(view -> {
            listPopupView.show();
        });
    }

    private void getBusList() {
        long count = busListDBHelper.queryCount();
        if (count > 0) {
            List<BusListParam.RowsDTO> listParamRows = busListDBHelper.query();
            busListAdapter.setData(listParamRows);
        } else {
            Api.config(Constant.NetWork.BUS_LINE_LIST, null, this).getRequest(new RequestCallback() {
                @Override
                public void success(String result) {
                    Gson gson = new Gson();
                    BusListParam busListParam = gson.fromJson(result, BusListParam.class);
                    if (busListParam.getCode() == HttpURLConnection.HTTP_OK) {
                        List<BusListParam.RowsDTO> listParamRows = busListParam.getRows();
                        runOnUiThread(() -> {
                            busListAdapter.setData(listParamRows);
                        });
                        busListDBHelper.insert(listParamRows);
                    } else {
                        showSyncToast(busListParam.getMsg());
                    }
                }

                @Override
                public void failure(Exception e) {
                    showSyncToast("网络异常");
                }
            });
        }
    }
}