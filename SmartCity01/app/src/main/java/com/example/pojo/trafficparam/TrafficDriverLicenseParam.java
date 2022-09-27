package com.example.pojo.trafficparam;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author kkk
 */
public class TrafficDriverLicenseParam extends BaseParam {

    /**
     * data
     */
    @SerializedName("data")
    private List<DataDTO> data;

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TrafficDriverLicenseParam{" +
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
         * userId
         */
        @SerializedName("userId")
        private Integer userId;
        /**
         * licenseNo
         */
        @SerializedName("licenseNo")
        private String licenseNo;
        /**
         * licenseLevel
         */
        @SerializedName("licenseLevel")
        private String licenseLevel;
        /**
         * idCard
         */
        @SerializedName("idCard")
        private String idCard;
        /**
         * score
         */
        @SerializedName("score")
        private Integer score;
        /**
         * applyDate
         */
        @SerializedName("applyDate")
        private String applyDate;
        /**
         * verifyDate
         */
        @SerializedName("verifyDate")
        private String verifyDate;
        /**
         * timeout
         */
        @SerializedName("timeout")
        private String timeout;
        /**
         * userName
         */
        @SerializedName("userName")
        private Object userName;
        /**
         * fileNo
         */
        @SerializedName("fileNo")
        private Object fileNo;
        /**
         * auditOffice
         */
        @SerializedName("auditOffice")
        private String auditOffice;
        /**
         * contact
         */
        @SerializedName("contact")
        private String contact;

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

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getLicenseNo() {
            return licenseNo;
        }

        public void setLicenseNo(String licenseNo) {
            this.licenseNo = licenseNo;
        }

        public String getLicenseLevel() {
            return licenseLevel;
        }

        public void setLicenseLevel(String licenseLevel) {
            this.licenseLevel = licenseLevel;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public Integer getScore() {
            return score;
        }

        public void setScore(Integer score) {
            this.score = score;
        }

        public String getApplyDate() {
            return applyDate;
        }

        public void setApplyDate(String applyDate) {
            this.applyDate = applyDate;
        }

        public String getVerifyDate() {
            return verifyDate;
        }

        public void setVerifyDate(String verifyDate) {
            this.verifyDate = verifyDate;
        }

        public String getTimeout() {
            return timeout;
        }

        public void setTimeout(String timeout) {
            this.timeout = timeout;
        }

        public Object getUserName() {
            return userName;
        }

        public void setUserName(Object userName) {
            this.userName = userName;
        }

        public Object getFileNo() {
            return fileNo;
        }

        public void setFileNo(Object fileNo) {
            this.fileNo = fileNo;
        }

        public String getAuditOffice() {
            return auditOffice;
        }

        public void setAuditOffice(String auditOffice) {
            this.auditOffice = auditOffice;
        }

        public String getContact() {
            return contact;
        }

        public void setContact(String contact) {
            this.contact = contact;
        }

        public static class ParamsDTO {
        }
    }
}
