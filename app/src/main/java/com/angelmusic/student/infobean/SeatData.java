package com.angelmusic.student.infobean;

/**
 * Created by fei on 2017/1/9.
 * 座位相关信息
 */

public class SeatData {
    private String classroomName;//教室名称
    private String classroomId;//教室id
    private String classId;//班级id
    private String seatId;//座位号
    private String rowNum;//班级座位行数
    private String columnNum;//班级座位列数

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public String getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(String classroomId) {
        this.classroomId = classroomId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public String getRowNum() {
        return rowNum;
    }

    public void setRowNum(String rowNum) {
        this.rowNum = rowNum;
    }

    public String getColumnNum() {
        return columnNum;
    }

    public void setColumnNum(String columnNum) {
        this.columnNum = columnNum;
    }
}
