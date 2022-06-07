package com.example.adapter.trafficadapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.pojo.trafficparam.TrafficCheckApplyParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class TrafficCheckApplyListAdapter extends RecyclerView.Adapter<TrafficCheckApplyListAdapter.InnerHolder> {
    private final List<TrafficCheckApplyParam.RowsDTO> trafficCheckApplyParamRows = new ArrayList<>();

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_traffic_check_apply_list, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        TrafficCheckApplyParam.RowsDTO rowsDTO = trafficCheckApplyParamRows.get(position);

        holder.plateName.setText(rowsDTO.getPlaceName());
        holder.userName.setText(rowsDTO.getUserName());
    }

    @Override
    public int getItemCount() {
        return trafficCheckApplyParamRows.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<TrafficCheckApplyParam.RowsDTO> data) {
        trafficCheckApplyParamRows.clear();
        trafficCheckApplyParamRows.addAll(data);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final TextView plateName;
        private final TextView userName;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            plateName = itemView.findViewById(R.id.traffic_check_apply_plate_name);
            userName = itemView.findViewById(R.id.traffic_check_apply_user_name);
        }
    }
}
