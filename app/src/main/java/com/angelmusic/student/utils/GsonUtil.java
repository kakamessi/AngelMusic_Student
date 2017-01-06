package com.angelmusic.student.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by fei on 2016/12/13.
 */

public class GsonUtil {
    private static Gson gson = null;

    static {
        if (gson == null) {
            gson = new Gson();
        }
    }

    private GsonUtil() {
    }

    /**
     * 转成json
     *
     * @param object 要转化的对象
     * @return json字符串
     */
    public static String objectToJsonString(Object object) {
        String jsonString = null;
        if (gson != null) {
            jsonString = gson.toJson(object);
        }
        return jsonString;
    }

    /**
     * json字符串转换成object
     */
    public static <T> T jsonToObject(String jsonString, Class<T> cls) {
        T t = null;
        if (!TextUtils.isEmpty(jsonString) && gson != null) {
            t = gson.fromJson(jsonString, cls);
        }
        return t;
    }

    /**
     * 转成list
     * 解决泛型问题
     *
     * @param jsonString json字符串
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToList2(String jsonString, Class<T> cls) {
        if (!TextUtils.isEmpty(jsonString) && gson != null) {
            List<T> list = new ArrayList<T>();
            JsonArray array = new JsonParser().parse(jsonString).getAsJsonArray();
            for (final JsonElement elem : array) {
                list.add(gson.fromJson(elem, cls));
            }
            return list;
        }
        return null;
    }

    /**
     * 转成list中有map的
     *
     * @param jsonString
     * @return
     */
    public static <T> List<Map<String, T>> jsonToListMaps(String jsonString) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(jsonString,
                    new TypeToken<List<Map<String, T>>>() {
                    }.getType());
        }
        return list;
    }

    /**
     * 转成map
     *
     * @param jsonString json字符串
     * @return
     */
    public static <T> Map<String, T> jsonToMaps(String jsonString) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(jsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }
}
