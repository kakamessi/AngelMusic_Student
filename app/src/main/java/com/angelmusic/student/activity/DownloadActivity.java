package com.angelmusic.student.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.angelmusic.student.R;
import com.angelmusic.student.adpater.DownloadAdapter;
import com.angelmusic.student.base.BaseActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i + "");
        }
        adapter = new DownloadAdapter(this, list);
        lvCourse.setAdapter(adapter);
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
