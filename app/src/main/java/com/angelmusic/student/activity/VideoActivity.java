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
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
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

import com.angelmusic.stu.u3ddownload.okhttp.HttpInfo;
import com.angelmusic.stu.u3ddownload.okhttp.OkHttpUtil;
import com.angelmusic.stu.u3ddownload.okhttp.OkHttpUtilInterface;
import com.angelmusic.stu.u3ddownload.okhttp.callback.CallbackOk;
import com.angelmusic.stu.usb.UsbDeviceConnect;
import com.angelmusic.stu.usb.UsbDeviceInfo;
import com.angelmusic.stu.usb.callback.CallbackInterface;
import com.angelmusic.stu.utils.Log;
import com.angelmusic.student.R;
import com.angelmusic.student.base.App;
import com.angelmusic.student.base.BaseActivity;
import com.angelmusic.student.constant.Constant;
import com.angelmusic.student.core.ActionType;
import com.angelmusic.student.core.music.MusicNote;
import com.angelmusic.student.core.music.NoteInfo;
import com.angelmusic.student.infobean.CourseData;
import com.angelmusic.student.infobean.ScoreData;
import com.angelmusic.student.utils.LogUtil;
import com.angelmusic.student.utils.MusicUtils;
import com.angelmusic.student.utils.SharedPreferencesUtil;
import com.angelmusic.student.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import butterknife.BindView;
import butterknife.OnClick;

import static com.angelmusic.stu.u3ddownload.okhttp.annotation.CacheLevel.FIRST_LEVEL;

