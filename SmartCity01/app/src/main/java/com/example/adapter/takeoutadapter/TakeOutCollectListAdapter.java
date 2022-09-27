package com.example.adapter.takeoutadapter;

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
import com.example.pojo.takeoutparam.TakeOutCollectListParam;
import com.example.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class TakeOutCollectListAdapter extends RecyclerView.Adapter<TakeOutCollectListAdapter.InnerHolder> {
    private List<TakeOutCollectListParam.RowsDTO> collectListParamRows = new ArrayList<>();

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_take_out_collect_list, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        TakeOutCollectListParam.RowsDTO rowsDTO = collectListParamRows.get(position);

        holder.collectName.setText(rowsDTO.getSellerName());
        holder.collectScore.setText(String.valueOf(rowsDTO.getScore()));
        holder.collectSale.setText(String.format("月售%s", rowsDTO.getSaleQuantity()));

        Glide.with(holder.itemView.getContext())
                .load(Constant.BASE_API + rowsDTO.getImgUrl())
                .transform(new RoundedCorners(14))
                .into(holder.collectCover);
    }

    @Override
    public int getItemCount() {
        return collectListParamRows.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<TakeOutCollectListParam.RowsDTO> data) {
        collectListParamRows.clear();
        collectListParamRows.addAll(data);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final ImageView collectCover;
        private final TextView collectName, collectScore, collectSale;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            collectCover = itemView.findViewById(R.id.item_take_out_collect_cover);
            collectName = itemView.findViewById(R.id.item_take_out_collect_name);
            collectScore = itemView.findViewById(R.id.item_take_out_collect_score);
            collectSale = itemView.findViewById(R.id.item_take_out_collect_sale);
        }
    }
}
