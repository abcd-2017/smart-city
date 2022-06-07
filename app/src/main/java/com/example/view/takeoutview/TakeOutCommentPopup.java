package com.example.view.takeoutview;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.R;
import com.example.activity.takeoutactivity.TakeOutSellerDetailActivity;
import com.example.adapter.takeoutadapter.TakeOutCarProductAdapter;
import com.example.pojo.takeoutparam.TakeOutProductListParam;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;
import com.lxj.xpopup.widget.VerticalRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kkk
 * 外卖购物车弹窗
 */
public class TakeOutCommentPopup extends BottomPopupView {

    private List<TakeOutProductListParam.DataDTO> productListParamRows;
    private LinearLayout clearCar;
    private VerticalRecyclerView carRecycler;
    private CardView paymentBtn;
    private TextView emptyText;
    private TakeOutCarProductAdapter outCarProductAdapter;
    private TakeOutSellerDetailActivity.ShowCarPopupCallback popupCallback;
    private Map<Integer, TakeOutProductListParam.DataDTO> productCar = new HashMap<>();

    public TakeOutCommentPopup(@NonNull Context context) {
        super(context);
        //初始化购物车适配器
        outCarProductAdapter = new TakeOutCarProductAdapter();
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_take_out_car;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        initView();

        carRecycler.setAdapter(outCarProductAdapter);
        paymentBtn.setBackgroundColor(Color.parseColor("#ffd161"));

        //购物车子项按钮点击事件
        outCarProductAdapter.setProductClickListener(new TakeOutCarProductAdapter.ProductCountClickListener() {
            @Override
            public boolean clickAddBtn(Integer count, TakeOutProductListParam.DataDTO productParam) {
                productParam.setCount(count + 1);
                return true;
            }

            @Override
            public boolean clickReduceBtn(Integer count, TakeOutProductListParam.DataDTO productParam) {
                if (count == 1) {
                    productCar.remove(productParam.getId());
                    renderData();
                    return false;
                }
                productParam.setCount(count - 1);
                return true;
            }
        });

        //一键清除购物车
        clearCar.setOnClickListener(view -> {
            productCar.clear();
            dismiss();
        });

        //点击跳转结算节目
        paymentBtn.setOnClickListener(view -> {
            popupCallback.closePopupToNewPage(productCar);
        });
    }

    private void initView() {
        clearCar = findViewById(R.id.popup_take_out_clear_car);
        carRecycler = findViewById(R.id.popup_take_out_recycler);
        paymentBtn = findViewById(R.id.popup_take_out_payment_btn);
        emptyText = findViewById(R.id.take_out_product_car_empty_text);
    }

    public void setActivityCallback(TakeOutSellerDetailActivity.ShowCarPopupCallback popupCallback) {
        this.popupCallback = popupCallback;
    }

    private void renderData() {
        if (productCar.size() > 0) {
            outCarProductAdapter.setList(productCar);
            emptyText.setVisibility(GONE);
            carRecycler.setVisibility(VISIBLE);
        } else {
            emptyText.setVisibility(VISIBLE);
            carRecycler.setVisibility(GONE);
        }
    }

    @Override
    public BasePopupView show() {
        //显示的时候从activity中拿到购物车数据
        productCar.clear();
        productCar.putAll(popupCallback.setDataToPopup());
        return super.show();
    }

    @Override
    protected void beforeShow() {
        super.beforeShow();
        renderData();
    }

    @Override
    public void dismiss() {
        //隐藏的时候将购物车数据传递给依附的activity
        super.dismiss();
        popupCallback.getDataToPopup(productCar);
    }

    @Override
    protected int getMaxHeight() {
        return (int) (XPopupUtils.getScreenHeight(getContext()) * 0.7f);
    }
}
