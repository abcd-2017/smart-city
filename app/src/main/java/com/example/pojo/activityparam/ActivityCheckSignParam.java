package com.example.pojo.activityparam;

import androidx.annotation.NonNull;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

/**
 * @author kkk
 */
public class ActivityCheckSignParam extends BaseParam {

    /**
     * isSignup
     */
    @SerializedName("isSignup")
    private Boolean isSignup;
    /**
     * id
     */
    @SerializedName("id")
    private Integer id;

    @NonNull
    @Override
    public String toString() {
        return "ActivityCheckSIgnParam{" +
                ", isSignup=" + isSignup +
                ", id=" + id +
                '}';
    }


    public Boolean getIsSignup() {
        return isSignup;
    }

    public void setIsSignup(Boolean isSignup) {
        this.isSignup = isSignup;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
