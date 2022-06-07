package com.example.activity.hospitalactivity;

import android.content.Intent;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.adapter.hospitaladapter.HospitalCategoryListAdapter;
import com.example.pojo.hospitalparam.HospitalCategoryListParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author kkk
 */
public class HospitalCategoryActivity extends BaseActivity {

    private static final String TAG = "HospitalCategoryActivity";
    private Toolbar toolbar;
    private ImageView finishBtn;
    private Integer hospitalCardId;
    private RecyclerView categoryRecycler;
    private HospitalCategoryListAdapter categoryListAdapter;
    private List<HospitalCategoryListParam.RowsDTO> listParamRows;
    private Gson gson = new Gson();
    private Set<Integer> vipSet;

    @Override
    protected int initLayout() {
        return R.layout.activity_hospital_category;
    }

    @Override
    protected void initView() {
        toolbar = findViewById(R.id.hospital_toolbar);
        finishBtn = findViewById(R.id.finish_btn);
        categoryRecycler = findViewById(R.id.hospital_category_recycler);
        categoryListAdapter = new HospitalCategoryListAdapter();
    }

    @Override
    protected void initData() {
        hospitalCardId = (Integer) getIntent().getExtras().get("hospitalCardId");
        setStringToSP("hospitalCardId", String.valueOf(hospitalCardId));

        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });

        toolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        categoryRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        categoryRecycler.setAdapter(categoryListAdapter);

        Api.config(Constant.NetWork.HOSPITAL_CATEGORY, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                HospitalCategoryListParam hospitalCategoryListParam = gson.fromJson(result, HospitalCategoryListParam.class);
                if (hospitalCategoryListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    listParamRows = hospitalCategoryListParam.getRows();
                    runOnUiThread(() -> {
                        categoryListAdapter.setData(listParamRows);
                    });
                } else {
                    showSyncToast(hospitalCategoryListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });

        Map<String, Object> param = new HashMap<>();
        param.put("type", 1);
        Api.config(Constant.NetWork.HOSPITAL_CATEGORY, param, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                HospitalCategoryListParam hospitalCategoryListParam = gson.fromJson(result, HospitalCategoryListParam.class);
                if (hospitalCategoryListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<HospitalCategoryListParam.RowsDTO> vipList = hospitalCategoryListParam.getRows();
                    vipSet = new HashSet<>();
                    for (HospitalCategoryListParam.RowsDTO rowsDTO : vipList) {
                        vipSet.add(rowsDTO.getId());
                    }
                } else {
                    showSyncToast(hospitalCategoryListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });

        categoryListAdapter.setItemClickListener(index -> {
            HospitalCategoryListParam.RowsDTO rowsDTO = listParamRows.get(index);
            jumpPageToIntent(
                    new Intent(this, HospitalCategoryDetailActivity.class)
                            .putExtra("categoryName", rowsDTO.getCategoryName())
                            .putExtra("isVip", vipSet.contains(rowsDTO.getId()))
            );
            setStringToSP("categoryId", String.valueOf(rowsDTO.getId()));
            setStringToSP("money", String.valueOf(rowsDTO.getMoney()));
        });
    }
}