package com.example.pojo.movieparam;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author kkk
 */
public class MovieTicketListParam extends BaseParam {

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

    @Override
    public String toString() {
        return "MovieTicketListParam{" +
                "rows=" + rows +
                ", total=" + total +
                '}';
    }

    public static class RowsDTO {
        /**
         * buyDate
         */
        @SerializedName("buyDate")
        private String buyDate;
        /**
         * codeX
         */
        @SerializedName("code")
        private String codeX;
        /**
         * id
         */
        @SerializedName("id")
        private Integer id;
        /**
         * movieId
         */
        @SerializedName("movieId")
        private Integer movieId;
        /**
         * orderItemId
         */
        @SerializedName("orderItemId")
        private Integer orderItemId;
        /**
         * roomId
         */
        @SerializedName("roomId")
        private Integer roomId;
        /**
         * seatId
         */
        @SerializedName("seatId")
        private Integer seatId;
        /**
         * status
         */
        @SerializedName("status")
        private String status;
        /**
         * theaterId
         */
        @SerializedName("theaterId")
        private Integer theaterId;
        /**
         * timesId
         */
        @SerializedName("timesId")
        private Integer timesId;
        /**
         * userId
         */
        @SerializedName("userId")
        private Integer userId;

        @Override
        public String toString() {
            return "RowsDTO{" +
                    "buyDate='" + buyDate + '\'' +
                    ", codeX='" + codeX + '\'' +
                    ", id=" + id +
                    ", movieId=" + movieId +
                    ", orderItemId=" + orderItemId +
                    ", roomId=" + roomId +
                    ", seatId=" + seatId +
                    ", status='" + status + '\'' +
                    ", theaterId=" + theaterId +
                    ", timesId=" + timesId +
                    ", userId=" + userId +
                    '}';
        }

        public String getBuyDate() {
            return buyDate;
        }

        public void setBuyDate(String buyDate) {
            this.buyDate = buyDate;
        }

        public String getCodeX() {
            return codeX;
        }

        public void setCodeX(String codeX) {
            this.codeX = codeX;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getMovieId() {
            return movieId;
        }

        public void setMovieId(Integer movieId) {
            this.movieId = movieId;
        }

        public Integer getOrderItemId() {
            return orderItemId;
        }

        public void setOrderItemId(Integer orderItemId) {
            this.orderItemId = orderItemId;
        }

        public Integer getRoomId() {
            return roomId;
        }

        public void setRoomId(Integer roomId) {
            this.roomId = roomId;
        }

        public Integer getSeatId() {
            return seatId;
        }

        public void setSeatId(Integer seatId) {
            this.seatId = seatId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Integer getTheaterId() {
            return theaterId;
        }

        public void setTheaterId(Integer theaterId) {
            this.theaterId = theaterId;
        }

        public Integer getTimesId() {
            return timesId;
        }

        public void setTimesId(Integer timesId) {
            this.timesId = timesId;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }
    }
}
