package com.example.adapter.hospitaladapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.pojo.hospitalparam.HospitalReservationListParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class HospitalReservationListAdapter extends RecyclerView.Adapter<HospitalReservationListAdapter.InnerHolder> {
    List<HospitalReservationListParam.RowsDTO> listParamRows = new ArrayList<>();
    private ItemClickListener itemClickListener;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hospital_reservation_list, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        HospitalReservationListParam.RowsDTO rowsDTO = listParamRows.get(position);
        holder.categoryName.setText(rowsDTO.getCategoryName());
        holder.categoryTime.setText(rowsDTO.getReserveTime());
        holder.itemView.setOnClickListener(view -> {
            itemClickListener.click(position);
        });
    }

    @Override
    public int getItemCount() {
        return listParamRows.size();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void click(Integer id);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<HospitalReservationListParam.RowsDTO> data) {
        listParamRows.clear();
        listParamRows.addAll(data);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final TextView categoryName;
        private final TextView categoryTime;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.reservation_category_name);
            categoryTime = itemView.findViewById(R.id.reservation_category_time);
        }
    }
}
