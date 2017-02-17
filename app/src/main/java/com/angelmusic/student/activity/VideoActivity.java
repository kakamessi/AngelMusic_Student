package com.angelmusic.student.activity;

import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.angelmusic.stu.usb.UsbDeviceConnect;
import com.angelmusic.stu.usb.UsbDeviceInfo;
import com.angelmusic.stu.usb.callback.CallbackInterface;
import com.angelmusic.stu.utils.Log;
import com.angelmusic.student.R;
import com.angelmusic.student.base.App;
import com.angelmusic.student.base.BaseActivity;
import com.angelmusic.student.core.ActionType;
import com.angelmusic.student.core.music.MusicNote;
import com.angelmusic.student.core.music.NoteInfo;
import com.angelmusic.student.infobean.CourseData;
import com.angelmusic.student.utils.LogUtil;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.angelmusic.student.core.music.MusicNote.music_g;

public class VideoActivity extends BaseActivity {


    @BindView(R.id.black_tv)
    TextView blackTv;
    @BindView(R.id.surface_view)
    SurfaceView surfaceView;
    @BindView(R.id.iv_yinfu_bg_ll_1)
    LinearLayout ivYinfuBgLl1;
    @BindView(R.id.white_key_ll_1)
    LinearLayout whiteKeyLl1;
    @BindView(R.id.iv_yinfu_bg_ll_up_2)
    LinearLayout ivYinfuBgLlUp2;
    @BindView(R.id.iv_yinfu_bg_ll_down_2)
    LinearLayout ivYinfuBgLlDown2;
    @BindView(R.id.white_key_ll_2)
    LinearLayout whiteKeyLl2;
    @BindView(R.id.iv_yinfu_bg_ll_up_3)
    LinearLayout ivYinfuBgLlUp3;
    @BindView(R.id.iv_yinfu_bg_ll_down_3)
    LinearLayout ivYinfuBgLlDown3;
    @BindView(R.id.white_key_ll_3)
    LinearLayout whiteKeyLl3;
    @BindView(R.id.iv_yinfu_bg_ll_4)
    LinearLayout ivYinfuBgLl4;
    @BindView(R.id.white_key_ll_4)
    LinearLayout whiteKeyLl4;
    @BindView(R.id.yuepu_group_ll)
    LinearLayout yuepuGroupLl;


    //课程信息
    private CourseData cd = App.getApplication().getCd();
    private String currentPath = "";
    private File currentfile = null;


    private MediaPlayer mediaPlayer;
    private int currentPosition = 0;
    private boolean isPlaying;
    private int swich = 0;

    /*记录钢琴弹奏输出*/
    private ArrayList<String> notes = new ArrayList<String>();
    /* 课程资源索引 */
    private int music_num = 1;

