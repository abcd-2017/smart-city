package com.example.pojo.takeoutparam;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

/**
 * @author kkk
 */
public class TakeOutSellerCheckParam extends BaseParam {

    /**
     * isCollect
     */
    @SerializedName("isCollect")
    private Boolean isCollect;
    /**
     * id
     */
    @SerializedName("id")
    private Integer id;

    public Boolean getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(Boolean isCollect) {
        this.isCollect = isCollect;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
