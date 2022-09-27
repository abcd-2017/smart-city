package com.example.activity.useractivity;

import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.pojo.BaseParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.time.temporal.ValueRange;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kkk
 */
public class FeedbackActivity extends BaseActivity {

    private static final String TAG = "FeedbackActivity";
    private Toolbar toolbar;
    private EditText feedbackTitle;
    private EditText feedbackContent;
    private Button submitFeedback;

    @Override
    protected int initLayout() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initView() {
        toolbar = findViewById(R.id.toolbar);
        feedbackTitle = findViewById(R.id.feedback_title);
        feedbackContent = findViewById(R.id.feedback_content);
        submitFeedback = findViewById(R.id.submit_feedback);
    }

    @Override
    protected void initData() {
        toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
        submitFeedback.setOnClickListener(view -> {
            String title = feedbackTitle.getText().toString();
            String content = feedbackContent.getText().toString();
            if (TextUtils.isEmpty(title)) {
                showSyncToast("请输入标题");
                return;
            }
            if (TextUtils.isEmpty(content)) {
                showSyncToast("内容不能为空");
                return;
            }
            Map<String, Object> param = new HashMap<>();
            param.put("content", content);
            param.put("title", title);
            Api.config(Constant.NetWork.ADD_FEEDBACK, param, this).postRequest(new RequestCallback() {
                @Override
                public void success(String result) {
                    Gson gson = new Gson();
                    BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                    if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                        showSyncToast("添加成功");
                        finish();
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
}