package com.angelmusic.student.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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
                HttpInfo.Builder().setUrl(domainName + getResources().getString(R.string
                        .course_info)).addParam
                        ("courseId", "----")//需要传入课程id参数
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
        fileInfoLists = new ArrayList<>();
        fileInfoLists.add(null);//第一条数据设置为null,保证第一条item显示全部下载的那个界面
        for (CourseInfo.DetailBean detailBean : detail) {
            String courseName = detailBean.getName();//课程名：“第n节课”
            List<FileInfo> listItem = new ArrayList<>();
            List<CourseInfo.DetailBean.SonPartBeanX> sonPart = detailBean.getSonPart();
            for (CourseInfo.DetailBean.SonPartBeanX sonPartBeanX : sonPart) {
                List<CourseInfo.DetailBean.SonPartBeanX.SonPartBean> sonPart1 = sonPartBeanX.getSonPart();
                for (CourseInfo.DetailBean.SonPartBeanX.SonPartBean sonPartBean : sonPart1) {
                    String video_uploadPath = sonPartBean.getVideo_uploadPath();//每个文件的后半部分url
                    String fileName = video_uploadPath.substring(1);//带后缀的文件名
                    String fileUrl = domainName + coursePartUrl + video_uploadPath;
                    listItem.add(new FileInfo(courseName, fileName, fileUrl, courseParentPath, 0, 0));
                }
            }
            fileInfoLists.add(listItem);
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
