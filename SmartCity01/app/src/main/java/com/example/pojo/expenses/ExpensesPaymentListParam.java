package com.example.pojo.expenses;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author kkk
 */
public class ExpensesPaymentListParam extends BaseParam {

    /**
     * data
     */
    @SerializedName("data")
    private List<DataDTO> data;

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ExpensesPaymentListParam{" +
                "data=" + data +
                '}';
    }

    public static class DataDTO {
        /**
         * id
         */
        @SerializedName("id")
        private Integer id;
        /**
         * ownerName
         */
        @SerializedName("ownerName")
        private String ownerName;
        /**
         * idCard
         */
        @SerializedName("idCard")
        private String idCard;
        /**
         * address
         */
        @SerializedName("address")
        private String address;
        /**
         * paymentNo
         */
        @SerializedName("paymentNo")
        private String paymentNo;
        /**
         * categoryId
         */
        @SerializedName("categoryId")
        private Integer categoryId;
        /**
         * userId
         */
        @SerializedName("userId")
        private Integer userId;

        @Override
        public String toString() {
            return "DataDTO{" +
                    "id=" + id +
                    ", ownerName='" + ownerName + '\'' +
                    ", idCard='" + idCard + '\'' +
                    ", address='" + address + '\'' +
                    ", paymentNo='" + paymentNo + '\'' +
                    ", categoryId=" + categoryId +
                    ", userId=" + userId +
                    '}';
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getOwnerName() {
            return ownerName;
        }

        public void setOwnerName(String ownerName) {
            this.ownerName = ownerName;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPaymentNo() {
            return paymentNo;
        }

        public void setPaymentNo(String paymentNo) {
            this.paymentNo = paymentNo;
        }

        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }
    }
}
