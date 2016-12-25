package com.weijie.vr4dream.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weijie.vr4dream.R;

import java.util.List;

/**
 * 全景列表适配器
 * 作者：guoweijie on 16/12/20 09:08
 * 邮箱：529844698@qq.com
 */
public class VRListAdapter extends RecyclerView.Adapter<VRListAdapter.ContentViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<String> mDataList;

    public VRListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void notifyDataSetChanged(List<String> dataList) {
        this.mDataList = dataList;
        super.notifyDataSetChanged();
    }

    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContentViewHolder(mLayoutInflater.inflate(R.layout.item_vr_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ContentViewHolder holder, int position) {
        holder.setData(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    static class ContentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTvTitle;

        public ContentViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }

        public void setData(String data) {
            mTvTitle.setText(data);
        }

        @Override
        public void onClick(View v) {
        }
    }

}

