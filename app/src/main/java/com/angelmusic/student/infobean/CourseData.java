package com.angelmusic.student.infobean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DELL on 2017/1/18.
 */

public class CourseData {

    private Map<String, String> files = new HashMap<>();

    public Map<String, String> getFiles() {
        return files;
    }

    public void setFiles(Map<String, String> files) {
        this.files = files;
    }

}
