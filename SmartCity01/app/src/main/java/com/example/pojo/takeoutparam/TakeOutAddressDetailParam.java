package com.example.pojo.takeoutparam;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

/**
 * @author kkk
 */
public class TakeOutAddressDetailParam extends BaseParam {

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
        return "TakeOutAddressDetailParam{" +
                "data=" + data +
                '}';
    }

    public static class DataDTO {
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
         * name
         */
        @SerializedName("name")
        private String name;
        /**
         * phone
         */
        @SerializedName("phone")
        private String phone;
        /**
         * addressDetail
         */
        @SerializedName("addressDetail")
        private String addressDetail;
        /**
         * houseNumber
         */
        @SerializedName("houseNumber")
        private String houseNumber;
        /**
         * label
         */
        @SerializedName("label")
        private String label;

        @Override
        public String toString() {
            return "DataDTO{" +
                    "id=" + id +
                    ", userId=" + userId +
                    ", name='" + name + '\'' +
                    ", phone='" + phone + '\'' +
                    ", addressDetail='" + addressDetail + '\'' +
                    ", houseNumber='" + houseNumber + '\'' +
                    ", label='" + label + '\'' +
                    '}';
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddressDetail() {
            return addressDetail;
        }

        public void setAddressDetail(String addressDetail) {
            this.addressDetail = addressDetail;
        }

        public String getHouseNumber() {
            return houseNumber;
        }

        public void setHouseNumber(String houseNumber) {
            this.houseNumber = houseNumber;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }
}
