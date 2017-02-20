package com.angelmusic.student.login;

import android.content.Context;

import com.angelmusic.stu.utils.Log;
import com.angelmusic.student.R;
import com.angelmusic.student.utils.GsonUtil;
import com.angelmusic.student.utils.Utils;
import com.angelmusic.stu.u3ddownload.okhttp.HttpInfo;
import com.angelmusic.stu.u3ddownload.okhttp.OkHttpUtil;
import com.angelmusic.stu.u3ddownload.okhttp.OkHttpUtilInterface;
import com.angelmusic.stu.u3ddownload.okhttp.callback.CallbackOk;

import java.io.IOException;

import static com.angelmusic.stu.u3ddownload.okhttp.annotation.CacheLevel.FIRST_LEVEL;

/**
 * Created by fei on 2017/1/17.
 */

public class LoginManager {
    private static StuInfo stuInfo;

    public static void login(final Context mContext, String classNo, final IsLoginSucceed isLoginSucceed) {
        String machineCode = Utils.getDeviceId(mContext);
        OkHttpUtilInterface okHttpUtil = OkHttpUtil.Builder()
                .setCacheLevel(FIRST_LEVEL)
                .setConnectTimeout(25).build(mContext);
        okHttpUtil.doPostAsync(
                HttpInfo.Builder().setUrl(mContext.getResources().getString(R.string.domain_name_request) + mContext.getResources().getString(R
                        .string.stu_login))
                        .addParam("machineCode", machineCode)
                        .addParam("classNo", classNo)
                        .build(),
                new CallbackOk() {
                    @Override
                    public void onResponse(HttpInfo info) throws IOException {
                        String jsonResult = info.getRetDetail();
                        Log.e("kaka","登录：" + jsonResult);
                        if (info.isSuccessful()) {
                            stuInfo = GsonUtil.jsonToObject(jsonResult, StuInfo.class);//Gson解析
                            if (stuInfo.getCode() == 200) {
                                isLoginSucceed.isSucceed(stuInfo);
                            } else {
                                isLoginSucceed.isFailed();
                            }
                        }
                    }
                });

    }

    //回调接口
    public interface IsLoginSucceed {
        void isSucceed(StuInfo stuInfo);

        void isFailed();
    }
}
