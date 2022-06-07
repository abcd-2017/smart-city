package com.example.adapter.trafficadapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.pojo.trafficparam.TrafficCarListParam;
import com.lxj.xpopup.XPopup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class TrafficCarListAdapter extends RecyclerView.Adapter<TrafficCarListAdapter.InnerHolder> {

    private final List<TrafficCarListParam.RowsDTO> carListParamRows = new ArrayList<>();
    private CarListOnLongListener onLongListener;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_traffic_car_list, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        TrafficCarListParam.RowsDTO rowsDTO = carListParamRows.get(position);

        holder.carListEngineNo.setText("引擎号" + rowsDTO.getEngineNo());
        holder.carListPlateNo.setText(rowsDTO.getPlateNo());
        holder.carListType.setText(rowsDTO.getType());

        XPopup.Builder builder = new XPopup.Builder(holder.itemView.getContext())
                .hasShadowBg(false)
                .atView(holder.listCard);

        holder.listCard.setOnLongClickListener(view -> {
            return onLongListener.onLongClick(position, builder);
        });
    }

    @Override
    public int getItemCount() {
        return carListParamRows.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<TrafficCarListParam.RowsDTO> data) {
        carListParamRows.clear();
        carListParamRows.addAll(data);
        notifyDataSetChanged();
    }

    public void setCarListOnLongListener(CarListOnLongListener onLongListener) {
        this.onLongListener = onLongListener;
    }

    public interface CarListOnLongListener {
        boolean onLongClick(Integer num, XPopup.Builder builder);
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final CardView listCard;
        private final TextView carListType, carListPlateNo, carListEngineNo;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            listCard = itemView.findViewById(R.id.traffic_car_card);
            carListType = itemView.findViewById(R.id.traffic_car_list_type);
            carListPlateNo = itemView.findViewById(R.id.traffic_car_list_plate_no);
            carListEngineNo = itemView.findViewById(R.id.traffic_car_list_engine_no);
        }
    }
}
