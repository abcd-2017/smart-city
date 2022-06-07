package com.example.adapter.workadapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.pojo.workparam.WorkProfessionListParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class WorkProfessionListAdapter extends RecyclerView.Adapter<WorkProfessionListAdapter.InnerHolder> {
    private final List<WorkProfessionListParam.RowsDTO> professionListParamRows = new ArrayList<>();
    private WorkProfessionClickListener clickListener;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_work_profession_list, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        WorkProfessionListParam.RowsDTO rowsDTO = professionListParamRows.get(position);

        holder.professionName.setText(rowsDTO.getProfessionName());

        holder.professionCard.setOnClickListener(view -> {
            clickListener.itemClick(rowsDTO);
        });
    }

    public void setProfessionClickListener(WorkProfessionClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface WorkProfessionClickListener {
        void itemClick(WorkProfessionListParam.RowsDTO rowsDTO);
    }

    @Override
    public int getItemCount() {
        return professionListParamRows.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<WorkProfessionListParam.RowsDTO> data) {
        professionListParamRows.clear();
        professionListParamRows.addAll(data);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final TextView professionName;
        private final ConstraintLayout professionCard;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            professionName = itemView.findViewById(R.id.work_profession_name);
            professionCard = itemView.findViewById(R.id.work_profession_card);
        }
    }
}
