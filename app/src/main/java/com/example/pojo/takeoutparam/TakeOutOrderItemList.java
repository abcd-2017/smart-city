package com.example.pojo.takeoutparam;

/**
 * @author kkk
 */
public class TakeOutOrderItemList {
    private Integer productId;
    private Integer quantity;

    public TakeOutOrderItemList(Integer productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public TakeOutOrderItemList() {
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "TakeOutOrderItemList{" +
                "productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
