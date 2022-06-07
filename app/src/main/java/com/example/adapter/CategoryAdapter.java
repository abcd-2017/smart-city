package com.example.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.pojo.NewsCategoryParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.InnerHolder> {
    List<NewsCategoryParam.DateDTO> list = new ArrayList<>();
    private int currId = 0;
    private ItemClickListener clickListener;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_list, parent, false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        View itemView = holder.itemView;
        NewsCategoryParam.DateDTO dateDTO = list.get(position);
        holder.categoryTitle.setText(dateDTO.getName());
        if (position == currId) {
            holder.categoryTitle.setTextSize(16);
            holder.categoryTitle.getPaint().setFakeBoldText(true);
            holder.categoryTitle.setTextColor(Color.BLACK);
            holder.bottomView.setBackgroundResource(R.drawable.shape_login_btn_background);
        } else {
            holder.categoryTitle.setTextSize(14);
            holder.categoryTitle.getPaint().setFakeBoldText(false);
            holder.categoryTitle.setTextColor(0xFF888888);
            holder.bottomView.setBackgroundResource(R.color.transparent);
        }
        itemView.setOnClickListener(view -> {
            clickListener.click(dateDTO.getId(), position);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItemClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ItemClickListener {
        void click(int id, int position);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<NewsCategoryParam.DateDTO> data) {
        list.clear();
        list.addAll(data);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCurrId(int currId) {
        this.currId = currId;
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final View bottomView;
        private final TextView categoryTitle;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            categoryTitle = itemView.findViewById(R.id.category_title);
            bottomView = itemView.findViewById(R.id.category_bottom_view);
        }
    }
}
