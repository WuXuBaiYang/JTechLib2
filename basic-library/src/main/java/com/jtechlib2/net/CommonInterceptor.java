package com.jtechlib2.net;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * 通用拦截器
 */
class CommonInterceptor implements Interceptor {
    private Map<String, String> headerMap;

    CommonInterceptor(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        //拼接请求，添加头
        Request.Builder builder = chain.request()
                .newBuilder();
        if (null != headerMap) {
            for (String key : headerMap.keySet()) {
                String value = headerMap.get(key);
                builder.addHeader(key, Objects.requireNonNull(value));
            }
        }
        return chain.proceed(builder.build());
    }
}