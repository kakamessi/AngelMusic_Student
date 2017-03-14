package com.angelmusic.student.core.music;

import android.content.Context;

import com.angelmusic.stu.usb.UsbDeviceInfo;

import java.util.ArrayList;

import static android.R.attr.id;

/**
 * Created by DELL on 2017/2/1
 */

public class MusicNote {


    /*亮灯*/
    public static byte ON_COLOR = 0x01;
    public static int ON_INDEX = 39;
    public static byte ON_RED = 0x01;
    public static byte ON_BLUE = 0x11;

    /*灭灯*/
    public static byte OFF_COLOR = 0x00;
    public static int OFF_INDEX = 39;
    public static byte OFF_RED = 0x00;
    public static byte OFF_BLUE = 0x10;

    public static final ArrayList<NoteInfo> note_1 = new ArrayList<>();
    public static final ArrayList<NoteInfo> note_2 = new ArrayList<>();
    public static final ArrayList<NoteInfo> note_3 = new ArrayList<>();

    /*--------------------------------------------------------------------------------------------------------乐谱合集*/
    public static final ArrayList[] note_1ist = {note_1,note_2,note_3};

    static{

        /*----第一张谱子---- 培训 第一个*/
        for(int i=0; i<8;i++){
            NoteInfo ni1 = new NoteInfo(39,i+1,8,true);
            note_1.add(ni1);
        }

        /*----第二张谱子--上面 低 蓝色-- 小学  第二个*/
        int two_i = 1;
        for(int n = 0; n<4; n++) {
            for (int i = 0; i < 6; i++) {
                if (i < 3) {
                    NoteInfo ni1 = new NoteInfo(39, two_i, 8, false);
                    note_2.add(ni1);
                }
                if (i < 6 && i > 2) {
                    NoteInfo ni2 = new NoteInfo(39, two_i, 8, true);
                    note_2.add(ni2);

                }
                two_i++;
            }
        }

        /*----第三张谱子---幼儿园-   第三个*/
        NoteInfo ni1 = new NoteInfo(39,0,8,true);
        NoteInfo ni2 = new NoteInfo(41,1,9,true);
        NoteInfo ni3 = new NoteInfo(43,2,10,true);
        NoteInfo ni4 = new NoteInfo(39,3,8,true);

        NoteInfo ni5 = new NoteInfo(41,4,9,true);
        NoteInfo ni6 = new NoteInfo(43,5,10,true);
        NoteInfo ni7 = new NoteInfo(43,6,10,true);
        NoteInfo ni8 = new NoteInfo(41,7,9,true);

        NoteInfo ni9 = new NoteInfo(39,8,8,true);
        NoteInfo ni10 = new NoteInfo(43,9,10,true);
        NoteInfo ni11= new NoteInfo(41,10,9,true);
        NoteInfo ni12 = new NoteInfo(39,11,8,true);

        note_3.add(ni1);note_3.add(ni2);note_3.add(ni3);note_3.add(ni4);
        note_3.add(ni5);note_3.add(ni6);note_3.add(ni7);note_3.add(ni8);
        note_3.add(ni9);note_3.add(ni10);note_3.add(ni11);note_3.add(ni12);


    }

    /**
     * 根据课程id 获取对应的 乐谱合集
     * //确认选谱  1培训 2小学 3幼儿园
     * @return
     */
    public static ArrayList<NoteInfo> getNoteList(int courseId){

        if(courseId-1 > note_1ist.length -1){
            return null;
        }

        ArrayList<NoteInfo> result = note_1ist[courseId-1];

        return result;

    }

    //--------------------------------------------------------------钢琴指令---------------------------------------------------------------

    /*开启打击乐*/
    public static byte[] open_djy = { 0x04, (byte)0xf0, 0x4d, 0x4c, 0x04, 0x4c, 0x53, 0x01, 0x06, 0x00, 0x00, (byte)0xf7 };

    /*关闭打击乐*/
    public static byte[] close_djy = { 0x04, (byte)0xf0, 0x4d, 0x4c, 0x04, 0x4c, 0x53, 0x00, 0x06, 0x00, 0x00, (byte)0xf7 };

