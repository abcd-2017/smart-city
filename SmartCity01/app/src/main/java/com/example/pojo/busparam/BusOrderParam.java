package com.example.pojo.busparam;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

/**
 * @author kkk
 */
public class BusOrderParam extends BaseParam {

    /**
     * orderNum
     */
    @SerializedName("orderNum")
    private String orderNum;

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        return "BusOrderParam{" +
                "orderNum='" + orderNum + '\'' +
                super.toString() +
                '}';
    }
}
