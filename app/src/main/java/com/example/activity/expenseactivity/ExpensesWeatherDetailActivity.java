package com.example.activity.expenseactivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.R;
import com.example.activity.BaseActivity;
import com.example.activity.HomeActivity;
import com.example.pojo.expenses.ExpensesWeatherParam;
import com.example.util.Constant;
import com.google.gson.Gson;

/**
 * @author kkk
 */
public class ExpensesWeatherDetailActivity extends BaseActivity {

    private ImageView finishBtn, detailWeatherImage;
    private Toolbar toolbar;
    private TextView detailDate, detailMinTemperature, detailMaxTemperature, detailBodyTemperature, detailHumidity, detailUV;
    private Gson gson = new Gson();

    @Override
    protected int initLayout() {
        return R.layout.activity_expenses_weather_detail;
    }

    @Override
    protected void initView() {
        finishBtn = findViewById(R.id.finish_btn);
        toolbar = findViewById(R.id.expenses_toolbar);
        detailDate = findViewById(R.id.expenses_weather_detail_date);
        detailMinTemperature = findViewById(R.id.expenses_weather_detail_min_temperature);
        detailMaxTemperature = findViewById(R.id.expenses_weather_detail_max_temperature);
        detailBodyTemperature = findViewById(R.id.expenses_weather_detail_body_temperature);
        detailHumidity = findViewById(R.id.expenses_weather_detail_humidity);
        detailUV = findViewById(R.id.expenses_weather_detail_uv);
        detailWeatherImage = findViewById(R.id.expenses_weather_detail_weather_image);
    }

    @Override
    protected void initData() {
        finishBtn.setOnClickListener(view -> {
            jumpPageFlag(HomeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        });
        toolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        Bundle extras = getIntent().getExtras();
        String jsonWeatherDTO = (String) extras.get("jsonWeatherDTO");
        ExpensesWeatherParam.DataDTO.WeatherListDTO weatherListDTO = gson.fromJson(jsonWeatherDTO, ExpensesWeatherParam.DataDTO.WeatherListDTO.class);
        detailDate.setText(weatherListDTO.getDay());
        detailMinTemperature.setText(weatherListDTO.getMinTemperature());
        detailMaxTemperature.setText(weatherListDTO.getMaxTemperature());
        detailBodyTemperature.setText(String.format("%s℃", weatherListDTO.getApparentTemperature()));
        detailHumidity.setText(String.format("%s", weatherListDTO.getHumidity()));
        detailUV.setText(weatherListDTO.getUv());

        Glide.with(this)
                .load(Constant.EXPENSES_WEATHER_MAPS.get(weatherListDTO.getWeather()))
                .transform(new CircleCrop())
                .into(detailWeatherImage);
    }
}