package com.angelmusic.student.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

/**
 * 课程下载界面
 */
public class DownloadActivity extends BaseActivity {

    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.lv_course)

    //--------------------------------------------
    private ListView lvCourse;
    private DownloadNewAdapter adapter;
    //下载标识  1-显示全部下载，2,3
    private int downLoadType = -1;

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
                    initHeadView();

                    break;

                case 2:

                    hideLoadingDialog();
                    adapter.setEmptyData();
                    setHeadViewType(1);

                    break;

                case 3:

                    adapter.refreshProgress();
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

    /**
     *
     * 1 下载， 2暂停 3删除
     */
    private void initView() {

        adapter = new DownloadNewAdapter(this);

        headView = LayoutInflater.from(this).inflate(R.layout.dload_first_item, null);
        btn_dload_all = (Button) headView.findViewById(R.id.btn_dload_all);
        btn_dload_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(downLoadType==1){
                    adapter.downloadAll(true);
                    setHeadViewType(2);

                }else if(downLoadType==2){
                    adapter.downloadAll(false);
                    setHeadViewType(1);

                }else if(downLoadType == 3){

                    showDeleteAll();
                }

            }
        });

        lvCourse.setAdapter(adapter);
        adapter.bindAty(this);

    }

    public void initHeadView(){
        float downNum = 0;
        float allNum = 0;
        for(CourseItemInfo cif : ccourseList){
            downNum = downNum + cif.getDone_num();
            allNum = allNum + cif.getAll_num();
        }
        if(downNum < allNum){
            setHeadViewType(1);
        }else if(downNum == allNum){
            setHeadViewType(3);

        }

    }

    public void refreashAdapter(){

        Message msg = Message.obtain();
        msg.what =3;
        myHandler.sendMessage(msg);

    }

    /**
     * 确定删除全部弹窗
     */
    private void showDeleteAll() {

                /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(DownloadActivity.this);
        normalDialog.setTitle("提醒");
        normalDialog.setMessage("确认删除全部课程资源?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        deleteAll();
                        showLoadingDialog();

                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();


    }

    private void deleteAll() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                File file = new File(Utils.getVideoPath());
                Utils.RecursionDeleteFile(file);

                Message msg = Message.obtain();
                msg.what =2;
                myHandler.sendMessage(msg);

            }
        }).start();

    }

    /**
     * 设置头View
     */
    public void setHeadViewType(int type){
        switch (type){
            case 1:
                btn_dload_all.setText("全部下载");
                downLoadType = 1;
                break;
            case 2:
                btn_dload_all.setText("全部暂停");
                downLoadType = 2;
                break;
            case 3:
                btn_dload_all.setText("全部删除");
                downLoadType = 3;
                break;
        }
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
