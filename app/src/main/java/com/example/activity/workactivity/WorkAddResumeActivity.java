package com.example.activity.workactivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.pojo.BaseParam;
import com.example.pojo.workparam.WorkResumeListParam;
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
public class WorkAddResumeActivity extends BaseActivity {

    private ImageView finishBtn;
    private Toolbar workToolbar;
    private EditText resumeMoney, resumeIndividual, resumeMostEducation, resumeExperience, resumeAddress;
    private CardView addResumeBtn;
    private TextView resumePosition, addResumeText;
    private Integer professionId = -1;
    private Gson gson = new Gson();
    private WorkResumeListParam.DataDTO resumeListParamData = null;

    @Override
    protected int initLayout() {
        return R.layout.activity_work_add_resume;
    }

    @Override
    protected void initView() {
        finishBtn = findViewById(R.id.finish_btn);
        workToolbar = findViewById(R.id.work_toolbar);
        resumeMoney = findViewById(R.id.work_add_resume_money);
        resumeIndividual = findViewById(R.id.work_add_resume_individual_resume);
        resumeMostEducation = findViewById(R.id.work_add_resume_most_education);
        resumePosition = findViewById(R.id.work_add_resume_position);
        resumeExperience = findViewById(R.id.work_add_resume_experience);
        resumeAddress = findViewById(R.id.work_add_resume_address);
        addResumeBtn = findViewById(R.id.work_add_resume_btn);
        addResumeText = findViewById(R.id.work_add_resume_text);
    }

    @Override
    protected void initData() {
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });
        workToolbar.setNavigationOnClickListener(view -> {
            finish();
        });
        Bundle jumBundle = getIntent().getExtras();
        String resumeType = (String) jumBundle.get("resumeType");
        if ("add".equals(resumeType)) {
            addResumeText.setText("创建简历");
            addResumeBtn.setCardBackgroundColor(Color.rgb(124, 171, 177));

            addResumeBtn.setOnClickListener(view -> {
                Api.config(Constant.NetWork.ADD_RESUME, addParam(), this).postRequest(new RequestCallback() {
                    @Override
                    public void success(String result) {
                        BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                        if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                            showSyncToast("创建成功");
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
        } else {
            String profession = (String) jumBundle.get("profession");

            Api.config(Constant.NetWork.QUERY_RESUME_DETAIL, null, this).getRestfulRequest(new RequestCallback() {
                @Override
                public void success(String result) {
                    WorkResumeListParam workResumeListParam = gson.fromJson(result, WorkResumeListParam.class);
                    if (workResumeListParam.getCode() == HttpURLConnection.HTTP_OK) {
                        resumeListParamData = workResumeListParam.getData();
                        System.out.println(resumeListParamData);
                        runOnUiThread(() -> {
                            resumeMoney.setText(resumeListParamData.getMoney());
                            resumeIndividual.setText(resumeListParamData.getIndividualResume());
                            resumeMostEducation.setText(resumeListParamData.getMostEducation());
                            resumeExperience.setText(resumeListParamData.getExperience());
                            resumeAddress.setText(resumeListParamData.getAddress());
                            resumePosition.setText(profession);
                        });
                    } else {
                        showSyncToast(workResumeListParam.getMsg());
                    }
                }

                @Override
                public void failure(Exception e) {
                    showSyncToast("网络异常");
                }
            }, getStringToSP("id"));

            addResumeText.setText("保存");
            addResumeBtn.setCardBackgroundColor(Color.rgb(65, 179, 73));

            addResumeBtn.setOnClickListener(view -> {
                Api.config(Constant.NetWork.ADD_RESUME, addParam(), this).putRequest(new RequestCallback() {
                    @Override
                    public void success(String result) {
                        BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                        if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                            showSyncToast("保存成功");
                            finish();
                        } else {
                            System.out.println(baseParam.getMsg());
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

        //初始化 下一个页面传参回调函数
        ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            Bundle extras = result.getData().getExtras();
            professionId = (Integer) extras.get("ProfessionId");
            String professionName = (String) extras.get("ProfessionName");
            resumePosition.setText(professionName);
        });

        resumePosition.setOnClickListener(view -> {
            resultLauncher.launch(new Intent(this, WorkSelectResumeActivity.class));
        });
    }

    private Map<String, Object> addParam() {
        Map<String, Object> param = new HashMap<>();
        param.put("address", resumeAddress.getText().toString());
        param.put("experience", resumeExperience.getText().toString());
        param.put("individualResume", resumeIndividual.getText().toString());
        param.put("money", resumeMoney.getText().toString());
        param.put("mostEducation", resumeMostEducation.getText().toString());
        if (professionId != -1) param.put("positionId", professionId);
        if (resumeListParamData != null) {
            param.put("id", resumeListParamData.getId());
        }

        return param;
    }
}