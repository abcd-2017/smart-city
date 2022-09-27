package com.example.activity.movieactivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.adapter.moiveadapter.MovieSeatListAdapter;
import com.example.pojo.BaseParam;
import com.example.pojo.movieparam.MovieOrderListParam;
import com.example.pojo.movieparam.MovieTheatreRoomListParam;
import com.example.pojo.movieparam.MovieTheatreSeatListParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.example.view.movieview.SeatTable;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author kkk
 */
public class MovieTheatreSeatActivity extends BaseActivity {

    private SeatTable theatreSeatTable;
    private ImageView finishBtn;
    private TextView titleText, btnText, filmName, filmTime;
    private CardView seatBtn;
    private Toolbar movieToolbar;
    private Gson gson = new Gson();
    private MovieTheatreSeatListParam.RowsDTO[][] flag;
    private RecyclerView seatListRecycler;
    private MovieSeatListAdapter movieSeatListAdapter;
    private List<int[]> seatList = new ArrayList<>();
    private Integer filmPrice;
    private View payView;
    private CardView dialogOkBtn;
    private CardView dialogCancelBtn;
    private RadioGroup payType;
    private RadioButton payTypeAli, payTypeWechat;
    private Integer orderPayType = -1;
    private String orderNo;

    @Override
    protected int initLayout() {
        return R.layout.activity_movie_theatre_seat;
    }

    @Override
    protected void initView() {
        finishBtn = findViewById(R.id.finish_btn);
        titleText = findViewById(R.id.movie_theatre_seat_title_text);
        movieToolbar = findViewById(R.id.movie_toolbar);
        btnText = findViewById(R.id.movie_theatre_seat_btn_text);
        filmName = findViewById(R.id.movie_theatre_seat_film_name);
        filmTime = findViewById(R.id.movie_theatre_seat_film_time);
        seatBtn = findViewById(R.id.movie_theatre_seat_btn);
        theatreSeatTable = findViewById(R.id.movie_theatre_seat_table);
        seatListRecycler = findViewById(R.id.movie_theatre_seat_list_recycler);
        movieSeatListAdapter = new MovieSeatListAdapter();

        payView = LayoutInflater.from(this)
                .inflate(R.layout.alert_bus_pay_order, null, false);
        dialogOkBtn = payView.findViewById(R.id.alert_dialog_ok);
        dialogCancelBtn = payView.findViewById(R.id.alert_dialog_cancel);
        payType = payView.findViewById(R.id.bus_order_pay_type);
        payTypeAli = payView.findViewById(R.id.bus_pay_type_ali);
        payTypeWechat = payView.findViewById(R.id.bus_pay_type_wechat);
    }

