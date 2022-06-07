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
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.R;
import com.example.pojo.activityparam.ActivityCommentListParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class ActivityCommentAdapter extends RecyclerView.Adapter<ActivityCommentAdapter.InnerHolder> {
    List<ActivityCommentListParam.RowsDTO> list = new ArrayList<>();

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment_list, parent, false);
        return new InnerHolder(inflate);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        View itemView = holder.itemView;
        ActivityCommentListParam.RowsDTO rowsDTO = list.get(position);
        holder.username.setText(rowsDTO.getNickName());
        holder.content.setText(rowsDTO.getContent());
        holder.publishTime.setText(rowsDTO.getCommentTime());
        Glide.with(itemView.getContext())
                .load(itemView.getResources().getDrawable(R.mipmap.user_img, null))
                .transform(new CircleCrop())
                .into(holder.avator);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<ActivityCommentListParam.RowsDTO> data) {
        list.clear();
        list.addAll(data);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final TextView username;
        private final TextView content;
        private final TextView publishTime;
        private final ImageView avator;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.comment_user_name);
            content = itemView.findViewById(R.id.comment_content);
            publishTime = itemView.findViewById(R.id.comment_publish_time);
            avator = itemView.findViewById(R.id.comment_image);
        }
    }
}
