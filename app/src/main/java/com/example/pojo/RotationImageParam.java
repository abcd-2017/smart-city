package com.example.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author kkk
 */
public class RotationImageParam extends BaseParam {

    @Override
    public String toString() {
        return "RotationImageParam{" +
                ", rows=" + rows +
                ", total='" + total + '\'' +
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
    private String total;

    public List<RowsDTO> getRows() {
        return rows;
    }

    public void setRows(List<RowsDTO> rows) {
        this.rows = rows;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public static class RowsDTO {
        /**
         * id
         */
        @SerializedName("id")
        private String id;
        /**
         * sort
         */
        @SerializedName("sort")
        private String sort;
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
        private String targetId;
        /**
         * type
         */
        @SerializedName("type")
        private String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "RowsDTO{" +
                    "id='" + id + '\'' +
                    ", sort='" + sort + '\'' +
                    ", advTitle='" + advTitle + '\'' +
                    ", advImg='" + advImg + '\'' +
                    ", servModule='" + servModule + '\'' +
                    ", targetId='" + targetId + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
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

        public String getTargetId() {
            return targetId;
        }

        public void setTargetId(String targetId) {
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
