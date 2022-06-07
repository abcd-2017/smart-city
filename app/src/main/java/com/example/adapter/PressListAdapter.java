package com.example.adapter;

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
import com.example.pojo.PressListParam;
import com.example.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class PressListAdapter extends RecyclerView.Adapter<PressListAdapter.InnerHolder> {
    private final List<PressListParam.RowsDTO> list = new ArrayList<>();
    private ItemClickListener itemClickListener;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_press_list, parent, false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        View itemView = holder.itemView;
        PressListParam.RowsDTO rowsDTO = list.get(position);
        holder.pressTitle.setText(rowsDTO.getTitle());
        holder.commentNum.setText(String.valueOf(rowsDTO.getCommentNum()));
        holder.likeNum.setText(String.valueOf(rowsDTO.getLikeNum()));
        holder.releaseDate.setText(String.valueOf(rowsDTO.getPublishDate()));
        Glide.with(itemView.getContext()).
                load(Constant.BASE_API + rowsDTO.getCover()).
                transform(new RoundedCorners(14)).
                into(holder.pressImage);
        itemView.setOnClickListener(view -> {
            itemClickListener.click(rowsDTO.getId());
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void click(Integer id);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<PressListParam.RowsDTO> data) {
        list.clear();
        list.addAll(data);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final TextView pressTitle;
        private final ImageView pressImage;
        private final TextView likeNum;
        private final TextView commentNum;
        private final TextView releaseDate;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            pressTitle = itemView.findViewById(R.id.press_title);
            pressImage = itemView.findViewById(R.id.press_image);
            likeNum = itemView.findViewById(R.id.press_like_num);
            commentNum = itemView.findViewById(R.id.press_comment_num);
            releaseDate = itemView.findViewById(R.id.release_date);
        }
    }
}
