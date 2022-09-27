package com.example.adapter.busadapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.pojo.busparam.BusListParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class BusListAdapter extends RecyclerView.Adapter<BusListAdapter.InnerHolder> {
    List<BusListParam.RowsDTO> rowsDTOS = new ArrayList<>();
    private BusItemClickListener itemClickListener;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bus_list, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        BusListParam.RowsDTO rowsDTO = rowsDTOS.get(position);
        holder.lineName.setText(rowsDTO.getName());
        holder.lineStart.setText(rowsDTO.getFirst());
        holder.lineEnd.setText(rowsDTO.getEnd());
        holder.lineStartTime.setText(rowsDTO.getStartTime());
        holder.lineEndTime.setText(rowsDTO.getEndTime());
        holder.linePrice.setText("￥ " + rowsDTO.getPrice());

        holder.lineItem.setOnClickListener(view -> {
            itemClickListener.click(rowsDTO);
        });
    }

    @Override
    public int getItemCount() {
        return rowsDTOS.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<BusListParam.RowsDTO> data) {
        rowsDTOS.clear();
        rowsDTOS.addAll(data);
        notifyDataSetChanged();
    }

    public void setBusItemClickListener(BusItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface BusItemClickListener {
        void click(BusListParam.RowsDTO rowsDTO);
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final TextView lineEndTime;
        private final TextView lineName;
        private final TextView lineStartTime;
        private final TextView lineEnd;
        private final TextView lineStart;
        private final CardView lineItem;
        private final TextView linePrice;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            lineEndTime = itemView.findViewById(R.id.bus_line_end_time);
            lineName = itemView.findViewById(R.id.bus_line_name);
            lineStartTime = itemView.findViewById(R.id.bus_line_start_time);
            lineEnd = itemView.findViewById(R.id.bus_line_end);
            lineStart = itemView.findViewById(R.id.bus_line_start);
            lineItem = itemView.findViewById(R.id.bus_line_item);
            linePrice = itemView.findViewById(R.id.bus_line_price);
        }
    }
}
