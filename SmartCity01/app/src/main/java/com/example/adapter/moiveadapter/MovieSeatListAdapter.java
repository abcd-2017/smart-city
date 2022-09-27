package com.example.adapter.moiveadapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class MovieSeatListAdapter extends RecyclerView.Adapter<MovieSeatListAdapter.InnerHolder> {
    private Integer filmPrice;
    private final List<int[]> seatList = new ArrayList<>();

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie_theatre_seat_list, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        int[] ints = seatList.get(position);

        holder.filmPrice.setText(String.format("￥%s", filmPrice));
        holder.seatName.setText(String.format("%s排%s座", ints[0], ints[1]));
    }

    public void setFilmPrice(Integer filmPrice) {
        this.filmPrice = filmPrice;
    }

    @Override
    public int getItemCount() {
        return seatList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSeatList(List<int[]> list) {
        seatList.clear();
        seatList.addAll(list);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final TextView seatName;
        private final TextView filmPrice;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            seatName = itemView.findViewById(R.id.item_movie_seat_name);
            filmPrice = itemView.findViewById(R.id.item_movie_seat_price);
        }
    }
}
