package com.angelmusic.student.core.music;

import android.content.Context;

import com.angelmusic.stu.usb.UsbDeviceInfo;
import com.angelmusic.student.R;
import com.angelmusic.student.constant.Constant;

import java.util.ArrayList;

/**
 * Created by DELL on 2017/2/1
 */

public class MusicNote {


    /**
     * delay1  亮灯时间点          循环播放时间节点
     * dur1    亮灯时长            每个音符显示持续时间
     * color1   亮灯颜色           c==0下蓝 g==1上红
     * index1   亮灯位置
     */
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


    //C社培及幼儿园上学期乐谱  第2课

    //C社培及幼儿园上学期乐谱  第3课
    public static float[] time_c_3 = { 19.69f, 41.88f };
    public static float[] duration_c_3 = { 0.92f, 0.92f, 0.92f, 0.92f, 0.92f, 0.92f, 0.92f, 0.92f, 0.92f, 0.92f, 0.92f, 0.92f, 0.92f, 0.92f, 0.92f, 0.92f};
    //string[] celf = { "g", "g", "g", "", "g", "g", "g", "", "g", "g", "g", "", "g", "g", "g", "" };
    public static int[] color1_c_3 ={1,1,1,-1,1,1,1,-1,1,1,1,-1,1,1,1,-1};
    public static int[] note_c_3 = { 39, 41, 43, -1, 39, 41, 43, -1, 43, 41, 39, -1, 43, 41, 39, -1};

    //C社培及幼儿园上学期乐谱  第4课
    public static float[] time_c_4 = { 14.54f, 43.63f };
    public static float[] duration_c_4 = { 0.61f, 0.61f, 0.61f, 0.61f, 1.22f, 1.22f, 0.61f, 0.61f, 0.61f, 0.61f, 1.22f, 1.22f, 0.61f, 0.61f, 0.61f, 0.61f, 1.22f, 1.22f, 0.61f, 0.61f, 0.61f, 0.61f, 1.22f, 1.22f};
    //string[] celf = { "g", "g", "g", "g", "g", "g", "g", "g", "g", "g", "g", "g", "g", "g", "g", "g", "g", "g", "g", "g", "g", "g", "g", "g", };
    public static int[] color1_c_4 ={1,1,1,1,1,1,1,1,1,1,1,1, 1,1,1,1,1,1,1,1,1,1,1,1};
    public static int[] note_c_4 = { 39, 41, 43, 39, 41, 41, 43, 39, 41, 41, 39, 39, 39, 41, 43, 39, 41, 41, 43, 41, 39, 41, 39, 39 };

    //C社培及幼儿园上学期乐谱  第5课
    public static float[] time_c_5 = { 13.75f, 53.75f };
    public static float[] duration_c_5 = { 0.63f, 0.63f, 0.63f, 0.63f, 1.26f, 1.26f, 0.63f, 0.63f, 0.63f, 0.63f, 1.26f, 1.26f, 0.63f, 0.63f, 0.63f, 0.63f, 1.26f, 1.26f, 0.63f, 0.63f, 0.63f, 0.63f, 1.26f, 1.26f };
    //string[] celf = { "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", };
    public static int[] color1_c_5 ={0,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,0,0,0,0};
    public static int[] note_c_5 = { 36, 36, 38, 38, 36, 36, 36, 36, 38, 38, 39, 39, 39, 39, 38, 38, 39, 39, 39, 39, 38, 38, 36, 36 };

    //C社培及幼儿园上学期乐谱  第6课
    public static float[] time_c_6 = { 11.58f ,45.26f };
    public static float[] duration_c_6 = { 0.53f, 0.53f, 0.53f, 0.53f, 0.53f, 0.53f, 0.53f, 0.53f, 0.53f, 0.53f, 0.53f, 0.53f, 1.06f, 1.06f, 0.53f, 0.53f, 0.53f, 0.53f, 0.53f, 0.53f, 0.53f, 0.53f, 0.53f, 0.53f, 0.53f, 0.53f, 1.06f, 1.06f};
    //string[] celf = { "c", "c", "c", "", "c", "c", "c", "", "c", "c", "c", "c", "c", "c", "g", "g", "g", "", "g", "g", "g", "", "g", "g", "g", "g", "g", "g", };
    public static int[] color1_c_6 ={0,0,0,-1,0,0,0,-1,0,0,0,0,0,0,1,1,1,-1,1,1,1,-1,1,1,1,1,1,1};
    public static int[] note_c_6 = { 39, 39, 36, -1,39, 39, 36,-1, 39, 38, 36, 38, 39, 36, 39, 43, 41,-1, 39, 43, 41,-1, 43, 39, 41, 43, 39, 39 };

