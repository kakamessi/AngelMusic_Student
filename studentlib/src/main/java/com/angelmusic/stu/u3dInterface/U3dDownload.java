package com.angelmusic.stu.u3dInterface;

import com.angelmusic.stu.u3ddownload.okhttp.HttpInfo;
import com.angelmusic.stu.u3ddownload.okhttp.OkHttpUtil;
import com.angelmusic.stu.u3ddownload.okhttp.callback.ProgressCallback;
import com.angelmusic.stu.utils.SendDataUtil;

/**
 * Created by DELL on 2017/5/25.
 */

public class U3dDownload {

    private static volatile U3dDownload singleton = null;

    private U3dDownload(){}

    public static U3dDownload getSingleton(){
        if(singleton == null){
            synchronized (U3dDownload.class){
                if(singleton == null){
                    singleton = new U3dDownload();
                }
            }
        }
        return singleton;
    }

    /**
     * U3d调用
     * @param urls
     * @param isStart
     */
    public void startDownload(String[] urls, String[] names, boolean isStart){

        for(int i=0; i<urls.length; i++){
            if(isStart){
                downloadFile(urls[i],names[i]);
            }else{
                OkHttpUtil.Builder().build().cancelRequest(names[i]);
            }
        }

    }


    /**
     * 单个文件的下载
     */
    private void downloadFile(final String url, String fileName) {

        final String fileNameCutSuffix = fileName.substring(0, fileName.lastIndexOf("."));//文件名，不带后缀（因为使用的网络框架下载完文件后会自动添加后缀名）
        final HttpInfo info = HttpInfo.Builder()
                .addDownloadFile(url, fileNameCutSuffix, new ProgressCallback() {
                    @Override
                    public void onProgressMain(int percent, long bytesWritten, long contentLength, boolean done) {
//                        LogUtil.e("----percent----", "=" + percent);
                        if (done) {
                            //返回u3d url表明下载成功
                            SendDataUtil.sendDataToUnity(U3dType.TYPE_Main_Camera,U3dType.METHOD_ON_ODR,url);
                        }
                    }
                    @Override
                    public void onResponseMain(String fileUrl, HttpInfo info) {
                        //下载异常中断，刷新UI界面状态
                        if(info.getRetCode()!=HttpInfo.SUCCESS){
                        }
                    }
                })
                .build();
                OkHttpUtil.Builder().setReadTimeout(120).build(fileName)//绑定请求标识
                .doDownloadFileAsync(info);
    }

}
