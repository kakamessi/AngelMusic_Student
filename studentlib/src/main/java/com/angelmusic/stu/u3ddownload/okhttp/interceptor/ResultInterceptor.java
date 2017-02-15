package com.angelmusic.stu.u3ddownload.okhttp.interceptor;


import com.angelmusic.stu.u3ddownload.okhttp.HttpInfo;

/**
 * 请求结果拦截器
 */
public interface ResultInterceptor {

    HttpInfo intercept(HttpInfo info) throws Exception;

}
