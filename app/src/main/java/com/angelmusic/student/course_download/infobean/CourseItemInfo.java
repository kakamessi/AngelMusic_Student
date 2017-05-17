package com.angelmusic.student.course_download.infobean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DELL on 2017/5/11.
 */

public class CourseItemInfo {

    //课程id
    private String course_id;
    //课程名称
    private String course_name;
    //列表索引
    private int item_index;
    //已下载
    private float done_num = 0;
    //全部数量
    private float all_num;
    /**
     * 下载状态
     * 默认 1准备下 2下载中 3暂停 4（下载完）删除
     */
    private int isActive;
    //下载路径
    private Map<String,String> resUrl = new HashMap<>();


    //进度
    private float process;

    public CourseItemInfo(){

    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
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

    public float getDone_num() {
        return done_num;
    }

    public void setDone_num(float done_num) {
        this.done_num = done_num;
    }

    public float getAll_num() {
        return all_num;
    }

    public void setAll_num(float all_num) {
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
        return done_num/all_num;
    }

    public void setProcess(float process) {
        this.process = process;
    }
}
