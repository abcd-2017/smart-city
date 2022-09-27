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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.R;
import com.example.pojo.metroparam.LastFoundListParam;
import com.example.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class LastFoundListAdapter extends RecyclerView.Adapter<LastFoundListAdapter.InnerHolder> {
    List<LastFoundListParam.DataDTO.MetroFoundListDTO> list = new ArrayList<>();

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_last_found_list, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        LastFoundListParam.DataDTO.MetroFoundListDTO dataDTO = list.get(position);
        View itemView = holder.itemView;
        holder.pickTime.setText(dataDTO.getFoundTime());
        holder.pickLocal.setText(dataDTO.getFoundPlace());
        holder.claimLocal.setText(dataDTO.getClaimPlace());
        Glide.with(itemView.getContext())
                .load(Constant.BASE_API + dataDTO.getImgUrl())
                .transform(new RoundedCorners(8))
                .into(holder.foundImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<LastFoundListParam.DataDTO.MetroFoundListDTO> data) {
        list.clear();
        list.addAll(data);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final ImageView foundImage;
        private final TextView pickTime;
        private final TextView pickLocal;
        private final TextView claimLocal;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            foundImage = itemView.findViewById(R.id.found_image);
            pickTime = itemView.findViewById(R.id.pickct_time);
            pickLocal = itemView.findViewById(R.id.pickct_local);
            claimLocal = itemView.findViewById(R.id.claim_localt);
        }
    }
}
