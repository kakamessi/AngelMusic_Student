package com.angelmusic.student.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.angelmusic.student.R;
import com.angelmusic.student.adpater.SeatAdapter;
import com.angelmusic.student.base.BaseActivity;
import com.angelmusic.student.infobean.SeatData;
import com.angelmusic.student.utils.NetworkUtil;
import com.angelmusic.student.utils.SharedPreferencesUtil;
import com.angelmusic.student.version_update.ApkManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 主页
 */
public class MainActivity extends BaseActivity {
    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.tv_wifi_name)
    TextView tvWifiName;
    @BindView(R.id.tv_classroom_name)
    TextView tvClassroomName;
    @BindView(R.id.tv_seat_id)
    TextView tvSeatId;
    @BindView(R.id.tv_connection_status)
    TextView tvConnectionStatus;
    @BindView(R.id.ib_download)
    ImageButton ibDownload;
    @BindView(R.id.tv_blackboard_chinese)
    TextView tvBlackboardChinese;
    @BindView(R.id.tv_blackboard_english)
    TextView tvBlackboardEnglish;
    @BindView(R.id.layout_main_01)
    RelativeLayout layoutMain01;
    @BindView(R.id.tv_seat_num)
    TextView tvSeatNum;
    @BindView(R.id.layout_main_02)
    RelativeLayout layoutMain02;
    private List<String> list;
    private String wifiName;
    private String classroomName;
    private String seatId;
    private String pianoConStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApkManager.getInstance(this).checkVersionInfo();//检查版本更新
        initData();
        initView();
    }

    private void initView() {
        tvWifiName.setText(Html.fromHtml("<u>" + wifiName + "</u>"));
        tvClassroomName.setText(Html.fromHtml("<u>" + classroomName + "</u>"));
        tvSeatId.setText(Html.fromHtml("<u>" + seatId + "</u>"));
        tvConnectionStatus.setText(Html.fromHtml("<u>" + pianoConStatus + "</u>"));
        tvSeatNum.setText(seatId);
    }

    private void initData() {
        String seatInfo = SharedPreferencesUtil.getString("seatInfo", null);
        SeatData seatData = getSeatData(seatInfo);
        wifiName = NetworkUtil.getWifiName(this);
        if (seatInfo != null) {
            classroomName = seatData.getClassroomName();
            seatId = seatData.getSeatId();
        } else {
            classroomName = "--";
            seatId = "--";
        }
        pianoConStatus = "未链接";
        list = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            list.add("第" + i + "号座位");
        }
    }

    /**
     * 根据教师端传入的字符串信息切割数据
     */
    private SeatData getSeatData(String seatInfo) {
        SeatData seatData = null;
        if (!TextUtils.isEmpty(seatInfo)) {
            String[] seatInfoArr = seatInfo.split("#");
            seatData = new SeatData();
            seatData.setClassroomName(seatInfoArr[0]);
            seatData.setClassroomId(seatInfoArr[1]);
            seatData.setClassId(seatInfoArr[2]);
            seatData.setSeatId(seatInfoArr[3]);
            seatData.setRowNum(seatInfoArr[4]);
            seatData.setColumnNum(seatInfoArr[5]);
        }
        return seatData;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setTAG() {
        TAG = "==MainActivity==";
    }

    //显示座位号的PopupWindow
    private void showSeatDialog(View view) {
        View contentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.seat_layout, null);
        GridView gridView = (GridView) contentView.findViewById(R.id.gv_seat);
        gridView.setNumColumns(5);//这里动态设置列数
        SeatAdapter adapter = new SeatAdapter(this, list);
        gridView.setAdapter(adapter);
        final PopupWindow popupWindow = new PopupWindow(contentView, 1200, 800);
        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        final WindowManager.LayoutParams wlBackground = getWindow().getAttributes();
        wlBackground.alpha = 0.5f;// 设置PopupWindow以外部分的背景颜色  有一种变暗的效果
        getWindow().setAttributes(wlBackground);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                wlBackground.alpha = 1.0f;// 当PopupWindow消失时,恢复其为原来的颜色
                getWindow().setAttributes(wlBackground);
            }
        });
        //关闭popupWindow的按钮
        contentView.findViewById(R.id.ib_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //设置PopupWindow进入和退出动画
        popupWindow.setAnimationStyle(R.style.PopupAnim);
        // 设置PopupWindow显示在中间
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    @OnClick({R.id.ib_download, R.id.tv_wifi_name, R.id.tv_classroom_name, R.id.tv_seat_id, R.id.tv_connection_status})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_download:
                //跳转到下载页
                startActivity(new Intent(MainActivity.this, DownloadActivity.class));
                break;
            case R.id.tv_wifi_name:
                //预留，后续可添加无网络时点击跳转到设置网络
                break;
            case R.id.tv_classroom_name:
                //预留
                break;
            case R.id.tv_seat_id:
                if (!TextUtils.isEmpty(tvSeatId.getText())) {
                    showSeatDialog(view);
                } else {
                    //向教师端请求数据

                }
                break;
            case R.id.tv_connection_status:
                //预留
                break;
            default:

                break;
        }
    }

    /**
     * 接收教师端消息
     */
    @Override
    protected void handleMsg(Message msg) {
        super.handleMsg(msg);
        //定义协议
        String teacherMsg = msg.obj.toString();
        String substring = teacherMsg.substring(teacherMsg.indexOf("|"));//不确定待定
        if (substring.equals("协商定义的内容")) {
            SharedPreferencesUtil.setString("seatInfo", "需要存储的信息");//存储
        }
    }

    /**
     * 向教师端发送数据
     */
    @Override
    protected void sendMsg(String str) {
        super.sendMsg(str);

    }
}
