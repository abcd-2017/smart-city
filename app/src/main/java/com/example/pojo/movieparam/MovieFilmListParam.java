package com.example.pojo.movieparam;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author kkk
 */
public class MovieFilmListParam extends BaseParam {

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

    @Override
    public String toString() {
        return "MovieFilmListParam{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
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
         * name
         */
        @SerializedName("name")
        private String name;
        /**
         * category
         */
        @SerializedName("category")
        private String category;
        /**
         * cover
         */
        @SerializedName("cover")
        private String cover;
        /**
         * playType
         */
        @SerializedName("playType")
        private String playType;
        /**
         * score
         */
        @SerializedName("score")
        private Integer score;
        /**
         * duration
         */
        @SerializedName("duration")
        private Integer duration;
        /**
         * playDate
         */
        @SerializedName("playDate")
        private String playDate;
        /**
         * likeNum
         */
        @SerializedName("likeNum")
        private Integer likeNum;
        /**
         * favoriteNum
         */
        @SerializedName("favoriteNum")
        private Integer favoriteNum;
        /**
         * language
         */
        @SerializedName("language")
        private String language;
        /**
         * introduction
         */
        @SerializedName("introduction")
        private String introduction;
        /**
         * other
         */
        @SerializedName("other")
        private Object other;
        /**
         * recommend
         */
        @SerializedName("recommend")
        private String recommend;

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
                    ", name='" + name + '\'' +
                    ", category='" + category + '\'' +
                    ", cover='" + cover + '\'' +
                    ", playType='" + playType + '\'' +
                    ", score=" + score +
                    ", duration=" + duration +
                    ", playDate='" + playDate + '\'' +
                    ", likeNum=" + likeNum +
                    ", favoriteNum=" + favoriteNum +
                    ", language='" + language + '\'' +
                    ", introduction='" + introduction + '\'' +
                    ", other=" + other +
                    ", recommend='" + recommend + '\'' +
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getPlayType() {
            return playType;
        }

        public void setPlayType(String playType) {
            this.playType = playType;
        }

        public Integer getScore() {
            return score;
        }

        public void setScore(Integer score) {
            this.score = score;
        }

        public Integer getDuration() {
            return duration;
        }

        public void setDuration(Integer duration) {
            this.duration = duration;
        }

        public String getPlayDate() {
            return playDate;
        }

        public void setPlayDate(String playDate) {
            this.playDate = playDate;
        }

        public Integer getLikeNum() {
            return likeNum;
        }

        public void setLikeNum(Integer likeNum) {
            this.likeNum = likeNum;
        }

        public Integer getFavoriteNum() {
            return favoriteNum;
        }

        public void setFavoriteNum(Integer favoriteNum) {
            this.favoriteNum = favoriteNum;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public Object getOther() {
            return other;
        }

        public void setOther(Object other) {
            this.other = other;
        }

        public String getRecommend() {
            return recommend;
        }

        public void setRecommend(String recommend) {
            this.recommend = recommend;
        }

        public static class ParamsDTO {
        }
    }
}
