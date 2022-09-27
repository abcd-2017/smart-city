package com.example.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author kkk
 */
public class UserInfoParam extends BaseParam implements Serializable {

    /**
     * user
     */
    @SerializedName("user")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
