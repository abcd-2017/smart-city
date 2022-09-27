package com.example.activity.expenseactivity;

import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.R;
import com.example.activity.BaseActivity;
import com.example.adapter.expensesadapter.ExpensesPayCategoryListAdapter;
import com.example.pojo.expenses.ExpensesPayCategoryListParam;
import com.example.pojo.expenses.ExpensesRotationListParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.AttachListPopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.net.HttpURLConnection;
import java.util.List;

public class ExpensesActivity extends BaseActivity {

    private ImageView finishBtn;
    private Banner<ExpensesRotationListParam.RowsDTO, BannerImageAdapter<ExpensesRotationListParam.RowsDTO>> expensesBanner;
    private RecyclerView payCategoryRecycler;
    private ExpensesPayCategoryListAdapter categoryListAdapter;
    private Gson gson = new Gson();
    private ImageView moreList;
    private LinearLayout weatherBtn;

    @Override
    protected int initLayout() {
        return R.layout.activity_expenses;
    }

    @Override
    protected void initView() {
        finishBtn = findViewById(R.id.finish_btn);
        expensesBanner = findViewById(R.id.expenses_banner);
        payCategoryRecycler = findViewById(R.id.expenses_pay_list_recycler);
        moreList = findViewById(R.id.expenses_more_list);
        weatherBtn = findViewById(R.id.expenses_weather_btn);
        categoryListAdapter = new ExpensesPayCategoryListAdapter();
    }

    @Override
    protected void initData() {
        finishBtn.setOnClickListener(view -> {
            finish();
        });

        payCategoryRecycler.setLayoutManager(new LinearLayoutManager(this));
        payCategoryRecycler.setAdapter(categoryListAdapter);

        getDate();

        categoryListAdapter.setItemClickListener(id -> {
            if (id == 1) {
                showSyncToast("暂未开放! ");
            } else {
                setStringToSP("expenses_category_id", String.valueOf(id));
                jumpPage(ExpensesPaymentListActivity.class);
            }
        });

        AttachListPopupView listPopupView = new XPopup.Builder(this)
                .hasShadowBg(false)
                .atView(moreList)
                .asAttachList(new String[]{"缴费记录"}, new int[0],
                        new OnSelectListener() {
                            @Override
                            public void onSelect(int position, String text) {
                                jumpPage(ExpensesReCordActivity.class);
                            }
                        });

        moreList.setOnClickListener(view -> {
            listPopupView.show();
        });

        weatherBtn.setOnClickListener(view -> {
            jumpPage(ExpensesWeatherForecastActivity.class);
        });
    }

    //获取数据
    private void getDate() {
        Api.config(Constant.NetWork.LIVING_ROTATION, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                ExpensesRotationListParam rotationListParam = gson.fromJson(result, ExpensesRotationListParam.class);
                if (rotationListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<ExpensesRotationListParam.RowsDTO> listParamRows = rotationListParam.getRows();
                    runOnUiThread(() -> {
                        initBanner(listParamRows);
                    });
                } else {
                    showSyncToast(rotationListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });

        Api.config(Constant.NetWork.LIVING_CATEGORY_LIST, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                ExpensesPayCategoryListParam categoryListParam = gson.fromJson(result, ExpensesPayCategoryListParam.class);
                if (categoryListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<ExpensesPayCategoryListParam.DataDTO> listParamData = categoryListParam.getData();
                    runOnUiThread(() -> {
                        categoryListAdapter.setData(listParamData);
                    });
                } else {
                    showSyncToast(categoryListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
    }

    private void initBanner(List<ExpensesRotationListParam.RowsDTO> listParamRows) {
        expensesBanner.setAdapter(new BannerImageAdapter<ExpensesRotationListParam.RowsDTO>(listParamRows) {
            @Override
            public void onBindView(BannerImageHolder holder, ExpensesRotationListParam.RowsDTO data, int position, int size) {
                holder.imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

                Glide.with(holder.itemView.getContext())
                        .load(Constant.BASE_API + data.getAdvImg())
                        .transform(new RoundedCorners(16))
                        .into(holder.imageView);
            }
        }).addBannerLifecycleObserver(this)
                .setIndicator(new CircleIndicator(this));
    }
}