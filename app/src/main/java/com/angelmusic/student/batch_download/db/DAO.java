package com.angelmusic.student.batch_download.db;

/**
 * Created by fei on 2017/1/16.
 * 数据库操作类接口
 */

public interface DAO {
    public void insertFile(String fileName, String courseName, int downloadState);

    public void deleteFile(String fileName);

    public void deleteFile(String fileName, String courseName);

    public void updateDownloadState(String fileName, int downloadState);

    public boolean isFileNameExist(String fileName);

    public boolean isCourseNameExist(String courseName);

    public boolean isCourseNameExist(String fileName, String courseName);

    public boolean isDownloadOk(String fileName);

    public boolean isDownloadOk(String fileName, String courseName);

    public boolean isCanDeleteLocalFile(String fileName);

}
