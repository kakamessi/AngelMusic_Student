package com.angelmusic.student.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.angelmusic.student.R;
import com.angelmusic.student.batch_download.adapter.DownloadAdapter;
import com.angelmusic.student.base.BaseActivity;
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
public class DownloadActivity extends BaseActivity {

    @BindView(R.id.ib_back)
    ImageButton ibBack;
    @BindView(R.id.btn_dload_all)
    Button btnDloadAll;
    @BindView(R.id.lv_course)
    ListView lvCourse;
    private DownloadAdapter adapter;
    //模拟数据
    private List<List<FileInfo>> fileInfoList;
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
        initData();
        adapter = new DownloadAdapter(this, fileInfoList);
        lvCourse.setAdapter(adapter);
    }

    private void initData() {
        fileInfoList = new ArrayList<>();
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
        fileInfoList.add(listItem1);
        fileInfoList.add(listItem2);
        fileInfoList.add(listItem3);
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
        return R.layout.activity_download;
    }

    @Override
    protected void setTAG() {
        TAG = "==DownloadActivity==";
    }

    @OnClick({R.id.ib_back, R.id.btn_dload_all})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.btn_dload_all:
                CharSequence text = btnDloadAll.getText();
                if (text.equals("全部下载")) {
                    btnDloadAll.setText("全部暂停");
                    btnDloadAll.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.down_all_btn_clicked, 0, 0, 0);
                } else if (text.equals("全部删除")) {
                    btnDloadAll.setText("全部下载");
                    btnDloadAll.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.delete_icon, 0, 0, 0);
                } else if (text.equals("全部暂停")) {
                    btnDloadAll.setText("全部下载");
                    btnDloadAll.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.down_all_btn_normal, 0, 0, 0);
                }
                break;
        }
    }
}
