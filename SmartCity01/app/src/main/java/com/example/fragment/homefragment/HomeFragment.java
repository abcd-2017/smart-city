package com.example.fragment.homefragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.activity.HomeActivity;
import com.example.activity.busactivity.BusActivity;
import com.example.activity.expenseactivity.ExpensesActivity;
import com.example.activity.hospitalactivity.HospitalActivity;
import com.example.activity.houseactivity.HouseActivity;
import com.example.activity.metroactivity.MetroActivity;
import com.example.activity.movieactivity.MovieActivity;
import com.example.activity.parkingactivity.ParkingActivity;
import com.example.activity.takeoutactivity.TakeOutActivity;
import com.example.activity.trafficactivity.TrafficActivity;
import com.example.activity.workactivity.WorkActivity;
import com.example.adapter.PressListAdapter;
import com.example.adapter.RotationAdapter;
import com.example.adapter.ServiceListAdapter;
import com.example.dbhelper.ServiceListDBHelper;
import com.example.fragment.BaseFragment;
import com.example.pojo.NewsCategoryParam;
import com.example.pojo.PressListParam;
import com.example.pojo.RotationImageParam;
import com.example.pojo.ServiceListParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;
import com.to.aboomy.pager2banner.Banner;
import com.to.aboomy.pager2banner.IndicatorView;

import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kkk
 */
public class HomeFragment extends BaseFragment {

