package com.example.adapter.takeoutadapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.pojo.takeoutparam.TakeOutOrderListParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class TakeOutOrderProductListAdapter extends RecyclerView.Adapter<TakeOutOrderProductListAdapter.InnerHolder> {
    List<TakeOutOrderListParam.RowsDTO.OrderInfoDTO.OrderItemListDTO> orderInfoOrderItemList = new ArrayList<>();

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_take_out_order_product_list, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        TakeOutOrderListParam.RowsDTO.OrderInfoDTO.OrderItemListDTO orderItemListDTO = orderInfoOrderItemList.get(position);

        holder.productName.setText(orderItemListDTO.getProductName());
        holder.productCount.setText(String.format("x %s", orderItemListDTO.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return orderInfoOrderItemList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<TakeOutOrderListParam.RowsDTO.OrderInfoDTO.OrderItemListDTO> data) {
        orderInfoOrderItemList.clear();
        orderInfoOrderItemList.addAll(data);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final TextView productName, productCount;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.item_take_out_order_product_name);
            productCount = itemView.findViewById(R.id.item_take_out_order_product_count);
        }
    }
}
