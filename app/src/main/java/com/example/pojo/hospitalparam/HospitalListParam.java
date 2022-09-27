package com.example.pojo.hospitalparam;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author kkk
 */
public class HospitalListParam extends BaseParam {

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
        return "HospitalListAdapter{" +
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
         * hospitalName
         */
        @SerializedName("hospitalName")
        private String hospitalName;
        /**
         * brief
         */
        @SerializedName("brief")
        private String brief;
        /**
         * level
         */
        @SerializedName("level")
        private Integer level;
        /**
         * imgUrl
         */
        @SerializedName("imgUrl")
        private String imgUrl;

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
                    ", hospitalName='" + hospitalName + '\'' +
                    ", brief='" + brief + '\'' +
                    ", level=" + level +
                    ", imgUrl='" + imgUrl + '\'' +
                    '}';
        }

        public Object getSearchValue() {
            return searchValue;
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

        public String getHospitalName() {
            return hospitalName;
        }

        public void setHospitalName(String hospitalName) {
            this.hospitalName = hospitalName;
        }

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public Integer getLevel() {
            return level;
        }

        public void setLevel(Integer level) {
            this.level = level;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public static class ParamsDTO {
        }
    }
}
