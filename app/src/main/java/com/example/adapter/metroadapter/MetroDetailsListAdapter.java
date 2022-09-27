package com.example.adapter.metroadapter;

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
import com.example.pojo.metroparam.MetroDetailsParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class MetroDetailsListAdapter extends RecyclerView.Adapter<MetroDetailsListAdapter.InnerHolder> {
    String runStationsName;
    List<MetroDetailsParam.DataDTO.MetroStepListDTO> list = new ArrayList<>();


    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_metro_list_details, parent, false);
        return new InnerHolder(view);
    }

    @SuppressLint({"CheckResult", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        View itemView = holder.itemView;
        MetroDetailsParam.DataDTO.MetroStepListDTO dataDTO = list.get(position);
        holder.stepName.setText(dataDTO.getName());
        if (dataDTO.getName().equals(runStationsName)) {
            Glide.with(itemView.getContext())
                    .load(itemView.getResources().getDrawable(R.drawable.shape_home_round_select, null))
                    .into(holder.stepImage);
        } else {
            Glide.with(itemView.getContext())
                    .load(itemView.getResources().getDrawable(R.drawable.shape_home_round, null))
                    .into(holder.stepImage);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<MetroDetailsParam.DataDTO.MetroStepListDTO> data, String runStationsName) {
        list.clear();
        list.addAll(data);
        this.runStationsName = runStationsName;
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final ImageView stepImage;
        private final TextView stepName;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            stepImage = itemView.findViewById(R.id.metro_step_image);
            stepName = itemView.findViewById(R.id.metro_step_name);
        }
    }
}
