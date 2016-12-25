package com.weijie.vr4dream.config;

import android.content.res.AssetManager;

import com.weijie.vr4dream.App;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * app配置读取
 * 作者：guoweijie on 16/12/15 20:44
 * 邮箱：529844698@qq.com
 */
public class AppConfig {

    private static Properties properties = null;

    static {
        try {
            properties = new Properties();
            AssetManager am = App.getInstance().getAssets();
            InputStream inputStream = am.open("config.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取服务器请求地址
     * @return 服务器请求地址
     */
    public static String getBaseUrl() {
        return String.valueOf(properties.getProperty("base_url"));
    }

    /**
     * 获取测试服务器请求地址
     * @return 服务器请求地址
     */
    public static String getTestBaseUrl() {
        return String.valueOf(properties.getProperty("base_url_test"));
    }

    /**
     * 获取当前接口版本号
     * @return 当前接口版本号
     */
    public static String getInterfaceVersion() {
        return String.valueOf(properties.getProperty("interface_version"));
    }
    /**
     * 是否打开了全局异常捕捉
     * @return result
     */
    public static boolean isOpenCrashHandler() {
        return Boolean.valueOf(properties.getProperty("crash_handler_open"));
    }

    /**
     * 是否打印服务器返回的数据
     * @return result
     */
    public static boolean isPrintResponseData() {
        return Boolean.valueOf(properties.getProperty("data_response_print"));
    }

    /**
     * 获取日志打印tag
     * @return tag
     */
    public static String getLogTag() {
        return String.valueOf(properties.getProperty("log_tag"));
    }

    /**
     * 获取偏好设置文件名
     * @return 偏好设置文件名
     */
    public static String getSharedPreferenceName() {
        return String.valueOf(properties.getProperty("shared_preference_name"));
    }

    /**
     * 获取图片保存文件夹
     * @return 图片保存文件夹
     */
    public static String getPicDir() {
        return String.valueOf(properties.getProperty("dir_pic"));
    }

    /**
     * 获取文件缓存保存文件夹
     * @return 文件缓存保存文件夹
     */
    public static String getFileCacheDir() {
        return String.valueOf(properties.getProperty("dir_file"));
    }

    /**
     * 获取log保存文件夹
     * @return log保存文件夹
     */
    public static String getLogDir() {
        return String.valueOf(properties.getProperty("dir_log"));
    }


}
