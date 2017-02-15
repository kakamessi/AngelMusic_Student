package com.angelmusic.student.base;


import com.angelmusic.stu.u3ddownload.okhttp.HttpInfo;
import com.angelmusic.stu.u3ddownload.okhttp.interceptor.ExceptionInterceptor;
import com.angelmusic.stu.u3ddownload.okhttp.interceptor.ResultInterceptor;

/**
 * Http拦截器
 * 1、请求结果统一预处理拦截器
 * 2、请求链路异常信息拦截器
 */
public class HttpInterceptor {


    /**
     * 请求结果统一预处理拦截器
     * 该拦截器会对所有网络请求返回结果进行预处理并修改
     */
    public static ResultInterceptor ResultInterceptor = new ResultInterceptor() {
        @Override
        public HttpInfo intercept(HttpInfo info) throws Exception {
            //请求结果预处理
            return info;
        }
    };

    /**
     * 请求链路异常信息拦截器
     * 该拦截器会发送网络请求时链路异常信息进行拦截处理
     */
    public static ExceptionInterceptor ExceptionInterceptor = new ExceptionInterceptor() {
        @Override
        public HttpInfo intercept(HttpInfo info) throws Exception {
            switch (info.getRetCode()) {
                case HttpInfo.NonNetwork:
                    info.setRetDetail("网络中断");
                    break;
                case HttpInfo.NoResult:
                    info.setRetDetail("服务器内部错误");
                    break;
                case HttpInfo.CheckURL:
                    info.setRetDetail("网络地址错误");
                    break;
                case HttpInfo.ProtocolException:
                    info.setRetDetail("协议类型错误");
                    break;
                case HttpInfo.CheckNet:
                    info.setRetDetail("请检查网络连接是否正常");
                    break;
                case HttpInfo.ConnectionTimeOut:
                    info.setRetDetail("连接超时");
                    break;
                case HttpInfo.WriteAndReadTimeOut:
                    info.setRetDetail("读写超时");
                    break;
                case HttpInfo.ConnectionInterruption:
                    info.setRetDetail("连接中断");
                    break;
            }
            return info;
        }
    };


}
