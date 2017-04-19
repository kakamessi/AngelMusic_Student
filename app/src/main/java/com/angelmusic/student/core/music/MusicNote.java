package com.angelmusic.student.core.music;

import android.content.Context;

import com.angelmusic.stu.usb.UsbDeviceInfo;
import com.angelmusic.student.constant.Constant;

import java.util.ArrayList;

/**
 * Created by DELL on 2017/2/1
 */

public class MusicNote {

    /*亮灯*/
    public static byte ON_RED = 0x01;
    public static byte ON_BLUE = 0x11;

    /*灭灯*/
    public static byte OFF_RED = 0x00;
    public static byte OFF_BLUE = 0x10;

    /*开启打击乐*/
    public static byte[] open_djy = { 0x04, (byte)0xf0, 0x4d, 0x4c, 0x04, 0x4c, 0x53, 0x01, 0x06, 0x00, 0x00, (byte)0xf7 };
    /*关闭打击乐*/
    public static byte[] close_djy = { 0x04, (byte)0xf0, 0x4d, 0x4c, 0x04, 0x4c, 0x53, 0x00, 0x06, 0x00, 0x00, (byte)0xf7 };

    //开启静音协议
    public static byte[] ACTION_MUTE ={ 0x1b, (byte)0xbF, 0x07, 0x00};
    //关闭静音
    public static byte[] ACTION_UNMUTE = { 0x1b, (byte)0xbF, 0x07, 0x7f };

    /*--------------------------------------------------------------------------------------------------------乐谱合集*/
    public static final ArrayList<NoteInfo> note_1 = new ArrayList<>();
    public static final ArrayList<NoteInfo> note_2 = new ArrayList<>();
    public static final ArrayList<NoteInfo> note_3 = new ArrayList<>();

    /*  丰台一小谱子  */
    public static final ArrayList<NoteInfo> fd_1 = new ArrayList<>();
    public static final ArrayList<NoteInfo> fd_2 = new ArrayList<>();
    public static final ArrayList<NoteInfo> fd_3 = new ArrayList<>();
    public static final ArrayList<NoteInfo> fd_5= new ArrayList<>();
    public static final ArrayList<NoteInfo> fd_6 = new ArrayList<>();
    public static final ArrayList<NoteInfo> fd_8 = new ArrayList<>();
    public static final ArrayList<NoteInfo> fd_9 = new ArrayList<>();
    public static final ArrayList<NoteInfo> fd_11 = new ArrayList<>();
    public static final ArrayList<NoteInfo> fd_12 = new ArrayList<>();

    /* C社培及幼儿园上学期乐谱 */
    public static final ArrayList<NoteInfo> c_1 = new ArrayList<>();
    public static final ArrayList<NoteInfo> c_2 = new ArrayList<>();
    public static final ArrayList<NoteInfo> c_3 = new ArrayList<>();
    public static final ArrayList<NoteInfo> c_4 = new ArrayList<>();
    public static final ArrayList<NoteInfo> c_5 = new ArrayList<>();
    public static final ArrayList<NoteInfo> c_6 = new ArrayList<>();
    public static final ArrayList<NoteInfo> c_7 = new ArrayList<>();
    public static final ArrayList<NoteInfo> c_8 = new ArrayList<>();

    public static final ArrayList[] note_1ist = {note_1,note_2,note_3};
    public static final ArrayList[] fengtai_1ist = {fd_1,fd_2,fd_3,fd_5,fd_6,fd_8,fd_9,fd_11,fd_12};
    public static final ArrayList[] c_1ist = {c_1,c_2,c_3,c_4,c_5,c_6,c_7,c_8};

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

        /*--丰台一小-----------------------------------*/
        /* 第二节课 第一 */
        for(int i=0; i<24;i++){
            NoteInfo fd_1n = new NoteInfo(39,i,8,true);
            fd_1.add(fd_1n);
        }

        /* 第二节课 第二 */
        NoteInfo fd_1_1n = new NoteInfo(39,0,8,false);
        NoteInfo fd_1_2n = new NoteInfo(39,1,8,false);
        NoteInfo fd_1_3n = new NoteInfo(39,2,8,false);
        NoteInfo fd_1_4n = new NoteInfo(39,3,8,true);
        NoteInfo fd_1_5n = new NoteInfo(39,4,8,true);
        NoteInfo fd_1_6n = new NoteInfo(39,5,8,true);
        NoteInfo fd_1_7n = new NoteInfo(39,6,8,false);
        NoteInfo fd_1_8n = new NoteInfo(39,7,8,false);
        NoteInfo fd_1_9n = new NoteInfo(39,8,8,false);
        NoteInfo fd_1_10n = new NoteInfo(39,9,8,true);
        NoteInfo fd_1_11n = new NoteInfo(39,10,8,true);
        NoteInfo fd_1_12n = new NoteInfo(39,11,8,true);

