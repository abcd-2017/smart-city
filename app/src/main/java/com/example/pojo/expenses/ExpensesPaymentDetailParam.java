package com.example.pojo.expenses;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

/**
 * @author kkk
 */
public class ExpensesPaymentDetailParam extends BaseParam {

    /**
     * data
     */
    @SerializedName("data")
    private DataDTO data;

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ExpensesPaymentDetailParam{" +
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
         * billNo
         */
        @SerializedName("billNo")
        private String billNo;
        /**
         * title
         */
        @SerializedName("title")
        private String title;
        /**
         * amount
         */
        @SerializedName("amount")
        private Integer amount;
        /**
         * chargeUnit
         */
        @SerializedName("chargeUnit")
        private String chargeUnit;
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
         * payStatus
         */
        @SerializedName("payStatus")
        private String payStatus;

        @Override
        public String toString() {
            return "DataDTO{" +
                    "id=" + id +
                    ", billNo='" + billNo + '\'' +
                    ", title='" + title + '\'' +
                    ", amount=" + amount +
                    ", chargeUnit='" + chargeUnit + '\'' +
                    ", address='" + address + '\'' +
                    ", paymentNo='" + paymentNo + '\'' +
                    ", categoryId=" + categoryId +
                    ", payStatus='" + payStatus + '\'' +
                    '}';
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getBillNo() {
            return billNo;
        }

        public void setBillNo(String billNo) {
            this.billNo = billNo;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public String getChargeUnit() {
            return chargeUnit;
        }

        public void setChargeUnit(String chargeUnit) {
            this.chargeUnit = chargeUnit;
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

        public String getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(String payStatus) {
            this.payStatus = payStatus;
        }
    }
}
