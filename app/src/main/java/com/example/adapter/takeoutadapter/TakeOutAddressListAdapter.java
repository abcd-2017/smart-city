package com.example.adapter.takeoutadapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.pojo.takeoutparam.TakeOutAddressListParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class TakeOutAddressListAdapter extends RecyclerView.Adapter<TakeOutAddressListAdapter.InnerHolder> {
    private List<TakeOutAddressListParam.DataDTO> addressListParamData = new ArrayList<>();
    private AddressListEditClickListener clickListener;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_take_out_address_list, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        TakeOutAddressListParam.DataDTO dataDTO = addressListParamData.get(position);

        holder.addressName.setText(dataDTO.getAddressDetail());
        holder.addressPhone.setText(dataDTO.getPhone());
        holder.addressUserName.setText(dataDTO.getName());
        holder.addressTag.setText(dataDTO.getLabel());

        holder.addressEditBtn.setOnClickListener(view -> {
            clickListener.editClick(dataDTO.getId());
        });

        holder.addressCard.setOnClickListener(view -> {
            clickListener.selectItem(dataDTO);
        });
    }

    @Override
    public int getItemCount() {
        return addressListParamData.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<TakeOutAddressListParam.DataDTO> data) {
        addressListParamData.clear();
        addressListParamData.addAll(data);
        notifyDataSetChanged();
    }

    public void setListEditClickListener(AddressListEditClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface AddressListEditClickListener {
        void editClick(Integer id);

        void selectItem(TakeOutAddressListParam.DataDTO dataDTO);
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        private final TextView addressName, addressUserName, addressPhone, addressTag;
        private final LinearLayout addressEditBtn;
        private final ConstraintLayout addressCard;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            addressName = itemView.findViewById(R.id.item_take_out_address_name);
            addressUserName = itemView.findViewById(R.id.item_take_out_address_user_name);
            addressPhone = itemView.findViewById(R.id.item_take_out_address_phone);
            addressTag = itemView.findViewById(R.id.item_take_out_address_tag);
            addressEditBtn = itemView.findViewById(R.id.item_take_out_address_edit_btn);
            addressCard = itemView.findViewById(R.id.item_take_out_address_card);
        }
    }
}
