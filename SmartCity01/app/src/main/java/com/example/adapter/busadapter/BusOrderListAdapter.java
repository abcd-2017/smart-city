package com.example.adapter.busadapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.pojo.busparam.BusOrderListParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class BusOrderListAdapter extends RecyclerView.Adapter<BusOrderListAdapter.InnerHolder> {
    private final List<BusOrderListParam.RowsDTO> orderListParamRows = new ArrayList<>();
    private BusItemPayClickListener clickListener;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bus_order_list, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        BusOrderListParam.RowsDTO rowsDTO = orderListParamRows.get(position);
        holder.orderStart.setText(rowsDTO.getStart());
        holder.orderEnd.setText(rowsDTO.getEnd());
        holder.orderName.setText(rowsDTO.getUserName());
        holder.orderPhone.setText(rowsDTO.getUserTel());
        holder.orderNum.setText(rowsDTO.getOrderNum());
        if (rowsDTO.getStatus() == 0) {
            holder.payBtn.setVisibility(View.GONE);
        } else {
            holder.payBtn.setVisibility(View.VISIBLE);
        }
        holder.payBtn.setOnClickListener(view -> {
            clickListener.click(rowsDTO);
        });
    }

    @Override
    public int getItemCount() {
        return orderListParamRows.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<BusOrderListParam.RowsDTO> data) {
        orderListParamRows.clear();
        orderListParamRows.addAll(data);
        notifyDataSetChanged();
    }

    public void setBusItemClickListener(BusItemPayClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface BusItemPayClickListener {
        void click(BusOrderListParam.RowsDTO rowsDTO);
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final TextView orderStart;
        private final TextView orderEnd;
        private final TextView orderName;
        private final TextView orderPhone;
        private final TextView orderNum;
        private final CardView payBtn;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            orderStart = itemView.findViewById(R.id.bus_order_start);
            orderEnd = itemView.findViewById(R.id.bus_order_end);
            orderName = itemView.findViewById(R.id.bus_order_name);
            orderPhone = itemView.findViewById(R.id.bus_order_phone);
            orderNum = itemView.findViewById(R.id.bus_order_num);
            payBtn = itemView.findViewById(R.id.bus_order_pay);
        }
    }
}
