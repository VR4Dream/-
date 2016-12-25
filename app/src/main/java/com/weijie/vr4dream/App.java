package com.weijie.vr4dream;

import android.app.Application;

import com.weijie.vr4dream.config.AppConfig;
import com.weijie.vr4dream.utils.FileUtil;
import com.weijie.vr4dream.utils.RxBus;

import java.io.File;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

/**
 * 作者：guoweijie on 16/12/15 17:11
 * 邮箱：529844698@qq.com
 * App
 */
public class App extends Application {

    private static App mInstance;

    private RxBus mRxBus;

    // 图片缓存文件夹创建结果
    private boolean mPicMkdirResult;
    // 图片文件夹缓存目录
    private String mPicCacheDir;
    // 文件缓存文件夹创建结果
    private boolean mFileMkdirResult;
    // 文件缓存文件夹目录
    private String mFileCacheDir;
    // 日志缓存文件夹创建结果
    private boolean mLogMkdirResult;
    // 日志缓存文件夹目录
    private String mLogCacheDir;

    // 日志打印tag
    private String mLogTag;

    // 服务器请求地址
    private String mBaseUrl;
    private String mTestBaseUrl;
    // 当前接口版本号
    private String mInterfaceVersion;

    private BmobUser user;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Bmob.initialize(mInstance, "3aefa2db08b19ecf61a8771d43afc413");
        //初始化bmob
        //BmobConfigUtil.initialize(mInstance);

        mRxBus = new RxBus();

        checkCacheDir();
        initLogTag();
        initNetConfig();
    }

    public static App getInstance() {
        return mInstance;
    }

    public RxBus getRxBus() {
        if (mRxBus == null) {
            mRxBus = new RxBus();
        }
        return mRxBus;
    }

    /**
     * 检查缓存文件夹
     */
    private void checkCacheDir() {
        // 图片
        File picFile = FileUtil.getDiskCacheDir(mInstance, AppConfig.getPicDir());
        mPicCacheDir = picFile.getAbsolutePath();
        if (!picFile.exists()) {
            mPicMkdirResult = picFile.mkdir();
        }

        // 文件
        File fileFile = FileUtil.getDiskCacheDir(mInstance, AppConfig.getFileCacheDir());
        mFileCacheDir = fileFile.getAbsolutePath();
        if (!fileFile.exists()) {
            mFileMkdirResult = fileFile.mkdir();
        }

        // 日志
        File logFile = FileUtil.getDiskCacheDir(mInstance, AppConfig.getLogDir());
        mLogCacheDir = logFile.getAbsolutePath();
        if (!logFile.exists()) {
            mLogMkdirResult = logFile.mkdir();
        }
    }

    /**
     * 初始化log打印tag
     */
    private void initLogTag() {
        mLogTag = AppConfig.getLogTag();
    }

    /**
     * 初始化网络配置
     */
    private void initNetConfig() {
        mBaseUrl = AppConfig.getBaseUrl();
        mTestBaseUrl = AppConfig.getTestBaseUrl();
        mInterfaceVersion = AppConfig.getInterfaceVersion();
    }

    /**
     * 创建图片缓存文件夹结果
     *
     * @return result
     */
    public boolean getPicMkdirResult() {
        return mPicMkdirResult;
    }

    /**
     * 创建文件缓存文件夹结果
     *
     * @return result
     */
    public boolean getFileMkdirResult() {
        return mFileMkdirResult;
    }


    /**
     * 创建日志缓存文件夹结果
     *
     * @return result
     */
    public boolean getLogMkdirResult() {
        return mLogMkdirResult;
    }

    /**
     * 获取图片缓存目录
     *
     * @return 图片缓存目录
     */
    public String getPicCacheDir() {
        return mPicCacheDir + File.separator;
    }

    /**
     * 获取文件缓存目录
     *
     * @return 文件缓存目录
     */
    public String getFileCacheDir() {
        return mFileCacheDir + File.separator;
    }

    /**
     * 获取日志缓存目录
     *
     * @return 日志缓存目录
     */
    public String getLogCacheDir() {
        return mLogCacheDir + File.separator;
    }

    /**
     * 获取日志打印tag
     *
     * @return tag
     */
    public String getLogTag() {
        return mLogTag;
    }

    /**
     * 服务器请求链接
     *
     * @return 链接
     */
    public String getBaseUrl() {
        return mBaseUrl;
    }

    /**
     * 获取汽车logo图片链接
     *
     * @return 链接
     */
    public String getCarLogoUrl(int logoId) {
        return mBaseUrl + "data/auto/70x70/" + logoId + ".png";
    }

    /**
     * 测试服务器请求链接
     *
     * @return 链接
     */
    public String getTestBaseUrl() {
        return mTestBaseUrl;
    }

    /**
     * 获取服务器接口版本号
     *
     * @return 服务器接口版本号
     */
    public String getInterfaceVersion() {
        return mInterfaceVersion;
    }

    public BmobUser getUser() {
        return user;
    }

    public void setUser(BmobUser user) {
        this.user = user;
    }
}
