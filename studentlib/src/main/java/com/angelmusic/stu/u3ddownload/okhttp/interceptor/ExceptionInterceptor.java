package com.angelmusic.stu.u3ddownload.okhttp.interceptor;


import com.angelmusic.stu.u3ddownload.okhttp.HttpInfo;

/**
 * 请求链路异常（非业务逻辑）拦截器
 */
public interface ExceptionInterceptor {

    HttpInfo intercept(HttpInfo info) throws Exception;

}
