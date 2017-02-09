package com.angelmusic.stu.u3ddownload.infobean;

/**
 * Created by fei on 2017/1/13.
 * 文件信息类
 */

public class FileInfo {
    private String CourseName;//第几节课
    private String fileName;//文件名
    private String fileUrl;//下载路径
    private String fileParentPath;//文件的存放路径
    private int downloadState;//0:未下载完，1:下载完毕
    private int quoteCount;//当前文件被几个课程使用,不是1的时候不能删除

    public FileInfo(String courseName, String fileName, String fileUrl, String fileParentPath, int downloadState, int quoteCount) {
        CourseName = courseName;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileParentPath = fileParentPath;
        this.downloadState = downloadState;
        this.quoteCount = quoteCount;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileParentPath() {
        return fileParentPath;
    }

    public void setFileParentPath(String fileParentPath) {
        this.fileParentPath = fileParentPath;
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
