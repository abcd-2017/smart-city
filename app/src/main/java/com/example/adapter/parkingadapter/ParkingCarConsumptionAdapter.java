package com.example.adapter.parkingadapter;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.pojo.parkingparam.ParkingCarConsumptionParam;
import com.lxj.xpopup.XPopup;

import java.util.LinkedList;
import java.util.List;

/**
 * @author kkk
 */
public class ParkingCarConsumptionAdapter extends RecyclerView.Adapter<ParkingCarConsumptionAdapter.InnerHolder> {
    private final List<ParkingCarConsumptionParam.RowsDTO> rowsDTOList = new LinkedList<>();
    private boolean[] flag;
    private consumptionItemClickListener clickListener;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parking_car_consumpation, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        ParkingCarConsumptionParam.RowsDTO rowsDTO = rowsDTOList.get(position);
        holder.consumptionMoney.setText("- " + rowsDTO.getAmount());
        holder.travelDistance.setText(rowsDTO.getTravelDistance() + "公里");
        holder.gasFiling.setText(rowsDTO.getGasFilling() + "元");
        holder.consumptionTime.setText(rowsDTO.getTravelDate());

        int height = 360;

        //切换按钮样式，淡入淡出显示详情界面
        holder.consumptionExpand.setOnClickListener(view -> {
            if (flag[position]) {
                flag[position] = false;
                toggleImage(holder.consumptionCardDetail, holder.consumptionImg, ValueAnimator.ofFloat(height, 0), ValueAnimator.ofFloat(180, 0), true);
            } else {
                flag[position] = true;
                toggleImage(holder.consumptionCardDetail, holder.consumptionImg, ValueAnimator.ofFloat(0, height), ValueAnimator.ofFloat(0, 180), false);
            }
        });

        holder.consumptionCard.setOnClickListener(view -> {
            clickListener.click(rowsDTO);
        });

        holder.consumptionCard.setOnLongClickListener(view -> {
            if (flag[position]) {
                toggleImage(holder.consumptionCardDetail, holder.consumptionImg, ValueAnimator.ofFloat(height, 0), ValueAnimator.ofFloat(180, 0), true);
            }
            clickListener.deleteItem(rowsDTO.getId(), new XPopup.Builder(holder.itemView.getContext())
                    .hasShadowBg(false)
                    .atView(holder.consumptionCard));
            return true;
        });
    }

    //动画切换
    private void toggleImage(CardView consumptionCardDetail, ImageView consumptionImg, ValueAnimator alphaAnimator, ValueAnimator imageAnimator, boolean flag) {
        //设置图片旋转
        imageAnimator.setDuration(500)
                .setInterpolator(new AccelerateDecelerateInterpolator());
        imageAnimator.addUpdateListener(animation -> {
            float rotationValue = (float) animation.getAnimatedValue();
            consumptionImg.setRotation(rotationValue);
        });

        //设置详情图展开收起动画
        alphaAnimator.addUpdateListener(animation -> {
            float height = (float) animation.getAnimatedValue();
            float alphaNum = alphaAnimator.getAnimatedFraction();
            if (flag) {
                alphaNum = 1 - alphaNum;
            }
            consumptionCardDetail.setAlpha(alphaNum);
            ViewGroup.LayoutParams layoutParams = consumptionCardDetail.getLayoutParams();
            layoutParams.height = (int) height;
            consumptionCardDetail.setLayoutParams(layoutParams);
        });
        alphaAnimator.setDuration(1000)
                .setInterpolator(new DecelerateInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(alphaAnimator).with(imageAnimator);
        animatorSet.start();
    }

    @Override
    public int getItemCount() {
        return rowsDTOList.size();
    }

    public void setItemClickListener(consumptionItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface consumptionItemClickListener {
        void click(ParkingCarConsumptionParam.RowsDTO rowsDTO);

        void deleteItem(Integer id, XPopup.Builder xPopupBuilder);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<ParkingCarConsumptionParam.RowsDTO> data) {
        flag = new boolean[data.size()];

        rowsDTOList.clear();
        rowsDTOList.addAll(data);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final ConstraintLayout consumptionExpand;
        private final CardView consumptionCardDetail, consumptionCard;
        private final TextView travelDistance, gasFiling, consumptionMoney, consumptionTime;
        private final ImageView consumptionImg;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            consumptionCard = itemView.findViewById(R.id.car_consumption_card);
            consumptionCardDetail = itemView.findViewById(R.id.car_consumption_card_detail);
            travelDistance = itemView.findViewById(R.id.car_consumption_travel_distance);
            gasFiling = itemView.findViewById(R.id.car_consumption_gas_filing);
            consumptionMoney = itemView.findViewById(R.id.parking_car_consumption_money);
            consumptionExpand = itemView.findViewById(R.id.car_consumption_expand);
            consumptionTime = itemView.findViewById(R.id.car_consumption_time);
            consumptionImg = itemView.findViewById(R.id.parking_consumption_image);
        }
    }
}
