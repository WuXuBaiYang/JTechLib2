package com.jtechlib2.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 缓存管理基类
 * Created by jianghan on 2016/9/21.
 */
public abstract class BaseCacheManager {
    private int mode;
    private Gson gson;
    private ACache aCache;
    private Context context;
    private SharedPreferences sharedPreferences;

    public BaseCacheManager(Context context) {
        this(context, Context.MODE_PRIVATE);
    }

    public BaseCacheManager(Context context, int mode) {
        this.context = context;
        this.mode = mode;
    }

    /**
     * 懒加载acache对象
     *
     * @return 当前类对象
     */
    private ACache getACache() {
        if (null == aCache) {
            this.aCache = ACache.get(context, getCacheName());
        }
        return aCache;
    }

    /**
     * 懒加载SharedPreferences对象
     *
     * @return sp对象
     */
    private SharedPreferences getSharedPreferences() {
        if (null == sharedPreferences) {
            this.sharedPreferences = context.getSharedPreferences(getCacheName(), mode);
        }
        return sharedPreferences;
    }

    /**
     * 懒加载Gson对象
     *
     * @return gson对象
     */
    private Gson getGson() {
        if (null == gson) {//首次获取gson
            this.gson = getCustomizeGson();
            if (null == gson) {//为空则创建默认gson
                this.gson = new Gson();
            }
        }
        return gson;
    }

    /**
     * 插入byte[]类型
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, Byte[] value) {
        put(key, value, -1);
    }

    /**
     * 插入byte[]类型
     *
     * @param key      键
     * @param value    值
     * @param saveTime 存储时间
     */
    public void put(String key, Byte[] value, int saveTime) {
        getACache().put(key, value, saveTime);
    }

    /**
     * 插入string类型
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, String value) {
        put(key, value, -1);
    }

    /**
     * 插入string类型
     *
     * @param key      键
     * @param value    值
     * @param saveTime 存储时间
     */
    public void put(String key, String value, int saveTime) {
        getACache().put(key, value, saveTime);
    }

    /**
     * 插入实现了serializable接口的对象
     *
     * @param key   键
     * @param value 值
     * @param <D>   泛型
     */
    public <D extends Serializable> void put(String key, D value) {
        put(key, value, -1);
    }

    /**
     * 插入实现了serializable接口的对象
     *
     * @param key      键
     * @param value    值
     * @param <D>      泛型
     * @param saveTime 存储时间
     */
    public <D extends Serializable> void put(String key, D value, int saveTime) {
        getACache().put(key, value, saveTime);
    }

    /**
     * 插入jsonobject对象
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, JSONObject value) {
        put(key, value, -1);
    }

    /**
     * 插入jsonobject对象
     *
     * @param key      键
     * @param value    值
     * @param saveTime 存储时间
     */
    public void put(String key, JSONObject value, int saveTime) {
        getACache().put(key, value, saveTime);
    }

    /**
     * 插入jsonarray对象
     *
     * @param key   键
     * @param value 值
     */
    public void put(String key, JSONArray value) {
        put(key, value, -1);
    }

    /**
     * 插入jsonarray对象
     *
     * @param key      键
     * @param value    值
     * @param saveTime 存储时间
     */
    public void put(String key, JSONArray value, int saveTime) {
        getACache().put(key, value, saveTime);
    }

    /**
     * 插入一个集合
     *
     * @param key    键
     * @param values 值
     * @param <D>    泛型
     * @return 是否插入成功
     */
    public <D> boolean put(String key, List<D> values) {
        return put(key, values, -1);
    }

    /**
     * 插入一个集合,使用gson解析为jsonarray进行存储，可能会出现很多对象不兼容的问题
     *
     * @param key      键
     * @param values   值
     * @param saveTime 存储时间
     * @param <D>      泛型
     * @return 是否存储成功
     */
    public <D> boolean put(String key, List<D> values, int saveTime) {
        String json = getGson().toJson(values);
        if (!TextUtils.isEmpty(json)) {
            getACache().put(key, json, saveTime);
        }
        return false;
    }

