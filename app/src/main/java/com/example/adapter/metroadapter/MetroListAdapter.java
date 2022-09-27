package com.example.adapter.metroadapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.pojo.metroparam.MetroListParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author kkk
 */
public class MetroListAdapter extends RecyclerView.Adapter<MetroListAdapter.InnerHolder> {
    List<MetroListParam.DataDTO> list = new ArrayList<>();
    private ItemClickListener itemClickListener;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_metro_list, parent, false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        View itemView = holder.itemView;
        MetroListParam.DataDTO dataDTO = list.get(position);
        holder.nextStep.setText(dataDTO.getNextStep().getName());
        holder.lineName.setText(dataDTO.getLineName());
        Random random = new Random();
        holder.nextStepNum.setText((random.nextInt(4) + 1) + "站");
        holder.nextTime.setText(random.nextInt(20) + "分钟");
        itemView.setOnClickListener(view -> {
            itemClickListener.click(dataDTO.getLineId());
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<MetroListParam.DataDTO> data) {
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

    public static class InnerHolder extends RecyclerView.ViewHolder {

        private final TextView nextStep;
        private final TextView nextStepNum;
        private final TextView nextTime;
        private final TextView lineName;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            nextStep = itemView.findViewById(R.id.next_step);
            nextStepNum = itemView.findViewById(R.id.next_step_num);
            nextTime = itemView.findViewById(R.id.next_time);
            lineName = itemView.findViewById(R.id.line_name);
        }
    }
}
