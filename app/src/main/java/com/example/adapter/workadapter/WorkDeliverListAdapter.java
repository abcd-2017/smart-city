package com.example.adapter.workadapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.pojo.workparam.WorkDeliverListParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class WorkDeliverListAdapter extends RecyclerView.Adapter<WorkDeliverListAdapter.InnerHolder> {
    private final List<WorkDeliverListParam.RowsDTO> deliverListParamRows = new ArrayList<>();

    @NonNull
    @Override

    public WorkDeliverListAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_work_deliver_list, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkDeliverListAdapter.InnerHolder holder, int position) {
        WorkDeliverListParam.RowsDTO rowsDTO = deliverListParamRows.get(position);

        holder.deliverProfessionName.setText(rowsDTO.getPostName());
        holder.deliverTime.setText(rowsDTO.getSatrTime());
        holder.deliverMoney.setText(rowsDTO.getMoney());
        holder.deliverCompenyNome.setText(rowsDTO.getCompanyName());
    }

    @Override
    public int getItemCount() {
        return deliverListParamRows.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<WorkDeliverListParam.RowsDTO> data) {
        deliverListParamRows.clear();
        deliverListParamRows.addAll(data);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final TextView deliverCompenyNome;
        private final TextView deliverMoney;
        private final TextView deliverTime;
        private final TextView deliverProfessionName;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            deliverCompenyNome = itemView.findViewById(R.id.work_deliver_compeny_name);
            deliverMoney = itemView.findViewById(R.id.work_deliver_money);
            deliverTime = itemView.findViewById(R.id.work_deliver_time);
            deliverProfessionName = itemView.findViewById(R.id.work_deliver_profession_name);
        }
    }
}
