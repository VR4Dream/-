package com.weijie.vr4dream.presenter.user;

import com.weijie.vr4dream.presenter.IBasePresenter;

/**
 * 意见反馈
 * 作者：guoweijie on 16/12/27 11:03
 * 邮箱：529844698@qq.com
 */
public interface ISuggestPresenter extends IBasePresenter {

    /**
     * 提交意见
     * @param content 文本内容
     */
    void clickCommit(String content);

}
