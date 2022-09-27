package com.example.pojo.trafficparam;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author kkk
 */
public class TrafficCheckApplyParam extends BaseParam {


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
        return "TrafficCheckApplyParam{" +
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
         * searchValue
         */
        @SerializedName("searchValue")
        private Object searchValue;
        /**
         * createBy
         */
        @SerializedName("createBy")
        private Object createBy;
        /**
         * createTime
         */
        @SerializedName("createTime")
        private Object createTime;
        /**
         * updateBy
         */
        @SerializedName("updateBy")
        private Object updateBy;
        /**
         * updateTime
         */
        @SerializedName("updateTime")
        private Object updateTime;
        /**
         * remark
         */
        @SerializedName("remark")
        private Object remark;
        /**
         * params
         */
        @SerializedName("params")
        private ParamsDTO params;
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
         * carId
         */
        @SerializedName("carId")
        private Object carId;
        /**
         * aptTime
         */
        @SerializedName("aptTime")
        private Object aptTime;
        /**
         * addressId
         */
        @SerializedName("addressId")
        private Integer addressId;
        /**
         * success
         */
        @SerializedName("success")
        private String success;
        /**
         * placeName
         */
        @SerializedName("placeName")
        private String placeName;
        /**
         * userName
         */
        @SerializedName("userName")
        private String userName;
        /**
         * plateNo
         */
        @SerializedName("plateNo")
        private Object plateNo;
        /**
         * engineNo
         */
        @SerializedName("engineNo")
        private Object engineNo;

        public Object getSearchValue() {
            return searchValue;
        }

        @Override
        public String toString() {
            return "RowsDTO{" +
                    "searchValue=" + searchValue +
                    ", createBy=" + createBy +
                    ", createTime=" + createTime +
                    ", updateBy=" + updateBy +
                    ", updateTime=" + updateTime +
                    ", remark=" + remark +
                    ", params=" + params +
                    ", id=" + id +
                    ", userId=" + userId +
                    ", carId=" + carId +
                    ", aptTime=" + aptTime +
                    ", addressId=" + addressId +
                    ", success='" + success + '\'' +
                    ", placeName='" + placeName + '\'' +
                    ", userName='" + userName + '\'' +
                    ", plateNo=" + plateNo +
                    ", engineNo=" + engineNo +
                    '}';
        }

        public void setSearchValue(Object searchValue) {
            this.searchValue = searchValue;
        }

        public Object getCreateBy() {
            return createBy;
        }

        public void setCreateBy(Object createBy) {
            this.createBy = createBy;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(Object updateBy) {
            this.updateBy = updateBy;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public ParamsDTO getParams() {
            return params;
        }

        public void setParams(ParamsDTO params) {
            this.params = params;
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

        public Object getCarId() {
            return carId;
        }

        public void setCarId(Object carId) {
            this.carId = carId;
        }

        public Object getAptTime() {
            return aptTime;
        }

        public void setAptTime(Object aptTime) {
            this.aptTime = aptTime;
        }

        public Integer getAddressId() {
            return addressId;
        }

        public void setAddressId(Integer addressId) {
            this.addressId = addressId;
        }

        public String getSuccess() {
            return success;
        }

        public void setSuccess(String success) {
            this.success = success;
        }

        public String getPlaceName() {
            return placeName;
        }

        public void setPlaceName(String placeName) {
            this.placeName = placeName;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public Object getPlateNo() {
            return plateNo;
        }

        public void setPlateNo(Object plateNo) {
            this.plateNo = plateNo;
        }

        public Object getEngineNo() {
            return engineNo;
        }

        public void setEngineNo(Object engineNo) {
            this.engineNo = engineNo;
        }

        public static class ParamsDTO {
        }
    }
}
