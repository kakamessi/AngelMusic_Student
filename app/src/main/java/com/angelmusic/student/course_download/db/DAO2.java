package com.angelmusic.student.course_download.db;

/**
 * Created by fei on 2017/1/22.
 * 表tb_state的操作接口
 */

public interface DAO2 {
    void insertFile(String courseName, String loadingState);

    void deleteFile(String courseName);

    void updateLoadingState(String courseName, String loadingState);

    boolean queryIsLoading(String courseName);

    boolean queryIsExist(String courseName);
}
