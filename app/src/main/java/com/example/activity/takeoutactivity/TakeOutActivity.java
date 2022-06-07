package com.example.activity.takeoutactivity;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.R;
import com.example.activity.BaseActivity;
import com.example.adapter.takeoutadapter.TakeOutSellerListAdapter;
import com.example.adapter.takeoutadapter.TakeOutThemeListAdapter;
import com.example.pojo.takeoutparam.TakeOutRotationListParam;
import com.example.pojo.takeoutparam.TakeOutSellerListParam;
import com.example.pojo.takeoutparam.TakeOutThemeListParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.AttachListPopupView;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.BaseIndicator;

import java.net.HttpURLConnection;
import java.util.List;

/**
 * @author kkk
 */
public class TakeOutActivity extends BaseActivity {

    private SearchView takeOutSearch;
    private ImageView finishBtn, moreBtn;
    private TextView searchSeller;
    private Banner<TakeOutRotationListParam.RowsDTO, BannerImageAdapter<TakeOutRotationListParam.RowsDTO>> takeOutRotation;
    private RecyclerView themeRecycler, sellerRecycler;
    private TakeOutThemeListAdapter outThemeListAdapter;
    private TakeOutSellerListAdapter outSellerListAdapter;
    private final Gson gson = new Gson();

    @Override
    protected int initLayout() {
        return R.layout.activity_take_out;
    }

    @Override
    protected void initView() {
        finishBtn = findViewById(R.id.finish_btn);
        moreBtn = findViewById(R.id.take_out_more);
        searchSeller = findViewById(R.id.take_out_search_seller);
        takeOutRotation = findViewById(R.id.take_out_rotation);
        themeRecycler = findViewById(R.id.take_out_theme_recycler);
        sellerRecycler = findViewById(R.id.take_out_seller_recycler);
        outThemeListAdapter = new TakeOutThemeListAdapter();
        outSellerListAdapter = new TakeOutSellerListAdapter();
    }

    @Override
    protected void initData() {
        finishBtn.setOnClickListener(view -> {
            finish();
        });

        themeRecycler.setLayoutManager(new GridLayoutManager(this, 5));
        themeRecycler.setAdapter(outThemeListAdapter);

        sellerRecycler.setLayoutManager(new LinearLayoutManager(this));
        sellerRecycler.setAdapter(outSellerListAdapter);

        //获取轮播图
        Api.config(Constant.NetWork.TAKE_OUT_ROTATION, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                TakeOutRotationListParam outThemeListParam = gson.fromJson(result, TakeOutRotationListParam.class);
                if (outThemeListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<TakeOutRotationListParam.RowsDTO> themeListParamData = outThemeListParam.getRows();
                    runOnUiThread(() -> {
                        setRotationImage(themeListParamData);
                    });
                } else {
                    showSyncToast(outThemeListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });

        //获取分类
        Api.config(Constant.NetWork.TAKE_OUT_THEME_LIST, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                TakeOutThemeListParam outThemeListParam = gson.fromJson(result, TakeOutThemeListParam.class);
                if (outThemeListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<TakeOutThemeListParam.DataDTO> themeListParamData = outThemeListParam.getData();
                    runOnUiThread(() -> {
                        outThemeListAdapter.setData(themeListParamData);
                    });
                } else {
                    showSyncToast(outThemeListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });

        //获取附近商家
        Api.config(Constant.NetWork.TAKE_OUT_SELLER_NEAR, null, this).getRestfulRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                TakeOutSellerListParam outSellerListParam = gson.fromJson(result, TakeOutSellerListParam.class);
                if (outSellerListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<TakeOutSellerListParam.RowsDTO> sellerListParamRows = outSellerListParam.getRows();
                    runOnUiThread(() -> {
                        outSellerListAdapter.setData(sellerListParamRows);
                    });
                } else {
                    showSyncToast(outSellerListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });

        //设置列表点击事件
        outSellerListAdapter.setSellerItemClickListener(id -> {
            jumpPageToIntent(new Intent(this, TakeOutSellerDetailActivity.class)
                    .putExtra("sellerId", id));
        });

        outThemeListAdapter.setThemeItemClickListener(id -> {
            jumpPageToIntent(new Intent(this, TakeOutSearchSellerActivity.class)
                    .putExtra("searchType", false)
                    .putExtra("themeId", id));
        });

        //点击搜索框跳转到新界面
        searchSeller.setOnClickListener(view -> {
            jumpPageToIntent(new Intent(this, TakeOutSearchSellerActivity.class)
                    .putExtra("searchType", true));
        });

        //更多选项
        AttachListPopupView listPopupView = new XPopup.Builder(this)
                .atView(moreBtn)
                .hasShadowBg(false)
                .asAttachList(new String[]{"我的收藏", "我的订单"}, new int[0], (position, text) -> {
                    if (position == 0) {
                        jumpPage(TakeOutCollectActivity.class);
                    } else {
                        jumpPage(TakeOutOrderActivity.class);
                    }
                });

        moreBtn.setOnClickListener(view -> {
            listPopupView.show();
        });
    }

    private void setRotationImage(List<TakeOutRotationListParam.RowsDTO> themeListParamData) {
        takeOutRotation.setAdapter(new BannerImageAdapter<TakeOutRotationListParam.RowsDTO>(themeListParamData) {
            @Override
            public void onBindView(BannerImageHolder holder, TakeOutRotationListParam.RowsDTO data, int position, int size) {
                holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                Glide.with(holder.itemView.getContext())
                        .load(Constant.BASE_API + data.getAdvImg())
                        .transform(new RoundedCorners(16))
                        .into(holder.imageView);

            }
        }).addBannerLifecycleObserver(this)
                .setIndicator(new BaseIndicator(this));
    }
}