package com.example.adapter.parkingadapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.R;
import com.example.pojo.parkingparam.ParkingLotListParam;
import com.example.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class ParkingLotListAdapter extends RecyclerView.Adapter<ParkingLotListAdapter.InnerHolder> {
    private final List<ParkingLotListParam.RowsDTO> rowsDTOS = new ArrayList<>();
    private ParkingItemClickListener itemClickListener;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parking_lot_list, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        ParkingLotListParam.RowsDTO rowsDTO = rowsDTOS.get(position);
        holder.parkingLotName.setText(rowsDTO.getParkName());
        holder.parkingLotRates.setText(rowsDTO.getRates() + " 元 / 小时");
        holder.parkingLotVacancy.setText(rowsDTO.getVacancy());
        if (!"Y".equals(rowsDTO.getOpen())) {
            holder.itemView.setVisibility(View.GONE);
        }
        Glide.with(holder.itemView)
                .load(Constant.BASE_API + rowsDTO.getImgUrl())
                .transform(new RoundedCorners(16))
                .error(Constant.BASE_API + "/prod-api/profile/upload/image/2021/11/22/b25dcd6c-2628-479d-a3f2-f32bf2cb0c74.jpg")
                .into(holder.parkingLotImage);
        holder.parkingCard.setOnClickListener(view -> {

            itemClickListener.click(position);
        });

    }

    public void setParkingItemClickListener(ParkingItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ParkingItemClickListener {
        void click(Integer id);
    }

    @Override
    public int getItemCount() {
        return rowsDTOS.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<ParkingLotListParam.RowsDTO> data) {
        rowsDTOS.clear();
        rowsDTOS.addAll(data);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final CardView parkingCard;
        private final TextView parkingLotName;
        private final ImageView parkingLotImage;
        private final TextView parkingLotRates;
        private final TextView parkingLotVacancy;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            parkingCard = itemView.findViewById(R.id.parking_lot_card);
            parkingLotName = itemView.findViewById(R.id.parking_lot_name);
            parkingLotImage = itemView.findViewById(R.id.parking_lot_image);
            parkingLotRates = itemView.findViewById(R.id.parking_lot_rates);
            parkingLotVacancy = itemView.findViewById(R.id.parking_lot_vacancy);
        }
    }
}
