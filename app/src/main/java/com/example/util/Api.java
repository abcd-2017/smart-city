package com.example.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.activity.useractivity.LoginActivity;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author kkk
 */
public class Api {
    public static final Api api = new Api();
    private static final String TAG = "Api";
    private static String url;
    private static Map<String, Object> params;
    private static Context content;
    private static OkHttpClient client;
    private static String token;
    private Gson gson = new Gson();

    public static Api config(String url, Map<String, Object> params, Context content) {
        Api.url = url;
        Api.params = params;
        Api.content = content;
        token = content.getSharedPreferences(Constant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).getString("token", "");
        client = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .build();
        return api;
    }

    public void getRequest(RequestCallback callback) {
        String requestParam = changeParam();
        Request request = new Request.Builder()
                .get()
                .url(requestParam)
                .addHeader("Authorization", token)
                .build();
        sendRequest(request, callback);
    }

    public void getRestfulRequest(RequestCallback callback, Object... args) {
        String requestParam = changeParamToRestful(args);
        Request request = new Request.Builder()
                .get()
                .url(requestParam)
                .addHeader("Authorization", token)
                .build();
        sendRequest(request, callback);
    }

    public void postRequest(RequestCallback callback) {
        String json = gson.toJson(params);
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(json, mediaType);

        params.clear();
        String requestUrl = changeParam();

        Request request = new Request.Builder()
                .post(requestBody)
                .url(requestUrl)
                .addHeader("Authorization", token)
                .build();

        sendRequest(request, callback);
    }

    public void postRestfulRequest(RequestCallback callback, Object... args) {
        String json = gson.toJson(changeParamToRestful(args));
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(json, mediaType);

        String requestUrl = changeParamToRestful(args);

        Request request = new Request.Builder()
                .post(requestBody)
                .url(requestUrl)
                .addHeader("Authorization", token)
                .build();

        sendRequest(request, callback);
    }

    public void putRequest(RequestCallback callback) {
        String json = gson.toJson(params);
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(json, mediaType);

        params.clear();
        String requestUrl = changeParam();

        Request request = new Request.Builder()
                .put(requestBody)
                .url(requestUrl)
                .addHeader("Authorization", token)
                .build();

        sendRequest(request, callback);
    }

    public void putRestfulRequest(RequestCallback callback, Object... args) {
        String json = gson.toJson(null);
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(json, mediaType);

        String requestParam = changeParamToRestful(args);
        Request request = new Request.Builder()
                .put(requestBody)
                .url(requestParam)
                .addHeader("Authorization", token)
                .build();
        sendRequest(request, callback);
    }

    public void deleteRequest(RequestCallback callback) {
        String requestParam = changeParam();
        Request request = new Request.Builder()
                .delete()
                .url(requestParam)
                .addHeader("Authorization", token)
                .build();
        sendRequest(request, callback);
    }

    public void deleteRestfulRequest(RequestCallback callback, Object... args) {
        String requestParam = changeParamToRestful(args);
        Request request = new Request.Builder()
                .delete()
                .url(requestParam)
                .addHeader("Authorization", token)
                .build();
        sendRequest(request, callback);
    }

    private void sendRequest(Request request, RequestCallback callback) {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d(TAG, "onFailure: e => " + e.getMessage());
                callback.failure(e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                int code = response.code();
                if (response.body() != null) {
                    String result = response.body().string();
                    if (result.contains("code\":401")) {
                        System.out.println("234213423");
                        content.startActivity(new Intent(content, LoginActivity.class));
                    }
                    callback.success(result);
                }
            }
        });
    }

    private String changeParam() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(Constant.BASE_API).
                append(url);
        if (params != null && params.size() != 0) {
            Iterator<Map.Entry<String, Object>> iterator = params.entrySet().iterator();
            buffer.append("?");
            while (iterator.hasNext()) {
                Map.Entry<String, Object> next = iterator.next();
                buffer.append(next.getKey()).append("=").append(next.getValue());
                if (iterator.hasNext()) {
                    buffer.append("&");
                }
            }
        }
        return buffer.toString();
    }

    private String changeParamToRestful(Object... args) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(Constant.BASE_API).
                append(url);
        for (Object arg : args) {
            buffer.append("/").append(arg);
        }
        return buffer.toString();
    }
}
