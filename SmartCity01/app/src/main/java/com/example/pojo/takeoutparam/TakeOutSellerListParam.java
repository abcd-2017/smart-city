package com.example.pojo.takeoutparam;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author kkk
 */
public class TakeOutSellerListParam extends BaseParam {

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
        return "TakeOutSellerListParam{" +
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
         * address
         */
        @SerializedName("address")
        private String address;
        /**
         * introduction
         */
        @SerializedName("introduction")
        private String introduction;
        /**
         * themeId
         */
        @SerializedName("themeId")
        private Integer themeId;
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
         * deliveryTime
         */
        @SerializedName("deliveryTime")
        private Integer deliveryTime;
        /**
         * imgUrl
         */
        @SerializedName("imgUrl")
        private String imgUrl;
        /**
         * avgCost
         */
        @SerializedName("avgCost")
        private Double avgCost;
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
        /**
         * distance
         */
        @SerializedName("distance")
        private Double distance;
        /**
         * saleNum3hour
         */
        @SerializedName("saleNum3hour")
        private Integer saleNum3hour;

        @Override
        public String toString() {
            return "RowsDTO{" +
                    "searchValue=" + searchValue +
                    ", createBy=" + createBy +
                    ", createTime='" + createTime + '\'' +
                    ", updateBy=" + updateBy +
                    ", updateTime='" + updateTime + '\'' +
                    ", remark=" + remark +
                    ", params=" + params +
                    ", id=" + id +
                    ", name='" + name + '\'' +
                    ", address='" + address + '\'' +
                    ", introduction='" + introduction + '\'' +
                    ", themeId=" + themeId +
                    ", score=" + score +
                    ", saleQuantity=" + saleQuantity +
                    ", deliveryTime=" + deliveryTime +
                    ", imgUrl='" + imgUrl + '\'' +
                    ", avgCost=" + avgCost +
                    ", other=" + other +
                    ", recommend='" + recommend + '\'' +
                    ", distance=" + distance +
                    ", saleNum3hour=" + saleNum3hour +
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getIntroduction() {
            return introduction;
        }

        public void setIntroduction(String introduction) {
            this.introduction = introduction;
        }

        public Integer getThemeId() {
            return themeId;
        }

        public void setThemeId(Integer themeId) {
            this.themeId = themeId;
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

        public Integer getDeliveryTime() {
            return deliveryTime;
        }

        public void setDeliveryTime(Integer deliveryTime) {
            this.deliveryTime = deliveryTime;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public Double getAvgCost() {
            return avgCost;
        }

        public void setAvgCost(Double avgCost) {
            this.avgCost = avgCost;
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

        public Double getDistance() {
            return distance;
        }

        public void setDistance(Double distance) {
            this.distance = distance;
        }

        public Integer getSaleNum3hour() {
            return saleNum3hour;
        }

        public void setSaleNum3hour(Integer saleNum3hour) {
            this.saleNum3hour = saleNum3hour;
        }

        public static class ParamsDTO {
        }
    }
}