        NoteInfo fd_1_13n = new NoteInfo(39,12,8,false);
        NoteInfo fd_1_14n = new NoteInfo(39,13,8,false);
        NoteInfo fd_1_15n = new NoteInfo(39,14,8,false);
        NoteInfo fd_1_16n = new NoteInfo(39,15,8,true);
        NoteInfo fd_1_17n = new NoteInfo(39,16,8,true);
        NoteInfo fd_1_18n = new NoteInfo(39,17,8,true);
        NoteInfo fd_1_19n = new NoteInfo(39,18,8,false);
        NoteInfo fd_1_20n = new NoteInfo(39,19,8,false);
        NoteInfo fd_1_21n = new NoteInfo(39,20,8,false);
        NoteInfo fd_1_22n = new NoteInfo(39,21,8,true);
        NoteInfo fd_1_23n = new NoteInfo(39,22,8,true);
        NoteInfo fd_1_24n = new NoteInfo(39,23,8,true);

        fd_2.add(fd_1_1n);fd_2.add(fd_1_2n);fd_2.add(fd_1_3n);fd_2.add(fd_1_4n);
        fd_2.add(fd_1_5n);fd_2.add(fd_1_6n);fd_2.add(fd_1_7n);fd_2.add(fd_1_8n);
        fd_2.add(fd_1_9n);fd_2.add(fd_1_10n);fd_2.add(fd_1_11n);fd_2.add(fd_1_12n);
        fd_2.add(fd_1_13n);fd_2.add(fd_1_14n);fd_2.add(fd_1_15n);fd_2.add(fd_1_16n);
        fd_2.add(fd_1_17n);fd_2.add(fd_1_18n);fd_2.add(fd_1_19n);fd_2.add(fd_1_20n);
        fd_2.add(fd_1_21n);fd_2.add(fd_1_22n);fd_2.add(fd_1_23n);fd_2.add(fd_1_24n);

        /* 第三节课 */
        NoteInfo fd_3_1n = new NoteInfo(39,0,getKeyIndex(39),true);
        NoteInfo fd_3_2n = new NoteInfo(41,1,getKeyIndex(41),true);
        NoteInfo fd_3_3n = new NoteInfo(39,2,getKeyIndex(39),false);
        NoteInfo fd_3_4n = new NoteInfo(39,3,getKeyIndex(39),false);

        NoteInfo fd_3_5n = new NoteInfo(39,4,getKeyIndex(39),false);
        NoteInfo fd_3_6n = new NoteInfo(41,5,getKeyIndex(41),true);
        NoteInfo fd_3_7n = new NoteInfo(43,6,getKeyIndex(43),true);
        NoteInfo fd_3_8n = new NoteInfo(39,7,getKeyIndex(39),false);

        NoteInfo fd_3_9n = new NoteInfo(39,8,getKeyIndex(39),false);
        NoteInfo fd_3_10n = new NoteInfo(39,9,getKeyIndex(39),false);
        NoteInfo fd_3_11n = new NoteInfo(39,10,getKeyIndex(39),true);
        NoteInfo fd_3_12n = new NoteInfo(41,11,getKeyIndex(41),true);

        NoteInfo fd_3_13n = new NoteInfo(39,12,getKeyIndex(39),false);
        NoteInfo fd_3_14n = new NoteInfo(39,13,getKeyIndex(39),false);
        NoteInfo fd_3_15n = new NoteInfo(39,14,getKeyIndex(39),false);
        NoteInfo fd_3_16n = new NoteInfo(41,15,getKeyIndex(41),true);

        NoteInfo fd_3_17n = new NoteInfo(41,16,getKeyIndex(41),true);
        NoteInfo fd_3_18n = new NoteInfo(43,17,getKeyIndex(43),true);
        NoteInfo fd_3_19n = new NoteInfo(43,18,getKeyIndex(43),true);
        NoteInfo fd_3_20n = new NoteInfo(39,19,getKeyIndex(39),false);

        NoteInfo fd_3_21n = new NoteInfo(39,20,getKeyIndex(39),false);
        NoteInfo fd_3_22n = new NoteInfo(39,21,getKeyIndex(39),false);

        fd_3.add(fd_3_1n);fd_3.add(fd_3_2n);fd_3.add(fd_3_3n);fd_3.add(fd_3_4n);
        fd_3.add(fd_3_5n);fd_3.add(fd_3_6n);fd_3.add(fd_3_7n);fd_3.add(fd_3_8n);
        fd_3.add(fd_3_9n);fd_3.add(fd_3_10n);fd_3.add(fd_3_11n);fd_3.add(fd_3_12n);
        fd_3.add(fd_3_13n);fd_3.add(fd_3_14n);fd_3.add(fd_3_15n);fd_3.add(fd_3_16n);
        fd_3.add(fd_3_17n);fd_3.add(fd_3_18n);fd_3.add(fd_3_19n);fd_3.add(fd_3_20n);
        fd_3.add(fd_3_21n);fd_3.add(fd_3_22n);

   /* 第五节课 */
        NoteInfo fd_5_1n = new NoteInfo(39,0,getKeyIndex(39),true);
        NoteInfo fd_5_2n = new NoteInfo(41,1,getKeyIndex(41),true);
        NoteInfo fd_5_3n = new NoteInfo(43,2,getKeyIndex(43),true);
        NoteInfo fd_5_4n = new NoteInfo(43,3,getKeyIndex(43),true);

