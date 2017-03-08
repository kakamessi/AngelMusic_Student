package com.angelmusic.student.utils;

import com.angelmusic.student.infobean.ScoreData;

import java.util.ArrayList;

/**
 * Created by DELL on 2017/3/8.
 */

public class MusicUtils {

    public static ScoreData getScore(ArrayList<String> noteIndex,ArrayList<String> correctIndexs){

        ScoreData sd = new ScoreData();

        sd.setScore(3);
        sd.setJiezouScore(1);
        sd.setShizhiScore(1);
        sd.setYingaoScore(1);

        return sd;

    }

}
