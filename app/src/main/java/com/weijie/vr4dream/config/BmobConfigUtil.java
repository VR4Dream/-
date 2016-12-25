package com.weijie.vr4dream.config;

import android.content.Context;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

/**
 * Bmob初始化工具
 * 作者：guoweijie on 16/12/24 10:41
 * 邮箱：529844698@qq.com
 */
public class BmobConfigUtil {

    public static void initialize(Context context) {
        BmobConfig config =new BmobConfig.Builder(context)
        //设置appkey
        .setApplicationId("3aefa2db08b19ecf61a8771d43afc413")
        //请求超时时间（单位为秒）：默认15s
        .setConnectTimeout(15)
        //文件分片上传时每片的大小（单位字节），默认512*1024
        .setUploadBlockSize(512*1024)
        //文件的过期时间(单位为秒)：默认1800s
        .setFileExpiration(2500)
        .build();
        Bmob.initialize(config);
    }

}