        NoteInfo fd_5_5n = new NoteInfo(39,4,getKeyIndex(39),true);
        NoteInfo fd_5_6n = new NoteInfo(39,5,getKeyIndex(39),true);
        NoteInfo fd_5_7n = new NoteInfo(41,6,getKeyIndex(41),true);
        NoteInfo fd_5_8n = new NoteInfo(41,7,getKeyIndex(41),true);

        NoteInfo fd_5_9n = new NoteInfo(43,8,getKeyIndex(43),true);
        NoteInfo fd_5_10n = new NoteInfo(43,9,getKeyIndex(43),true);
        NoteInfo fd_5_11n = new NoteInfo(43,10,getKeyIndex(43),true);
        NoteInfo fd_5_12n = new NoteInfo(39,11,getKeyIndex(39),false);

        NoteInfo fd_5_13n = new NoteInfo(38,12,getKeyIndex(38),false);
        NoteInfo fd_5_14n = new NoteInfo(36,13,getKeyIndex(36),false);
        NoteInfo fd_5_15n = new NoteInfo(36,14,getKeyIndex(36),false);
        NoteInfo fd_5_16n = new NoteInfo(39,15,getKeyIndex(39),false);

        NoteInfo fd_5_17n = new NoteInfo(39,16,getKeyIndex(39),false);
        NoteInfo fd_5_18n = new NoteInfo(38,17,getKeyIndex(38),false);
        NoteInfo fd_5_19n = new NoteInfo(38,18,getKeyIndex(38),false);
        NoteInfo fd_5_20n = new NoteInfo(36,19,getKeyIndex(36),false);

        NoteInfo fd_5_21n = new NoteInfo(36,20,getKeyIndex(36),false);
        NoteInfo fd_5_22n = new NoteInfo(36,21,getKeyIndex(36),false);

        fd_5.add(fd_5_1n);fd_5.add(fd_5_2n);fd_5.add(fd_5_3n);fd_5.add(fd_5_4n);
        fd_5.add(fd_5_5n);fd_5.add(fd_5_6n);fd_5.add(fd_5_7n);fd_5.add(fd_5_8n);
        fd_5.add(fd_5_9n);fd_5.add(fd_5_10n);fd_5.add(fd_5_11n);fd_5.add(fd_5_12n);
        fd_5.add(fd_5_13n);fd_5.add(fd_5_14n);fd_5.add(fd_5_15n);fd_5.add(fd_5_16n);
        fd_5.add(fd_5_17n);fd_5.add(fd_5_18n);fd_5.add(fd_5_19n);fd_5.add(fd_5_20n);
        fd_5.add(fd_5_21n);fd_5.add(fd_5_22n);


   /* 第六节课 */
        NoteInfo fd_6_1n = new NoteInfo(39,0,getKeyIndex(39),true);
        NoteInfo fd_6_2n = new NoteInfo(41,1,getKeyIndex(41),true);
        NoteInfo fd_6_3n = new NoteInfo(43,2,getKeyIndex(43),true);
        NoteInfo fd_6_4n = new NoteInfo(44,3,getKeyIndex(44),true);

        NoteInfo fd_6_5n = new NoteInfo(46,4,getKeyIndex(46),true);
        NoteInfo fd_6_6n = new NoteInfo(46,5,getKeyIndex(46),true);
        NoteInfo fd_6_7n = new NoteInfo(39,6,getKeyIndex(39),false);
        NoteInfo fd_6_8n = new NoteInfo(38,7,getKeyIndex(38),false);

        NoteInfo fd_6_9n = new NoteInfo(36,8,getKeyIndex(36),false);
        NoteInfo fd_6_10n = new NoteInfo(38,9,getKeyIndex(38),false);
        NoteInfo fd_6_11n = new NoteInfo(39,10,getKeyIndex(39),false);
        NoteInfo fd_6_12n = new NoteInfo(39,11,getKeyIndex(39),false);

        NoteInfo fd_6_13n = new NoteInfo(39,12,getKeyIndex(39),false);
        NoteInfo fd_6_14n = new NoteInfo(39,13,getKeyIndex(39),true);
        NoteInfo fd_6_15n = new NoteInfo(41,14,getKeyIndex(41),true);
        NoteInfo fd_6_16n = new NoteInfo(43,15,getKeyIndex(43),true);

        NoteInfo fd_6_17n = new NoteInfo(44,16,getKeyIndex(44),true);
        NoteInfo fd_6_18n = new NoteInfo(46,17,getKeyIndex(46),true);
        NoteInfo fd_6_19n = new NoteInfo(46,18,getKeyIndex(46),true);
        NoteInfo fd_6_20n = new NoteInfo(46,19,getKeyIndex(46),true);

        NoteInfo fd_6_21n = new NoteInfo(39,20,getKeyIndex(39),false);
        NoteInfo fd_6_22n = new NoteInfo(38,21,getKeyIndex(38),false);
        NoteInfo fd_6_23n = new NoteInfo(36,22,getKeyIndex(36),false);
        NoteInfo fd_6_24n = new NoteInfo(38,23,getKeyIndex(38),false);

