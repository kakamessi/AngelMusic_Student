package com.angelmusic.student.course_download.infobean;

import java.util.Map;

/**
 * Created by DELL on 2017/5/11.
 */

public class CourseItemInfo {

    //课程id
    private String course_id;
    //列表索引
    private int item_index;
    //已下载
    private int done_num;
    //全部数量
    private int all_num;
    /**
     * 下载状态
     * 默认 1准备下 2下载中 3暂停 4（下载完）删除
     */
    /
    private int isActive;
    //下载路径
    private Map<String,String> resUrl;


    //进度
    private float process;


    public CourseItemInfo(String course_id, int item_index, int done_num, int all_num, int isActive, Map<String, String> resUrl, float process) {
        this.course_id = course_id;
        this.item_index = item_index;
        this.done_num = done_num;
        this.all_num = all_num;
        this.isActive = isActive;
        this.resUrl = resUrl;
        this.process = process;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public int getItem_index() {
        return item_index;
    }

    public void setItem_index(int item_index) {
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

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public Map<String, String> getResUrl() {
        return resUrl;
    }

    public void setResUrl(Map<String, String> resUrl) {
        this.resUrl = resUrl;
    }

    public float getProcess() {
        return process;
    }

    public void setProcess(float process) {
        this.process = process;
    }
}
