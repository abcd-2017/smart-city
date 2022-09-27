package com.example.activity.hospitalactivity;

import android.content.Intent;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.adapter.hospitaladapter.HospitalPatientListAdapter;
import com.example.pojo.hospitalparam.HospitalPatientListParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.util.List;

/**
 * @author kkk
 */
public class HospitalCardActivity extends BaseActivity {

    private ImageView finishBtn;
    private Toolbar toolbar;
    private RecyclerView cardRecycler;
    private CardView addCard;
    private HospitalPatientListAdapter patientListAdapter;
    private List<HospitalPatientListParam.RowsDTO> listParamRows;
    private Gson gson = new Gson();

    @Override
    protected int initLayout() {
        return R.layout.activity_hospital_card;
    }

    @Override
    protected void initView() {
        finishBtn = findViewById(R.id.finish_btn);
        toolbar = findViewById(R.id.hospital_card_toolbar);
        cardRecycler = findViewById(R.id.hospital_card_recycler);
        addCard = findViewById(R.id.add_hospital_card);
        patientListAdapter = new HospitalPatientListAdapter();
    }

    @Override
    protected void initData() {
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });

        toolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        addCard.setOnClickListener(view -> {
            jumpPageToIntent(new Intent(this, AddHospitalCardActivity.class).putExtra("status", -1));
        });

        cardRecycler.setAdapter(patientListAdapter);
        cardRecycler.setLayoutManager(new LinearLayoutManager(this));

        Api.config(Constant.NetWork.HOSPITAL_PATIENT_LIST, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                HospitalPatientListParam patientListParam = gson.fromJson(result, HospitalPatientListParam.class);
                if (patientListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    listParamRows = patientListParam.getRows();
                    runOnUiThread(() -> {
                        patientListAdapter.setData(listParamRows);
                    });
                } else {
                    showSyncToast(patientListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });

        patientListAdapter.setItemClickListener(new HospitalPatientListAdapter.ItemClickListener() {
            @Override
            public void clickCard(Integer index) {
                Intent intent = new Intent(HospitalCardActivity.this, AddHospitalCardActivity.class);
                intent.putExtra("status", 1);
                intent.putExtra("cardDetail", gson.toJson(listParamRows.get(index)));
                jumpPageToIntent(intent);
            }

            @Override
            public void clickNext(Integer id, String name) {
                setStringToSP("patientName", name);
                jumpPageToIntent(new Intent(HospitalCardActivity.this, HospitalCategoryActivity.class).putExtra("hospitalCardId", id));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}