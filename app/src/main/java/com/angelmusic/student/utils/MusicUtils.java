package com.angelmusic.student.utils;

import com.angelmusic.student.constant.Constant;
import com.angelmusic.student.core.music.MusicNote;
import com.angelmusic.student.infobean.ScoreData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by DELL on 2017/3/8.
 */

public class MusicUtils {

    public static ScoreData getScore(ArrayList<String> noteIndex,String course_id,String gendeng_id){

        List<Integer> correctIndexs = null;
        Integer[] a = new Integer[0];

        if(course_id.equals("1") && Constant.PLAY_TOGHTER_COMPLETE_ONE.equals(gendeng_id)){
            for(int i= 0; i<MusicNote.delay1.length;i++){
                  a = concat(a, MusicNote.index1);
            }
        }else if(course_id.equals("1") && Constant.RHYTHM_COMPLETE.equals(gendeng_id)){
            for(int i= 0; i<MusicNote.delay2.length;i++){
                a = concat(a, MusicNote.index2);
            }
        }else if(course_id.equals("2") && Constant.PLAY_TOGHTER_COMPLETE_ONE.equals(gendeng_id)){
            for(int i= 0; i<MusicNote.delay3.length;i++){
                a = concat(a, MusicNote.index3);
            }
        }else if(course_id.equals("2") && Constant.RHYTHM_COMPLETE.equals(gendeng_id)){
            for(int i= 0; i<MusicNote.delay4.length;i++){
                a = concat(a, MusicNote.index4);
            }
        }else if(course_id.equals("3") && Constant.RHYTHM_COMPLETE.equals(gendeng_id)){
            for(int i= 0; i<MusicNote.delay5.length;i++){
                a = concat(a, MusicNote.index5);
            }
        }
        correctIndexs = Arrays.asList(a);

        int correctNoteCount = 0;
        int totalCount = correctIndexs.size();
        ScoreData sd = new ScoreData();

        /* 循环比对 */
        for(int i=0; i<noteIndex.size(); i++){
            if(noteIndex.get(i).equals(correctIndexs.get(i)+"")){
                correctNoteCount++;
            }
        }

        Random random = new Random();
        sd.setScore((correctNoteCount/totalCount)*100);
        sd.setJiezouScore(0.5f + random.nextFloat()/2);
        sd.setShizhiScore(0.5f + random.nextFloat()/2);
        sd.setYingaoScore(0.5f + random.nextFloat()/2);

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

}
