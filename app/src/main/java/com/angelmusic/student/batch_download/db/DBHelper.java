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
         * 此表定义五个字段
         * file_name:文件名称
         * file_path:文件存放地址
         * file_url:文件下载地址
         * course_name:从属哪一节课的课程名
         * download_state:下载的状态:1下载完,0未完成
         * quote_count:当前文件被几节课使用，方便删除时的判断
         */
        final String SQL_CREATE_TB = "create table " + TB_NAME_COURSE + "(_id integer primary key autoincrement," +
                "file_name String," +
                "file_path String," +
                "file_url String," +
                "course_name String," +
                "download_state integer," +
                "quote_count integer)";
        db.execSQL(SQL_CREATE_TB);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
