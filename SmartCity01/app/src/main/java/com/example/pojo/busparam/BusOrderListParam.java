package com.example.pojo.busparam;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author kkk
 */
public class BusOrderListParam extends BaseParam {

    /**
     * total
     */
    @SerializedName("total")
    private Integer total;
    /**
     * rows
     */
    @SerializedName("rows")
    private List<RowsDTO> rows;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<RowsDTO> getRows() {
        return rows;
    }

    public void setRows(List<RowsDTO> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "BusOrderListParam{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }

    public static class RowsDTO {
        /**
         * id
         */
        @SerializedName("id")
        private Integer id;
        /**
         * orderNum
         */
        @SerializedName("orderNum")
        private String orderNum;
        /**
         * path
         */
        @SerializedName("path")
        private String path;
        /**
         * start
         */
        @SerializedName("start")
        private String start;
        /**
         * end
         */
        @SerializedName("end")
        private String end;
        /**
         * price
         */
        @SerializedName("price")
        private Integer price;
        /**
         * userName
         */
        @SerializedName("userName")
        private String userName;
        /**
         * userTel
         */
        @SerializedName("userTel")
        private String userTel;
        /**
         * userId
         */
        @SerializedName("userId")
        private Integer userId;
        /**
         * status
         */
        @SerializedName("status")
        private Integer status;
        /**
         * paymentType
         */
        @SerializedName("paymentType")
        private String paymentType;
        /**
         * payTime
         */
        @SerializedName("payTime")
        private String payTime;

        public Integer getId() {
            return id;
        }

        @Override
        public String toString() {
            return "RowsDTO{" +
                    "id=" + id +
                    ", orderNum='" + orderNum + '\'' +
                    ", path='" + path + '\'' +
                    ", start='" + start + '\'' +
                    ", end='" + end + '\'' +
                    ", price=" + price +
                    ", userName='" + userName + '\'' +
                    ", userTel='" + userTel + '\'' +
                    ", userId=" + userId +
                    ", status=" + status +
                    ", paymentType='" + paymentType + '\'' +
                    ", payTime='" + payTime + '\'' +
                    '}';
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserTel() {
            return userTel;
        }

        public void setUserTel(String userTel) {
            this.userTel = userTel;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(String paymentType) {
            this.paymentType = paymentType;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }
    }
}
