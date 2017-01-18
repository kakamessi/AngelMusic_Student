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
         * file_name:文件名称(主键)
         * file_path:文件存放的绝对路径
         * download_state:下载的状态:1下载完,0未完成
         * quote_count:当前文件被几节课使用，方便删除时的判断，不为1的时候不能删除本地文件，只执行quote-1操作
         */
        final String SQL_CREATE_TB = "create table " + TB_NAME_COURSE +
                "(file_name String primary key," +
                "file_path String," +
                "download_state integer," +
                "quote_count integer)";
        db.execSQL(SQL_CREATE_TB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
