package com.example.adapter.expensesadapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.R;
import com.example.pojo.expenses.ExpensesWeatherParam;
import com.example.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class ExpensesWeatherListAdapter extends RecyclerView.Adapter<ExpensesWeatherListAdapter.InnerHolder> {
    List<ExpensesWeatherParam.DataDTO.WeatherListDTO> weatherList = new ArrayList<>();
    private weatherItemClickListener clickListener;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expenses_weather_list, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        ExpensesWeatherParam.DataDTO.WeatherListDTO weatherListDTO = weatherList.get(position);

        holder.listMinTemperature.setText(weatherListDTO.getMinTemperature());
        holder.listMaxTemperature.setText(weatherListDTO.getMaxTemperature());
        Glide.with(holder.itemView.getContext())
                .load(Constant.EXPENSES_WEATHER_MAPS.get(weatherListDTO.getWeather()))
                .transform(new CircleCrop())
                .into(holder.listImage);

        String[] daySplit = weatherListDTO.getDay().split("日", 2);
        holder.listDay.setText(daySplit[1]);
        String[] dateSplit = daySplit[0].split("月");
        holder.listDate.setText(dateSplit[0] + "/" + dateSplit[1]);

        holder.weatherListCard.setOnClickListener(view -> {
            clickListener.click(weatherListDTO);
        });
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public void setWeatherItemClickListener(weatherItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface weatherItemClickListener {
        void click(ExpensesWeatherParam.DataDTO.WeatherListDTO weatherListDTO);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<ExpensesWeatherParam.DataDTO.WeatherListDTO> data) {
        weatherList.clear();
        weatherList.addAll(data);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final ConstraintLayout weatherListCard;
        private final TextView listDate, listDay, listMinTemperature, listMaxTemperature;
        private final ImageView listImage;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            weatherListCard = itemView.findViewById(R.id.expenses_weather_list_card);
            listDate = itemView.findViewById(R.id.expenses_weather_list_date);
            listDay = itemView.findViewById(R.id.expenses_weather_list_day);
            listMinTemperature = itemView.findViewById(R.id.expenses_weather_list_min_temperature);
            listMaxTemperature = itemView.findViewById(R.id.expenses_weather_list_max_temperature);
            listImage = itemView.findViewById(R.id.expenses_weather_list_image);
        }
    }
}
