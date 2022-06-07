package com.example.adapter.hospitaladapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.pojo.hospitalparam.HospitalCategoryListParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class HospitalCategoryListAdapter extends RecyclerView.Adapter<HospitalCategoryListAdapter.InnerHolder> {
    List<HospitalCategoryListParam.RowsDTO> listParamRows = new ArrayList<>();
    private ItemClickListener itemClickListener;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hospital_category_list, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        HospitalCategoryListParam.RowsDTO rowsDTO = listParamRows.get(position);
        holder.categoryName.setText(rowsDTO.getCategoryName());
        holder.categoryItem.setOnClickListener(view -> {
            itemClickListener.click(position);
        });
    }

    @Override
    public int getItemCount() {
        return listParamRows.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<HospitalCategoryListParam.RowsDTO> data) {
        listParamRows.clear();
        listParamRows.addAll(data);
        notifyDataSetChanged();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void click(Integer index);
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final CardView categoryItem;
        private final TextView categoryName;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            categoryItem = itemView.findViewById(R.id.hospital_category_item);
            categoryName = itemView.findViewById(R.id.hospital_category_name);
        }
    }
}
