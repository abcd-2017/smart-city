package com.example.adapter.moiveadapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.R;
import com.example.pojo.movieparam.MovieFilmListParam;
import com.example.pojo.movieparam.MovieFilmPreviewListParam;
import com.example.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.InnerHolder> {
    private final List<MovieFilmPreviewListParam.RowsDTO> previewListParamRows = new ArrayList<>();
    private final List<MovieFilmListParam.RowsDTO> filmListParamRows = new ArrayList<>();
    private boolean isFilm = true;

    private MovieItemBtnClickListener clickListener;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie_list, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        if (isFilm) {
            MovieFilmListParam.RowsDTO rowsDTO = filmListParamRows.get(position);

            Glide.with(holder.itemView.getContext())
                    .load(Constant.BASE_API + rowsDTO.getCover())
                    .transform(new RoundedCorners(12))
                    .into(holder.movieCover);

            holder.moviePlayDate.setVisibility(View.GONE);
            holder.movieName.setText(rowsDTO.getName());
            holder.movieBtnText.setText("购票");
            holder.movieBtn.setCardBackgroundColor(Color.rgb(255, 30, 30));

            holder.movieBtn.setOnClickListener(view -> {
                clickListener.click(rowsDTO.getId());
            });
        } else {
            MovieFilmPreviewListParam.RowsDTO rowsDTO = previewListParamRows.get(position);

            Glide.with(holder.itemView.getContext())
                    .load(Constant.BASE_API + rowsDTO.getCover())
                    .transform(new RoundedCorners(12))
                    .into(holder.movieCover);

            holder.moviePlayDate.setVisibility(View.GONE);
            holder.movieName.setText(rowsDTO.getName());
            holder.movieBtnText.setText("想看");
            holder.movieBtn.setCardBackgroundColor(Color.rgb(255, 203, 30));

            holder.movieBtn.setOnClickListener(view -> {
                clickListener.click(rowsDTO.getId());
            });
        }
    }

    @Override
    public int getItemCount() {
        if (isFilm) {
            return filmListParamRows.size();
        } else {
            return previewListParamRows.size();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setPreviewData(List<MovieFilmPreviewListParam.RowsDTO> previewData) {
        previewListParamRows.clear();
        previewListParamRows.addAll(previewData);
        isFilm = false;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setFilmData(List<MovieFilmListParam.RowsDTO> filmData) {
        filmListParamRows.clear();
        filmListParamRows.addAll(filmData);
        isFilm = true;
        notifyDataSetChanged();
    }

    public void setMovieItemClickListener(MovieItemBtnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface MovieItemBtnClickListener {
        void click(Integer id);
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final TextView moviePlayDate, movieName, movieBtnText;
        private final CardView movieBtn;
        private final ImageView movieCover;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            moviePlayDate = itemView.findViewById(R.id.movie_list_play_date);
            movieName = itemView.findViewById(R.id.movie_list_name);
            movieBtnText = itemView.findViewById(R.id.movie_list_btn_text);
            movieBtn = itemView.findViewById(R.id.movie_list_btn);
            movieCover = itemView.findViewById(R.id.movie_list_cover);
        }
    }
}
