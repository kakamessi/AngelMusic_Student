package com.angelmusic.stu.u3ddownload.infobean;

/**
 * Created by fei on 2017/2/9.
 */

public class SendDataInfo {
    /**
     * [{"course":"第一节课","progress":"30"},{"course":"第二节课","progress":"40"}]
     */
    private String course;
    private String progress;

    public SendDataInfo(String course, String progress) {
        this.course = course;
        this.progress = progress;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }
}
