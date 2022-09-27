package com.example.activity.metroactivity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.adapter.metroadapter.LastFoundListAdapter;
import com.example.pojo.metroparam.LastFoundListParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class LastAndFoundActivity extends BaseActivity {

    private static final String TAG = "LastAndFoundActivity";
    private Toolbar metroToolbar;
    private ImageView finishBtn;
    private RecyclerView foundRecycler;
    private Spinner foundSpinner;
    private List<LastFoundListParam.DataDTO> foundListParamData;
    private LastFoundListAdapter foundListAdapter;

    @Override
    protected int initLayout() {
        return R.layout.activity_last_and_found;
    }

    @Override
    protected void initView() {
        metroToolbar = findViewById(R.id.metro_toolbar);
        finishBtn = findViewById(R.id.finish_btn);
        foundRecycler = findViewById(R.id.last_found_recycler);
        foundSpinner = findViewById(R.id.last_found_spinner);
        foundListAdapter = new LastFoundListAdapter();
    }

    @Override
    protected void initData() {
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });
        metroToolbar.setNavigationOnClickListener(view -> {
            finish();
        });
        Api.config(Constant.NetWork.GET_LAST_FOUND_LIST, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                LastFoundListParam lastFoundListParam = gson.fromJson(result, LastFoundListParam.class);
                if (lastFoundListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    foundListParamData = lastFoundListParam.getData();
                    runOnUiThread(() -> {
                        getSpinnerList();
                    });
                } else {
                    showSyncToast(lastFoundListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });

        foundRecycler.setLayoutManager(new LinearLayoutManager(this));
        foundRecycler.setAdapter(foundListAdapter);
        foundRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void getSpinnerList() {
        List<String> arrays = new ArrayList<>();
        for (int i = foundListParamData.size() - 1; i >= 0; i--) {
            arrays.add(foundListParamData.get(i).getPublishDate());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_select, arrays);
        adapter.setDropDownViewResource(R.layout.item_dropdown);
        foundSpinner.setAdapter(adapter);
        foundSpinner.setSelection(0);
        foundSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                foundListAdapter.setData(foundListParamData.get(position).getMetroFoundList());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}