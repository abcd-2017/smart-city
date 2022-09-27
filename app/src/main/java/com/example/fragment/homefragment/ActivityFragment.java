package com.example.fragment.homefragment;

import android.content.Intent;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.R;
import com.example.activity.activity.ActivityDetailsActivity;
import com.example.activity.activity.ActivityList;
import com.example.adapter.activityadapter.ActivityCategoryAdapter;
import com.example.adapter.activityadapter.ActivityListAdapter;
import com.example.fragment.BaseFragment;
import com.example.pojo.activityparam.ActivityCategoryParam;
import com.example.pojo.activityparam.ActivityListParam;
import com.example.pojo.activityparam.ActivityRotationParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kkk
 */
public class ActivityFragment extends BaseFragment {

    private RecyclerView categoryRecycler;
    private RecyclerView listRecycler;
    private Gson gson = new Gson();
    private List<ActivityRotationParam.RowsDTO> rotationParamRows;
    private Banner<ActivityRotationParam.RowsDTO, BannerImageAdapter<ActivityRotationParam.RowsDTO>> banner;
    private Map<String, Object> param = new HashMap<>();
    private ActivityCategoryAdapter categoryAdapter;
    private ActivityListAdapter activityListAdapter;

    public static ActivityFragment newInstance() {
        return new ActivityFragment();
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_activity;
    }

    @Override
    protected void initView() {
        banner = mContent.findViewById(R.id.activity_banner);
        categoryRecycler = mContent.findViewById(R.id.activity_category_recycler);
        listRecycler = mContent.findViewById(R.id.activity_list_recycler);
        categoryAdapter = new ActivityCategoryAdapter();
        activityListAdapter = new ActivityListAdapter();
    }

    @Override
    protected void initData() {
        categoryRecycler.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        categoryRecycler.setAdapter(categoryAdapter);

        listRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        listRecycler.setAdapter(activityListAdapter);
        listRecycler.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        Api.config(Constant.NetWork.ACTIVITY_BANNER, null, requireActivity()).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                ActivityRotationParam rotationParam = gson.fromJson(result, ActivityRotationParam.class);
                if (rotationParam.getCode() == HttpURLConnection.HTTP_OK) {
                    rotationParamRows = rotationParam.getRows();
                    requireActivity().runOnUiThread(() -> {
                        setBannerImage();
                    });
                } else {
                    showSyncToast(rotationParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });

        Api.config(Constant.NetWork.ACTIVITY_CATEGORY, null, requireActivity()).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                ActivityCategoryParam activityCategoryParam = gson.fromJson(result, ActivityCategoryParam.class);
                if (activityCategoryParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<ActivityCategoryParam.DataDTO> categoryParamData = activityCategoryParam.getData();
                    requireActivity().runOnUiThread(() -> {
                        categoryAdapter.setData(categoryParamData);
                    });
                } else {
                    showSyncToast(activityCategoryParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });

        getActivityList();

        categoryAdapter.setItemCLickListener(id -> {
            jumpPageToIntent(new Intent(requireContext(), ActivityList.class).putExtra("categoryId", id));
        });

        activityListAdapter.setItemClickListener(id -> {
            jumpPageToIntent(new Intent(requireContext(), ActivityDetailsActivity.class).putExtra("ActivityId", id));
        });
    }

    private void setBannerImage() {
        banner.setAdapter(new BannerImageAdapter<ActivityRotationParam.RowsDTO>(rotationParamRows) {
            @Override
            public void onBindView(BannerImageHolder holder, ActivityRotationParam.RowsDTO data, int position, int size) {
                Glide.with(holder.itemView.getContext())
                        .load(Constant.BASE_API + data.getAdvImg())
                        .transform(new RoundedCorners(24))
                        .into(holder.imageView);
            }
        }).addBannerLifecycleObserver(this)
                .setIndicator(new CircleIndicator(requireActivity()));
    }

    private void getActivityList() {
        param.clear();
        param.put("recommend", "Y");
        Api.config(Constant.NetWork.ACTIVITY_LIST, param, requireActivity()).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                ActivityListParam activityListParam = gson.fromJson(result, ActivityListParam.class);
                if (activityListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<ActivityListParam.RowsDTO> activityListParamRows = activityListParam.getRows();
                    requireActivity().runOnUiThread(() -> {
                        activityListAdapter.setData(activityListParamRows);
                    });
                } else {
                    showSyncToast(activityListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
    }
}