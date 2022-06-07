package com.example.activity.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.R;
import com.example.activity.BaseActivity;
import com.example.adapter.activityadapter.ActivityCommentAdapter;
import com.example.pojo.BaseParam;
import com.example.pojo.activityparam.ActivityCheckSignParam;
import com.example.pojo.activityparam.ActivityCommentListParam;
import com.example.pojo.activityparam.ActivityCommentNumberParam;
import com.example.pojo.activityparam.ActivityDetailsParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.constant.SpinnerStyle;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kkk
 */
public class ActivityDetailsActivity extends BaseActivity {

    private static final String TAG = "ActivityDetailsActivity";
    private ImageView detailImage;
    private TextView detailsTitle, detailsLikeNum, commentNum, detailsContent, detailTag;
    private RecyclerView detailRecycler;
    private Integer activityId;
    private Gson gson = new Gson();
    private Toolbar detailsToolbar;
    private TextView publishTime;
    private Map<String, Object> param = new HashMap<>();
    private Integer pageNum = 1;
    private ActivityCommentAdapter commentAdapter;
    private SmartRefreshLayout refreshLayout;
    private List<ActivityCommentListParam.RowsDTO> list = new ArrayList<>();
    private ActivityCommentNumberParam numberParam;
    private Button commentBtn;
    private EditText publishComment;
    private Button signupBtn;
    private Boolean isSignup = false;

    @Override
    protected int initLayout() {
        return R.layout.activity_details;
    }

    @Override
    protected void initView() {
        detailsTitle = findViewById(R.id.activity_details_title);
        detailImage = findViewById(R.id.activity_details_image);
        detailsLikeNum = findViewById(R.id.details_like_num);
        commentNum = findViewById(R.id.comment_number);
        detailRecycler = findViewById(R.id.detail_comment_recycler);
        detailsContent = findViewById(R.id.activity_details_content);
        detailsToolbar = findViewById(R.id.activity_detail_toolbar);
        detailTag = findViewById(R.id.activity_details_tag);
        publishTime = findViewById(R.id.activity_detail_publish_time);
        refreshLayout = findViewById(R.id.comment_refresh);
        publishComment = findViewById(R.id.publish_comment);
        commentBtn = findViewById(R.id.comment_btn);
        signupBtn = findViewById(R.id.signup_activity);
        commentAdapter = new ActivityCommentAdapter();
    }

