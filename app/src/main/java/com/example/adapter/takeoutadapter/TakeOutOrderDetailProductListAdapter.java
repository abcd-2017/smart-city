package com.example.adapter.takeoutadapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.R;
import com.example.pojo.takeoutparam.TakeOutOrderDetailListParam;
import com.example.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class TakeOutOrderDetailProductListAdapter extends RecyclerView.Adapter<TakeOutOrderDetailProductListAdapter.InnerHolder> {
    private List<TakeOutOrderDetailListParam.DataDTO.OrderInfoDTO.OrderItemListDTO> orderItemList = new ArrayList<>();

    @NonNull
    @Override
    public TakeOutOrderDetailProductListAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_take_out_order_detail_product, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull TakeOutOrderDetailProductListAdapter.InnerHolder holder, int position) {
        TakeOutOrderDetailListParam.DataDTO.OrderInfoDTO.OrderItemListDTO orderItemListDTO = orderItemList.get(position);

        holder.productCount.setText(String.format("x %s", orderItemListDTO.getQuantity()));
        holder.productName.setText(orderItemListDTO.getProductName());
        holder.productPrice.setText(String.format("￥ %s", orderItemListDTO.getProductPrice()));

        Glide.with(holder.itemView.getContext())
                .load(Constant.BASE_API + orderItemListDTO.getProductImage())
                .transform(new RoundedCorners(10))
                .into(holder.productCover);
    }

    @Override
    public int getItemCount() {
        return orderItemList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<TakeOutOrderDetailListParam.DataDTO.OrderInfoDTO.OrderItemListDTO> data) {
        orderItemList.clear();
        orderItemList.addAll(data);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final TextView productCount, productName, productPrice;
        private final ImageView productCover;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            productCount = itemView.findViewById(R.id.item_take_out_order_detail_product_count);
            productName = itemView.findViewById(R.id.item_take_out_order_detail_product_name);
            productPrice = itemView.findViewById(R.id.item_take_out_order_detail_product_price);
            productCover = itemView.findViewById(R.id.item_take_out_order_detail_product_cover);
        }
    }
}