    //C社培及幼儿园上学期乐谱  第7课
    public static float[] time_c_7 = {10.00f};
    public static float[] duration_c_7 = {0.5f,0.5f,0.5f,0.5f,1.0f,1.0f,2.0f,2.0f,0.5f,0.5f,0.5f,0.5f,1.0f,1.0f,2.0f,2.0f,0.5f,0.5f,0.5f,0.5f,1.0f,1.0f,0.5f,0.5f,0.5f,0.5f,1.0f,1.0f,0.5f,0.5f,0.5f,0.5f,1.0f,1.0f,2.0f,2.0f};
    //string[] celf = {g,g,g,g,g,g,g,g,c,c,c,c,c,c,c,c,g,g,g,g,g,g,g,g,g,g,g,g,g,g,g,g,g,c,c,g};
    public static int[] color1_c_7 ={1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,1};
    public static int[] note_c_7 = {39,39,41,43,41,39,41,41,39,39,38,36,38,39,38,38,39,39,41,43,41,39,39,39,41,43,41,39,43,43,41,39,41,38,39,39} ;

    //C社培及幼儿园上学期乐谱  第8课
    public static float[] time_c_8 = {16.55f,52.45f};
    public static float[] duration_c_8 = {0.75f,0.75f,0.75f,0.75f,0.75f,0.75f,1.5f,0.75f,0.75f,0.75f,0.75f,1.5f,1.5f,0.75f,0.75f,0.75f,0.75f,0.75f,0.75f,1.5f,0.75f,0.75f,0.75f,0.75f,3.0f};
    //string[] celf = {g,g,g,g,g,g,g,g,g,g,g,g,c,g,g,g,g,g,g,g,c,c,c,c,c};
    public static int[] color1_c_8 ={1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,0,0,0,0,0};
    public static int[] note_c_8 = {39,39,41,41,43,44,46,39,39,41,41,39,38,39,39,41,41,43,44,46,39,38,36,38,39}  ;

    //C社培及幼儿园上学期乐谱  第9课
    public static float[] time_c_9 = {15.00f};
    public static float[] duration_c_9 = {0.75f,0.75f,0.75f,0.75f,1.5f,1.5f,0.75f,0.75f,0.75f,0.75f,1.5f,1.5f,0.75f,0.75f,0.75f,0.75f,1.5f,1.5f,0.75f,0.75f,0.75f,0.75f,3.0f,0.75f,0.75f,1.5f,0.75f,0.75f,1.5f,0.75f,0.75f,1.5f,3.0f,0.75f,0.75f,0.75f,0.75f,0.75f,0.75f,1.5f,1.5f,1.5f,3.0f};
    //string[] celf = {g,g,g,g,g,g,g,g,  g,g,g,g,g,g,g,g,  g,g,g,g,g,g,g,g,  g,g,g,g,g,c,c,c,   g,g,g,g,g,c,c,c,  g,g,g};
    public static int[] color1_c_9 ={1,1,1,1,1,1,1,1,  1,1,1,1,1,1,1,1, 1,1,1,1,1,1,1,1, 1,1,1,1,1,0,0,0,  1,1,1,1,1,0,0,0, 1,1,1};
    public static int[] note_c_9 = {39,39,43,46,39,39,41,43,44,41,43,39,39,39,43,46,39,39,41,43,44,41,39,44,41,41,43,39,39,36,38,39,41,39,39,43,46,39,38,39,41,41,39};



    //后18  第11课
    public static float[] time_c_11 = {10.799f,39.599f};
    public static float[] duration_c_11 = {1.200f,1.200f,0.600f,0.600f,1.200f,0.600f,0.600f,0.600f,0.600f,2.400f,1.200f,1.200f,0.600f,0.600f,1.200f,0.600f,0.600f,0.600f,0.600f,2.400f};
    //string[] celf = {g,g,g,g,g,  g,g,g,g,g,  c,c,c,c,c,  c,c,c,c,c};
    public static int[] color1_c_11 ={1,1,1,1,1,  1,1,1,1,1, 0,0,0,0,0,  0,0,0,0,0};
    public static int[] note_c_11 = {39,41,63,44,46,46,46,44,44,43,39,38,36,34,32,32,34,36,38,39};

