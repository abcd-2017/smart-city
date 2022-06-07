package com.example.adapter.moiveadapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.pojo.movieparam.MovieTicketListParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class MovieTicketListAdapter extends RecyclerView.Adapter<MovieTicketListAdapter.InnerHolder> {
    private final List<MovieTicketListParam.RowsDTO> ticketListParamRows = new ArrayList<>();

    @NonNull
    @Override

    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie_ticket_list, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        MovieTicketListParam.RowsDTO rowsDTO = ticketListParamRows.get(position);

//        holder.filmName.setText(rowsDTO.);
    }

    @Override
    public int getItemCount() {
        return ticketListParamRows.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<MovieTicketListParam.RowsDTO> data) {
        ticketListParamRows.clear();
        ticketListParamRows.addAll(data);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final ImageView filmCover;
        private final TextView filmName, payTime, playTime, ticketStatus, ticketTheatre;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            filmCover = itemView.findViewById(R.id.item_movie_ticket_film_cover);
            filmName = itemView.findViewById(R.id.item_movie_ticket_film_name);
            payTime = itemView.findViewById(R.id.item_movie_ticket_pay_time);
            playTime = itemView.findViewById(R.id.item_movie_ticket_play_time);
            ticketStatus = itemView.findViewById(R.id.item_movie_ticket_status);
            ticketTheatre = itemView.findViewById(R.id.item_movie_ticket_theatre);
        }
    }
}