        NoteInfo fd_6_25n = new NoteInfo(39,24,getKeyIndex(39),false);
        NoteInfo fd_6_26n = new NoteInfo(39,25,getKeyIndex(39),false);

        fd_6.add(fd_6_1n);fd_6.add(fd_6_2n);fd_6.add(fd_6_3n);fd_6.add(fd_6_4n);
        fd_6.add(fd_6_5n);fd_6.add(fd_6_6n);fd_6.add(fd_6_7n);fd_6.add(fd_6_8n);
        fd_6.add(fd_6_9n);fd_6.add(fd_6_10n);fd_6.add(fd_6_11n);fd_6.add(fd_6_12n);
        fd_6.add(fd_6_13n);fd_6.add(fd_6_14n);fd_6.add(fd_6_15n);fd_6.add(fd_6_16n);
        fd_6.add(fd_6_17n);fd_6.add(fd_6_18n);fd_6.add(fd_6_19n);fd_6.add(fd_6_20n);
        fd_6.add(fd_6_21n);fd_6.add(fd_6_22n);fd_6.add(fd_6_23n);fd_6.add(fd_6_24n);
        fd_6.add(fd_6_25n);fd_6.add(fd_6_26n);


   /* 第八节课 */
        NoteInfo fd_8_1n = new NoteInfo(46,0,getKeyIndex(46),true);
        NoteInfo fd_8_2n = new NoteInfo(44,1,getKeyIndex(44),true);
        NoteInfo fd_8_3n = new NoteInfo(43,2,getKeyIndex(43),true);
        NoteInfo fd_8_4n = new NoteInfo(41,3,getKeyIndex(41),true);

        NoteInfo fd_8_5n = new NoteInfo(39,4,getKeyIndex(39),true);
        NoteInfo fd_8_6n = new NoteInfo(39,5,getKeyIndex(39),true);
        NoteInfo fd_8_7n = new NoteInfo(32,6,getKeyIndex(32),false);
        NoteInfo fd_8_8n = new NoteInfo(34,7,getKeyIndex(34),false);

        NoteInfo fd_8_9n = new NoteInfo(36,8,getKeyIndex(36),false);
        NoteInfo fd_8_10n = new NoteInfo(38,9,getKeyIndex(38),false);
        NoteInfo fd_8_11n = new NoteInfo(39,10,getKeyIndex(39),false);
        NoteInfo fd_8_12n = new NoteInfo(39,11,getKeyIndex(39),false);

        NoteInfo fd_8_13n = new NoteInfo(39,12,getKeyIndex(39),false);
        NoteInfo fd_8_14n = new NoteInfo(46,13,getKeyIndex(46),true);
        NoteInfo fd_8_15n = new NoteInfo(44,14,getKeyIndex(44),true);
        NoteInfo fd_8_16n = new NoteInfo(43,15,getKeyIndex(43),true);

        NoteInfo fd_8_17n = new NoteInfo(41,16,getKeyIndex(41),true);
        NoteInfo fd_8_18n = new NoteInfo(39,17,getKeyIndex(39),true);
        NoteInfo fd_8_19n = new NoteInfo(39,18,getKeyIndex(39),true);
        NoteInfo fd_8_20n = new NoteInfo(32,19,getKeyIndex(32),false);

        NoteInfo fd_8_21n = new NoteInfo(34,20,getKeyIndex(34),false);
        NoteInfo fd_8_22n = new NoteInfo(36,21,getKeyIndex(36),false);
        NoteInfo fd_8_23n = new NoteInfo(38,22,getKeyIndex(38),false);
        NoteInfo fd_8_24n = new NoteInfo(39,23,getKeyIndex(39),false);

        fd_8.add(fd_8_1n);fd_8.add(fd_8_2n);fd_8.add(fd_8_3n);fd_8.add(fd_8_4n);
        fd_8.add(fd_8_5n);fd_8.add(fd_8_6n);fd_8.add(fd_8_7n);fd_8.add(fd_8_8n);
        fd_8.add(fd_8_9n);fd_8.add(fd_8_10n);fd_8.add(fd_8_11n);fd_8.add(fd_8_12n);
        fd_8.add(fd_8_13n);fd_8.add(fd_8_14n);fd_8.add(fd_8_15n);fd_8.add(fd_8_16n);
        fd_8.add(fd_8_17n);fd_8.add(fd_8_18n);fd_8.add(fd_8_19n);fd_8.add(fd_8_20n);
        fd_8.add(fd_8_21n);fd_8.add(fd_8_22n);fd_8.add(fd_8_23n);fd_8.add(fd_8_24n);


   /* 第九节课 */
        NoteInfo fd_9_1n = new NoteInfo(32,0,getKeyIndex(32),false);
        NoteInfo fd_9_2n = new NoteInfo(32,1,getKeyIndex(32),false);
        NoteInfo fd_9_3n = new NoteInfo(36,2,getKeyIndex(36),false);
        NoteInfo fd_9_4n = new NoteInfo(39,3,getKeyIndex(39),false);

