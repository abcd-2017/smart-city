package com.example.activity.movieactivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.pojo.BaseParam;
import com.example.pojo.movieparam.MovieFilmDetailParam;
import com.example.pojo.movieparam.MovieFilmPreviewParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;

import java.net.HttpURLConnection;

/**
 * @author kkk
 */
public class MovieDetailActivity extends BaseActivity {

    private ImageView finishBtn, fileDetailCover;
    private Toolbar movieToolbar;
    private TextView filmDetailName, filmDetailType, filmDetailPlayTime, filmDetailTag, filmDetailLikeNum, filmDetailIntroduction;
    private CardView filmDetailLikeBtn, filmDetailFavoriteBtn;
    private RatingBar filmDetailRatingBar;
    private LinearLayout filmDetailAddBtn;
    private Gson gson = new Gson();
    private ConstraintLayout filmDetailRatingCard;

    @Override
    protected int initLayout() {
        return R.layout.activity_movie_detail;
    }

    @Override
    protected void initView() {
        finishBtn = findViewById(R.id.finish_btn);
        movieToolbar = findViewById(R.id.movie_toolbar);
        fileDetailCover = findViewById(R.id.movie_film_detail_cover);
        filmDetailName = findViewById(R.id.movie_film_name);
        filmDetailLikeBtn = findViewById(R.id.movie_film_like_btn);
        filmDetailFavoriteBtn = findViewById(R.id.movie_film_favorite_btn);
        filmDetailType = findViewById(R.id.movie_film_type);
        filmDetailPlayTime = findViewById(R.id.movie_film_pay_time);
        filmDetailTag = findViewById(R.id.movie_film_tag);
        filmDetailLikeNum = findViewById(R.id.movie_film_detail_like_num);
        filmDetailRatingBar = findViewById(R.id.movie_film_detail_rating_bar);
        filmDetailIntroduction = findViewById(R.id.movie_film_detail_introduction);
        filmDetailAddBtn = findViewById(R.id.movie_film_detail_add_btn);
        filmDetailRatingCard = findViewById(R.id.movie_film_detail_rating_card);
    }

    @Override
    protected void initData() {
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });
        movieToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        Bundle extras = getIntent().getExtras();
        Integer movieId = (Integer) extras.get("movieId");
        String movieType = (String) extras.get("movieType");

        if ("preview".equals(movieType)) {
            filmDetailAddBtn.setVisibility(View.GONE);
            filmDetailTag.setVisibility(View.GONE);
            filmDetailRatingCard.setVisibility(View.GONE);
            filmDetailType.setVisibility(View.GONE);

            Api.config(Constant.NetWork.MOVIE_FILM_PREVIEW, null, this).getRestfulRequest(new RequestCallback() {
                @Override
                public void success(String result) {
                    MovieFilmPreviewParam filmPreviewParam = gson.fromJson(result, MovieFilmPreviewParam.class);
                    if (filmPreviewParam.getCode() == HttpURLConnection.HTTP_OK) {
                        MovieFilmPreviewParam.DataDTO previewParamData = filmPreviewParam.getData();
                        runOnUiThread(() -> {
                            setFilmPreViewDataParam(previewParamData);
                        });
                    } else {
                        showSyncToast(filmPreviewParam.getMsg());
                    }
                }

                @Override
                public void failure(Exception e) {
                    showSyncToast("网络异常");
                }
            }, movieId);
        } else {
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

            filmDetailAddBtn.setOnClickListener(view -> {
                setStringToSP("movie_id", String.valueOf(movieId));
                jumpPage(MovieTheatreActivity.class);
            });
        }

        filmDetailLikeBtn.setOnClickListener(view -> {
            Api.config(Constant.NetWork.MOVIE_FILM_LIKE, null, this).postRestfulRequest(requestCallback, movieId);
        });
        filmDetailFavoriteBtn.setOnClickListener(view -> {
            Api.config(Constant.NetWork.MOVIE_FILM_LIKE, null, this).postRestfulRequest(requestCallback, movieId);
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

    private void setFilmPreViewDataParam(MovieFilmPreviewParam.DataDTO previewParamData) {
        filmDetailName.setText(previewParamData.getName());
        filmDetailIntroduction.setText(Html.fromHtml(previewParamData.getIntroduction(), Html.FROM_HTML_MODE_COMPACT));

        Glide.with(this)
                .load(Constant.BASE_API + previewParamData.getCover())
                .transform(new RoundedCorners(14))
                .into(fileDetailCover);

        filmDetailPlayTime.setText(String.format("%s  中国大陆上映",
                previewParamData.getPlayDate()));
    }

    private void setFilmDataParam(MovieFilmDetailParam.DataDTO detailParamData) {
        filmDetailName.setText(detailParamData.getName());
        filmDetailRatingBar.setRating(detailParamData.getScore());
        filmDetailIntroduction.setText(Html.fromHtml(detailParamData.getIntroduction(), Html.FROM_HTML_MODE_COMPACT));

        Glide.with(this)
                .load(Constant.BASE_API + detailParamData.getCover())
                .transform(new RoundedCorners(14))
                .into(fileDetailCover);

        filmDetailType.setText(String.format("%s  /  %s",
                Constant.FILM_CATEGORY.get(Integer.parseInt(detailParamData.getCategory()) - 1),
                detailParamData.getLanguage()));
        filmDetailPlayTime.setText(String.format("%s  中国大陆上映  /  %s分钟",
                detailParamData.getPlayDate(), detailParamData.getDuration()));
        filmDetailTag.setText(Constant.FILM_PLAY_TYPE.get(Integer.parseInt(detailParamData.getPlayType()) - 1));

        filmDetailLikeNum.setText(String.format("%s人喜欢 %s人想看"
                , detailParamData.getFavoriteNum(), detailParamData.getLikeNum()));
    }
}