    private static final String TAG = "HomeFragment";
    private Banner banner;
    private RotationAdapter rotationAdapter;
    private Runnable thread;
    private ServiceListAdapter serviceListAdapter;
    private RecyclerView serviceRecyclerView;
    private LinearLayout newsLayout;
    private TableLayout tableLayout;
    private List<RotationImageParam.RowsDTO> rows;
    private List<NewsCategoryParam.DateDTO> newsCategory;
    private List<PressListParam.RowsDTO> pressListParamRows;
    private RecyclerView newsRecycler;
    private PressListAdapter pressListAdapter;
    private JumpPageListener jumpPageListener;
    private EditText searchNews;
    private Map<String, Class> jumpService = new HashMap<>();
    private ServiceListDBHelper serviceListDBHelper;

    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        banner = mContent.findViewById(R.id.rotation_banner);
        serviceRecyclerView = mContent.findViewById(R.id.service_list);
        newsLayout = mContent.findViewById(R.id.news_linear_layout);
        newsRecycler = mContent.findViewById(R.id.news_recycler);
        searchNews = mContent.findViewById(R.id.search_news_input);
        rotationAdapter = new RotationAdapter();
        serviceListAdapter = new ServiceListAdapter();
        pressListAdapter = new PressListAdapter();
        serviceListDBHelper = new ServiceListDBHelper(requireContext());
    }

    @Override
    protected void initData() {
        //全部服务
        serviceRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 5));
        serviceRecyclerView.setAdapter(serviceListAdapter);

        Map<String, Object> param = new HashMap<>();
        param.put("type", 2);
        Api.config(Constant.NetWork.ROTATION_IMAGE, param, getActivity()).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                RotationImageParam rotationImageParam = gson.fromJson(result, RotationImageParam.class);
                rows = rotationImageParam.getRows();
                if (rotationImageParam.getCode() == HttpURLConnection.HTTP_OK) {
                    getActivity().runOnUiThread(() -> {
                        rotationAdapter.setData(rows);
                    });
                } else {
                    showSyncToast(rotationImageParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });

        //轮播图小圆点
        IndicatorView indicatorView = new IndicatorView(getActivity())
                .setIndicatorSelectorColor(Color.rgb(124, 202, 247))
                .setIndicatorColor(Color.WHITE)
                .setIndicatorStyle(IndicatorView.IndicatorStyle.INDICATOR_DASH);

        banner.setIndicator(indicatorView).setAdapter(rotationAdapter);

        if (serviceListDBHelper.queryCount() > 0) {
            List<ServiceListParam.RowsDTO> rowsDTOList = serviceListDBHelper.query();
            getActivity().runOnUiThread(() -> {
                serviceListAdapter.setData(rowsDTOList);
            });
        } else {
            Api.config(Constant.NetWork.SERVICE_LIST, null, getActivity()).getRequest(new RequestCallback() {
                @Override
                public void success(String result) {
                    Gson gson = new Gson();
                    ServiceListParam serviceListParam = gson.fromJson(result, ServiceListParam.class);
                    if (serviceListParam.getCode() == HttpURLConnection.HTTP_OK) {
                        List<ServiceListParam.RowsDTO> listParamRows = serviceListParam.getRows();
                        List<ServiceListParam.RowsDTO> subList = listParamRows.subList(0, 9);
                        Collections.sort(subList, (a, b) -> a.getSort() - b.getSort());
                        subList.add(new ServiceListParam.RowsDTO(-1, "全部服务", null, null, listParamRows.get(listParamRows.size() - 1).getImgUrl(), "AllService", null, null));
                        serviceListDBHelper.insert(subList);
                        getActivity().runOnUiThread(() -> {
                            serviceListAdapter.setData(subList);
                        });
                    } else {
                        showSyncToast("");
                    }
                }

                @Override
                public void failure(Exception e) {
                    showSyncToast("网络异常");
                }
            });
        }

        Api.config(Constant.NetWork.NEWS_CATEGORY_LIST, null, getActivity()).getRequest(new RequestCallback() {

            @Override
            public void success(String result) {
                Gson gson = new Gson();
                NewsCategoryParam newsCategoryParam = gson.fromJson(result, NewsCategoryParam.class);
                if (newsCategoryParam.getCode() == HttpURLConnection.HTTP_OK) {
                    newsCategory = newsCategoryParam.getData();
                    getNews(newsCategory.get(0).getId());
                } else {
                    showSyncToast(newsCategoryParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
        newsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        //跳转到新闻界面
        newsLayout.setOnClickListener(view -> {
            jumpPageListener.jumpNews();
        });

        searchNews.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(requireActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
                String searchContent = searchNews.getText().toString();
                if (!TextUtils.isEmpty(searchContent)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("searchContent", searchContent);
                    getParentFragmentManager().setFragmentResult("newsBundle", bundle);
                    searchNews.setText(null);
                    jumpPageListener.jumpNews();
                } else {
                    showSyncToast("搜索的内容为空");
                }
                return false;
            }
        });

        serviceListAdapter.setItemClickListener(id -> {
            if (id == -1) {
                jumpPageListener.jumpServices();
            } else if (id == 2) {
                jumpPage(MetroActivity.class);
            } else if (id == 5) {
                jumpPage(HospitalActivity.class);
            } else if (id == 17) {
                jumpPage(ParkingActivity.class);
            } else if (id == 3) {
                jumpPage(BusActivity.class);
            } else if (id == 7) {
                jumpPage(ExpensesActivity.class);
            } else if (id == 20) {
                jumpPage(HouseActivity.class);
            } else if (id == 21) {
                jumpPage(WorkActivity.class);
            } else if (id == 9) {
                jumpPage(TrafficActivity.class);
            } else if (id == 18) {
                jumpPage(MovieActivity.class);
            } else if (id == 19) {
                jumpPage(TakeOutActivity.class);
            }
            Log.d(TAG, "initData: " + id);
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context != null) {
            jumpPageListener = (HomeActivity) context;
        }
    }

    private void getNews(int id) {
        Map<String, Object> param = new HashMap<>();
        param.put("type", id);
        Api.config(Constant.NetWork.GET_PRESS_LIST, param, requireActivity()).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                PressListParam pressListParam = gson.fromJson(result, PressListParam.class);
                if (pressListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    pressListParamRows = pressListParam.getRows();
                    requireActivity().runOnUiThread(() -> {
                        pressListAdapter.setData(pressListParamRows);
                        newsRecycler.setAdapter(pressListAdapter);
                    });
                } else {
                    showSyncToast(pressListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
        pressListAdapter.setItemClickListener(currId -> {
            jumpPageListener.jumpNews();
        });
    }

    public interface JumpPageListener {
        void jumpNews();

        void jumpServices();
    }
}