/**
 *
 *  本界面初始化只是一个单纯的准备中界面，其界面样式完全由教师端发消息来控制
 *
 *  界面样式由setLayoutStyle来控制，
 *
 *  主要有钢琴通讯处理，和教师端通信处理
 *
 *
 *
 */
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
    @BindView(R.id.ll_yuepu)
    LinearLayout llYuepu;

    //定时任务
    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    //课程信息
    private CourseData cd = null;
    //当前播放视频资源路径
    private String currentPath = "";
    private MediaPlayer mediaPlayer;
    private int currentPosition = 0;
    private boolean isMediaPlaying = false;

    /* 弹奏评分 */
    private ScoreData sd = null;
    /* 弹出成绩弹窗 */
    private PopupWindow scoreWindow = null;
    /*记录钢琴弹奏输出*/
    private ArrayList<String> notes = new ArrayList<String>();
    /*是否进行钢琴检测,  只有在真正弹奏环节，才启动钢琴处理逻辑*/
    private boolean isPianoActive = false;
    /*是否开启成绩计算*/
    private boolean isScore = false;
    /* 当前课程id */
    private int course_id = -1;
    /* 上传成绩参数 */
    private String lesson_id = "-1";
    /* 选择跟灯资源 */
    private String gendeng_id = "-1";
    /* 选谱资源索引 */
    private String yuepu_tag = "-1";

    /*------------------------------------------------------------------------------收到钢琴消息handler*/
    private Handler pianoHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            String str = (String) msg.obj;
            switch (msg.what) {
                case 1:

                    if(isPianoActive) {
                        //根据钢琴输出是否正确，来显示界面音符变化，亮灯操作
                        handlerNote1(str);
                    }

                    if(isScore) {
                        if (str.endsWith("0 ")) {
                            //获取钢琴弹奏音符
                            String[] myDatas = str.substring(str.indexOf("=") + 1).split(" ");
                            int key = Integer.parseInt(myDatas[2], 16) - 21;
                            notes.add(key+"");
                        }

                    }

                    break;

                case 2:
                    //提交成绩，弹出评分界面
                    break;
            }
        }
    };

    /**
     *
     *  夏津课程
     *
     *  确认选谱
     *  获取钢琴返回音符index
     *  比对是否正确
     *  UI显示下一个音符
     */
    private int index_new = 0;
    private void handlerNote(String str){

        //课程选谱
        ArrayList<NoteInfo> al = MusicNote.getNoteList(course_id,yuepu_tag);
        if(al==null){
            return;
        }

        //获取钢琴弹奏音符
        String[] myDatas = str.substring(str.indexOf("=") + 1).split(" ");
        int key = Integer.parseInt(myDatas[2], 16) - 21;

        //处理输出
        NoteInfo ni = al.get(index_new);
        NoteInfo nextInfo = index_new + 1 > al.size() - 1 ? al.get(0) : al.get(index_new + 1);
        if (key == ni.getNoteNum()) {

            //处理显示正确音符和钢琴键逻辑
            if (course_id == Constant.COURSE_1) {
                setViewStyle(1, nextInfo.getNoteIndex(), nextInfo.isRed() == true ? Color.RED : Color.BLUE, nextInfo.getKeyIndex(), nextInfo.isRed() == true ? Color.RED : Color.BLUE);
            } else if (course_id == Constant.COURSE_2) {
                if (nextInfo.getNoteIndex() < 13) {
                    setViewStyle(2, nextInfo.getNoteIndex(), nextInfo.isRed() == true ? Color.RED : Color.BLUE, nextInfo.getKeyIndex(), nextInfo.isRed() == true ? Color.RED : Color.BLUE);
                } else {
                    setViewStyle(3, nextInfo.getNoteIndex(), nextInfo.isRed() == true ? Color.RED : Color.BLUE, nextInfo.getKeyIndex(), nextInfo.isRed() == true ? Color.RED : Color.BLUE);
                }
            } else if (course_id == Constant.COURSE_3) {
                setViewStyle(4, nextInfo.getNoteIndex() + 1, nextInfo.isRed() == true ? Color.RED : Color.BLUE, nextInfo.getKeyIndex(), nextInfo.isRed() == true ? Color.RED : Color.BLUE);
            }else{

            }

            if (str.endsWith("0 ")) {
                //处理亮灯逻辑
                MusicNote.closeAndOpenNext(VideoActivity.this,ni.getNoteNum(),ni.isRed(),nextInfo.getNoteNum(),nextInfo.isRed());

                //处理循环
                if (index_new == al.size() - 1) {
                    index_new = -1;
                }
                index_new++;
            }
        }

    }

    /**
     *
     * 丰台一小课程
     * @param str
     */
    private void handlerNote1(String str){

        //课程选谱
        ArrayList<NoteInfo> al = MusicNote.getNoteList(course_id,yuepu_tag);
        if(al==null){
            return;
        }
        //获取钢琴弹奏音符
        String[] myDatas = str.substring(str.indexOf("=") + 1).split(" ");
        int key = Integer.parseInt(myDatas[2], 16) - 21;
        //处理输出
        NoteInfo ni = al.get(index_new);
        NoteInfo nextInfo = index_new + 1 > al.size() - 1 ? al.get(0) : al.get(index_new + 1);
        if (key == ni.getNoteNum()) {

            //由于UI实现逻辑变更，需要重新处理新课程曲谱
            if(course_id == Constant.COURSE_2_ft && yuepu_tag.equals(Constant.PLAY_TOGHTER_FOLLOW_ONE)) {
                if(index_new<11) {
                    replaceLayout(llYuepu,R.layout.layout_yuepu_1_1);
                }else{
                    replaceLayout(llYuepu,R.layout.layout_yuepu_1_2);
                }
            }else if(course_id == Constant.COURSE_2_ft && yuepu_tag.equals(Constant.PLAY_TOGHTER_FOLLOW_ONE_TWO)){
                if(index_new<11) {
                    replaceLayout(llYuepu,R.layout.layout_yuepu_1);
                }else{
                    replaceLayout(llYuepu,R.layout.layout_yuepu_2);
                }
            }else if(course_id == Constant.COURSE_3_ft){
                if(index_new<9) {
                    replaceLayout(llYuepu,R.layout.layout_yuepu_sqtwt1);
                }else{
                    replaceLayout(llYuepu,R.layout.layout_yuepu_sqtwt2);
                }
            }else if(course_id == Constant.COURSE_5){
                if(index_new<10) {
                    replaceLayout(llYuepu,R.layout.layout_yuepu_slzzg1);
                }else{
                    replaceLayout(llYuepu,R.layout.layout_yuepu_slzzg2);
                }
            }else if(course_id == Constant.COURSE_6){
                if(index_new<12) {
                    replaceLayout(llYuepu,R.layout.layout_yuepu_yycb1);
                }else{
                    replaceLayout(llYuepu,R.layout.layout_yuepu_yycb2);
                }
            }else if(course_id == Constant.COURSE_8){
                if(index_new<12) {
                    replaceLayout(llYuepu,R.layout.layout_yuepu_sddjr1);
                }else{
                    replaceLayout(llYuepu,R.layout.layout_yuepu_sddjr2);
                }
            }else if(course_id == Constant.COURSE_9){
                if(index_new<12) {
                    replaceLayout(llYuepu,R.layout.layout_yuepu_kadj1);
                }else{
                    replaceLayout(llYuepu,R.layout.layout_yuepu_kadj2);
                }
            }else if(course_id == Constant.COURSE_11){
                if(index_new<13) {
                    replaceLayout(llYuepu,R.layout.layout_yuepu_jnxz1);
                }else{
                    replaceLayout(llYuepu,R.layout.layout_yuepu_jnxz2);
                }
            }else if(course_id == Constant.COURSE_12){
                if(index_new<9) {
                    replaceLayout(llYuepu,R.layout.layout_yuepu_ydjjx1);
                }else{
                    replaceLayout(llYuepu,R.layout.layout_yuepu_ydjjx2);
                }
            }
            setNoteAndKey(llYuepu, nextInfo.getNoteIndex()+1, nextInfo.isRed(), nextInfo.getKeyIndex(), nextInfo.isRed());

            if (str.endsWith("0 ")) {
                //处理亮灯逻辑
                MusicNote.closeAndOpenNext(VideoActivity.this,ni.getNoteNum(),ni.isRed(),nextInfo.getNoteNum(),nextInfo.isRed());
                //处理循环
                if (index_new == al.size() - 1) {
                    index_new = -1;
                }
                index_new++;
            }
        }

    }

    /*------------------------------------------------------------------------------收到钢琴消息handler*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();

//        测试画谱
//        setLayoutStyle(2);
//        isPianoActive = true;
//        setNoteAndKey(llYuepu, 12, true, 1, true);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    MusicNote.followTempo(VideoActivity.this,MusicNote.delay_ft_11,MusicNote.dur1_ft_11,MusicNote.color1_ft_11,MusicNote.index1_ft_11);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //结束上课 必须重置或释放一些资源 重置钢琴音色
        resetStatus();
        // 关闭视频播放
        stop();
        //重置handler
        mBaseApp.setVideoHandler(null);

    }

    private void initData() {

        cd = App.getApplication().getCd();
        course_id = Integer.parseInt(cd.getCourse_Id());

        mBaseApp.setVideoHandler(pianoHandler);
        // 为SurfaceHolder添加回调
        surfaceView.getHolder().addCallback(callback);

    }

    private void initView() {

        blackTv.setText("准备中");
        blackTv.setVisibility(View.VISIBLE);

    }

    @Override
    protected int setContentViewId() {
        return R.layout.activity_video;
    }

    @Override
    protected void setTAG() {
        TAG = "VideoActivity";
    }

    /**
     * //============================================================================通讯逻辑
     *
     *  1，播放视频
     *
     *  2，画谱界面
     *
     *  3，暂停or播放
     *
     * @param msg
     */
    @Override
    protected void handleMsg(Message msg) {

        String str = msg.obj.toString();
        String[] ac = str.split("\\|");

        if (ActionType.ACTION_PLAY.equals(ac[0])) {
            //-----------------------------------------------------------------------视频状态
            resetStatus();

            //判断是否投大屏，以及是否计算成绩
            boolean isDaPing = false;
            boolean isDajiYue = false;
            boolean isGenDeng = false;

            String[] playParams = ac[1].split("&");
            String[] scoreStr = playParams[1].split("-");
            String[] gendengStr = playParams[3].split("-");

            lesson_id = scoreStr[1];
            gendeng_id = gendengStr[1];

            if("0".equals(playParams[0])){
                isDaPing = true;
            }
            if("1".equals(scoreStr[0])){
                isScore = true;
            }
            if("1".equals(playParams[2])){
                isDajiYue = true;
            }
            if("1".equals(gendengStr[0])){
                isGenDeng = true;
            }

            if (isDaPing) {
                Log.e("kaka","--AV handleMsg--"+  "qing kan da ping mu");
                //请看大屏幕
                stop();
                setLayoutStyle(1);

            } else {

                Log.e("kaka","--AV handleMsg--"+  "bo fang shi ping");
                //学生端播放视频，会带有附加逻辑~~~~~~

                setLayoutStyle(3);

                String path = cd.getFiles().get(ac[2]);
                switchVedio(path);

                //打击乐
                if(isDajiYue){
                    MusicNote.setPianoAction(this,MusicNote.open_djy);
                }else{
                    MusicNote.setPianoAction(this,MusicNote.close_djy);
                }

                //跟灯
                checkGZ2(isGenDeng);

            }

        } else if (ActionType.ACTION_GZ_ONE.equals(ac[0])) {
            //-----------------------------------------------------------------------画谱状态
            resetStatus();
            Log.e("kaka","--AV handleMsg--"+  " geng deng hua pu ");

            yuepu_tag = ac[1];
            isPianoActive = true;
            stop();
            //小学2 培训 幼儿园

            setLayoutStyle(2);

        }else if (ActionType.ACTION_PAUSE_RESUME.equals(ac[0])) {
            //暂停or继续

            if (ac[1].equals("2")) {
                Log.e("kaka","--AV handleMsg--"+  "xia ke ");
                this.finish();

            } else {

                Log.e("kaka","--AV handleMsg--"+  "bo fang or zan ting" + isMediaPlaying);
                pause();
            }


        }else if(ActionType.ACTION_POST_SCORE.equals(ac[0])){
            //重新上传成绩
            postAccount(this);

        }

    }


    //-----------------------------------------------------------------初始化哪个模式界面
    private void setLayoutStyle(int type) {

        if (type == 1) {
            //请看大屏幕
            blackTv.setVisibility(View.VISIBLE);
            blackTv.setText("请看大屏幕");
            //yuepuGroupLl.setVisibility(View.INVISIBLE);
            setPuzi(0);

        } else if (type == 2) {
            //乐谱跟奏

            blackTv.setVisibility(View.INVISIBLE);
            //yuepuGroupLl.setVisibility(View.VISIBLE);
            setPuzi(1);

            /* 初始化界面显示的时候 默认高亮音符信息 */
            if (course_id == Constant.COURSE_1) {
                //培训
                setViewStyle(1, 1, Color.RED, 8, Color.RED);
                MusicNote.openLight(VideoActivity.this,39,true);

            } else if (course_id == Constant.COURSE_2) {
                //小学
                setViewStyle(2, 1, Color.BLUE, 8, Color.BLUE);
                MusicNote.openLight(VideoActivity.this,39,false);

            } else if (course_id == Constant.COURSE_3) {
                //幼儿园
                setViewStyle(4, 1, Color.RED, 8, Color.RED);
                MusicNote.openLight(VideoActivity.this,39,true);

            } else{
                //处理画谱新逻辑
                setPuzi(2);
                //设置乐谱的第一个颜色
                if(course_id == Constant.COURSE_2_ft && yuepu_tag.equals(Constant.PLAY_TOGHTER_FOLLOW_ONE)) {
                        replaceLayout(llYuepu,R.layout.layout_yuepu_1_1);
                        setNoteAndKey(llYuepu,1,true,0,true);
                }else if(course_id == Constant.COURSE_2_ft && yuepu_tag.equals(Constant.PLAY_TOGHTER_FOLLOW_ONE_TWO)){
                        replaceLayout(llYuepu,R.layout.layout_yuepu_1);
                    setNoteAndKey(llYuepu,1,false,0,true);
                }else if(course_id == Constant.COURSE_3_ft){
                        replaceLayout(llYuepu,R.layout.layout_yuepu_sqtwt1);
                    setNoteAndKey(llYuepu,1,true,0,true);
                }else if(course_id == Constant.COURSE_5){
                        replaceLayout(llYuepu,R.layout.layout_yuepu_slzzg1);
                    setNoteAndKey(llYuepu,1,true,0,true);
                }else if(course_id == Constant.COURSE_6){
                        replaceLayout(llYuepu,R.layout.layout_yuepu_yycb1);
                    setNoteAndKey(llYuepu,1,true,0,true);
                }else if(course_id == Constant.COURSE_8){
                        replaceLayout(llYuepu,R.layout.layout_yuepu_sddjr1);
                    setNoteAndKey(llYuepu,1,true,0,true);
                }else if(course_id == Constant.COURSE_9){
                        replaceLayout(llYuepu,R.layout.layout_yuepu_kadj1);
                    setNoteAndKey(llYuepu,1,false,0,true);
                }else if(course_id == Constant.COURSE_11){
                        replaceLayout(llYuepu,R.layout.layout_yuepu_jnxz1);
                    setNoteAndKey(llYuepu,1,true,0,true);
                }else if(course_id == Constant.COURSE_12){
                        replaceLayout(llYuepu,R.layout.layout_yuepu_ydjjx1);
                    setNoteAndKey(llYuepu,1,false,0,true);
                }
            }

        } else if (type == 3) {
            //播放视频
            blackTv.setVisibility(View.INVISIBLE);
            //yuepuGroupLl.setVisibility(View.INVISIBLE);
            setPuzi(0);

        }

    }

    /* 各种状态重置 */
    private void resetStatus() {

        //熄灭所有亮灯
        MusicNote.setPianoAction(this,MusicNote.close_djy);
        MusicNote.closeAllLight(this);

        //关闭之前跟灯
        if(gzThread!=null){
            gzThread.interrupt();
            gzThread = null;
        }

        //默认不统计成绩
        isScore = false;
        isPianoActive = false;
        notes.clear();

        //弹奏音符index重置
        index_new = 0;
        //选谱参数重置
        yuepu_tag = "-1";

        if(scoreWindow!=null){
            scoreWindow.dismiss();
        }
    }
    //============================================================================通讯逻辑

    //-----------------------------------------------------------------判断是否启动跟奏亮灯
    private Thread gzThread = null;
    private void checkGZ2(boolean isGd) {
        if(isGd){
            isPianoActive = true;
            if(course_id==Constant.COURSE_1 && Constant.PLAY_TOGHTER_COMPLETE_ONE.equals(gendeng_id)){
                gzThread = new Thread(new VideoRun(-1,MusicNote.delay1,MusicNote.dur1,MusicNote.color1,MusicNote.index1));
            }else if(course_id==Constant.COURSE_1 && Constant.RHYTHM_COMPLETE.equals(gendeng_id)){
                gzThread = new Thread(new VideoRun(-1,MusicNote.delay2,MusicNote.dur2,MusicNote.color2,MusicNote.index2));
            }else if(course_id==Constant.COURSE_2 && Constant.PLAY_TOGHTER_COMPLETE_ONE.equals(gendeng_id)){
                gzThread =  new Thread(new VideoRun(-1,MusicNote.delay3,MusicNote.dur3,MusicNote.color3,MusicNote.index3));
            }else if(course_id==Constant.COURSE_2 && Constant.RHYTHM_COMPLETE.equals(gendeng_id)){
                gzThread = new Thread(new VideoRun(-1,MusicNote.delay4,MusicNote.dur4,MusicNote.color4,MusicNote.index4));
            }else if(course_id==Constant.COURSE_3 && Constant.RHYTHM_COMPLETE.equals(gendeng_id)){
                gzThread = new Thread(new VideoRun(-1,MusicNote.delay5,MusicNote.dur5,MusicNote.color5,MusicNote.index5));
            }

            if(gzThread!=null){
                gzThread.start();
            }
        }
    }
