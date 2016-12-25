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
 * 灵感列表适配器
 * 作者：guoweijie on 16/12/20 09:08
 * 邮箱：529844698@qq.com
 */
public class IdeaListAdapter extends RecyclerView.Adapter<IdeaListAdapter.ContentViewHolder> {

    private OnListItemClickListener mItemClickListener;
    private LayoutInflater mLayoutInflater;
    private List<String> mDataList;

    public IdeaListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnListItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void notifyDataSetChanged(List<String> dataList) {
        this.mDataList = dataList;
        super.notifyDataSetChanged();
    }

    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContentViewHolder(mLayoutInflater.inflate(R.layout.item_idea_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ContentViewHolder holder, final int position) {
        holder.setData(mDataList.get(position));

        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClickListener(v, position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    static class ContentViewHolder extends RecyclerView.ViewHolder {

        TextView mTvTitle;

        public ContentViewHolder(View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }

        public void setData(String data) {
            //mTvTitle.setText(data);
        }
    }

}

