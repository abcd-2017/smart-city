package com.example.adapter.takeoutadapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.R;
import com.example.pojo.takeoutparam.TakeOutProductListParam;
import com.example.util.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kkk
 */
public class TakeOutSellerDetailProductAdapter extends RecyclerView.Adapter<TakeOutSellerDetailProductAdapter.InnerHolder> {
    private final List<TakeOutProductListParam.DataDTO> listParamData = new ArrayList<>();
    private static final Map<Integer, TakeOutProductListParam.DataDTO> productCar = new HashMap<>();
    private DetailProductCountClickListener countClickListener;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_take_out_seller_detail_product, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        TakeOutProductListParam.DataDTO dataDTO = listParamData.get(position);

        holder.productName.setText(dataDTO.getName());
        holder.productPrice.setText(String.format("￥%s", dataDTO.getPrice()));
        holder.productSaleQuantity.setText(String.format("月售 %s", dataDTO.getSaleQuantity()));
        holder.productCount.setText("0");

        Glide.with(holder.itemView.getContext())
                .load(Constant.BASE_API + dataDTO.getImgUrl())
                .transform(new RoundedCorners(14))
                .into(holder.productCover);

        if (productCar.containsKey(dataDTO.getId())) {
            TakeOutProductListParam.DataDTO product = productCar.get(dataDTO.getId());
            holder.productCount.setText(String.valueOf(product.getCount()));
            holder.productReduceBtn.setVisibility(View.VISIBLE);
            holder.productCount.setVisibility(View.VISIBLE);
        } else {
            holder.productReduceBtn.setVisibility(View.GONE);
            holder.productCount.setVisibility(View.GONE);
        }

        holder.productAddBtn.setOnClickListener(view -> {
            Integer count = Integer.parseInt(holder.productCount.getText().toString());
            if (countClickListener.clickAddBtn(count, dataDTO)) {
                holder.productCount.setText(String.valueOf(count + 1));
                holder.productCount.setVisibility(View.VISIBLE);
                holder.productReduceBtn.setVisibility(View.VISIBLE);
            }
        });
        holder.productReduceBtn.setOnClickListener(view -> {
            Integer count = Integer.parseInt(holder.productCount.getText().toString());
            if (countClickListener.clickReduceBtn(count, dataDTO)) {
                holder.productCount.setText(String.valueOf(count - 1));
                holder.productCount.setVisibility(View.VISIBLE);
                holder.productReduceBtn.setVisibility(View.VISIBLE);
            } else {
                holder.productCount.setText(String.valueOf(0));
                holder.productCount.setVisibility(View.GONE);
                holder.productReduceBtn.setVisibility(View.GONE);
            }
        });

        holder.productCard.setOnClickListener(view -> {
            countClickListener.clickDetail(dataDTO);
        });
    }

    @Override
    public int getItemCount() {
        return listParamData.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(Map<Integer, TakeOutProductListParam.DataDTO> map, List<TakeOutProductListParam.DataDTO> listData) {
        productCar.clear();
        productCar.putAll(map);
        listParamData.clear();
        listParamData.addAll(listData);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setProductCar(Map<Integer, TakeOutProductListParam.DataDTO> map) {
        productCar.clear();
        productCar.putAll(map);
        notifyDataSetChanged();
    }

    public void setProductClickListener(DetailProductCountClickListener countClickListener) {
        this.countClickListener = countClickListener;
    }

    public interface DetailProductCountClickListener {
        //返回值为true， 代表数量正常添加，为false，则需要隐藏递减按钮
        boolean clickAddBtn(Integer count, TakeOutProductListParam.DataDTO productParam);

        boolean clickReduceBtn(Integer count, TakeOutProductListParam.DataDTO productParam);

        void clickDetail(TakeOutProductListParam.DataDTO dataDTO);
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final CardView productReduceBtn, productAddBtn;
        private final ImageView productCover;
        private final TextView productCount, productName, productPrice, productSaleQuantity;
        private final CardView productCard;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            productCover = itemView.findViewById(R.id.item_take_out_seller_product_cover);
            productName = itemView.findViewById(R.id.item_take_out_seller_product_name);
            productPrice = itemView.findViewById(R.id.item_take_out_seller_product_price);
            productSaleQuantity = itemView.findViewById(R.id.item_take_out_seller_product_sale_quantity);
            productAddBtn = itemView.findViewById(R.id.item_take_out_product_add_btn);
            productReduceBtn = itemView.findViewById(R.id.item_take_out_product_reduce_btn);
            productCount = itemView.findViewById(R.id.item_take_out_product_count);
            productCard = itemView.findViewById(R.id.item_take_out_seller_product_card);
        }
    }
}
