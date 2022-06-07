package com.example.pojo.workparam;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

/**
 * @author kkk
 */
public class WorkDetailParam extends BaseParam {

    /**
     * data
     */
    @SerializedName("data")
    private DataDTO data;

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "WorkDetailParam{" +
                "data=" + data +
                '}';
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
         * companyId
         */
        @SerializedName("companyId")
        private Integer companyId;
        /**
         * professionId
         */
        @SerializedName("professionId")
        private Integer professionId;
        /**
         * contacts
         */
        @SerializedName("contacts")
        private String contacts;
        /**
         * name
         */
        @SerializedName("name")
        private String name;
        /**
         * obligation
         */
        @SerializedName("obligation")
        private String obligation;
        /**
         * address
         */
        @SerializedName("address")
        private String address;
        /**
         * need
         */
        @SerializedName("need")
        private String need;
        /**
         * salary
         */
        @SerializedName("salary")
        private String salary;
        /**
         * companyName
         */
        @SerializedName("companyName")
        private String companyName;
        /**
         * professionName
         */
        @SerializedName("professionName")
        private Object professionName;

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
                    ", companyId=" + companyId +
                    ", professionId=" + professionId +
                    ", contacts='" + contacts + '\'' +
                    ", name='" + name + '\'' +
                    ", obligation='" + obligation + '\'' +
                    ", address='" + address + '\'' +
                    ", need='" + need + '\'' +
                    ", salary='" + salary + '\'' +
                    ", companyName=" + companyName +
                    ", professionName=" + professionName +
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

        public Integer getCompanyId() {
            return companyId;
        }

        public void setCompanyId(Integer companyId) {
            this.companyId = companyId;
        }

        public Integer getProfessionId() {
            return professionId;
        }

        public void setProfessionId(Integer professionId) {
            this.professionId = professionId;
        }

        public String getContacts() {
            return contacts;
        }

        public void setContacts(String contacts) {
            this.contacts = contacts;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getObligation() {
            return obligation;
        }

        public void setObligation(String obligation) {
            this.obligation = obligation;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getNeed() {
            return need;
        }

        public void setNeed(String need) {
            this.need = need;
        }

        public String getSalary() {
            return salary;
        }

        public void setSalary(String salary) {
            this.salary = salary;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public Object getProfessionName() {
            return professionName;
        }

        public void setProfessionName(Object professionName) {
            this.professionName = professionName;
        }

        public static class ParamsDTO {
        }
    }
}
