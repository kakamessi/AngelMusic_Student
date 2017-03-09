package com.angelmusic.student.version_update;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.angelmusic.stu.u3ddownload.okhttp.HttpInfo;
import com.angelmusic.stu.u3ddownload.okhttp.OkHttpUtil;
import com.angelmusic.stu.u3ddownload.okhttp.OkHttpUtilInterface;
import com.angelmusic.stu.u3ddownload.okhttp.bean.DownloadFileInfo;
import com.angelmusic.stu.u3ddownload.okhttp.callback.CallbackOk;
import com.angelmusic.stu.u3ddownload.okhttp.callback.ProgressCallback;
import com.angelmusic.student.R;
import com.angelmusic.student.base.App;
import com.angelmusic.student.utils.FileUtil;
import com.angelmusic.student.utils.GsonUtil;
import com.angelmusic.student.utils.SDCardUtil;

import java.io.File;
import java.io.IOException;

import static com.angelmusic.stu.u3ddownload.okhttp.annotation.CacheLevel.FIRST_LEVEL;


/**
 * Created by fei on 2016/12/13.
 * APP版本升级的核心管理类
 */

public class ApkManager {
    private Context mContext;
    private static ApkManager apkManager;
    private DownloadFileInfo fileInfo = null;
    private ApkVersionInfo apkVersionInfo;
    private final String APK_PATH = "apk_update";
    private Dialog downloadDialog;

    public ApkManager(Context mContext) {
        this.mContext = mContext;
    }

    public static ApkManager getInstance(Context mContext) {
        if (apkManager == null) {
            apkManager = new ApkManager(mContext);
        }
        return apkManager;
    }

