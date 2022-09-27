package com.example.activity.movieactivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.adapter.moiveadapter.MovieListAdapter;
import com.example.pojo.movieparam.MovieFilmListParam;
import com.example.pojo.movieparam.MovieFilmPreviewListParam;
import com.example.pojo.movieparam.MovieRotationListParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.AttachListPopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.BaseIndicator;

import java.net.HttpURLConnection;
import java.util.List;

/**
 * @author kkk
 */
public class MovieActivity extends BaseActivity {

    private ImageView finishBtn, moreBtn;
    private Banner<MovieRotationListParam.RowsDTO, BannerImageAdapter<MovieRotationListParam.RowsDTO>> movieRotation;
    private RecyclerView hitMovieRecycler, toBtnShownRecycler;
    private MovieListAdapter hitMovieAdapter, toBeShownAdapter;
    private Gson gson = new Gson();
    private boolean isClearPage = false, pageStatus = false;

    @Override
    protected int initLayout() {
        return R.layout.activity_movie;
    }

    @Override
    protected void initView() {
        finishBtn = findViewById(R.id.finish_btn);
        movieRotation = findViewById(R.id.movie_rotation_list);
        hitMovieRecycler = findViewById(R.id.movie_hit_movie_recycler);
        toBtnShownRecycler = findViewById(R.id.movie_to_be_shown_recycler);
        moreBtn = findViewById(R.id.moreBtn);
        hitMovieAdapter = new MovieListAdapter();
        toBeShownAdapter = new MovieListAdapter();
    }

    @Override
    protected void initData() {
        finishBtn.setOnClickListener(view -> {
            finish();
        });

        hitMovieRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        hitMovieRecycler.setAdapter(hitMovieAdapter);

        toBtnShownRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        toBtnShownRecycler.setAdapter(toBeShownAdapter);

        Api.config(Constant.NetWork.MOVIE_ROTATION, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                MovieRotationListParam movieListParam = gson.fromJson(result, MovieRotationListParam.class);
                if (movieListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<MovieRotationListParam.RowsDTO> movieListParamRows = movieListParam.getRows();
                    runOnUiThread(() -> {
                        setRotationList(movieListParamRows);
                    });
                } else {
                    showSyncToast(movieListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });

        Api.config(Constant.NetWork.MOVIE_FILM_LIST, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                MovieFilmListParam movieFilmListParam = gson.fromJson(result, MovieFilmListParam.class);
                if (movieFilmListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<MovieFilmListParam.RowsDTO> filmListParamRows = movieFilmListParam.getRows();
                    runOnUiThread(() -> {
                        hitMovieAdapter.setFilmData(filmListParamRows);
                    });
                } else {
                    showSyncToast(movieFilmListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });

        hitMovieAdapter.setMovieItemClickListener(id -> {
            pageStatus = false;
            jumpPageToIntent(new Intent(this, MovieDetailActivity.class)
                    .putExtra("movieId", id)
                    .putExtra("movieType", "film"));
        });

        Api.config(Constant.NetWork.MOVIE_FILM_PREVIEW_LIST, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                MovieFilmPreviewListParam filePreviewListParam = gson.fromJson(result, MovieFilmPreviewListParam.class);
                if (filePreviewListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<MovieFilmPreviewListParam.RowsDTO> previewListParamRows = filePreviewListParam.getRows();
                    runOnUiThread(() -> {
                        toBeShownAdapter.setPreviewData(previewListParamRows);
                    });
                } else {
                    showSyncToast(filePreviewListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });

        toBeShownAdapter.setMovieItemClickListener(id -> {
            pageStatus = false;
            jumpPageToIntent(new Intent(this, MovieDetailActivity.class)
                    .putExtra("movieId", id)
                    .putExtra("movieType", "preview"));
        });

        AttachListPopupView listPopupView = new XPopup.Builder(this)
                .hasShadowBg(false)
                .atView(moreBtn)
                .asAttachList(new String[]{"我的订单", "我的影票"}, new int[0], new OnSelectListener() {
                    @Override
                    public void onSelect(int position, String text) {
                        pageStatus = false;
                        if (position == 0) {
                            jumpPage(MovieTicketOrderActivity.class);
                        } else if (position == 1) {
                            jumpPage(MovieTicketActivity.class);
                        }
                    }
                });

        moreBtn.setOnClickListener(view -> {
            listPopupView.show();
        });
    }

    private void setRotationList(List<MovieRotationListParam.RowsDTO> movieListParamRows) {
        movieRotation.setAdapter(new BannerImageAdapter<MovieRotationListParam.RowsDTO>(movieListParamRows) {
            @Override
            public void onBindView(BannerImageHolder holder, MovieRotationListParam.RowsDTO data, int position, int size) {
                holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                Glide.with(holder.itemView.getContext())
                        .load(Constant.BASE_API + data.getAdvImg())
                        .transform(new RoundedCorners(12))
                        .into(holder.imageView);
            }
        }).addBannerLifecycleObserver(this)
                .setIndicator(new BaseIndicator(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            isClearPage = (boolean) extras.get("isClearPage");
        }
        pageStatus = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isClearPage && pageStatus) {
            jumpPageFlag(HomeActivity.class, Constant.CLEAR_INTENT_STACK);
        }
    }
}