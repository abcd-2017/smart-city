package com.example.activity.workactivity;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.activity.useractivity.LoginActivity;
import com.example.pojo.BaseParam;
import com.example.pojo.workparam.WorkDetailParam;
import com.example.pojo.workparam.WorkResumeDetailParam;
import com.example.util.AlertDialogUtils;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kkk
 */
public class WorkDetailActivity extends BaseActivity {

    private ImageView finishBtn;
    private Toolbar workToolbar;
    private TextView detailName, detailSalary, detailAddress, detailContacts, detailCompanyName, detailObligation, detailNeed;
    private Gson gson = new Gson();
    private Integer hasResume = -1;
    private CardView detailDeliverBtn;
    private Map<String, Object> param = new HashMap<>();
    private String professionName;
    private Integer professionId;

    @Override
    protected int initLayout() {
        return R.layout.activity_work_detail;
    }

    @Override
    protected void initView() {
        finishBtn = findViewById(R.id.finish_btn);
        workToolbar = findViewById(R.id.work_toolbar);
        detailName = findViewById(R.id.work_detail_name);
        detailSalary = findViewById(R.id.work_detail_salary);
        detailAddress = findViewById(R.id.work_detail_address);
        detailContacts = findViewById(R.id.work_detail_contacts);
        detailCompanyName = findViewById(R.id.work_detail_company_name);
        detailObligation = findViewById(R.id.work_detail_obligation);
        detailNeed = findViewById(R.id.work_detail_need);
        detailDeliverBtn = findViewById(R.id.work_add_resume_btn);
    }

    @Override
    protected void initData() {
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });
        workToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        hasResume();
        Integer workId = (Integer) getIntent().getExtras().get("workId");
        Api.config(Constant.NetWork.JOB_POST_DETAIL, null, this).getRestfulRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                WorkDetailParam workDetailParam = gson.fromJson(result, WorkDetailParam.class);
                if (workDetailParam.getCode() == HttpURLConnection.HTTP_OK) {
                    WorkDetailParam.DataDTO detailParamData = workDetailParam.getData();
                    runOnUiThread(() -> {
                        setViewValue(detailParamData);
                    });
                    professionId = detailParamData.getProfessionId();
                    professionName = detailParamData.getName();
                } else {
                    showSyncToast(workDetailParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        }, workId);

        detailDeliverBtn.setOnClickListener(view -> {
            if (hasResume == -1) {
                hasResume();
                return;
            }
            AlertDialogUtils.getInstance();
            AlertDialogUtils.showConfirmDialog(this, "提示", "确认投递简历？", new AlertDialogUtils.OnDialogButtonClickListener() {
                @Override
                public void clickOk() {
                    if (hasResume == 1) {
                        showSyncToast("暂无简历，请先选择简历");
                        jumpPageToIntent(new Intent(WorkDetailActivity.this, WorkAddResumeActivity.class)
                                .putExtra("resumeType", "add"));
                    } else {
                        param.clear();
                        param.put("companyId", workId);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        param.put("satrTime", simpleDateFormat.format(new Date()));
                        param.put("postId", professionId);
                        param.put("postName", professionName);

                        Api.config(Constant.NetWork.JOB_DELIVER, param, WorkDetailActivity.this).postRequest(new RequestCallback() {
                            @Override
                            public void success(String result) {
                                BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                                if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                                    showSyncToast("投递成功");

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
                    }
                }

                @Override
                public void clickCancel() {

                }
            });
        });
    }

    private void hasResume() {
        String id = getStringToSP("id");
        if (id == null) {
            jumpPage(LoginActivity.class);
        }
        Api.config(Constant.NetWork.QUERY_RESUME_DETAIL, null, this).getRestfulRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                WorkResumeDetailParam workResumeDetail = gson.fromJson(result, WorkResumeDetailParam.class);
                if (workResumeDetail.getCode() == HttpURLConnection.HTTP_OK) {
                    WorkResumeDetailParam.DataDTO workResumeDetailData = workResumeDetail.getData();
                    if (workResumeDetailData == null) {
                        hasResume = 1;
                    } else {
                        hasResume = 2;
                    }
                } else {
                    showSyncToast(workResumeDetail.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        }, id);
    }

    private void setViewValue(WorkDetailParam.DataDTO detailParamData) {
        detailName.setText(detailParamData.getName());
        detailAddress.setText(detailParamData.getAddress());
        detailNeed.setText(detailParamData.getNeed());
        detailObligation.setText(detailParamData.getObligation());
        detailSalary.setText(detailParamData.getSalary());
        detailCompanyName.setText(detailParamData.getCompanyName());
        detailContacts.setText(detailParamData.getContacts());
    }
}