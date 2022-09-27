package com.example.adapter.hospitaladapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.R;
import com.example.pojo.hospitalparam.HospitalListParam;
import com.example.util.Constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author kkk
 */
public class HospitalListAdapter extends RecyclerView.Adapter<HospitalListAdapter.InnerHolder> {
    List<HospitalListParam.RowsDTO> list = new ArrayList<>();
    private ItemClickListener itemClickListener;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hospital_list, parent, false);
        return new InnerHolder(inflate);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        View itemView = holder.itemView;
        HospitalListParam.RowsDTO rowsDTO = list.get(position);
        holder.hospitalName.setText(rowsDTO.getHospitalName());
        for (int i = 0; i < rowsDTO.getLevel(); i++) {
            holder.imageList.get(i).setImageResource(R.drawable.ic_unstar_rate_24);
        }
        Glide.with(itemView.getContext())
                .load(Constant.BASE_API + rowsDTO.getImgUrl())
                .transform(new RoundedCorners(14))
                .into(holder.hospitalImage);

        itemView.setOnClickListener(view -> {
            itemClickListener.click(rowsDTO.getId());
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<HospitalListParam.RowsDTO> data) {
        list.clear();
        list.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void click(Integer id);
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final ImageView hospitalImage;
        private final TextView hospitalName;
        private final List<ImageView> imageList;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            hospitalImage = itemView.findViewById(R.id.hospital_image);
            hospitalName = itemView.findViewById(R.id.hospital_name);
            imageList = new ArrayList<>(Arrays.asList(
                    itemView.findViewById(R.id.hospital_start1),
                    itemView.findViewById(R.id.hospital_start2),
                    itemView.findViewById(R.id.hospital_start3),
                    itemView.findViewById(R.id.hospital_start4),
                    itemView.findViewById(R.id.hospital_start5)
            ));
        }
    }
}
