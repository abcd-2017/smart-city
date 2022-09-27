package com.example.pojo.metroparam;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author kkk
 */
public class MetroDetailsParam extends BaseParam {

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
        return "MetroDetailsParam{" +
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
         * name
         */
        @SerializedName("name")
        private String name;
        /**
         * first
         */
        @SerializedName("first")
        private String first;
        /**
         * end
         */
        @SerializedName("end")
        private String end;
        /**
         * startTime
         */
        @SerializedName("startTime")
        private String startTime;
        /**
         * endTime
         */
        @SerializedName("endTime")
        private String endTime;
        /**
         * cityId
         */
        @SerializedName("cityId")
        private Integer cityId;
        /**
         * stationsNumber
         */
        @SerializedName("stationsNumber")
        private Integer stationsNumber;
        /**
         * km
         */
        @SerializedName("km")
        private Integer km;
        /**
         * runStationsName
         */
        @SerializedName("runStationsName")
        private String runStationsName;
        /**
         * metroStepList
         */
        @SerializedName("metroStepList")
        private List<MetroStepListDTO> metroStepList;
        /**
         * remainingTime
         */
        @SerializedName("remainingTime")
        private Integer remainingTime;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFirst() {
            return first;
        }

        public void setFirst(String first) {
            this.first = first;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public Integer getCityId() {
            return cityId;
        }

        public void setCityId(Integer cityId) {
            this.cityId = cityId;
        }

        public Integer getStationsNumber() {
            return stationsNumber;
        }

        public void setStationsNumber(Integer stationsNumber) {
            this.stationsNumber = stationsNumber;
        }

        public Integer getKm() {
            return km;
        }

        public void setKm(Integer km) {
            this.km = km;
        }

        public String getRunStationsName() {
            return runStationsName;
        }

        public void setRunStationsName(String runStationsName) {
            this.runStationsName = runStationsName;
        }

        public List<MetroStepListDTO> getMetroStepList() {
            return metroStepList;
        }

        public void setMetroStepList(List<MetroStepListDTO> metroStepList) {
            this.metroStepList = metroStepList;
        }

        public Integer getRemainingTime() {
            return remainingTime;
        }

        public void setRemainingTime(Integer remainingTime) {
            this.remainingTime = remainingTime;
        }

        @Override
        public String toString() {
            return "DataDTO{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", first='" + first + '\'' +
                    ", end='" + end + '\'' +
                    ", startTime='" + startTime + '\'' +
                    ", endTime='" + endTime + '\'' +
                    ", cityId=" + cityId +
                    ", stationsNumber=" + stationsNumber +
                    ", km=" + km +
                    ", runStationsName='" + runStationsName + '\'' +
                    ", metroStepList=" + metroStepList +
                    ", remainingTime=" + remainingTime +
                    '}';
        }

        public static class MetroStepListDTO {
            /**
             * searchValue
             */
            @SerializedName("searchValue")
            private Object searchValue;
            /**
             * createBy
             */
            @SerializedName("createBy")
            private Object createBy;
            /**
             * createTime
             */
            @SerializedName("createTime")
            private String createTime;
            /**
             * updateBy
             */
            @SerializedName("updateBy")
            private Object updateBy;
            /**
             * updateTime
             */
            @SerializedName("updateTime")
            private String updateTime;
            /**
             * remark
             */
            @SerializedName("remark")
            private Object remark;
            /**
             * params
             */
            @SerializedName("params")
            private ParamsDTO params;
            /**
             * id
             */
            @SerializedName("id")
            private Integer id;
            /**
             * name
             */
            @SerializedName("name")
            private String name;
            /**
             * seq
             */
            @SerializedName("seq")
            private Integer seq;
            /**
             * lineId
             */
            @SerializedName("lineId")
            private Integer lineId;
            /**
             * firstCh
             */
            @SerializedName("firstCh")
            private String firstCh;

            public Object getSearchValue() {
                return searchValue;
            }

            public void setSearchValue(Object searchValue) {
                this.searchValue = searchValue;
            }

            public Object getCreateBy() {
                return createBy;
            }

            public void setCreateBy(Object createBy) {
                this.createBy = createBy;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public Object getUpdateBy() {
                return updateBy;
            }

            public void setUpdateBy(Object updateBy) {
                this.updateBy = updateBy;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public ParamsDTO getParams() {
                return params;
            }

            public void setParams(ParamsDTO params) {
                this.params = params;
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Integer getSeq() {
                return seq;
            }

            public void setSeq(Integer seq) {
                this.seq = seq;
            }

            public Integer getLineId() {
                return lineId;
            }

            public void setLineId(Integer lineId) {
                this.lineId = lineId;
            }

            public String getFirstCh() {
                return firstCh;
            }

            public void setFirstCh(String firstCh) {
                this.firstCh = firstCh;
            }

            public static class ParamsDTO {
            }

            @Override
            public String toString() {
                return "MetroStepListDTO{" +
                        "searchValue=" + searchValue +
                        ", createBy=" + createBy +
                        ", createTime='" + createTime + '\'' +
                        ", updateBy=" + updateBy +
                        ", updateTime='" + updateTime + '\'' +
                        ", remark=" + remark +
                        ", params=" + params +
                        ", id=" + id +
                        ", name='" + name + '\'' +
                        ", seq=" + seq +
                        ", lineId=" + lineId +
                        ", firstCh='" + firstCh + '\'' +
                        '}';
            }
        }
    }
}
