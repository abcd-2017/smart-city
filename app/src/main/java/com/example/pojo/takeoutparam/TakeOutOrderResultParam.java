package com.example.pojo.takeoutparam;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

/**
 * @author kkk
 */
public class TakeOutOrderResultParam extends BaseParam {

    /**
     * orderNo
     */
    @SerializedName("orderNo")
    private String orderNo;

    public String getOrderNo() {
        return orderNo;
    }

    @Override
    public String toString() {
        return "TakeOutOrderResultParam{" +
                "orderNo='" + orderNo + '\'' +
                '}';
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
