package com.example.pojo.movieparam;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author kkk
 */
public class MovieOrderListParam extends BaseParam {

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
        return "MovieOrderListParam{" +
                "rows=" + rows +
                ", total=" + total +
                '}';
    }

    public static class RowsDTO {
        /**
         * id
         */
        @SerializedName("id")
        private Integer id;
        /**
         * orderNo
         */
        @SerializedName("orderNo")
        private String orderNo;
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
         * seatId
         */
        @SerializedName("seatId")
        private Object seatId;
        /**
         * timesId
         */
        @SerializedName("timesId")
        private Integer timesId;
        /**
         * movieId
         */
        @SerializedName("movieId")
        private Integer movieId;
        /**
         * price
         */
        @SerializedName("price")
        private Integer price;
        /**
         * status
         */
        @SerializedName("status")
        private String status;
        /**
         * userId
         */
        @SerializedName("userId")
        private Integer userId;
        /**
         * paymentType
         */
        @SerializedName("paymentType")
        private Object paymentType;
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
         * seatRow
         */
        @SerializedName("seatRow")
        private Object seatRow;
        /**
         * seatCol
         */
        @SerializedName("seatCol")
        private Object seatCol;
        /**
         * movieName
         */
        @SerializedName("movieName")
        private String movieName;
        /**
         * userName
         */
        @SerializedName("userName")
        private Object userName;
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
         * playDate
         */
        @SerializedName("playDate")
        private String playDate;
        /**
         * payTime
         */
        @SerializedName("payTime")
        private String payTime;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "RowsDTO{" +
                    "id=" + id +
                    ", orderNo='" + orderNo + '\'' +
                    ", theaterId=" + theaterId +
                    ", roomId=" + roomId +
                    ", seatId=" + seatId +
                    ", timesId=" + timesId +
                    ", movieId=" + movieId +
                    ", price=" + price +
                    ", status='" + status + '\'' +
                    ", userId=" + userId +
                    ", paymentType=" + paymentType +
                    ", theatreName='" + theatreName + '\'' +
                    ", roomName='" + roomName + '\'' +
                    ", seatRow=" + seatRow +
                    ", seatCol=" + seatCol +
                    ", movieName='" + movieName + '\'' +
                    ", userName=" + userName +
                    ", startTime='" + startTime + '\'' +
                    ", endTime='" + endTime + '\'' +
                    ", playDate='" + playDate + '\'' +
                    ", payTime='" + payTime + '\'' +
                    '}';
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
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

        public Object getSeatId() {
            return seatId;
        }

        public void setSeatId(Object seatId) {
            this.seatId = seatId;
        }

        public Integer getTimesId() {
            return timesId;
        }

        public void setTimesId(Integer timesId) {
            this.timesId = timesId;
        }

        public Integer getMovieId() {
            return movieId;
        }

        public void setMovieId(Integer movieId) {
            this.movieId = movieId;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Object getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(Object paymentType) {
            this.paymentType = paymentType;
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

        public Object getSeatRow() {
            return seatRow;
        }

        public void setSeatRow(Object seatRow) {
            this.seatRow = seatRow;
        }

        public Object getSeatCol() {
            return seatCol;
        }

        public void setSeatCol(Object seatCol) {
            this.seatCol = seatCol;
        }

        public String getMovieName() {
            return movieName;
        }

        public void setMovieName(String movieName) {
            this.movieName = movieName;
        }

        public Object getUserName() {
            return userName;
        }

        public void setUserName(Object userName) {
            this.userName = userName;
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

        public String getPlayDate() {
            return playDate;
        }

        public void setPlayDate(String playDate) {
            this.playDate = playDate;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }
    }
}