    //后18  第12课
    public static float[] time_c_12 = {13.200f,42.000f};
    public static float[] duration_c_12 = {1.200f,0.600f,0.600f,0.600f,0.600f,1.200f,0.600f,0.600f,0.600f,0.600f,0.600f,0.600f,0.600f,1.200f,0.600f,0.600f,0.600f,0.600f,1.200f,0.600f,0.600f,0.600f,0.600f,0.600f,0.600f,0.600f};
    //string[] celf = {g,g,g,g,g,  g,g,g,g,g,  g,g,,c,c,   c,c,c,c,c,  c,c,c,c,c,  c,,};
    public static int[] color1_c_12 ={1,1,1,1,1, 1,1,1,1,1, 1,1,-1,0,0, 0,0,0,0,0, 0,0,0,0,0, 0,-1};
    public static int[] note_c_12 = {46,43,43,44,44,43,41,41,39,41,43,43,43,-1,32,36,36,34,34,36,38,38,36,38,39,39,39,-1};

    //后18  第13课
    public static float[] time_c_13 = {8.436f,29.061f};
    public static float[] duration_c_13 = {1.875f,1.875f,0.9375f,0.9375f,1.875f,0.9375f,0.9375f,0.9375f,0.9375f,1.875f,0.9375f,0.9375f,1.875f,1.875f,0.9375f,0.9375f,1.875f,0.9375f,0.9375f,0.9375f,0.9375f,0.9375f,0.9375f,0.9375f,0.9375f,};
    //string[] celf = {c,c,c,c,c,  c,c,c,c,c,  c,,g,g,g,  g,g,g,g,g,  g,g,g,g,,};
    public static int[] color1_c_13 ={0,0,0,0,0, 0,0,0,0,0, 0,-1,1,1,1, 1,1,1,1,1, 1,1,1,1,-1};
    public static int[] note_c_13 = {27,29,31,31,31,34,34,32,32,31,31,-1,46,44,43,43,43,41,41,43,41,39,39,39,-1};

    //后18  第14课
    public static float[] time_c_14 = {12.272f,45.000f};
    public static float[] duration_c_14 = {0.682f,0.682f,1.362f,0.682f,0.682f,1.362f,1.362f,0.682f,0.682f,2.727f,0.682f,0.682f,1.362f,0.682f,0.682f,1.362f,0.682f,0.682f,0.682f,0.682f,0.682f,0.682f,0.682f,0.682f,};
    //string[] celf = {c,c,c,c,c,  c,c,c,c,c,  g,g,g,g,g,  g,g,g,g,g,  g,g,,};
    public static int[] color1_c_14 ={0,0,0,0,0, 0,0,0,0,0, 1,1,1,1,1, 1,1,1,1,1, 1,1,-1};
    public static int[] note_c_14 = {27,27,34,34,34,27,29,31,32,31,27,27,34,34,34,27,32,32,31,29,27,27,27,-1,};

    //后18  第15课
    public static float[] time_c_15 = {13.500f,49.500f};
    public static float[] duration_c_15 = {1.500f,0.750f,0.750f,0.750f,0.750f,1.500f,1.500f,0.750f,0.750f,1.500f,1.500f,1.500f,0.750f,0.750f,0.750f,0.750f,1.500f,0.750f,0.750f,0.750f,0.750f,3.000f,};
    //string[] celf = {g,g,g,g,g,  g,c,c,c,c,  c,g,g,g,g,  g,g,c,c,c, c,c,};
    public static int[] color1_c_15 ={1,1,1,1,1, 1,0,0,0,0, 0,1,1,1,1, 1,1,0,0,0, 0,0};
    public static int[] note_c_15 = {46,46,44,43,41,39,31,31,32,34,34,46,46,44,43,41,39,34,32,31,29,27,};

    //后18  第16课
    public static float[] time_c_16 = {12.000f,40.800f};
    public static float[] duration_c_16 = {0.600f,0.600f,0.600f,0.600f,0.600f,0.600f,0.600f,0.600f,0.600f,0.600f,0.600f,0.600f,0.600f,0.600f,1.200f,0.600f,0.600f,0.600f,0.600f,0.600f,0.600f,0.600f,0.600f,0.600f,0.600f,0.600f,0.600f,2.400f,};
    //string[] celf = { "g", "g", "g", "", "g", "g", "g", "", "g", "g", "g", "g", "g", "g", "g", "c", "c", "c", "", "c", "c", "c", "", "c", "c", "c", "c", "c"};
    public static int[] color1_c_16 ={1,1,1,-1,1, 1,1,-1,1,1, 1,1,1,1,1, 0,0,0,-1,0, 0,0,-1,0,0,0,0,0};
    public static int[] note_c_16 = {46, 43, 43, -1, 44, 41, 41, -1, 39, 41, 43, 44, 46, 46, 46, 34, 31, 31, -1, 32, 29, 29, -1, 27, 31, 34, 34, 27};

