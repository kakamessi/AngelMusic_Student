package com.angelmusic.student.infobean;

import java.util.List;

/**
 * Created by fei on 2017/1/9.
 * 座位相关信息
 */

public class SeatDataInfo {

    /**
     * schoolID : 1
     * schoolName :
     * roomName : 测试教室
     * roomID :
     * classID :
     * lineNo : 3
     * columnNo : 3
     * seatNo : 1
     * seatList : [{"seatIndexDescription":"1","state":"1"},{"seatIndexDescription":"2","state":"1"},{"seatIndexDescription":"3","state":"1"},{"seatIndexDescription":"4","state":"1"},{"seatIndexDescription":"无","state":"2"},{"seatIndexDescription":"5","state":"1"},{"seatIndexDescription":"6","state":"1"},{"seatIndexDescription":"7","state":"3"},{"seatIndexDescription":"8","state":"1"}]
     */

    private String schoolID;
    private String schoolName;
    private String roomName;
    private String roomID;
    private String classID;
    private int lineNo;
    private int columnNo;
    private String seatNo;
    private List<SeatListBean> seatList;

    public String getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(String schoolID) {
        this.schoolID = schoolID;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public int getLineNo() {
        return lineNo;
    }

    public void setLineNo(int lineNo) {
        this.lineNo = lineNo;
    }

    public int getColumnNo() {
        return columnNo;
    }

    public void setColumnNo(int columnNo) {
        this.columnNo = columnNo;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public List<SeatListBean> getSeatList() {
        return seatList;
    }

    public void setSeatList(List<SeatListBean> seatList) {
        this.seatList = seatList;
    }

    public static class SeatListBean {
        /**
         * seatIndexDescription : 1
         * state : 1
         */

        private String seatIndexDescription;
        private String state;

        public String getSeatIndexDescription() {
            return seatIndexDescription;
        }

        public void setSeatIndexDescription(String seatIndexDescription) {
            this.seatIndexDescription = seatIndexDescription;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        @Override
        public String toString() {
            return "SeatListBean{" +
                    "seatIndexDescription='" + seatIndexDescription + '\'' +
                    ", state='" + state + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SeatDataInfo{" +
                "schoolID='" + schoolID + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", roomName='" + roomName + '\'' +
                ", roomID='" + roomID + '\'' +
                ", classID='" + classID + '\'' +
                ", lineNo=" + lineNo +
                ", columnNo=" + columnNo +
                ", seatNo='" + seatNo + '\'' +
                ", seatList=" + seatList +
                '}';
    }
}
