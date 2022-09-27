package com.example.activity.takeoutactivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.adapter.MyFragmentAdapter;
import com.example.fragment.takeoutfragment.TakeOutSellerCommentFragment;
import com.example.fragment.takeoutfragment.TakeOutSellerMenuFragment;
import com.example.pojo.BaseParam;
import com.example.pojo.takeoutparam.TakeOutProductListParam;
import com.example.pojo.takeoutparam.TakeOutSellerCheckParam;
import com.example.pojo.takeoutparam.TakeOutSellerDetailParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.example.view.takeoutview.TakeOutCommentPopup;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kkk
 */
public class TakeOutSellerDetailActivity extends BaseActivity {

    private ImageView finishBtn, detailCover;
    private Toolbar takeOutToolbar;
    private TextView detailAddress, detailName, detailCollect, detailTag, detailScore, detailMenuText, detailCommentText;
    private CardView detailMenu, detailComment;
    private ViewPager2 detailViewPager;
    private LinearLayout detailCartBtn, detailPaymentBtn, detailMenuCard, detailCommentCard;
    private Integer sellerId;
    private Gson gson = new Gson();
    private Map<String, Object> param = new HashMap<>();
    private Boolean isCollect = false;
    private Integer currCategory = null;
    //购物车集合
    private Map<Integer, TakeOutProductListParam.DataDTO> productCar = new HashMap<>();

    @Override

    protected int initLayout() {
        return R.layout.activity_take_out_seller_detail;
    }

    @Override
    protected void initView() {
        finishBtn = findViewById(R.id.finish_btn);
        takeOutToolbar = findViewById(R.id.take_out_toolbar);
        detailAddress = findViewById(R.id.take_out_seller_detail_address);
        detailName = findViewById(R.id.take_out_seller_detail_name);
        detailCollect = findViewById(R.id.take_out_seller_detail_collect);
        detailCover = findViewById(R.id.take_out_seller_detail_cover);
        detailTag = findViewById(R.id.take_out_seller_detail_tag);
        detailScore = findViewById(R.id.take_out_seller_detail_score);
        detailMenu = findViewById(R.id.take_out_seller_detail_menu);
        detailMenuText = findViewById(R.id.take_out_seller_detail_menu_text);
        detailComment = findViewById(R.id.take_out_seller_detail_comment);
        detailCommentText = findViewById(R.id.take_out_seller_detail_comment_text);
        detailViewPager = findViewById(R.id.take_out_seller_detail_viewpager);
        detailCartBtn = findViewById(R.id.take_out_seller_detail_cart_btn);
        detailPaymentBtn = findViewById(R.id.take_out_seller_detail_payment_btn);
        detailMenuCard = findViewById(R.id.take_out_seller_detail_menu_card);
        detailCommentCard = findViewById(R.id.take_out_seller_detail_comment_card);
    }

