package com.example.activity.workactivity;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.adapter.workadapter.WorkProfessionListAdapter;
import com.example.pojo.workparam.WorkProfessionListParam;
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
public class WorkSelectResumeActivity extends BaseActivity {


    private ImageView finishBtn;
    private Toolbar workToolbar;
    private RecyclerView resumeRecycler;
    private EditText selectResumeName;
    private Map<String, Object> param = new HashMap<>();
    private String professionName = "";
    private Gson gson = new Gson();
    private WorkProfessionListAdapter professionListAdapter;

    @Override
    protected int initLayout() {
        return R.layout.activity_work_select_resume;
    }

    @Override
    protected void initView() {
        finishBtn = findViewById(R.id.finish_btn);
        workToolbar = findViewById(R.id.work_toolbar);
        resumeRecycler = findViewById(R.id.work_select_resume_recycler);
        selectResumeName = findViewById(R.id.work_select_resume_name);
        professionListAdapter = new WorkProfessionListAdapter();
    }

    @Override
    protected void initData() {
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });
        workToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        resumeRecycler.setLayoutManager(new LinearLayoutManager(this));
        resumeRecycler.setAdapter(professionListAdapter);
        resumeRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        professionListAdapter.setProfessionClickListener(rowsDTO -> {
            Intent intent = new Intent(this, WorkAddResumeActivity.class);
            intent.putExtra("ProfessionId", rowsDTO.getId());
            intent.putExtra("ProfessionName", rowsDTO.getProfessionName());
            setResult(RESULT_OK, intent);
            finish();
        });

        getProfessionList();

        selectResumeName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                professionName = selectResumeName.getText().toString();
                getProfessionList();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                return true;
            }
        });
    }

    public void getProfessionList() {
        param.clear();
        param.put("professionName", professionName);

        Api.config(Constant.NetWork.JOB_PROFESSION_LIST, param, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                WorkProfessionListParam workProfessionListParam = gson.fromJson(result, WorkProfessionListParam.class);
                if (workProfessionListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<WorkProfessionListParam.RowsDTO> professionListParamRows = workProfessionListParam.getRows();
                    runOnUiThread(() -> {
                        professionListAdapter.setData(professionListParamRows);
                    });
                } else {
                    showSyncToast(workProfessionListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
    }
}