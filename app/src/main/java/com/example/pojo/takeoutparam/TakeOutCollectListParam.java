package com.example.pojo.takeoutparam;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author kkk
 */
public class TakeOutCollectListParam extends BaseParam {

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
        return "TakeOutCollectListParam{" +
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
         * sellerId
         */
        @SerializedName("sellerId")
        private Integer sellerId;
        /**
         * sellerName
         */
        @SerializedName("sellerName")
        private String sellerName;
        /**
         * address
         */
        @SerializedName("address")
        private String address;
        /**
         * imgUrl
         */
        @SerializedName("imgUrl")
        private String imgUrl;
        /**
         * score
         */
        @SerializedName("score")
        private Double score;
        /**
         * saleQuantity
         */
        @SerializedName("saleQuantity")
        private Integer saleQuantity;
        /**
         * userName
         */
        @SerializedName("userName")
        private String userName;
        /**
         * nickName
         */
        @SerializedName("nickName")
        private String nickName;

        @Override
        public String toString() {
            return "RowsDTO{" +
                    "id=" + id +
                    ", userId=" + userId +
                    ", sellerId=" + sellerId +
                    ", sellerName='" + sellerName + '\'' +
                    ", address='" + address + '\'' +
                    ", imgUrl='" + imgUrl + '\'' +
                    ", score=" + score +
                    ", saleQuantity=" + saleQuantity +
                    ", userName='" + userName + '\'' +
                    ", nickName='" + nickName + '\'' +
                    '}';
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

        public Integer getSellerId() {
            return sellerId;
        }

        public void setSellerId(Integer sellerId) {
            this.sellerId = sellerId;
        }

        public String getSellerName() {
            return sellerName;
        }

        public void setSellerName(String sellerName) {
            this.sellerName = sellerName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public Double getScore() {
            return score;
        }

        public void setScore(Double score) {
            this.score = score;
        }

        public Integer getSaleQuantity() {
            return saleQuantity;
        }

        public void setSaleQuantity(Integer saleQuantity) {
            this.saleQuantity = saleQuantity;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }
    }
}
