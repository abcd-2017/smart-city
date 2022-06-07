package com.example.activity.movieactivity;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.adapter.moiveadapter.MovieTheatreListAdapter;
import com.example.pojo.BaseParam;
import com.example.pojo.movieparam.MovieFilmDetailParam;
import com.example.pojo.movieparam.MovieTheatreListParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.util.List;

/**
 * @author kkk
 */
public class MovieTheatreActivity extends BaseActivity {

    private ImageView finishBtn;
    private Toolbar movieToolbar;
    private CardView filmLikeBtn, filmFavoriteBtn;
    private ImageView filmCover;
    private TextView fileTitleText, filmName, filmPlayTime, filmType, filmTag, filmLanguage;
    private RecyclerView theatreListRecycler;
    private MovieTheatreListAdapter movieTheatreListAdapter;
    private Gson gson = new Gson();

    @Override
    protected int initLayout() {
        return R.layout.activity_movie_theatre;
    }

    @Override
    protected void initView() {
        finishBtn = findViewById(R.id.finish_btn);
        movieToolbar = findViewById(R.id.movie_toolbar);
        filmLikeBtn = findViewById(R.id.movie_theatre_film_like_btn);
        filmFavoriteBtn = findViewById(R.id.movie_theatre_film_favorite_btn);
        filmCover = findViewById(R.id.movie_theatre_film_detail_cover);
        fileTitleText = findViewById(R.id.movie_theatre_film_title_text);
        filmName = findViewById(R.id.movie_theatre_film_name);
        filmPlayTime = findViewById(R.id.movie_theatre_film_pay_time);
        filmType = findViewById(R.id.movie_theatre_film_type);
        filmTag = findViewById(R.id.movie_theatre_film_tag);
        filmLanguage = findViewById(R.id.movie_theatre_film_language);
        theatreListRecycler = findViewById(R.id.movie_theatre_list_recycler);
        movieTheatreListAdapter = new MovieTheatreListAdapter();
    }

    @Override
    protected void initData() {
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });
        movieToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        Integer movieId = Integer.parseInt(getStringToSP("movie_id"));

        theatreListRecycler.setLayoutManager(new LinearLayoutManager(this));
        theatreListRecycler.setAdapter(movieTheatreListAdapter);
        theatreListRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        Api.config(Constant.NetWork.MOVIE_FILM_DETAIL, null, this).getRestfulRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                MovieFilmDetailParam filmDetailParam = gson.fromJson(result, MovieFilmDetailParam.class);
                if (filmDetailParam.getCode() == HttpURLConnection.HTTP_OK) {
                    MovieFilmDetailParam.DataDTO detailParamData = filmDetailParam.getData();
                    runOnUiThread(() -> {
                        setFilmDataParam(detailParamData);
                    });
                } else {
                    showSyncToast(filmDetailParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        }, movieId);

        Api.config(Constant.NetWork.MOVIE_THEATRE, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                MovieTheatreListParam theatreListParam = gson.fromJson(result, MovieTheatreListParam.class);
                if (theatreListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<MovieTheatreListParam.RowsDTO> theatreListParamRows = theatreListParam.getRows();
                    runOnUiThread(() -> {
                        movieTheatreListAdapter.setData(theatreListParamRows);
                    });
                } else {
                    showSyncToast(theatreListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });

        filmLikeBtn.setOnClickListener(view -> {
            Api.config(Constant.NetWork.MOVIE_FILM_LIKE, null, this).postRestfulRequest(requestCallback, movieId);
        });
        filmFavoriteBtn.setOnClickListener(view -> {
            Api.config(Constant.NetWork.MOVIE_FILM_LIKE, null, this).postRestfulRequest(requestCallback, movieId);
        });

        movieTheatreListAdapter.setTheatreItemClickListener(id -> {
            setStringToSP("theatre_id", String.valueOf(id));
            jumpPage(MovieTheatreDetailActivity.class);
        });
    }

    private RequestCallback requestCallback = new RequestCallback() {
        @Override
        public void success(String result) {
            BaseParam baseParam = gson.fromJson(result, BaseParam.class);
            if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                showSyncToast("添加成功");
            } else {
                showSyncToast(baseParam.getMsg());
            }
        }

        @Override
        public void failure(Exception e) {
            showSyncToast("网络异常");
        }
    };

    private void setFilmDataParam(MovieFilmDetailParam.DataDTO detailParamData) {
        filmName.setText(detailParamData.getName());
        fileTitleText.setText(detailParamData.getName());

        Glide.with(this)
                .load(Constant.BASE_API + detailParamData.getCover())
                .transform(new RoundedCorners(14))
                .into(filmCover);

        filmType.setText(Constant.FILM_CATEGORY.get(Integer.parseInt(detailParamData.getCategory()) - 1));
        filmPlayTime.setText(String.format("%s  中国大陆上映", detailParamData.getPlayDate()));
        filmTag.setText(Constant.FILM_PLAY_TYPE.get(Integer.parseInt(detailParamData.getPlayType()) - 1));
        filmLanguage.setText(String.format("%s  /  %s分钟",
                detailParamData.getLanguage(), detailParamData.getDuration()));
    }
}