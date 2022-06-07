package com.example.activity.workactivity;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.adapter.workadapter.WorkListAdapter;
import com.example.pojo.workparam.WorkListParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.AttachListPopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kkk
 */
public class WorkActivity extends BaseActivity {

    private ImageView finishBtn;
    private RecyclerView workListRecycler;
    private EditText searchProfession;
    private WorkListAdapter workListAdapter;
    private Gson gson = new Gson();
    private Map<String, Object> param = new HashMap<>();
    private String professionName = "";
    private TextView empty;
    private ImageView workMore;

    @Override
    protected int initLayout() {
        return R.layout.activity_work;
    }

    @Override
    protected void initView() {
        finishBtn = findViewById(R.id.finish_btn);
        workListRecycler = findViewById(R.id.work_list_recycler);
        searchProfession = findViewById(R.id.work_search_profession);
        empty = findViewById(R.id.work_list_empty);
        workMore = findViewById(R.id.work_more);
        workListAdapter = new WorkListAdapter();
    }

    @Override
    protected void initData() {
        finishBtn.setOnClickListener(view -> {
            finish();
        });

        workListRecycler.setLayoutManager(new LinearLayoutManager(this));
        workListRecycler.setAdapter(workListAdapter);

        searchProfession.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                professionName = searchProfession.getText().toString();
                getWorkList();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                return false;
            }
        });

        workListAdapter.setWorkClickListener(id -> {
            jumpPageToIntent(new Intent(this, WorkDetailActivity.class).putExtra("workId", id));
        });

        getWorkList();

        AttachListPopupView listPopupView = new XPopup.Builder(this)
                .hasShadowBg(false)
                .atView(workMore)
                .asAttachList(new String[]{"我的简历", "投递历史"}, new int[0], new OnSelectListener() {
                    @Override
                    public void onSelect(int position, String text) {
                        if (position == 0) {
                            jumpPage(WorkResumeActivity.class);
                        } else {
                            jumpPage(WorkDeliverActivity.class);
                        }
                    }
                });

        workMore.setOnClickListener(view -> {
            listPopupView.show();
        });
    }

    private void getWorkList() {
        param.clear();
        param.put("name", professionName);

        Api.config(Constant.NetWork.JOB_POST_LIST, param, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                WorkListParam workListParam = gson.fromJson(result, WorkListParam.class);
                if (workListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<WorkListParam.RowsDTO> workListParamRows = workListParam.getRows();
                    runOnUiThread(() -> {
                        workListAdapter.setData(workListParamRows);
                        if (workListParamRows.size() > 0) {
                            empty.setVisibility(View.GONE);
                            workListRecycler.setVisibility(View.VISIBLE);
                        } else {
                            empty.setVisibility(View.VISIBLE);
                            workListRecycler.setVisibility(View.GONE);
                        }
                    });
                } else {
                    showSyncToast(workListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
    }
}