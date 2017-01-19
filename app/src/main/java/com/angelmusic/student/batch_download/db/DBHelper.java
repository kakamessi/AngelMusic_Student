package com.angelmusic.student.batch_download.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库帮助类
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "download_db";//数据库名
    private static final String TB_NAME_COURSE = "tb_course";//表名
    private static final int VERSION = 1;//版本号

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         * 此表定义4个字段
         * file_name:文件名称
         * download_state:下载的状态:1下载完,0未完成
         * course_name:课程名
         */
        final String SQL_CREATE_TB = "create table " + TB_NAME_COURSE +
                "(_id integer primary key autoincrement," +
                "file_name String ," +
                "course_name String," +
                "download_state)";
        db.execSQL(SQL_CREATE_TB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
