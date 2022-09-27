package com.example.adapter.parkingadapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.pojo.parkingparam.ParkingIntegralListParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class ParkingIntegralListAdapter extends RecyclerView.Adapter<ParkingIntegralListAdapter.InnerHolder> {
    List<ParkingIntegralListParam.RowsDTO> rowsDTOList = new ArrayList<>();
    private ParkingIntegralListClickListener integralListClickListener;

    @NonNull
    @Override

    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parking_integral_list, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        ParkingIntegralListParam.RowsDTO rowsDTO = rowsDTOList.get(position);
        holder.integralName.setText(rowsDTO.getName());
        holder.integralNum.setText(String.valueOf(rowsDTO.getScore()));
        holder.integralPrice.setText("￥" + rowsDTO.getPrice());
        holder.integralSaleQuantity.setText(String.valueOf(rowsDTO.getSaleQuantity()));

        holder.itemView.setOnClickListener(view -> {
            integralListClickListener.click(rowsDTO.getId());
        });
    }

    @Override
    public int getItemCount() {
        return rowsDTOList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<ParkingIntegralListParam.RowsDTO> data) {
        rowsDTOList.clear();
        rowsDTOList.addAll(data);
        notifyDataSetChanged();
    }

    public void setListClickListener(ParkingIntegralListClickListener integralListClickListener) {
        this.integralListClickListener = integralListClickListener;
    }


    public interface ParkingIntegralListClickListener {
        void click(Integer id);
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final TextView integralName;
        private final TextView integralPrice;
        private final TextView integralNum;
        private final TextView integralSaleQuantity;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            integralName = itemView.findViewById(R.id.parking_integral_name);
            integralPrice = itemView.findViewById(R.id.parking_integral_price);
            integralNum = itemView.findViewById(R.id.parking_integral_num);
            integralSaleQuantity = itemView.findViewById(R.id.parking_integral_saleQuantity);
        }
    }
}
