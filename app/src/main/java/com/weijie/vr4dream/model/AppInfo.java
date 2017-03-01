package com.weijie.vr4dream.model;

import android.text.TextUtils;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * 作者：guoweijie on 17/2/10 15:37
 * 邮箱：529844698@qq.com
 */
public class AppInfo extends BmobObject implements Serializable {

    private Integer versionCode;
    private String versionName;
    private String url;	//下载地址
    private String title; //对话框标题，如果是强制更新，请填写"强制更新"
    private String desc; //升级说明

    //检查有效性
    public boolean isValid(){
        return versionCode >0 &&
                !TextUtils.isEmpty(url) &&
                !TextUtils.isEmpty(versionName) &&
                !TextUtils.isEmpty(desc);
    }

    //是否是强制更新
    public boolean isForced(){
        return "强制更新".equals(title);
    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
