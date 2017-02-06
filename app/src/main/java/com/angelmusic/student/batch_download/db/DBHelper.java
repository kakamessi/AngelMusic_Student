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
    private static final String TB_NAME_STATE = "tb_state";//表名
    private static final int VERSION = 1;//版本号

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         * 表1定义4个字段,记录每个文件的下载内容
         * file_name:文件名称
         * download_state:下载的状态:"1"下载完,"0"未完成
         * course_name:课程名
         */
        final String SQL_CREATE_TB = "create table " + TB_NAME_COURSE +
                "(_id integer primary key autoincrement," +
                "file_name String ," +
                "course_name String," +
                "download_state String)";
        db.execSQL(SQL_CREATE_TB);

        /** loading_state:课程所处的下载状态按钮:"1"没在下载状态，"0"下载中*/
        final String SQL_CREATE_TB2 = "create table " + TB_NAME_STATE +
                "(course_name String primary key," +
                "loading_state String)";
        db.execSQL(SQL_CREATE_TB2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
