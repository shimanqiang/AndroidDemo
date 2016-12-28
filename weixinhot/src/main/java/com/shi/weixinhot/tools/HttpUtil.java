package com.shi.weixinhot.tools;

import android.util.Log;

import java.io.IOException;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/12/25.
 */

public class HttpUtil {
    private final static String TAG = HttpUtil.class.getSimpleName();

    private static OkHttpClient okHttpClient;

    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient();
        }
        return okHttpClient;
    }

    public interface ResponseCallBack extends Callback {

    }


    /**
     * 异步执行 post请求
     *
     * @param url
     * @param params
     * @param callback
     */
    public static void executePost(String url, Map<String, String> params, ResponseCallBack callback) {
        // 创建okHttpClient对象
        FormBody.Builder builder = new FormBody.Builder();
        if (params != null) {
            Log.d(TAG, params.toString());
            try {
                for (String key : params.keySet()) {
                    String tempVal = params.get(key) == null ? "" : params.get(key);
                    builder.add(key, tempVal);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "请求出错", e);
            }
        }
        FormBody body = builder.build();
        Request request = new Request.Builder().url(url).post(body).build();
        getOkHttpClient().newCall(request).enqueue(callback);
    }

    /**
     * 同步执行get请求
     *
     * @param url
     * @return
     */
    public static Response executeGetSync(String url) {
        Request request = new Request.Builder().url(url).build();
        try {
            return getOkHttpClient().newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
