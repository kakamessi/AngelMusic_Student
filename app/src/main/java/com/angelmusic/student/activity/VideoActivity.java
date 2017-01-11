package com.angelmusic.student.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

import com.angelmusic.student.R;
import com.angelmusic.student.base.BaseActivity;
import com.angelmusic.student.utils.LogUtil;

import java.io.File;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

public class VideoActivity extends BaseActivity {

    @BindView(R.id.btn_play)
    Button btnPlay;
    @BindView(R.id.btn_pause)
    Button btnPause;
    @BindView(R.id.btn_replay)
    Button btnReplay;
    @BindView(R.id.btn_stop)
    Button btnStop;
    @BindView(R.id.surface_view)
    SurfaceView surfaceView;

    private MediaPlayer mediaPlayer;
    private int currentPosition = 0;
    private boolean isPlaying;
    private int swich = 0;
    private String currentPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {

        // 为SurfaceHolder添加回调
        surfaceView.getHolder().addCallback(callback);

//        // 为进度条添加进度更改事件
//        seekBar.setOnSeekBarChangeListener(change);

        setPlayPath();
    }

    @Override
    protected void handleMsg(Message msg) {
        super.handleMsg(msg);

       // LogUtil.i(msg.obj.toString());
        Toast.makeText(VideoActivity.this, msg.obj.toString() ,0).show();

        if((msg.obj.toString()).equals("1")){
            play(0);
        }else {
            pause();
        }

    }


    protected void dialog7() {

        LayoutInflater inflater = getLayoutInflater();

        View layout = inflater.inflate(R.layout.dialog_score, null);

        // TODO: 2016/5/17 创建PopupWindow对象，指定宽度和高度
        PopupWindow window = new PopupWindow(layout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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


//        LayoutInflater inflater = getLayoutInflater();
//        View layout = inflater.inflate(R.layout.dialog_score, null);
//        new AlertDialog.Builder(this).setView(layout).show();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_video;
    }

    @Override
    protected void setTAG() {
        TAG = "==VideoActivity==";
    }

    @OnClick({R.id.btn_play, R.id.btn_pause, R.id.btn_replay, R.id.btn_stop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_play:

                play(0);
                break;
            case R.id.btn_pause:

                pause();

                break;
            case R.id.btn_replay:

                //replay();
                switchVedio();

                break;
            case R.id.btn_stop:

                dialog7();

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
     * 开始播放
     *
     * @param msec 播放初始位置
     */
    protected void  play(final int msec) {
        // 获取视频文件地址
        String path = currentPath;

        File file = new File(path);
        if (!file.exists()) {
            Toast.makeText(this, "视频文件路径错误", Toast.LENGTH_LONG).show();
            return;
        }
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

                    // 设置进度条的最大进度为视频流的最大播放时长
//                    seekBar.setMax(mediaPlayer.getDuration());
//                    // 开始线程，更新进度条的刻度
//                    new Thread() {
//
//                        @Override
//                        public void run() {
//                            try {
//                                isPlaying = true;
//                                while (isPlaying) {
//                                    int current = mediaPlayer
//                                            .getCurrentPosition();
//                                    seekBar.setProgress(current);
//
//                                    sleep(500);
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }.start();

                    btnPlay.setEnabled(false);

                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    // 在播放完毕被回调
                    btnPlay.setEnabled(true);
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
            LogUtil.e("--------------------------CAT  play(final int msec)   ");
        }
    }

    private String setPlayPath() {
        String result = "";

        int rr = new Random().nextInt(3);

        if (rr == 0) {
            result = "/sdcard/ykzzldx.mp4";
        } else if(rr ==1){
            result = "/sdcard/hehe.mp4";
        }else {
            result = "/sdcard/ffff.mp4";
        }
        swich++;

        currentPath = result;
        return result;
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
            btnPlay.setEnabled(true);
            isPlaying = false;
        }
    }

    /**
     * 重新开始播放
     */
    protected void replay() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(0);

            btnPause.setText("暂停");
            return;
        }
        isPlaying = false;
        play(0);


    }

    /**
     * 切换视频
     */
    protected void switchVedio() {
        setPlayPath();
        stop();

        isPlaying = false;
        play(0);

    }

    /**
     * 暂停或继续
     */
    protected void pause() {

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            btnPause.setText("继续");

            return;
        }

        if (btnPause.getText().toString().trim().equals("继续")) {
            btnPause.setText("暂停");
            mediaPlayer.start();

            return;
        }

    }
}
