package com.example.activity.takeoutactivity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.adapter.takeoutadapter.TakeOutOrderListAdapter;
import com.example.pojo.BaseParam;
import com.example.pojo.takeoutparam.TakeOutOrderListParam;
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
public class TakeOutOrderActivity extends BaseActivity {

    private Toolbar takeOutToolbar;
    private ImageView finishBtn;
    private RecyclerView orderRecycler;
    private TakeOutOrderListAdapter orderListAdapter;
    private Integer pageNum = 1;
    private SmartRefreshLayout orderRefresh;
    private View payView, commentView;
    private CardView dialogOkBtn, dialogCancelBtn, commentOkBtn, commentCancelBtn;
    private RadioGroup payType;
    private RadioButton payTypeWechat, payTypeAli;
    private Integer selectPayType = -1;
    private String orderNum;
    private EditText alertTakeOutComment;
    private TextView alertTitle;
    private Gson gson = new Gson();
    private Map<String, Object> param = new HashMap<>();
    private List<TakeOutOrderListParam.RowsDTO> orderList = new ArrayList<>();
    private boolean hasNext = true, dialogStatus;

    @Override
    protected int initLayout() {
        return R.layout.activity_take_out_order;
    }

    @Override
    protected void initView() {
        takeOutToolbar = findViewById(R.id.take_out_toolbar);
        finishBtn = findViewById(R.id.finish_btn);
        orderRecycler = findViewById(R.id.take_out_order_recycler);
        orderRefresh = findViewById(R.id.take_out_order_refresh);
        orderListAdapter = new TakeOutOrderListAdapter();

        payView = LayoutInflater.from(this)
                .inflate(R.layout.alert_bus_pay_order, null, false);
        dialogOkBtn = payView.findViewById(R.id.alert_dialog_ok);
        dialogCancelBtn = payView.findViewById(R.id.alert_dialog_cancel);
        payType = payView.findViewById(R.id.bus_order_pay_type);
        payTypeAli = payView.findViewById(R.id.bus_pay_type_ali);
        payTypeWechat = payView.findViewById(R.id.bus_pay_type_wechat);

        commentView = LayoutInflater.from(this)
                .inflate(R.layout.alert_dialog_take_out_comment, null, false);
        alertTakeOutComment = commentView.findViewById(R.id.alert_take_out_comment);
        commentOkBtn = commentView.findViewById(R.id.alert_dialog_ok);
        commentCancelBtn = commentView.findViewById(R.id.alert_dialog_cancel);
        alertTitle = commentView.findViewById(R.id.alert_dialog_title);
    }