    //后18  第17课
    public static float[] time_c_17 = {20.250f,47.250f};
    public static float[] duration_c_17 = {0.750f,0.750f,0.750f,0.750f,0.750f,0.750f,2.250f,2.250f,2.250f,2.250f,0.750f,0.750f,0.750f,0.750f,0.750f,0.750f,2.250f,2.250f,2.250f,2.250f,};
    //string[] celf = {g,g,g,g,g,  g,g,c,g,c,  g,g,g,g,g,  g,g,c,g,c,};
    public static int[] color1_c_17 ={1,1,1,1,1, 1,1,0,1,0, 1,1,1,1,1, 1,1,0,1,0};
    public static int[] note_c_17 = {46,46,46,44,44,  44,44,34,43,27,  43,43,43,41,41,  41,41,34,39,27};

    //后18  第18课
    public static float[] time_c_18 = {18.409f,42.954f};
    public static float[] duration_c_18 = {0.681f, 0.681f, 0.681f, 0.681f, 0.681f, 0.681f,2.045f,2.045f,2.045f,2.045f,0.681f, 0.681f, 0.681f, 0.681f, 0.681f, 0.681f,2.045f,2.045f,2.045f,2.045f,};
    //string[] celf = {c,c,c,g,g,  g,g,c,g,c,  g,g,g,c,c,  c,g,c,g,c,};
    public static int[] color1_c_18 ={0,0,0,1,1, 1,1,0,1,0, 1,1,1,0,0, 0,1,0,1,0,};
    public static int[] note_c_18 = {27,31,34,39,43,46,44,32,43,27,46,43,39,34,31,27,41,34,39,27,};

    //后18  第19课
    public static float[] time_c_19 = {16.421f,56.842f};
    public static float[] duration_c_19 = {0.631f,0.631f,0.631f,0.631f,0.631f,0.631f,0.631f,0.631f,0.631f,1.263f,0.631f,0.631f,1.263f,0.631f,2.526f,2.526f,0.631f,0.631f,0.631f,0.631f,0.631f,0.631f,0.631f,0.631f,0.631f,1.263f,0.631f,0.631f,1.263f,0.631f,2.526f,2.526f,};
    //string[] celf = {g,g,g,g,g,  g,,g,c,g,  g,c,g,g,c,  g,g,g,g,g,  g,,g,c,g, g,c,g,g,c,};
    public static int[] color1_c_19 ={1,1,1,1,1, 1,-1,10,1, 1,0,1,1,0, 1,1,1,1,1, 1,-1,1,0,1, 1,0,1,1,0};
    public static int[] note_c_19 = {39,39,43,43,46,46,43,-1,41,34,46,44,34,41,43,27,43,43,39,39,34,34,31,-1,41,34,43,44,34,41,39,27,};

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
    /* 夏津 */
    public static final ArrayList<NoteInfo> note_1 = new ArrayList<>();
    public static final ArrayList<NoteInfo> note_2 = new ArrayList<>();
    public static final ArrayList<NoteInfo> note_3 = new ArrayList<>();

    /*  丰台一小谱子  */
    public static  ArrayList<NoteInfo> fd_1 = new ArrayList<>();
    public static  ArrayList<NoteInfo> fd_2 = new ArrayList<>();
    public static  ArrayList<NoteInfo> fd_3 = new ArrayList<>();
    public static  ArrayList<NoteInfo> fd_5= new ArrayList<>();
    public static  ArrayList<NoteInfo> fd_6 = new ArrayList<>();
    public static  ArrayList<NoteInfo> fd_8 = new ArrayList<>();
    public static  ArrayList<NoteInfo> fd_9 = new ArrayList<>();
    public static  ArrayList<NoteInfo> fd_11 = new ArrayList<>();
    public static  ArrayList<NoteInfo> fd_12 = new ArrayList<>();

    /* C社培及幼儿园上学期乐谱 */
    public static ArrayList<NoteInfo> c_2 = new ArrayList<>();
    public static ArrayList<NoteInfo> c_3 = new ArrayList<>();
    public static ArrayList<NoteInfo> c_4 = new ArrayList<>();
    public static ArrayList<NoteInfo> c_5 = new ArrayList<>();
    public static ArrayList<NoteInfo> c_6 = new ArrayList<>();
    public static ArrayList<NoteInfo> c_7 = new ArrayList<>();
    public static ArrayList<NoteInfo> c_8 = new ArrayList<>();
    public static ArrayList<NoteInfo> c_9 = new ArrayList<>();

