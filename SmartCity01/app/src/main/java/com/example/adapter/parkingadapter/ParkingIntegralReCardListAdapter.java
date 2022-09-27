package com.example.adapter.parkingadapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.pojo.parkingparam.ParkingIntegralReCardListParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class ParkingIntegralReCardListAdapter extends RecyclerView.Adapter<ParkingIntegralReCardListAdapter.InnerHolder> {
    List<ParkingIntegralReCardListParam.RowsDTO> rowsDTOList = new ArrayList<>();

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parking_integral_recard, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        ParkingIntegralReCardListParam.RowsDTO rowsDTO = rowsDTOList.get(position);
        holder.reCardMessage.setText(rowsDTO.getEvent());
        holder.reCardPrice.setText(rowsDTO.getScore());
        holder.reCardTime.setText(rowsDTO.getChangeDate());
    }

    @Override
    public int getItemCount() {
        return rowsDTOList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<ParkingIntegralReCardListParam.RowsDTO> data) {
        rowsDTOList.clear();
        rowsDTOList.addAll(data);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final TextView reCardMessage;
        private final TextView reCardTime;
        private final TextView reCardPrice;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            reCardMessage = itemView.findViewById(R.id.parking_recard_message);
            reCardTime = itemView.findViewById(R.id.parking_reacrd_time);
            reCardPrice = itemView.findViewById(R.id.parking_recard_price);
        }
    }
}
