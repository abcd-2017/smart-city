package com.example.adapter.takeoutadapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.pojo.takeoutparam.TakeOutProductCategoryParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class TakeOutSellerMenuAdapter extends RecyclerView.Adapter<TakeOutSellerMenuAdapter.InnerHolder> {
    List<TakeOutProductCategoryParam.DataDTO> categoryParamData = new ArrayList<>();
    private CategoryItemClickListener categoryItemClickListener;
    private int currSelect = 0;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_take_out_seller_menu, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        TakeOutProductCategoryParam.DataDTO dataDTO = categoryParamData.get(position);
        holder.menuText.setText(dataDTO.getName());
        holder.sellerMenuCard.setOnClickListener(view -> {
            categoryItemClickListener.click(dataDTO.getId());
            selectItem(position);
        });
        if (position == currSelect) {
            holder.menuText.getPaint().setFakeBoldText(true);
            holder.menuText.setTextColor(Color.parseColor("#444444"));
            holder.sellerMenuCard.setBackgroundColor(Color.WHITE);
        } else {
            holder.menuText.getPaint().setFakeBoldText(false);
            holder.menuText.setTextColor(Color.parseColor("#999999"));
            holder.sellerMenuCard.setBackgroundColor(Color.parseColor("#fafafa"));
        }
    }

    @Override
    public int getItemCount() {
        return categoryParamData.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void selectItem(Integer position) {
        currSelect = position;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<TakeOutProductCategoryParam.DataDTO> data) {
        categoryParamData.clear();
        categoryParamData.addAll(data);
        notifyDataSetChanged();
    }

    public void setCategoryItemClickListener(CategoryItemClickListener categoryItemClickListener) {
        this.categoryItemClickListener = categoryItemClickListener;
    }

    public interface CategoryItemClickListener {
        void click(Integer id);
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final ConstraintLayout sellerMenuCard;
        private final TextView menuText;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            sellerMenuCard = itemView.findViewById(R.id.take_out_seller_menu_card);
            menuText = itemView.findViewById(R.id.take_out_seller_menu_text);
        }
    }
}