    /*------------------------------------------------------------------------------收到钢琴消息handler*/
    private Handler pianoHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            String str = (String) msg.obj;
            notes.add(str);
            Log.e(TAG, str + ":" + str.length());
            //根据钢琴输出是否正确，来显示界面音符变化，亮灯操作
            handlerNewNote(str);

        }
    };

    int index_new = 0;

    private void handlerNewNote(String str) {

        //确认选谱
        ArrayList<NoteInfo> al = MusicNote.note_1ist[music_num];

        //获取钢琴弹奏音符
        String[] myDatas = str.substring(str.indexOf("=") + 1).split(" ");
        int key = Integer.parseInt(myDatas[2], 16) - 21;

        //处理输出
        NoteInfo ni = al.get(index_new);
        NoteInfo nextInfo = index_new + 1 > al.size() - 1 ? al.get(0) : al.get(index_new + 1);

        if (key == ni.getNoteNum()) {

            //显示正确音符 和 钢琴键   setYinfuBgColor(ni.getNoteIndex(), ni.isRed()==true?Color.RED:Color.BLUE);
            if (music_num == 0) {
                setViewStyle(1, nextInfo.getNoteIndex(), nextInfo.isRed() == true ? Color.RED : Color.BLUE, nextInfo.getKeyIndex(), nextInfo.isRed() == true ? Color.RED : Color.BLUE);

            } else if (music_num == 1) {

                if (index_new < 11) {
                    setViewStyle(2, nextInfo.getNoteIndex(), nextInfo.isRed() == true ? Color.RED : Color.BLUE, nextInfo.getKeyIndex(), nextInfo.isRed() == true ? Color.RED : Color.BLUE);
                } else {
                    setViewStyle(3, nextInfo.getNoteIndex(), nextInfo.isRed() == true ? Color.RED : Color.BLUE, nextInfo.getKeyIndex(), nextInfo.isRed() == true ? Color.RED : Color.BLUE);
                }

            } else if (music_num == 2) {

                setViewStyle(4, nextInfo.getNoteIndex() + 1, nextInfo.isRed() == true ? Color.RED : Color.BLUE, nextInfo.getKeyIndex(), nextInfo.isRed() == true ? Color.RED : Color.BLUE);
            }

            //处理循环
            if (str.endsWith("0 ")) {

                if (index_new == al.size() - 1) {
                    index_new = -1;
                }
                index_new++;

                //亮灯

            }
        }

    }

    /*------------------------------------------------------------------------------收到钢琴消息handler*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        closePiano();
        initView();
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_video;
    }

    @Override
    protected void setTAG() {
        TAG = "VideoActivity";
    }

    private void initPiano() {

        UsbDeviceConnect.setCallbackInterface(new CallbackInterface() {
            @Override
            public void onReadCallback(String str) {

                Message msg = Message.obtain();
                msg.obj = str;
                pianoHandler.sendMessage(msg);

            }

            @Override
            public void onSendCallback(boolean isSend) {

            }

        });

        UsbDeviceInfo.getUsbDeviceInfo(this).update();
        UsbDeviceInfo.getUsbDeviceInfo(this).connect();

    }

    private void closePiano() {
        UsbDeviceInfo.getUsbDeviceInfo(this).stopConnect();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        stop();

    }

    private void initView() {
        // 为SurfaceHolder添加回调
        surfaceView.getHolder().addCallback(callback);
        blackTv.setText("准备中");

        blackTv.setVisibility(View.VISIBLE);
        yuepuGroupLl.setVisibility(View.INVISIBLE);

        setLayoutStyle(2);
    }

    @Override
    protected void handleMsg(Message msg) {

        String str = msg.obj.toString();
        String[] ac = str.split("\\|");
        Log.e(TAG, "消息入口:  ---------- " + str);

        //播放，切换视频
        if (ActionType.ACTION_PLAY.equals(ac[0])) {

            if (ac[1].equals("0")) {
                Log.e(TAG, "动作:  ---------- " + "投大屏");
                stop();
                setLayoutStyle(1);

            } else {
                setLayoutStyle(3);
                String path = cd.getFiles().get(ac[2]);

                switchVedio(path);
//              currentPath = path;
//              play(0);

                Log.e(TAG, "动作:  ---------- " + "投学生屏开始播放视频：" + path);
            }

            //暂停继续播放视频
        } else if (ActionType.ACTION_PAUSE_RESUME.equals(ac[0])) {

            if (ac[1].equals("2")) {

                Log.e(TAG, "动作:  ---------- " + "下课：");
                this.finish();

            } else {

                pause();
                Log.e(TAG, "动作:  ---------- " + "暂停继续：");
            }


        } else if (ActionType.ACTION_GZ_ONE.equals(ac[0])) {

            stop();
            //小学2 培训 幼儿园
            music_num = Integer.parseInt(ac[1]) - 1;
            setLayoutStyle(2);

        }

    }

    private void setLayoutStyle(int type) {

        if (type == 1) {
            //请看大屏幕
            blackTv.setVisibility(View.VISIBLE);
            blackTv.setText("请看大屏幕");
            yuepuGroupLl.setVisibility(View.INVISIBLE);

        } else if (type == 2) {
            //乐谱跟奏
            initPiano();
            blackTv.setVisibility(View.INVISIBLE);
            yuepuGroupLl.setVisibility(View.VISIBLE);

            /* 初始化界面显示的时候 默认高亮音符信息 */
            if (music_num == 0) {

                setViewStyle(1, 1, Color.RED, 8, Color.RED);

            } else if (music_num == 1) {

                setViewStyle(2, 1, Color.BLUE, 8, Color.BLUE);

            } else if (music_num == 2) {

                setViewStyle(4, 1, Color.RED, 8, Color.RED);
            }

        } else if (type == 3) {
            //播放视频
            blackTv.setVisibility(View.INVISIBLE);
            yuepuGroupLl.setVisibility(View.INVISIBLE);

        }

    }

    @OnClick({R.id.black_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.black_tv:
                //dialog7();
                break;
        }
    }

    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback() {
        // SurfaceHolder被修改的时候回调
        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            LogUtil.i("SurfaceHolder 被销毁");
            // 销毁SurfaceHolder的时候记录当前的播放位置并停止播放
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                currentPosition = mediaPlayer.getCurrentPosition();
                mediaPlayer.stop();
            }
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            LogUtil.i("SurfaceHolder 被创建");
            if (currentPosition > 0) {
                // 创建SurfaceHolder的时候，如果存在上次播放的位置，则按照上次播放位置进行播放
                play(currentPosition);
                currentPosition = 0;
            }

        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {
            LogUtil.i("SurfaceHolder 大小被改变");
        }

    };

    private OnSeekBarChangeListener change = new OnSeekBarChangeListener() {

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // 当进度条停止修改的时候触发
            // 取得当前进度条的刻度
            int progress = seekBar.getProgress();
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                // 设置当前播放的位置
                mediaPlayer.seekTo(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {

        }
    };

    /**
     * 切换视频
     */
    protected void switchVedio(String path) {
        currentPath = path;
        stop();

        isPlaying = false;
        play(0);

    }

    /**
     * 开始播放
     *
     * @param msec 播放初始位置
     */
    protected void play(final int msec) {
        // 获取视频文件地址
        String path = currentPath;

        File file = new File(path);
        if (!file.exists()) {
            Toast.makeText(this, "视频文件路径错误", Toast.LENGTH_LONG).show();
            return;
        }
        currentfile = file;
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            // 设置播放的视频源
            mediaPlayer.setDataSource(file.getAbsolutePath());
            // 设置显示视频的SurfaceHolder
            mediaPlayer.setDisplay(surfaceView.getHolder());
            LogUtil.e("开始装载:   " + file.getAbsolutePath());
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer mp) {

                    LogUtil.i("装载完成");
                    mediaPlayer.start();
                    // 按照初始位置播放
                    mediaPlayer.seekTo(msec);

                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    // 在播放完毕被回调

                }
            });

            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {

                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    // 发生错误重新播放
                    play(0);
                    isPlaying = false;
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("视频播放Exception：                               " + e.toString());
        }
    }

    /**
     * 暂停或继续
     */
    protected void pause() {

        if (mediaPlayer == null) {
            return;
        }

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {

            mediaPlayer.pause();
            return;

        } else {

            mediaPlayer.start();
            return;
        }

    }

    /*
    * 停止播放
    */
    protected void stop() {
        if (mediaPlayer != null) {
            // && mediaPlayer.isPlaying()  某些异常情况下可以加上判断

            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;

            isPlaying = false;
        }
    }

    /**
     * 重新开始播放
     */
    protected void replay() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(0);

            return;
        }
        isPlaying = false;
        play(0);


    }

    private String setPlayPath() {
        String result = "";

//        int rr = new Random().nextInt(1);
//        if (rr == 0) {
//            result = "/sdcard/ykzzldx.mp4";
//        } else if(rr ==1){
//            result = "/sdcard/hehe.mp4";
//        }else {
//            result = "/sdcard/ffff.mp4";
//        }

        if (swich % 2 == 0) {
            result = "/sdcard/ykzzldx.mp4";
        } else if (swich % 2 == 1) {
            result = "/sdcard/hehe.mp4";
        }
        swich++;

        currentPath = result;
        return result;
    }

    protected void dialog7() {

        int starNum = 1;
        int yg = 1;
        int jz = 1;
        int sz = 1;

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_score, null);

        // TODO: 2016/5/17 创建PopupWindow对象，指定宽度和高度 1221, 1134  733  680
        PopupWindow window = new PopupWindow(layout, 1221, 1134);
        // TODO: 2016/5/17 设置背景颜色
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        // TODO: 2016/5/17 设置可以获取焦点
        window.setFocusable(true);
        // TODO: 2016/5/17 设置可以触摸弹出框以外的区域
        window.setOutsideTouchable(true);
        // TODO：更新popupwindow的状态
        window.update();
        // TODO: 2016/5/17 以下拉的方式显示，并且可以设置显示的位置
        window.showAtLocation(findViewById(R.id.activity_video), Gravity.CENTER, 0, 0);


        ImageView ivBG = (ImageView) layout.findViewById(R.id.imageView);

        ImageView iv_1 = (ImageView) layout.findViewById(R.id.iv_yingao_full);
        ImageView iv_2 = (ImageView) layout.findViewById(R.id.iv_jz_full);
        ImageView iv_3 = (ImageView) layout.findViewById(R.id.iv_sz_full);

