package com.weijie.vr4dream.config;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;
import com.weijie.vr4dream.App;

/**
 * Glide属性配置
 * 作者：guoweijie on 16/12/28 14:56
 * 邮箱：529844698@qq.com
 */
public class GlideConfiguration implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        builder.setDiskCache(new DiskLruCacheFactory(//
                App.getInstance().getPicCacheDir(),//
                300));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
    }
}
