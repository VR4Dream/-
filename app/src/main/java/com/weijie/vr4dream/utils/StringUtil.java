package com.weijie.vr4dream.utils;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * Created by keweiquan on 15/11/26.
 */
public class StringUtil {

    /**
     * md5加密
     * @param key 加密字符串
     * @return 加密字符串
     */
    public static String encodeByMD5(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    /**
     * 转换字节数组为十六进制字符串
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xFF & aByte);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * 验证手机
     * @param mobile 手机号
     * @return 是否格式正确
     */
    public static boolean validateMobile(String mobile) {
        String regex = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mobile);
        if (TextUtils.isEmpty(mobile)) {
            return false;
        } else if (!matcher.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 验证邮箱
     * @param email 邮箱
     * @return 是否格式正确
     */
    public static boolean validateEmail(String email) {
        String regex = "^[a-z0-9]+([._\\\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (TextUtils.isEmpty(email)) {
            return false;
        } else if (!matcher.matches()) {
            return false;
        }
        return true;
    }

}
