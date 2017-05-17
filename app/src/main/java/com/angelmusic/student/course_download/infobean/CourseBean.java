package com.angelmusic.student.course_download.infobean;

import java.util.List;

/**
 * Created by DELL on 2017/5/17.
 */

public class CourseBean {

    private String course_id;
    private String name;
    private List<PathBean> pathList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public List<PathBean> getPathList() {
        return pathList;
    }

    public void setPathList(List<PathBean> pathList) {
        this.pathList = pathList;
    }
}
