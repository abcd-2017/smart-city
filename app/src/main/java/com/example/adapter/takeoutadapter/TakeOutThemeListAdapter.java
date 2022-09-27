package com.example.adapter.takeoutadapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.R;
import com.example.pojo.takeoutparam.TakeOutThemeListParam;
import com.example.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class TakeOutThemeListAdapter extends RecyclerView.Adapter<TakeOutThemeListAdapter.InnerHoller> {

    private TakeOutThemeItemClickListener clickListener;
    private final List<TakeOutThemeListParam.DataDTO> themeListParamData = new ArrayList<>();

    @NonNull
    @Override
    public InnerHoller onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_take_out_theme, parent, false);
        return new InnerHoller(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHoller holder, int position) {
        TakeOutThemeListParam.DataDTO dataDTO = themeListParamData.get(position);

        holder.themeName.setText(dataDTO.getThemeName());
        Glide.with(holder.itemView.getContext())
                .load(Constant.BASE_API + dataDTO.getImgUrl())
                .transform(new CircleCrop())
                .into(holder.themeIcon);

        holder.themeCard.setOnClickListener(view -> {
            clickListener.click(dataDTO.getId());
        });
    }

    @Override
    public int getItemCount() {
        return themeListParamData.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<TakeOutThemeListParam.DataDTO> data) {
        themeListParamData.clear();
        themeListParamData.addAll(data);
        notifyDataSetChanged();
    }

    public void setThemeItemClickListener(TakeOutThemeItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface TakeOutThemeItemClickListener {
        void click(Integer id);
    }


    public class InnerHoller extends RecyclerView.ViewHolder {

        private final ImageView themeIcon;
        private final TextView themeName;
        private final LinearLayout themeCard;

        public InnerHoller(@NonNull View itemView) {
            super(itemView);
            themeIcon = itemView.findViewById(R.id.item_take_out_theme_icon);
            themeName = itemView.findViewById(R.id.item_take_out_theme_name);
            themeCard = itemView.findViewById(R.id.item_take_out_theme_card);
        }
    }
}
