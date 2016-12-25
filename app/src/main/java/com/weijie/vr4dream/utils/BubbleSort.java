package com.weijie.vr4dream.utils;

/**
 * 冒泡排序
 * Created by keweiquan on 15/11/30.
 */
public class BubbleSort {

    /**
     * 进行排序
     *
     * @param a 等待排序的数组
     */
    public static void sort(int[] a) {
        int temp = 0;
        for (int i = a.length - 1; i > 0; --i) {
            for (int j = 0; j < i; ++j) {
                if (a[j + 1] < a[j]) {
                    temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
        }
    }

}
