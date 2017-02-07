package com.angelmusic.student.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.angelmusic.student.R;
import com.angelmusic.student.base.BaseActivity;
import com.angelmusic.student.batch_download.adapter.DownloadAdapter;
import com.angelmusic.student.batch_download.db.DAO2Impl;
import com.angelmusic.student.batch_download.infobean.FileInfo;
import com.angelmusic.student.utils.SDCardUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 课程下载界面
 */
public class DownloadTestActivity extends BaseActivity {

    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.lv_course)
    ListView lvCourse;
    private DownloadAdapter adapter;
    //模拟数据
    private List<List<FileInfo>> fileInfoLists;
    private String[] urls1 = new String[]{
            "http://pic29.nipic.com/20130524/12835793_143405446113_2.jpg",//===1
            "http://www.qqpk.cn/article/uploadfiles/201111/20111108212457686.jpg",//===2
            "http://video.angelmusic360.com/video/20161012/next8.mp4",//===3
            "http://video.angelmusic360.com/video/20161012/next9.mp4",//===4
            "http://video.angelmusic360.com/video/20161026/B04-2-1.mp4",//===5
            "http://video.angelmusic360.com/video/20161109/B04-5-1.mp4",//===6
            "http://video.angelmusic360.com/video/20161109/B04-5-2.mp4",//===7
            "http://video.angelmusic360.com/txt/20161014/B04T2Point.txt",//===8
            "http://video.angelmusic360.com/stave/20161014/B04T2.png"//===9
    };
    private String[] urls2 = new String[]{
            "http://video.angelmusic360.com/video/20161109/B04-5-1.mp4",
            "http://video.angelmusic360.com/video/20161026/B04-2-4.mp4",//===10
            "http://video.angelmusic360.com/video/20161012/next8.mp4",
            "http://video.angelmusic360.com/video/20161012/next9.mp4",
            "http://video.angelmusic360.com/video/20161012/B04T1.mp4",//===11
            "http://video.angelmusic360.com/stave/20161014/B04T2.png"
    };
    private String[] urls3 = new String[]{
            "http://video.angelmusic360.com/video/20161109/B04-5-1.mp4",
            "http://video.angelmusic360.com/video/20161109/B04-5-2.mp4",
            "http://video.angelmusic360.com/video/20161026/B04-2-1.mp4",
            "http://video.angelmusic360.com/video/20161026/B04-2-4.mp4",
            "http://video.angelmusic360.com/video/20161012/next8.mp4",
            "http://video.angelmusic360.com/txt/20161014/B04T2MIDI.txt",//===12
            "http://video.angelmusic360.com/txt/20161014/B04T2Point.txt",
            "http://video.angelmusic360.com/stave/20161014/B04T2.png"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new DownloadAdapter(this);
        lvCourse.setAdapter(adapter);
        initData();
    }

    private void initData() {
        fileInfoLists = new ArrayList<>();
        //假数据，共3节课，每节课中有重复的文件
        //第一节课
        List<FileInfo> listItem1 = new ArrayList<>();
        for (int j = 0; j < 9; j++) {
            listItem1.add(new FileInfo("第一节课", getFileName(urls1[j]), urls1[j], SDCardUtil.getAppFilePath(this) + "COURSE" + File.separator, 0, 0));
        }
        List<FileInfo> listItem2 = new ArrayList<>();
        for (int j = 0; j < 6; j++) {
            listItem2.add(new FileInfo("第二节课", getFileName(urls2[j]), urls2[j], SDCardUtil.getAppFilePath(this) + "COURSE" + File.separator, 0, 0));
        }
        List<FileInfo> listItem3 = new ArrayList<>();
        for (int j = 0; j < 8; j++) {
            listItem3.add(new FileInfo("第三节课", getFileName(urls3[j]), urls3[j], SDCardUtil.getAppFilePath(this) + "COURSE" + File.separator, 0, 0));
        }
        fileInfoLists.add(null);//第一条设置成null
        fileInfoLists.add(listItem1);
        fileInfoLists.add(listItem2);
        fileInfoLists.add(listItem3);
        adapter.setData(fileInfoLists);
        for (int i = 1; i < fileInfoLists.size(); i++) {
            String courseName = fileInfoLists.get(i).get(1).getCourseName();
            if (DAO2Impl.getInstance(this).queryIsExist(courseName)) {
                DAO2Impl.getInstance(this).updateLoadingState(courseName, "1");
            }
        }
    }

    //切割url获取文件名，带后缀
    private String getFileName(String fileUrl) {
        if (fileUrl != null) {
            return fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        }
        return null;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_download_test;
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