    @Override
    protected void initData() {
        takeOutToolbar.setNavigationOnClickListener(view -> {
            finish();
        });
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });

        Bundle extras = getIntent().getExtras();
        sellerId = (Integer) extras.get("sellerId");
        if (extras.get("categoryId") != null) {
            currCategory = (Integer) extras.get("categoryId");
        }

        toggleTitleStyle(detailMenuText, detailMenu, detailCommentText, detailComment);

        //viewpager设置fragment
        List<Fragment> fragmentList = new ArrayList<>(Arrays.asList(TakeOutSellerMenuFragment.newInstance(), TakeOutSellerCommentFragment.newInstance()));
        detailViewPager.setAdapter(new MyFragmentAdapter(this, fragmentList));

        //获取商品页对象
        TakeOutSellerMenuFragment productFragment = (TakeOutSellerMenuFragment) fragmentList.get(0);

        //判断店铺是否收藏
        param.put("sellerId", sellerId);
        Api.config(Constant.NetWork.TAKE_OUT_COLLECT_CHECK, param, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                TakeOutSellerCheckParam sellerCheckParam = gson.fromJson(result, TakeOutSellerCheckParam.class);
                if (sellerCheckParam.getCode() == HttpURLConnection.HTTP_OK) {
                    isCollect = sellerCheckParam.getIsCollect();
                    runOnUiThread(() -> {
                        if (isCollect) {
                            detailCollect.setText("已收藏");
                        } else {
                            detailCollect.setText("收藏");
                        }
                    });
                } else {
                    showSyncToast(sellerCheckParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });

        //店铺详情
        Api.config(Constant.NetWork.TAKE_OUT_SELLER, null, this).getRestfulRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                TakeOutSellerDetailParam sellerDetailParam = gson.fromJson(result, TakeOutSellerDetailParam.class);
                if (sellerDetailParam.getCode() == HttpURLConnection.HTTP_OK) {
                    TakeOutSellerDetailParam.DataDTO detailParamData = sellerDetailParam.getData();
                    runOnUiThread(() -> {
                        detailName.setText(detailParamData.getName());
                        detailAddress.setText(detailParamData.getAddress());
                        detailScore.setText(String.format("%s", detailParamData.getScore()));
                        setStringToSP("take_out_seller_name", detailParamData.getName());
                        if (TextUtils.isEmpty(detailParamData.getIntroduction()) || detailParamData.getIntroduction().length() > 10) {
                            detailTag.setVisibility(View.GONE);
                        } else {
                            detailTag.setText(detailParamData.getIntroduction());
                        }

                        Glide.with(TakeOutSellerDetailActivity.this)
                                .load(Constant.BASE_API + detailParamData.getImgUrl())
                                .transform(new RoundedCorners(4))
                                .into(detailCover);
                    });
                } else {
                    showSyncToast(sellerDetailParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        }, sellerId);

        //更换收藏
        detailCollect.setOnClickListener(view -> {
            param.clear();
            param.put("sellerId", sellerId);

            if (isCollect) {
                Api.config(Constant.NetWork.TAKE_OUT_COLLECT, null, this).deleteRestfulRequest(collectCallback, sellerId);
            } else {
                Api.config(Constant.NetWork.TAKE_OUT_COLLECT, param, this).postRequest(collectCallback);
            }
        });

        //购物车弹窗
        TakeOutCommentPopup takeOutCommentPopup = new TakeOutCommentPopup(this);
        BasePopupView basePopupView = new XPopup.Builder(this)
                .asCustom(takeOutCommentPopup);

        //此activity与弹窗之间的通信
        takeOutCommentPopup.setActivityCallback(new ShowCarPopupCallback() {
            @Override
            public void getDataToPopup(Map<Integer, TakeOutProductListParam.DataDTO> productCar) {
                productFragment.setValue(productCar);
            }

            @Override
            public void closePopupToNewPage(Map<Integer, TakeOutProductListParam.DataDTO> productList) {
                productCar.clear();
                productCar.putAll(productList);
                if (productCar.size() > 0) {
                    takeOutCommentPopup.dismiss();
                }
                settlementOrder();
            }

            @Override
            public Map<Integer, TakeOutProductListParam.DataDTO> setDataToPopup() {
                return productFragment.getValue();
            }
        });

        //购物车按钮
        detailCartBtn.setOnClickListener(view -> {
            basePopupView.show();
        });

        //结算按钮
        detailPaymentBtn.setOnClickListener(view -> {
            productCar = productFragment.getValue();
            settlementOrder();
        });

        //点击点菜
        detailMenuCard.setOnClickListener(view -> {
            detailViewPager.setCurrentItem(0);
            toggleTitleStyle(detailMenuText, detailMenu, detailCommentText, detailComment);
        });

        //点击评论
        detailCommentCard.setOnClickListener(view -> {
            detailViewPager.setCurrentItem(1);
            toggleTitleStyle(detailCommentText, detailComment, detailMenuText, detailMenu);
        });

        //viewpager切换事件
        detailViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    int currentItem = detailViewPager.getCurrentItem();
                    if (currentItem == 0) {
                        detailViewPager.setCurrentItem(0);
                        toggleTitleStyle(detailMenuText, detailMenu, detailCommentText, detailComment);
                    } else if (currentItem == 1) {
                        detailViewPager.setCurrentItem(1);
                        toggleTitleStyle(detailCommentText, detailComment, detailMenuText, detailMenu);
                    }
                }
            }
        });


    }

    private void toggleTitleStyle(TextView showText, CardView showCard, TextView hiddenText, CardView hiddenCard) {
        showText.getPaint().setFakeBoldText(true);
        showText.setTextColor(Color.parseColor("#333333"));
        showCard.setCardBackgroundColor(Color.parseColor("#ffd161"));

        hiddenText.getPaint().setFakeBoldText(false);
        hiddenText.setTextColor(Color.parseColor("#757575"));
        hiddenCard.setCardBackgroundColor(Color.WHITE);
    }

    //留给子fragment拿到店铺id
    public int getSellerId() {
        return sellerId;
    }

    public Integer getCategoryId() {
        return currCategory;
    }

    //收藏按钮回调函数
    RequestCallback collectCallback = new RequestCallback() {
        @Override
        public void success(String result) {
            BaseParam baseParam = gson.fromJson(result, BaseParam.class);
            if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                showSyncToast("操作成功");
                runOnUiThread(() -> {
                    if (isCollect) {
                        detailCollect.setText("收藏");
                    } else {
                        detailCollect.setText("已收藏");
                    }
                });
                isCollect = !isCollect;
            } else {
                showSyncToast(baseParam.getMsg());
            }
        }

        @Override
        public void failure(Exception e) {
            showSyncToast("网络异常");
        }
    };

    //显示弹窗给弹窗传值
    public interface ShowCarPopupCallback {
        Map<Integer, TakeOutProductListParam.DataDTO> setDataToPopup();

        void getDataToPopup(Map<Integer, TakeOutProductListParam.DataDTO> productCar);

        //弹窗跳转到结算页面，经过父activity来周转跳
        void closePopupToNewPage(Map<Integer, TakeOutProductListParam.DataDTO> productCar);
    }

    //订单结算
    public void settlementOrder() {
        if (productCar.size() == 0) {
            showSyncToast("请选选择商品！");
            return;
        }
        String productCarJson = gson.toJson(productCar.values());
        setStringToSP("take_out_product_car", productCarJson);
        setStringToSP("take_out_seller_id", String.valueOf(sellerId));

        jumpPage(TakeOutOrderSettlementActivity.class);
    }
}