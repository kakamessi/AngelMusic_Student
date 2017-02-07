package com.okhttp.interceptor;


import com.okhttp.HttpInfo;

/**
 * 请求结果拦截器
 */
public interface ResultInterceptor {

    HttpInfo intercept(HttpInfo info) throws Exception;

}
