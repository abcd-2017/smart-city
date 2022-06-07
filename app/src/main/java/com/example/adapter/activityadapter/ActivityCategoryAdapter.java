package com.example.adapter.activityadapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.pojo.activityparam.ActivityCategoryParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class ActivityCategoryAdapter extends RecyclerView.Adapter<ActivityCategoryAdapter.InnerHolder> {
    List<ActivityCategoryParam.DataDTO> list = new ArrayList<>();
    private ItemClickListener itemClickListener;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_category, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        View itemView = holder.itemView;
        ActivityCategoryParam.DataDTO dataDTO = list.get(position);
        holder.categoryName.setText(dataDTO.getName());
        itemView.setOnClickListener(view -> {
            itemClickListener.click(dataDTO.getId());
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<ActivityCategoryParam.DataDTO> data) {
        list.clear();
        list.addAll(data);
        notifyDataSetChanged();
    }

    public void setItemCLickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void click(Integer id);
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        private final TextView categoryName;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.category_name);
        }
    }
}
