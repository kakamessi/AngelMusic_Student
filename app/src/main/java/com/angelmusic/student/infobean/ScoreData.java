package com.angelmusic.student.infobean;

/**
 * Created by DELL on 2017/3/8.
 */

public class ScoreData {

    /* 最高一百分 */
    private int score = 100;
    private float yingaoScore = 1;
    private float jiezouScore = 1;
    private float shizhiScore = 1;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public float getYingaoScore() {
        return yingaoScore;
    }

    public void setYingaoScore(float yingaoScore) {
        this.yingaoScore = yingaoScore;
    }

    public float getJiezouScore() {
        return jiezouScore;
    }

    public void setJiezouScore(float jiezouScore) {
        this.jiezouScore = jiezouScore;
    }

    public float getShizhiScore() {
        return shizhiScore;
    }

    public void setShizhiScore(float shizhiScore) {
        this.shizhiScore = shizhiScore;
    }
}
