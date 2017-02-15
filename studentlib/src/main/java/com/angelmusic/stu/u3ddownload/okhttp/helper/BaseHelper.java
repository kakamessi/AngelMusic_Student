package com.angelmusic.stu.u3ddownload.okhttp.helper;

import android.util.Log;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * 业务操作基类：日志拦截与打印、Https验证
 */
abstract class BaseHelper {

    OkHttpClient httpClient;
    private String TAG;
    private long timeStamp;
    private boolean showHttpLog;

    BaseHelper(HelperInfo helperInfo) {
        OkHttpClient.Builder clientBuilder = helperInfo.getClientBuilder();
        clientBuilder.addInterceptor(LOG_INTERCEPTOR);
        setSslSocketFactory(clientBuilder);
        httpClient = clientBuilder.build();
        TAG = helperInfo.getLogTAG();
        timeStamp = helperInfo.getTimeStamp();
        showHttpLog = helperInfo.isShowHttpLog();
    }


    /**
     * 日志拦截器
     */
    private Interceptor LOG_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            long startTime = System.currentTimeMillis();
            showLog(String.format("%s-URL: %s %n",chain.request().method(),
                    chain.request().url()));
            Response res = chain.proceed(chain.request());
            long endTime = System.currentTimeMillis();
            showLog(String.format("CostTime: %.1fs", (endTime-startTime) / 1000f));
            return res;
        }
    };

    /**
     * 设置HTTPS认证
     */
    private void setSslSocketFactory(OkHttpClient.Builder clientBuilder){
        clientBuilder.hostnameVerifier(DO_NOT_VERIFY);
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            X509TrustManager trustManager = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }
                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };
            sc.init(null,new TrustManager[]{trustManager}, new SecureRandom());
            clientBuilder.sslSocketFactory(sc.getSocketFactory(),trustManager);
        } catch (Exception e) {
            showLog("Https认证异常: "+e.getMessage());
        }
    }

    /**
     *主机名验证
     */
    private final HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };


    /**
     * 打印日志
     * @param msg 日志信息
     */
    void showLog(String msg){
        if(showHttpLog)
            Log.d(TAG+"["+timeStamp+"]", msg);
    }


}
