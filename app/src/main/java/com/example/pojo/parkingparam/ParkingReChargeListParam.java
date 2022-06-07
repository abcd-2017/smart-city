package com.example.pojo.parkingparam;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author kkk
 */
public class ParkingReChargeListParam extends BaseParam {

    /**
     * rows
     */
    @SerializedName("rows")
    private List<RowsDTO> rows;
    /**
     * total
     */
    @SerializedName("total")
    private String total;

    @Override
    public String toString() {
        return "ParkingReChargeListParam{" +
                ", rows=" + rows +
                ", total='" + total + '\'' +
                '}';
    }

    public List<RowsDTO> getRows() {
        return rows;
    }

    public void setRows(List<RowsDTO> rows) {
        this.rows = rows;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public static class RowsDTO {
        /**
         * id
         */
        @SerializedName("id")
        private Integer id;
        /**
         * userId
         */
        @SerializedName("userId")
        private Integer userId;
        /**
         * money
         */
        @SerializedName("money")
        private Integer money;
        /**
         * rechargeDate
         */
        @SerializedName("rechargeDate")
        private String rechargeDate;
        /**
         * payType
         */
        @SerializedName("payType")
        private String payType;

        @Override
        public String toString() {
            return "RowsDTO{" +
                    "id=" + id +
                    ", userId=" + userId +
                    ", money=" + money +
                    ", rechargeDate='" + rechargeDate + '\'' +
                    ", payType='" + payType + '\'' +
                    '}';
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getMoney() {
            return money;
        }

        public void setMoney(Integer money) {
            this.money = money;
        }

        public String getRechargeDate() {
            return rechargeDate;
        }

        public void setRechargeDate(String rechargeDate) {
            this.rechargeDate = rechargeDate;
        }

        public String getPayType() {
            return payType;
        }

        public void setPayType(String payType) {
            this.payType = payType;
        }
    }
}
