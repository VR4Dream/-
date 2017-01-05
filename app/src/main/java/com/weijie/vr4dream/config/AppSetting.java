package com.weijie.vr4dream.config;

import android.content.Context;
import android.content.SharedPreferences;

import com.weijie.vr4dream.App;

import java.util.Set;

/**
 * 偏好设置帮助类
 * Created by keweiquan on 15/11/26.
 */
public class AppSetting {
    private static AppSetting appSetting;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    static {
        sharedPreferences = App.getInstance().getSharedPreferences(AppConfig.getSharedPreferenceName(), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    AppSetting() {

    }

    public static AppSetting getInstance() {
        if (appSetting == null) {
            synchronized (AppSetting.class) {
                if (appSetting == null) {
                    appSetting = new AppSetting();
                }
            }
        }
        return appSetting;
    }

    /**
     * 保存String类型的数据
     *
     * @param key   键
     * @param value 值
     */
    public void save(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 保存int类型的数据
     *
     * @param key   键
     * @param value 值
     */
    public void save(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 保存boolean类型的数据
     *
     * @param key   键
     * @param value 值
     */
    public void save(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 保存float类型的数据
     *
     * @param key   键
     * @param value 值
     */
    public void save(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
    }

    /**
     * 保存long类型的数据
     *
     * @param key   键
     * @param value 值
     */
    public void save(String key, long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * 保存Set<String>类型的数据
     *
     * @param key   键
     * @param value 值
     */
    public void save(String key, Set<String> value) {
        editor.putStringSet(key, value);
        editor.commit();
    }

    /**
     * 获取数据
     *
     * @param key 键
     * @return
     */
    public String getString(String key) {
        return sharedPreferences.getString(key, null);
    }

    /**
     * 获取数据
     *
     * @param key 键
     * @return
     */
    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    /**
     * 获取数据
     *
     * @param key 键
     * @return
     */
    public int getInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    /**
     * 获取数据
     *
     * @param key 键
     * @return
     */
    public int getInt(String key, int defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    /**
     * 获取数据
     *
     * @param key 键
     * @return
     */
    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    /**
     * 获取数据
     *
     * @param key 键
     * @return
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    /**
     * 获取数据
     *
     * @param key 键
     * @return
     */
    public float getFloat(String key) {
        return sharedPreferences.getFloat(key, 0f);
    }

    /**
     * 获取数据
     *
     * @param key 键
     * @return
     */
    public float getFloat(String key, float defaultValue) {
        return sharedPreferences.getFloat(key, defaultValue);
    }

    /**
     * 获取数据
     *
     * @param key 键
     * @return
     */
    public long getLong(String key) {
        return sharedPreferences.getLong(key, 0l);
    }

    /**
     * 获取数据
     *
     * @param key 键
     * @return
     */
    public long getLong(String key, long defaultValue) {
        return sharedPreferences.getLong(key, defaultValue);
    }

    /**
     * 获取数据
     *
     * @param key 键
     * @return
     */
    public Set<String> getStringSet(String key) {
        return sharedPreferences.getStringSet(key, null);
    }

    /**
     * 获取数据
     *
     * @param key 键
     * @return
     */
    public Set<String> getStringSet(String key, Set<String> defaultValue) {
        return sharedPreferences.getStringSet(key, defaultValue);
    }

    /**
     * 根据key删除某一项内容
     *
     * @param key 键
     */
    public void removeAppSettingByKey(String key) {
        editor.remove(key);
        editor.commit();
    }

    /**
     * 清空sharepreference
     */
    public void clearAllAppSetting() {
        editor.clear();
        editor.commit();
    }

}
