package com.angelmusic.student.activity;

import android.app.Service;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Message;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.angelmusic.stu.utils.Log;
import com.angelmusic.student.R;
import com.angelmusic.student.base.App;
import com.angelmusic.student.base.BaseActivity;
import com.angelmusic.student.core.ActionType;
import com.angelmusic.student.infobean.CourseData;
import com.angelmusic.student.utils.LogUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class VideoActivity extends BaseActivity {


    @BindView(R.id.black_tv)
    TextView blackTv;
    @BindView(R.id.surface_view)
    SurfaceView surfaceView;


    //课程信息
    private CourseData cd = App.getApplication().getCd();
    private String currentPath = "";
    private File currentfile = null;


    private MediaPlayer mediaPlayer;
    private int currentPosition = 0;
    private boolean isPlaying;
    private int swich = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stop();

    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_video;
    }

    @Override
    protected void setTAG() {
        TAG = "VideoActivity";
    }

    @OnClick({R.id.black_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.black_tv:
                //dialog7();
                break;
        }
    }

    private void initView() {
        // 为SurfaceHolder添加回调
        surfaceView.getHolder().addCallback(callback);
        blackTv.setText("准备中");
    }

    @Override
    protected void handleMsg(Message msg) {

        String str = msg.obj.toString();
        String[] ac = str.split("\\|");
        Log.e(TAG,"消息入口:  ---------- " + str);

        //播放，切换视频
        if (ActionType.ACTION_PLAY.equals(ac[0])) {

            if(ac[1].equals("0")){
                Log.e(TAG,"动作:  ---------- " + "投大屏");
                stop();
                blackTv.setVisibility(View.VISIBLE);
                blackTv.setText("请看大屏幕");
            }else{
                blackTv.setVisibility(View.INVISIBLE);
                String path = cd.getFiles().get(ac[2]);

                switchVedio(path);
//              currentPath = path;
//              play(0);

                Log.e(TAG,"动作:  ---------- " + "投学生屏开始播放视频：" + path);
            }

            //暂停继续播放视频
        } else if (ActionType.ACTION_PAUSE_RESUME.equals(ac[0])) {

            if(ac[1].equals("2")) {

                Log.e(TAG,"动作:  ---------- " + "下课：");
                this.finish();

            }else{

                pause();
                Log.e(TAG,"动作:  ---------- " + "暂停继续：");
            }

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

        if(mediaPlayer==null){
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

        LayoutInflater inflater = getLayoutInflater();

        View layout = inflater.inflate(R.layout.dialog_score, null);

        // TODO: 2016/5/17 创建PopupWindow对象，指定宽度和高度 1221, 1134
        PopupWindow window = new PopupWindow(layout, 733, 680);
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

        ImageView iv_1 = (ImageView) layout.findViewById(R.id.iv_yingao_full);
        ImageView iv_2 = (ImageView) layout.findViewById(R.id.iv_jz_full);
        ImageView iv_3 = (ImageView) layout.findViewById(R.id.iv_sz_full);

        ViewGroup.LayoutParams params = iv_1.getLayoutParams();
        params.height=ViewGroup.LayoutParams.WRAP_CONTENT;
        params.width = getIntFromDimens(350/2);
        iv_1.setLayoutParams(params);

        TextView tv_1 = (TextView) layout.findViewById(R.id.tv_yg_score);
        TextView tv_2 = (TextView) layout.findViewById(R.id.tv_jz_score);
        TextView tv_3 = (TextView) layout.findViewById(R.id.tv_sz_score);


//        LayoutInflater inflater = getLayoutInflater();
//        View layout = inflater.inflate(R.layout.dialog_score, null);
//        new AlertDialog.Builder(this).setView(layout).show();

    }

    public int getIntFromDimens(float index) {
        int result = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, index, getResources().getDisplayMetrics());
        return result;
    }

    public void OpenVolume() {

        AudioManager audioManager = (AudioManager) getSystemService(Service.AUDIO_SERVICE);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_SYSTEM);
        mediaPlayer.setVolume(audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM), audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM));
        mediaPlayer.start();

    }

}
