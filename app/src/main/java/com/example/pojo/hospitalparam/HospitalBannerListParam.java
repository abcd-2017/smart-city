package com.example.pojo.hospitalparam;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author kkk
 */
public class HospitalBannerListParam extends BaseParam {

    /**
     * data
     */
    @SerializedName("data")
    private List<DataDTO> data;


    @Override
    public String toString() {
        return "HospitalBannerListParam{" +
                ", data=" + data +
                '}';
    }

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public static class DataDTO {
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
         * imgUrl
         */
        @SerializedName("imgUrl")
        private String imgUrl;
        /**
         * hospitalId
         */
        @SerializedName("hospitalId")
        private Integer hospitalId;

        public Object getSearchValue() {
            return searchValue;
        }

        @Override
        public String toString() {
            return "DataDTO{" +
                    "searchValue=" + searchValue +
                    ", createBy=" + createBy +
                    ", createTime=" + createTime +
                    ", updateBy=" + updateBy +
                    ", updateTime=" + updateTime +
                    ", remark=" + remark +
                    ", params=" + params +
                    ", id=" + id +
                    ", imgUrl='" + imgUrl + '\'' +
                    ", hospitalId=" + hospitalId +
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

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public Integer getHospitalId() {
            return hospitalId;
        }

        public void setHospitalId(Integer hospitalId) {
            this.hospitalId = hospitalId;
        }

        public static class ParamsDTO {
        }
    }
}
