package com.example.pojo.metroparam;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author kkk
 */
public class MetroListParam extends BaseParam {

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
        return "MetroListParam{" +
                ", data=" + data +
                '}';
    }

    public static class DataDTO {
        /**
         * lineId
         */
        @SerializedName("lineId")
        private Integer lineId;
        /**
         * lineName
         */
        @SerializedName("lineName")
        private String lineName;
        /**
         * preStep
         */
        @SerializedName("preStep")
        private PreStepDTO preStep;
        /**
         * nextStep
         */
        @SerializedName("nextStep")
        private NextStepDTO nextStep;
        /**
         * currentName
         */
        @SerializedName("currentName")
        private String currentName;
        /**
         * reachTime
         */
        @SerializedName("reachTime")
        private Integer reachTime;

        public Integer getLineId() {
            return lineId;
        }

        public void setLineId(Integer lineId) {
            this.lineId = lineId;
        }

        public String getLineName() {
            return lineName;
        }

        public void setLineName(String lineName) {
            this.lineName = lineName;
        }

        public PreStepDTO getPreStep() {
            return preStep;
        }

        public void setPreStep(PreStepDTO preStep) {
            this.preStep = preStep;
        }

        public NextStepDTO getNextStep() {
            return nextStep;
        }

        public void setNextStep(NextStepDTO nextStep) {
            this.nextStep = nextStep;
        }

        public String getCurrentName() {
            return currentName;
        }

        public void setCurrentName(String currentName) {
            this.currentName = currentName;
        }

        public Integer getReachTime() {
            return reachTime;
        }

        public void setReachTime(Integer reachTime) {
            this.reachTime = reachTime;
        }

        @Override
        public String toString() {
            return "DataDTO{" +
                    "lineId=" + lineId +
                    ", lineName='" + lineName + '\'' +
                    ", preStep=" + preStep +
                    ", nextStep=" + nextStep +
                    ", currentName='" + currentName + '\'' +
                    ", reachTime=" + reachTime +
                    '}';
        }

        public static class PreStepDTO {
            /**
             * name
             */
            @SerializedName("name")
            private String name;
            /**
             * lines
             */
            @SerializedName("lines")
            private List<LinesDTO> lines;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<LinesDTO> getLines() {
                return lines;
            }

            public void setLines(List<LinesDTO> lines) {
                this.lines = lines;
            }

            @Override
            public String toString() {
                return "PreStepDTO{" +
                        "name='" + name + '\'' +
                        ", lines=" + lines +
                        '}';
            }

        }

        public static class NextStepDTO {
            /**
             * name
             */
            @SerializedName("name")
            private String name;
            /**
             * lines
             */
            @SerializedName("lines")
            private List<LinesDTO> lines;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<LinesDTO> getLines() {
                return lines;
            }

            public void setLines(List<LinesDTO> lines) {
                this.lines = lines;
            }

            @Override
            public String toString() {
                return "NextStepDTO{" +
                        "name='" + name + '\'' +
                        ", lines=" + lines +
                        '}';
            }


        }
    }

    public static class LinesDTO {
        /**
         * lineId
         */
        @SerializedName("lineId")
        private Integer lineId;
        /**
         * lineName
         */
        @SerializedName("lineName")
        private String lineName;

        public Integer getLineId() {
            return lineId;
        }

        public void setLineId(Integer lineId) {
            this.lineId = lineId;
        }

        public String getLineName() {
            return lineName;
        }

        @Override
        public String toString() {
            return "LinesDTO{" +
                    "lineId=" + lineId +
                    ", lineName='" + lineName + '\'' +
                    '}';
        }

        public void setLineName(String lineName) {
            this.lineName = lineName;
        }
    }
}
