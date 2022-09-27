package com.example.activity.expenseactivity;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.activity.BaseActivity;
import com.example.adapter.expensesadapter.ExpensesWeatherHoursListAdapter;
import com.example.adapter.expensesadapter.ExpensesWeatherListAdapter;
import com.example.pojo.expenses.ExpensesWeatherParam;
import com.example.util.Api;
import com.example.util.Constant;
import com.example.util.RequestCallback;
import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.util.List;

/**
 * @author kkk
 */
public class ExpensesWeatherForecastActivity extends BaseActivity {

    private TextView cardBodyTemperature, cardWindDirection, cardWindScale, cardHumidity, cardUV, cardSports, cardDressing, pm2_5, uvNum, temperature, apparentTemperature, weather, minTemperature, maxTemperature, cardPm2_5, cardPm10, cardCo, cardSO2, airQuality;
    private RecyclerView hoursRecycler, weatherListRecycler;
    private ProgressBar cardPm2_5Bar, cardPm10Bar, cardPmCOBar, cardPmCO2Bar;
    private Toolbar toolbar;
    private ExpensesWeatherListAdapter weatherListAdapter;
    private ExpensesWeatherHoursListAdapter weatherHoursListAdapter;
    private ImageView weatherUVImage;
    private Gson gson = new Gson();

    @Override
    protected int initLayout() {
        return R.layout.activity_expenses_weather_forecast;
    }

    @Override
    protected void initView() {
        toolbar = findViewById(R.id.expenses_toolbar);
        cardBodyTemperature = findViewById(R.id.expenses_weather_card_body_temperature);
        cardWindDirection = findViewById(R.id.expenses_weather_card_wind_direction);
        cardWindScale = findViewById(R.id.expenses_weather_card_wind_scale);
        cardHumidity = findViewById(R.id.expenses_weather_card_humidity);
        cardUV = findViewById(R.id.expenses_weather_card_uv);
        cardSports = findViewById(R.id.expenses_weather_card_sports);
        cardDressing = findViewById(R.id.expenses_weather_card_dressing);
        pm2_5 = findViewById(R.id.expenses_weather_pm2_5);
        uvNum = findViewById(R.id.expenses_weather_uv_num);
        temperature = findViewById(R.id.expenses_weather_temperature);
        apparentTemperature = findViewById(R.id.expenses_weather_apparent_temperature);
        weather = findViewById(R.id.expenses_weather_weather);
        minTemperature = findViewById(R.id.expenses_weather_min_temperature);
        maxTemperature = findViewById(R.id.expenses_weather_max_temperature);
        hoursRecycler = findViewById(R.id.expenses_weather_hours_recycler);
        weatherListRecycler = findViewById(R.id.expenses_weather_weatherList_recycler);
        cardPm2_5 = findViewById(R.id.expenses_weather_card_pm25);
        cardPm10 = findViewById(R.id.expenses_weather_card_pm10);
        cardCo = findViewById(R.id.expenses_weather_card_co);
        cardSO2 = findViewById(R.id.expenses_weather_card_co2);
        airQuality = findViewById(R.id.expenses_weather_air_quality);
        cardPm2_5Bar = findViewById(R.id.expenses_weather_card_pm25_bar);
        cardPm10Bar = findViewById(R.id.expenses_weather_card_pm10_bar);
        cardPmCOBar = findViewById(R.id.expenses_weather_card_co_bar);
        cardPmCO2Bar = findViewById(R.id.expenses_weather_card_co2_bar);
        weatherUVImage = findViewById(R.id.expenses_weather_uv_image);

        weatherListAdapter = new ExpensesWeatherListAdapter();
        weatherHoursListAdapter = new ExpensesWeatherHoursListAdapter();
    }

    @Override
    protected void initData() {
        toolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        //设置适配器
        hoursRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        hoursRecycler.setAdapter(weatherHoursListAdapter);

        weatherListRecycler.setLayoutManager(new LinearLayoutManager(this));
        weatherListRecycler.setAdapter(weatherListAdapter);

        getWeatherInfo();

        weatherListAdapter.setWeatherItemClickListener(weatherListDTO -> {
            String jsonWeatherDTO = gson.toJson(weatherListDTO);
            jumpPageToIntent(new Intent(this, ExpensesWeatherDetailActivity.class)
                    .putExtra("jsonWeatherDTO", jsonWeatherDTO));
        });
    }

