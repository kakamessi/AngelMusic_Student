package com.angelmusic.student.batch_download.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.angelmusic.student.batch_download.infobean.FileInfo;

/**
 * Created by fei on 2017/1/16.
 */

public class DAOImpl implements DAO {
    private DBHelper dbHelper;
    private static DAOImpl mThreadDAOImpl;

    public DAOImpl(Context context) {
        dbHelper = new DBHelper(context);
    }

    /**
     * 获取单例（线程安全）
     *
     * @param mContext 上下文对象
     */
    public synchronized static DAOImpl getInstance(Context mContext) {
        if (null == mThreadDAOImpl) {
            synchronized (DAOImpl.class) {
                if (null == mThreadDAOImpl) {
                    mThreadDAOImpl = new DAOImpl(mContext);
                }
            }
        }
        return mThreadDAOImpl;
    }

    /**
     * 插入一个文件的下载信息
     */
    @Override
    public synchronized void insertFile(FileInfo fileInfo) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.execSQL("insert into tb_course(file_name,file_path,file_url,course_name,download_state,quote_count) values(?,?,?,?,?,?)",
                new Object[]{fileInfo.getFileName(), fileInfo.getFileAbsPath(), fileInfo.getFileUrl(), fileInfo.getCourseName(), fileInfo
                        .getDownloadState(), fileInfo.getQuoteCount()});
        db.close();
    }

    /**
     * 删除指定文件名的信息
     */
    @Override
    public synchronized void deleteFile(String fileName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.execSQL("delete from tb_course where file_name = ?",
                new Object[]{fileName});
        db.close();
    }

    /**
     * 更新文件的下载状态
     */
    @Override
    public synchronized void updateDownloadState(String fileName, String downloadState) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put("download_state", downloadState);
        db.update("tb_course", values, "file_name=?", new String[]{fileName});
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    /**
     * 更新文件被几节课使用的数量
     */
    @Override
    public synchronized void updateQuoteCount(String fileName, int quoteCount) {

    }

    /**
     * 查询某个文件被几节课使用
     *
     * @return 最大引用数
     */
    @Override
    public synchronized int queryQuoteCount(String fileName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_course where file_name = ?", new String[]{fileName});
        int quoteCount = 0;
        while (cursor.moveToNext()) {
            int count = cursor.getInt(cursor.getColumnIndex("quote_count"));
            if (count > quoteCount) {
                quoteCount = count;
            }
        }
        db.close();
        return quoteCount;
    }

    /**
     * 查询一个文件的存放路径
     */
    @Override
    public synchronized String queryFilePath(String fileName) {
        return null;
    }

    /**
     * 查询某个文件是否下载完毕
     */
    @Override
    public synchronized boolean queryDownloadState(String fileName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_course where file_name = ?", new String[]{fileName});
        boolean isDownloadOk = false;
        while (cursor.moveToNext()) {
            int downloadState = cursor.getInt(cursor.getColumnIndex("download_state"));
            if (downloadState == 1) {
                isDownloadOk = true;
            }
        }
        db.close();
        return isDownloadOk;
    }
}
