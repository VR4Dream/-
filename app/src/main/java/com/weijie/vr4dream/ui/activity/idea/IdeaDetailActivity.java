package com.weijie.vr4dream.ui.activity.idea;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.weijie.vr4dream.R;
import com.weijie.vr4dream.presenter.idea.IdeaDetailPresenter;
import com.weijie.vr4dream.ui.activity.BaseActivity;
import com.weijie.vr4dream.ui.view.idea.IIdeaDetailView;
import com.weijie.vr4dream.utils.SpannableUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 文章详情页
 * 作者：guoweijie on 16/12/22 10:09
 * 邮箱：529844698@qq.com
 */
public class IdeaDetailActivity extends BaseActivity<IdeaDetailPresenter> implements IIdeaDetailView {

    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.tv_comment)
    TextView tvComment;

    @Override
    protected void initPresenter() {
        mPresenter = new IdeaDetailPresenter(mContext, this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_idea_detail;
    }

    @Override
    protected void initialize() {
        tvComment.setText(SpannableUtils.setTextUnderlineForeground("智慧如你，快来评论吧！", 5, 7, Color.parseColor("#34b895")));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }
        webView.setWebChromeClient(client);
        webView.setWebViewClient(webViewClient);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://oa.linshimuye.com:8082/api/consume/8a8a8159588ac69b01589017a3565ec9/discover.html");
    }

    WebChromeClient client = new WebChromeClient() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
        }
    };

    WebViewClient webViewClient = new WebViewClient(){

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return true;
        }

        public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
        }

        @Override
        public void onPageFinished(WebView view, String url) {
        }
    };

    @OnClick({R.id.btn_back, R.id.btn_more})
    void onMenuClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }
    }

}
