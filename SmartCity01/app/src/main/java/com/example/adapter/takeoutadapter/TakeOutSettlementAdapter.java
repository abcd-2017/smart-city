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
import com.example.pojo.takeoutparam.TakeOutProductListParam;
import com.example.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class TakeOutSettlementAdapter extends RecyclerView.Adapter<TakeOutSettlementAdapter.InnerHolder> {
    private List<TakeOutProductListParam.DataDTO> productList = new ArrayList<>();

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_take_out_order_settlement_list, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        TakeOutProductListParam.DataDTO product = productList.get(position);

        holder.settlementName.setText(product.getName());
        holder.settlementCount.setText(String.format("x%s", product.getCount()));
        holder.settlementPrice.setText(String.format("￥ %s", product.getPrice()));

        Glide.with(holder.itemView.getContext())
                .load(Constant.BASE_API + product.getImgUrl())
                .transform(new RoundedCorners(14))
                .into(holder.settlementCover);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<TakeOutProductListParam.DataDTO> data) {
        productList.clear();
        productList.addAll(data);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final TextView settlementCount, settlementName, settlementPrice;
        private final ImageView settlementCover;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            settlementCount = itemView.findViewById(R.id.item_take_out_settlement_count);
            settlementName = itemView.findViewById(R.id.item_take_out_settlement_name);
            settlementPrice = itemView.findViewById(R.id.item_take_out_settlement_price);
            settlementCover = itemView.findViewById(R.id.item_take_out_settlement_cover);
        }
    }
}
