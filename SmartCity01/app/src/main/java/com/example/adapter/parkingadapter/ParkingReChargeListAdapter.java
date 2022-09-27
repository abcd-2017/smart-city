package com.example.adapter.parkingadapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.pojo.parkingparam.ParkingReChargeListParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class ParkingReChargeListAdapter extends RecyclerView.Adapter<ParkingReChargeListAdapter.InnerHolder> {
    List<ParkingReChargeListParam.RowsDTO> listParamRows = new ArrayList<>();

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parking_lot_recharge_list, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        ParkingReChargeListParam.RowsDTO rowsDTO = listParamRows.get(position);
        holder.rechargeTime.setText(rowsDTO.getRechargeDate());
        holder.rechargeMoney.setText("￥" + rowsDTO.getMoney());
        holder.rechargeType.setText(rowsDTO.getPayType());
    }

    @Override
    public int getItemCount() {
        return listParamRows.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<ParkingReChargeListParam.RowsDTO> data) {
        listParamRows.clear();
        listParamRows.addAll(data);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final TextView rechargeMoney;
        private final TextView rechargeTime;
        private final TextView rechargeType;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            rechargeMoney = itemView.findViewById(R.id.recharge_money);
            rechargeTime = itemView.findViewById(R.id.recharge_time);
            rechargeType = itemView.findViewById(R.id.recharge_type);
        }
    }
}
