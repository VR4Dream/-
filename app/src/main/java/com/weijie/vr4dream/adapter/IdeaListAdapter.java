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
 * 灵感列表适配器
 * 作者：guoweijie on 16/12/20 09:08
 * 邮箱：529844698@qq.com
 */
public class IdeaListAdapter extends RecyclerView.Adapter<IdeaListAdapter.ContentViewHolder> {

    private OnListItemClickListener mItemClickListener;
    private LayoutInflater mLayoutInflater;
    private List<Idea> mDataList;
    private Context mContext;

    public IdeaListAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnListItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void notifyDataSetChanged(List<Idea> dataList) {
        this.mDataList = dataList;
        super.notifyDataSetChanged();
    }

    public void loadMore(List<Idea> dataList) {
        this.mDataList.addAll(dataList);
        super.notifyDataSetChanged();
    }

    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContentViewHolder(mLayoutInflater.inflate(R.layout.item_idea_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ContentViewHolder holder, final int position) {
        if(!mDataList.isEmpty()) {
            final Idea data = mDataList.get(position);
            holder.tvTitle.setText(data.getTitle());
            holder.tvTag.setText(data.getTag());
            holder.tvShow.setText(data.getShowTime()+"");

            holder.tvLikes.setText(data.getLikesNum()+"");
            Glide.with(mContext)
                    .load(data.getCover())
                    .crossFade()
                    .placeholder(R.mipmap.defause_cover)
                    .error(R.mipmap.defause_cover)
                    .into(holder.ivCover);

            if (mItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mItemClickListener.onItemClickListener(v, data);
                    }
                });
            }
        }

    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    static class ContentViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_cover)
        ImageView ivCover;
        @Bind(R.id.tv_title)
        TextView tvTitle;
        @Bind(R.id.tv_tag)
        TextView tvTag;
        @Bind(R.id.tv_show)
        TextView tvShow;
        @Bind(R.id.tv_likes)
        TextView tvLikes;

        public ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}

