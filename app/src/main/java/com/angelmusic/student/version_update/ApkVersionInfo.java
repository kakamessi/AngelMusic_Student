package com.angelmusic.student.version_update;

import java.io.Serializable;

/**
 * Created by fei on 2017/1/3.
 * 版本信息封装类
 */

public class ApkVersionInfo implements Serializable {

    private DetailBean detail;
    private String description;
    private String code;

    public DetailBean getDetail() {
        return detail;
    }

    public void setDetail(DetailBean detail) {
        this.detail = detail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class DetailBean {
        private int id;
        private String create_date;
        private String remark;
        private String code;
        private String type;
        private int coursetype;
        private String isForced;
        private String url;
        private String info;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getCoursetype() {
            return coursetype;
        }

        public void setCoursetype(int coursetype) {
            this.coursetype = coursetype;
        }

        public String getIsForced() {
            return isForced;
        }

        public void setIsForced(String isForced) {
            this.isForced = isForced;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }
    }
}
