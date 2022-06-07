package com.example.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * @author kkk
 */
public class LoginParam extends BaseParam {
    /**
     * token
     */
    @SerializedName("token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginParam{" +
                ", token='" + token + '\'' +
                '}';
    }
}
