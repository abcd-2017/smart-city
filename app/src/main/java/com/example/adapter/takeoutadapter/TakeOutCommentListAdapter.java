package com.example.adapter.takeoutadapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.pojo.takeoutparam.TakeOutSellerCommentListParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class TakeOutCommentListAdapter extends RecyclerView.Adapter<TakeOutCommentListAdapter.InnerHolder> {
    private List<TakeOutSellerCommentListParam.RowsDTO> listParamRows = new ArrayList<>();

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_take_out_seller_comment, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        TakeOutSellerCommentListParam.RowsDTO rowsDTO = listParamRows.get(position);

        holder.commentDate.setText(rowsDTO.getCommentDate());
        holder.commentUserName.setText(rowsDTO.getUserName());
        holder.commentTag.setText(rowsDTO.getSellerName());
        holder.commentComm.setText(rowsDTO.getContent());

        holder.commentRating.setRating(rowsDTO.getScore());
    }

    @Override
    public int getItemCount() {
        return listParamRows.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<TakeOutSellerCommentListParam.RowsDTO> data) {
        listParamRows.clear();
        listParamRows.addAll(data);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final TextView commentComm, commentDate, commentTag, commentUserName;
        private final RatingBar commentRating;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            commentComm = itemView.findViewById(R.id.item_take_out_seller_comment_comm);
            commentDate = itemView.findViewById(R.id.item_take_out_seller_comment_date);
            commentTag = itemView.findViewById(R.id.item_take_out_seller_comment_tag);
            commentUserName = itemView.findViewById(R.id.item_take_out_seller_comment_user_name);
            commentRating = itemView.findViewById(R.id.item_take_out_seller_comment_rating);
        }
    }
}
