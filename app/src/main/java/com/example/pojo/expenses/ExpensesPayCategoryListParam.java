package com.example.pojo.expenses;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author kkk
 */
public class ExpensesPayCategoryListParam extends BaseParam {

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
        return "ExpensesPayCategoryListParam{" +
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
         * liveName
         */
        @SerializedName("liveName")
        private String liveName;
        /**
         * imgUrl
         */
        @SerializedName("imgUrl")
        private String imgUrl;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getLiveName() {
            return liveName;
        }

        public void setLiveName(String liveName) {
            this.liveName = liveName;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        @Override
        public String toString() {
            return "DataDTO{" +
                    "id=" + id +
                    ", liveName='" + liveName + '\'' +
                    ", imgUrl='" + imgUrl + '\'' +
                    '}';
        }
    }
}
