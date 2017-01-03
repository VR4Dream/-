package com.weijie.vr4dream.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.weijie.vr4dream.ui.fragment.RefreshableFragment;
import com.weijie.vr4dream.ui.fragment.idea.IdeaListFragment;

import java.util.ArrayList;

/**
 * ViewPager-Fragment 适配器
 * 作者：guoweijie on 16/12/20 16:48
 * 邮箱：529844698@qq.com
 */
public class PagerAdapter extends FragmentPagerAdapter {

    private ArrayList<RefreshableFragment> mFragments;

    public PagerAdapter(FragmentManager fragmentManager,String[] tabs) {
        super(fragmentManager);
        this.mFragments = new ArrayList<>();
        if(tabs.length>1) {
            IdeaListFragment fragment = new IdeaListFragment();
            fragment.refreshOnActivityCreated = true;
            Bundle bundle = new Bundle();
            bundle.putInt("type", 1);
            fragment.setArguments(bundle);
            mFragments.add(fragment);
        }
        for(int i = 1; i < tabs.length; i++) {
            IdeaListFragment fragment = new IdeaListFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", i+1);
            fragment.setArguments(bundle);
            mFragments.add(fragment);
        }
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    public void refresh(int index){
        RefreshableFragment fragment = mFragments.get(index);
        if(fragment != null && !fragment.refreshed){
            fragment.refresh();
        }
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;//返回这个表示该对象已改变,需要刷新
    }

}