    /**
     * 插入int类型数据
     *
     * @param key   键
     * @param value 值
     * @return 是否存储成功
     */
    public boolean put(String key, int value) {
        return getSharedPreferences()
                .edit()
                .putInt(key, value)
                .commit();
    }

    /**
     * 插入boolean类型数据
     *
     * @param key   键
     * @param value 值
     * @return 是否存储成功
     */
    public boolean put(String key, boolean value) {
        return getSharedPreferences()
                .edit()
                .putBoolean(key, value)
                .commit();
    }

    /**
     * 插入float类型
     *
     * @param key   键
     * @param value 值
     * @return 是否存储成功
     */
    public boolean put(String key, float value) {
        return getSharedPreferences()
                .edit()
                .putFloat(key, value)
                .commit();
    }

    /**
     * 插入long类型
     *
     * @param key   键
     * @param value 值
     * @return 是否存储成功
     */
    public boolean put(String key, long value) {
        return getSharedPreferences()
                .edit()
                .putLong(key, value)
                .commit();
    }

    /**
     * 根据key删除文件
     *
     * @param key 键
     * @return 是否删除成功
     */
    public boolean deleteByKey(@NonNull String key) {
        return getACache().remove(key) || getSharedPreferences()
                .edit()
                .remove(key)
                .commit();
    }

    /**
     * 查byte[]类型
     *
     * @param key 键
     * @return 获取byte数组
     */
    public byte[] getBinary(@NonNull String key) {
        return getACache().getAsBinary(key);
    }

    /**
     * 差String类型
     *
     * @param key 键
     * @return 获取存储的string 类型值
     */
    public String getString(@NonNull String key) {
        return getACache().getAsString(key);
    }

    /**
     * 查询实现了Serializable的对象
     *
     * @param key 键
     * @param <R> 泛型
     * @return 序列化的对象
     */
    public <R extends Serializable> R getSerializable(@NonNull String key) {
        return (R) getACache().getAsObject(key);
    }

    /**
     * 查询jsonobject
     *
     * @param key 键
     * @return jsonobject对象
     */
    public JSONObject getJsonObject(@NonNull String key) {
        return getACache().getAsJSONObject(key);
    }

    /**
     * 查询jsonarray
     *
     * @param key 键
     * @return jsonarray集合
     */
    public JSONArray getJsonArray(@NonNull String key) {
        return getACache().getAsJSONArray(key);
    }

    /**
     * 获取一个集合
     *
     * @param key     键
     * @param typeOfT 类别
     * @param <D>     泛型
     * @return 集合
     */
    public <D> List<D> getList(@NonNull String key, Type typeOfT) {
        JSONArray jsonArray = getJsonArray(key);
        return getGson().fromJson(null != jsonArray ? jsonArray.toString() : "[]", typeOfT);
    }

    /**
     * 查询int
     *
     * @param key      键
     * @param defValut 默认值
     * @return int值
     */
    public int getInt(@NonNull String key, int defValut) {
        return getSharedPreferences().getInt(key, defValut);
    }

    /**
     * 查询boolean
     *
     * @param key      键
     * @param defValue 默认值
     * @return 布尔值
     */
    public boolean getBoolean(@NonNull String key, boolean defValue) {
        return getSharedPreferences().getBoolean(key, defValue);
    }

    /**
     * 查询float
     *
     * @param key      键
     * @param defValue 默认值
     * @return float值
     */
    public float getFloat(@NonNull String key, float defValue) {
        return getSharedPreferences().getFloat(key, defValue);
    }

    /**
     * 查询long
     *
     * @param key      键
     * @param defVlaue 默认值
     * @return long值
     */
    public long getLong(@NonNull String key, long defVlaue) {
        return getSharedPreferences().getLong(key, defVlaue);
    }

    /**
     * 获取缓存路径名
     *
     * @return 获取存储路径名
     */
    public abstract String getCacheName();

    /**
     * 获取定制gson,使用者可以重写,不为空则会使用当前返回
     *
     * @return 获取自定义的gson
     */
    public Gson getCustomizeGson() {
        return new Gson();
    }
}