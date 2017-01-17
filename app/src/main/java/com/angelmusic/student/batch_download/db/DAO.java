package com.angelmusic.student.batch_download.db;

import com.angelmusic.student.batch_download.infobean.FileInfo;

/**
 * Created by fei on 2017/1/16.
 * 数据库操作类接口
 */

public interface DAO {
    public void insertFile(FileInfo fileInfo);

    public void deleteFile(String fileName);

    public void updateDownloadState(String fileName, String downloadState);

    public void updateQuoteCount(String fileName, int quoteCount);

    public int queryQuoteCount(String fileName);

    public String queryFilePath(String fileName);

    public boolean queryDownloadState(String fileName);
}
