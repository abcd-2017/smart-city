package com.example.util;

import okhttp3.Response;

/**
 * @author kkk
 */
public interface RequestCallback {
    /**
     * 请求成功
     */
    void success(String result);

    /**
     * 请求失败
     */
    void failure(Exception e);
}
