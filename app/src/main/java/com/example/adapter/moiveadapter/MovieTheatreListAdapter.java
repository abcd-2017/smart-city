package com.example.adapter.moiveadapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.R;
import com.example.pojo.movieparam.MovieTheatreListParam;
import com.example.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class MovieTheatreListAdapter extends RecyclerView.Adapter<MovieTheatreListAdapter.InnerHolder> {
    private final List<MovieTheatreListParam.RowsDTO> theatreListParamRows = new ArrayList<>();
    private MovieTheatreItemClickListener clickListener;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie_theatre_list, parent, false);
        return new InnerHolder(inflate);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        MovieTheatreListParam.RowsDTO rowsDTO = theatreListParamRows.get(position);

        holder.theatreAddress.setText(rowsDTO.getAddress());
        holder.theatreName.setText(rowsDTO.getName());
        holder.theatreDistance.setText(rowsDTO.getDistance());
        holder.theatreScore.setText(String.format("评分%d", rowsDTO.getScore()));

        Glide.with(holder.itemView.getContext())
                .load(Constant.BASE_API + rowsDTO.getCover())
                .transform(new RoundedCorners(14))
                .error(Constant.BASE_API + "/prod-api/profile/upload/image/2021/05/12/3f7ec6a0-e846-44e0-be9a-16292f4d815c.jpg")
                .into(holder.theatreCover);

        holder.theatreCard.setOnClickListener(view -> {
            clickListener.itemClick(rowsDTO.getId());
        });
    }

    @Override
    public int getItemCount() {
        return theatreListParamRows.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<MovieTheatreListParam.RowsDTO> data) {
        theatreListParamRows.clear();
        theatreListParamRows.addAll(data);
        notifyDataSetChanged();
    }

    public void setTheatreItemClickListener(MovieTheatreItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface MovieTheatreItemClickListener {
        void itemClick(Integer id);
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final ImageView theatreCover;
        private final ConstraintLayout theatreCard;
        private final TextView theatreAddress, theatreDistance, theatreName, theatreScore;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            theatreCover = itemView.findViewById(R.id.movie_theatre_list_cover);
            theatreAddress = itemView.findViewById(R.id.movie_theatre_list_address);
            theatreCard = itemView.findViewById(R.id.movie_theatre_film_list_card);
            theatreDistance = itemView.findViewById(R.id.movie_theatre_list_distance);
            theatreName = itemView.findViewById(R.id.movie_theatre_list_name);
            theatreScore = itemView.findViewById(R.id.movie_theatre_list_score);
        }
    }
}