        NoteInfo fd_9_5n = new NoteInfo(44,4,getKeyIndex(44),true);
        NoteInfo fd_9_6n = new NoteInfo(44,5,getKeyIndex(44),true);
        NoteInfo fd_9_7n = new NoteInfo(43,6,getKeyIndex(43),true);
        NoteInfo fd_9_8n = new NoteInfo(44,7,getKeyIndex(44),true);

        NoteInfo fd_9_9n = new NoteInfo(43,8,getKeyIndex(43),true);
        NoteInfo fd_9_10n = new NoteInfo(41,9,getKeyIndex(41),true);
        NoteInfo fd_9_11n = new NoteInfo(39,10,getKeyIndex(39),true);
        NoteInfo fd_9_12n = new NoteInfo(39,11,getKeyIndex(39),true);

        NoteInfo fd_9_13n = new NoteInfo(39,12,getKeyIndex(39),true);
        NoteInfo fd_9_14n = new NoteInfo(32,13,getKeyIndex(32),false);
        NoteInfo fd_9_15n = new NoteInfo(32,14,getKeyIndex(32),false);
        NoteInfo fd_9_16n = new NoteInfo(36,15,getKeyIndex(36),false);

        NoteInfo fd_9_17n = new NoteInfo(39,16,getKeyIndex(39),false);
        NoteInfo fd_9_18n = new NoteInfo(44,17,getKeyIndex(44),true);
        NoteInfo fd_9_19n = new NoteInfo(44,18,getKeyIndex(44),true);
        NoteInfo fd_9_20n = new NoteInfo(39,19,getKeyIndex(39),false);

        NoteInfo fd_9_21n = new NoteInfo(39,20,getKeyIndex(39),false);
        NoteInfo fd_9_22n = new NoteInfo(36,21,getKeyIndex(36),false);
        NoteInfo fd_9_23n = new NoteInfo(36,22,getKeyIndex(36),false);
        NoteInfo fd_9_24n = new NoteInfo(32,23,getKeyIndex(32),false);

        fd_9.add(fd_9_1n);fd_9.add(fd_9_2n);fd_9.add(fd_9_3n);fd_9.add(fd_9_4n);
        fd_9.add(fd_9_5n);fd_9.add(fd_9_6n);fd_9.add(fd_9_7n);fd_9.add(fd_9_8n);
        fd_9.add(fd_9_9n);fd_9.add(fd_9_10n);fd_9.add(fd_9_11n);fd_9.add(fd_9_12n);
        fd_9.add(fd_9_13n);fd_9.add(fd_9_14n);fd_9.add(fd_9_15n);fd_9.add(fd_9_16n);
        fd_9.add(fd_9_17n);fd_9.add(fd_9_18n);fd_9.add(fd_9_19n);fd_9.add(fd_9_20n);
        fd_9.add(fd_9_21n);fd_9.add(fd_9_22n);fd_9.add(fd_9_23n);fd_9.add(fd_9_24n);


   /* 第十一节课 */
        NoteInfo fd_11_1n = new NoteInfo(46,0,getKeyIndex(46),true);
        NoteInfo fd_11_2n = new NoteInfo(44,1,getKeyIndex(44),true);
        NoteInfo fd_11_3n = new NoteInfo(43,2,getKeyIndex(43),true);
        NoteInfo fd_11_4n = new NoteInfo(41,3,getKeyIndex(41),true);

        NoteInfo fd_11_5n = new NoteInfo(39,4,getKeyIndex(39),true);
        NoteInfo fd_11_6n = new NoteInfo(41,5,getKeyIndex(41),true);
        NoteInfo fd_11_7n = new NoteInfo(43,6,getKeyIndex(43),true);
        NoteInfo fd_11_8n = new NoteInfo(34,7,getKeyIndex(34),false);

        NoteInfo fd_11_9n = new NoteInfo(32,8,getKeyIndex(32),false);
        NoteInfo fd_11_10n = new NoteInfo(31,9,getKeyIndex(31),false);
        NoteInfo fd_11_11n = new NoteInfo(29,10,getKeyIndex(29),false);
        NoteInfo fd_11_12n = new NoteInfo(27,11,getKeyIndex(27),false);

        NoteInfo fd_11_13n = new NoteInfo(29,12,getKeyIndex(29),false);
        NoteInfo fd_11_14n = new NoteInfo(31,13,getKeyIndex(31),false);
        NoteInfo fd_11_15n = new NoteInfo(41,14,getKeyIndex(41),true);
        NoteInfo fd_11_16n = new NoteInfo(43,15,getKeyIndex(43),true);

        NoteInfo fd_11_17n = new NoteInfo(44,16,getKeyIndex(44),true);
        NoteInfo fd_11_18n = new NoteInfo(46,17,getKeyIndex(46),true);
        NoteInfo fd_11_19n = new NoteInfo(39,18,getKeyIndex(39),true);
        NoteInfo fd_11_20n = new NoteInfo(39,19,getKeyIndex(39),true);

