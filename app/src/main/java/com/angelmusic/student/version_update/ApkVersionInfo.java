package com.angelmusic.student.version_update;

import java.io.Serializable;

/**
 * Created by fei on 2017/1/3.
 * 版本信息封装类
 */

public class ApkVersionInfo implements Serializable {

    /**
     * code : 200
     * description : 请求成功
     * detail : {"code":"1.0.1","createDate":"2017-01-10 14:36:29","id":1,"info":"hhaa","isforced":1,"remark":"","state":1,"type":1,"url":"/a/1.apk"}
     */

    private int code;
    private String description;
    private DetailBean detail;

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

    public DetailBean getDetail() {
        return detail;
    }

    public void setDetail(DetailBean detail) {
        this.detail = detail;
    }

    public static class DetailBean {
        /**
         * code : 1.0.1
         * createDate : 2017-01-10 14:36:29
         * id : 1
         * info : hhaa
         * isforced : 1
         * remark :
         * state : 1
         * type : 1
         * url : /a/1.apk
         */

        private String code;
        private String createDate;
        private int id;
        private String info;
        private int isforced;
        private String remark;
        private int state;
        private int type;
        private String url;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public int getIsforced() {
            return isforced;
        }

        public void setIsforced(int isforced) {
            this.isforced = isforced;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
