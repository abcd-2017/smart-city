package com.example.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * @author kkk
 */
public class PressDetailsParam extends BaseParam {

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
        return "PressDetailsParam{" +
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
         * appType
         */
        @SerializedName("appType")
        private String appType;
        /**
         * cover
         */
        @SerializedName("cover")
        private String cover;
        /**
         * title
         */
        @SerializedName("title")
        private String title;
        /**
         * subTitle
         */
        @SerializedName("subTitle")
        private Object subTitle;
        /**
         * content
         */
        @SerializedName("content")
        private String content;
        /**
         * status
         */
        @SerializedName("status")
        private String status;
        /**
         * publishDate
         */
        @SerializedName("publishDate")
        private String publishDate;
        /**
         * tags
         */
        @SerializedName("tags")
        private Object tags;
        /**
         * commentNum
         */
        @SerializedName("commentNum")
        private Integer commentNum;
        /**
         * likeNum
         */
        @SerializedName("likeNum")
        private Integer likeNum;
        /**
         * readNum
         */
        @SerializedName("readNum")
        private Integer readNum;
        /**
         * type
         */
        @SerializedName("type")
        private String type;
        /**
         * top
         */
        @SerializedName("top")
        private String top;
        /**
         * hot
         */
        @SerializedName("hot")
        private String hot;

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
                    ", appType='" + appType + '\'' +
                    ", cover='" + cover + '\'' +
                    ", title='" + title + '\'' +
                    ", subTitle=" + subTitle +
                    ", content='" + content + '\'' +
                    ", status='" + status + '\'' +
                    ", publishDate='" + publishDate + '\'' +
                    ", tags=" + tags +
                    ", commentNum=" + commentNum +
                    ", likeNum=" + likeNum +
                    ", readNum=" + readNum +
                    ", type='" + type + '\'' +
                    ", top='" + top + '\'' +
                    ", hot='" + hot + '\'' +
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

        public String getAppType() {
            return appType;
        }

        public void setAppType(String appType) {
            this.appType = appType;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Object getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(Object subTitle) {
            this.subTitle = subTitle;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPublishDate() {
            return publishDate;
        }

        public void setPublishDate(String publishDate) {
            this.publishDate = publishDate;
        }

        public Object getTags() {
            return tags;
        }

        public void setTags(Object tags) {
            this.tags = tags;
        }

        public Integer getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(Integer commentNum) {
            this.commentNum = commentNum;
        }

        public Integer getLikeNum() {
            return likeNum;
        }

        public void setLikeNum(Integer likeNum) {
            this.likeNum = likeNum;
        }

        public Integer getReadNum() {
            return readNum;
        }

        public void setReadNum(Integer readNum) {
            this.readNum = readNum;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTop() {
            return top;
        }

        public void setTop(String top) {
            this.top = top;
        }

        public String getHot() {
            return hot;
        }

        public void setHot(String hot) {
            this.hot = hot;
        }

        public static class ParamsDTO {
        }
    }
}
