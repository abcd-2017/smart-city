package com.example.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.R;
import com.example.pojo.BaseParam;
import com.example.pojo.PressDetailsParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kkk
 */
public class PressDetailsActivity extends BaseActivity {

    private static final String TAG = "PressDetailsActivity";
    private TextView pressTitle;
    private TextView pressContent;
    private Toolbar toolbar;
    private ImageView pressImage;
    private ImageView likeBtn;
    private TextView searchContent;
    private RecyclerView commentRecycler;
    private Gson gson;

    @Override
    protected int initLayout() {
        return R.layout.activity_press_details;
    }

    @Override
    protected void initView() {
        toolbar = findViewById(R.id.press_details_toolbar);
        pressTitle = findViewById(R.id.press_details_title);
        pressContent = findViewById(R.id.press_details_content);
        pressImage = findViewById(R.id.press_details_image);
        likeBtn = findViewById(R.id.press_details_like_btn);
        searchContent = findViewById(R.id.press_details_search);
        commentRecycler = findViewById(R.id.press_details_comment_recycler);
    }

    @Override
    protected void initData() {
        toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
        Bundle extras = getIntent().getExtras();
        Object id = extras.get("id");
        Log.d(TAG, "initData: id => " + id);
        Api.config(Constant.NetWork.GET_PRESS_DETAILS, null, this).getRestfulRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                gson = new Gson();
                PressDetailsParam detailsParam = gson.fromJson(result, PressDetailsParam.class);
                if (detailsParam.getCode() == HttpURLConnection.HTTP_OK) {
                    PressDetailsParam.DataDTO dataDTO = detailsParam.getData();
                    runOnUiThread(() -> {
                        pressTitle.setText(dataDTO.getTitle());
                        pressTitle.getPaint().setFakeBoldText(true);
                        pressContent.setText(Html.fromHtml(dataDTO.getContent(), Html.FROM_HTML_MODE_COMPACT));
                        Glide.with(PressDetailsActivity.this)
                                .load(Constant.BASE_API + dataDTO.getCover())
                                .transform(new RoundedCorners(10))
                                .into(pressImage);
                    });
                } else {
                    showSyncToast(detailsParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        }, id);

        likeBtn.setOnClickListener(view -> {
            Api.config(Constant.NetWork.PRESS_LIKE, null, this).putRestfulRequest(new RequestCallback() {
                @Override
                public void success(String result) {
                    Gson gson = new Gson();
                    BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                    if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                        showSyncToast("点赞成功");
                        runOnUiThread(() -> {
                            likeBtn.setBackgroundResource(R.drawable.ic_like_select_24);
                        });
                    } else {
                        showSyncToast(baseParam.getMsg());
                    }
                }

                @Override
                public void failure(Exception e) {
                    showSyncToast("网络异常");
                }
            }, id);
        });

        searchContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    String searchStr = searchContent.getText().toString();
                    searchContent.setText(null);
                    if (!TextUtils.isEmpty(searchStr)) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                        Map<String, Object> map = new HashMap<>();
                        map.put("newsId", id);
                        map.put("content", searchStr);
                        Api.config(Constant.NetWork.SEND_PRESS_COMMENT, map, PressDetailsActivity.this).postRequest(new RequestCallback() {
                            @Override
                            public void success(String result) {
                                BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                                if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                                    showSyncToast("发表成功");
                                } else {
                                    showSyncToast(baseParam.getMsg());
                                }
                            }

                            @Override
                            public void failure(Exception e) {
                                showSyncToast("网络异常");
                            }
                        });
                    }
                }
                return false;
            }
        });

        Api.config(Constant.NetWork.PRESS_LIST, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {

            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
    }
}