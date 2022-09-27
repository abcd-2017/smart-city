package com.example.pojo.takeoutparam;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author kkk
 */
public class TakeOutOrderDetailListParam extends BaseParam {

    /**
     * data
     */
    @SerializedName("data")
    private DataDTO data;

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public static class DataDTO {
        /**
         * sellerInfo
         */
        @SerializedName("sellerInfo")
        private SellerInfoDTO sellerInfo;
        /**
         * orderInfo
         */
        @SerializedName("orderInfo")
        private OrderInfoDTO orderInfo;

        public SellerInfoDTO getSellerInfo() {
            return sellerInfo;
        }

        public void setSellerInfo(SellerInfoDTO sellerInfo) {
            this.sellerInfo = sellerInfo;
        }

        public OrderInfoDTO getOrderInfo() {
            return orderInfo;
        }

        public void setOrderInfo(OrderInfoDTO orderInfo) {
            this.orderInfo = orderInfo;
        }

        public static class SellerInfoDTO {
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
            private Integer score;
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
            private Integer avgCost;
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
            private Integer distance;
            /**
             * saleNum3hour
             */
            @SerializedName("saleNum3hour")
            private Integer saleNum3hour;

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

            public Integer getScore() {
                return score;
            }

            public void setScore(Integer score) {
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

            public Integer getAvgCost() {
                return avgCost;
            }

            public void setAvgCost(Integer avgCost) {
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

            public Integer getDistance() {
                return distance;
            }

            public void setDistance(Integer distance) {
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

        public static class OrderInfoDTO {
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
             * orderNo
             */
            @SerializedName("orderNo")
            private String orderNo;
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
             * amount
             */
            @SerializedName("amount")
            private Double amount;
            /**
             * postage
             */
            @SerializedName("postage")
            private Object postage;
            /**
             * status
             */
            @SerializedName("status")
            private String status;
            /**
             * paymentType
             */
            @SerializedName("paymentType")
            private String paymentType;
            /**
             * payTime
             */
            @SerializedName("payTime")
            private Object payTime;
            /**
             * sendTime
             */
            @SerializedName("sendTime")
            private Object sendTime;
            /**
             * receiverName
             */
            @SerializedName("receiverName")
            private String receiverName;
            /**
             * receiverPhone
             */
            @SerializedName("receiverPhone")
            private String receiverPhone;
            /**
             * receiverAddress
             */
            @SerializedName("receiverAddress")
            private String receiverAddress;
            /**
             * receiverLabel
             */
            @SerializedName("receiverLabel")
            private String receiverLabel;
            /**
             * houseNumber
             */
            @SerializedName("houseNumber")
            private Object houseNumber;
            /**
             * orderItemList
             */
            @SerializedName("orderItemList")
            private List<OrderItemListDTO> orderItemList;

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

            public String getOrderNo() {
                return orderNo;
            }

            public void setOrderNo(String orderNo) {
                this.orderNo = orderNo;
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

            public Double getAmount() {
                return amount;
            }

            public void setAmount(Double amount) {
                this.amount = amount;
            }

            public Object getPostage() {
                return postage;
            }

            public void setPostage(Object postage) {
                this.postage = postage;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getPaymentType() {
                return paymentType;
            }

            public void setPaymentType(String paymentType) {
                this.paymentType = paymentType;
            }

            public Object getPayTime() {
                return payTime;
            }

            public void setPayTime(Object payTime) {
                this.payTime = payTime;
            }

            public Object getSendTime() {
                return sendTime;
            }

            public void setSendTime(Object sendTime) {
                this.sendTime = sendTime;
            }

            public String getReceiverName() {
                return receiverName;
            }

            public void setReceiverName(String receiverName) {
                this.receiverName = receiverName;
            }

            public String getReceiverPhone() {
                return receiverPhone;
            }

            public void setReceiverPhone(String receiverPhone) {
                this.receiverPhone = receiverPhone;
            }

            public String getReceiverAddress() {
                return receiverAddress;
            }

            public void setReceiverAddress(String receiverAddress) {
                this.receiverAddress = receiverAddress;
            }

            public String getReceiverLabel() {
                return receiverLabel;
            }

            public void setReceiverLabel(String receiverLabel) {
                this.receiverLabel = receiverLabel;
            }

            public Object getHouseNumber() {
                return houseNumber;
            }

            public void setHouseNumber(Object houseNumber) {
                this.houseNumber = houseNumber;
            }

            public List<OrderItemListDTO> getOrderItemList() {
                return orderItemList;
            }

            public void setOrderItemList(List<OrderItemListDTO> orderItemList) {
                this.orderItemList = orderItemList;
            }

            public static class ParamsDTO {
            }

            public static class OrderItemListDTO {
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
                 * userId
                 */
                @SerializedName("userId")
                private Integer userId;
                /**
                 * orderNo
                 */
                @SerializedName("orderNo")
                private String orderNo;
                /**
                 * productId
                 */
                @SerializedName("productId")
                private Integer productId;
                /**
                 * productName
                 */
                @SerializedName("productName")
                private String productName;
                /**
                 * productImage
                 */
                @SerializedName("productImage")
                private String productImage;
                /**
                 * productPrice
                 */
                @SerializedName("productPrice")
                private Double productPrice;
                /**
                 * quantity
                 */
                @SerializedName("quantity")
                private Integer quantity;
                /**
                 * totalPrice
                 */
                @SerializedName("totalPrice")
                private Double totalPrice;

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

                public Integer getUserId() {
                    return userId;
                }

                public void setUserId(Integer userId) {
                    this.userId = userId;
                }

                public String getOrderNo() {
                    return orderNo;
                }

                public void setOrderNo(String orderNo) {
                    this.orderNo = orderNo;
                }

                public Integer getProductId() {
                    return productId;
                }

                public void setProductId(Integer productId) {
                    this.productId = productId;
                }

                public String getProductName() {
                    return productName;
                }

                public void setProductName(String productName) {
                    this.productName = productName;
                }

                public String getProductImage() {
                    return productImage;
                }

                public void setProductImage(String productImage) {
                    this.productImage = productImage;
                }

                public Double getProductPrice() {
                    return productPrice;
                }

                public void setProductPrice(Double productPrice) {
                    this.productPrice = productPrice;
                }

                public Integer getQuantity() {
                    return quantity;
                }

                public void setQuantity(Integer quantity) {
                    this.quantity = quantity;
                }

                public Double getTotalPrice() {
                    return totalPrice;
                }

                public void setTotalPrice(Double totalPrice) {
                    this.totalPrice = totalPrice;
                }

                public static class ParamsDTO {
                }
            }
        }
    }
}
