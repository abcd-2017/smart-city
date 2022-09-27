package com.example.adapter.takeoutadapter;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.R;
import com.example.pojo.takeoutparam.TakeOutProductListParam;
import com.example.util.Constant;
import com.lxj.easyadapter.EasyAdapter;
import com.lxj.easyadapter.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author kkk
 */
public class TakeOutCarProductAdapter extends EasyAdapter<TakeOutProductListParam.DataDTO> {
    private static final Map<Integer, TakeOutProductListParam.DataDTO> productCar = new HashMap<>();
    private static final List<TakeOutProductListParam.DataDTO> productList = new ArrayList<>();
    private ProductCountClickListener countClickListener;

    public TakeOutCarProductAdapter() {
        super(productList, R.layout.item_take_out_car_product);
    }

    @Override
    protected void bind(@NonNull ViewHolder viewHolder, TakeOutProductListParam.DataDTO dataDTO, int i) {
        ImageView productImage = viewHolder.getView(R.id.item_take_out_car_product_image);
        TextView productName = viewHolder.getView(R.id.item_take_out_car_product_name);
        TextView productPrice = viewHolder.getView(R.id.item_take_out_car_product_price);
        CardView productAddBtn = viewHolder.getView(R.id.item_take_out_car_product_add_btn);
        CardView productReduceBtn = viewHolder.getView(R.id.item_take_out_car_product_reduce_btn);
        TextView productCount = viewHolder.getView(R.id.item_take_out_car_product_count);
        ConstraintLayout productCard = viewHolder.getView(R.id.item_take_out_car_product_card);

        productName.setText(dataDTO.getName());
        productPrice.setText(String.format("￥%s", dataDTO.getPrice()));
        Glide.with(viewHolder.getConvertView())
                .load(Constant.BASE_API + dataDTO.getImgUrl())
                .transform(new RoundedCorners(10))
                .into(productImage);

        TakeOutProductListParam.DataDTO product = productCar.get(dataDTO.getId());
        Integer carCount = product.getCount();
        if (carCount == 0) {
            productReduceBtn.setVisibility(View.GONE);
            productCount.setVisibility(View.GONE);
        } else {
            productReduceBtn.setVisibility(View.VISIBLE);
            productCount.setVisibility(View.VISIBLE);
            productCount.setText(String.valueOf(carCount));
        }

        productAddBtn.setOnClickListener(view -> {
            Integer count = Integer.parseInt(productCount.getText().toString());
            if (countClickListener.clickAddBtn(count, dataDTO)) {
                productCount.setText(String.valueOf(count + 1));
                productCount.setVisibility(View.VISIBLE);
                productReduceBtn.setVisibility(View.VISIBLE);
            }
        });
        productReduceBtn.setOnClickListener(view -> {
            Integer count = Integer.parseInt(productCount.getText().toString());
            if (countClickListener.clickReduceBtn(count, dataDTO)) {
                productCount.setText(String.valueOf(count - 1));
                productCount.setVisibility(View.VISIBLE);
                productReduceBtn.setVisibility(View.VISIBLE);
            } else {
                productCount.setText("0");
                productCount.setVisibility(View.GONE);
                productReduceBtn.setVisibility(View.GONE);
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(Map<Integer, TakeOutProductListParam.DataDTO> data) {
        productCar.clear();
        productCar.putAll(data);
        productList.clear();
        productList.addAll(data.values());
        notifyDataSetChanged();
    }

    public void setProductClickListener(ProductCountClickListener countClickListener) {
        this.countClickListener = countClickListener;
    }

    public interface ProductCountClickListener {
        //返回值为true， 代表数量正常添加，为false，则需要隐藏递减按钮
        boolean clickAddBtn(Integer count, TakeOutProductListParam.DataDTO productParam);

        boolean clickReduceBtn(Integer count, TakeOutProductListParam.DataDTO productParam);
    }
}