    @Override
    protected void initData() {
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });
        movieToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        seatListRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        seatListRecycler.setAdapter(movieSeatListAdapter);

        Integer roomId = Integer.valueOf(getStringToSP("movie_room_id"));
        Integer theatreId = Integer.valueOf(getStringToSP("theatre_id"));
        Integer movieId = Integer.valueOf(getStringToSP("movie_id"));
        Integer timesId = Integer.valueOf(getStringToSP("times_id"));

        Map<String, Object> param = new HashMap<>();
        param.put("movieId", movieId);
        param.put("theatreId", theatreId);
        param.put("roomId", roomId);

        //获取影片场次信息
        Api.config(Constant.NetWork.MOVIE_THEATRE_TIMES_LIST, param, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                MovieTheatreRoomListParam theatreRoomListParam = gson.fromJson(result, MovieTheatreRoomListParam.class);
                if (theatreRoomListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    MovieTheatreRoomListParam.RowsDTO roomListParamRow = theatreRoomListParam.getRows().get(0);
                    runOnUiThread(() -> {
                        filmName.setText(roomListParamRow.getMovieName());
                        titleText.setText(roomListParamRow.getMovieName());
                        filmTime.setText(String.format("%s %s - %s", roomListParamRow.getPlayDate(), roomListParamRow.getStartTime(), roomListParamRow.getEndTime()));
                        filmPrice = roomListParamRow.getPrice();
                        movieSeatListAdapter.setFilmPrice(filmPrice);
                    });
                } else {
                    showSyncToast(theatreRoomListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });

        param.clear();
        param.put("roomId", roomId);

        //获取电影座位表
        Api.config(Constant.NetWork.MOVIE_THEATRE_SEAT_LIST, param, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                MovieTheatreSeatListParam theatreSeatListParam = gson.fromJson(result, MovieTheatreSeatListParam.class);
                if (theatreSeatListParam.getCode() == HttpURLConnection.HTTP_OK) {
                    List<MovieTheatreSeatListParam.RowsDTO> seatListParamRows = theatreSeatListParam.getRows();
                    runOnUiThread(() -> {
                        setSeatList(seatListParamRows);
                    });
                } else {
                    showSyncToast(theatreSeatListParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });

        theatreSeatTable.setSeatChecker(new SeatTable.SeatChecker() {
            @Override
            public boolean isValidSeat(int row, int column) {
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
                return "0".equals(flag[row][column].getType());
            }

            @Override
            public void checked(int row, int column) {
                seatList.add(new int[]{row, column});
                runOnUiThread(() -> {
                    selectBtnText();
                });
            }

            @Override
            public void unCheck(int row, int column) {
                Optional<int[]> first = seatList.stream().filter(item -> row == item[0] && column == item[1]).findFirst();
                seatList.remove(first.get());
                runOnUiThread(() -> {
                    selectBtnText();
                });
            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return new String[0];
            }
        });

        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(payView)
                .create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable(AppCompatResources.getDrawable(this, R.drawable.alert_bg_translate));

        //取消按钮监听事件
        dialogCancelBtn.setOnClickListener(view -> {
            Api.config(Constant.NetWork.MOVIE_TICKET_PAY_ORDER, null, this).deleteRestfulRequest(new RequestCallback() {
                @Override
                public void success(String result) {
                    BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                    if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                        alertDialog.hide();
                    } else {
                        showSyncToast(baseParam.getMsg());
                    }
                }

                @Override
                public void failure(Exception e) {
                    showSyncToast("网络异常");
                }
            }, Long.parseLong(orderNo));
            alertDialog.hide();
        });
        //确认按钮监听事件
        dialogOkBtn.setOnClickListener(view -> {
            if (orderPayType == -1) {
                showSyncToast("请选择支付方式");
                return;
            }
            Api.config(Constant.NetWork.MOVIE_TICKER_ORDER, null, this).getRestfulRequest(new RequestCallback() {
                @Override
                public void success(String result) {
                    BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                    if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                        showSyncToast("购买成功");
                    } else {
                        showSyncToast(baseParam.getMsg());
                    }
                }

                @Override
                public void failure(Exception e) {
                    showSyncToast("网络异常");
                }
            }, Long.parseLong(orderNo));
            alertDialog.dismiss();
            jumpPageFlag(MovieActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });
        //支付方式切换事件
        payType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == payTypeAli.getId()) {
                    orderPayType = 2;
                } else if (checkedId == payTypeWechat.getId()) {
                    orderPayType = 1;
                } else {
                    orderPayType = 0;
                }
            }
        });

        seatBtn.setOnClickListener(view -> {
            if (seatList.size() == 0) {
                return;
            }

            List<Map<String, Object>> orderItemsList = new ArrayList<>();
            seatList.forEach(item -> {
                Map<String, Object> orderItems = new HashMap<>();
                int row = item[0], col = item[1];
                orderItems.put("seatRow", row);
                orderItems.put("seatCol", col);
                orderItems.put("seatId", flag[row][col].getId());
                orderItemsList.add(orderItems);
            });

            param.clear();
            param.put("movieId", movieId);
            param.put("theatreId", theatreId);
            param.put("roomId", roomId);
            param.put("timesId", timesId);
            param.put("orderItems", orderItemsList);

            Api.config(Constant.NetWork.MOVIE_TICKET, param, this).postRequest(new RequestCallback() {
                @Override
                public void success(String result) {
                    BaseParam baseParam = gson.fromJson(result, BaseParam.class);
                    if (baseParam.getCode() == HttpURLConnection.HTTP_OK) {
                        Api.config(Constant.NetWork.MOVIE_TICKET_ORDER_LIST, param, MovieTheatreSeatActivity.this).getRequest(new RequestCallback() {
                            @Override
                            public void success(String result) {
                                MovieOrderListParam orderListParam = gson.fromJson(result, MovieOrderListParam.class);
                                if (orderListParam.getCode() == HttpURLConnection.HTTP_OK) {
                                    orderNo = orderListParam.getRows().get(0).getOrderNo();
                                    runOnUiThread(alertDialog::show);
                                } else {
                                    showSyncToast(orderListParam.getMsg());
                                }
                            }

                            @Override
                            public void failure(Exception e) {
                                showSyncToast("网络异常");
                            }
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
        });
    }

    //初始化座位控件
    private void setSeatList(List<MovieTheatreSeatListParam.RowsDTO> seatListParamRows) {
        int maxCol = 0, maxRow = 0;
        for (MovieTheatreSeatListParam.RowsDTO seatListParamRow : seatListParamRows) {
            maxCol = Math.max(maxCol, Integer.parseInt(seatListParamRow.getCol()));
            maxRow = Math.max(maxRow, Integer.parseInt(seatListParamRow.getRow()));
        }
        //座位是否被选中
        flag = new MovieTheatreSeatListParam.RowsDTO[maxRow][maxCol];
        for (MovieTheatreSeatListParam.RowsDTO seatListParamRow : seatListParamRows) {
            int col = Integer.parseInt(seatListParamRow.getCol()) - 1, row = Integer.parseInt(seatListParamRow.getRow()) - 1;
            flag[row][col] = seatListParamRow;
        }

        //设置座位的行数和列数
        theatreSeatTable.setData(maxRow, maxCol);
        //设置每次最多买4张
        theatreSeatTable.setMaxSelected(4);
    }

    //切换按钮文字
    private void selectBtnText() {
        movieSeatListAdapter.setSeatList(seatList);
        if (seatList.size() == 0) {
            btnText.setTextColor(Color.parseColor("#cbcbcb"));
            btnText.setText("请先选座");
            btnText.getPaint().setFakeBoldText(false);
        } else {
            btnText.setText(String.format("￥%s 确认选座", seatList.size() * filmPrice));
            btnText.setTextColor(Color.WHITE);
            btnText.getPaint().setFakeBoldText(true);
        }
    }
}