package com.example.adapter.parkingadapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.pojo.parkingparam.ParkingReCordParam;

import java.util.LinkedList;
import java.util.List;

/**
 * @author kkk
 */
public class ParkingReCardListAdapter extends RecyclerView.Adapter<ParkingReCardListAdapter.InnerHolder> {
    private final List<ParkingReCordParam.RowsDTO> rowsDTOS = new LinkedList<>();

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parking_recard_list, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        ParkingReCordParam.RowsDTO rowsDTO = rowsDTOS.get(position);
        holder.plateNo.setText(rowsDTO.getPlateNumber());
        holder.reCardName.setText(rowsDTO.getParkName());
        holder.reCardTime.setText(rowsDTO.getEntryTime());
        holder.reCardName.getPaint().setFakeBoldText(true);
    }

    @Override
    public int getItemCount() {
        return rowsDTOS.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<ParkingReCordParam.RowsDTO> data) {
        rowsDTOS.clear();
        rowsDTOS.addAll(data);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final TextView plateNo;
        private final TextView reCardName;
        private final TextView reCardTime;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            plateNo = itemView.findViewById(R.id.parking_recard_plate_no);
            reCardName = itemView.findViewById(R.id.parking_recard_name);
            reCardTime = itemView.findViewById(R.id.parking_recard_time);
        }
    }
}
