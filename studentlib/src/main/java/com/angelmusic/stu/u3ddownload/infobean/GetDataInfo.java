package com.angelmusic.stu.u3ddownload.infobean;

import java.util.List;

/**
 * Created by fei on 2017/2/9.
 */

public class GetDataInfo {

    private String course_name;
    private List<String> urls;

    public String getCourse_name() {
        return course_name;
    }

    public void setCourseName(String course_name) {
        this.course_name = course_name;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
