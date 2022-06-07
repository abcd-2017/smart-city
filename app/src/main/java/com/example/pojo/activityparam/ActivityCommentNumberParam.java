package com.example.pojo.activityparam;

import com.example.pojo.BaseParam;
import com.google.gson.annotations.SerializedName;

/**
 * @author kkk
 */
public class ActivityCommentNumberParam extends BaseParam {

    /**
     * commentNum
     */
    @SerializedName("commentNum")
    private Integer commentNum;


    @Override
    public String toString() {
        return "ActivityCommentNumberParam{" +
                ", commentNum=" + commentNum +
                '}';
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

}