    /* 后18节课 */
    public static ArrayList<NoteInfo> c_11 = new ArrayList<>();
    public static ArrayList<NoteInfo> c_12 = new ArrayList<>();
    public static ArrayList<NoteInfo> c_13 = new ArrayList<>();
    public static ArrayList<NoteInfo> c_14 = new ArrayList<>();
    public static ArrayList<NoteInfo> c_15 = new ArrayList<>();
    public static ArrayList<NoteInfo> c_16 = new ArrayList<>();
    public static ArrayList<NoteInfo> c_17 = new ArrayList<>();
    public static ArrayList<NoteInfo> c_18 = new ArrayList<>();
    public static ArrayList<NoteInfo> c_19 = new ArrayList<>();

    public static final ArrayList[] fengtai_1ist = {fd_1,fd_2,fd_3,fd_5,fd_6,fd_8,fd_9,fd_11,fd_12};
    public static final ArrayList[] c_1ist = {c_2,c_3,c_4,c_5,c_6,c_7,c_8,c_9};
    public static final ArrayList[] h18_1ist = {c_11,c_12,c_13,c_14,c_15,c_16,c_17,c_18,c_19};
    public static final ArrayList[] note_1ist = {note_1,note_2,note_3};

    static{

        /*丰台一小*/
        fd_1.addAll(setNoteList(color1_ft_2_1,index1_ft_2_1));
        fd_2.addAll(setNoteList(color1_ft_2_2,index1_ft_2_2));
        fd_3.addAll(setNoteList(color1_ft_3,index1_ft_3));
        fd_5.addAll(setNoteList(color1_ft_5,index1_ft_5));
        fd_6.addAll(setNoteList(color1_ft_6,index1_ft_6));
        fd_8.addAll(setNoteList(color1_ft_8,index1_ft_8));
        fd_9.addAll(setNoteList(color1_ft_9,index1_ft_9));
        fd_11.addAll(setNoteList(color1_ft_11,index1_ft_11));
        fd_12.addAll(setNoteList(color1_ft_12,index1_ft_12));

        /*C社培及幼儿园上学期乐谱*/
        c_3.addAll(setNoteList(color1_c_3,note_c_3));
        c_4.addAll(setNoteList(color1_c_4,note_c_4));
        c_5.addAll(setNoteList(color1_c_5,note_c_5));
        c_6.addAll(setNoteList(color1_c_6,note_c_6));
        c_7.addAll(setNoteList(color1_c_7,note_c_7));
        c_8.addAll(setNoteList(color1_c_8,note_c_8));
        c_9.addAll(setNoteList(color1_c_9,note_c_9));

        /*后18*/
        c_11.addAll(setNoteList(color1_c_11,note_c_11));
        c_12.addAll(setNoteList(color1_c_12,note_c_12));
        c_13.addAll(setNoteList(color1_c_13,note_c_13));
        c_14.addAll(setNoteList(color1_c_14,note_c_14));
        c_15.addAll(setNoteList(color1_c_15,note_c_15));
        c_16.addAll(setNoteList(color1_c_16,note_c_16));
        c_17.addAll(setNoteList(color1_c_17,note_c_17));
        c_18.addAll(setNoteList(color1_c_18,note_c_18));
        c_19.addAll(setNoteList(color1_c_19,note_c_19));

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
            case Constant.COURSE_2441:
            case Constant.COURSE_2458:
            case Constant.COURSE_1:
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
            case Constant.COURSE_1020:
                if(Constant.PLAY_TOGHTER_FOLLOW_ONE.equals(tag)){
                    result = fengtai_1ist[0];
                }else{
                    result = fengtai_1ist[1];
                }
                break;

            case Constant.COURSE_1039:
                 result = fengtai_1ist[1];
                break;

            case Constant.COURSE_1339:
            case Constant.COURSE_1059:
            case Constant.COURSE_3_ft:
                result = fengtai_1ist[2];
                break;

            case Constant.COURSE_1360:
            case Constant.COURSE_1380:
            case Constant.COURSE_5:
                result = fengtai_1ist[3];
                break;

            case Constant.COURSE_1403:
            case Constant.COURSE_1422:
            case Constant.COURSE_6:
                result = fengtai_1ist[4];
                break;

            case Constant.COURSE_1443:
            case Constant.COURSE_1462:
            case Constant.COURSE_8:
                result = fengtai_1ist[5];
                break;

            case Constant.COURSE_1482:
            case Constant.COURSE_1499:
            case Constant.COURSE_9:
                result = fengtai_1ist[6];
                break;

            case Constant.COURSE_1518:
            case Constant.COURSE_1536:
            case Constant.COURSE_11:
                result = fengtai_1ist[7];
                break;

            case Constant.COURSE_1554:
            case Constant.COURSE_1572:
            case Constant.COURSE_12:
                result = fengtai_1ist[8];
                break;

            //c版本 c3
            case Constant.COURSE_2481:
            case Constant.COURSE_2497:
                result = c_1ist[1];
                break;

            //c4
            case Constant.COURSE_2520:
            case Constant.COURSE_2537:
                result = c_1ist[2];
                break;

            //c5
            case Constant.COURSE_2561:
            case Constant.COURSE_2578:
                result = c_1ist[3];
                break;

            //c6
            case Constant.COURSE_2601:
            case Constant.COURSE_2617:
                result = c_1ist[4];
                break;

            //c7
            case Constant.COURSE_2640:
            case Constant.COURSE_2658:
                result = c_1ist[5];
                break;

            //c8
            case Constant.COURSE_2682:
            case Constant.COURSE_2698:
                result = c_1ist[6];
                break;

            //c9
            case Constant.COURSE_2721:
            case Constant.COURSE_2737:
                result = c_1ist[7];
                break;

            //  C11 - C19
            //C11
            case Constant.COURSE_h18_3111:
            case Constant.COURSE_h18_3129:
                result = h18_1ist[0];
                break;
            //C12
            case Constant.COURSE_h18_3140:
            case Constant.COURSE_h18_3157:
                result = h18_1ist[1];
                break;
            //C13
            case Constant.COURSE_h18_3180:
            case Constant.COURSE_h18_3190:
                result = h18_1ist[2];
                break;
            //C14
            case Constant.COURSE_h18_3218:
            case Constant.COURSE_h18_3232:
                result = h18_1ist[3];
                break;
            //C15
            case Constant.COURSE_h18_3256:
            case Constant.COURSE_h18_3271:
                result = h18_1ist[4];
                break;
            //C16
            case Constant.COURSE_h18_3292:
            case Constant.COURSE_h18_3309:
                result = h18_1ist[5];
                break;
            //C17
            case Constant.COURSE_h18_3330:
            case Constant.COURSE_h18_3352:
                result = h18_1ist[6];
                break;
            //C18
            case Constant.COURSE_h18_3379:
            case Constant.COURSE_h18_3395:
                result = h18_1ist[7];
                break;
            //C19
            case Constant.COURSE_h18_3423:
            case Constant.COURSE_h18_3441:
                result = h18_1ist[8];
                break;

            //社培
            case Constant.COURSE_512_567:
                break;
            case Constant.COURSE_512_597:
                result = note_1;
                break;
            case Constant.COURSE_512_630:
                //c3
                if(tag.equals(Constant.PLAY_TOGHTER_FOLLOW_ONE)){
                    result = c_3;
                }else if(tag.equals(Constant.REVIEW_PLAY_FOLLOW_ONE)){
                    result = note_1;
                }
                break;
            case Constant.COURSE_512_804:
                if(tag.equals(Constant.PLAY_TOGHTER_FOLLOW_ONE)){
                    result = c_4;
                }else if(tag.equals(Constant.REVIEW_PLAY_FOLLOW_ONE)){
                    result = c_3;
                }
                break;
            case Constant.COURSE_512_844:
                if(tag.equals(Constant.PLAY_TOGHTER_FOLLOW_ONE)){
                    result = c_5;
                }else if(tag.equals(Constant.REVIEW_PLAY_FOLLOW_ONE)){
                    result = c_4;
                }
                break;
            case Constant.COURSE_512_1949:
                if(tag.equals(Constant.PLAY_TOGHTER_FOLLOW_ONE)){
                    result = c_6;
                }else if(tag.equals(Constant.REVIEW_PLAY_FOLLOW_ONE)){
                    result = c_5;
                }
                break;
            case Constant.COURSE_512_1987:
                if(tag.equals(Constant.PLAY_TOGHTER_FOLLOW_ONE)){
                    result = c_7;
                }else if(tag.equals(Constant.REVIEW_PLAY_FOLLOW_ONE)){
                    result = c_6;
                }
                break;
            case Constant.COURSE_512_2028:
                if(tag.equals(Constant.PLAY_TOGHTER_FOLLOW_ONE)){
                    result = c_8;
                }else if(tag.equals(Constant.REVIEW_PLAY_FOLLOW_ONE)){
                    result = c_7;
                }
                break;
            case Constant.COURSE_512_2066:
                if(tag.equals(Constant.PLAY_TOGHTER_FOLLOW_ONE)){
                    result = c_9;
                }else if(tag.equals(Constant.REVIEW_PLAY_FOLLOW_ONE)){
                    result = c_8;
                }
                break;
            case Constant.COURSE_512_2104:
                if(tag.equals(Constant.PLAY_TOGHTER_FOLLOW_ONE)){
                    result = c_11;
                }else if(tag.equals(Constant.REVIEW_PLAY_FOLLOW_ONE)){
                    result = c_11;
                }
                break;
            case Constant.COURSE_512_3831:
                if(tag.equals(Constant.PLAY_TOGHTER_FOLLOW_ONE)){
                    result = c_11;
                }else if(tag.equals(Constant.REVIEW_PLAY_FOLLOW_ONE)){
                    result = c_11;
                }
                break;
            case Constant.COURSE_512_3870:
                if(tag.equals(Constant.PLAY_TOGHTER_FOLLOW_ONE)){
                    result = c_13;
                }else if(tag.equals(Constant.REVIEW_PLAY_FOLLOW_ONE)){
                    result = c_11;
                }
                break;
            case Constant.COURSE_512_3907:
                if(tag.equals(Constant.PLAY_TOGHTER_FOLLOW_ONE)){
                    result = c_14;
                }else if(tag.equals(Constant.REVIEW_PLAY_FOLLOW_ONE)){
                    result = c_13;
                }
                break;
            case Constant.COURSE_512_3944:
                if(tag.equals(Constant.PLAY_TOGHTER_FOLLOW_ONE)){
                    result = c_15;
                }else if(tag.equals(Constant.REVIEW_PLAY_FOLLOW_ONE)){
                    result = c_14;
                }
                break;
            case Constant.COURSE_512_3979:
                if(tag.equals(Constant.PLAY_TOGHTER_FOLLOW_ONE)){
                    result = c_16;
                }else if(tag.equals(Constant.REVIEW_PLAY_FOLLOW_ONE)){
                    result = c_15;
                }
                break;
            case Constant.COURSE_512_4016:
                if(tag.equals(Constant.PLAY_TOGHTER_FOLLOW_ONE)){
                    result = c_17;
                }else if(tag.equals(Constant.REVIEW_PLAY_FOLLOW_ONE)){
                    result = c_16;
                }
                break;
            case Constant.COURSE_512_4064:
                if(tag.equals(Constant.PLAY_TOGHTER_FOLLOW_ONE)){
                    result = c_18;
                }else if(tag.equals(Constant.REVIEW_PLAY_FOLLOW_ONE)){
                    result = c_17;
                }
                break;
            case Constant.COURSE_512_4107:
                if(tag.equals(Constant.PLAY_TOGHTER_FOLLOW_ONE)){
                    result = c_19;
                }else if(tag.equals(Constant.REVIEW_PLAY_FOLLOW_ONE)){
                    result = c_18;
                }
                break;

        }

