package com.angelmusic.stu.u3dInterface;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.angelmusic.stu.server.socket.constant.Constant;
import com.angelmusic.stu.u3ddownload.HttpInterceptor;
import com.angelmusic.stu.u3ddownload.okhttp.HttpInfo;
import com.angelmusic.stu.u3ddownload.okhttp.OkHttpUtil;
import com.angelmusic.stu.u3ddownload.okhttp.annotation.CacheLevel;
import com.angelmusic.stu.u3ddownload.okhttp.annotation.CacheType;
import com.angelmusic.stu.u3ddownload.okhttp.callback.ProgressCallback;
import com.angelmusic.stu.u3ddownload.okhttp.cookie.PersistentCookieJar;
import com.angelmusic.stu.u3ddownload.okhttp.cookie.cache.SetCookieCache;
import com.angelmusic.stu.u3ddownload.okhttp.cookie.persistence.SharedPrefsCookiePersistor;
import com.angelmusic.stu.utils.SendDataUtil;

/**
 * Created by DELL on 2017/5/25.
 */

public class U3dDownload {

    private OkHttpUtil.Builder init;
    private String path;
    private Context mContext;

    private static volatile U3dDownload singleton = null;

    private U3dDownload(Context context){
        mContext = context;
    }

    public static U3dDownload getSingleton(Context context){
        if(singleton == null){
            synchronized (U3dDownload.class){
                if(singleton == null){
                    singleton = new U3dDownload(context);
                }
            }
        }
        return singleton;
    }

    public void setFilePath(String path){

        init = OkHttpUtil.init(mContext);
        init.setConnectTimeout(30)//连接超时时间
                .setWriteTimeout(30)//写超时时间
                .setReadTimeout(30)//读超时时间
                .setMaxCacheSize(10 * 1024 * 1024)//缓存空间大小
                .setCacheLevel(CacheLevel.FIRST_LEVEL)//缓存等级
                .setCacheType(CacheType.NETWORK_THEN_CACHE)//缓存类型
                .setShowHttpLog(true)//显示请求日志
                .setShowLifecycleLog(true)//显示Activity销毁日志
                .setRetryOnConnectionFailure(false)//失败后不自动重连
                .setDownloadFileDir(Environment.getExternalStorageDirectory().getAbsolutePath() + "/anglefile/")//文件下载保存目录(根据实际需求设置App.init.set...)
                .addResultInterceptor(HttpInterceptor.ResultInterceptor)//请求结果拦截器
                .addExceptionInterceptor(HttpInterceptor.ExceptionInterceptor)//请求链路异常拦截器
                .setCookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(mContext)))//持久化cookie
                .build();

        //Toast.makeText(mContext,mContext.getExternalFilesDir(null).getAbsolutePath() + "/video/",0).show();

    }

    /**
     * U3d调用
     * @param urls
     * @param isStart
     */
    public void startDownload(String urls, String flag, String names, String isStart){

            if(isStart.equals("1")){
                downloadFile(urls,flag,names);
            }else{
                OkHttpUtil.Builder().build().cancelRequest(names);
            }

    }


    /**
     * 单个文件的下载
     */
    private void downloadFile(final String url, final String flag, String fileName) {

        final String fileNameCutSuffix = fileName.substring(0, fileName.lastIndexOf("."));//文件名，不带后缀（因为使用的网络框架下载完文件后会自动添加后缀名）
        final HttpInfo info = HttpInfo.Builder()
                .addDownloadFile(url, fileNameCutSuffix, new ProgressCallback() {
                    @Override
                    public void onProgressMain(int percent, long bytesWritten, long contentLength, boolean done) {
//                        LogUtil.e("----percent----", "=" + percent);
                        if (done) {
                            //返回u3d url表明下载成功
                            SendDataUtil.sendDataToUnity(U3dType.TYPE_Communication,U3dType.METHOD_ON_ODR,url
                                    +U3dType.FLAG_CUT + flag
                                    +U3dType.FLAG_CUT + 1);
                        }
                    }
                    @Override
                    public void onResponseMain(String fileUrl, HttpInfo info) {
                        //下载异常中断，刷新UI界面状态
                        if(info.getRetCode()!=HttpInfo.SUCCESS){

                            SendDataUtil.sendDataToUnity(U3dType.TYPE_Communication,U3dType.METHOD_ON_ODR,url
                                    +U3dType.FLAG_CUT + flag
                                    +U3dType.FLAG_CUT + 0);

                        }
                    }
                })
                .build();
                OkHttpUtil.Builder().setReadTimeout(120).setDownloadFileDir(mContext.getExternalFilesDir(null).getAbsolutePath() + "/video/").build(fileName)//绑定请求标识
                .doDownloadFileAsync(info);
    }

}
