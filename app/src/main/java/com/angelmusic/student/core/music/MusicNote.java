package com.angelmusic.student.core.music;

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


}





