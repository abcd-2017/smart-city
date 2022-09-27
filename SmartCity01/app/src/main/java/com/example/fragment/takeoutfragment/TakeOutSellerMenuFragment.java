package com.example.fragment.takeoutfragment;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.R;
import com.example.activity.takeoutactivity.TakeOutSellerDetailActivity;
import com.example.adapter.takeoutadapter.TakeOutSellerDetailProductAdapter;
import com.example.adapter.takeoutadapter.TakeOutSellerMenuAdapter;
import com.example.fragment.BaseFragment;
import com.example.pojo.takeoutparam.TakeOutProductCategoryParam;
import com.example.pojo.takeoutparam.TakeOutProductListParam;
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
public class TakeOutSellerMenuFragment extends BaseFragment {
    private RecyclerView menuRecycler, productRecycler;
    private CardView detailAddBtn;
    private LinearLayout detailCloseBtn;
    private ImageView detailCover;
    private TextView detailName, detailPrice, detailSale;
    private TakeOutSellerMenuAdapter takeOutSellerMenuAdapter;
    private TakeOutSellerDetailProductAdapter detailProductAdapter;
    private TakeOutSellerDetailActivity parentActivity;
    private final Gson gson = new Gson();
    private Integer categoryId;
    private final Map<String, Object> param = new HashMap<>();
    private Map<Integer, TakeOutProductListParam.DataDTO> productCar = new HashMap<>();
    private View inflate;

    public static TakeOutSellerMenuFragment newInstance() {
        return new TakeOutSellerMenuFragment();
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_take_out_seller_menu;
    }

    @Override
    protected void initView() {
        menuRecycler = mContent.findViewById(R.id.take_out_seller_menu_recycler);
        productRecycler = mContent.findViewById(R.id.take_out_seller_product_recycler);
        takeOutSellerMenuAdapter = new TakeOutSellerMenuAdapter();
        detailProductAdapter = new TakeOutSellerDetailProductAdapter();
        parentActivity = (TakeOutSellerDetailActivity) getActivity();

        inflate = LayoutInflater.from(mContent.getContext())
                .inflate(R.layout.alert_take_out_product_detail, null, false);
        detailAddBtn = inflate.findViewById(R.id.alert_take_out_produce_detail_add_btn);
        detailCloseBtn = inflate.findViewById(R.id.alert_take_out_produce_detail_close_btn);
        detailCover = inflate.findViewById(R.id.alert_take_out_produce_detail_cover);
        detailName = inflate.findViewById(R.id.alert_take_out_produce_detail_name);
        detailPrice = inflate.findViewById(R.id.alert_take_out_produce_detail_price);
        detailSale = inflate.findViewById(R.id.alert_take_out_produce_detail_sale);
    }

    @Override
    protected void initData() {
        int sellerId = parentActivity.getSellerId();
        categoryId = parentActivity.getCategoryId();

        menuRecycler.setLayoutManager(new LinearLayoutManager(mContent.getContext()));
        menuRecycler.setAdapter(takeOutSellerMenuAdapter);

        productRecycler.setLayoutManager(new LinearLayoutManager(mContent.getContext()));
        productRecycler.setAdapter(detailProductAdapter);

        //获取分类列表
        param.put("sellerId", sellerId);
        Api.config(Constant.NetWork.TAKE_OUT_CATEGORY_LIST, param, mContent.getContext()).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                TakeOutProductCategoryParam categoryParam = gson.fromJson(result, TakeOutProductCategoryParam.class);
                if (categoryParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<TakeOutProductCategoryParam.DataDTO> categoryParamData = categoryParam.getData();
                    if (categoryId == null) {
                        categoryId = categoryParamData.get(0).getId();
                    }
                    requireActivity().runOnUiThread(() -> {
                        takeOutSellerMenuAdapter.setData(categoryParamData);
                        getProductList();
                    });
                } else {
                    showSyncToast(categoryParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });

        //点击类别
        takeOutSellerMenuAdapter.setCategoryItemClickListener(id -> {
            categoryId = id;
            getProductList();
        });

        AlertDialog alertDialog = new AlertDialog.Builder(mContent.getContext())
                .setView(inflate)
                .create();
        alertDialog.getWindow().setBackgroundDrawable(AppCompatResources.getDrawable(mContent.getContext(), R.drawable.alert_bg_translate));
        alertDialog.setCancelable(true);

        //关闭弹窗
        detailCloseBtn.setOnClickListener(view -> {
            alertDialog.hide();
        });

        //点击产品数量
        detailProductAdapter.setProductClickListener(new TakeOutSellerDetailProductAdapter.DetailProductCountClickListener() {
            @Override
            public boolean clickAddBtn(Integer count, TakeOutProductListParam.DataDTO productParam) {
                if (!productCar.containsKey(productParam.getId())) {
                    productCar.put(productParam.getId(), productParam);
                }
                productParam.setCount(count + 1);
                return true;
            }

            @Override
            public boolean clickReduceBtn(Integer count, TakeOutProductListParam.DataDTO productParam) {
                if (count == 1) {
                    productCar.remove(productParam.getId());
                    return false;
                }
                productParam.setCount(count - 1);
                return true;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            //点击查看商品详情
            public void clickDetail(TakeOutProductListParam.DataDTO dataDTO) {
                detailName.setText(dataDTO.getName());
                detailSale.setText(String.format("月售%s", dataDTO.getSaleQuantity()));
                detailPrice.setText(String.format("￥%s", dataDTO.getPrice()));
                Glide.with(mContent.getContext())
                        .load(Constant.BASE_API + dataDTO.getImgUrl())
                        .transform(new RoundedCorners(14))
                        .into(detailCover);

                alertDialog.show();

                detailAddBtn.setOnClickListener(view -> {
//                    if (!productCar.containsKey(dataDTO.getId())) {
//                        productCar.put(dataDTO.getId(), dataDTO);
//                        this.clickAddBtn(1, dataDTO);
//                        detailProductAdapter.notifyDataSetChanged();
//                    }
                    showSyncToast("添加异常");
                    alertDialog.hide();
                });
            }
        });
    }

    //获取商品列表
    private void getProductList() {
        param.put("categoryId", categoryId);
        Api.config(Constant.NetWork.TAKE_OUT_PRODUCT_LIST, param, mContent.getContext()).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                TakeOutProductListParam productListParam = gson.fromJson(result, TakeOutProductListParam.class);
                if (productListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<TakeOutProductListParam.DataDTO> listParamData = productListParam.getData();
                    requireActivity().runOnUiThread(() -> {
                        detailProductAdapter.setList(productCar, listParamData);
                    });
                } else {
                    showSyncToast(productListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
    }

    //和父activity交换值
    public void setValue(Map<Integer, TakeOutProductListParam.DataDTO> productCar) {
        this.productCar.clear();
        this.productCar.putAll(productCar);
        detailProductAdapter.setProductCar(productCar);
    }

    public Map<Integer, TakeOutProductListParam.DataDTO> getValue() {
        return productCar;
    }
}