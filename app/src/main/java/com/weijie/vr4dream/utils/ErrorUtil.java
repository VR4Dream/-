package com.weijie.vr4dream.utils;

import cn.bmob.v3.exception.BmobException;

/**
 * bmob异常工具类
 * 作者：guoweijie on 16/12/24 17:21
 * 邮箱：529844698@qq.com
 */
public class ErrorUtil {

    public static String getErrorMsg(BmobException e) {
        switch (e.getErrorCode()) {
            case 202:
                return "用户名已经存在";
            case 203:
                return "邮箱已经存在";
            case 207:
                return "验证码错误";
            case 209:
                return "该手机号码已经存在";
            case 210:
                return "旧密码不正确";
            default:
                return "未知异常: " + e.getErrorCode();
        }
    }

}