    @Override
    protected void initData() {
        takeOutToolbar.setNavigationOnClickListener(view -> {
            finish();
        });
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });

        orderRecycler.setLayoutManager(new LinearLayoutManager(this));
        orderRecycler.setAdapter(orderListAdapter);


        orderRefresh.setRefreshHeader(new BezierRadarHeader(this));
        orderRefresh.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Translate));

        orderRefresh.setOnRefreshListener(refreshLayout -> {
            refreshLayout.autoRefresh(2000);
            pageNum = 1;
            orderList.clear();
            hasNext = true;
            getOrderList(true);
        });
        orderRefresh.setOnLoadMoreListener(refreshLayout -> {
            pageNum++;
            refreshLayout.autoLoadMore(2000);
            getOrderList(false);
        });

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(payView)
                .create();
        alertDialog.getWindow().setBackgroundDrawable(AppCompatResources.getDrawable(this, R.drawable.alert_bg_translate));
        alertDialog.setCancelable(false);

        //支付弹窗按钮监听事件
        dialogCancelBtn.setOnClickListener(view -> {
            alertDialog.dismiss();
        });
        dialogOkBtn.setOnClickListener(view -> {
            if (selectPayType == -1) {
                showSyncToast("请选择支付方式");
                return;
            }
            param.clear();
            param.put("orderNo", orderNum);
            param.put("paymentType", Constant.PAY_TYPE[selectPayType]);

            Api.config(Constant.NetWork.TAKE_OUT_PAY, param, TakeOutOrderActivity.this).postRequest(new RequestCallback() {
                @Override
                public void success(String result) {
                    BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                    if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                        alertDialog.dismiss();
                        showSyncToast("支付成功");
                        pageNum = 1;
                        orderList.clear();
                        hasNext = true;
                        getOrderList(true);
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

        payType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == payTypeAli.getId()) {
                    selectPayType = 0;
                } else if (checkedId == payTypeWechat.getId()) {
                    selectPayType = 1;
                } else {
                    selectPayType = 2;
                }
            }
        });

//        //取消订单弹窗
//        AlertDialogUtils.getInstance();
//        AlertDialogUtils.showConfirmDialog(this, "提示", "确认要取消订单？", new AlertDialogUtils.OnDialogButtonClickListener() {
//            @Override
//            public void clickOk() {
//            }
//
//            @Override
//            public void clickCancel() {
//
//            }
//        });

        //评论弹窗
        AlertDialog commentDialog = new AlertDialog.Builder(this)
                .setView(commentView)
                .create();
        commentDialog.getWindow().setBackgroundDrawable(AppCompatResources.getDrawable(this, R.drawable.alert_bg_translate));
        commentDialog.setCancelable(false);
        commentCancelBtn.setOnClickListener(view -> {
            commentDialog.hide();
        });
        commentOkBtn.setOnClickListener(view -> {
            if (TextUtils.isEmpty(alertTakeOutComment.getText())) {
                if (dialogStatus) {
                    showSyncToast("请填写退款理由");
                } else {
                    showSyncToast("请填写评价");
                }
                return;
            }
            if (dialogStatus) {
                param.clear();
                param.put("reason", alertTakeOutComment.getText().toString());
                param.put("orderNo", orderNum);
                Api.config(Constant.NetWork.TAKE_OUT_ORDER_REFOUND, param, this).postRequest(new RequestCallback() {
                    @Override
                    public void success(String result) {
                        BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                        if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                            showSyncToast("取消订单成功");
                            runOnUiThread(() -> {
                                commentDialog.dismiss();
                                getOrderList(true);
                            });
                        } else {
                            showSyncToast(baseParam.getMsg());
                        }
                    }

                    @Override
                    public void failure(Exception e) {
                        showSyncToast("网络异常");
                    }
                });
            } else {
                param.clear();
                param.put("content", alertTakeOutComment.getText().toString());
                param.put("orderNo", orderNum);
                param.put("score", 5);
                Api.config(Constant.NetWork.TAKE_OUT_COMMENT, param, this).postRequest(new RequestCallback() {
                    @Override
                    public void success(String result) {
                        BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                        if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                            showSyncToast("支付成功");
                            runOnUiThread(() -> {
                                commentDialog.dismiss();
                                getOrderList(true);
                            });
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
        });

        getOrderList(true); //订单点击事件
        orderListAdapter.setItemClickListener(new TakeOutOrderListAdapter.ItemClickListener() {
            @Override
            public void clickOkBtn(String orderNo) {
                orderNum = orderNo;
                alertDialog.show();
            }

            @Override
            public void clickCancelBtn(String orderNo) {
                orderNum = orderNo;
                dialogStatus = true;
                alertTitle.setText("订单退款");
                alertTakeOutComment.setText("");
                alertTakeOutComment.setHint("请输入退款理由");
                commentDialog.show();
            }

            @Override
            public void clickAddComment(String orderNo) {
                orderNum = orderNo;
                dialogStatus = false;
                alertTitle.setText("评论");
                alertTakeOutComment.setText("");
                alertTakeOutComment.setHint("请输入评论");
                commentDialog.show();
            }

            @Override
            public void clickDetail(String orderNo) {
                jumpPageToIntent(new Intent(TakeOutOrderActivity.this, TakeOutOrderDetailActivity.class)
                        .putExtra("orderNo", orderNo));
            }
        });
    }

    //获取订单详情
    private void getOrderList(boolean isRefresh) {
        param.clear();
        param.put("pageNum", pageNum);
        param.put("pageSize", Constant.pageSize_6);

        Api.config(Constant.NetWork.TAKE_OUT_ORDER_LIST, param, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                TakeOutOrderListParam outOrderListParam = gson.fromJson(result, TakeOutOrderListParam.class);
                if (outOrderListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<TakeOutOrderListParam.RowsDTO> orderListParamRows = outOrderListParam.getRows();
                    System.out.println(orderListParamRows.size() + " " + orderList.size());
                    if (orderListParamRows.size() > 0 && hasNext) {
                        if (isRefresh) {
                            orderRefresh.finishRefresh(1200);
                        } else {
                            orderRefresh.finishLoadMore(1200);
                        }
                        if (orderListParamRows.size() < Constant.pageSize_6) {
                            hasNext = false;
                        }
                        runOnUiThread(() -> {
                            new Thread(() -> {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            });
                            orderList.addAll(orderListParamRows);
                            orderListAdapter.setData(orderList);
                        });
                    } else {
                        if (isRefresh) {
                            orderRefresh.finishRefresh(true);
                        } else {
                            showSyncToast("没有更多数据了");
                            orderRefresh.finishLoadMore(true);
                        }
                    }
                } else {
                    showSyncToast(outOrderListParam.getMsg());
                    if (isRefresh) {
                        orderRefresh.finishRefresh(true);
                    } else {
                        orderRefresh.finishLoadMore(false);
                    }
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
                if (isRefresh) {
                    orderRefresh.finishRefresh(false);
                } else {
                    orderRefresh.finishLoadMore(false);
                }
            }
        });
    }
}