package com.example.pojo.movieparam;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author kkk
 */
public class MovieTheatreRoomListParam extends BaseParam {

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
        return "MovieTheatreRoomListParam{" +
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
         * theaterId
         */
        @SerializedName("theaterId")
        private Integer theaterId;
        /**
         * roomId
         */
        @SerializedName("roomId")
        private Integer roomId;
        /**
         * movieId
         */
        @SerializedName("movieId")
        private Integer movieId;
        /**
         * startTime
         */
        @SerializedName("startTime")
        private String startTime;
        /**
         * endTime
         */
        @SerializedName("endTime")
        private String endTime;
        /**
         * price
         */
        @SerializedName("price")
        private Integer price;
        /**
         * playType
         */
        @SerializedName("playType")
        private String playType;
        /**
         * playDate
         */
        @SerializedName("playDate")
        private String playDate;
        /**
         * saleNum
         */
        @SerializedName("saleNum")
        private Integer saleNum;
        /**
         * totalNum
         */
        @SerializedName("totalNum")
        private Integer totalNum;
        /**
         * status
         */
        @SerializedName("status")
        private String status;
        /**
         * theatreName
         */
        @SerializedName("theatreName")
        private String theatreName;
        /**
         * roomName
         */
        @SerializedName("roomName")
        private String roomName;
        /**
         * movieName
         */
        @SerializedName("movieName")
        private String movieName;

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
                    ", theaterId=" + theaterId +
                    ", roomId=" + roomId +
                    ", movieId=" + movieId +
                    ", startTime='" + startTime + '\'' +
                    ", endTime='" + endTime + '\'' +
                    ", price=" + price +
                    ", playType='" + playType + '\'' +
                    ", playDate='" + playDate + '\'' +
                    ", saleNum=" + saleNum +
                    ", totalNum=" + totalNum +
                    ", status='" + status + '\'' +
                    ", theatreName='" + theatreName + '\'' +
                    ", roomName='" + roomName + '\'' +
                    ", movieName='" + movieName + '\'' +
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

        public Integer getTheaterId() {
            return theaterId;
        }

        public void setTheaterId(Integer theaterId) {
            this.theaterId = theaterId;
        }

        public Integer getRoomId() {
            return roomId;
        }

        public void setRoomId(Integer roomId) {
            this.roomId = roomId;
        }

        public Integer getMovieId() {
            return movieId;
        }

        public void setMovieId(Integer movieId) {
            this.movieId = movieId;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public String getPlayType() {
            return playType;
        }

        public void setPlayType(String playType) {
            this.playType = playType;
        }

        public String getPlayDate() {
            return playDate;
        }

        public void setPlayDate(String playDate) {
            this.playDate = playDate;
        }

        public Integer getSaleNum() {
            return saleNum;
        }

        public void setSaleNum(Integer saleNum) {
            this.saleNum = saleNum;
        }

        public Integer getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(Integer totalNum) {
            this.totalNum = totalNum;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTheatreName() {
            return theatreName;
        }

        public void setTheatreName(String theatreName) {
            this.theatreName = theatreName;
        }

        public String getRoomName() {
            return roomName;
        }

        public void setRoomName(String roomName) {
            this.roomName = roomName;
        }

        public String getMovieName() {
            return movieName;
        }

        public void setMovieName(String movieName) {
            this.movieName = movieName;
        }

        public static class ParamsDTO {
        }
    }
}
