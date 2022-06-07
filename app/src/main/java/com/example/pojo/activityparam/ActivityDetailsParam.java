package com.example.pojo.activityparam;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

/**
 * @author kkk
 */
public class ActivityDetailsParam extends BaseParam {

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
        return "ActivityDetailsParam{" +
                ", data=" + data +
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
        private String createBy;
        /**
         * createTime
         */
        @SerializedName("createTime")
        private String createTime;
        /**
         * updateBy
         */
        @SerializedName("updateBy")
        private String updateBy;
        /**
         * updateTime
         */
        @SerializedName("updateTime")
        private String updateTime;
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
         * name
         */
        @SerializedName("name")
        private String name;
        /**
         * content
         */
        @SerializedName("content")
        private String content;
        /**
         * imgUrl
         */
        @SerializedName("imgUrl")
        private String imgUrl;
        /**
         * categoryId
         */
        @SerializedName("categoryId")
        private Integer categoryId;
        /**
         * recommend
         */
        @SerializedName("recommend")
        private String recommend;
        /**
         * signupNum
         */
        @SerializedName("signupNum")
        private Integer signupNum;
        /**
         * likeNum
         */
        @SerializedName("likeNum")
        private Integer likeNum;
        /**
         * status
         */
        @SerializedName("status")
        private String status;
        /**
         * publishTime
         */
        @SerializedName("publishTime")
        private String publishTime;
        /**
         * categoryName
         */
        @SerializedName("categoryName")
        private String categoryName;

        @Override
        public String toString() {
            return "DataDTO{" +
                    "searchValue=" + searchValue +
                    ", createBy='" + createBy + '\'' +
                    ", createTime='" + createTime + '\'' +
                    ", updateBy='" + updateBy + '\'' +
                    ", updateTime='" + updateTime + '\'' +
                    ", remark=" + remark +
                    ", params=" + params +
                    ", id=" + id +
                    ", name='" + name + '\'' +
                    ", content='" + content + '\'' +
                    ", imgUrl='" + imgUrl + '\'' +
                    ", categoryId=" + categoryId +
                    ", recommend='" + recommend + '\'' +
                    ", signupNum=" + signupNum +
                    ", likeNum=" + likeNum +
                    ", status='" + status + '\'' +
                    ", publishTime='" + publishTime + '\'' +
                    ", categoryName='" + categoryName + '\'' +
                    '}';
        }

        public Object getSearchValue() {
            return searchValue;
        }

        public void setSearchValue(Object searchValue) {
            this.searchValue = searchValue;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public String getRecommend() {
            return recommend;
        }

        public void setRecommend(String recommend) {
            this.recommend = recommend;
        }

        public Integer getSignupNum() {
            return signupNum;
        }

        public void setSignupNum(Integer signupNum) {
            this.signupNum = signupNum;
        }

        public Integer getLikeNum() {
            return likeNum;
        }

        public void setLikeNum(Integer likeNum) {
            this.likeNum = likeNum;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public static class ParamsDTO {
        }
    }
}
