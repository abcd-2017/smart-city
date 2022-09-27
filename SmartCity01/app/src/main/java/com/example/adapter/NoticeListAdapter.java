package com.example.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.pojo.NoticeListParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class NoticeListAdapter extends RecyclerView.Adapter<NoticeListAdapter.InnerHolder> {
    List<NoticeListParam.RowsDTO> list = new ArrayList<>();
    private ItemClickListener itemClickListener;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notive_list, parent, false);
        return new InnerHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        NoticeListParam.RowsDTO rowsDTO = list.get(position);
        holder.noticeText.setText((position + 1) + "." + rowsDTO.getTitle());
        holder.itemView.setOnClickListener(view -> {
            itemClickListener.click(position);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<NoticeListParam.RowsDTO> data) {
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

        public TextView noticeText;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            noticeText = itemView.findViewById(R.id.notice_text);
        }
    }
}
