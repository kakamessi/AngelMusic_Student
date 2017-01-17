package com.angelmusic.student.login;

/**
 * Created by fei on 2017/1/17.
 */

public class StuInfo {

    /**
     * code : 200
     * description : 请求成功
     * detail : {"stuInfo":{"address":null,"age":1,"classId":1,"entrancetime":"2017-01-07 16:23:33","gender":"1","id":1,"name":"1","phone":"1","schoolId":1,"state":1,"studentNo":"1"},"IP":"192.168.2.104"}
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
         * stuInfo : {"address":null,"age":1,"classId":1,"entrancetime":"2017-01-07 16:23:33","gender":"1","id":1,"name":"1","phone":"1","schoolId":1,"state":1,"studentNo":"1"}
         * IP : 192.168.2.104
         */

        private StuInfoBean stuInfo;
        private String IP;

        public StuInfoBean getStuInfo() {
            return stuInfo;
        }

        public void setStuInfo(StuInfoBean stuInfo) {
            this.stuInfo = stuInfo;
        }

        public String getIP() {
            return IP;
        }

        public void setIP(String IP) {
            this.IP = IP;
        }

        public static class StuInfoBean {
            /**
             * address : null
             * age : 1
             * classId : 1
             * entrancetime : 2017-01-07 16:23:33
             * gender : 1
             * id : 1
             * name : 1
             * phone : 1
             * schoolId : 1
             * state : 1
             * studentNo : 1
             */

            private Object address;
            private int age;
            private int classId;
            private String entrancetime;
            private String gender;
            private int id;
            private String name;
            private String phone;
            private int schoolId;
            private int state;
            private String studentNo;

            public Object getAddress() {
                return address;
            }

            public void setAddress(Object address) {
                this.address = address;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }

            public int getClassId() {
                return classId;
            }

            public void setClassId(int classId) {
                this.classId = classId;
            }

            public String getEntrancetime() {
                return entrancetime;
            }

            public void setEntrancetime(String entrancetime) {
                this.entrancetime = entrancetime;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public int getSchoolId() {
                return schoolId;
            }

            public void setSchoolId(int schoolId) {
                this.schoolId = schoolId;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public String getStudentNo() {
                return studentNo;
            }

            public void setStudentNo(String studentNo) {
                this.studentNo = studentNo;
            }
        }
    }
}
