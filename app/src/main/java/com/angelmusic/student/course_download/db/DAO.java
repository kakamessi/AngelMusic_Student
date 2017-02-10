package com.angelmusic.student.course_download.db;

/**
 * Created by fei on 2017/1/16.
 * 数据库操作类接口
 */

public interface DAO {
     void insertFile(String fileName, String courseName, String downloadState);

     void deleteFile(String fileName, String courseName);

     void updateDownloadState(String fileName, String downloadState);

     boolean isFileNameExist(String fileName);

     boolean isCourseNameExist(String courseName);

     boolean isCourseNameExist(String fileName, String courseName);

     boolean isDownloadOk(String fileName);

     boolean isDownloadOk(String fileName, String courseName);

     boolean isCanDeleteLocalFile(String fileName);

}
