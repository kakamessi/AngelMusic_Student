package com.angelmusic.student.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.angelmusic.stu.utils.Log;
import com.angelmusic.student.R;
import com.angelmusic.student.base.BaseActivity;
import com.angelmusic.student.batch_download.adapter.DownloadAdapter;
import com.angelmusic.student.batch_download.db.DAO2Impl;
import com.angelmusic.student.batch_download.infobean.CourseInfo;
import com.angelmusic.student.batch_download.infobean.FileInfo;
import com.angelmusic.student.utils.GsonUtil;
import com.angelmusic.student.utils.SDCardUtil;
import com.angelmusic.stu.okhttp.HttpInfo;
import com.angelmusic.stu.okhttp.OkHttpUtil;
import com.angelmusic.stu.okhttp.OkHttpUtilInterface;
import com.angelmusic.stu.okhttp.callback.CallbackOk;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.media.CamcorderProfile.get;
import static android.view.View.X;
import static com.angelmusic.stu.okhttp.annotation.CacheLevel.FIRST_LEVEL;

/**
 * 课程下载界面
 */
public class DownloadActivity extends BaseActivity {

    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.lv_course)
    ListView lvCourse;
    private String domainName;//域名
    private String coursePartUrl;//文件的部分下载地址
    private String courseParentPath;//文件存放的路径
    private CourseInfo courseInfo;//网络下载封装成的课程信息总类
    private List<List<FileInfo>> fileInfoLists;//适配器需要传入的数据
    private DownloadAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new DownloadAdapter(this);
        lvCourse.setAdapter(adapter);
        initData();
    }

    //网络请求数据
    private void initData() {
        domainName = getResources().getString(R.string.domain_name);
        coursePartUrl = getResources().getString(R.string.course_part_path);
        courseParentPath = SDCardUtil.getAppFilePath(this) + "course" + File.separator;
        OkHttpUtilInterface okHttpUtil = OkHttpUtil.Builder()
                .setCacheLevel(FIRST_LEVEL)
                .setConnectTimeout(25).build(this);
        okHttpUtil.doGetAsync(
                HttpInfo.Builder().setUrl("http://192.168.1.179/client/course/findCoursePart.json").addParam
                        ("schoolId", "1")//需要传入课程id参数
                        .build(),
                new CallbackOk() {
                    @Override
                    public void onResponse(HttpInfo info) throws IOException {
                        String jsonResult = info.getRetDetail();
                        if (info.isSuccessful()) {
                            courseInfo = GsonUtil.jsonToObject(jsonResult, CourseInfo.class);//Gson解析
                            if (courseInfo.getCode() == 200) {
                                //封装课程下载数据List<List<FileInfo>>
                                List<List<FileInfo>> packageData = packageData(courseInfo.getDetail());
                                if (packageData != null) {
                                    adapter.setData(packageData);
                                }
                                //强退后再进入时将所有下载中的按钮设置成暂停
                                for (int i = 1; i < fileInfoLists.size(); i++) {
                                    String courseName = fileInfoLists.get(i).get(1).getCourseName();
                                    if (DAO2Impl.getInstance(DownloadActivity.this).queryIsExist(courseName)) {
                                        DAO2Impl.getInstance(DownloadActivity.this).updateLoadingState(courseName, "1");
                                    }
                                }
                            } else {
                                Toast.makeText(DownloadActivity.this, courseInfo.getDescription(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    /**
     * 封装适配器的数据
     */
    private List<List<FileInfo>> packageData(List<CourseInfo.DetailBean> detail) {
        Log.e("==========", "=detail.size=" + detail.size());
        fileInfoLists = new ArrayList<>();
        fileInfoLists.add(null);//第一条数据设置为null,保证第一条item显示全部下载的那个界面
        if (detail != null) {
            for (CourseInfo.DetailBean detailBean : detail) {//遍历detail
                List<FileInfo> listItem = new ArrayList<>();//每new一个listItem代表一节课
                String courseName = detailBean.getName();//课程名：“第N节课”
                List<CourseInfo.DetailBean.SonPartBeanX> sonPart1 = detailBean.getSonPart();//第一层sonPart
                for (CourseInfo.DetailBean.SonPartBeanX sonPartBeanX1 : sonPart1) {//遍历第一层sonPart
                    List<CourseInfo.DetailBean.SonPartBeanX> sonPart2 = sonPartBeanX1.getSonPart();//第二层sonPart
                    for (CourseInfo.DetailBean.SonPartBeanX sonPartBeanX2 : sonPart2) {//遍历第二层sonPart
                        List<CourseInfo.DetailBean.SonPartBeanX> sonPart3 = sonPartBeanX2.getSonPart();//第三层sonPart
                        if (sonPart3 != null) {
                            for (CourseInfo.DetailBean.SonPartBeanX sonPartBeanX3 : sonPart3) {//遍历
                                List<CourseInfo.DetailBean.SonPartBeanX> sonPart4 = sonPartBeanX3.getSonPart();
                                String video_uploadPath3 = sonPartBeanX3.getVideo_uploadPath();
                                if (!TextUtils.isEmpty(video_uploadPath3)) {
                                    Log.e("-------------4----------", video_uploadPath3);
                                    String fileName = video_uploadPath3.substring(1);//带后缀的文件名
                                    String fileUrl = domainName  + video_uploadPath3;
                                    listItem.add(new FileInfo(courseName, fileName, fileUrl, courseParentPath, 0, 0));
                                }
                            }
                        } else {
                            String video_uploadPath2 = sonPartBeanX2.getVideo_uploadPath();
                            if (!TextUtils.isEmpty(video_uploadPath2)) {
                                Log.e("-------------3----------", video_uploadPath2);
                                String fileName = video_uploadPath2.substring(1);//带后缀的文件名
                                String fileUrl = domainName + coursePartUrl + video_uploadPath2;
                                listItem.add(new FileInfo(courseName, fileName, fileUrl, courseParentPath, 0, 0));
                            }
                        }
                    }
                }
                if (listItem != null)
                    fileInfoLists.add(listItem);
            }
        }
        return fileInfoLists;
    }


    @Override
    protected int setContentViewId() {
        return R.layout.activity_download;
    }

    @Override
    protected void setTAG() {
        TAG = "==DownloadActivity==";
    }

    @OnClick({R.id.ib_back})
    public void onClick(View view) {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.top_in, R.anim.top_out);
    }
}
