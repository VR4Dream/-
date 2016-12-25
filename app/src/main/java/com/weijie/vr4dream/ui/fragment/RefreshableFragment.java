package com.weijie.vr4dream.ui.fragment;

import android.os.Bundle;

import com.weijie.vr4dream.presenter.BaseFragmentPresenter;


public abstract class RefreshableFragment<P extends BaseFragmentPresenter> extends BaseFragment<P>{

	public boolean refreshed; // 是否已刷新过
	public boolean refreshOnActivityCreated; //在onActivityCreated时是否刷新fragment

	@Override
	public void onActivityCreated( Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if(refreshOnActivityCreated){
			refreshOnActivityCreated = false;
			refresh();
		}
	}


	/**
	 * Method invoked when the fragment need to be refreshed
	 */
	public abstract void refresh();


}