package com.example.adapter.expensesadapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
public class ExpensesWeatherHoursListAdapter extends RecyclerView.Adapter<ExpensesWeatherHoursListAdapter.InnerHolder> {
    List<ExpensesWeatherParam.DataDTO.TodayDTO.HoursDTO> dataTodayHours = new ArrayList<>();


    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_expenses_weather_hours_list, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        ExpensesWeatherParam.DataDTO.TodayDTO.HoursDTO hoursDTO = dataTodayHours.get(position);

        holder.hoursTemperature.setText(hoursDTO.getTemperature() + "℃");
        if (position == 0) {
            holder.hoursTime.setText("现在");
        } else {
            holder.hoursTime.setText(hoursDTO.getHour());
        }
        Glide.with(holder.itemView.getContext())
                .load(Constant.EXPENSES_WEATHER_MAPS.get(hoursDTO.getWeather()))
                .transform(new CircleCrop())
                .into(holder.hoursImage);
    }

    @Override
    public int getItemCount() {
        return dataTodayHours.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<ExpensesWeatherParam.DataDTO.TodayDTO.HoursDTO> data) {
        dataTodayHours.clear();
        dataTodayHours.addAll(data);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final TextView hoursTemperature, hoursTime;
        private final ImageView hoursImage;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            hoursImage = itemView.findViewById(R.id.expenses_weather_hours_images);
            hoursTemperature = itemView.findViewById(R.id.expenses_weather_hours_temperature);
            hoursTime = itemView.findViewById(R.id.expenses_weather_hours_time);
        }
    }
}