    /**
     * 请求服务器检查是否需要更新
     */
    public void checkVersionInfo() {
//        ((BaseActivity) mContext).showLoadingDialog();//显示加载进度圆圈
        OkHttpUtilInterface okHttpUtil = OkHttpUtil.Builder()
                .setCacheLevel(FIRST_LEVEL)
                .setConnectTimeout(25).build(mContext);
        okHttpUtil.doGetAsync(
                HttpInfo.Builder().setUrl(mContext.getResources().getString(R.string.domain_name_request) + mContext.getResources().getString(R
                        .string.apk_check_version)).addParam("type", "2").build(), new CallbackOk() {
                    @Override
                    public void onResponse(HttpInfo info) throws IOException {
                        String jsonResult = info.getRetDetail();
//                        ((BaseActivity) mContext).hideLoadingDialog();//关闭旋转进度圆
                        if (info.isSuccessful()) {
                            apkVersionInfo = GsonUtil.jsonToObject(jsonResult, ApkVersionInfo.class);//Gson解析
                            if (apkVersionInfo.getCode() == 200) {

                                int serverCode = Integer.parseInt(apkVersionInfo.getDetail().getCode());
                                int versionCode = Integer.parseInt(ApkUtil.getVersionCode(mContext));
                                if(serverCode>versionCode){
                                    showUpdateDialog(apkVersionInfo);
                                }

//                                if (!apkVersionInfo.getDetail().getCode().equals(ApkUtil.getVersionCode(mContext))) {
//                                    showUpdateDialog(apkVersionInfo);
//                                }

                            } else {
                                Toast.makeText(mContext, apkVersionInfo.getDescription(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    /**
     * 弹出新版本更新提示
     *
     * @param apkVersionInfo 更新内容类信息
     */
    public void showUpdateDialog(final ApkVersionInfo apkVersionInfo) {
        //截取URL后半部分作为文件名
        final String apkName = apkVersionInfo.getDetail().getUrl().substring(apkVersionInfo.getDetail().getUrl().lastIndexOf("/") + 1);
        //拼接apk的下载地址
        final String apkUrl = mContext.getResources().getString(R.string.domain_name_download) + apkVersionInfo.getDetail().getUrl();//apk的下载地址
        //下载的apk的存储路径
        final String apkPath = SDCardUtil.getAppFilePath(mContext) + APK_PATH + File.separator;
        //弹框
        final Dialog updateDialog = new Dialog(mContext, R.style.CustomAlertDialogBackground);
        updateDialog.setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(mContext).inflate(R.layout.version_update_dialog, null);
        final Button btnOk = (Button) view.findViewById(R.id.btn_update_id_ok);
        final Button btnCancel = (Button) view.findViewById(R.id.btn_update_id_cancel);
        final TextView tvContent = (TextView) view.findViewById(R.id.tv_update_content);
        final TextView tvUpdateTile = (TextView) view.findViewById(R.id.tv_update_title);
        tvUpdateTile.setText("最新版本：" + apkVersionInfo.getDetail().getCode());
        tvContent.setText(apkVersionInfo.getDetail().getInfo());
        //判断是否强制更新
        final int isForced = apkVersionInfo.getDetail().getIsforced();
        if (isForced == 1) {//非强制更新
            btnCancel.setVisibility(View.VISIBLE);
        } else if (isForced == 2) {//强制更新
            btnCancel.setVisibility(View.GONE);
            updateDialog.setCancelable(false);
        }
        updateDialog.setContentView(view);
        updateDialog.show();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDialog.dismiss();
                //首先检查下载路径下是否已经下载了该apk
                if (FileUtil.isFileExist(apkPath, apkName)) {
                    //新版本已经下载直接安装
                    initApk(apkPath + apkName, isForced);
                } else {
                    showDownloadingDialog(apkUrl, apkPath, apkName, isForced);
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDialog.dismiss();
            }
        });

    }

    /**
     * 显示apk下载进度的对话框
     */
    public void showDownloadingDialog(final String apkUrl, final String apkPath, final String apkName, int isForced) {
        downloadDialog = new AlertDialog.Builder(mContext, R.style.CustomAlertDialogBackground).create();
        downloadDialog.setCancelable(false);
        if (isForced == 1) {//非强制更新
            downloadDialog.setCanceledOnTouchOutside(true);
        } else if (isForced == 2) {//强制更新
            downloadDialog.setCanceledOnTouchOutside(false);
        }
        downloadDialog.show();
        View view = LayoutInflater.from(mContext).inflate(R.layout.apk_download_layout, null);
        downloadDialog.setContentView(view);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        final TextView tvProgress = (TextView) view.findViewById(R.id.tv_progress_apk_download);
        downloadApk(apkUrl, apkPath, apkName, progressBar, tvProgress, isForced);
    }

    /**
     * 断点下载
     */
    private void downloadApk(final String apkUrl, final String apkPath, final String apkName, final ProgressBar downloadProgress, final
    TextView tvResult, final int isForced) {
        App.init.setDownloadFileDir(apkPath);//设置下载路径
        String fileName = apkName.substring(0, apkName.lastIndexOf("."));//将传入的文件名去掉后缀，因为下载后会自动添加后缀名
        if (null == fileInfo)
            fileInfo = new DownloadFileInfo(apkUrl, fileName, new ProgressCallback() {
                @Override
                public void onProgressMain(int percent, long bytesWritten, long contentLength, boolean done) {
                    downloadProgress.setProgress(percent);
                    tvResult.setText(percent + "%");
                }

                @Override
                public void onResponseMain(String fileUrl, HttpInfo info) {
                    if (info.isSuccessful()) {
                        //下载完成自动安装apk
                        initApk(apkPath + apkName, isForced);
                        downloadDialog.dismiss();//下载完成关闭下载进度对话框
                    } else {
                        Toast.makeText(mContext, info.getRetDetail(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        HttpInfo info = HttpInfo.Builder().addDownloadFile(fileInfo).build();
        OkHttpUtil.Builder().setReadTimeout(120).build(this).doDownloadFileAsync(info);
    }

    /**
     * 安装APK
     */
    private void initApk(String apkPath, int isForced) {
        //如果本地存在下载的安装包则直接安装
        File apkFile = new File(apkPath);
        if (!apkFile.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        mContext.startActivity(intent);
        if (isForced == 2) {
            System.exit(0);
        }
    }
}
