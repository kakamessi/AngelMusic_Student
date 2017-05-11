package com.angelmusic.student.course_download.infobean;

import java.util.Map;

/**
 * Created by DELL on 2017/5/11.
 */

public class CourseItemInfo {

    //课程id
    private String course_id;
    //列表索引
    private String item_index;
    //已下载
    private int done_num;
    //全部数量
    private int all_num;
    //下载状态
    private boolean isActive;
    //下载路径
    private Map<String,String> resUrl;


    //进度
    private float process;



    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getItem_index() {
        return item_index;
    }

    public void setItem_index(String item_index) {
        this.item_index = item_index;
    }

    public int getDone_num() {
        return done_num;
    }

    public void setDone_num(int done_num) {
        this.done_num = done_num;
    }

    public int getAll_num() {
        return all_num;
    }

    public void setAll_num(int all_num) {
        this.all_num = all_num;
    }

    public float getProcess() {

        if(all_num!=0)
            return done_num/all_num;
        return 0;
    }

    public void setProcess(float process) {
        this.process = process;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Map<String, String> getResUrl() {
        return resUrl;
    }

    public void setResUrl(Map<String, String> resUrl) {
        this.resUrl = resUrl;
    }
}
