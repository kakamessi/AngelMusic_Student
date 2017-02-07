package com.okhttp.interceptor;


import com.okhttp.HttpInfo;

/**
 * 请求链路异常（非业务逻辑）拦截器
 */
public interface ExceptionInterceptor {

    HttpInfo intercept(HttpInfo info) throws Exception;

}
