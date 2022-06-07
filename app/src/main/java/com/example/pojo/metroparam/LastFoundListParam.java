package com.example.pojo.metroparam;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author kkk
 */
public class LastFoundListParam extends BaseParam {

    /**
     * data
     */
    @SerializedName("data")
    private List<DataDTO> data;

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LastFoundListParam{" +
                ", data=" + data +
                '}';
    }

    public static class DataDTO {
        /**
         * publishDate
         */
        @SerializedName("publishDate")
        private String publishDate;
        /**
         * metroFoundList
         */
        @SerializedName("metroFoundList")
        private List<MetroFoundListDTO> metroFoundList;

        public String getPublishDate() {
            return publishDate;
        }

        public void setPublishDate(String publishDate) {
            this.publishDate = publishDate;
        }

        public List<MetroFoundListDTO> getMetroFoundList() {
            return metroFoundList;
        }

        public void setMetroFoundList(List<MetroFoundListDTO> metroFoundList) {
            this.metroFoundList = metroFoundList;
        }

        @Override
        public String toString() {
            return "DataDTO{" +
                    "publishDate='" + publishDate + '\'' +
                    ", metroFoundList=" + metroFoundList +
                    '}';
        }

        public static class MetroFoundListDTO {
            /**
             * id
             */
            @SerializedName("id")
            private Integer id;
            /**
             * imgUrl
             */
            @SerializedName("imgUrl")
            private String imgUrl;
            /**
             * foundTime
             */
            @SerializedName("foundTime")
            private String foundTime;
            /**
             * foundPlace
             */
            @SerializedName("foundPlace")
            private String foundPlace;
            /**
             * claimPlace
             */
            @SerializedName("claimPlace")
            private String claimPlace;

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getFoundTime() {
                return foundTime;
            }

            public void setFoundTime(String foundTime) {
                this.foundTime = foundTime;
            }

            public String getFoundPlace() {
                return foundPlace;
            }

            public void setFoundPlace(String foundPlace) {
                this.foundPlace = foundPlace;
            }

            public String getClaimPlace() {
                return claimPlace;
            }

            public void setClaimPlace(String claimPlace) {
                this.claimPlace = claimPlace;
            }

            @Override
            public String toString() {
                return "MetroFoundListDTO{" +
                        "id=" + id +
                        ", imgUrl='" + imgUrl + '\'' +
                        ", foundTime='" + foundTime + '\'' +
                        ", foundPlace='" + foundPlace + '\'' +
                        ", claimPlace='" + claimPlace + '\'' +
                        '}';
            }
        }
    }
}