//
//    private int checkGZ2(String[] strs) {
//
//        int result = -1;
//        if(course_id.equals("1") && strs[0].equals("一起弹奏吧1") && strs[1].equals("完整奏1")){
//            isPianoActive = true;
//            gzThread = new Thread(new VideoRun(-1,MusicNote.delay1,MusicNote.dur1,MusicNote.color1,MusicNote.index1));
//            result = 1;
//        }
//
//        if(course_id.equals("1") && strs[0].equals("节奏连连看") && strs[1].equals("完整奏")){
//            isPianoActive = true;
//            gzThread = new Thread(new VideoRun(-1,MusicNote.delay2,MusicNote.dur2,MusicNote.color2,MusicNote.index2));
//            result = 2;
//
//        }
//
//        if(course_id.equals("2") && strs[0].equals("一起弹奏吧1") && strs[1].equals("完整奏1")){
//            isPianoActive = true;
//            gzThread =  new Thread(new VideoRun(-1,MusicNote.delay3,MusicNote.dur3,MusicNote.color3,MusicNote.index3));
//            result = 3;
//        }
//
//        if(course_id.equals("2") && strs[0].equals("节奏连连看") && strs[1].equals("完整奏")){
//            isPianoActive = true;
//            gzThread = new Thread(new VideoRun(-1,MusicNote.delay4,MusicNote.dur4,MusicNote.color4,MusicNote.index4));
//
//            result = 4;
//        }
//
//        if(course_id.equals("3") && strs[0].equals("节奏连连看") && strs[1].equals("完整奏")){
//            isPianoActive = true;
//            gzThread = new Thread(new VideoRun(-1,MusicNote.delay5,MusicNote.dur5,MusicNote.color5,MusicNote.index5));
//
//            result = 5;
//        }
//
//        if(gzThread!=null){
//            gzThread.start();
//        }
//
//        return result;
//    }

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

        isMediaPlaying = false;
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
            //Toast.makeText(this, "视频文件路径错误", Toast.LENGTH_LONG).show();
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

                    isMediaPlaying = true;

                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    // 在播放完毕被回调
                    isMediaPlaying = false;

                    //视频播放完毕，弹出成绩界面，并上传成绩
                    if(isScore) {

                        sd = MusicUtils.getScore(notes,course_id,(Integer.parseInt(gendeng_id)-1)+"");
                        if(sd!=null) {
                            showScore();
                            postAccount(VideoActivity.this);
                        }

                    }

                }
            });

            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {

                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    // 发生错误重新播放
                    play(0);
                    isMediaPlaying = false;
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

        if(!isMediaPlaying){
            mediaPlayer.start();
            isMediaPlaying = true;
            return;
        }
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isMediaPlaying =false;
        }

    }

    /*
    * 停止播放
    */
    protected void stop() {
        if (mediaPlayer != null) {
            // && mediaPlayer.isMediaPlaying()  某些异常情况下可以加上判断

            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;

            isMediaPlaying = false;
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
        isMediaPlaying = false;
        play(0);


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
            for (int i = 0; i < 6; i++) {
                ivYinfuBgLlUp2.getChildAt(i).setBackgroundResource(0);//无背景
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
            for (int i = 0; i < 6; i++) {
                ivYinfuBgLlDown2.getChildAt(i).setBackgroundResource(0);//无背景
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
            for (int i = 0; i < 6; i++) {
                ivYinfuBgLlUp2.getChildAt(i).setBackgroundResource(0);//无背景
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
            for (int i = 0; i < 6; i++) {
                ivYinfuBgLlDown2.getChildAt(i).setBackgroundResource(0);//无背景
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
        if (13 <= yinfuPosition && yinfuPosition <= 15) {
            for (int i = 0; i < 3; i++) {
                if (i == yinfuPosition - 13) {
                    if (yinfuBgColor == Color.RED) {
                        ivYinfuBgLlDown3.getChildAt(i).setBackgroundResource(R.mipmap.kc_red_puzi_bg);//红色背景
                    } else if (yinfuBgColor == Color.BLUE) {
                        ivYinfuBgLlDown3.getChildAt(i).setBackgroundResource(R.mipmap.kc_blue_puzi_bg);//蓝色色背景
                    }
                } else {
                    ivYinfuBgLlDown3.getChildAt(i).setBackgroundResource(0);//无背景
                }
            }
            for (int i = 0; i < 6; i++) {
                ivYinfuBgLlUp3.getChildAt(i).setBackgroundResource(0);//无背景
            }
        } else if (16 <= yinfuPosition && yinfuPosition <= 18) {
            for (int i = 0; i < 3; i++) {
                if (i == yinfuPosition - 16) {
                    if (yinfuBgColor == Color.RED) {
                        ivYinfuBgLlUp3.getChildAt(i).setBackgroundResource(R.mipmap.kc_red_puzi_bg);//红色背景
                    } else if (yinfuBgColor == Color.BLUE) {
                        ivYinfuBgLlUp3.getChildAt(i).setBackgroundResource(R.mipmap.kc_blue_puzi_bg);//蓝色色背景
                    }
                } else {
                    ivYinfuBgLlUp3.getChildAt(i).setBackgroundResource(0);//无背景
                }
            }
            for (int i = 0; i < 6; i++) {
                ivYinfuBgLlDown3.getChildAt(i).setBackgroundResource(0);//无背景
            }
        } else if (19 <= yinfuPosition && yinfuPosition <= 21) {
            for (int i = 3; i < 6; i++) {
                if (i == yinfuPosition - 16) {
                    if (yinfuBgColor == Color.RED) {
                        ivYinfuBgLlDown3.getChildAt(i).setBackgroundResource(R.mipmap.kc_red_puzi_bg);//红色背景
                    } else if (yinfuBgColor == Color.BLUE) {
                        ivYinfuBgLlDown3.getChildAt(i).setBackgroundResource(R.mipmap.kc_blue_puzi_bg);//蓝色色背景
                    }
                } else {
                    ivYinfuBgLlDown3.getChildAt(i).setBackgroundResource(0);//无背景
                }
            }
            for (int i = 0; i < 6; i++) {
                ivYinfuBgLlUp3.getChildAt(i).setBackgroundResource(0);//无背景
            }
        } else if (22 <= yinfuPosition && yinfuPosition <= 24) {
            for (int i = 3; i < 6; i++) {
                if (i == yinfuPosition - 19) {
                    if (yinfuBgColor == Color.RED) {
                        ivYinfuBgLlUp3.getChildAt(i).setBackgroundResource(R.mipmap.kc_red_puzi_bg);//红色背景
                    } else if (yinfuBgColor == Color.BLUE) {
                        ivYinfuBgLlUp3.getChildAt(i).setBackgroundResource(R.mipmap.kc_blue_puzi_bg);//蓝色色背景
                    }
                } else {
                    ivYinfuBgLlUp3.getChildAt(i).setBackgroundResource(0);//无背景
                }
            }
            for (int i = 0; i < 6; i++) {
                ivYinfuBgLlDown3.getChildAt(i).setBackgroundResource(0);//无背景
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

    protected void showScore() {

        int starNum = sd.getScore();
        float ratio_yg = sd.getYingaoScore();
        float ratio_jz = sd.getJiezouScore();
        float ratio_sz = sd.getShizhiScore();

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog_score, null);

        //星星
        ImageView ivBG = (ImageView) layout.findViewById(R.id.imageView);
        TextView tv_num = (TextView) layout.findViewById(R.id.textView1);
        if (starNum == 3) {
            ivBG.setImageResource(R.drawable.score_three);
            tv_num.setText("哇！获得了三颗星");
        } else if (starNum == 2) {
            ivBG.setImageResource(R.drawable.score_two);
            tv_num.setText("哇！获得了二颗星");
        } else if (starNum == 1) {
            ivBG.setImageResource(R.drawable.score_one);
            tv_num.setText("哇！获得了一颗星");
        }

        //分数比例条， 数字
        ImageView iv_1 = (ImageView) layout.findViewById(R.id.iv_yingao_full);
        ImageView iv_2 = (ImageView) layout.findViewById(R.id.iv_jz_full);
        ImageView iv_3 = (ImageView) layout.findViewById(R.id.iv_sz_full);

        //音高
        TextView tv_1 = (TextView) layout.findViewById(R.id.tv_yg_score);
        tv_1.setText((int)(100*ratio_yg) + "%");
        ViewGroup.LayoutParams params = iv_1.getLayoutParams();
        params.width = getIntFromDimens(350*ratio_yg);
        iv_1.setLayoutParams(params);

        //节奏
        TextView tv_2 = (TextView) layout.findViewById(R.id.tv_jz_score);
        tv_2.setText((int)(100*ratio_jz) + "%");
        ViewGroup.LayoutParams params1 = iv_2.getLayoutParams();
        params1.width = getIntFromDimens(350*ratio_jz);
        iv_2.setLayoutParams(params1);

        //时值
        TextView tv_3 = (TextView) layout.findViewById(R.id.tv_sz_score);
        tv_3.setText((int)(100*ratio_sz) + "%");
        ViewGroup.LayoutParams params2 = iv_3.getLayoutParams();
        params2.width = getIntFromDimens(350*ratio_sz);
        iv_3.setLayoutParams(params2);

        // TODO: 2016/5/17 创建PopupWindow对象，指定宽度和高度 1221, 1134  733  680
        scoreWindow = new PopupWindow(layout, 1221, 1134);
        // TODO: 2016/5/17 设置背景颜色
        scoreWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        // TODO: 2016/5/17 设置可以获取焦点
        scoreWindow.setFocusable(true);
        // TODO: 2016/5/17 设置可以触摸弹出框以外的区域
        scoreWindow.setOutsideTouchable(true);
        // TODO：更新popupwindow的状态
        scoreWindow.update();
        // TODO: 2016/5/17 以下拉的方式显示，并且可以设置显示的位置
        scoreWindow.showAtLocation(findViewById(R.id.activity_video), Gravity.CENTER, 0, 0);

    }

    /**
     * 上传分数
     * @param mContext
     */
    public void postAccount(final Context mContext) {

        if(sd==null){
            return;
        }

        String stuId = SharedPreferencesUtil.getString(Constant.CACHE_STUDENT_ID,"-1");
        String cid = SharedPreferencesUtil.getString(Constant.CACHE_CLASS_ID,"-1");

        String machineCode = Utils.getDeviceId(mContext);
        OkHttpUtilInterface okHttpUtil = OkHttpUtil.Builder()
                .setCacheLevel(FIRST_LEVEL)
                .setConnectTimeout(25).build(mContext);
        okHttpUtil.doPostAsync(
                HttpInfo.Builder().setUrl(mContext.getResources().getString(R.string.domain_name_request) + mContext.getResources().getString(R
                        .string.account_submit))
                        .addParam("score", sd.getScore()+"")
                        .addParam("yingaoScore", sd.getYingaoScore()+"")
                        .addParam("jiezouScore", sd.getJiezouScore()+"")
                        .addParam("shizhiScore", sd.getShizhiScore()+"")
                        .addParam("lessonId", lesson_id)
                        .addParam("jiekeId", course_id+"")
                        .addParam("stuId", stuId)
                        .addParam("cid", cid)
                        .build(),
                new CallbackOk() {
                    @Override
                    public void onResponse(HttpInfo info) throws IOException {
                        String jsonResult = info.getRetDetail();
                        Log.e("kaka","--上传分数--"+jsonResult);

                    }
                });

    }

    //--------------------------------------------设置音符，琴键----------------------------------------------------------

//    setLayoutStyle(2);
//    replaceLayout(yuepuGroupLl,R.layout.layout_yuepu_1);
//    setNoteAndKey(yuepuGroupLl,0,0);

    /**
     * UI切换
     * @param type
     */
    private void setPuzi(int type){
        if(type==0) {
            //都隐藏
            yuepuGroupLl.setVisibility(View.GONE);
            llYuepu.setVisibility(View.GONE);
        }else if(type==1){
            //显示旧的
            yuepuGroupLl.setVisibility(View.VISIBLE);
            llYuepu.setVisibility(View.GONE);
        }else if(type==2){
            //显示新的
            yuepuGroupLl.setVisibility(View.GONE);
            llYuepu.setVisibility(View.VISIBLE);
        }
    }

    /**
     *
     * 根据课程动态选择 布局文件
     * @param fu
     * @param zi
     */
    private void replaceLayout(ViewGroup fu,int resId){

        fu.removeAllViews();
        ViewGroup vg = (ViewGroup)LayoutInflater.from(this).inflate(resId,fu);

    }

    /**
     *      动态操作UI元素
     *
     *      根据跟奏[数据] 设置对应的音符，琴键界面变化
     *      音符位置，音符颜色
     *
     *      音符Tag从左至右升序排列 0 - 10  上下都有的序号相同
     *
     *
     *      音符蓝色背景 R.mipmap.kc_blue_puzi_bg 无背景 0
     *
     *
     */
    private ArrayList<ImageView> noteList = null;
    private void setNoteAndKey(ViewGroup vg,int noteIndex, boolean isNoteRed, int keyIndex, boolean isKeyRed){

        getNotes(vg);

        //设置对应位置的颜色
        for(ImageView iv : noteList){
            String index = (String) iv.getTag();
            if(index.equals(noteIndex + "")) {
                if(isNoteRed){
                    iv.setBackgroundResource(R.mipmap.kc_red_puzi_bg);
                }else{
                    iv.setBackgroundResource(R.mipmap.kc_blue_puzi_bg);
                }
            }else{
                iv.setBackgroundResource(0);
            }

        }

        noteList = null;
    }

    private void getNotes(ViewGroup vg){
        if(noteList==null){
            noteList = new ArrayList<>();
        }
        for(int i=0;i<vg.getChildCount();i++){
            if(vg.getChildAt(i) instanceof ViewGroup){
                getNotes((ViewGroup) vg.getChildAt(i));
            }else{
                if(vg.getChildAt(i).getTag()!=null && !TextUtils.isEmpty(vg.getChildAt(i).getTag().toString())){
                    noteList.add((ImageView) vg.getChildAt(i));
                }
            }
        }
    }

    //--------------------------------------------设置音符，琴键----------------------------------------------------------


    //--------------------------------------------跟灯----------------------------------------------------------
    /* 退出视频时间检测循环 */
    class VideoRun implements Runnable{
        float[] delay = null; //时间延迟执行
        float[] dur = null;   //亮灯时间
        int[] color = null;
        int[] index = null;   //亮灯位置
        public VideoRun(int types,float[] mDelays,float[] mdur,int[] mcolor,int[] mindex) {
            delay = mDelays;
            dur = mdur;
            color = mcolor;
            index = mindex;
        }
        @Override
        public void run() {
            int xunhuan = 0;
            try {
                while(true){
                    if(xunhuan>delay.length-1){
                        return;
                    }
                    if(mediaPlayer!=null){
                            int curTime =  mediaPlayer.getCurrentPosition();
                            if(curTime>(delay[xunhuan]*1000)){
                                MusicNote.followTempo(VideoActivity.this,delay,dur,color,index);
                                xunhuan++;
                            }
                    }
                }
            }catch (Exception e){

            }

        }
    }

    //--------------------------------------------跟灯----------------------------------------------------------


//    private void initPiano() {
//
//        UsbDeviceConnect.setCallbackInterface(new CallbackInterface() {
//            @Override
//            public void onReadCallback(String str) {
//                Message msg = Message.obtain();
//                msg.what = 1;
//                msg.obj = str;
//                pianoHandler.sendMessage(msg);
//            }
//            @Override
//            public void onSendCallback(boolean isSend) {
//            }
//            @Override
//            public void onConnect(boolean isConnected) {
//                if(isConnected){
//
//                }
//            }
//
//        });
//        UsbDeviceInfo.getUsbDeviceInfo(this).update();
//        UsbDeviceInfo.getUsbDeviceInfo(this).connect();
//
//    }
//
//    private void closePiano() {
//        UsbDeviceInfo.getUsbDeviceInfo(this).stopConnect();
//    }


}
