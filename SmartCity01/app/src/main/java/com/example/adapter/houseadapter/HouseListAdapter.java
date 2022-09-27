package com.example.adapter.houseadapter;

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
import com.example.pojo.houseparam.HouseListParam;
import com.example.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class HouseListAdapter extends RecyclerView.Adapter<HouseListAdapter.InnerHolder> {
    List<HouseListParam.RowsDTO> listParamRows = new ArrayList<>();
    private HouseItemClickListener itemClickListener;

    @NonNull
    @Override
    public HouseListAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_house_list, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull HouseListAdapter.InnerHolder holder, int position) {
        HouseListParam.RowsDTO rowsDTO = listParamRows.get(position);

        holder.listPrice.setText(rowsDTO.getPrice());
        holder.listTitle.setText(rowsDTO.getSourceName());
        holder.listType.setText(rowsDTO.getHouseType());

        Glide.with(holder.itemView.getContext())
                .load(Constant.BASE_API + rowsDTO.getPic())
                .transform(new RoundedCorners(6))
                .into(holder.listImage);

        holder.listCard.setOnClickListener(view -> {
            itemClickListener.click(rowsDTO.getId());
        });
    }

    public void setHouseItemClickListener(HouseItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface HouseItemClickListener {
        void click(Integer id);
    }

    @Override
    public int getItemCount() {
        return listParamRows.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<HouseListParam.RowsDTO> data) {
        listParamRows.clear();
        listParamRows.addAll(data);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final TextView listPrice, listTitle, listType;
        private final ImageView listImage;
        private final CardView listCard;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            listPrice = itemView.findViewById(R.id.house_list_area);
            listTitle = itemView.findViewById(R.id.house_list_title);
            listType = itemView.findViewById(R.id.house_list_type);
            listImage = itemView.findViewById(R.id.house_list_image);
            listCard = itemView.findViewById(R.id.house_list_card);
        }
    }
}
