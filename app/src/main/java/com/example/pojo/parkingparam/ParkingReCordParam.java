package com.example.pojo.parkingparam;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author kkk
 */
public class ParkingReCordParam extends BaseParam {

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
        return "ParkingReCordParam{" +
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
         * lotId
         */
        @SerializedName("lotId")
        private Integer lotId;
        /**
         * entryTime
         */
        @SerializedName("entryTime")
        private String entryTime;
        /**
         * outTime
         */
        @SerializedName("outTime")
        private String outTime;
        /**
         * plateNumber
         */
        @SerializedName("plateNumber")
        private String plateNumber;
        /**
         * monetary
         */
        @SerializedName("monetary")
        private String monetary;
        /**
         * parkName
         */
        @SerializedName("parkName")
        private String parkName;
        /**
         * parkNo
         */
        @SerializedName("parkNo")
        private String parkNo;
        /**
         * address
         */
        @SerializedName("address")
        private String address;

        @Override
        public String toString() {
            return "RowsDTO{" +
                    "id=" + id +
                    ", lotId=" + lotId +
                    ", entryTime='" + entryTime + '\'' +
                    ", outTime='" + outTime + '\'' +
                    ", plateNumber='" + plateNumber + '\'' +
                    ", monetary='" + monetary + '\'' +
                    ", parkName='" + parkName + '\'' +
                    ", parkNo='" + parkNo + '\'' +
                    ", address='" + address + '\'' +
                    '}';
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getLotId() {
            return lotId;
        }

        public void setLotId(Integer lotId) {
            this.lotId = lotId;
        }

        public String getEntryTime() {
            return entryTime;
        }

        public void setEntryTime(String entryTime) {
            this.entryTime = entryTime;
        }

        public String getOutTime() {
            return outTime;
        }

        public void setOutTime(String outTime) {
            this.outTime = outTime;
        }

        public String getPlateNumber() {
            return plateNumber;
        }

        public void setPlateNumber(String plateNumber) {
            this.plateNumber = plateNumber;
        }

        public String getMonetary() {
            return monetary;
        }

        public void setMonetary(String monetary) {
            this.monetary = monetary;
        }

        public String getParkName() {
            return parkName;
        }

        public void setParkName(String parkName) {
            this.parkName = parkName;
        }

        public String getParkNo() {
            return parkNo;
        }

        public void setParkNo(String parkNo) {
            this.parkNo = parkNo;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
