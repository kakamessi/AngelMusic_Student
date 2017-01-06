package com.angelmusic.student.batch_download;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库帮助类
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "download_db";//数据库名
    private static final String TB_NAME = "download_info_tb";//表名
    private static final int VERSION = 1;//版本号

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         * 此表定义五个字段
         * file_name:文件名称
         * file_url:文件的下载地址
         * file_size:要下载文件总大小
         * class_id:属于哪一课
         * download_state:下载的状态:0为下载完成,1为下载未完成
         */
        final String SQL_CREATE_TB = "create table " + TB_NAME + "(_id integer primary key autoincrement," +
                "file_name String," +
                "file_url String," +
                "file_size integer," +
                "complete_percent integer," +
                "download_state integer)";
        db.execSQL(SQL_CREATE_TB);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
