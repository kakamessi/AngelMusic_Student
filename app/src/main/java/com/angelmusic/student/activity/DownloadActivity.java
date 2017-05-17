package com.angelmusic.student.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.angelmusic.stu.u3ddownload.okhttp.HttpInfo;
import com.angelmusic.stu.u3ddownload.okhttp.OkHttpUtil;
import com.angelmusic.stu.u3ddownload.okhttp.OkHttpUtilInterface;
import com.angelmusic.stu.u3ddownload.okhttp.callback.CallbackOk;
import com.angelmusic.student.R;
import com.angelmusic.student.base.BaseActivity;
import com.angelmusic.student.course_download.adapter.DownloadNewAdapter;
import com.angelmusic.student.course_download.infobean.CourseBean;
import com.angelmusic.student.course_download.infobean.CourseInfo;
import com.angelmusic.student.course_download.infobean.CourseItemInfo;
import com.angelmusic.student.course_download.infobean.FileInfo;
import com.angelmusic.student.course_download.infobean.NewCourseInfo;
import com.angelmusic.student.course_download.infobean.PathBean;
import com.angelmusic.student.utils.GsonUtil;
import com.angelmusic.student.utils.SDCardUtil;
import com.angelmusic.student.utils.SharedPreferencesUtil;
import com.angelmusic.student.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.angelmusic.stu.u3ddownload.okhttp.annotation.CacheLevel.FIRST_LEVEL;
import static com.angelmusic.student.R.id.btn_dload_all;
import static com.angelmusic.student.R.id.circleProgress;

/**
 * 课程下载界面
 */
public class DownloadActivity extends BaseActivity {

    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.lv_course)
    ListView lvCourse;
    private String domainNameRequest;//信息请求域名
    private String domainNameDownload;//下载文件的域名
    private String courseInfoJson;//获取课程信息json的接口
    private String courseParentPath;//文件存放的路径
    private CourseInfo courseInfo;//网络下载封装成的课程信息总类
    private List<List<FileInfo>> fileInfoLists;//适配器需要传入的数据
    private DownloadNewAdapter adapter;
    private String schoolId;//学校ID


    //--------------------------------------------
    private List<CourseItemInfo> ccourseList = new ArrayList<CourseItemInfo>();
    private NewCourseInfo nci;//网络下载封装成的课程信息总类
    private Button btn_dload_all;
    private View headView;

    private Handler myHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case 1:
                    adapter.setData(ccourseList);
                    break;

            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        initView();
        initCourse();
    }

    private boolean isPause = true;
    private void initView() {

        adapter = new DownloadNewAdapter(this);

        headView = LayoutInflater.from(this).inflate(R.layout.dload_first_item, null);
        btn_dload_all = (Button) headView.findViewById(R.id.btn_dload_all);
        btn_dload_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPause){
                    adapter.downloadAll(true);
                    isPause = false;
                    btn_dload_all.setText("全部暂停");
                }else{
                    adapter.downloadAll(false);
                    isPause = true;
                    btn_dload_all.setText("全部下载");
                }
            }
        });

        lvCourse.setAdapter(adapter);

    }

    /**
     *
     * 初始化课程信息
     *
     * 获取对应课程信息，与本地文件比对，初始化出已经下载信息
     *
     */
    private void initCourse() {

        String schoolId = SharedPreferencesUtil.getString("schoolId", "2");
        String domainNameRequest = getResources().getString(R.string.domain_name_request);
        String courseInfoJson = getResources().getString(R.string.newcourse_info_json);

        OkHttpUtilInterface okHttpUtil = OkHttpUtil.Builder()
                .setCacheLevel(FIRST_LEVEL)
                .setConnectTimeout(25).build(this);
        okHttpUtil.doGetAsync(
                HttpInfo.Builder().setUrl(domainNameRequest + courseInfoJson).addParam
                        ("schoolId", schoolId)//需要传入课程id参数
                        .build(),
                new CallbackOk() {
                    @Override
                    public void onResponse(HttpInfo info) throws IOException {

                        final String jsonResult = info.getRetDetail();
                        if (info.isSuccessful()) {

                            lvCourse.addHeaderView(headView);

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    nci = GsonUtil.jsonToObject(jsonResult, NewCourseInfo.class);//Gson解析
                                    if (nci.getCode() == 200) {
                                        //封装数据
                                        List<CourseBean> lp = nci.getDetail();
                                        for(int i=0; i<lp.size(); i++){

                                            List<PathBean> lb = lp.get(i).getPathList();
                                            CourseItemInfo cii = new CourseItemInfo();

                                            int done_num = 0;

                                            for(PathBean pb : lb){
                                                cii.getResUrl().put(pb.getVideoName(), getResources().getString(R.string.domain_name_download) + pb.getVideoPath());
                                            }

                                            //检测已下载文件数量
                                            for (Map.Entry<String, String> entry : cii.getResUrl().entrySet()) {
                                                //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                                                if(Utils.isFileExist(entry.getKey())){
                                                    done_num++;
                                                }
                                            }

                                            cii.setCourse_name(lp.get(i).getName());
                                            cii.setAll_num(cii.getResUrl().size());
                                            cii.setDone_num(done_num);
                                            if(cii.getDone_num()==cii.getAll_num()){
                                                cii.setIsActive(4);
                                            }else{
                                                cii.setIsActive(1);
                                            }

                                            ccourseList.add(cii);
                                        }

                                        Message msg = Message.obtain();
                                        msg.what =1;
                                        myHandler.sendMessage(msg);

                                    }
                                }
                            }).start();
                        }
                    }
                });
    }


    //网络请求数据
    private void initData() {
        schoolId = SharedPreferencesUtil.getString("schoolId", "1");
        domainNameRequest = getResources().getString(R.string.domain_name_request);
        courseInfoJson = getResources().getString(R.string.course_info_json);
        courseParentPath = SDCardUtil.getAppFilePath(this) + "course" + File.separator;
        OkHttpUtilInterface okHttpUtil = OkHttpUtil.Builder()
                .setCacheLevel(FIRST_LEVEL)
                .setConnectTimeout(25).build(this);
        okHttpUtil.doGetAsync(
                HttpInfo.Builder().setUrl(domainNameRequest + courseInfoJson).addParam
                        ("schoolId", schoolId)//需要传入课程id参数
                        .build(),
                new CallbackOk() {
                    @Override
                    public void onResponse(HttpInfo info) throws IOException {

                        String jsonResult = info.getRetDetail();
                        if (info.isSuccessful()) {
                            courseInfo = GsonUtil.jsonToObject(jsonResult, CourseInfo.class);//Gson解析
                            if (courseInfo.getCode() == 200) {
                                //封装数据



                            } else {
                                Toast.makeText(DownloadActivity.this, courseInfo.getDescription(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                });
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
