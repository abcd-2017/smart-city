package com.example.pojo.takeoutparam;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

/**
 * @author kkk
 */
public class TakeOutProductListParam extends BaseParam {

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
        return "TakeOutProductListParam{" +
                "data=" + data +
                '}';
    }

    public static class DataDTO {
        /**
         * id
         */
        @SerializedName("id")
        private Integer id;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DataDTO dataDTO = (DataDTO) o;
            return Objects.equals(id, dataDTO.id) && Objects.equals(categoryId, dataDTO.categoryId) && Objects.equals(imgUrl, dataDTO.imgUrl) && Objects.equals(name, dataDTO.name) && Objects.equals(price, dataDTO.price) && Objects.equals(detail, dataDTO.detail) && Objects.equals(status, dataDTO.status) && Objects.equals(saleQuantity, dataDTO.saleQuantity) && Objects.equals(favorRate, dataDTO.favorRate) && Objects.equals(sellerId, dataDTO.sellerId) && Objects.equals(count, dataDTO.count);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, categoryId, imgUrl, name, price, detail, status, saleQuantity, favorRate, sellerId, count);
        }

        /**
         * categoryId
         */
        @SerializedName("categoryId")
        private Integer categoryId;
        /**
         * imgUrl
         */
        @SerializedName("imgUrl")
        private String imgUrl;
        /**
         * name
         */
        @SerializedName("name")
        private String name;
        /**
         * price
         */
        @SerializedName("price")
        private Double price;
        /**
         * detail
         */
        @SerializedName("detail")
        private String detail;
        /**
         * status
         */
        @SerializedName("status")
        private String status;
        /**
         * saleQuantity
         */
        @SerializedName("saleQuantity")
        private Integer saleQuantity;
        /**
         * favorRate
         */
        @SerializedName("favorRate")
        private Integer favorRate;
        /**
         * sellerId
         */
        @SerializedName("sellerId")
        private Integer sellerId;

        public Integer getId() {
            return id;
        }

        public Integer count = 0;

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        @Override
        public String toString() {
            return "DataDTO{" +
                    "id=" + id +
                    ", categoryId=" + categoryId +
                    ", imgUrl='" + imgUrl + '\'' +
                    ", name='" + name + '\'' +
                    ", price=" + price +
                    ", detail='" + detail + '\'' +
                    ", status='" + status + '\'' +
                    ", saleQuantity=" + saleQuantity +
                    ", favorRate=" + favorRate +
                    ", sellerId=" + sellerId +
                    '}';
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getPrice() {
            return price;
        }

        public void setPrice(Double price) {
            this.price = price;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Integer getSaleQuantity() {
            return saleQuantity;
        }

        public void setSaleQuantity(Integer saleQuantity) {
            this.saleQuantity = saleQuantity;
        }

        public Integer getFavorRate() {
            return favorRate;
        }

        public void setFavorRate(Integer favorRate) {
            this.favorRate = favorRate;
        }

        public Integer getSellerId() {
            return sellerId;
        }

        public void setSellerId(Integer sellerId) {
            this.sellerId = sellerId;
        }
    }
}
