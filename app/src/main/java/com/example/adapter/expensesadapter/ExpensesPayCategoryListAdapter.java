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
import com.example.pojo.expenses.ExpensesPayCategoryListParam;
import com.example.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kkk
 */
public class ExpensesPayCategoryListAdapter extends RecyclerView.Adapter<ExpensesPayCategoryListAdapter.InnerHolder> {
    List<ExpensesPayCategoryListParam.DataDTO> listParamData = new ArrayList<>();
    private CategoryItemClickListener clickListener;

    @NonNull
    @Override
    public ExpensesPayCategoryListAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expenses_pay_category_list, parent, false);
        return new InnerHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpensesPayCategoryListAdapter.InnerHolder holder, int position) {
        ExpensesPayCategoryListParam.DataDTO dataDTO = listParamData.get(position);

        holder.categoryName.setText(dataDTO.getLiveName());

        Glide.with(holder.itemView.getContext())
                .load(Constant.BASE_API + dataDTO.getImgUrl())
                .transform(new CircleCrop())
                .into(holder.categoryIcon);

        holder.categoryCard.setOnClickListener(view -> {
            clickListener.click(dataDTO.getId());
        });
    }

    public void setItemClickListener(CategoryItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public interface CategoryItemClickListener {
        void click(Integer id);
    }

    @Override
    public int getItemCount() {
        return listParamData.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<ExpensesPayCategoryListParam.DataDTO> data) {
        listParamData.clear();
        listParamData.addAll(data);
        notifyDataSetChanged();
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private final TextView categoryName;
        private final ImageView categoryIcon;
        private final CardView categoryCard;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.expenses_pay_category_name);
            categoryIcon = itemView.findViewById(R.id.expenses_pay_category_icon);
            categoryCard = itemView.findViewById(R.id.expenses_pay_category_card);
        }
    }
}
