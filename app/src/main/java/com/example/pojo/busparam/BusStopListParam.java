package com.example.pojo.busparam;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author kkk
 */
public class BusStopListParam extends BaseParam {

    /**
     * total
     */
    @SerializedName("total")
    private Integer total;
    /**
     * rows
     */
    @SerializedName("rows")
    private List<RowsDTO> rows;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<RowsDTO> getRows() {
        return rows;
    }

    public void setRows(List<RowsDTO> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "BusStopListParam{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }

    public static class RowsDTO {
        /**
         * linesId
         */
        @SerializedName("linesId")
        private Integer linesId;
        /**
         * stepsId
         */
        @SerializedName("stepsId")
        private Integer stepsId;
        /**
         * name
         */
        @SerializedName("name")
        private String name;
        /**
         * sequence
         */
        @SerializedName("sequence")
        private Integer sequence;

        public Integer getLinesId() {
            return linesId;
        }

        public void setLinesId(Integer linesId) {
            this.linesId = linesId;
        }

        public Integer getStepsId() {
            return stepsId;
        }

        public void setStepsId(Integer stepsId) {
            this.stepsId = stepsId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getSequence() {
            return sequence;
        }

        public void setSequence(Integer sequence) {
            this.sequence = sequence;
        }

        @Override
        public String toString() {
            return "RowsDTO{" +
                    "linesId=" + linesId +
                    ", stepsId=" + stepsId +
                    ", name='" + name + '\'' +
                    ", sequence=" + sequence +
                    '}';
        }
    }
}
