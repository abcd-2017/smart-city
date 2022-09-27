package com.example.adapter.hospitaladapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.pojo.hospitalparam.HospitalPatientListParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class HospitalPatientListAdapter extends RecyclerView.Adapter<HospitalPatientListAdapter.InnerHolder> {
    List<HospitalPatientListParam.RowsDTO> listParamRows = new ArrayList<>();
    private ItemClickListener itemClickListener;

    @NonNull
    @Override
    public HospitalPatientListAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hospital_patient_list, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull HospitalPatientListAdapter.InnerHolder holder, int position) {
        HospitalPatientListParam.RowsDTO rowsDTO = listParamRows.get(position);
        holder.cardAddress.setText(rowsDTO.getAddress());
        holder.cardName.setText(rowsDTO.getName());
        holder.cardPhone.setText(rowsDTO.getTel());
        holder.cardSex.setText("0".equals(rowsDTO.getSex()) ? "男" : "女");
        holder.cardId.setText(rowsDTO.getAddress());
        holder.hospitalCard.setOnClickListener(view -> {
            itemClickListener.clickCard(position);
        });
        holder.cardToNext.setOnClickListener(view -> {
            itemClickListener.clickNext(rowsDTO.getId(), rowsDTO.getName());
        });
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void clickCard(Integer index);

        void clickNext(Integer id, String name);
    }

    @Override
    public int getItemCount() {
        return listParamRows.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<HospitalPatientListParam.RowsDTO> data) {
        listParamRows.clear();
        listParamRows.addAll(data);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final TextView cardName;
        private final TextView cardId;
        private final TextView cardAddress;
        private final TextView cardPhone;
        private final TextView cardSex;
        private final CardView cardToNext;
        private final CardView hospitalCard;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            cardName = itemView.findViewById(R.id.hospital_card_name);
            cardId = itemView.findViewById(R.id.hospital_card_cardId);
            cardAddress = itemView.findViewById(R.id.hospital_card_address);
            cardPhone = itemView.findViewById(R.id.hospital_card_phone);
            cardSex = itemView.findViewById(R.id.hospital_card_sex);
            cardToNext = itemView.findViewById(R.id.hospital_card_to_next);
            hospitalCard = itemView.findViewById(R.id.hospital_card);
        }
    }
}
