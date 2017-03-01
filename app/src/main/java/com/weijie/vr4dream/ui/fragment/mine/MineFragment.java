package com.weijie.vr4dream.ui.fragment.mine;

import android.content.DialogInterface;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.weijie.vr4dream.App;
import com.weijie.vr4dream.R;
import com.weijie.vr4dream.presenter.mine.MinePresenter;
import com.weijie.vr4dream.rxEvent.FavouriteChangeEvent;
import com.weijie.vr4dream.rxEvent.LoginStateChangeEvent;
import com.weijie.vr4dream.ui.fragment.BaseFragment;
import com.weijie.vr4dream.ui.view.mine.IMineView;
import com.weijie.vr4dream.ui.widget.AlertDialogFragment;
import com.weijie.vr4dream.ui.widget.ShareAppDialog;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * 我的页面fragment
 * 作者：guoweijie on 16/12/16 09:31
 * 邮箱：529844698@qq.com
 */
public class MineFragment extends BaseFragment<MinePresenter> implements IMineView {

    @Bind(R.id.tv_name)
    TextView tvUserName;
    @Bind(R.id.tv_idea_fav_num)
    TextView tvIdeaNum;
    @Bind(R.id.tv_gallery_fav_num)
    TextView tvGalleryNum;

    private ShareAppDialog dialog;
    private WindowManager.LayoutParams params;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new MinePresenter(mContext, this);
    }

    @Override
    protected void initialize() {
        mPresenter.initInfo();
        subscribeEvent();
    }

    @Override
    public void setUserName(String userName) {
        tvUserName.setText(userName);
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
                mPresenter.clickHeadIcon();
            }
        });
        dialogFragment.show(getFragmentManager(), "loginTip");
    }

    @OnClick({R.id.ivHeadIcon, R.id.tv_setting, R.id.tv_info, R.id.tv_gallery_fav, R.id.tv_idea_fav, R.id.tv_suggest, R.id.tv_share})
    void onTextMenuClick(View view) {
        switch (view.getId()) {
            case R.id.ivHeadIcon:
                mPresenter.clickHeadIcon();
                break;
            case R.id.tv_info:
                mPresenter.clickPersonalData();
                break;
            case R.id.tv_gallery_fav:
                mPresenter.clickGalleryFavourite();
                break;
            case R.id.tv_idea_fav:
                mPresenter.clickIdeaFavourite();
                break;
            case R.id.tv_setting:
                mPresenter.clickSetting();
                break;
            case R.id.tv_suggest:
                mPresenter.clickSuggest();
                break;
            case R.id.tv_share:
                mPresenter.clickShare();
                break;

        }
    }

    @Override
    public void setIdeaNum(int num) {
        tvIdeaNum.setText(num + "");
    }

    @Override
    public void setGalleryNum(int num) {
        tvGalleryNum.setText(num+"");
    }

    @Override
    public void showShareDialog() {
        if(dialog == null) {
            dialog = new ShareAppDialog(mContext,R.style.dialog_setting);

            dialog.findViewById(R.id.btn_blog).setOnClickListener(menListener);
            dialog.findViewById(R.id.btn_space).setOnClickListener(menListener);
            dialog.findViewById(R.id.btn_wechat).setOnClickListener(menListener);
            dialog.findViewById(R.id.btn_circle).setOnClickListener(menListener);
            dialog.findViewById(R.id.btn_qq).setOnClickListener(menListener);
            dialog.findViewById(R.id.btn_cancle).setOnClickListener(menListener);
            dialog.setCancelable(false);
            Window window = dialog.getWindow();
            window.setGravity(Gravity.CENTER);
            window.setWindowAnimations(R.style.dialog_animation_center); // 添加动画
            WindowManager windowManager = getActivity().getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            params = dialog.getWindow().getAttributes();
            params.width = (int) (display.getWidth());
            dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    WindowManager.LayoutParams params = MineFragment.this.dialog.getWindow().getAttributes();
                    params.dimAmount = 0.0f;
                    MineFragment.this.dialog.getWindow().setAttributes(params);
                }
            });
        }
        params.dimAmount=0.5f;
        dialog.getWindow().setAttributes(params);
        dialog.show();
    }

    private void hideShareDialog() {
        if(dialog != null)
            dialog.hide();
    }

    private View.OnClickListener menListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_blog:
                    mPresenter.shareBlog();
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
                case R.id.btn_qq:
                    mPresenter.shareQQ();
                    break;
                case R.id.btn_cancle:
                    hideShareDialog();
                    break;
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
                        } else if(o instanceof FavouriteChangeEvent) {
                            //收藏数改变
                            FavouriteChangeEvent event = (FavouriteChangeEvent)o;
                            if(event.isTag()) {
                                int num = Integer.parseInt(tvGalleryNum.getText().toString());
                                tvGalleryNum.setText(event.isActive() ? num+1+"" : num-1+"");
                            } else {
                                int num = Integer.parseInt(tvIdeaNum.getText().toString());
                                tvIdeaNum.setText(event.isActive() ? num+1+"" : num-1+"");
                            }
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        App.getInstance()
                .getRxBus()
                .unSubscribe(this);
        super.onDestroy();
    }
}
