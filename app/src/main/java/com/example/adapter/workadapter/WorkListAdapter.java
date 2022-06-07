package com.example.adapter.workadapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.pojo.workparam.WorkListParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class WorkListAdapter extends RecyclerView.Adapter<WorkListAdapter.InnerHolder> {
    List<WorkListParam.RowsDTO> workListParamRows = new ArrayList<>();
    private WorkListClickListener clickListener;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_work_list, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        WorkListParam.RowsDTO rowsDTO = workListParamRows.get(position);

        holder.listAddress.setText(rowsDTO.getAddress());
        holder.listCompanyName.setText(rowsDTO.getCompanyName());
        holder.listSalary.setText(rowsDTO.getSalary());
        holder.listName.setText(rowsDTO.getName());

        holder.listCard.setOnClickListener(view -> {
            clickListener.click(rowsDTO.getId());
        });
    }

    @Override
    public int getItemCount() {
        return workListParamRows.size();
    }

    public void setWorkClickListener(WorkListClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface WorkListClickListener {
        void click(Integer id);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<WorkListParam.RowsDTO> data) {
        workListParamRows.clear();
        workListParamRows.addAll(data);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final CardView listCard;
        private final TextView listAddress;
        private final TextView listCompanyName;
        private final TextView listName;
        private final TextView listSalary;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            listCard = itemView.findViewById(R.id.work_list_card);
            listAddress = itemView.findViewById(R.id.work_list_address);
            listCompanyName = itemView.findViewById(R.id.work_list_compay_name);
            listName = itemView.findViewById(R.id.work_list_name);
            listSalary = itemView.findViewById(R.id.work_list_salary);
        }
    }
}
