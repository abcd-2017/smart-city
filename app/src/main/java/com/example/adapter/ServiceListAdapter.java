package com.example.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.R;
import com.example.pojo.ServiceListParam;
import com.example.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.InnerHolder> {
    private final List<ServiceListParam.RowsDTO> list = new ArrayList<>();
    private ItemClickListener itemClickListener;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service_list, parent, false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        ServiceListParam.RowsDTO rowsDTO = list.get(position);
        View itemView = holder.itemView;
        Glide.with(itemView.getContext()).
                load(Constant.BASE_API + rowsDTO.getImgUrl()).
                circleCrop()
                .into(holder.author);
        holder.title.setText(rowsDTO.getServiceName());
        itemView.setOnClickListener(view -> {
            itemClickListener.click(rowsDTO.getId());
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<ServiceListParam.RowsDTO> data) {
        list.clear();
        list.addAll(data);
        notifyDataSetChanged();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void click(Integer id);
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final ImageView author;
        private final TextView title;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.service_image);
            title = itemView.findViewById(R.id.service_title);
        }
    }
}
