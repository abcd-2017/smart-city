package com.example.pojo.workparam;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

/**
 * @author kkk
 */
public class WorkResumeDetailParam extends BaseParam {

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
        return "WorkResumeDetail{" +
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
         * mostEducation
         */
        @SerializedName("mostEducation")
        private String mostEducation;
        /**
         * education
         */
        @SerializedName("education")
        private String education;
        /**
         * address
         */
        @SerializedName("address")
        private String address;
        /**
         * experience
         */
        @SerializedName("experience")
        private String experience;
        /**
         * individualResume
         */
        @SerializedName("individualResume")
        private String individualResume;
        /**
         * money
         */
        @SerializedName("money")
        private String money;
        /**
         * positionId
         */
        @SerializedName("positionId")
        private Integer positionId;
        /**
         * files
         */
        @SerializedName("files")
        private String files;
        /**
         * positionName
         */
        @SerializedName("positionName")
        private Object positionName;
        /**
         * userName
         */
        @SerializedName("userName")
        private Object userName;

        public Integer getId() {
            return id;
        }

        @Override
        public String toString() {
            return "DataDTO{" +
                    "id=" + id +
                    ", userId=" + userId +
                    ", mostEducation='" + mostEducation + '\'' +
                    ", education='" + education + '\'' +
                    ", address='" + address + '\'' +
                    ", experience='" + experience + '\'' +
                    ", individualResume='" + individualResume + '\'' +
                    ", money='" + money + '\'' +
                    ", positionId=" + positionId +
                    ", files='" + files + '\'' +
                    ", positionName=" + positionName +
                    ", userName=" + userName +
                    '}';
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

        public String getMostEducation() {
            return mostEducation;
        }

        public void setMostEducation(String mostEducation) {
            this.mostEducation = mostEducation;
        }

        public String getEducation() {
            return education;
        }

        public void setEducation(String education) {
            this.education = education;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public String getIndividualResume() {
            return individualResume;
        }

        public void setIndividualResume(String individualResume) {
            this.individualResume = individualResume;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public Integer getPositionId() {
            return positionId;
        }

        public void setPositionId(Integer positionId) {
            this.positionId = positionId;
        }

        public String getFiles() {
            return files;
        }

        public void setFiles(String files) {
            this.files = files;
        }

        public Object getPositionName() {
            return positionName;
        }

        public void setPositionName(Object positionName) {
            this.positionName = positionName;
        }

        public Object getUserName() {
            return userName;
        }

        public void setUserName(Object userName) {
            this.userName = userName;
        }
    }
}
