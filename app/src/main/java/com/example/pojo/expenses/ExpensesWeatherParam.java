package com.example.pojo.expenses;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author kkk
 */
public class ExpensesWeatherParam extends BaseParam {

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
        return "ExpensesWeatherParam{" +
                "data=" + data +
                '}';
    }

    public static class DataDTO {
        /**
         * today
         */
        @SerializedName("today")
        private TodayDTO today;
        /**
         * weatherList
         */
        @SerializedName("weatherList")
        private List<WeatherListDTO> weatherList;

        public TodayDTO getToday() {
            return today;
        }

        public void setToday(TodayDTO today) {
            this.today = today;
        }

        public List<WeatherListDTO> getWeatherList() {
            return weatherList;
        }

        public void setWeatherList(List<WeatherListDTO> weatherList) {
            this.weatherList = weatherList;
        }

        @Override
        public String toString() {
            return "DataDTO{" +
                    "today=" + today +
                    ", weatherList=" + weatherList +
                    '}';
        }

        public static class TodayDTO {
            /**
             * hours
             */
            @SerializedName("hours")
            private List<HoursDTO> hours;
            /**
             * airQuantity
             */
            @SerializedName("airQuantity")
            private AirQuantityDTO airQuantity;
            /**
             * comfortLevel
             */
            @SerializedName("comfortLevel")
            private ComfortLevelDTO comfortLevel;
            /**
             * tempInfo
             */
            @SerializedName("tempInfo")
            private TempInfoDTO tempInfo;
            /**
             * updateTime
             */
            @SerializedName("updateTime")
            private String updateTime;
            /**
             * wind
             */
            @SerializedName("wind")
            private WindDTO wind;

            @Override
            public String toString() {
                return "TodayDTO{" +
                        "hours=" + hours +
                        ", airQuantity=" + airQuantity +
                        ", comfortLevel=" + comfortLevel +
                        ", tempInfo=" + tempInfo +
                        ", updateTime='" + updateTime + '\'' +
                        ", wind=" + wind +
                        '}';
            }

            public List<HoursDTO> getHours() {
                return hours;
            }

            public void setHours(List<HoursDTO> hours) {
                this.hours = hours;
            }

            public AirQuantityDTO getAirQuantity() {
                return airQuantity;
            }

            public void setAirQuantity(AirQuantityDTO airQuantity) {
                this.airQuantity = airQuantity;
            }

            public ComfortLevelDTO getComfortLevel() {
                return comfortLevel;
            }

            public void setComfortLevel(ComfortLevelDTO comfortLevel) {
                this.comfortLevel = comfortLevel;
            }

            public TempInfoDTO getTempInfo() {
                return tempInfo;
            }

            public void setTempInfo(TempInfoDTO tempInfo) {
                this.tempInfo = tempInfo;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public WindDTO getWind() {
                return wind;
            }

            public void setWind(WindDTO wind) {
                this.wind = wind;
            }

            public static class AirQuantityDTO {
                /**
                 * no2
                 */
                @SerializedName("no2")
                private Integer no2;
                /**
                 * pm25
                 */
                @SerializedName("pm25")
                private Integer pm25;
                /**
                 * o3
                 */
                @SerializedName("o3")
                private Integer o3;
                /**
                 * so2
                 */
                @SerializedName("so2")
                private Integer so2;
                /**
                 * pm10
                 */
                @SerializedName("pm10")
                private Integer pm10;
                /**
                 * co
                 */
                @SerializedName("co")
                private Double co;

                @Override
                public String toString() {
                    return "AirQuantityDTO{" +
                            "no2=" + no2 +
                            ", pm25=" + pm25 +
                            ", o3=" + o3 +
                            ", so2=" + so2 +
                            ", pm10=" + pm10 +
                            ", co=" + co +
                            '}';
                }

                public Integer getNo2() {
                    return no2;
                }

                public void setNo2(Integer no2) {
                    this.no2 = no2;
                }

                public Integer getPm25() {
                    return pm25;
                }

                public void setPm25(Integer pm25) {
                    this.pm25 = pm25;
                }

                public Integer getO3() {
                    return o3;
                }

                public void setO3(Integer o3) {
                    this.o3 = o3;
                }

                public Integer getSo2() {
                    return so2;
                }

                public void setSo2(Integer so2) {
                    this.so2 = so2;
                }

                public Integer getPm10() {
                    return pm10;
                }

                public void setPm10(Integer pm10) {
                    this.pm10 = pm10;
                }

                public Double getCo() {
                    return co;
                }

                public void setCo(Double co) {
                    this.co = co;
                }
            }

            public static class ComfortLevelDTO {
                /**
                 * uv
                 */
                @SerializedName("uv")
                private Integer uv;
                /**
                 * dressingIndex
                 */
                @SerializedName("dressingIndex")
                private String dressingIndex;
                /**
                 * humidity
                 */
                @SerializedName("humidity")
                private Integer humidity;
                /**
                 * coldIndex
                 */
                @SerializedName("coldIndex")
                private String coldIndex;
                /**
                 * apparentTemperature
                 */
                @SerializedName("apparentTemperature")
                private Integer apparentTemperature;
                /**
                 * uvIndex
                 */
                @SerializedName("uvIndex")
                private String uvIndex;
                /**
                 * washIndex
                 */
                @SerializedName("washIndex")
                private String washIndex;
                /**
                 * sportIndex
                 */
                @SerializedName("sportIndex")
                private String sportIndex;

                @Override
                public String toString() {
                    return "ComfortLevelDTO{" +
                            "uv=" + uv +
                            ", dressingIndex='" + dressingIndex + '\'' +
                            ", humidity=" + humidity +
                            ", coldIndex='" + coldIndex + '\'' +
                            ", apparentTemperature=" + apparentTemperature +
                            ", uvIndex='" + uvIndex + '\'' +
                            ", washIndex='" + washIndex + '\'' +
                            ", sportIndex='" + sportIndex + '\'' +
                            '}';
                }

                public Integer getUv() {
                    return uv;
                }

                public void setUv(Integer uv) {
                    this.uv = uv;
                }

                public String getDressingIndex() {
                    return dressingIndex;
                }

                public void setDressingIndex(String dressingIndex) {
                    this.dressingIndex = dressingIndex;
                }

                public Integer getHumidity() {
                    return humidity;
                }

                public void setHumidity(Integer humidity) {
                    this.humidity = humidity;
                }

                public String getColdIndex() {
                    return coldIndex;
                }

                public void setColdIndex(String coldIndex) {
                    this.coldIndex = coldIndex;
                }

                public Integer getApparentTemperature() {
                    return apparentTemperature;
                }

                public void setApparentTemperature(Integer apparentTemperature) {
                    this.apparentTemperature = apparentTemperature;
                }

                public String getUvIndex() {
                    return uvIndex;
                }

                public void setUvIndex(String uvIndex) {
                    this.uvIndex = uvIndex;
                }

                public String getWashIndex() {
                    return washIndex;
                }

                public void setWashIndex(String washIndex) {
                    this.washIndex = washIndex;
                }

                public String getSportIndex() {
                    return sportIndex;
                }

                public void setSportIndex(String sportIndex) {
                    this.sportIndex = sportIndex;
                }
            }

            public static class TempInfoDTO {
                /**
                 * maxTemperature
                 */
                @SerializedName("maxTemperature")
                private String maxTemperature;
                /**
                 * uv
                 */
                @SerializedName("uv")
                private String uv;
                /**
                 * minTemperature
                 */
                @SerializedName("minTemperature")
                private String minTemperature;
                /**
                 * temperature
                 */
                @SerializedName("temperature")
                private String temperature;
                /**
                 * weather
                 */
                @SerializedName("weather")
                private String weather;
                /**
                 * humidity
                 */
                @SerializedName("humidity")
                private String humidity;
                /**
                 * air
                 */
                @SerializedName("air")
                private String air;
                /**
                 * apparentTemperature
                 */
                @SerializedName("apparentTemperature")
                private String apparentTemperature;
                /**
                 * label
                 */
                @SerializedName("label")
                private String label;
                /**
                 * day
                 */
                @SerializedName("day")
                private String day;

                @Override
                public String toString() {
                    return "TempInfoDTO{" +
                            "maxTemperature='" + maxTemperature + '\'' +
                            ", uv='" + uv + '\'' +
                            ", minTemperature='" + minTemperature + '\'' +
                            ", temperature='" + temperature + '\'' +
                            ", weather='" + weather + '\'' +
                            ", humidity='" + humidity + '\'' +
                            ", air='" + air + '\'' +
                            ", apparentTemperature='" + apparentTemperature + '\'' +
                            ", label='" + label + '\'' +
                            ", day='" + day + '\'' +
                            '}';
                }

                public String getMaxTemperature() {
                    return maxTemperature;
                }

                public void setMaxTemperature(String maxTemperature) {
                    this.maxTemperature = maxTemperature;
                }

                public String getUv() {
                    return uv;
                }

                public void setUv(String uv) {
                    this.uv = uv;
                }

                public String getMinTemperature() {
                    return minTemperature;
                }

                public void setMinTemperature(String minTemperature) {
                    this.minTemperature = minTemperature;
                }

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public String getHumidity() {
                    return humidity;
                }

                public void setHumidity(String humidity) {
                    this.humidity = humidity;
                }

                public String getAir() {
                    return air;
                }

                public void setAir(String air) {
                    this.air = air;
                }

                public String getApparentTemperature() {
                    return apparentTemperature;
                }

                public void setApparentTemperature(String apparentTemperature) {
                    this.apparentTemperature = apparentTemperature;
                }

                public String getLabel() {
                    return label;
                }

                public void setLabel(String label) {
                    this.label = label;
                }

                public String getDay() {
                    return day;
                }

                public void setDay(String day) {
                    this.day = day;
                }
            }

            public static class WindDTO {
                /**
                 * windStrength
                 */
                @SerializedName("windStrength")
                private String windStrength;
                /**
                 * windDirection
                 */
                @SerializedName("windDirection")
                private String windDirection;

                public String getWindStrength() {
                    return windStrength;
                }

                public void setWindStrength(String windStrength) {
                    this.windStrength = windStrength;
                }

                public String getWindDirection() {
                    return windDirection;
                }

                @Override
                public String toString() {
                    return "WindDTO{" +
                            "windStrength='" + windStrength + '\'' +
                            ", windDirection='" + windDirection + '\'' +
                            '}';
                }

                public void setWindDirection(String windDirection) {
                    this.windDirection = windDirection;
                }
            }

            public static class HoursDTO {
                /**
                 * hour
                 */
                @SerializedName("hour")
                private String hour;
                /**
                 * weather
                 */
                @SerializedName("weather")
                private String weather;
                /**
                 * temperature
                 */
                @SerializedName("temperature")
                private Integer temperature;

                @Override
                public String toString() {
                    return "HoursDTO{" +
                            "hour='" + hour + '\'' +
                            ", weather='" + weather + '\'' +
                            ", temperature=" + temperature +
                            '}';
                }

                public String getHour() {
                    return hour;
                }

                public void setHour(String hour) {
                    this.hour = hour;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public Integer getTemperature() {
                    return temperature;
                }

                public void setTemperature(Integer temperature) {
                    this.temperature = temperature;
                }
            }
        }

        public static class WeatherListDTO {
            /**
             * maxTemperature
             */
            @SerializedName("maxTemperature")
            private String maxTemperature;
            /**
             * uv
             */
            @SerializedName("uv")
            private String uv;
            /**
             * minTemperature
             */
            @SerializedName("minTemperature")
            private String minTemperature;
            /**
             * temperature
             */
            @SerializedName("temperature")
            private String temperature;
            /**
             * weather
             */
            @SerializedName("weather")
            private String weather;
            /**
             * humidity
             */
            @SerializedName("humidity")
            private Integer humidity;
            /**
             * air
             */
            @SerializedName("air")
            private String air;
            /**
             * apparentTemperature
             */
            @SerializedName("apparentTemperature")
            private String apparentTemperature;
            /**
             * label
             */
            @SerializedName("label")
            private String label;
            /**
             * day
             */
            @SerializedName("day")
            private String day;

            public String getMaxTemperature() {
                return maxTemperature;
            }

            @Override
            public String toString() {
                return "WeatherListDTO{" +
                        "maxTemperature='" + maxTemperature + '\'' +
                        ", uv='" + uv + '\'' +
                        ", minTemperature='" + minTemperature + '\'' +
                        ", temperature='" + temperature + '\'' +
                        ", weather='" + weather + '\'' +
                        ", humidity=" + humidity +
                        ", air='" + air + '\'' +
                        ", apparentTemperature='" + apparentTemperature + '\'' +
                        ", label='" + label + '\'' +
                        ", day='" + day + '\'' +
                        '}';
            }

            public void setMaxTemperature(String maxTemperature) {
                this.maxTemperature = maxTemperature;
            }

            public String getUv() {
                return uv;
            }

            public void setUv(String uv) {
                this.uv = uv;
            }

            public String getMinTemperature() {
                return minTemperature;
            }

            public void setMinTemperature(String minTemperature) {
                this.minTemperature = minTemperature;
            }

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public Integer getHumidity() {
                return humidity;
            }

            public void setHumidity(Integer humidity) {
                this.humidity = humidity;
            }

            public String getAir() {
                return air;
            }

            public void setAir(String air) {
                this.air = air;
            }

            public String getApparentTemperature() {
                return apparentTemperature;
            }

            public void setApparentTemperature(String apparentTemperature) {
                this.apparentTemperature = apparentTemperature;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }
        }
    }
}