//        ViewGroup.LayoutParams params = iv_1.getLayoutParams();
//        params.width = getIntFromDimens(350/10);
//        iv_1.setLayoutParams(params);

        TextView tv_1 = (TextView) layout.findViewById(R.id.tv_yg_score);
        TextView tv_2 = (TextView) layout.findViewById(R.id.tv_jz_score);
        TextView tv_3 = (TextView) layout.findViewById(R.id.tv_sz_score);

        if (starNum == 3) {
            ivBG.setImageResource(R.drawable.score_three);
        } else if (starNum == 2) {
            ivBG.setImageResource(R.drawable.score_two);
        } else if (starNum == 1) {
            ivBG.setImageResource(R.drawable.score_one);
        }

//        LayoutInflater inflater = getLayoutInflater();
//        View layout = inflater.inflate(R.layout.dialog_score, null);
//        new AlertDialog.Builder(this).setView(layout).show();

    }

    public int getIntFromDimens(float index) {
        int result = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, index, getResources().getDisplayMetrics());
        return result;
    }

    public void OpenVolume() {
        AudioManager audioManager = (AudioManager) getSystemService(Service.AUDIO_SERVICE);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_SYSTEM);
        mediaPlayer.setVolume(audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM), audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM));
        mediaPlayer.start();

    }

    /**
     * 设置乐谱视图样式
     * 设置界面样式只需调用此方法即可
     *
     * @param viewId        第几张谱子
     * @param yinfuPosition 音符位置，从1开始，不用区分几行谱子
     * @param yinfuBgColor  音符的覆盖颜色:Color.RED,Color.BLUE
     * @param keyPosition   按键位置(1-15)
     * @param keyBgColor    按键颜色:Color.RED,Color.BLUE
     */
    private void setViewStyle(int viewId, int yinfuPosition, int yinfuBgColor, int keyPosition, int keyBgColor) {
        int childCount = yuepuGroupLl.getChildCount();
        //设置显示哪个布局
        for (int i = 1; i < childCount + 1; i++) {
            yuepuGroupLl.getChildAt(i - 1).setVisibility(View.GONE);
            if (i == viewId) {
                yuepuGroupLl.getChildAt(i - 1).setVisibility(View.VISIBLE);
            }
        }
        //设置布局中显示样式
        switch (viewId) {
            case 1:
                setView1Style(yinfuPosition, yinfuBgColor, keyPosition, keyBgColor);
                break;
            case 2:
                setView2Style(yinfuPosition, yinfuBgColor, keyPosition, keyBgColor);
                break;
            case 3:
                setView3Style(yinfuPosition, yinfuBgColor, keyPosition, keyBgColor);
                break;
            case 4:
                setView4Style(yinfuPosition, yinfuBgColor, keyPosition, keyBgColor);
                break;
        }
    }

    //设置乐谱布局1样式
    private void setView1Style(int yinfuPosition, int yinfuBgColor, int keyPosition, int keyBgColor) {
        //音符背景颜色
        int childCount1 = ivYinfuBgLl1.getChildCount();
        if (1 <= yinfuPosition && yinfuPosition < childCount1 + 1) {
            for (int i = 1; i < childCount1 + 1; i++) {
                if (i == yinfuPosition) {
                    if (yinfuBgColor == Color.RED) {
                        ivYinfuBgLl1.getChildAt(i - 1).setBackgroundResource(R.mipmap.kc_red_puzi_bg);//红色背景
                    } else if (yinfuBgColor == Color.BLUE) {
                        ivYinfuBgLl1.getChildAt(i - 1).setBackgroundResource(R.mipmap.kc_blue_puzi_bg);//蓝色色背景
                    }
                } else {
                    ivYinfuBgLl1.getChildAt(i - 1).setBackgroundResource(0);//无背景
                }
            }
        } else {
            Toast.makeText(this, "音符位置错误", Toast.LENGTH_SHORT).show();
        }
        //按键的颜色
        int childCount2 = whiteKeyLl1.getChildCount();
        if (1 <= keyPosition && keyPosition < childCount2 + 1) {
            for (int i = 1; i < childCount2 + 1; i++) {
                if (i == keyPosition) {
                    if (keyBgColor == Color.RED) {
                        ((ImageView) whiteKeyLl1.getChildAt(i - 1)).setImageDrawable(getTintPic(this, R.mipmap.kc_white_key, 0xFFFB5555));
                    } else if (keyBgColor == Color.BLUE) {
                        ((ImageView) whiteKeyLl1.getChildAt(i - 1)).setImageDrawable(getTintPic(this, R.mipmap.kc_white_key, 0xFF34B4FF));
                    }
                } else {
                    ((ImageView) whiteKeyLl1.getChildAt(i - 1)).setImageDrawable(getTintPic(this, R.mipmap.kc_white_key, Color.WHITE));
                }
            }
        } else {
            Toast.makeText(this, "按键位置错误", Toast.LENGTH_SHORT).show();
        }
    }

    //设置乐谱布局2样式
    private void setView2Style(int yinfuPosition, int yinfuBgColor, int keyPosition, int keyBgColor) {
        //音符背景颜色
        if (1 <= yinfuPosition && yinfuPosition <= 3) {
            for (int i = 0; i < 3; i++) {
                if (i == yinfuPosition - 1) {
                    if (yinfuBgColor == Color.RED) {
                        ivYinfuBgLlDown2.getChildAt(i).setBackgroundResource(R.mipmap.kc_red_puzi_bg);//红色背景
                    } else if (yinfuBgColor == Color.BLUE) {
                        ivYinfuBgLlDown2.getChildAt(i).setBackgroundResource(R.mipmap.kc_blue_puzi_bg);//蓝色色背景
                    }
                } else {
                    ivYinfuBgLlDown2.getChildAt(i).setBackgroundResource(0);//无背景
                }
            }
        } else if (4 <= yinfuPosition && yinfuPosition <= 6) {
            for (int i = 0; i < 3; i++) {
                if (i == yinfuPosition - 4) {
                    if (yinfuBgColor == Color.RED) {
                        ivYinfuBgLlUp2.getChildAt(i).setBackgroundResource(R.mipmap.kc_red_puzi_bg);//红色背景
                    } else if (yinfuBgColor == Color.BLUE) {
                        ivYinfuBgLlUp2.getChildAt(i).setBackgroundResource(R.mipmap.kc_blue_puzi_bg);//蓝色色背景
                    }
                } else {
                    ivYinfuBgLlUp2.getChildAt(i).setBackgroundResource(0);//无背景
                }
            }
        } else if (7 <= yinfuPosition && yinfuPosition <= 9) {
            for (int i = 3; i < 6; i++) {
                if (i == yinfuPosition - 4) {
                    if (yinfuBgColor == Color.RED) {
                        ivYinfuBgLlDown2.getChildAt(i).setBackgroundResource(R.mipmap.kc_red_puzi_bg);//红色背景
                    } else if (yinfuBgColor == Color.BLUE) {
                        ivYinfuBgLlDown2.getChildAt(i).setBackgroundResource(R.mipmap.kc_blue_puzi_bg);//蓝色色背景
                    }
                } else {
                    ivYinfuBgLlDown2.getChildAt(i).setBackgroundResource(0);//无背景
                }
            }
        } else if (10 <= yinfuPosition && yinfuPosition <= 12) {
            for (int i = 3; i < 6; i++) {
                if (i == yinfuPosition - 7) {
                    if (yinfuBgColor == Color.RED) {
                        ivYinfuBgLlUp2.getChildAt(i).setBackgroundResource(R.mipmap.kc_red_puzi_bg);//红色背景
                    } else if (yinfuBgColor == Color.BLUE) {
                        ivYinfuBgLlUp2.getChildAt(i).setBackgroundResource(R.mipmap.kc_blue_puzi_bg);//蓝色色背景
                    }
                } else {
                    ivYinfuBgLlUp2.getChildAt(i).setBackgroundResource(0);//无背景
                }
            }
        } else {
            Toast.makeText(this, "音符位置错误", Toast.LENGTH_SHORT).show();
        }
        //按键的颜色
        int childCount2 = whiteKeyLl2.getChildCount();
        if (1 <= keyPosition && keyPosition < childCount2 + 1) {
            for (int i = 1; i < childCount2 + 1; i++) {
                if (i == keyPosition) {
                    if (keyBgColor == Color.RED) {
                        ((ImageView) whiteKeyLl2.getChildAt(i - 1)).setImageDrawable(getTintPic(this, R.mipmap.kc_white_key, 0xFFFB5555));
                    } else if (keyBgColor == Color.BLUE) {
                        ((ImageView) whiteKeyLl2.getChildAt(i - 1)).setImageDrawable(getTintPic(this, R.mipmap.kc_white_key, 0xFF34B4FF));
                    }
                } else {
                    ((ImageView) whiteKeyLl2.getChildAt(i - 1)).setImageDrawable(getTintPic(this, R.mipmap.kc_white_key, Color.WHITE));
                }
            }
        } else {
            Toast.makeText(this, "按键位置错误", Toast.LENGTH_SHORT).show();
        }
    }

    //设置乐谱布局3样式
    private void setView3Style(int yinfuPosition, int yinfuBgColor, int keyPosition, int keyBgColor) {
        //音符背景颜色
        if (1 <= yinfuPosition && yinfuPosition <= 3) {
            for (int i = 0; i < 3; i++) {
                if (i == yinfuPosition - 1) {
                    if (yinfuBgColor == Color.RED) {
                        ivYinfuBgLlDown3.getChildAt(i).setBackgroundResource(R.mipmap.kc_red_puzi_bg);//红色背景
                    } else if (yinfuBgColor == Color.BLUE) {
                        ivYinfuBgLlDown3.getChildAt(i).setBackgroundResource(R.mipmap.kc_blue_puzi_bg);//蓝色色背景
                    }
                } else {
                    ivYinfuBgLlDown3.getChildAt(i).setBackgroundResource(0);//无背景
                }
            }
        } else if (4 <= yinfuPosition && yinfuPosition <= 6) {
            for (int i = 0; i < 3; i++) {
                if (i == yinfuPosition - 4) {
                    if (yinfuBgColor == Color.RED) {
                        ivYinfuBgLlUp3.getChildAt(i).setBackgroundResource(R.mipmap.kc_red_puzi_bg);//红色背景
                    } else if (yinfuBgColor == Color.BLUE) {
                        ivYinfuBgLlUp3.getChildAt(i).setBackgroundResource(R.mipmap.kc_blue_puzi_bg);//蓝色色背景
                    }
                } else {
                    ivYinfuBgLlUp3.getChildAt(i).setBackgroundResource(0);//无背景
                }
            }
        } else if (7 <= yinfuPosition && yinfuPosition <= 9) {
            for (int i = 3; i < 6; i++) {
                if (i == yinfuPosition - 4) {
                    if (yinfuBgColor == Color.RED) {
                        ivYinfuBgLlDown3.getChildAt(i).setBackgroundResource(R.mipmap.kc_red_puzi_bg);//红色背景
                    } else if (yinfuBgColor == Color.BLUE) {
                        ivYinfuBgLlDown3.getChildAt(i).setBackgroundResource(R.mipmap.kc_blue_puzi_bg);//蓝色色背景
                    }
                } else {
                    ivYinfuBgLlDown3.getChildAt(i).setBackgroundResource(0);//无背景
                }
            }
        } else if (10 <= yinfuPosition && yinfuPosition <= 12) {
            for (int i = 3; i < 6; i++) {
                if (i == yinfuPosition - 7) {
                    if (yinfuBgColor == Color.RED) {
                        ivYinfuBgLlUp3.getChildAt(i).setBackgroundResource(R.mipmap.kc_red_puzi_bg);//红色背景
                    } else if (yinfuBgColor == Color.BLUE) {
                        ivYinfuBgLlUp3.getChildAt(i).setBackgroundResource(R.mipmap.kc_blue_puzi_bg);//蓝色色背景
                    }
                } else {
                    ivYinfuBgLlUp3.getChildAt(i).setBackgroundResource(0);//无背景
                }
            }
        } else {
            Toast.makeText(this, "音符位置错误", Toast.LENGTH_SHORT).show();
        }
        //按键的颜色
        int childCount2 = whiteKeyLl3.getChildCount();
        if (1 <= keyPosition && keyPosition < childCount2 + 1) {
            for (int i = 1; i < childCount2 + 1; i++) {
                if (i == keyPosition) {
                    if (keyBgColor == Color.RED) {
                        ((ImageView) whiteKeyLl3.getChildAt(i - 1)).setImageDrawable(getTintPic(this, R.mipmap.kc_white_key, 0xFFFB5555));
                    } else if (keyBgColor == Color.BLUE) {
                        ((ImageView) whiteKeyLl3.getChildAt(i - 1)).setImageDrawable(getTintPic(this, R.mipmap.kc_white_key, 0xFF34B4FF));
                    }
                } else {
                    ((ImageView) whiteKeyLl3.getChildAt(i - 1)).setImageDrawable(getTintPic(this, R.mipmap.kc_white_key, Color.WHITE));
                }
            }
        } else {
            Toast.makeText(this, "按键位置错误", Toast.LENGTH_SHORT).show();
        }
    }

    //设置乐谱布局4样式
    private void setView4Style(int yinfuPosition, int yinfuBgColor, int keyPosition, int keyBgColor) {
        //音符背景颜色
        int childCount1 = ivYinfuBgLl4.getChildCount();
        if (1 <= yinfuPosition && yinfuPosition < childCount1 + 1) {
            for (int i = 1; i < childCount1 + 1; i++) {
                if (i == yinfuPosition) {
                    if (yinfuBgColor == Color.RED) {
                        ivYinfuBgLl4.getChildAt(i - 1).setBackgroundResource(R.mipmap.kc_red_puzi_bg);//红色背景
                    } else if (yinfuBgColor == Color.BLUE) {
                        ivYinfuBgLl4.getChildAt(i - 1).setBackgroundResource(R.mipmap.kc_blue_puzi_bg);//蓝色色背景
                    }
                } else {
                    ivYinfuBgLl4.getChildAt(i - 1).setBackgroundResource(0);//无背景
                }
            }
        } else {
            Toast.makeText(this, "音符位置错误", Toast.LENGTH_SHORT).show();
        }
        //按键的颜色
        int childCount2 = whiteKeyLl4.getChildCount();
        if (1 <= keyPosition && keyPosition < childCount2 + 1) {
            for (int i = 1; i < childCount2 + 1; i++) {
                if (i == keyPosition) {
                    if (keyBgColor == Color.RED) {
                        ((ImageView) whiteKeyLl4.getChildAt(i - 1)).setImageDrawable(getTintPic(this, R.mipmap.kc_white_key, 0xFFFB5555));
                    } else if (keyBgColor == Color.BLUE) {
                        ((ImageView) whiteKeyLl4.getChildAt(i - 1)).setImageDrawable(getTintPic(this, R.mipmap.kc_white_key, 0xFF34B4FF));
                    }
                } else {
                    ((ImageView) whiteKeyLl4.getChildAt(i - 1)).setImageDrawable(getTintPic(this, R.mipmap.kc_white_key, Color.WHITE));
                }
            }
        } else {
            Toast.makeText(this, "按键位置错误", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 改变图片颜色
     */
    private Drawable getTintPic(Context context, int image, int color) {
        Drawable drawable = ContextCompat.getDrawable(context, image);
        Drawable.ConstantState constantState = drawable.getConstantState();
        Drawable newDrawable = DrawableCompat.wrap(constantState == null ? drawable : constantState.newDrawable()).mutate();
        DrawableCompat.setTint(newDrawable, color);
        return newDrawable;
    }
}
