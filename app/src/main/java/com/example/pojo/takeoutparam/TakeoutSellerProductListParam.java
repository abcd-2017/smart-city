package com.example.pojo.takeoutparam;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author kkk
 */
public class TakeoutSellerProductListParam extends BaseParam {

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
        return "TakeoutSellerProductListParam{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }

    public static class RowsDTO {
        /**
         * name
         */
        @SerializedName("name")
        private String name;
        /**
         * address
         */
        @SerializedName("address")
        private Object address;
        /**
         * introduction
         */
        @SerializedName("introduction")
        private Object introduction;
        /**
         * score
         */
        @SerializedName("score")
        private Double score;
        /**
         * saleQuantity
         */
        @SerializedName("saleQuantity")
        private Object saleQuantity;
        /**
         * imgUrl
         */
        @SerializedName("imgUrl")
        private String imgUrl;
        /**
         * avgCost
         */
        @SerializedName("avgCost")
        private Integer avgCost;
        /**
         * deliveryTime
         */
        @SerializedName("deliveryTime")
        private Integer deliveryTime;
        /**
         * distance
         */
        @SerializedName("distance")
        private Integer distance;
        /**
         * productList
         */
        @SerializedName("productList")
        private List<ProductListDTO> productList;

        @Override
        public String toString() {
            return "RowsDTO{" +
                    "name='" + name + '\'' +
                    ", address=" + address +
                    ", introduction=" + introduction +
                    ", score=" + score +
                    ", saleQuantity=" + saleQuantity +
                    ", imgUrl='" + imgUrl + '\'' +
                    ", avgCost=" + avgCost +
                    ", deliveryTime=" + deliveryTime +
                    ", distance=" + distance +
                    ", productList=" + productList +
                    '}';
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public Object getIntroduction() {
            return introduction;
        }

        public void setIntroduction(Object introduction) {
            this.introduction = introduction;
        }

        public Double getScore() {
            return score;
        }

        public void setScore(Double score) {
            this.score = score;
        }

        public Object getSaleQuantity() {
            return saleQuantity;
        }

        public void setSaleQuantity(Object saleQuantity) {
            this.saleQuantity = saleQuantity;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public Integer getAvgCost() {
            return avgCost;
        }

        public void setAvgCost(Integer avgCost) {
            this.avgCost = avgCost;
        }

        public Integer getDeliveryTime() {
            return deliveryTime;
        }

        public void setDeliveryTime(Integer deliveryTime) {
            this.deliveryTime = deliveryTime;
        }

        public Integer getDistance() {
            return distance;
        }

        public void setDistance(Integer distance) {
            this.distance = distance;
        }

        public List<ProductListDTO> getProductList() {
            return productList;
        }

        public void setProductList(List<ProductListDTO> productList) {
            this.productList = productList;
        }

        public static class ProductListDTO {
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
            private String createTime;
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
            private Object detail;
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

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
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

            public Object getDetail() {
                return detail;
            }

            public void setDetail(Object detail) {
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

            public static class ParamsDTO {
            }
        }
    }
}