    private void getWeatherInfo() {
        Api.config(Constant.NetWork.LIVING_WEATHER, null, this).getRequest(new RequestCallback() {
            @Override
            public void success(String result) {
                ExpensesWeatherParam weatherParam = gson.fromJson(result, ExpensesWeatherParam.class);
                if (weatherParam.getCode() == HttpURLConnection.HTTP_OK) {
                    ExpensesWeatherParam.DataDTO weatherParamData = weatherParam.getData();
                    //天气列表
                    List<ExpensesWeatherParam.DataDTO.WeatherListDTO> weatherList = weatherParamData.getWeatherList();
                    ExpensesWeatherParam.DataDTO.TodayDTO paramDataToday = weatherParamData.getToday();
                    //时刻天气
                    List<ExpensesWeatherParam.DataDTO.TodayDTO.HoursDTO> dataTodayHours = paramDataToday.getHours();
                    //空气质量
                    ExpensesWeatherParam.DataDTO.TodayDTO.AirQuantityDTO dataTodayAirQuantity = paramDataToday.getAirQuantity();
                    //空气舒适度
                    ExpensesWeatherParam.DataDTO.TodayDTO.ComfortLevelDTO dataTodayComfortLevel = paramDataToday.getComfortLevel();
                    //天气临时信息
                    ExpensesWeatherParam.DataDTO.TodayDTO.TempInfoDTO tempInfo = paramDataToday.getTempInfo();
                    //风向信息
                    ExpensesWeatherParam.DataDTO.TodayDTO.WindDTO dataTodayWind = paramDataToday.getWind();

                    //给页面赋值
                    runOnUiThread(() -> {
                        //卡片图标信息
                        cardBodyTemperature.setText(String.format("%s℃", dataTodayComfortLevel.getApparentTemperature()));
                        cardWindDirection.setText(dataTodayWind.getWindDirection());
                        cardWindScale.setText(dataTodayWind.getWindStrength());
                        cardHumidity.setText(dataTodayComfortLevel.getHumidity() + "%");
                        cardUV.setText(dataTodayComfortLevel.getUvIndex());
                        if ("弱".equals(dataTodayComfortLevel.getUvIndex())) {
                            weatherUVImage.setBackgroundResource(R.mipmap.weather_weak_ultraviolet);
                        } else {
                            weatherUVImage.setBackgroundResource(R.mipmap.weather_strong_ultraviolet);
                        }
                        cardSports.setText(dataTodayComfortLevel.getSportIndex());
                        cardDressing.setText(dataTodayComfortLevel.getDressingIndex());

                        pm2_5.setText(String.valueOf(dataTodayAirQuantity.getPm25()));
                        uvNum.setText(dataTodayComfortLevel.getUvIndex());

                        temperature.setText(tempInfo.getTemperature());
                        apparentTemperature.setText(tempInfo.getTemperature());
                        weather.setText(tempInfo.getWeather());
                        minTemperature.setText(tempInfo.getMinTemperature());
                        maxTemperature.setText(tempInfo.getMaxTemperature());

                        //空气卡片信息
                        cardPm2_5.setText(String.valueOf(dataTodayAirQuantity.getPm25()));
                        cardPm10.setText(String.valueOf(dataTodayAirQuantity.getPm10()));
                        cardCo.setText(String.valueOf(dataTodayAirQuantity.getCo()));
                        cardSO2.setText(String.valueOf(dataTodayAirQuantity.getSo2()));
                        airQuality.setText(tempInfo.getAir());

                        Integer pm25 = dataTodayAirQuantity.getPm25();
                        cardPm2_5Bar.setProgress(pm25);
                        if (pm25 <= 75) {
                            cardPm2_5Bar.setProgressDrawable(AppCompatResources.getDrawable(ExpensesWeatherForecastActivity.this, R.drawable.progress_style_green));
                        } else if (pm25 <= 150) {
                            cardPm2_5Bar.setProgressDrawable(AppCompatResources.getDrawable(ExpensesWeatherForecastActivity.this, R.drawable.progress_style_yellow));
                        } else {
                            cardPm2_5Bar.setProgressDrawable(AppCompatResources.getDrawable(ExpensesWeatherForecastActivity.this, R.drawable.progress_style_red));
                        }
                        cardPm10Bar.setProgress(dataTodayAirQuantity.getPm10());
                        if (dataTodayAirQuantity.getPm10() <= 50) {
                            cardPm10Bar.setProgressDrawable(AppCompatResources.getDrawable(ExpensesWeatherForecastActivity.this, R.drawable.progress_style_green));
                        } else {
                            cardPm10Bar.setProgressDrawable(AppCompatResources.getDrawable(ExpensesWeatherForecastActivity.this, R.drawable.progress_style_yellow));
                        }

                        cardPmCOBar.setProgress((int) (double) dataTodayAirQuantity.getCo());
                        cardPmCO2Bar.setProgress(dataTodayAirQuantity.getNo2());

                        weatherHoursListAdapter.setData(dataTodayHours);
                        weatherListAdapter.setData(weatherList);
                    });
                } else {
                    showSyncToast(weatherParam.getMsg());
                }
            }

            @Override
            public void failure(Exception e) {
                showSyncToast("网络异常");
            }
        });
    }
}