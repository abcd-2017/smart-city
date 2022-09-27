package com.example.adapter.activityadapter;

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
import com.example.pojo.activityparam.ActivityListParam;
import com.example.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.InnerHolder> {
    List<ActivityListParam.RowsDTO> list = new ArrayList<>();
    private ItemClickListener itemClickListener;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_list, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        ActivityListParam.RowsDTO rowsDTO = list.get(position);
        holder.likeNum.setText(String.valueOf(rowsDTO.getLikeNum()));
        holder.signupNum.setText(String.valueOf(rowsDTO.getSignupNum()));
        holder.name.setText(rowsDTO.getName());
        Glide.with(holder.itemView.getContext())
                .load(Constant.BASE_API + rowsDTO.getImgUrl())
                .transform(new RoundedCorners(16))
                .into(holder.image);

        holder.itemView.setOnClickListener(view -> {
            itemClickListener.click(rowsDTO.getId());
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<ActivityListParam.RowsDTO> data) {
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

        private final ImageView image;
        private final TextView likeNum;
        private final TextView signupNum;
        private final TextView name;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.activity_image);
            likeNum = itemView.findViewById(R.id.activity_like_num);
            signupNum = itemView.findViewById(R.id.activity_signup_num);
            name = itemView.findViewById(R.id.activity_name);
        }
    }
}
