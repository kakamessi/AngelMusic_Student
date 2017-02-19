package com.angelmusic.student.infobean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DELL on 2017/1/18.
 */

public class CourseData {

    private Map<String, String> files = new HashMap<>();

    /* 当前上课课程id */
    private String Course_Id = "";

    public Map<String, String> getFiles() {
        return files;
    }

    public void setFiles(Map<String, String> files) {
        this.files = files;
    }

    public String getCourse_Id() {
        return Course_Id;
    }

    public void setCourse_Id(String course_Id) {
        Course_Id = course_Id;
    }
}
