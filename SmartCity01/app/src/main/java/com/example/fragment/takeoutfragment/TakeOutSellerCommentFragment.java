package com.example.fragment.takeoutfragment;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.activity.takeoutactivity.TakeOutSellerDetailActivity;
import com.example.adapter.takeoutadapter.TakeOutCommentListAdapter;
import com.example.fragment.BaseFragment;
import com.example.pojo.takeoutparam.TakeOutSellerCommentListParam;
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
public class TakeOutSellerCommentFragment extends BaseFragment {


    private RecyclerView commentRecycler;
    private TakeOutCommentListAdapter commentListAdapter;
    private Map<String, Object> param = new HashMap<>();
    private Gson gson = new Gson();
    private Integer pageNum = 1;
    private SmartRefreshLayout commentRefresh;
    private List<TakeOutSellerCommentListParam.RowsDTO> commentList = new ArrayList<>();
    private boolean hasLine = true;

    public static TakeOutSellerCommentFragment newInstance() {
        return new TakeOutSellerCommentFragment();
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_take_out_seller_comment;
    }

    @Override
    protected void initView() {
        commentRecycler = mContent.findViewById(R.id.take_out_seller_comment_recycler);
        commentRefresh = mContent.findViewById(R.id.take_out_seller_comment_refresh);
        commentListAdapter = new TakeOutCommentListAdapter();
    }

    @Override
    protected void initData() {
        TakeOutSellerDetailActivity activity = (TakeOutSellerDetailActivity) getActivity();
        int sellerId = activity.getSellerId();

        param.put("sellerId", sellerId);
        param.put("pageSize", Constant.pageSize);

        commentRefresh.setEnableRefresh(false);
        commentRefresh.setRefreshFooter(new BallPulseFooter(mContent.getContext()).setSpinnerStyle(SpinnerStyle.Translate));

        commentRefresh.setOnLoadMoreListener(refreshLayout -> {
            pageNum++;
            refreshLayout.autoLoadMore(2000);
            getCommentList();
        });

        commentRecycler.setLayoutManager(new LinearLayoutManager(mContent.getContext()));
        commentRecycler.setAdapter(commentListAdapter);
        commentRecycler.addItemDecoration(new DividerItemDecoration(mContent.getContext(), DividerItemDecoration.VERTICAL));

        getCommentList();
    }

    private void getCommentList() {
        param.put("pageNum", pageNum);
        Api.config(Constant.NetWork.TAKE_OUT_COMMENT_LIST, param, mContent.getContext()).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                TakeOutSellerCommentListParam commentListParam = gson.fromJson(result, TakeOutSellerCommentListParam.class);
                if (commentListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<TakeOutSellerCommentListParam.RowsDTO> listParamRows = commentListParam.getRows();
                    System.out.println(listParamRows.size());
                    requireActivity().runOnUiThread(() -> {
                        if (listParamRows.size() > 0 || hasLine) {
                            if (commentList.size() != 0) {
                                commentRefresh.autoLoadMore(1000);
                                new Thread(() -> {
                                    try {
                                        Thread.sleep(1200);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                });
                            }
                            hasLine = listParamRows.size() == Constant.pageSize;
                            commentRefresh.finishLoadMore(true);
                            commentList.addAll(listParamRows);
                            commentListAdapter.setData(commentList);
                        } else {
                            showSyncToast("没有更多了！");
                            commentRefresh.finishLoadMore(true);
                        }
                    });
                } else {
                    showSyncToast(commentListParam.getMsg());
                    commentRefresh.finishLoadMore(false);
                }
            }

            @Override
            public void failure(Exception e) {
                commentRefresh.finishLoadMore(false);
                showSyncToast("网络异常");
            }
        });

    }
}