package com.example.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author kkk
 */
public class ServiceListParam extends BaseParam {
    @Override
    public String toString() {
        return "ServiceListParam{" +
                ", rows=" + rows +
                ", total=" + total +
                '}';
    }

    /**
     * rows
     */
    @SerializedName("rows")
    private List<RowsDTO> rows;
    /**
     * total
     */
    @SerializedName("total")
    private Integer total;

    public List<RowsDTO> getRows() {
        return rows;
    }

    public void setRows(List<RowsDTO> rows) {
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public static class RowsDTO {
        /**
         * id
         */
        @SerializedName("id")
        private Integer id;
        /**
         * serviceName
         */
        @SerializedName("serviceName")
        private String serviceName;

        public RowsDTO() {
        }

        public RowsDTO(Integer id, String serviceName, String serviceDesc, String serviceType, String imgUrl, String link, Integer sort, String isRecommend) {
            this.id = id;
            this.serviceName = serviceName;
            this.serviceDesc = serviceDesc;
            this.serviceType = serviceType;
            this.imgUrl = imgUrl;
            this.link = link;
            this.sort = sort;
            this.isRecommend = isRecommend;
        }

        /**
         * serviceDesc
         */
        @SerializedName("serviceDesc")
        private String serviceDesc;
        /**
         * serviceType
         */
        @SerializedName("serviceType")
        private String serviceType;
        /**
         * imgUrl
         */
        @SerializedName("imgUrl")
        private String imgUrl;

        @Override
        public String toString() {
            return "RowsDTO{" +
                    "id=" + id +
                    ", serviceName='" + serviceName + '\'' +
                    ", serviceDesc='" + serviceDesc + '\'' +
                    ", serviceType='" + serviceType + '\'' +
                    ", imgUrl='" + imgUrl + '\'' +
                    ", link='" + link + '\'' +
                    ", sort=" + sort +
                    ", isRecommend='" + isRecommend + '\'' +
                    '}';
        }

        /**
         * link
         */
        @SerializedName("link")
        private String link;
        /**
         * sort
         */
        @SerializedName("sort")
        private Integer sort;
        /**
         * isRecommend
         */
        @SerializedName("isRecommend")
        private String isRecommend;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getServiceDesc() {
            return serviceDesc;
        }

        public void setServiceDesc(String serviceDesc) {
            this.serviceDesc = serviceDesc;
        }

        public String getServiceType() {
            return serviceType;
        }

        public void setServiceType(String serviceType) {
            this.serviceType = serviceType;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public Integer getSort() {
            return sort;
        }

        public void setSort(Integer sort) {
            this.sort = sort;
        }

        public String getIsRecommend() {
            return isRecommend;
        }

        public void setIsRecommend(String isRecommend) {
            this.isRecommend = isRecommend;
        }
    }
}
