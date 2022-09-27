package com.example.activity.workactivity;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.pojo.User;
import com.example.pojo.UserInfoParam;
import com.example.pojo.workparam.WorkProfessionListParam;
import com.example.pojo.workparam.WorkResumeListParam;
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
public class WorkResumeActivity extends BaseActivity {

    private ImageView finishBtn;
    private Toolbar workToolbar;
    private TextView resumeExperience, resumeName, resumeMostEducation, resumeAddress, resumeIndividual, resumePosition, resumeMoney;
    private CardView resumeUpdateBtn;
    private Gson gson = new Gson();
    private Map<Integer, String> professionMap = new HashMap<>();
    private User user;

    @Override
    protected int initLayout() {
        return R.layout.activity_work_resume;
    }

    @Override
    protected void initView() {
        finishBtn = findViewById(R.id.finish_btn);
        workToolbar = findViewById(R.id.work_toolbar);
        resumeExperience = findViewById(R.id.work_resume_experience);
        resumeName = findViewById(R.id.work_resume_name);
        resumeMostEducation = findViewById(R.id.work_resume_most_education);
        resumeAddress = findViewById(R.id.work_resume_address);
        resumeIndividual = findViewById(R.id.work_resume_individual_resume);
        resumePosition = findViewById(R.id.work_resume_position);
        resumeMoney = findViewById(R.id.work_resume_money);
        resumeUpdateBtn = findViewById(R.id.work_resume_update_btn);
    }

    @Override
    protected void initData() {
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });
        workToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        Api.config(Constant.NetWork.JOB_PROFESSION_LIST, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                WorkProfessionListParam professionListParam = gson.fromJson(result, WorkProfessionListParam.class);
                if (professionListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<WorkProfessionListParam.RowsDTO> listParamRows = professionListParam.getRows();
                    for (WorkProfessionListParam.RowsDTO listParamRow : listParamRows) {
                        professionMap.put(listParamRow.getId(), listParamRow.getProfessionName());
                    }
                } else {
                    showSyncToast(professionListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });

        Api.config(Constant.NetWork.GETINFO, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                UserInfoParam userInfoParam = gson.fromJson(result, UserInfoParam.class);
                if (userInfoParam.getCode() == HttpURLConnection.HTTP_OK) {
                    user = userInfoParam.getUser();
                    runOnUiThread(() -> {
                        resumeName.setText(user.getUserName());
                        getData();
                        setStringToSP("id", String.valueOf(user.getUserId()));
                    });
                } else {
                    showSyncToast(userInfoParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });

        resumeUpdateBtn.setOnClickListener(view -> {
            jumpPageToIntent(new Intent(this, WorkAddResumeActivity.class)
                    .putExtra("resumeType", "update")
                    .putExtra("profession", resumePosition.getText()));
        });
    }

    private void getData() {
        Api.config(Constant.NetWork.QUERY_RESUME_DETAIL, null, this).getRestfulRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                WorkResumeListParam resumeListParam = gson.fromJson(result, WorkResumeListParam.class);
                if (resumeListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    WorkResumeListParam.DataDTO resumeListParamData = resumeListParam.getData();
                    runOnUiThread(() -> {
                        resumeAddress.setText(resumeListParamData.getAddress());
                        resumeMostEducation.setText(resumeListParamData.getMostEducation());
                        resumeMoney.setText(resumeListParamData.getMoney());
                        resumePosition.setText(professionMap.get(resumeListParamData.getPositionId()));
                        if (TextUtils.isEmpty(resumeListParamData.getIndividualResume())) {
                            resumeIndividual.setHint("暂无个人简介");
                        } else {
                            resumeIndividual.setText(resumeListParamData.getIndividualResume());
                        }
                        if (TextUtils.isEmpty(resumeListParamData.getExperience())) {
                            resumeExperience.setHint("暂无工作经历");
                        } else {
                            resumeExperience.setText(resumeListParamData.getExperience());
                        }
                    });
                } else {
                    showSyncToast(resumeListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        }, getStringToSP("id"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }
}