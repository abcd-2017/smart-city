package com.example.pojo.trafficparam;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author kkk
 */
public class TrafficCarListParam extends BaseParam {

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
        return "TrafficCarList{" +
                "rows=" + rows +
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
         * plateNo
         */
        @SerializedName("plateNo")
        private String plateNo;
        /**
         * engineNo
         */
        @SerializedName("engineNo")
        private String engineNo;
        /**
         * type
         */
        @SerializedName("type")
        private String type;
        /**
         * userName
         */
        @SerializedName("userName")
        private Object userName;

        @Override
        public String toString() {
            return "RowsDTO{" +
                    "id=" + id +
                    ", userId=" + userId +
                    ", plateNo='" + plateNo + '\'' +
                    ", engineNo='" + engineNo + '\'' +
                    ", type='" + type + '\'' +
                    ", userName=" + userName +
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

        public String getPlateNo() {
            return plateNo;
        }

        public void setPlateNo(String plateNo) {
            this.plateNo = plateNo;
        }

        public String getEngineNo() {
            return engineNo;
        }

        public void setEngineNo(String engineNo) {
            this.engineNo = engineNo;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getUserName() {
            return userName;
        }

        public void setUserName(Object userName) {
            this.userName = userName;
        }
    }
}
