package com.angelmusic.student.course_download.infobean;

import java.util.List;

/**
 * Created by DELL on 2017/5/17.
 */

public class QukuDetail {

    private int code;
    private String description;
    private List<DetailBean> detail;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DetailBean> getDetail() {
        return detail;
    }

    public void setDetail(List<DetailBean> detail) {
        this.detail = detail;
    }
}
