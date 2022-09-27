package com.example.fragment.homefragment;

import android.util.Log;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
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
import com.example.adapter.ServiceListAdapter;
import com.example.fragment.BaseFragment;
import com.example.pojo.ServiceListParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author kkk
 */
public class AllServiceFragment extends BaseFragment {

    private static final String TAG = "AllServiceFragment";
    private RecyclerView allServiceRecycler;
    private ServiceListAdapter serviceListAdapter;

    public static AllServiceFragment newInstance() {
        return new AllServiceFragment();
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_all_service;
    }

    @Override
    protected void initView() {
        allServiceRecycler = mContent.findViewById(R.id.all_service_recycler);
        serviceListAdapter = new ServiceListAdapter();
    }

    @Override
    protected void initData() {
        allServiceRecycler.setLayoutManager(new GridLayoutManager(requireContext(), 4));
        allServiceRecycler.setAdapter(serviceListAdapter);

        Api.config(Constant.NetWork.SERVICE_LIST, null, requireActivity()).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                ServiceListParam serviceListParam = gson.fromJson(result, ServiceListParam.class);
                if (serviceListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<ServiceListParam.RowsDTO> listParamRows = serviceListParam.getRows();
                    List<ServiceListParam.RowsDTO> serviceArr = listParamRows.stream().filter(item -> item.getSort() < 12).collect(Collectors.toList());
                    requireActivity().runOnUiThread(() -> {
                        serviceListAdapter.setData(serviceArr);
                    });
                } else {
                    showSyncToast(serviceListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });

        serviceListAdapter.setItemClickListener(id -> {
            if (id == 2) {
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
}