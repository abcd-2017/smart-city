package com.example.adapter.takeoutadapter;


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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.R;
import com.example.pojo.takeoutparam.TakeoutSellerProductListParam;
import com.example.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class TakeOutSellerProductAdapter extends RecyclerView.Adapter<TakeOutSellerProductAdapter.InnerHolder> {

    private final List<TakeoutSellerProductListParam.RowsDTO> productListParamRows = new ArrayList<>();
    private ProductItemClickListener clickListener;

    @NonNull
    @Override
    public TakeOutSellerProductAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_take_out_seller_product, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull TakeOutSellerProductAdapter.InnerHolder holder, int position) {
        TakeoutSellerProductListParam.RowsDTO.ProductListDTO productListDTO = productListParamRows.get(position).getProductList().get(0);

        holder.productName.setText(productListDTO.getName());
        holder.productPrice.setText(String.format("￥%s", productListDTO.getPrice()));
        holder.productFavorRate.setText(String.format("好评率：%s", productListDTO.getFavorRate()));
        holder.productSaleQuantity.setText(String.format("月售￥%s", productListDTO.getSaleQuantity()));

        Glide.with(holder.itemView.getContext())
                .load(Constant.BASE_API + productListDTO.getImgUrl())
                .transform(new RoundedCorners(12))
                .into(holder.productCover);

        holder.productCard.setOnClickListener(view -> {
            clickListener.click(productListDTO);
        });
    }

    @Override
    public int getItemCount() {
        System.out.println(productListParamRows.size());
        return productListParamRows.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<TakeoutSellerProductListParam.RowsDTO> data) {
        productListParamRows.clear();
        productListParamRows.addAll(data);
        notifyDataSetChanged();
    }

    public void setProductItemClickListener(ProductItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface ProductItemClickListener {
        void click(TakeoutSellerProductListParam.RowsDTO.ProductListDTO productListDTO);
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        private final CardView productCard;
        private final ImageView productCover;
        private final TextView productFavorRate, productName, productPrice, productSaleQuantity;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            productCard = itemView.findViewById(R.id.item_take_out_product_card);
            productCover = itemView.findViewById(R.id.item_take_out_product_cover);
            productFavorRate = itemView.findViewById(R.id.item_take_out_product_favor_rate);
            productName = itemView.findViewById(R.id.item_take_out_product_name);
            productPrice = itemView.findViewById(R.id.item_take_out_product_price);
            productSaleQuantity = itemView.findViewById(R.id.item_take_out_product_sale_quantitiy);
        }
    }
}
