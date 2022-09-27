package com.example.pojo.expenses;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author kkk
 */
public class ExpensesRotationListParam extends BaseParam {

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

    @Override
    public String toString() {
        return "ExpensesRotationListParam{" +
                "rows=" + rows +
                ", total=" + total +
                '}';
    }

    public static class RowsDTO {
        /**
         * id
         */
        @SerializedName("id")
        private Integer id;
        /**
         * appType
         */
        @SerializedName("appType")
        private String appType;
        /**
         * status
         */
        @SerializedName("status")
        private String status;
        /**
         * sort
         */
        @SerializedName("sort")
        private Integer sort;
        /**
         * advTitle
         */
        @SerializedName("advTitle")
        private String advTitle;
        /**
         * advImg
         */
        @SerializedName("advImg")
        private String advImg;
        /**
         * servModule
         */
        @SerializedName("servModule")
        private String servModule;
        /**
         * targetId
         */
        @SerializedName("targetId")
        private Integer targetId;
        /**
         * type
         */
        @SerializedName("type")
        private String type;

        @Override
        public String toString() {
            return "RowsDTO{" +
                    "id=" + id +
                    ", appType='" + appType + '\'' +
                    ", status='" + status + '\'' +
                    ", sort=" + sort +
                    ", advTitle='" + advTitle + '\'' +
                    ", advImg='" + advImg + '\'' +
                    ", servModule='" + servModule + '\'' +
                    ", targetId=" + targetId +
                    ", type='" + type + '\'' +
                    '}';
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getAppType() {
            return appType;
        }

        public void setAppType(String appType) {
            this.appType = appType;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Integer getSort() {
            return sort;
        }

        public void setSort(Integer sort) {
            this.sort = sort;
        }

        public String getAdvTitle() {
            return advTitle;
        }

        public void setAdvTitle(String advTitle) {
            this.advTitle = advTitle;
        }

        public String getAdvImg() {
            return advImg;
        }

        public void setAdvImg(String advImg) {
            this.advImg = advImg;
        }

        public String getServModule() {
            return servModule;
        }

        public void setServModule(String servModule) {
            this.servModule = servModule;
        }

        public Integer getTargetId() {
            return targetId;
        }

        public void setTargetId(Integer targetId) {
            this.targetId = targetId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