    /*亮灯指令*/
    public static byte[] ON_DATA ={0x04, (byte) 0xf0, 0x4d, 0x4c, 0x04,
            0x4c, 0x45, (byte) (ON_INDEX+21), 0x06, ON_COLOR, 0x0,
            (byte) 0xf7 };
    /* 熄灯指令 */
    public static byte[] OFF_DATA = {0x04, (byte) 0xf0, 0x4d, 0x4c, 0x04, 0x4c, 0x45,
            (byte)(OFF_INDEX+21), 0x06, OFF_COLOR, 0x0, (byte) 0xf7 };

    /* 设置钢琴动作指令 */
    public static void setPianoAction(Context context,byte[] data){

        UsbDeviceInfo.getUsbDeviceInfo(context).setData(data);

    }

    //获取开灯指令集
    public static byte[] getLightbytes(int index,boolean isRed){
        byte on_color = 0x01;
        if(isRed){
            on_color = ON_RED;
        }else{
            on_color = ON_BLUE;
        }
        byte[] ON_DATA ={0x04, (byte) 0xf0, 0x4d, 0x4c, 0x04,
                0x4c, 0x45, (byte) (index+21), 0x06, on_color, 0x0,
                (byte) 0xf7 };

        return ON_DATA;
    }

    //获取关灯指令集
    public static byte[] getCloseBytes(int index,boolean isRed){
        byte off_color = 0x01;

        if(isRed){
            off_color = OFF_RED;
        }else{
            off_color = OFF_BLUE;
        }

        byte[] OFF_DATA = {0x04, (byte) 0xf0, 0x4d, 0x4c, 0x04, 0x4c, 0x45,
                (byte)(index+21), 0x06, off_color, 0x0, (byte) 0xf7 };

        return OFF_DATA;
    }

    //闪烁一次灯
    public static void beat(final Context context, final int index, final boolean isRed){

        new Thread(new Runnable() {
            @Override
            public void run() {

                UsbDeviceInfo.getUsbDeviceInfo(context).setData(getLightbytes(index,isRed));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                UsbDeviceInfo.getUsbDeviceInfo(context).setData(getCloseBytes(index,isRed));

            }
        }).start();

    }

    //闪烁一次灯
    public static void beat(final Context context,final int index,final boolean isRed,final long time){

        new Thread(new Runnable() {
            @Override
            public void run() {

                UsbDeviceInfo.getUsbDeviceInfo(context).setData(getLightbytes(index,isRed));
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                UsbDeviceInfo.getUsbDeviceInfo(context).setData(getCloseBytes(index,isRed));

            }
        }).start();

    }

    //闪烁一次灯
    public static void beat2(final Context context,final int index,final boolean isRed,final long time) throws InterruptedException {

        UsbDeviceInfo.getUsbDeviceInfo(context).setData(getLightbytes(index,isRed));
        Thread.sleep(time);
        UsbDeviceInfo.getUsbDeviceInfo(context).setData(getCloseBytes(index,isRed));

    }

    public static void openLight(final Context context, final int index, final boolean isRed){

        UsbDeviceInfo.getUsbDeviceInfo(context).setData(getLightbytes(index,isRed));

    }

    public static void closeAllLight(Context context){

        UsbDeviceInfo.getUsbDeviceInfo(context).setData(getCloseBytes(39, true));
        UsbDeviceInfo.getUsbDeviceInfo(context).setData(getCloseBytes(41, true));
        UsbDeviceInfo.getUsbDeviceInfo(context).setData(getCloseBytes(43, true));

        UsbDeviceInfo.getUsbDeviceInfo(context).setData(getCloseBytes(39, false));
        UsbDeviceInfo.getUsbDeviceInfo(context).setData(getCloseBytes(41, false));
        UsbDeviceInfo.getUsbDeviceInfo(context).setData(getCloseBytes(43, false));

    }

    public static void closeAndOpenNext(final Context context,final int closeIndex, final boolean closeRed, final int openIndex, final boolean openRed){

        new Thread(new Runnable() {
            @Override
            public void run() {

                UsbDeviceInfo.getUsbDeviceInfo(context).setData(getCloseBytes(closeIndex,closeRed));

                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                UsbDeviceInfo.getUsbDeviceInfo(context).setData(getLightbytes(openIndex,openRed));

            }
        }).start();

    }




