package com.angelmusic.student.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.angelmusic.stu.bean.UnityInterface;
import com.angelmusic.stu.u3ddownload.utils.GsonUtil;
import com.angelmusic.stu.usb.UsbDeviceConnect;
import com.angelmusic.stu.usb.UsbDeviceInfo;
import com.angelmusic.stu.usb.callback.CallbackInterface;
import com.angelmusic.stu.utils.SendDataUtil;
import com.angelmusic.student.R;
import com.angelmusic.student.adpater.SeatAdapter;
import com.angelmusic.student.base.App;
import com.angelmusic.student.base.BaseActivity;
import com.angelmusic.student.constant.Constant;
import com.angelmusic.student.core.ActionType;
import com.angelmusic.student.core.music.MusicNote;
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
    @BindView(R.id.iv_quku)
    ImageView iv_quku;

    private String wifiName;
    private String schoolName;
    private String roomName;
    private String seatId;
    private String pianoConStatus;
    private SeatDataInfo seatDataInfo;
    private PopupWindow popupWindow;

    private static String[] PERMISSION = {Manifest.permission.READ_PHONE_STATE};
    protected static final String ACTION_USB_PERMISSION = "com.Aries.usbhosttest.USB_PERMISSION";

    private QuKuFragment qukuFragment  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApkManager.getInstance(this).checkVersionInfo();//检查版本更新
        initData();
        initView();
        initPiano();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (islacksOfPermission(PERMISSION[0])) {
            ActivityCompat.requestPermissions(this, PERMISSION, 0x12);
        } else {

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MusicNote.setPianoAction(MainActivity.this,MusicNote.ACTION_UNMUTE);
        UsbDeviceInfo.getUsbDeviceInfo(MainActivity.this).colse();
    }

    @Override
    protected void setTAG() {
        TAG = "MainActivity";
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_main;
    }

    /**
     * 数据初始化
     */
    private void initData() {
        wifiName = NetworkUtil.getWifiName(this);//获取当前pad连接的wifi名称
        seatId = SharedPreferencesUtil.getString("seatNo", "00");
        roomName = SharedPreferencesUtil.getString("roomName", "00");
        schoolName = SharedPreferencesUtil.getString("schoolName", "天使音乐");
        pianoConStatus = "未链接";//钢琴是否连接
    }

    private void initView() {
        tvBlackboard.setText(schoolName);
        tvWifiName.setText(Html.fromHtml("<u>" + wifiName + "</u>"));
        tvClassroomName.setText(Html.fromHtml("<u>" + roomName + "</u>"));
        tvSeatId.setText(Html.fromHtml("<u>" + seatId + "</u>"));
        tvConnectionStatus.setText(Html.fromHtml("<u>" + pianoConStatus + "</u>"));
    }

    private void initPiano() {
        final IntentFilter filter = new IntentFilter();
        filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        filter.addAction(ACTION_USB_PERMISSION);
        registerReceiver(mUsbReceiver, filter);

//        updateDevice();
//        connectDevice();
        setPiano();
    }

    // 更新设备列表
    public boolean updateDevice() {
        return UsbDeviceInfo.getUsbDeviceInfo(MainActivity.this).update();
    }

    // 连接设备
    public void connectDevice() {
        UsbDeviceInfo.getUsbDeviceInfo(MainActivity.this).connect();
    }

    @OnClick({R.id.ib_download, R.id.tv_wifi_name, R.id.tv_classroom_name, R.id.seatId_ll, R.id.tv_connection_status, R.id.iv_quku})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_download:
                //跳转到下载页
                startActivity(new Intent(MainActivity.this, DownloadActivity.class));
                overridePendingTransition(R.anim.bottom_in, R.anim.bottom_out);

                break;
            case R.id.tv_wifi_name:
                wifiName = NetworkUtil.getWifiName(this);//获取当前pad连接的wifi名称
                tvWifiName.setText(Html.fromHtml("<u>" + wifiName + "</u>"));
                break;
            case R.id.tv_classroom_name:
                //预留
                break;
            case R.id.seatId_ll:
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
            case R.id.iv_quku:
                addFragment();

                break;
            default:

                break;
        }
    }

    /**
     * 显示所有座位号列表的弹框
     */
    private void showSeatPopup(View view) {
        View contentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.seat_layout, null);
        GridView gridView = (GridView) contentView.findViewById(R.id.gv_seat);
        if (seatDataInfo == null) {
            String seatInfoJson = SharedPreferencesUtil.getString("seatInfoJson", null);
            seatDataInfo = GsonUtil.jsonToObject(seatInfoJson, SeatDataInfo.class);
        }
        if (seatDataInfo != null) {
            gridView.setNumColumns(seatDataInfo.getColumnNo());//这里动态设置列数
        }
        SeatAdapter adapter = new SeatAdapter(this, seatDataInfo);
        gridView.setAdapter(adapter);
        final PopupWindow popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
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


    /**
     * 弹框显示当前pad的座位号
     *
     * @param seatId 当前PAD座位号
     */
    private void showSeatIdPopupWindow(String seatId) {
        View contentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.seat_id_layout, null);
        TextView tvSeatNum = (TextView) contentView.findViewById(R.id.tv_seat_num);
        tvSeatNum.setText(seatId);
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(true);
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
     * 接收教师端消息
     */
    @Override
    protected void handleMsg(Message msg) {
        super.handleMsg(msg);
        String teacherMsg = msg.obj.toString();
        String[] ac = teacherMsg.split("\\|");

        if (!TextUtils.isEmpty(teacherMsg) && "2".equals(teacherMsg.substring(0, 1))) {
            String json = teacherMsg.substring(2);
            seatDataInfo = GsonUtil.jsonToObject(json, SeatDataInfo.class);
            if (seatDataInfo != null) {
                showSeatIdPopupWindow(seatDataInfo.getSeatNo());
                //设置界面显示
                tvSeatId.setText(seatDataInfo.getSeatNo());
                tvClassroomName.setText(seatDataInfo.getRoomName());
                tvBlackboard.setText(seatDataInfo.getSchoolName());
                //存储信息
                SharedPreferencesUtil.setString("seatInfoJson", json);
                SharedPreferencesUtil.setString("seatNo", seatDataInfo.getSeatNo());
                SharedPreferencesUtil.setString("roomName", seatDataInfo.getRoomName());
                SharedPreferencesUtil.setString("schoolName", seatDataInfo.getSchoolName());
                SharedPreferencesUtil.setString("schoolId", seatDataInfo.getSchoolID());//课程信息下载需要此参数
            }
        } else if (ActionType.ACTION_PREPARE.equals(ac[0])) {

            //开始进行常规课
            App.getApplication().getCd().setCourse_Id(ac[1]);

            //整合视频资源
            String[] names = ac[2].split("&");
            String sdDir = Environment.getExternalStorageDirectory().getAbsolutePath() + Constant.FILE_PATH;
            //String sdDir = this.getFilesDir().getAbsolutePath() + "/video/";

            App.getApplication().getCd().getFiles().clear();
            for (String name : names) {
                App.getApplication().getCd().getFiles().put(name, sdDir + name);
            }

            startActivity(new Intent(this, VideoActivity.class));

        } else if (ActionType.ACTION_LOGIN.equals(ac[0])) {

            //登录操作
            login(ac[1]);

        } else if (ActionType.ACTION_GET_CLASS.equals(ac[0])) {

            //保存班级id
            //SharedPreferencesUtil.setString(Constant.CACHE_CLASS_ID, ac[1]);

        }

    }

    /**
     * 向教师端发送数据
     */
    @Override
    protected void sendMsg(String str) {
        super.sendMsg(str);

    }

    private UnityInterface.OnUpdateListener updateListener;

    public void stopConnect() {
        UsbDeviceInfo.getUsbDeviceInfo(MainActivity.this).stopConnect();
    }

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //Log.i(TAG, "BroadcastReceiver-->" + action);
            String status = null;
            switch (action) {
                case UsbManager.ACTION_USB_DEVICE_ATTACHED:
                    status = "usb-insert";
                    //Log.i(TAG, "检测到有USB插口接入-->" + action);
                    Toast.makeText(context, "检测到有USB插口接入", Toast.LENGTH_SHORT).show();
                    setPiano();
                    // updateDeviceList();
                    if (updateListener != null) {
                        updateListener.onUpdate();
                    }
                    break;
                case UsbManager.ACTION_USB_DEVICE_DETACHED:
                    status = "usb-discrete";
                    //Log.i(TAG, "USB线被拔出-->" + action);
                    Toast.makeText(context, "USB线被拔出", Toast.LENGTH_SHORT).show();
                    UsbDeviceInfo.getUsbDeviceInfo(MainActivity.this).colse();
                    if (updateListener != null) {
                        updateListener.onUpdate();
                    }
                    // clear();
                    break;
                case ACTION_USB_PERMISSION:
                    boolean isconnect = false;
                    // 判断用户点击的是取消还是确认
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED,
                            false)) {
                        //Log.i(TAG, "连接权限被允许-->" + action);
                        Toast.makeText(context, "连接权限被允许", Toast.LENGTH_SHORT)
                                .show();
                        isconnect = UsbDeviceInfo.getUsbDeviceInfo(
                                MainActivity.this).getUsbDeviceConnection();

                        //Log.d(TAG, "连接-->" + isconnect);
                    } else {
                        stopConnect();
                        //Log.i(TAG, "连接权限被取消-->" + action);
                    }
                    status = isconnect ? "link-success" : "link-fail";

                    //连上钢琴 开启静音模式
                    MusicNote.setPianoAction(MainActivity.this,MusicNote.ACTION_MUTE);

                    break;
            }
            if (status != null) {
                // 发送状态到unity
                SendDataUtil.sendDataToUnity(UnityInterface.cameraName,
                        UnityInterface.sendStatusAddress, status);
            }
        }
    };

    private void setPiano() {

        UsbDeviceConnect.setCallbackInterface(new CallbackInterface() {
            @Override
            public void onReadCallback(String str) {
                Message msg = Message.obtain();
                msg.what = 1;
                msg.obj = str;
                if(mBaseApp.getVideoHandler()!=null){
                    mBaseApp.getVideoHandler().sendMessage(msg);
                }
            }
            @Override
            public void onSendCallback(boolean isSend) {
            }
            @Override
            public void onConnect(boolean isConnected) {
                if(isConnected){
                    MusicNote.setPianoAction(MainActivity.this,MusicNote.ACTION_MUTE);
                }
            }

        });
        updateDevice();
        connectDevice();

    }

    private boolean islacksOfPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.checkSelfPermission(this, permission) ==
                    PackageManager.PERMISSION_DENIED;
        }
        return false;
    }


    /*
    *
    * Fragment相关-------------------------------------------------------------------------
    *
    * */
    public void addFragment() {
        FragmentManager fm = getSupportFragmentManager();
        qukuFragment = (QuKuFragment) fm.findFragmentById(R.id.layout_main_01);
        if(qukuFragment == null )
        {
            qukuFragment = new QuKuFragment();
            fm.beginTransaction().add(R.id.layout_main_01,qukuFragment).addToBackStack(null).commit();
        }
    }

    public void popFragment() {

        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack();

    }



}
