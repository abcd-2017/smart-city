package com.example.adapter.hospitaladapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author kkk
 */
public class HospitalCategoryDetailListAdapter extends RecyclerView.Adapter<HospitalCategoryDetailListAdapter.InnerHolder> {

    private String currTime;
    private final String[] hours = {"08:00", "09:00", "10:00", "11:00", "14:00", "15:00", "16:00", "17:00"};
    private ItemClickListener itemClickListener;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Calendar calendar = GregorianCalendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (day == 0) {
            calendar.add(day, 1);
        } else if (day == 6) {
            calendar.add(day, 2);
        }
        Date time = calendar.getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        currTime = simpleDateFormat.format(time);
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hospital_category_detail_list, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        String time = currTime + " " + hours[position];
        holder.registerTime.setText(time);
        holder.reservation.setOnClickListener(view -> {
            itemClickListener.click(time);
        });
    }

    @Override
    public int getItemCount() {
        return hours.length;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void click(String time);
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final Button reservation;
        private final TextView registerTime;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            registerTime = itemView.findViewById(R.id.category_register_time);
            reservation = itemView.findViewById(R.id.reservation);
        }
    }
}
