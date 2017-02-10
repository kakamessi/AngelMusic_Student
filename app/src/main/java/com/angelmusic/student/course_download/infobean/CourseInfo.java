package com.angelmusic.student.course_download.infobean;

import java.util.List;

/**
 * Created by fei on 2017/1/22.
 */

public class CourseInfo {
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

    public static class DetailBean {
        private int course_id;
        private int part_sort;
        private String path;
        private String name;
        private int pid;
        private int id;
        private int has_source;
        private int state;
        private int type;
        private List<SonPartBeanX> sonPart;

        public int getCourse_id() {
            return course_id;
        }

        public void setCourse_id(int course_id) {
            this.course_id = course_id;
        }

        public int getPart_sort() {
            return part_sort;
        }

        public void setPart_sort(int part_sort) {
            this.part_sort = part_sort;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getHas_source() {
            return has_source;
        }

        public void setHas_source(int has_source) {
            this.has_source = has_source;
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

        public List<SonPartBeanX> getSonPart() {
            return sonPart;
        }

        public void setSonPart(List<SonPartBeanX> sonPart) {
            this.sonPart = sonPart;
        }

        public static class SonPartBeanX {
            private int course_id;
            private int part_sort;
            private int pid;
            private int has_source;
            private int type;
            private String path;
            private String png_uploadPath;
            private String name;
            private int id;
            private int state;
            private String xml_uploadPath;
            private String video_uploadPath;
            private List<SonPartBeanX> sonPart;

            public int getCourse_id() {
                return course_id;
            }

            public void setCourse_id(int course_id) {
                this.course_id = course_id;
            }

            public int getPart_sort() {
                return part_sort;
            }

            public void setPart_sort(int part_sort) {
                this.part_sort = part_sort;
            }

            public int getPid() {
                return pid;
            }

            public void setPid(int pid) {
                this.pid = pid;
            }

            public int getHas_source() {
                return has_source;
            }

            public void setHas_source(int has_source) {
                this.has_source = has_source;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getPath() {
                return path;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getPng_uploadPath() {
                return png_uploadPath;
            }

            public void setPng_uploadPath(String png_uploadPath) {
                this.png_uploadPath = png_uploadPath;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public String getXml_uploadPath() {
                return xml_uploadPath;
            }

            public void setXml_uploadPath(String xml_uploadPath) {
                this.xml_uploadPath = xml_uploadPath;
            }

            public String getVideo_uploadPath() {
                return video_uploadPath;
            }

            public void setVideo_uploadPath(String video_uploadPath) {
                this.video_uploadPath = video_uploadPath;
            }

            public List<SonPartBeanX> getSonPart() {
                return sonPart;
            }

            public void setSonPart(List<SonPartBeanX> sonPart) {
                this.sonPart = sonPart;
            }

        }
    }
}
