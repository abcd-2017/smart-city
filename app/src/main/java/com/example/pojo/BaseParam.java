package com.example.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * @author kkk
 */
public class BaseParam {

    /**
     * msg
     */
    @SerializedName("msg")
    private String msg;
    /**
     * code
     */
    @SerializedName("code")
    private Integer code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "BaseParam{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                '}';
    }

    public static class DateDTO {

    }
}
