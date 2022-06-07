package com.example.adapter.takeoutadapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.R;
import com.example.pojo.takeoutparam.TakeOutOrderListParam;
import com.example.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class TakeOutOrderListAdapter extends RecyclerView.Adapter<TakeOutOrderListAdapter.InnerHolder> {
    private List<TakeOutOrderListParam.RowsDTO> orderListParamRows = new ArrayList<>();
    private ItemClickListener clickListener;

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_take_out_order, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        TakeOutOrderListParam.RowsDTO rowsDTO = orderListParamRows.get(position);
        TakeOutOrderListParam.RowsDTO.SellerInfoDTO sellerInfo = rowsDTO.getSellerInfo();
        TakeOutOrderListParam.RowsDTO.OrderInfoDTO orderInfo = rowsDTO.getOrderInfo();
        List<TakeOutOrderListParam.RowsDTO.OrderInfoDTO.OrderItemListDTO> orderInfoOrderItemList = orderInfo.getOrderItemList();
        TakeOutOrderProductListAdapter orderProductListAdapter = new TakeOutOrderProductListAdapter();


        if ("待支付".equals(orderInfo.getStatus())) {
            holder.orderPayBtn.setOnClickListener(view -> {
                clickListener.clickOkBtn(orderInfo.getOrderNo());
            });
            holder.orderCancelBtn.setOnClickListener(view -> {
                clickListener.clickCancelBtn(orderInfo.getOrderNo());
            });
        } else if ("待评价".equals(orderInfo.getStatus())) {
            holder.orderCancelBtn.setVisibility(View.GONE);
            holder.orderPayText.setText("立即评价");
            holder.orderPayBtn.setOnClickListener(view -> {
                clickListener.clickAddComment(orderInfo.getOrderNo());
            });
            holder.orderPayBtn.setCardBackgroundColor(Color.parseColor("#66a9c9"));
        } else {
            holder.orderBtns.setVisibility(View.GONE);
        }

        holder.orderName.setText(sellerInfo.getName());
        holder.orderStatus.setText(orderInfo.getStatus());
        Glide.with(holder.itemView.getContext())
                .load(Constant.BASE_API + sellerInfo.getImgUrl())
                .transform(new RoundedCorners(14))
                .into(holder.orderCover);
        holder.orderPrice.setText((String.format("￥%s", orderInfo.getAmount())));
        int count = 0;
        for (TakeOutOrderListParam.RowsDTO.OrderInfoDTO.OrderItemListDTO listDTO : orderInfoOrderItemList) {
            count += listDTO.getQuantity();
        }
        holder.orderCount.setText(String.format("共%s件商品", count));

        holder.orderRecycler.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.orderRecycler.setAdapter(orderProductListAdapter);

        orderProductListAdapter.setData(orderInfoOrderItemList);

        //点击进入详情
        holder.orderCard.setOnClickListener(view -> {
            clickListener.clickDetail(orderInfo.getOrderNo());
        });
    }

    @Override
    public int getItemCount() {
        return orderListParamRows.size();
    }

    public void setItemClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ItemClickListener {
        void clickOkBtn(String orderNo);

        void clickCancelBtn(String orderNo);

        void clickAddComment(String orderNo);

        void clickDetail(String orderNo);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<TakeOutOrderListParam.RowsDTO> data) {
        orderListParamRows.clear();
        orderListParamRows.addAll(data);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final RecyclerView orderRecycler;
        private final ImageView orderCover;
        private final TextView orderName, orderStatus, orderCount, orderPrice, orderPayText;
        private final LinearLayout orderBtns;
        private final CardView orderCancelBtn, orderPayBtn, orderCard;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            orderRecycler = itemView.findViewById(R.id.item_take_out_order_recycler);
            orderCover = itemView.findViewById(R.id.item_take_out_order_cover);
            orderName = itemView.findViewById(R.id.item_take_out_order_name);
            orderStatus = itemView.findViewById(R.id.item_take_out_order_status);
            orderCount = itemView.findViewById(R.id.item_take_out_order_count);
            orderPrice = itemView.findViewById(R.id.item_take_out_order_price);
            orderBtns = itemView.findViewById(R.id.item_take_out_order_btns);
            orderCancelBtn = itemView.findViewById(R.id.item_take_out_order_cancel_btn);
            orderPayBtn = itemView.findViewById(R.id.item_take_out_order_pay_btn);
            orderPayText = itemView.findViewById(R.id.item_take_out_order_pay_text);
            orderCard = itemView.findViewById(R.id.item_take_out_order_card);
        }
    }
}
