package com.angelmusic.student.core.music;

import android.content.Context;

import com.angelmusic.stu.usb.UsbDeviceInfo;

import java.util.ArrayList;

import static android.os.Build.VERSION_CODES.N;

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
    /*乐谱合集*/
    public static final ArrayList[] note_1ist = {note_1,note_2,note_3};

    static{

        /*----第一张谱子----*/
        for(int i=0; i<8;i++){
            NoteInfo ni1 = new NoteInfo(39,i+1,8,true);
            note_1.add(ni1);
        }

        /*----第二张谱子--低 蓝色--*/
        int two_i = 1;
        for(int n = 0; n<4; n++) {
            for (int i = 0; i < 6; i++) {
                if (i < 3) {
                    NoteInfo ni1 = new NoteInfo(39, two_i, 8, true);
                    note_2.add(ni1);
                }
                if (i < 6 && i > 2) {
                    NoteInfo ni2 = new NoteInfo(39, two_i, 8, false);
                    note_2.add(ni2);

                }
                two_i++;
            }
        }

        /*----第三张谱子----*/
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


    /*乐谱信息   1  */
    public static final int[] music_1 = {39,39,39,39,  39,39,39,39};

    /*乐谱信息   2  */
    public static final int[] music_2 = {39,39,39,39,  39,39,39,39};

    /*乐谱合集*/
    public static final int[][] music_g = {music_1,music_2};


    /*亮灯指令*/
    public static final byte[] ON_DATA ={0x04, (byte) 0xf0, 0x4d, 0x4c, 0x04,
            0x4c, 0x45, (byte) (ON_INDEX+21), 0x06, ON_COLOR, 0x0,
            (byte) 0xf7 };

    /* 熄灯指令 */
    public static final byte[] OFF_DATA = {0x04, (byte) 0xf0, 0x4d, 0x4c, 0x04, 0x4c, 0x45,
            (byte)(OFF_INDEX+21), 0x06, OFF_COLOR, 0x0, (byte) 0xf7 };


    //获取开灯指令集
    public static byte[] getLightbytes(int index,boolean isRed){

        ON_INDEX = index;
        if(isRed){
            ON_COLOR = ON_RED;
        }else{
            ON_COLOR = ON_BLUE;
        }

        return ON_DATA;
    }

    //获取关灯指令集
    public static byte[] getCloseBytes(int index,boolean isRed){

        OFF_INDEX = index;
        if(isRed){
            OFF_COLOR = OFF_RED;
        }else{
            OFF_COLOR = OFF_BLUE;
        }

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


}





