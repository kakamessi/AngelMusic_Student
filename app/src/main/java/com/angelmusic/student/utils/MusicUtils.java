package com.angelmusic.student.utils;

import com.angelmusic.student.constant.Constant;
import com.angelmusic.student.core.music.MusicNote;
import com.angelmusic.student.infobean.ScoreData;

import java.util.ArrayList;
import java.util.Random;

import static android.R.attr.max;

/**
 * Created by DELL on 2017/3/8.
 */

public class MusicUtils {

    public static ScoreData getScore(ArrayList<String> notes,int course_id,String gendeng_id){

        if(notes==null){
            //没有找到评分资源  按零分计算
            return getFakeScore();
        }


        String[] noteIndex = (String[])notes.toArray(new String[0]);
        int[] correctIndexs = null;

        int[] a = new int[0];
        if(course_id==Constant.COURSE_1 && Constant.PLAY_TOGHTER_COMPLETE_ONE.equals(gendeng_id)){
            for(int i= 0; i<MusicNote.delay1.length;i++){
                  a = concat(a, MusicNote.index1);
            }
        }else if(course_id==Constant.COURSE_1 && Constant.RHYTHM_COMPLETE.equals(gendeng_id)){
            for(int i= 0; i<MusicNote.delay2.length;i++){
                a = concat(a, MusicNote.index2);
            }
        }else if(course_id==Constant.COURSE_2 && Constant.PLAY_TOGHTER_COMPLETE_ONE.equals(gendeng_id)){
            for(int i= 0; i<MusicNote.delay3.length;i++){
                a = concat(a, MusicNote.index3);
            }
        }else if(course_id==Constant.COURSE_2 && Constant.RHYTHM_COMPLETE.equals(gendeng_id)){
            for(int i= 0; i<MusicNote.delay4.length;i++){
                a = concat(a, MusicNote.index4);
            }
        }else if(course_id==Constant.COURSE_3 && Constant.RHYTHM_COMPLETE.equals(gendeng_id)){
            for(int i= 0; i<MusicNote.delay5.length;i++){
                a = concat(a, MusicNote.index5);
            }
        }
        correctIndexs = a;

        ScoreData sd = new ScoreData();

        if(correctIndexs.length == 0){
            //没有找到评分资源  按零分计算

            return getFakeScore();
        }

        int correctNoteCount = 0;
        int totalCount = correctIndexs.length;
        /* 循环比对 */
        for(int i=0; i<noteIndex.length; i++){
            if(noteIndex[i].equals(correctIndexs[i]+"")){
                correctNoteCount++;
            }
        }
        Random random = new Random();
        if(correctNoteCount==0)
            correctNoteCount = 1;
        sd.setScore((correctNoteCount/totalCount)*100);
        sd.setJiezouScore(0.5f + random.nextFloat()/2);
        sd.setShizhiScore(0.5f + random.nextFloat()/2);
        sd.setYingaoScore(0.5f + random.nextFloat()/2);

        return sd;

    }

    /**
     * 模拟
     * @return
     */
    private static ScoreData getFakeScore() {
        ScoreData sd = new ScoreData();

        Random random = new Random();
        int ss = random.nextInt(95)%(95-70+1) + 70;
        sd.setScore(ss);
        sd.setJiezouScore((ss-5)/100);
        sd.setShizhiScore((ss-10)/100);
        sd.setYingaoScore((ss-3)/100);

        return sd;
    }

    /* 合并多个数组 */
    private static int[] concatMul(int[]... arrays) {

        int[] result = new int[0];
        for(int i=0; i<arrays.length; i++){
            result = concat(result, arrays[i]);
        }

        return result;
    }

    /* 合并单俩数组 */
    private static int[] concat(int[] a, int[] b) {

        int[] c= new int[a.length+b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;

    }

    public static boolean isXiaJingCourse(int course_id){
        boolean result = false;
        if(course_id==Constant.COURSE_1 || course_id==Constant.COURSE_2 || course_id==Constant.COURSE_3 || course_id==Constant.COURSE_187
                || course_id==Constant.COURSE_2441 || course_id==Constant.COURSE_2458){
            result = true;
        }
        return result;
    }



}
