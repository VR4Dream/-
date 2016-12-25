package com.weijie.vr4dream.ui.activity;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.RadioGroup;

import com.weijie.vr4dream.App;
import com.weijie.vr4dream.R;
import com.weijie.vr4dream.adapter.FragmentTabAdapter;
import com.weijie.vr4dream.presenter.MainPresenter;
import com.weijie.vr4dream.rxEvent.LoginStateChangeEvent;
import com.weijie.vr4dream.rxEvent.SlidingMenuStatusEvent;
import com.weijie.vr4dream.ui.fragment.gallery.GalleryFragment;
import com.weijie.vr4dream.ui.fragment.idea.IdeaFragment;
import com.weijie.vr4dream.ui.fragment.mine.MineFragment;
import com.weijie.vr4dream.ui.view.IMainView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.functions.Action1;

/**
 * 作者：guoweijie on 16/12/15 21:00
 * 邮箱：529844698@qq.com
 */
public class MainActivity extends BaseActivity<MainPresenter> implements IMainView {

    @Bind(R.id.tabs_rg)
    RadioGroup rgs;

    private List<Fragment> fragments = new ArrayList<Fragment>();

    @Override
    protected void initPresenter() {
        mPresenter = new MainPresenter(mContext, this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initialize() {
        subscribeEvent();

        fragments.add(new GalleryFragment());
        fragments.add(new IdeaFragment());
        fragments.add(new MineFragment());
        new FragmentTabAdapter(this, fragments, R.id.flContent, rgs);
    }

    @OnClick({R.id.tvIdea, R.id.tvMine})
    void onGuideMenuClick(View view) {
        App.getInstance()
                .getRxBus().sendNormalEvent(new SlidingMenuStatusEvent());
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
