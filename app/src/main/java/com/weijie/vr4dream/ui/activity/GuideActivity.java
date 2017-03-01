package com.weijie.vr4dream.ui.activity;

import android.view.View;
import android.widget.ImageView;

import com.weijie.vr4dream.R;
import com.weijie.vr4dream.presenter.GuidePresenter;
import com.weijie.vr4dream.ui.view.IGuideView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 引导页
 * 作者：guoweijie on 17/2/9 15:34
 * 邮箱：529844698@qq.com
 */
public class GuideActivity extends BaseActivity<GuidePresenter> implements IGuideView {

    private int[] res;
    private int index = 0;

    @Bind(R.id.iv_guide)
    ImageView ivGuide;

    @Override
    protected void initPresenter() {
        mPresenter = new GuidePresenter(mContext, this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initialize() {
        res = new int[] {R.mipmap.guide1, R.mipmap.guide3, R.mipmap.guide4, R.mipmap.guide5, R.mipmap.guide6, R.mipmap.guide7, R.mipmap.guide8, R.mipmap.guide9, R.mipmap.guide10, R.mipmap.guide11};
        ivGuide.setImageResource(res[0]);
        index++;
    }

    @OnClick(R.id.iv_guide)
    void onImgClick(View view) {
        if(index < res.length) {
            ivGuide.setImageResource(res[index]);
            index++;
        } else {
            finish();
        }
    }

}
