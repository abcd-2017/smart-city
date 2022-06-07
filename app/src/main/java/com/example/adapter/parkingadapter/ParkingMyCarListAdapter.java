package com.example.adapter.parkingadapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.pojo.parkingparam.ParkingMyCarListParam;
import com.lxj.xpopup.XPopup;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class ParkingMyCarListAdapter extends RecyclerView.Adapter<ParkingMyCarListAdapter.InnerHolder> {
    List<ParkingMyCarListParam.RowsDTO> rowsDTOList = new ArrayList<>();
    private CarItemListener carItemListener;
    private Context mContext;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parking_my_car_list, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        ParkingMyCarListParam.RowsDTO rowsDTO = rowsDTOList.get(position);
        holder.carNum.setText(rowsDTO.getPlateNo());
        holder.carType.setText(rowsDTO.getType());
        if ("新能源车".equals(rowsDTO.getType())) {
            holder.carCard.setCardBackgroundColor(Color.rgb(65, 174, 60));
        } else {
            holder.carCard.setCardBackgroundColor(Color.rgb(23, 114, 180));
        }

        XPopup.Builder xPopupBuilder = new XPopup.Builder(mContext)
                .hasShadowBg(false)
                .watchView(holder.carCard);

        holder.carCard.setOnClickListener(view -> {
            carItemListener.click(rowsDTO.getPlateNo());
        });
        holder.carCard.setOnLongClickListener(view -> {
            carItemListener.onLongClick(rowsDTO, xPopupBuilder);
            return true;
        });

        holder.carDeleteBtn.setOnClickListener(view -> {
            carItemListener.deleteCar(rowsDTO.getId(), holder.carSwipe);
        });
    }

    @Override
    public int getItemCount() {
        return rowsDTOList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<ParkingMyCarListParam.RowsDTO> data) {
        rowsDTOList.clear();
        rowsDTOList.addAll(data);
        notifyDataSetChanged();
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public void setCarItemListener(CarItemListener carItemClickListener) {
        this.carItemListener = carItemClickListener;
    }

    public interface CarItemListener {
        void click(String plateNo);

        void onLongClick(ParkingMyCarListParam.RowsDTO carParam, XPopup.Builder xPopupBuilder);

        void deleteCar(Integer id, SwipeMenuLayout carSwiper);
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final CardView carCard;
        private final TextView carType;
        private final TextView carNum;
        private final Button carDeleteBtn;
        private final SwipeMenuLayout carSwipe;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            carCard = itemView.findViewById(R.id.parking_car_card);
            carType = itemView.findViewById(R.id.parking_car_type);
            carNum = itemView.findViewById(R.id.parking_car_num);
            carDeleteBtn = itemView.findViewById(R.id.parking_car_delete_btn);
            carSwipe = itemView.findViewById(R.id.parking_car_swipe);
        }
    }
}
