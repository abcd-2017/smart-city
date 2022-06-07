package com.example.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.pojo.AllOrderListParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class AllOrderAdapter extends RecyclerView.Adapter<AllOrderAdapter.InnerHolder> {
    private List<AllOrderListParam.RowsDTO> orderListParamRows = new ArrayList<>();

    @NonNull
    @Override
    public AllOrderAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_all_order_list, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull AllOrderAdapter.InnerHolder holder, int position) {
        AllOrderListParam.RowsDTO rowsDTO = orderListParamRows.get(position);
        holder.orderName.setText(rowsDTO.getName());
        holder.orderPrice.setText(String.format("￥ %s", rowsDTO.getAmount()));
        holder.orderStatus.setText(rowsDTO.getOrderStatus());
    }

    @Override
    public int getItemCount() {
        return orderListParamRows.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<AllOrderListParam.RowsDTO> data) {
        orderListParamRows.clear();
        orderListParamRows.addAll(data);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final TextView orderName, orderPrice, orderStatus;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            orderName = itemView.findViewById(R.id.all_order_name);
            orderPrice = itemView.findViewById(R.id.all_order_price);
            orderStatus = itemView.findViewById(R.id.all_order_status);
        }
    }
}
