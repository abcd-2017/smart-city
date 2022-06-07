package com.example.adapter.expensesadapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.pojo.expenses.ExpensesPaymentListParam;
import com.example.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class ExpensesPaymentListAdapter extends RecyclerView.Adapter<ExpensesPaymentListAdapter.InnerHolder> {
    List<ExpensesPaymentListParam.DataDTO> listParamData = new ArrayList<>();
    private PaymentItemClickListener itemClickListener;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expenses_payment_list, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        ExpensesPaymentListParam.DataDTO dataDTO = listParamData.get(position);

        holder.paymentAddress.setText(dataDTO.getAddress());
        holder.paymentType.setText(Constant.EXPENSES_PAY_TYPE[dataDTO.getCategoryId() - 1]);
        holder.paymentHostName.setText(dataDTO.getOwnerName());

        holder.paymentCard.setOnClickListener(view -> {
            itemClickListener.itemClick(dataDTO.getPaymentNo());
        });
    }

    @Override
    public int getItemCount() {
        return listParamData.size();
    }

    public void setPaymentItemClickListener(PaymentItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface PaymentItemClickListener {
        void itemClick(String paymentNo);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<ExpensesPaymentListParam.DataDTO> data) {
        listParamData.clear();
        listParamData.addAll(data);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final CardView paymentCard;
        private final TextView paymentAddress;
        private final TextView paymentHostName;
        private final TextView paymentType;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            paymentCard = itemView.findViewById(R.id.expenses_payment_card);
            paymentAddress = itemView.findViewById(R.id.expenses_payment_address);
            paymentHostName = itemView.findViewById(R.id.expenses_payment_host_name);
            paymentType = itemView.findViewById(R.id.expenses_payment_type);
        }
    }
}
