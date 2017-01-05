package com.weijie.vr4dream.ui.activity.gallery;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.weijie.vr4dream.R;
import com.weijie.vr4dream.presenter.gallery.GalleryDetailPresenter;
import com.weijie.vr4dream.ui.activity.BaseActivity;
import com.weijie.vr4dream.ui.view.gallery.IGalleryDetailView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * VR详情页
 * 作者：guoweijie on 17/1/3 10:58
 * 邮箱：529844698@qq.com
 */
public class GalleryDetailActivity extends BaseActivity<GalleryDetailPresenter> implements IGalleryDetailView {

    @Bind(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initPresenter() {
        mPresenter = new GalleryDetailPresenter(mContext, this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_gallery_detail;
    }

    @Override
    protected void initialize() {
        webView.setKeepScreenOn(true);
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        //支持js
        webView.getSettings().setJavaScriptEnabled(true);

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.JELLY_BEAN){
            fixNewAndroid(webView);
        }
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

        });
        webView.loadUrl("http://oa.linshimuye.com:8082/pano/8a8a8159595a814b01595ce350843bad/tour.html?LSMY=MMJZ");
    }

    private void fixNewAndroid(WebView webView) {
        try {
            webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        } catch(NullPointerException e) {
        }
    }

    @OnClick({R.id.btn_left})
    void clickMenu(View view) {
        switch (view.getId()) {
            case R.id.btn_left:
                finish();
                break;
        }
    }

}
