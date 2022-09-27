package com.example.adapter.trafficadapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.pojo.trafficparam.TrafficCheckCarPlateListParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class TrafficCheckCarListAdapter extends RecyclerView.Adapter<TrafficCheckCarListAdapter.InnerHolder> {
    List<TrafficCheckCarPlateListParam.RowsDTO> carPlateListParamRows = new ArrayList<>();
    private ItemBtnClickListener btnClickListener;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_traffic_vehicle_list, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        TrafficCheckCarPlateListParam.RowsDTO rowsDTO = carPlateListParamRows.get(position);
        holder.checkCarAddress.setText(rowsDTO.getAddress());
        holder.checkCarPhone.setText(rowsDTO.getPhone());
        holder.checkCarPlateName.setText(rowsDTO.getPlaceName());

        String[] split = rowsDTO.getRemarks().split(":", 2);
        holder.checkCarWeek.setText(split[0]);
        String[] timeSplit = split[1].split(";");
        holder.checkCarTimeAm.setText(timeSplit[0]);
        holder.checkCarTimePm.setText(timeSplit[1]);

        holder.checkCarBtn.setOnClickListener(view -> {
            btnClickListener.click(rowsDTO.getId());
        });
    }

    @Override
    public int getItemCount() {
        return carPlateListParamRows.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<TrafficCheckCarPlateListParam.RowsDTO> data) {
        carPlateListParamRows.clear();
        carPlateListParamRows.addAll(data);
        notifyDataSetChanged();
    }

    public void setItemBtnClickListener(ItemBtnClickListener btnClickListener) {
        this.btnClickListener = btnClickListener;
    }

    public interface ItemBtnClickListener {
        void click(Integer id);
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final TextView checkCarAddress, checkCarWeek, checkCarTimeAm, checkCarTimePm, checkCarPhone, checkCarPlateName;
        private final CardView checkCarBtn;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            checkCarAddress = itemView.findViewById(R.id.traffic_vehicle_check_car_address);
            checkCarWeek = itemView.findViewById(R.id.traffic_vehicle_check_car_week);
            checkCarTimeAm = itemView.findViewById(R.id.traffic_vehicle_check_car_time_am);
            checkCarTimePm = itemView.findViewById(R.id.traffic_vehicle_check_car_time_pm);
            checkCarPhone = itemView.findViewById(R.id.traffic_vehicle_check_car_phone);
            checkCarPlateName = itemView.findViewById(R.id.traffic_vehicle_check_car_plate_name);
            checkCarBtn = itemView.findViewById(R.id.traffic_vehicle_check_car_btn);
        }
    }
}
