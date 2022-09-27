package com.example.pojo.parkingparam;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author kkk
 */
public class ParkingCarConsumptionParam extends BaseParam {

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

    @Override
    public String toString() {
        return "ParkingCarConsumptionParam{" +
                ", rows=" + rows +
                ", total='" + total + '\'' +
                '}';
    }

    public static class RowsDTO {
        /**
         * id
         */
        @SerializedName("id")
        private Integer id;
        /**
         * plateNo
         */
        @SerializedName("plateNo")
        private String plateNo;
        /**
         * travelDate
         */
        @SerializedName("travelDate")
        private String travelDate;
        /**
         * travelDistance
         */
        @SerializedName("travelDistance")
        private Integer travelDistance;
        /**
         * gasFilling
         */
        @SerializedName("gasFilling")
        private Integer gasFilling;
        /**
         * amount
         */
        @SerializedName("amount")
        private Integer amount;
        /**
         * userId
         */
        @SerializedName("userId")
        private Integer userId;
        /**
         * userName
         */
        @SerializedName("userName")
        private Object userName;

        @Override
        public String toString() {
            return "RowsDTO{" +
                    "id=" + id +
                    ", plateNo='" + plateNo + '\'' +
                    ", travelDate='" + travelDate + '\'' +
                    ", travelDistance=" + travelDistance +
                    ", gasFilling=" + gasFilling +
                    ", amount=" + amount +
                    ", userId=" + userId +
                    ", userName=" + userName +
                    '}';
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getPlateNo() {
            return plateNo;
        }

        public void setPlateNo(String plateNo) {
            this.plateNo = plateNo;
        }

        public String getTravelDate() {
            return travelDate;
        }

        public void setTravelDate(String travelDate) {
            this.travelDate = travelDate;
        }

        public Integer getTravelDistance() {
            return travelDistance;
        }

        public void setTravelDistance(Integer travelDistance) {
            this.travelDistance = travelDistance;
        }

        public Integer getGasFilling() {
            return gasFilling;
        }

        public void setGasFilling(Integer gasFilling) {
            this.gasFilling = gasFilling;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Object getUserName() {
            return userName;
        }

        public void setUserName(Object userName) {
            this.userName = userName;
        }
    }
}
