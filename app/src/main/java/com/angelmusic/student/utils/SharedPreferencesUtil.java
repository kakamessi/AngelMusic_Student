package com.angelmusic.student.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Created by fei on 2016/12/2.
 * 共享文件操作类工具:能存储boolean，int，float，long和String五种简单的数据类型
 */
public class SharedPreferencesUtil {

    private static SharedPreferences pref;// 文件操作对象

    /**
     * 设置上下文，并且初始化文件操作对象
     *
     * @param context 上下文对象
     * @param name    所需的首选项文件。如果参数文件的名称不存在，当你检索一个编辑器（SharedPreferences.edit()）然后提交更改（编辑。commit()）则会创建。
     * @param mode    Context.MODE_PRIVATE:指定该SharedPreferences数据只能被本应用程序读、写。0x0000
     *                Context.MODE_WORLD_READABLE:  指定该SharedPreferences数据能被其他应用程序读，但不能写。0x0001
     *                Context.MODE_WORLD_WRITEABLE: 指定该SharedPreferences数据能被其他应用程序读，写。0x0002
     */
    public static void setContextAndInit(Context context, String name, int mode) {
        if (pref == null)
            pref = context.getSharedPreferences(name, mode); // SharedPreferences本身是一个接口，程序无法直接创建SharedPreferences实例，只能通过Context提供的getSharedPreferences(String name, int mode)方法来获取SharedPreferences实例
    }

    /**
     * 返回 SharedPreferences 是否已经初始化
     *
     * @return
     */
    public static boolean isInit() {
        return pref != null;
    }

    /**
     * 查看是否包含某个键值
     *
     * @param key TextUtils.isEmpty():Returns true if the string is null or 0-length.
     */
    public static boolean hasKey(String key) {
        if (!TextUtils.isEmpty(key)) {
            return pref.contains(key);
        }
        return false;
    }

    /**
     * 向共享文件中保存string型数据
     *
     * @param key      表签名
     * @param defValue 值
     */
    public static void setString(String key, String defValue) {
        pref.edit().putString(key, defValue).commit();
    }

    /**
     * 向共享文件中保存int型数据
     *
     * @param key      表签名
     * @param defValue 值
     */
    public static void setInt(String key, int defValue) {
        pref.edit().putInt(key, defValue).commit();
    }

    /**
     * 向共享文件中保存long型数据
     *
     * @param key      表签名
     * @param defValue 值
     */
    public static void setLong(String key, long defValue) {
        pref.edit().putLong(key, defValue).commit();
    }

    /**
     * 向共享文件中保存float型数据
     *
     * @param key      表签名
     * @param defValue 值
     */
    public static void setFloat(String key, float defValue) {
        pref.edit().putFloat(key, defValue).commit();
    }

    /**
     * 向共享文件中保存boolean数据
     *
     * @param key      表签名
     * @param defValue 值
     */
    public static void setBoolean(String key, boolean defValue) {
        pref.edit().putBoolean(key, defValue).commit();
    }

    /**
     * 从共享文件中获取string数据
     *
     * @param key      表签名
     * @param defValue 值
     */
    public static String getString(String key, String defValue) {
        return pref.getString(key, defValue);
    }

    /**
     * 从共享文件中获取int数据
     *
     * @param key      表签名
     * @param defValue 值
     */
    public static int getInt(String key, int defValue) {
        return pref.getInt(key, defValue);
    }

    /**
     * 从共享文件中获取long数据
     *
     * @param key      表签名
     * @param defValue 值
     */
    public static long getLong(String key, long defValue) {
        return pref.getLong(key, defValue);
    }

    /**
     * 从共享文件中获取float数据
     *
     * @param key      表签名
     * @param defValue 值
     */
    public static float getFloat(String key, float defValue) {
        return pref.getFloat(key, defValue);
    }

    /**
     * 从共享文件中获取boolean数据
     *
     * @param key      表签名
     * @param defValue 值
     */
    public static boolean getBoolean(String key, boolean defValue) {
        return pref.getBoolean(key, defValue);
    }
}

