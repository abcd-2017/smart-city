package com.example.adapter.expensesadapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.R;
import com.example.pojo.expenses.ExpensesPayReCordListParam;
import com.example.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class ExpensesReCordListAdapter extends RecyclerView.Adapter<ExpensesReCordListAdapter.InnerHolder> {
    List<ExpensesPayReCordListParam.RowsDTO> reCordListParamRows = new ArrayList<>();

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expenses_record_list, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        ExpensesPayReCordListParam.RowsDTO rowsDTO = reCordListParamRows.get(position);

        holder.recordPrice.setText("-" + rowsDTO.getAmount());
        holder.recordTime.setText(rowsDTO.getRechargeTime());
        holder.recordType.setText(Constant.EXPENSES_PAY_TYPE[rowsDTO.getCategoryId() - 1]);
        holder.recordType.getPaint().setFakeBoldText(true);

        Glide.with(holder.itemView.getContext())
                .load(Constant.BASE_API + rowsDTO.getImgUrl())
                .transform(new CircleCrop())
                .into(holder.recordIcon);
    }

    @Override
    public int getItemCount() {
        return reCordListParamRows.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<ExpensesPayReCordListParam.RowsDTO> data) {
        reCordListParamRows.clear();
        reCordListParamRows.addAll(data);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final CardView recordCard;
        private final ImageView recordIcon;
        private final TextView recordType;
        private final TextView recordTime;
        private final TextView recordPrice;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            recordCard = itemView.findViewById(R.id.expenses_record_card);
            recordIcon = itemView.findViewById(R.id.expenses_record_icon);
            recordType = itemView.findViewById(R.id.expenses_record_type);
            recordTime = itemView.findViewById(R.id.expenses_record_time);
            recordPrice = itemView.findViewById(R.id.expenses_record_price);
        }
    }
}
