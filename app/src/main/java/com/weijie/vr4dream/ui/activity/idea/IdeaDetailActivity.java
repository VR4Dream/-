package com.weijie.vr4dream.ui.activity.idea;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

import com.bumptech.glide.Glide;
import com.weijie.vr4dream.App;
import com.weijie.vr4dream.R;
import com.weijie.vr4dream.model.Idea;
import com.weijie.vr4dream.model.IdeaComment;
import com.weijie.vr4dream.presenter.idea.IdeaDetailPresenter;
import com.weijie.vr4dream.rxEvent.AddCommentEvent;
import com.weijie.vr4dream.rxEvent.LoginStateChangeEvent;
import com.weijie.vr4dream.ui.activity.BaseActivity;
import com.weijie.vr4dream.ui.view.idea.IIdeaDetailView;
import com.weijie.vr4dream.ui.widget.AlertDialogFragment;
import com.weijie.vr4dream.ui.widget.CircleImageView;
import com.weijie.vr4dream.utils.SpannableUtils;
import com.weijie.vr4dream.utils.StringUtil;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;

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
    @Bind(R.id.lv_comment)
    LinearLayout lvComment;
    @Bind(R.id.tv_comment_num)
    TextView tvCommentNum;

    TextView btnLikes;
    TextView btnFavourite;

    private PopupWindow popupWindow;

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
        initPopupWindow();
        Idea idea = (Idea)getIntent().getSerializableExtra("data");
        mPresenter.setIdea(idea);
        subscribeEvent();
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

    @OnClick({R.id.btn_back, R.id.btn_more, R.id.tv_comment, R.id.tv_checked, R.id.tv_add_comment})
    void onMenuClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_more:
                showPopupdWindow(view);
                break;
            case R.id.tv_comment:
            case R.id.tv_add_comment:
                mPresenter.clickComment();
                break;
            case R.id.tv_checked:
                mPresenter.clickCheckAllComment();
                break;
        }
    }

    @Override
    public void loadWeb(String link) {
        tvComment.setText(SpannableUtils.setTextUnderlineForeground("智慧如你，快来评论吧！", 5, 7, Color.parseColor("#34b895")));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }
        webView.setWebChromeClient(client);
        webView.setWebViewClient(webViewClient);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(link);
    }

    @Override
    public void setLikesStatus(boolean status) {
        btnLikes.setSelected(status);
    }

    @Override
    public void setFavourite(boolean status) {
        btnFavourite.setSelected(status);
    }

    private View.OnClickListener menListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_likes:
                    mPresenter.clickLikes();
                    hidePopupWindow();
                    break;
                case R.id.btn_favourite:
                    mPresenter.clickFavourite();
                    hidePopupWindow();
                    break;
                case R.id.btn_comment:
                    mPresenter.clickComment();
                    hidePopupWindow();
                    break;
                case R.id.btn_share:
                    hidePopupWindow();
                    break;
            }
        }
    };

    @Override
    public void setCommentNum(int num) {
        tvCommentNum.setText("全部评论(" + num + ")");
    }

    private void initPopupWindow() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.detail_popup_window, null);
        popupWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT, true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());

        btnLikes = (TextView)view.findViewById(R.id.btn_likes);
        btnFavourite = (TextView)view.findViewById(R.id.btn_favourite);
        mPresenter.checkedInitStatus();
        view.findViewById(R.id.btn_likes).setOnClickListener(menListener);
        view.findViewById(R.id.btn_favourite).setOnClickListener(menListener);
        view.findViewById(R.id.btn_comment).setOnClickListener(menListener);
        view.findViewById(R.id.btn_share).setOnClickListener(menListener);
    }

    private void showPopupdWindow(View v) {
        if(popupWindow==null) {
            initPopupWindow();
        }
        popupWindow.showAsDropDown(v);
    }

    private void hidePopupWindow() {
        if(popupWindow!=null) {
            popupWindow.dismiss();
        }
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

    @Override
    public void createComment(IdeaComment comment) {
        tvComment.setVisibility(View.GONE);
        LayoutInflater inflater = getLayoutInflater();
        View item = inflater.inflate(R.layout.item_comment, null, false);
        lvComment.addView(item);
        setCommentInfo(item, comment);
    }

    private void setCommentInfo(View view, IdeaComment comment) {
        TextView name = (TextView)view.findViewById(R.id.tv_name);
        TextView date = (TextView)view.findViewById(R.id.tv_date);
        TextView content = (TextView)view.findViewById(R.id.tv_content);
        CircleImageView icon = (CircleImageView)view.findViewById(R.id.iv_icon);
        String sName = comment.getAuthor().getUsername();
        if(StringUtil.validateMobile(sName)) {
            sName = sName.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
        }
        name.setText(sName);
        date.setText(comment.getCreatedAt());
        content.setText(comment.getContent());
        Glide.with(mContext)
                .load(comment.getAuthor().getIcon().getUrl())
                //.load("http://bmob-cdn-8496.b0.upaiyun.com/2016/12/30/7ec4915040a639f1804c6ce0b54b832b.jpeg")
                .crossFade()
                .placeholder(R.mipmap.user_pic)
                .error(R.mipmap.user_pic)
                .into(icon);
    }

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
                        } else if(o instanceof AddCommentEvent) {
                            // 登陆状态更改
                            mPresenter.addComment((AddCommentEvent) o);
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
