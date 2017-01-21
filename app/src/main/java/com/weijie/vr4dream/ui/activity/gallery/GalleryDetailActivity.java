package com.weijie.vr4dream.ui.activity.gallery;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.weijie.vr4dream.App;
import com.weijie.vr4dream.R;
import com.weijie.vr4dream.model.Gallery;
import com.weijie.vr4dream.presenter.gallery.GalleryDetailPresenter;
import com.weijie.vr4dream.rxEvent.LoginStateChangeEvent;
import com.weijie.vr4dream.ui.activity.BaseActivity;
import com.weijie.vr4dream.ui.view.gallery.IGalleryDetailView;
import com.weijie.vr4dream.ui.widget.AlertDialogFragment;
import com.weijie.vr4dream.ui.widget.MenuDialog;
import com.weijie.vr4dream.ui.widget.ShareDialog;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * VR详情页
 * 作者：guoweijie on 17/1/3 10:58
 * 邮箱：529844698@qq.com
 */
public class GalleryDetailActivity extends BaseActivity<GalleryDetailPresenter> implements IGalleryDetailView {

    @Bind(R.id.webView)
    WebView webView;
    TextView btnLikes;
    TextView btnFavourite;


    private MenuDialog dialog;
    private ShareDialog shareDialog;
    private WindowManager.LayoutParams params;
    private Gallery data;

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
        initDialog();
        data = (Gallery)getIntent().getSerializableExtra("data");
        mPresenter.setGallery(data);
        subscribeEvent();
    }

    @Override
    public void loadWeb(String link) {
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
        webView.loadUrl(link);
    }

    private void fixNewAndroid(WebView webView) {
        try {
            webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        } catch(NullPointerException e) {
        }
    }

    @OnClick({R.id.btn_left, R.id.iv_show_menu})
    void clickMenu(View view) {
        switch (view.getId()) {
            case R.id.btn_left:
                finish();
                break;
            case R.id.iv_show_menu:
                showMenuDialog();
                break;
        }
    }

    @Override
    public void showMenuDialog() {
        if(dialog == null) {
            initDialog();
        }
        dialog.show();
    }

    void initDialog() {
        dialog = new MenuDialog(this,R.style.dialog_setting);

        dialog.findViewById(R.id.iv_hide_menu).setOnClickListener(menuClickListsner);

        btnLikes = (TextView)dialog.findViewById(R.id.btn_likes);
        btnLikes.setOnClickListener(menuClickListsner);

        btnFavourite = (TextView)dialog.findViewById(R.id.btn_favourite);
        btnFavourite.setOnClickListener(menuClickListsner);

        dialog.findViewById(R.id.btn_share).setOnClickListener(menuClickListsner);
        dialog.findViewById(R.id.btn_comment).setOnClickListener(menuClickListsner);
        //dialog.findViewById(R.id.btn_recommend).setOnClickListener(menuClickListsner);

        ((RadioGroup)dialog.findViewById(R.id.rg_vr)).setOnCheckedChangeListener(checkedChangeListener);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.RIGHT);
        window.setWindowAnimations(R.style.dialog_animation); // 添加动画
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.height = (int) (display.getHeight());
        params.dimAmount =0f;
        dialog.getWindow().setAttributes(params);
        dialog.hide();
    }

    @Override
    public void hideMenuDialog() {
        dialog.hide();
    }

    @Override
    public void setLikesStatus(boolean status) {
        btnLikes.setSelected(status);
    }

    @Override
    public void setFavourite(boolean status) {
        btnFavourite.setSelected(status);
    }

    @Override
    public void showLoginDialog(String content) {
        AlertDialogFragment dialogFragment = AlertDialogFragment.getInstance(content);
        dialogFragment.setOnDialogClickListener(new AlertDialogFragment.OnDialogClickListener() {
            @Override
            public void onPositiveButtonClickListener() {
            }
            @Override
            public void onNegativeButtonClickListener() {
                mPresenter.clickLogin();
            }
        });
        dialogFragment.show(getSupportFragmentManager(), "loginTip");
    }

    private View.OnClickListener menuClickListsner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_hide_menu:
                    hideMenuDialog();
                    break;
                case R.id.btn_likes:
                    mPresenter.clickLikes();
                    break;
                case R.id.btn_favourite:
                    mPresenter.clickFavourite();
                    break;
                case R.id.btn_share:
                    hideMenuDialog();
                    showShareDialog();
                    break;
                case R.id.btn_comment:
                    mPresenter.clickComment();
                    break;
                case R.id.btn_qq:
                    mPresenter.shareQQ();
                    break;
                case R.id.btn_space:
                    mPresenter.shareSpace();
                    break;
                case R.id.btn_wechat:
                    mPresenter.shareWeChat();
                    break;
                case R.id.btn_circle:
                    mPresenter.shareWechatMoments();
                    break;
                case R.id.btn_blog:
                    mPresenter.shareBlog();
                    break;
//                case R.id.btn_recommend:
//                    break;
            }
        }
    };

    private void showShareDialog() {
        if(shareDialog == null) {
            shareDialog = new ShareDialog(this,R.style.dialog_setting);

            shareDialog.findViewById(R.id.btn_qq).setOnClickListener(menuClickListsner);
            shareDialog.findViewById(R.id.btn_space).setOnClickListener(menuClickListsner);
            shareDialog.findViewById(R.id.btn_wechat).setOnClickListener(menuClickListsner);
            shareDialog.findViewById(R.id.btn_circle).setOnClickListener(menuClickListsner);
            shareDialog.findViewById(R.id.btn_blog).setOnClickListener(menuClickListsner);

            Window window = shareDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.dialog_animation_down); // 添加动画
            WindowManager windowManager = getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            params = shareDialog.getWindow().getAttributes();
            params.width = (int) (display.getWidth());
            shareDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            shareDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    WindowManager.LayoutParams params = GalleryDetailActivity.this.shareDialog.getWindow().getAttributes();
                    params.dimAmount = 0.0f;
                    GalleryDetailActivity.this.shareDialog.getWindow().setAttributes(params);
                }
            });
        }
        params.dimAmount=0.5f;
        shareDialog.getWindow().setAttributes(params);
        shareDialog.show();
    }

    private RadioGroup.OnCheckedChangeListener checkedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(checkedId==R.id.rb_3d) {
                webView.loadUrl(data.getLink());
            } else if(checkedId==R.id.rb_2d) {
                webView.loadUrl(data.getLink2d());
            }
        }
    };

    /**
     * 事件订阅
     */
    private void subscribeEvent() {
        App.getInstance()
                .getRxBus()
                .subscribeNormalEvent(this, new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        if (o instanceof LoginStateChangeEvent) {
                            // 登陆状态更改
                            mPresenter.loginStateChange((LoginStateChangeEvent) o);
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        App.getInstance()
                .getRxBus()
                .unSubscribe(this);
        super.onDestroy();
    }

}
