package com.angelmusic.student.batch_download.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.angelmusic.stu.utils.Log;
import com.angelmusic.student.utils.LogUtil;

/**
 * Created by fei on 2017/1/16.
 */

public class DAOImpl implements DAO {
    private DBHelper dbHelper;
    private static DAOImpl mDAOImpl;

    public DAOImpl(Context context) {
        dbHelper = new DBHelper(context);
    }

    /**
     * 获取单例（线程安全）
     *
     * @param mContext 上下文对象
     */
    public synchronized static DAOImpl getInstance(Context mContext) {
        if (null == mDAOImpl) {
            synchronized (DAOImpl.class) {
                if (null == mDAOImpl) {
                    mDAOImpl = new DAOImpl(mContext);
                }
            }
        }
        return mDAOImpl;
    }

    /**
     * 插入一条新数据
     */
    @Override
    public synchronized void insertFile(String fileName, String courseName, String downloadState) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.execSQL("insert into tb_course(file_name,course_name,download_state) values(?,?,?)",
                new Object[]{fileName, courseName, downloadState});
        db.close();
    }

    /**
     * 删除所有file_name=fileName并且course_name=courseName的数据
     */
    @Override
    public synchronized void deleteFile(String fileName, String courseName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.execSQL("delete from tb_course where file_name = ? and course_name = ?",
                new Object[]{fileName, courseName});
        db.close();
    }

    /**
     * 将所有file_name=fileName的download_state的状态设置为downloadState
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
     * 查询是否存在file_name=fileName的条目
     */
    @Override
    public synchronized boolean isFileNameExist(String fileName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_course where file_name = ?", new String[]{fileName});
        boolean isExist = false;
        if (cursor.moveToNext()) {
            isExist = true;
        }
        db.close();
        return isExist;
    }

    @Override
    public synchronized boolean isCourseNameExist(String courseName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_course where course_name = ?", new String[]{courseName});
        boolean isExist = false;
        if (cursor.moveToNext()) {
            isExist = true;
        }
        db.close();
        return isExist;
    }

    /**
     * 查询file_name=fileName并且course_name=courseName的条目是否存在
     */
    @Override
    public synchronized boolean isCourseNameExist(String fileName, String courseName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_course where file_name = ? and course_name = ?", new String[]{fileName, courseName});
        boolean isExist = false;
        if (cursor.moveToNext()) {
            isExist = true;
        }
        db.close();
        return isExist;
    }

    /**
     * 查询file_name=fileName的条目中是否有下载完的
     */
    @Override
    public synchronized boolean isDownloadOk(String fileName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_course where file_name = ?", new String[]{fileName});
        boolean isDownloadOk = false;
        while (cursor.moveToNext()) {
            String downloadState = cursor.getString(cursor.getColumnIndex("download_state"));
            if ("1".equals(downloadState)) {
                isDownloadOk = true;
            }
        }
        db.close();
        return isDownloadOk;
    }

    /**
     * 查询file_name=fileName并且course_name=courseName的条目是否有下载完
     */
    @Override
    public synchronized boolean isDownloadOk(String fileName, String courseName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_course where file_name = ? and course_name = ?", new String[]{fileName, courseName});
        Log.e("-cursor--02--", cursor.getCount() + "");
        boolean isDownloadOk = false;
        if (cursor.moveToNext()) {
            String downloadState = cursor.getString(cursor.getColumnIndex("download_state"));
            if ("1".equals(downloadState)) {
                isDownloadOk = true;
            }
        }
        db.close();
        return isDownloadOk;
    }

    /**
     * 查询file_name=fileName的文件是否可以在本地删除
     */
    @Override
    public synchronized boolean isCanDeleteLocalFile(String fileName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_course where file_name = ?", new String[]{fileName});
        boolean isCanDelete = false;
        if (cursor.getCount() == 1) {
            isCanDelete = true;
        }
        db.close();
        return isCanDelete;
    }
}
