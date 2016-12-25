package com.weijie.vr4dream.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * 文件工具类
 * 作者：guoweijie on 16/12/15 20:46
 * 邮箱：529844698@qq.com
 */
public class FileUtil {

    /**
     * 获取本地缓存文件夹
     *
     * @param context    context
     * @param uniqueName 文件夹名
     * @return file
     */
    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            if (context.getExternalCacheDir() != null)
                cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * 删除文件
     *
     * @param filePathAndName String 文件路径及名称 如c:/fqf.txt
     */
    public static boolean delFile(String filePathAndName) {
        try {
            java.io.File myDelFile = new java.io.File(filePathAndName);
            return myDelFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除文件夹
     *
     * @param folderPath String 文件夹路径及名称 如c:/fqf
     * @return boolean
     */
    public static boolean delFolder(String folderPath) {
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            java.io.File myFilePath = new java.io.File(folderPath);
            return myFilePath.delete(); // 删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除文件夹里面的所有文件
     *
     * @param path String 文件夹路径 如 c:/fqf
     */
    public static void delAllFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            return;
        }
        String[] tempList = file.list();
        File temp;
        for (String aTempList : tempList) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + aTempList);
            } else {
                temp = new File(path + File.separator + aTempList);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + aTempList);// 先删除文件夹里面的文件
                delFolder(path + "/" + aTempList);// 再删除空文件夹
            }
        }
    }

    /**
     * 计算文件或者文件夹的大小 ，单位 MB
     *
     * @param path 路径
     * @return 大小，单位：MB
     */
    public static double getSize(String path) {
        File file = new File(path);
        // 判断文件是否存在
        if (file.exists()) {
            // 如果是目录则递归计算其内容的总大小，如果是文件则直接返回其大小
            if (!file.isFile()) {
                // 获取文件大小
                File[] fl = file.listFiles();
                double size = 0;
                for (File f : fl) {
                    path = f.getAbsolutePath();
                    size += getSize(path);
                }
                return size;

            } else {
                return (double) file.length() / 1024 / 1024;
            }

        } else {
            return 0;
        }

    }

    /**
     * 字符串转换unicode
     */
    public static String string2Unicode(String string) {
        StringBuilder unicode = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            // 取出每一个字符
            char c = string.charAt(i);
            // 转换为unicode
            unicode.append("\\u").append(Integer.toHexString(c));
        }
        return unicode.toString();
    }

}
