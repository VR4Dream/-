package com.weijie.vr4dream.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.weijie.vr4dream.R;
import com.weijie.vr4dream.model.IdeaComment;
import com.weijie.vr4dream.ui.widget.CircleImageView;
import com.weijie.vr4dream.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 评论列表适配器
 * 作者：guoweijie on 16/12/20 09:08
 * 邮箱：529844698@qq.com
 */
public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ContentViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<IdeaComment> mDataList;
    private Context mContext;

    public CommentListAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void notifyDataSetChanged(List<IdeaComment> dataList) {
        if(dataList==null) {
            mDataList = new ArrayList<>();
        } else
            this.mDataList = dataList;
        super.notifyDataSetChanged();
    }

    public void loadMore(List<IdeaComment> dataList) {
        this.mDataList.addAll(dataList);
        super.notifyDataSetChanged();
    }

    public void addTop(IdeaComment comment) {
        if(mDataList==null) {
            mDataList = new ArrayList<>();
        }
        this.mDataList.add(0, comment);
        super.notifyDataSetChanged();
    }

    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContentViewHolder(mLayoutInflater.inflate(R.layout.item_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(ContentViewHolder holder, final int position) {
        if(!mDataList.isEmpty()) {
            final IdeaComment data = mDataList.get(position);
            String sName = data.getAuthor().getUsername();
            if(StringUtil.validateMobile(sName)) {
                sName = sName.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
            }
            holder.tvName.setText(sName);
            holder.tvDate.setText(data.getCreatedAt());
            holder.tvContent.setText(data.getContent());
            Glide.with(mContext)
                    .load(data.getAuthor().getIcon().getUrl())
                    .crossFade()
                    .placeholder(R.mipmap.user_pic)
                    .error(R.mipmap.user_pic)
                    .into(holder.ivIcon);
        }

    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    static class ContentViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_icon)
        CircleImageView ivIcon;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_date)
        TextView tvDate;
        @Bind(R.id.tv_content)
        TextView tvContent;

        public ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}

