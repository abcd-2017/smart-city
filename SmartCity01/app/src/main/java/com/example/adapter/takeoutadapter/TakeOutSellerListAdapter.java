package com.example.adapter.takeoutadapter;

import android.annotation.SuppressLint;
import android.text.TextUtils;
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
import com.example.pojo.takeoutparam.TakeOutSellerListParam;
import com.example.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class TakeOutSellerListAdapter extends RecyclerView.Adapter<TakeOutSellerListAdapter.InnerHolder> {

    private SellerItemClickListener clickListener;
    private final List<TakeOutSellerListParam.RowsDTO> sellerListParamRows = new ArrayList<>();

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_take_out_seller_list, parent, false);
        return new InnerHolder(inflate);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        TakeOutSellerListParam.RowsDTO rowsDTO = sellerListParamRows.get(position);

        holder.sellerName.setText(rowsDTO.getName());
        holder.sellerScore.setText(String.valueOf(rowsDTO.getScore()));
        holder.sellerAvgCost.setText(String.format("人均 ￥%s", rowsDTO.getAvgCost()));
        if (TextUtils.isEmpty(rowsDTO.getIntroduction()) || rowsDTO.getIntroduction().length() > 10) {
            holder.sellerTag.setVisibility(View.GONE);
        } else {
            holder.sellerTag.setText(rowsDTO.getIntroduction());
        }
        holder.sellerDeliveryTime.setText(String.format("%d分钟 %skm", rowsDTO.getDeliveryTime(), (rowsDTO.getDistance() / 1000)));
        holder.sellerSaleQuantity.setText(String.format("月售 %d", rowsDTO.getSaleQuantity()));

        Glide.with(holder.itemView.getContext())
                .load(Constant.BASE_API + rowsDTO.getImgUrl())
                .transform(new RoundedCorners(14))
                .into(holder.sellerCover);

        holder.sellerCard.setOnClickListener(view -> {
            clickListener.click(rowsDTO.getId());
        });
    }

    @Override
    public int getItemCount() {
        return sellerListParamRows.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<TakeOutSellerListParam.RowsDTO> data) {
        sellerListParamRows.clear();
        sellerListParamRows.addAll(data);
        notifyDataSetChanged();
    }

    public void setSellerItemClickListener(SellerItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface SellerItemClickListener {
        void click(Integer id);
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final CardView sellerCard;
        private final ImageView sellerCover;
        private final TextView sellerAvgCost, sellerName, sellerDeliveryTime, sellerSaleQuantity, sellerScore, sellerTag;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            sellerCard = itemView.findViewById(R.id.item_take_out_seller_card);
            sellerCover = itemView.findViewById(R.id.item_take_out_seller_cover);
            sellerAvgCost = itemView.findViewById(R.id.item_take_out_seller_avg_cost);
            sellerName = itemView.findViewById(R.id.item_take_out_seller_name);
            sellerDeliveryTime = itemView.findViewById(R.id.item_take_out_seller_delivery_time);
            sellerSaleQuantity = itemView.findViewById(R.id.item_take_out_seller_sale_quantitiy);
            sellerScore = itemView.findViewById(R.id.item_take_out_seller_score);
            sellerTag = itemView.findViewById(R.id.item_take_out_seller_tag);
        }
    }
}
