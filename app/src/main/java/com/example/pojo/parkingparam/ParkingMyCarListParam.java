package com.example.pojo.parkingparam;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author kkk
 */
public class ParkingMyCarListParam extends BaseParam {

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
        return "ParkingMyCarListParam{" +
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
         * type
         */
        @SerializedName("type")
        private String type;
        /**
         * userId
         */
        @SerializedName("userId")
        private Integer userId;
        /**
         * userName
         */
        @SerializedName("userName")
        private String userName;

        @Override
        public String toString() {
            return "RowsDTO{" +
                    "id=" + id +
                    ", plateNo='" + plateNo + '\'' +
                    ", type='" + type + '\'' +
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
