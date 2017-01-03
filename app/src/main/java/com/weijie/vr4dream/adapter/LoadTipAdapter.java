package com.weijie.vr4dream.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.weijie.vr4dream.R;
import com.weijie.vr4dream.model.Idea;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 加载提示语适配器
 * 作者：guoweijie on 16/12/20 09:08
 * 邮箱：529844698@qq.com
 */
public class LoadTipAdapter extends RecyclerView.Adapter<LoadTipAdapter.ContentViewHolder> {

    private ViewStatus viewStatus;

    public enum ViewStatus {
        // 加载
        STATUS_LOADING,
        // 错误
        STATUS_ERR,
        // 成功
        STATUS_SUCCESS,
        // 空
        STATUS_EMPTY
    }

    private LayoutInflater mLayoutInflater;

    public LoadTipAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void notifyDataSetChanged(ViewStatus viewStatus) {
        this.viewStatus = viewStatus;
        super.notifyDataSetChanged();
    }

    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContentViewHolder(mLayoutInflater.inflate(R.layout.empty, parent, false));
    }

    @Override
    public void onBindViewHolder(ContentViewHolder holder, final int position) {
        switch (viewStatus) {
            case STATUS_EMPTY:
                holder.tvTip.setText("(b_d) 好像什么都没有～");
                break;
            case STATUS_LOADING:
                holder.tvTip.setText("正在加载");
                break;
            case STATUS_ERR:
                holder.tvTip.setText("网络出现异常，请检查一下");
                break;
            case STATUS_SUCCESS:
                holder.tvTip.setText("Success");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    static class ContentViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.tv_tip)
        TextView tvTip;

        public ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}

