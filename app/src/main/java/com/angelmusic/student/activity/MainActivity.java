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
import com.angelmusic.student.infobean.SeatDataInfo;
import com.angelmusic.student.utils.NetworkUtil;
import com.angelmusic.student.utils.SharedPreferencesUtil;
import com.angelmusic.student.version_update.ApkManager;

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
    @BindView(R.id.tv_blackboard)
    TextView tvBlackboard;
    @BindView(R.id.layout_main_01)
    RelativeLayout layoutMain01;
    private SeatDataInfo seatDataInfo;
    private String wifiName;
    private String schoolName;
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
        tvBlackboard.setText(schoolName);
        tvSeatId.setText(Html.fromHtml("<u>" + seatId + "</u>"));
        tvConnectionStatus.setText(Html.fromHtml("<u>" + pianoConStatus + "</u>"));
    }

    /**
     * 数据初始化
     */
    private void initData() {
        wifiName = NetworkUtil.getWifiName(this);//获取当前pad连接的wifi名称
        String seatInfoString = SharedPreferencesUtil.getString("seatInfo", null);//本地获取学校id,班级id等等信息，这是从教师端获取后存储在本地的String串
        if (seatInfoString == null) {
            //如果本地不存在数据则请求教师数据
        } else {
            //如果有数据则将教师端发过来的字符串数据切割封装成SeatDataInfo类
            seatDataInfo = getSeatDataInfo(seatInfoString);
        }
        if (seatDataInfo != null) {
            classroomName = seatDataInfo.getClassroomName();
            schoolName = seatDataInfo.getSchoolName();
            seatId = seatDataInfo.getSeatId();
        } else {
            schoolName = "天使音乐";
            classroomName = "00";
            seatId = "00";
        }
        pianoConStatus = "未链接";//钢琴是否连接
    }

    /**
     * 将教师端发过来的字符串数据切割封装成SeatDataInfo类,还需要和教师端协商再修改
     */
    private SeatDataInfo getSeatDataInfo(String seatInfo) {
        SeatDataInfo seatDataInfo = null;
        if (!TextUtils.isEmpty(seatInfo)) {
            String[] seatInfoArr = seatInfo.split("#");
            seatDataInfo = new SeatDataInfo();
//            seatDataInfo.setSchoolName(seatInfoArr[]);
//            seatDataInfo.setSchoolId(seatInfoArr[]);
//            seatDataInfo.setClassroomName(seatInfoArr[]);
//            seatDataInfo.setClassroomId(seatInfoArr[]);
//            seatDataInfo.setClassId(seatInfoArr[]);
//            seatDataInfo.setSeatId(seatInfoArr[]);
//            seatDataInfo.setRowNum(seatInfoArr[]);
//            seatDataInfo.setColumnNum(seatInfoArr[]);
        }
        return seatDataInfo;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setTAG() {
        TAG = "==MainActivity==";
    }

    //显示所有座位号列表的弹框
    private void showSeatPopup(View view) {
        View contentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.seat_layout, null);
        GridView gridView = (GridView) contentView.findViewById(R.id.gv_seat);
        gridView.setNumColumns(5);//这里动态设置列数
        SeatAdapter adapter = new SeatAdapter(this);
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
                tvSeatId.setClickable(true);//防止重复点击
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
                    tvSeatId.setClickable(false);
                    showSeatPopup(view);
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

    private PopupWindow popupWindow;

    /**
     * 弹框显示当前pad的座位号
     *
     * @param seatId 当前PAD座位号
     */
    private void showSeatIdPopupWindow(String seatId) {
        View contentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.seat_id_layout, null);
        TextView tvSeatNum = (TextView) contentView.findViewById(R.id.tv_seat_num);
        tvSeatNum.setText(seatId);
        popupWindow = new PopupWindow(contentView, 800, 800);
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
        //设置PopupWindow进入和退出动画
        popupWindow.setAnimationStyle(R.style.PopupAnim);
        // 设置PopupWindow显示在中间
        popupWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);
    }

    /**
     * 去掉显示座位号的弹框
     */
    private void hideSeatIdPopupWindow() {
        popupWindow.dismiss();
    }

    /**
     * 接收教师端消息
     */
    @Override
    protected void handleMsg(Message msg) {
        super.handleMsg(msg);
        //需要和教师端定义协议
        String teacherMsg = msg.obj.toString();
        String substring = teacherMsg.substring(teacherMsg.indexOf("|"));//不确定待定
        if (substring.equals("协商定义的内容")) {
            SharedPreferencesUtil.setString("seatInfo", "需要存储的信息字符串");//存储
            SeatDataInfo seatDataInfo = getSeatDataInfo(substring);
            showSeatIdPopupWindow(seatDataInfo.getSeatId());
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
