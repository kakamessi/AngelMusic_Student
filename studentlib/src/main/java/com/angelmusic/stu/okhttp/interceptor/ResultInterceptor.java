package com.angelmusic.stu.okhttp.interceptor;


import com.angelmusic.stu.okhttp.HttpInfo;

/**
 * 请求结果拦截器
 */
public interface ResultInterceptor {

    HttpInfo intercept(HttpInfo info) throws Exception;

}
