package com.example.activity.movieactivity;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.adapter.moiveadapter.MovieTheatreRoomListAdapter;
import com.example.pojo.movieparam.MovieFilmDetailParam;
import com.example.pojo.movieparam.MovieTheatreDetailParam;
import com.example.pojo.movieparam.MovieTheatreRoomListParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kkk
 */
public class MovieTheatreDetailActivity extends BaseActivity {


    private ImageView finishBtn;
    private ImageView filmCover;
    private Toolbar movieToolbar;
    private TextView theatreName, theatreAddress, theatreDistance, filmName, emptyText;
    private RecyclerView theatreRoomRecycler;
    private MovieTheatreRoomListAdapter theatreRoomListAdapter;
    private Gson gson = new Gson();

    @Override
    protected int initLayout() {
        return R.layout.activity_movie_theatre_detail;
    }

    @Override
    protected void initView() {
        finishBtn = findViewById(R.id.finish_btn);
        filmCover = findViewById(R.id.movie_theatre_detail_film_cover);
        movieToolbar = findViewById(R.id.movie_toolbar);
        theatreName = findViewById(R.id.movie_theatre_detail_name);
        theatreAddress = findViewById(R.id.movie_theatre_detail_address);
        theatreDistance = findViewById(R.id.movie_theatre_detail_distance);
        filmName = findViewById(R.id.movie_theatre_detail_film_name);
        emptyText = findViewById(R.id.empty_text);
        theatreRoomRecycler = findViewById(R.id.movie_theatre_detail_recycler);
        theatreRoomListAdapter = new MovieTheatreRoomListAdapter();
    }

    @Override
    protected void initData() {
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });
        movieToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        theatreRoomRecycler.setLayoutManager(new LinearLayoutManager(this));
        theatreRoomRecycler.setAdapter(theatreRoomListAdapter);

        int movieId = Integer.parseInt(getStringToSP("movie_id"));
        int theatreId = Integer.parseInt(getStringToSP("theatre_id"));

        //获取影片相关
        Api.config(Constant.NetWork.MOVIE_FILM_DETAIL, null, this).getRestfulRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                MovieFilmDetailParam filmDetailParam = gson.fromJson(result, MovieFilmDetailParam.class);
                if (filmDetailParam.getCode() == HttpURLConnection.HTTP_OK) {
                    MovieFilmDetailParam.DataDTO detailParamData = filmDetailParam.getData();
                    runOnUiThread(() -> {
                        filmName.setText(detailParamData.getName());
                        Glide.with(MovieTheatreDetailActivity.this)
                                .load(Constant.BASE_API + detailParamData.getCover())
                                .transform(new RoundedCorners(14))
                                .into(filmCover);
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

        //获取影院相关
        Api.config(Constant.NetWork.MOVIE_THEATRE_DETAIL, null, this).getRestfulRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                MovieTheatreDetailParam theatreDetailParam = gson.fromJson(result, MovieTheatreDetailParam.class);
                if (theatreDetailParam.getCode() == HttpURLConnection.HTTP_OK) {
                    MovieTheatreDetailParam.DataDTO detailParamData = theatreDetailParam.getData();
                    runOnUiThread(() -> {
                        theatreAddress.setText(detailParamData.getAddress());
                        theatreName.setText(detailParamData.getName());
                        theatreDistance.setText(detailParamData.getDistance());
                    });
                } else {
                    showSyncToast(theatreDetailParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        }, theatreId);

        Map<String, Object> param = new HashMap<>();
        param.put("movieId", movieId);
        param.put("theatreId", theatreId);

        //获取影片场次信息
        Api.config(Constant.NetWork.MOVIE_THEATRE_TIMES_LIST, param, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                MovieTheatreRoomListParam theatreRoomListParam = gson.fromJson(result, MovieTheatreRoomListParam.class);
                if (theatreRoomListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<MovieTheatreRoomListParam.RowsDTO> roomListParamRows = theatreRoomListParam.getRows();
                    runOnUiThread(() -> {
                        theatreRoomListAdapter.setData(roomListParamRows);
                    });
                } else {
                    showSyncToast(theatreRoomListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });

        theatreRoomListAdapter.setMovieRoomItemClickListener(item -> {
            setStringToSP("movie_room_id", String.valueOf(item.getRoomId()));
            setStringToSP("times_id", String.valueOf(item.getId()));
            jumpPage(MovieTheatreSeatActivity.class);
        });
    }
}