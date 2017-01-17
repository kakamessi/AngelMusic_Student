package com.angelmusic.student.batch_download.infobean;

/**
 * Created by fei on 2017/1/13.
 * 文件信息类
 */

public class FileInfo {
    private String fileName;
    private String fileAbsPath;
    private String fileUrl;
    private String courseName;
    private int downloadState;//0:未下载完，1:下载完毕
    private int quoteCount;//当前文件被几个课程使用

    public FileInfo(String fileName, String fileAbsPath, String fileUrl, String courseName, int downloadState, int quoteCount) {
        this.fileName = fileName;
        this.fileAbsPath = fileAbsPath;
        this.fileUrl = fileUrl;
        this.courseName = courseName;
        this.downloadState = downloadState;
        this.quoteCount = quoteCount;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileAbsPath() {
        return fileAbsPath;
    }

    public void setFileAbsPath(String fileAbsPath) {
        this.fileAbsPath = fileAbsPath;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getDownloadState() {
        return downloadState;
    }

    public void setDownloadState(int downloadState) {
        this.downloadState = downloadState;
    }

    public int getQuoteCount() {
        return quoteCount;
    }

    public void setQuoteCount(int quoteCount) {
        this.quoteCount = quoteCount;
    }
}
