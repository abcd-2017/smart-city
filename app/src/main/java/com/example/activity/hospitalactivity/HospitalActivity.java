package com.example.activity.hospitalactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.adapter.hospitaladapter.HospitalListAdapter;
import com.example.pojo.hospitalparam.HospitalListParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kkk
 */
public class HospitalActivity extends BaseActivity {

    private static final String TAG = "HospitalActivity";
    private ImageView finishBtn;
    private EditText searchHospitalName;
    private RecyclerView hospitalRecycler;
    private CardView reservationBtn;
    private HospitalListAdapter hospitalListAdapter;
    private ImageView clearImage;
    private Map<String, Object> param = new HashMap<>();
    private TextView emptyMessage;

    @Override
    protected int initLayout() {
        return R.layout.activity_hospital;
    }

    @Override
    protected void initView() {
        finishBtn = findViewById(R.id.finish_btn);
        searchHospitalName = findViewById(R.id.search_hospital_name);
        hospitalRecycler = findViewById(R.id.hospital_list_recycler);
        reservationBtn = findViewById(R.id.reservation_btn);
        clearImage = findViewById(R.id.clear_search_name);
        emptyMessage = findViewById(R.id.empty_message);
        hospitalListAdapter = new HospitalListAdapter();
        Bundle extras = getIntent().getExtras();
    }

    @Override
    protected void initData() {
        hospitalRecycler.setLayoutManager(new LinearLayoutManager(this));
        hospitalRecycler.setAdapter(hospitalListAdapter);

        finishBtn.setOnClickListener(view -> {
            finish();
        });

        getHospitalList();

        searchHospitalName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                clearImage.setVisibility(View.VISIBLE);
                getHospitalList();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                return false;
            }
        });

        clearImage.setOnClickListener(view -> {
            clearImage.setVisibility(View.GONE);
            searchHospitalName.setText("");
            getHospitalList();
        });
        hospitalListAdapter.setItemClickListener(id -> {
            jumpPageToIntent(new Intent(this, HospitalDetailActivity.class).putExtra("hospitalId", id));
        });

        reservationBtn.setOnClickListener(view -> {
            jumpPage(HospitalReservationListActivity.class);
        });
    }

    private void getHospitalList() {
        param.put("hospitalName", searchHospitalName.getText().toString());
        Api.config(Constant.NetWork.HOSPITAL_LIST, param, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                HospitalListParam hospitalListParam = gson.fromJson(result, HospitalListParam.class);
                if (hospitalListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<HospitalListParam.RowsDTO> listParamRows = hospitalListParam.getRows();
                    runOnUiThread(() -> {
                        if (listParamRows.size() == 0) {
                            emptyMessage.setVisibility(View.VISIBLE);
                            hospitalRecycler.setVisibility(View.GONE);
                        } else {
                            hospitalRecycler.setVisibility(View.VISIBLE);
                            emptyMessage.setVisibility(View.GONE);
                            hospitalListAdapter.setData(listParamRows);
                        }
                    });
                } else {
                    showSyncToast(hospitalListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
    }
}