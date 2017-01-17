package com.angelmusic.student.login;

import android.content.Context;

import com.angelmusic.stu.utils.Log;
import com.angelmusic.student.R;
import com.angelmusic.student.utils.GsonUtil;
import com.angelmusic.student.utils.Utils;
import com.okhttplib.HttpInfo;
import com.okhttplib.OkHttpUtil;
import com.okhttplib.OkHttpUtilInterface;
import com.okhttplib.callback.CallbackOk;

import java.io.IOException;

import static com.okhttplib.annotation.CacheLevel.FIRST_LEVEL;

/**
 * Created by fei on 2017/1/17.
 */

public class LoginManager {
    private static StuInfo stuInfo;

    public static void login(final Context mContext, String classNo, final IsLoginSucceed isLoginSucceed) {
        String machineCode = Utils.getDeviceId(mContext);
        Log.e("===machineCode===", machineCode);
        OkHttpUtilInterface okHttpUtil = OkHttpUtil.Builder()
                .setCacheLevel(FIRST_LEVEL)
                .setConnectTimeout(25).build(mContext);
        okHttpUtil.doGetAsync(
                HttpInfo.Builder().setUrl(mContext.getResources().getString(R.string.domain_name) + mContext.getResources().getString(R
                        .string.stu_login))
                        .addParam("machineCode", machineCode)
                        .addParam("classNo", classNo)
                        .build(),
                new CallbackOk() {
                    @Override
                    public void onResponse(HttpInfo info) throws IOException {
                        String jsonResult = info.getRetDetail();
                        Log.e("===jsonResult===", jsonResult);
                        if (info.isSuccessful()) {
                            Log.e("======", "--55---");
                            stuInfo = GsonUtil.jsonToObject(jsonResult, StuInfo.class);//Gson解析
                            if (stuInfo.getCode() == 200) {
                                isLoginSucceed.isSucceed();
                                Log.e("======", "--44---");
                            } else {
                                Log.e("======", "--33---");
                                isLoginSucceed.isFailed();
                            }
                        }
                    }
                });

    }

    //回调接口
    public interface IsLoginSucceed {
        void isSucceed();

        void isFailed();
    }
}
