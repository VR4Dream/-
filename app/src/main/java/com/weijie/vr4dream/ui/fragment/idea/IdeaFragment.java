package com.weijie.vr4dream.ui.fragment.idea;

import android.support.v4.view.ViewPager;

import com.weijie.vr4dream.R;
import com.weijie.vr4dream.adapter.PagerAdapter;
import com.weijie.vr4dream.presenter.idea.IdeaPresenter;
import com.weijie.vr4dream.ui.fragment.BaseFragment;
import com.weijie.vr4dream.ui.view.idea.IIdeaView;
import com.weijie.vr4dream.ui.widget.GuideTabView;

import butterknife.Bind;

/**
 * 灵感fragment
 * 作者：guoweijie on 16/12/16 09:31
 * 邮箱：529844698@qq.com
 */
public class IdeaFragment extends BaseFragment<IdeaPresenter> implements IIdeaView, GuideTabView.OnTabSeletedListener {

    @Bind(R.id.guide_tab)
    GuideTabView guideTabView;
    @Bind(R.id.viewPager)
    ViewPager viewPager;

    private PagerAdapter pagerAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_idea;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new IdeaPresenter(mContext, this);
    }

    @Override
    protected void initialize() {
        String[] tabs = getResources().getStringArray(R.array.tab_names);
        guideTabView.setTabs(tabs);
        guideTabView.setUp(viewPager);
        guideTabView.setOnTabSeletedListener(this);
        pagerAdapter = new PagerAdapter(getFragmentManager(),tabs);
        //一次性载入所有Pager，注意控制数据载入的时机
        viewPager.setOffscreenPageLimit(pagerAdapter.getCount());
        viewPager.setAdapter(pagerAdapter);
        guideTabView.setCurrentSelectTabIndex(0);
    }

    @Override
    public void onTabSeleted(int index) {
        pagerAdapter.refresh(index);
    }
}
