package com.example.adapter.moiveadapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.pojo.movieparam.MovieOrderListParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class MovieTicketOrderListAdapter extends RecyclerView.Adapter<MovieTicketOrderListAdapter.InnerHolder> {
    private final List<MovieOrderListParam.RowsDTO> orderListParamRows = new ArrayList<>();
    private MovieOrderItemClickListener clickListener;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie_ticket_order_list, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        MovieOrderListParam.RowsDTO rowsDTO = orderListParamRows.get(position);

        holder.filmName.setText(rowsDTO.getMovieName());
        holder.payTime.setText(String.format("%s  %s - %s", rowsDTO.getPlayDate(), rowsDTO.getStartTime(), rowsDTO.getEndTime()));
        holder.orderTheatre.setText(String.format("%s %s", rowsDTO.getTheatreName(), rowsDTO.getRoomName()));
        holder.filmPrice.setText(String.format("￥%s", rowsDTO.getPrice()));

        if ("Y".equals(rowsDTO.getStatus())) {
            holder.payBtn.setVisibility(View.GONE);
            holder.orderStatus.setVisibility(View.GONE);
        } else {
            holder.orderStatus.setText("未支付");
            holder.payBtn.setOnClickListener(view -> {
                clickListener.clickBtn(rowsDTO.getId());
            });
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDate(List<MovieOrderListParam.RowsDTO> data) {
        orderListParamRows.clear();
        orderListParamRows.addAll(data);
        notifyDataSetChanged();
    }

    public void setMovieOrderClickListener(MovieOrderItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface MovieOrderItemClickListener {
        void clickBtn(Integer id);
    }

    @Override
    public int getItemCount() {
        return orderListParamRows.size();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final CardView payBtn;
        private final TextView filmName, filmPrice, payTime, orderStatus, orderTheatre;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            payBtn = itemView.findViewById(R.id.item_movie_order_pay_btn);
            filmName = itemView.findViewById(R.id.item_movie_order_film_name);
            filmPrice = itemView.findViewById(R.id.item_movie_order_film_price);
            payTime = itemView.findViewById(R.id.item_movie_order_pay_time);
            orderStatus = itemView.findViewById(R.id.item_movie_order_status);
            orderTheatre = itemView.findViewById(R.id.item_movie_order_theatre);
        }
    }
}