        NoteInfo fd_11_21n = new NoteInfo(29,20,getKeyIndex(29),false);
        NoteInfo fd_11_22n = new NoteInfo(31,21,getKeyIndex(31),false);
        NoteInfo fd_11_23n = new NoteInfo(32,22,getKeyIndex(32),false);
        NoteInfo fd_11_24n = new NoteInfo(34,23,getKeyIndex(34),false);

        NoteInfo fd_11_25n = new NoteInfo(27,24,getKeyIndex(27),false);

        fd_11.add(fd_11_1n);fd_11.add(fd_11_2n);fd_11.add(fd_11_3n);fd_11.add(fd_11_4n);
        fd_11.add(fd_11_5n);fd_11.add(fd_11_6n);fd_11.add(fd_11_7n);fd_11.add(fd_11_8n);
        fd_11.add(fd_11_9n);fd_11.add(fd_11_10n);fd_11.add(fd_11_11n);fd_11.add(fd_11_12n);
        fd_11.add(fd_11_13n);fd_11.add(fd_11_14n);fd_11.add(fd_11_15n);fd_11.add(fd_11_16n);
        fd_11.add(fd_11_17n);fd_11.add(fd_11_18n);fd_11.add(fd_11_19n);fd_11.add(fd_11_20n);
        fd_11.add(fd_11_21n);fd_11.add(fd_11_22n);fd_11.add(fd_11_23n);fd_11.add(fd_11_24n);
        fd_11.add(fd_11_25n);



   /* 第十二节课 */
        NoteInfo fd_12_1n = new NoteInfo(34,0,getKeyIndex(34),false);
        NoteInfo fd_12_2n = new NoteInfo(31,1,getKeyIndex(31),false);
        NoteInfo fd_12_3n = new NoteInfo(34,2,getKeyIndex(34),false);
        NoteInfo fd_12_4n = new NoteInfo(31,3,getKeyIndex(31),false);

        NoteInfo fd_12_5n = new NoteInfo(29,4,getKeyIndex(29),false);
        NoteInfo fd_12_6n = new NoteInfo(29,5,getKeyIndex(29),false);
        NoteInfo fd_12_7n = new NoteInfo(29,6,getKeyIndex(29),false);
        NoteInfo fd_12_8n = new NoteInfo(32,7,getKeyIndex(32),false);

        NoteInfo fd_12_9n = new NoteInfo(31,8,getKeyIndex(31),false);
        NoteInfo fd_12_10n = new NoteInfo(34,9,getKeyIndex(34),false);
        NoteInfo fd_12_11n = new NoteInfo(46,10,getKeyIndex(46),true);
        NoteInfo fd_12_12n = new NoteInfo(43,11,getKeyIndex(43),true);

        NoteInfo fd_12_13n = new NoteInfo(46,12,getKeyIndex(46),true);
        NoteInfo fd_12_14n = new NoteInfo(43,13,getKeyIndex(43),true);
        NoteInfo fd_12_15n = new NoteInfo(41,14,getKeyIndex(41),true);
        NoteInfo fd_12_16n = new NoteInfo(44,15,getKeyIndex(44),true);

        NoteInfo fd_12_17n = new NoteInfo(43,16,getKeyIndex(43),true);
        NoteInfo fd_12_18n = new NoteInfo(41,17,getKeyIndex(41),true);
        NoteInfo fd_12_19n = new NoteInfo(39,18,getKeyIndex(39),true);

        fd_12.add(fd_12_1n);fd_12.add(fd_12_2n);fd_12.add(fd_12_3n);fd_12.add(fd_12_4n);
        fd_12.add(fd_12_5n);fd_12.add(fd_12_6n);fd_12.add(fd_12_7n);fd_12.add(fd_12_8n);
        fd_12.add(fd_12_9n);fd_12.add(fd_12_10n);fd_12.add(fd_12_11n);fd_12.add(fd_12_12n);
        fd_12.add(fd_12_13n);fd_12.add(fd_12_14n);fd_12.add(fd_12_15n);fd_12.add(fd_12_16n);
        fd_12.add(fd_12_17n);fd_12.add(fd_12_18n);fd_12.add(fd_12_19n);
    }


