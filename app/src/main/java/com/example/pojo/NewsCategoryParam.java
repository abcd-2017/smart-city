package com.example.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author kkk
 */
public class NewsCategoryParam extends BaseParam {

    /**
     * data
     */
    @SerializedName("data")
    private List<DateDTO> data;

    public static class DateDTO {
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
         * appType
         */
        @SerializedName("appType")
        private String appType;
        /**
         * name
         */
        @SerializedName("name")
        private String name;
        /**
         * sort
         */
        @SerializedName("sort")
        private Integer sort;
        /**
         * status
         */
        @SerializedName("status")
        private String status;
        /**
         * parentId
         */
        @SerializedName("parentId")
        private Object parentId;

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

        public String getAppType() {
            return appType;
        }

        public void setAppType(String appType) {
            this.appType = appType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getSort() {
            return sort;
        }

        public void setSort(Integer sort) {
            this.sort = sort;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getParentId() {
            return parentId;
        }

        public void setParentId(Object parentId) {
            this.parentId = parentId;
        }

        public static class ParamsDTO {
        }

        @Override
        public String toString() {
            return "DateDTO{" +
                    "searchValue=" + searchValue +
                    ", createBy=" + createBy +
                    ", createTime=" + createTime +
                    ", updateBy=" + updateBy +
                    ", updateTime=" + updateTime +
                    ", remark=" + remark +
                    ", params=" + params +
                    ", id=" + id +
                    ", appType='" + appType + '\'' +
                    ", name='" + name + '\'' +
                    ", sort=" + sort +
                    ", status='" + status + '\'' +
                    ", parentId=" + parentId +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "NewsCategoryParam{" +
                ", data=" + data +
                '}';
    }

    public List<DateDTO> getData() {
        return data;
    }

    public void setData(List<DateDTO> data) {
        this.data = data;
    }
}