    /* ----------------------------自动跟灯逻辑 ----------------------------------------------   */
    //1
    public static float[] delay1 = { 17.11f, 30.85f, 58.22f,72.02f };
    public static float[] dur1 = { 0.816f, 0.816f, 0.816f, 0.816f, 0.816f, 0.816f, 0.816f, 0.816f };
    public static int[] color1 = { 1, 1, 1, 1, 1, 1, 1, 1};
    public static int[] index1 = { 39, 39, 39, 39, 39, 39, 39, 39 };

    public static  float[] delay2= { 6.54f, 15.09f, 23.59f, 32.16f };
    public static float[] dur2 = { 0.556f, 0.556f, 0.556f, 0.556f, 0.556f, 0.556f, 0.556f, 0.556f };
    public static int[] color2 = {1, 1, 1, 1, 1, 1, 1, 1};
    public static int[] index2 = { 21, 21, 21, 21, 21, 21, 21, 21 };
    //2
    public static float[] delay3 = { 7.18f, 19.1f, 31.13f, 43.1f };
    public static float[] dur3 = { 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, };
    public static int[] color3 = { 0,0,0,-1,1,1,1,-1,0,0,0,-1,1,1,1,-1 };
    public static int[] index3 = { 39, 39, 39, -1, 39, 39, 39, -1, 39, 39, 39, -1, 39, 39, 39, -1 };

    public static float[] delay4 = { 11.2f, 21.2f, 31.33f, 41.31f };
    //public static float[] dur4 = { 1.764f, 0.588f, 0.588f, 0.588f, 0.588f, 0.588f, 0.588f };
    public static float[] dur4 = { 0.588f, 0.588f, 0.588f, 0.588f, 0.588f, 0.588f, 0.588f };
    public static int[] color4 = { 1,1,1,1,-1,1,-1};
    public static int[] index4 = { 3, 3, 3, 3, -1, 3, -1};

    //3
    public static float[] delay5 = { 11.01f, 21.01f, 30.76f, 40.46f };
    //public static float[] dur5 = { 1.836f, 0.612f, 0.612f, 0.612f, 0.612f, 0.612f, 0.612f };
    public static float[] dur5 = { 0.612f, 0.612f, 0.612f, 0.612f, 0.612f, 0.612f, 0.612f };
    public static int[] color5 = { 1,1,-1,1,-1,1,-1};
    public static int[] index5 = { 3, 3, -1, 3, -1, 3, -1 };

    public static void followTempo(Context context,float[] delay,float[] dur,int[] color,int[] index) throws InterruptedException {

        final Context icontext = context;
        final float[] idelay = delay;  //每次延迟时间， 循环次数依据
        final float[] idur = dur;      //音符间隔       音符个数
        final int[] icolor = color;   //色值判断
        final int[] iindex = index;  //亮灯位置

        for(int n = 0; n<idur.length;n++){

            /* 立即打断循环 */
            Thread.sleep(2);

            if(iindex[n]!=-1){
                if(icolor[n]==1) {
                    beat(icontext, iindex[n], true,(long) (idur[0]*1000)/2);
                }else if(icolor[n]==0){
                    beat(icontext, iindex[n], false,(long) (idur[0]*1000)/2);
                }

                Thread.sleep((long)(idur[4]*1000));

            }else{

                Thread.sleep((long)(idur[4]*1000));

            }
        }
    }

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for(int n = 0; n<idur.length;n++){
//
//                    if(iindex[n]!=-1){
//                        if(icolor[n]==1) {
//                            beat(icontext, iindex[n], true,(long) (idur[0]*1000)/2);
//                        }else if(icolor[n]==0){
//                            beat(icontext, iindex[n], false,(long) (idur[0]*1000)/2);
//                        }
//                        try {
//                            Thread.sleep((long)(idur[4]*1000));
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                    }else{
//
//                        try {
//                            Thread.sleep((long)(idur[4]*1000));
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }
//            }
//        }).start();

    /* ----------------------------自动跟灯逻辑 ----------------------------------------------   */





}





