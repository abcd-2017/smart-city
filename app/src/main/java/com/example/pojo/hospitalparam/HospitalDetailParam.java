package com.example.pojo.hospitalparam;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

/**
 * @author kkk
 */
public class HospitalDetailParam extends BaseParam {

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
        return "HospitalDetailParam{" +
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
         * hospitalName
         */
        @SerializedName("hospitalName")
        private String hospitalName;
        /**
         * brief
         */
        @SerializedName("brief")
        private String brief;
        /**
         * level
         */
        @SerializedName("level")
        private Integer level;
        /**
         * imgUrl
         */
        @SerializedName("imgUrl")
        private String imgUrl;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getHospitalName() {
            return hospitalName;
        }

        @Override
        public String toString() {
            return "DataDTO{" +
                    "id=" + id +
                    ", hospitalName='" + hospitalName + '\'' +
                    ", brief='" + brief + '\'' +
                    ", level=" + level +
                    ", imgUrl='" + imgUrl + '\'' +
                    '}';
        }

        public void setHospitalName(String hospitalName) {
            this.hospitalName = hospitalName;
        }

        public String getBrief() {
            return brief;
        }

        public void setBrief(String brief) {
            this.brief = brief;
        }

        public Integer getLevel() {
            return level;
        }

        public void setLevel(Integer level) {
            this.level = level;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }
    }
}
