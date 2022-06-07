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
import com.example.pojo.movieparam.MovieTheatreRoomListParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class MovieTheatreRoomListAdapter extends RecyclerView.Adapter<MovieTheatreRoomListAdapter.InnerHolder> {

    private final List<MovieTheatreRoomListParam.RowsDTO> roomListParamRows = new ArrayList<>();
    private MovieRoomItemClickListener itemClickListener;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie_theatre_room_list, parent, false);
        return new InnerHolder(inflate);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        MovieTheatreRoomListParam.RowsDTO rowsDTO = roomListParamRows.get(position);

        holder.endTime.setText(String.format("%s 散场", rowsDTO.getEndTime()));
        holder.startTime.setText(rowsDTO.getStartTime());
        holder.roomName.setText(rowsDTO.getRoomName());
        holder.filmPrice.setText(String.valueOf(rowsDTO.getPrice()));
        holder.picketRemaining.setText(String.format("剩余 %d", rowsDTO.getTotalNum() - rowsDTO.getSaleNum()));

        holder.buyBtn.setOnClickListener(view -> {
            itemClickListener.clickBuyBtn(rowsDTO);
        });
    }

    @Override
    public int getItemCount() {
        return roomListParamRows.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<MovieTheatreRoomListParam.RowsDTO> data) {
        roomListParamRows.clear();
        roomListParamRows.addAll(data);
        notifyDataSetChanged();
    }

    public void setMovieRoomItemClickListener(MovieRoomItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface MovieRoomItemClickListener {
        void clickBuyBtn(MovieTheatreRoomListParam.RowsDTO item);
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final CardView buyBtn;
        private final TextView roomName, endTime, startTime, filmPrice, picketRemaining;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            buyBtn = itemView.findViewById(R.id.movie_theatre_room_list_buy_btn);
            roomName = itemView.findViewById(R.id.movie_theatre_room_list_room_name);
            endTime = itemView.findViewById(R.id.movie_theatre_room_list_end_time);
            startTime = itemView.findViewById(R.id.movie_theatre_room_list_start_time);
            filmPrice = itemView.findViewById(R.id.movie_theatre_room_list_price);
            picketRemaining = itemView.findViewById(R.id.movie_theatre_room_list_remaining);
        }
    }
}