/*--------------------------------------------------------------------------------------------------------乐谱合集*/

    /**
     * 根据课程id 获取对应的 乐谱合集
     * //确认选谱  1培训 2小学 3幼儿园
     * @return
     */
    public static ArrayList<NoteInfo> getNoteList(int courseId, String tag){

        ArrayList<NoteInfo> result = null;
        switch(courseId)
        {
            case Constant.COURSE_1:
                result = note_1ist[0];
                break;
            case Constant.COURSE_187:
                result = note_1ist[0];
                break;
            case Constant.COURSE_2:
                result = note_1ist[1];
                break;
            case Constant.COURSE_3:
                result = note_1ist[2];
                break;

            //丰台一小
            case Constant.COURSE_2_ft:
                if(Constant.PLAY_TOGHTER_FOLLOW_ONE.equals(tag)){
                    result = fengtai_1ist[0];
                }else{
                    result = fengtai_1ist[1];
                }
                break;
            case Constant.COURSE_3_ft:
                result = fengtai_1ist[2];
                break;
            case Constant.COURSE_5:
                result = fengtai_1ist[3];
                break;
            case Constant.COURSE_6:
                result = fengtai_1ist[4];
                break;
            case Constant.COURSE_8:
                result = fengtai_1ist[5];
                break;
            case Constant.COURSE_9:
                result = fengtai_1ist[6];
                break;
            case Constant.COURSE_11:
                result = fengtai_1ist[7];
                break;
            case Constant.COURSE_12:
                result = fengtai_1ist[8];
                break;
        }

        return result;

    }

    //根据音符index获取琴键index
    public static int getKeyIndex(int noteIndex){
        int result  = -1;
        switch(noteIndex) {
            case 27:
                result = 1;
                break;
            case 29:
                result = 2;
                break;
            case 31:
                result = 3;
                break;
            case 32:
                result = 4;
                break;
            case 34:
                result = 5;
                break;
            case 36:
                result = 6;
                break;
            case 38:
                result = 7;
                break;
            case 39:
                result = 8;
                break;
            case 41:
                result = 9;
                break;
            case 43:
                result = 10;
                break;
            case 44:
                result = 11;
                break;
            case 46:
                result = 12;
                break;
            case 48:
                result = 13;
                break;
            case 50:
                result = 14;
                break;

        }

        return result;
    }

    //--------------------------------------------------------------钢琴指令---------------------------------------------------------------

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
    public static void beatSync(final Context context,final int index,final boolean isRed,final long time){

                UsbDeviceInfo.getUsbDeviceInfo(context).setData(getLightbytes(index,isRed));
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                UsbDeviceInfo.getUsbDeviceInfo(context).setData(getCloseBytes(index,isRed));

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

        for(int i=27;i<50;i++){
            UsbDeviceInfo.getUsbDeviceInfo(context).setData(getCloseBytes(i, true));
            UsbDeviceInfo.getUsbDeviceInfo(context).setData(getCloseBytes(i, false));
        }

//        UsbDeviceInfo.getUsbDeviceInfo(context).setData(getCloseBytes(39, true));
//        UsbDeviceInfo.getUsbDeviceInfo(context).setData(getCloseBytes(41, true));
//        UsbDeviceInfo.getUsbDeviceInfo(context).setData(getCloseBytes(43, true));
//
//        UsbDeviceInfo.getUsbDeviceInfo(context).setData(getCloseBytes(39, false));
//        UsbDeviceInfo.getUsbDeviceInfo(context).setData(getCloseBytes(41, false));
//        UsbDeviceInfo.getUsbDeviceInfo(context).setData(getCloseBytes(43, false));

    }

    public static void closeAndOpenNext(final Context context,final int closeIndex, final boolean closeRed, final int openIndex, final boolean openRed){

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                UsbDeviceInfo.getUsbDeviceInfo(context).setData(getCloseBytes(closeIndex,closeRed));
//
//                try {
//                    Thread.sleep(300);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                UsbDeviceInfo.getUsbDeviceInfo(context).setData(getLightbytes(openIndex,openRed));
//
//            }
//        }).start();

        UsbDeviceInfo.getUsbDeviceInfo(context).setData(getCloseBytes(closeIndex,closeRed));

        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        UsbDeviceInfo.getUsbDeviceInfo(context).setData(getLightbytes(openIndex,openRed));

    }




    /* ----------------------------自动跟灯逻辑 ----------------------------------------------   */
    /**
     * delay1  亮灯时间点
     * dur1    亮灯时长
     * color1   亮灯颜色    c下蓝 g上红
     * index1   亮灯位置
     */

    //丰台一小 第二课一
    public static float[] delay_ft_2_1 = {07.000f,19.125f,30.875f,43.000f};
    public static float[] dur1_ft_2_1 = { 0.50f,0.50f,0.50f,0.50f,0.50f,0.50f,0.50f,0.50f,0.50f,0.50f,0.50f,0.50f,0.50f,0.50f,0.50f,0.50f};
    public static int[] color1_ft_2_1 = { 1,1,1,-1,1,1,1,-1,1,1,1,-1,1,1,1,-1};
    public static int[] index1_ft_2_1 = { 39,39,39,-1,39,39,39,-1,39,39,39,-1,39,39,39,-1};

    //丰台一小 第二课二
    public static float[] delay_ft_2_2 = {7.18f,19.1f,31.13f,43.1f};
    public static float[] dur1_ft_2_2 = { 0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f};
    public static int[] color1_ft_2_2 = { 0,0,0,-1,1,1,1,-1,0,0,0,-1,1,1,1,-1};
    public static int[] index1_ft_2_2 = { 39,39,39,-1,39,39,39,-1,39,39,39,-1,39,39,39,-1};

    //丰台一小 第三课
    public static float[] delay_ft_3 = {12.764f,41.000f};
    public static float[] dur1_ft_3 = { 1.42f,1.42f,0.71f,0.71f,1.42f,1.42f,1.42f,0.71f,0.71f,1.42f,1.42f,1.42f,0.71f,0.71f,1.42f,0.71f,0.71f,0.71f,0.71f,0.71f,0.71f,1.42f};
    public static int[] color1_ft_3 = { 1,1,0,0,0,1,1,0,0,0,1,1,0,0,0,1,1,1,1,0,0,0};
    public static int[] index1_ft_3 = { 39,41,39,39,39,41,43,39,39,39,39,41,39,39,39,41,41,43,43,39,39,39};

    //丰台一小 第五课
    public static float[] delay_ft_5 = {12.000f,44.000f};
    public static float[] dur1_ft_5 = {1.34f,1.34f,1.34f,1.34f,0.67f,0.67f,0.67f,0.67f,0.67f,0.67f,1.34f,1.34f,1.34f,1.34f,1.34f,0.67f,0.67f,0.67f,0.67f,0.67f,0.67f,1.34f};
    public static int[] color1_ft_5 = { 1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0};
    public static int[] index1_ft_5 = { 39,41,43,43,39,39,41,41,43,43,43,39,38,36,36,39,39,38,38,36,36,36};

    //丰台一小 第六课
    public static float[] delay_ft_6 = {09.472f,41.051f};
    public static float[] dur1_ft_6 = {0.79f,0.79f,0.79f,0.79f,1.58f,1.58f,0.79f,0.79f,0.79f,0.79f,0.79f,0.79f,0.79f,0.79f,0.79f,0.79f,0.79f,0.79f,0.79f,0.79f,1.58f,0.79f,0.79f,0.79f,0.79f,1.58f,0.79f,0.79f};
    public static int[] color1_ft_6 = { 1,1,1,1,1,1,0,0,0,0,0,0,0,-1,1,1,1,1,1,1,1,0,0,0,0,0,0,-1};
    public static int[] index1_ft_6 = { 39,41,43,44,46,46,39,38,36,38,39,39,39,-1,39,41,43,44,46,46,46,39,38,36,38,39,39,-1};

    //丰台一小 第八课
    public static float[] delay_ft_8 = {14.110f,55.229f};
    public static float[] dur1_ft_8 = {0.79f,0.79f,0.79f,0.79f,1.58f,1.58f,0.79f,0.79f,0.79f,0.79f,0.79f,0.79f,0.79f,0.79f,0.79f,0.79f,0.79f,0.79f,0.79f,0.79f,0.79f,0.79f,0.79f,0.79f,0.79f,0.79f,3.16f};
    public static int[] color1_ft_8 = {1,1,1,1,1,1,0,0,0,0,0,0,0,-1,1,1,1,1,1,-1,1,-1,0,0,0,0,0};
    public static int[] index1_ft_8 = {46,44,43,41,39,39,32,34,36,38,39,39,39,-1,46,44,43,41,39,-1,39,-1,32,34,36,38,39};

    //丰台一小 第九课
    public static float[] delay_ft_9 = {10.000f,34.000f};
    public static float[] dur1_ft_9 = {0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,0.5f,2.00f};
    public static int[] color1_ft_9 = { 0,0,0,0,1,-1,1,-1,1,1,1,1,1,1,1,-1,0,0,0,0,1,-1,1,-1,0,0,0,0,0};
    public static int[] index1_ft_9 = { 32,32,36,39,44,-1,44,-1,43,44,43,41,39,39,39,-1,32,32,36,39,44,-1,44,-1,39,39,36,36,32};

    //丰台一小 第十一课
    public static float[] delay_ft_11 = {22.833f,65.333f};
    public static float[] dur1_ft_11 = {0.67f,0.67f,0.67f,0.67f,0.67f,0.67f,0.67f,0.67f,0.67f,0.67f,0.67f,0.67f,0.67f,0.67f,0.67f,0.67f,0.67f,0.67f,0.67f,0.67f,1.34f,1.34f,0.67f,0.67f,0.67f,0.67f,2.68f};
    public static int[] color1_ft_11 = { 1,1,1,1,1,1,1,-1,0,0,0,0,0,0,0,-1,1,1,1,1,1,1,0,0,0,0,0};
    public static int[] index1_ft_11 = { 46,44,43,41,39,41,43,-1,34,32,31,29,27,29,31,-1,41,43,44,46,39,39,29,31,32,34,27};

    //丰台一小 第十二课
    public static float[] delay_ft_12 = {06.583f,33.333f};
    public static float[] dur1_ft_12 = {1.34f,0.67f,0.67f,1.34f,0.67f,0.67f,0.67f,0.67f,0.67f,0.67f,1.34f,0.67f,0.67f,1.34f,0.67f,0.67f,1.34f,0.67f,0.67f,0.67f,0.67f,0.67f,0.67f,2.68f};
    public static int[] color1_ft_12 = { 0,0,-1,0,0,-1,0,0,0,0,0,0,-1,1,1,-1,1,1,-1,1,1,1,1,1};
    public static int[] index1_ft_12 = { 34,31,-1,34,31,-1,29,29,29,32,31,34,-1,46,43,-1,46,43,-1,41,44,43,41,39 };

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


    /* ----------------------------自动跟灯逻辑 ----------------------------------------------   */





}





