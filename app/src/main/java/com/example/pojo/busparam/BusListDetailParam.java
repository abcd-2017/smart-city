package com.example.pojo.busparam;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

/**
 * @author kkk
 */
public class BusListDetailParam extends BaseParam {

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
        return "BusListDetailParam{" +
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
         * price
         */
        @SerializedName("price")
        private Integer price;
        /**
         * mileage
         */
        @SerializedName("mileage")
        private String mileage;

        @Override
        public String toString() {
            return "DataDTO{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", first='" + first + '\'' +
                    ", end='" + end + '\'' +
                    ", startTime='" + startTime + '\'' +
                    ", endTime='" + endTime + '\'' +
                    ", price=" + price +
                    ", mileage='" + mileage + '\'' +
                    '}';
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

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public String getMileage() {
            return mileage;
        }

        public void setMileage(String mileage) {
            this.mileage = mileage;
        }
    }
}