        return result;

    }

    //匹配课程id c11 - c19
    public static boolean filterCourse(int course_id, String course){
        boolean result = true;

        if(course.equals("c11") && (course_id == Constant.COURSE_h18_3111 || course_id == Constant.COURSE_h18_3129
                || course_id == Constant.COURSE_h18_3480 || course_id == Constant.COURSE_h18_3498)){
            //后18 c11

        }else if(course.equals("c12") && (course_id == Constant.COURSE_h18_3140 || course_id == Constant.COURSE_h18_3157
                || course_id == Constant.COURSE_h18_3509 || course_id == Constant.COURSE_h18_3526)){
            //后18 c12

        }else if(course.equals("c13") && (course_id == Constant.COURSE_h18_3180 || course_id == Constant.COURSE_h18_3190
                || course_id == Constant.COURSE_h18_3549 || course_id == Constant.COURSE_h18_3565)){
            //后18 c13

        }else if(course.equals("c14") && (course_id == Constant.COURSE_h18_3218 || course_id == Constant.COURSE_h18_3232
                || course_id == Constant.COURSE_h18_3587 || course_id == Constant.COURSE_h18_3601)){
            //后18 c14

        }else if(course.equals("c15") && (course_id == Constant.COURSE_h18_3256 || course_id == Constant.COURSE_h18_3271
                || course_id == Constant.COURSE_h18_3625 || course_id == Constant.COURSE_h18_3640)){
            //后18 c15

        }else if(course.equals("c16") && (course_id == Constant.COURSE_h18_3292 || course_id == Constant.COURSE_h18_3309
                || course_id == Constant.COURSE_h18_3661 || course_id == Constant.COURSE_h18_3678)){
            //后18 c16

        }else if(course.equals("c17") && (course_id == Constant.COURSE_h18_3330 || course_id == Constant.COURSE_h18_3352
                || course_id == Constant.COURSE_h18_3699 || course_id == Constant.COURSE_h18_3721)){
            //后18 c17

        }else if(course.equals("c18") && (course_id == Constant.COURSE_h18_3379 || course_id == Constant.COURSE_h18_3395
                || course_id == Constant.COURSE_h18_3748 || course_id == Constant.COURSE_h18_3764)){
            //后18 c18

        }else if(course.equals("c19") && (course_id == Constant.COURSE_h18_3423 || course_id == Constant.COURSE_h18_3441
                || course_id == Constant.COURSE_h18_3792 || course_id == Constant.COURSE_h18_3810)){
            //后18 c19

        }else if(course.equals("c3") && (course_id == Constant.COURSE_2481 || course_id == Constant.COURSE_2497)){

        }else if(course.equals("c4") && (course_id == Constant.COURSE_2520 || course_id == Constant.COURSE_2537)){

        }else if(course.equals("c5") && (course_id == Constant.COURSE_2561 || course_id == Constant.COURSE_2578)){

        }else if(course.equals("c6") && (course_id == Constant.COURSE_2601 || course_id == Constant.COURSE_2617)){

        }else if(course.equals("c7") && (course_id == Constant.COURSE_2640 || course_id == Constant.COURSE_2658)){

        }else if(course.equals("c8") && (course_id == Constant.COURSE_2682 || course_id == Constant.COURSE_2698)){

        }else if(course.equals("c9") && (course_id == Constant.COURSE_2721  || course_id == Constant.COURSE_2737)){

        }else{
            result = false;
        }

        return result;
    }

    /**
     * 社培id
     *
     *  sp1
     */
    public static boolean filterSP(int course_id, String course) {
        boolean result = true;

        if (course.equals("sp_1") && course_id == Constant.COURSE_512_567) {

        }else if(course.equals("sp_2") && course_id == Constant.COURSE_512_597){

        }else if(course.equals("sp_3") && course_id == Constant.COURSE_512_630){

        }else if(course.equals("sp_4") && course_id == Constant.COURSE_512_804){

        }else if(course.equals("sp_5") && course_id == Constant.COURSE_512_844){

        }else if(course.equals("sp_6") && course_id == Constant.COURSE_512_1949){

        }else if(course.equals("sp_7") && course_id == Constant.COURSE_512_1987){

        }else if(course.equals("sp_8") && course_id == Constant.COURSE_512_2028){

        }else if(course.equals("sp_9") && course_id == Constant.COURSE_512_2066){

        }else if(course.equals("sp_10") && course_id == Constant.COURSE_512_2104){

        }else if(course.equals("sp_11") && course_id == Constant.COURSE_512_3831){

        }else if(course.equals("sp_12") && course_id == Constant.COURSE_512_3870){

        }else if(course.equals("sp_13") && course_id == Constant.COURSE_512_3907){

        }else if(course.equals("sp_14") && course_id == Constant.COURSE_512_3944){

        }else if(course.equals("sp_15") && course_id == Constant.COURSE_512_3979){

        }else if(course.equals("sp_16") && course_id == Constant.COURSE_512_4016){

        }else if(course.equals("sp_17") && course_id == Constant.COURSE_512_4064){

        }else if(course.equals("sp_18") && course_id == Constant.COURSE_512_4107){

        }else{
            result = false;
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
     * 生成 跟奏 需要的乐谱
     * @param color
     * @param note
     * @return
     */
    public static ArrayList<NoteInfo> setNoteList(int[] color, int[] note){

        ArrayList<NoteInfo> al = new ArrayList<NoteInfo>();
        for(int i=0,m=0; i<color.length;i++){

            if(note[i]==-1){
                continue;
            }

            NoteInfo ni = new NoteInfo();
            ni.setNoteNum(note[i]);
            ni.setNoteIndex(m);
            ni.setKeyIndex(getKeyIndex(note[i]));
            ni.setRed(color[i]==1?true:false);
            al.add(ni);

            m++;
        }

        return al;
    }

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
                    beat(icontext, iindex[n], true,(long) (idur[n]*1000)/2);// 0 0 4 4 
                }else if(icolor[n]==0){
                    beat(icontext, iindex[n], false,(long) (idur[n]*1000)/2);
                }

                Thread.sleep((long)(idur[n]*1000));

            }else{

                Thread.sleep((long)(idur[n]*1000));

            }
        }
    }





}





