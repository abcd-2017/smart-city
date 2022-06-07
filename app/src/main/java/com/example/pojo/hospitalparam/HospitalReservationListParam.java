package com.example.pojo.hospitalparam;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author kkk
 */
public class HospitalReservationListParam extends BaseParam {

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

    @Override
    public String toString() {
        return "HospitalReservationListParam{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }

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

    public static class RowsDTO {
        /**
         * id
         */
        @SerializedName("id")
        private Integer id;
        /**
         * orderNo
         */
        @SerializedName("orderNo")
        private String orderNo;
        /**
         * patientName
         */
        @SerializedName("patientName")
        private String patientName;
        /**
         * categoryId
         */
        @SerializedName("categoryId")
        private Integer categoryId;
        /**
         * type
         */
        @SerializedName("type")
        private String type;
        /**
         * money
         */
        @SerializedName("money")
        private Integer money;
        /**
         * reserveTime
         */
        @SerializedName("reserveTime")
        private String reserveTime;
        /**
         * status
         */
        @SerializedName("status")
        private String status;
        /**
         * categoryName
         */
        @SerializedName("categoryName")
        private String categoryName;
        /**
         * userId
         */
        @SerializedName("userId")
        private Integer userId;

        public Integer getId() {
            return id;
        }

        @Override
        public String toString() {
            return "RowsDTO{" +
                    "id=" + id +
                    ", orderNo='" + orderNo + '\'' +
                    ", patientName='" + patientName + '\'' +
                    ", categoryId=" + categoryId +
                    ", type='" + type + '\'' +
                    ", money=" + money +
                    ", reserveTime='" + reserveTime + '\'' +
                    ", status='" + status + '\'' +
                    ", categoryName='" + categoryName + '\'' +
                    ", userId=" + userId +
                    '}';
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getPatientName() {
            return patientName;
        }

        public void setPatientName(String patientName) {
            this.patientName = patientName;
        }

        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Integer getMoney() {
            return money;
        }

        public void setMoney(Integer money) {
            this.money = money;
        }

        public String getReserveTime() {
            return reserveTime;
        }

        public void setReserveTime(String reserveTime) {
            this.reserveTime = reserveTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }
    }
}
