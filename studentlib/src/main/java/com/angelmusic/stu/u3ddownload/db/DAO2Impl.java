package com.angelmusic.stu.u3ddownload.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by fei on 2017/1/16.
 * 表tb_state的操作接口实现类
 */

public class DAO2Impl implements DAO2 {
    private DBHelper dbHelper;
    private static DAO2Impl mDAO2Impl;

    public DAO2Impl(Context context) {
        dbHelper = new DBHelper(context);
    }

    /**
     * 获取单例（线程安全）
     *
     * @param mContext 上下文对象
     */
    public synchronized static DAO2Impl getInstance(Context mContext) {
        if (null == mDAO2Impl) {
            synchronized (DAO2Impl.class) {
                if (null == mDAO2Impl) {
                    mDAO2Impl = new DAO2Impl(mContext);
                }
            }
        }
        return mDAO2Impl;
    }

    /**
     * 插入一条新数据
     */
    @Override
    public synchronized void insertFile(String courseName, String loadingState) {
        SQLiteDatabase db = null;
        try {
            db = dbHelper.getReadableDatabase();
            db.execSQL("insert into tb_state(course_name,loading_state) values(?,?)",
                    new Object[]{courseName, loadingState});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    /**
     * 删除所有course_name=courseName的数据
     */
    @Override
    public synchronized void deleteFile(String courseName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.execSQL("delete from tb_state where course_name = ?",
                new Object[]{courseName});
        db.close();
    }


    /**
     * 将所有course_name=courseName的loading_state的状态设置为loadingState
     */
    @Override
    public synchronized void updateLoadingState(String courseName, String loadingState) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        values.put("loading_state", loadingState);
        db.update("tb_state", values, "course_name=?", new String[]{courseName});
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    /**
     * 查询某一课下载状态
     *
     * @return "0":正在下载，"1":没有正在下载
     */
    @Override
    public boolean queryIsLoading(String courseName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_state where course_name = ?", new String[]{courseName});
        boolean isLoading = false;
        if (cursor.moveToNext()) {
            String loadingState = cursor.getString(cursor.getColumnIndex("loading_state"));
            if ("0".equals(loadingState)) {
                isLoading = true;
            }
        }
        cursor.close();
        db.close();
        return isLoading;
    }

    /**
     * 查询当前课程存不存在表中
     */
    @Override
    public boolean queryIsExist(String courseName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from tb_state where course_name = ?", new String[]{courseName});
        boolean isExist = false;
        if (cursor.getCount() > 0) {
            isExist = true;
        }
        cursor.close();
        db.close();
        return isExist;
    }
}
