package com.example.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author kkk
 */
public class User implements Serializable {
    private static final long serialVersionUID = 0L;

    /**
     * userId
     */
    @SerializedName("userId")
    private Integer userId;
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
    /**
     * email
     */
    @SerializedName("email")
    private String email;
    /**
     * phonenumber
     */
    @SerializedName("phonenumber")
    private String phonenumber;
    /**
     * sex
     */
    @SerializedName("sex")
    private String sex;
    /**
     * avatar
     */
    @SerializedName("avatar")
    private String avatar;
    /**
     * idCard
     */
    @SerializedName("idCard")
    private String idCard;
    /**
     * balance
     */
    @SerializedName("balance")
    private Double balance;
    /**
     * score
     */
    @SerializedName("score")
    private Integer score;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
