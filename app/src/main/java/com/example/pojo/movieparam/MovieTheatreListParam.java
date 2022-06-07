package com.example.pojo.movieparam;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author kkk
 */
public class MovieTheatreListParam extends BaseParam {

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
        return "MovieTheatreListParam{" +
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
         * cover
         */
        @SerializedName("cover")
        private String cover;
        /**
         * province
         */
        @SerializedName("province")
        private String province;
        /**
         * city
         */
        @SerializedName("city")
        private String city;
        /**
         * area
         */
        @SerializedName("area")
        private String area;
        /**
         * address
         */
        @SerializedName("address")
        private String address;
        /**
         * score
         */
        @SerializedName("score")
        private Integer score;
        /**
         * tags
         */
        @SerializedName("tags")
        private Object tags;
        /**
         * brand
         */
        @SerializedName("brand")
        private Object brand;
        /**
         * distance
         */
        @SerializedName("distance")
        private String distance;
        /**
         * status
         */
        @SerializedName("status")
        private String status;
        /**
         * movieId
         */
        @SerializedName("movieId")
        private Object movieId;
        /**
         * timesId
         */
        @SerializedName("timesId")
        private Object timesId;

        @Override
        public String toString() {
            return "RowsDTO{" +
                    "searchValue=" + searchValue +
                    ", createBy='" + createBy + '\'' +
                    ", createTime='" + createTime + '\'' +
                    ", updateBy='" + updateBy + '\'' +
                    ", updateTime='" + updateTime + '\'' +
                    ", remark=" + remark +
                    ", params=" + params +
                    ", id=" + id +
                    ", name='" + name + '\'' +
                    ", cover='" + cover + '\'' +
                    ", province='" + province + '\'' +
                    ", city='" + city + '\'' +
                    ", area='" + area + '\'' +
                    ", address='" + address + '\'' +
                    ", score=" + score +
                    ", tags=" + tags +
                    ", brand=" + brand +
                    ", distance='" + distance + '\'' +
                    ", status='" + status + '\'' +
                    ", movieId=" + movieId +
                    ", timesId=" + timesId +
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

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Integer getScore() {
            return score;
        }

        public void setScore(Integer score) {
            this.score = score;
        }

        public Object getTags() {
            return tags;
        }

        public void setTags(Object tags) {
            this.tags = tags;
        }

        public Object getBrand() {
            return brand;
        }

        public void setBrand(Object brand) {
            this.brand = brand;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getMovieId() {
            return movieId;
        }

        public void setMovieId(Object movieId) {
            this.movieId = movieId;
        }

        public Object getTimesId() {
            return timesId;
        }

        public void setTimesId(Object timesId) {
            this.timesId = timesId;
        }

        public static class ParamsDTO {
        }
    }
}