    @Override
    protected void initData() {
        detailsToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        Bundle extras = getIntent().getExtras();
        activityId = (Integer) extras.get("ActivityId");
        Api.config(Constant.NetWork.ACTIVITY_DETAILS, null, this).getRestfulRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                ActivityDetailsParam detailsParam = gson.fromJson(result, ActivityDetailsParam.class);
                if (detailsParam.getCode() == HttpURLConnection.HTTP_OK) {
                    ActivityDetailsParam.DataDTO paramData = detailsParam.getData();
                    runOnUiThread(() -> {
                        detailsTitle.setText(paramData.getName());
                        detailsTitle.getPaint().setFakeBoldText(true);
                        detailsContent.setText(Html.fromHtml(paramData.getContent(), Html.FROM_HTML_MODE_LEGACY));
                        Glide.with(ActivityDetailsActivity.this)
                                .load(Constant.BASE_API + paramData.getImgUrl())
                                .transform(new RoundedCorners(16))
                                .into(detailImage);
                        detailsLikeNum.setText(String.valueOf(paramData.getLikeNum()));
                        detailTag.setText(paramData.getCategoryName());
                        publishTime.setText(paramData.getPublishTime());
                    });
                } else {
                    showSyncToast(detailsParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        }, activityId);

        detailRecycler.setLayoutManager(new LinearLayoutManager(this));
        detailRecycler.setAdapter(commentAdapter);
        detailRecycler.setNestedScrollingEnabled(false);

        refreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Translate));
        refreshLayout.setEnableRefresh(false);

        refreshLayout.setOnLoadMoreListener(refresh -> {
            pageNum++;
            getComment();
            refresh.finishLoadMore(2000);
        });

        getComment();

        commentBtn.setOnClickListener(view -> {
            String publishContent = publishComment.getText().toString();
            if (TextUtils.isEmpty(publishContent)) {
                showSyncToast("评论内容为空!");
                return;
            }
            param.clear();
            param.put("activityId", activityId);
            param.put("content", publishContent);
            Api.config(Constant.NetWork.ACTIVITY_COMMENT, param, this).postRequest(new RequestCallback() {
                @Override
                public void success(String result) {
                    BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                    if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                        pageNum = 1;
                        list.clear();
                        showSyncToast("评论成功");
                        getComment();
                    } else {
                        showSyncToast(baseParam.getMsg());
                    }
                }

                @Override
                public void failure(Exception e) {
                    showSyncToast("网络异常");
                }
            });
            publishComment.setText("");
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        });

        checkSignup();

        signupBtn.setOnClickListener(view -> {
            if (isSignup) {
                showSyncToast("活动已经报名");
                return;
            }
            param.clear();
            param.put("activityId", activityId);
            Api.config(Constant.NetWork.SIGNUP_ACTIVITY, param, this).postRequest(new RequestCallback() {
                @Override
                public void success(String result) {
                    BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                    if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                        showSyncToast("报名成功");
                        checkSignup();
                    } else {
                        showSyncToast(baseParam.getMsg());
                    }
                }

                @Override
                public void failure(Exception e) {
                    showSyncToast("网络异常");
                }
            });
        });
    }

    private void getComment() {
        param.clear();
        param.put("activityId", activityId);
        Api.config(Constant.NetWork.ACTIVITY_COMMENT_NUMBER, param, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                numberParam = gson.fromJson(result, ActivityCommentNumberParam.class);
                if (numberParam.getCode() == HttpURLConnection.HTTP_OK) {
                    runOnUiThread(() -> {
                        commentNum.setText(String.valueOf(numberParam.getCommentNum()));
                    });
                    getCommentList();
                } else {
                    showSyncToast(numberParam.getMsg());
                }

            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
    }

    private void getCommentList() {
        param.put("pageNum", pageNum);
        param.put("pageSize", Constant.pageSize);
        Log.d(TAG, "getComment: " + param);
        Api.config(Constant.NetWork.ACTIVITY_COMMENT_LIST, param, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                ActivityCommentListParam commentListParam = gson.fromJson(result, ActivityCommentListParam.class);
                if (commentListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<ActivityCommentListParam.RowsDTO> listParamRows = commentListParam.getRows();
                    if (listParamRows.size() != 0 && pageNum <= Math.ceil((double) numberParam.getCommentNum() / Constant.pageSize)) {
                        list.addAll(listParamRows);
                        runOnUiThread(() -> {
                            commentAdapter.setData(list);
                        });
                    } else {
                        showSyncToast("没有更多评论了");
                    }
                } else {
                    showSyncToast(commentListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
                refreshLayout.finishLoadMore(false);
            }
        });
    }

    public void checkSignup() {
        param.clear();
        param.put("activityId", activityId);
        Api.config(Constant.NetWork.CHECK_SIGNUP_ACTIVITY, param, this).getRequest(new RequestCallback() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void success(String result) {
                ActivityCheckSignParam checkSignParam = gson.fromJson(result, ActivityCheckSignParam.class);
                if (checkSignParam.getCode() == HttpURLConnection.HTTP_OK) {
                    isSignup = checkSignParam.getIsSignup();
                    if (isSignup) {
                        runOnUiThread(() -> {
                            signupBtn.setBackgroundResource(R.drawable.shape_gray_background);
                            signupBtn.setText("已报名");
                        });
                    }
                } else {
                    showSyncToast(checkSignParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
    }
}