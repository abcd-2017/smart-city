package com.example.fragment.homefragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.activity.PressDetailsActivity;
import com.example.adapter.CategoryAdapter;
import com.example.adapter.PressListAdapter;
import com.example.fragment.BaseFragment;
import com.example.pojo.NewsCategoryParam;
import com.example.pojo.PressListParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.header.BezierRadarHeader;
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
public class NewsFragment extends BaseFragment {

    private static final String TAG = "NewsFragment";
    private RecyclerView newsRecycler, categoryRecycler;
    private String searchContent;
    private EditText searchNews;
    private PressListAdapter listAdapter;
    private CategoryAdapter categoryAdapter;
    private int id = 9, pageNum = 1;
    private SmartRefreshLayout refreshLayout;
    private TextView emptyToast;
    private List<PressListParam.RowsDTO> pressList = new ArrayList<>();

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initView() {
        newsRecycler = mContent.findViewById(R.id.news_recycler_list);
        categoryRecycler = mContent.findViewById(R.id.category_recycler);
        searchNews = mContent.findViewById(R.id.search_news);
        refreshLayout = mContent.findViewById(R.id.news_smart_refresh_layout);
        emptyToast = mContent.findViewById(R.id.empty_toast);
        listAdapter = new PressListAdapter();
        categoryAdapter = new CategoryAdapter();
    }

    @Override
    protected void initData() {
        newsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        newsRecycler.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        newsRecycler.setAdapter(listAdapter);

        getParentFragmentManager().setFragmentResultListener("newsBundle", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                searchContent = (String) result.get("searchContent");
                searchNews.setText(searchContent);
                queryNews(true);
            }
        });

        Api.config(Constant.NetWork.NEWS_CATEGORY_LIST, null, requireActivity()).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                NewsCategoryParam categoryParam = gson.fromJson(result, NewsCategoryParam.class);
                if (categoryParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<NewsCategoryParam.DateDTO> categoryParamData = categoryParam.getData();
                    id = categoryParamData.get(0).getId();
                    requireActivity().runOnUiThread(() -> {
                        categoryAdapter.setData(categoryParamData);
                    });
                } else {
                    showSyncToast(categoryParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
        //分类信息
        categoryRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        categoryRecycler.setAdapter(categoryAdapter);

        categoryAdapter.setItemClickListener(new CategoryAdapter.ItemClickListener() {
            @Override
            public void click(int id, int position) {
                NewsFragment.this.id = id;
                searchContent = null;
                queryNews(true);
                requireActivity().runOnUiThread(() -> {
                    categoryAdapter.setCurrId(position);
                });
            }
        });

        //设置刷新组件
        refreshLayout.setRefreshHeader(new BezierRadarHeader(requireActivity()).setEnableHorizontalDrag(true));
        refreshLayout.setRefreshFooter(new BallPulseFooter(requireActivity()).setSpinnerStyle(SpinnerStyle.Translate));

        refreshLayout.setOnRefreshListener(refreshLayout1 -> {
            pageNum = 1;
            queryNews(true);
            refreshLayout1.finishRefresh(2000);
        });

        refreshLayout.setOnLoadMoreListener(refreshLayout1 -> {
            pageNum++;
            refreshLayout1.finishLoadMore(20000);
            queryNews(false);
        });

        //新闻列表
        queryNews(true);

        searchNews.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchContent = searchNews.getText().toString();
                    InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(requireActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    queryNews(true);
                }
                return true;
            }
        });
    }


    private void queryNews(boolean isRefresh) {
        if (isRefresh) {
            refreshLayout.autoRefresh();
        }

        Map<String, Object> param = new HashMap<>();
        if (!TextUtils.isEmpty(searchContent)) {
            param.put("title", searchContent);
        } else {
            param.put("type", id);
        }
        param.put("pageNum", pageNum);
        param.put("pageSize", Constant.pageSize);
        Api.config(Constant.NetWork.GET_PRESS_LIST, param, requireActivity()).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                PressListParam pressListParam = gson.fromJson(result, PressListParam.class);
                if (pressListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<PressListParam.RowsDTO> pressListParamRows = pressListParam.getRows();
                    if (isRefresh) {
                        refreshLayout.finishRefresh(2000);
                    } else {
                        refreshLayout.finishLoadMore(2000);
                    }
                    if (pressListParamRows.size() > 0) {
                        emptyToast.setVisibility(View.GONE);
                        if (isRefresh) {
                            pressList.clear();
                        } else {
                            try {
                                Thread.sleep(1500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            refreshLayout.finishLoadMore();
                        }
                        pressList.addAll(pressListParamRows);
                        requireActivity().runOnUiThread(() -> {
                            listAdapter.setData(pressListParamRows);
                        });
                    } else {
                        if (pageNum == 1 && pressList.size() == 0) {
                            emptyToast.setVisibility(View.VISIBLE);
                        }
                        if (isRefresh) {
                            showSyncToast("刷新失败");
                        } else {
                            showSyncToast("没有更多数据啦!");
                        }
                    }
                } else {
                    showSyncToast(pressListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
                if (isRefresh) {
                    refreshLayout.finishRefresh(true);
                } else {
                    refreshLayout.finishRefresh(true);
                }
            }
        });
        listAdapter.setItemClickListener(id -> {
            jumpPageToIntent(new Intent(requireActivity(), PressDetailsActivity.class).putExtra("id", id));
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        searchNews.setText(null);
    }
}