package com.example.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author kkk
 */
public class PressListParam extends BaseParam  {

    /**
     * rows
     */
    @SerializedName("rows")
    private List<RowsDTO> rows;
    /**
     * total
     */
    @SerializedName("total")
    private Integer total;

    @Override
    public String toString() {
        return "PressListParam{}";
    }


    public List<RowsDTO> getRows() {
        return rows;
    }

    public void setRows(List<RowsDTO> rows) {
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public static class RowsDTO {
        /**
         * id
         */
        @SerializedName("id")
        private Integer id;
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
        private String subTitle;
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
            return "RowsDTO{" +
                    "id=" + id +
                    ", cover='" + cover + '\'' +
                    ", title='" + title + '\'' +
                    ", subTitle='" + subTitle + '\'' +
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

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
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

        public String getSubTitle() {
            return subTitle;
        }

        public void setSubTitle(String subTitle) {
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
    }
}
