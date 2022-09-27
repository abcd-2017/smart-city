package com.example.pojo.metroparam;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

/**
 * @author kkk
 */
public class MetroCardParam extends BaseParam {

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

    @Override
    public String toString() {
        return "MetroCardParam{" +
                ", data=" + data +
                '}';
    }

    public static class DataDTO {
        /**
         * id
         */
        @SerializedName("id")
        private Integer id;
        /**
         * userName
         */
        @SerializedName("userName")
        private String userName;
        /**
         * idCard
         */
        @SerializedName("idCard")
        private String idCard;
        /**
         * phonenumber
         */
        @SerializedName("phonenumber")
        private String phonenumber;
        /**
         * cardNum
         */
        @SerializedName("cardNum")
        private String cardNum;
        /**
         * userId
         */
        @SerializedName("userId")
        private Integer userId;

        public Integer getId() {
            return id;
        }

        @Override
        public String toString() {
            return "DataDTO{" +
                    "id=" + id +
                    ", userName='" + userName + '\'' +
                    ", idCard='" + idCard + '\'' +
                    ", phonenumber='" + phonenumber + '\'' +
                    ", cardNum='" + cardNum + '\'' +
                    ", userId=" + userId +
                    '}';
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getPhonenumber() {
            return phonenumber;
        }

        public void setPhonenumber(String phonenumber) {
            this.phonenumber = phonenumber;
        }

        public String getCardNum() {
            return cardNum;
        }

        public void setCardNum(String cardNum) {
            this.cardNum = cardNum;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }
    }
}
