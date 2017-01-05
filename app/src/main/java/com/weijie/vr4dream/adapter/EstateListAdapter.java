package com.weijie.vr4dream.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.weijie.vr4dream.R;
import com.weijie.vr4dream.model.BuildingEstate;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 楼盘列表适配器
 * 作者：guoweijie on 16/12/20 09:08
 * 邮箱：529844698@qq.com
 */
public class EstateListAdapter extends RecyclerView.Adapter<EstateListAdapter.ContentViewHolder> {

    private OnListItemClickListener mItemClickListener;
    private LayoutInflater mLayoutInflater;
    private List<BuildingEstate> mDataList;
    private Context mContext;

    public EstateListAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnListItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public void notifyDataSetChanged(List<BuildingEstate> dataList) {
        this.mDataList = dataList;
        super.notifyDataSetChanged();
    }

    public void loadMore(List<BuildingEstate> dataList) {
        this.mDataList.addAll(dataList);
        super.notifyDataSetChanged();
    }


    @Override
    public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContentViewHolder(mLayoutInflater.inflate(R.layout.item_estate_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ContentViewHolder holder, final int position) {
        if(!mDataList.isEmpty()) {
            final BuildingEstate data = mDataList.get(position);
            holder.tvName.setText(data.getName());
            holder.tvTips.setText(data.getProvince() + " " + data.getCity() + " " + data.getDistrict());
            //java.util.Random random=new java.util.Random();// 定义随机类
            //int result=random.nextInt(10)+1;// 返回[0,10)集合中的整数，注意不包括10
            switch (position % 5) {
                case 1:
                    holder.ivIcon.setImageResource(R.mipmap.loupan_pic1);
                    break;
                case 2:
                    holder.ivIcon.setImageResource(R.mipmap.loupan_pic2);
                    break;
                case 3:
                    holder.ivIcon.setImageResource(R.mipmap.loupan_pic3);
                    break;
                case 4:
                    holder.ivIcon.setImageResource(R.mipmap.loupan_pic4);
                    break;
                case 5:
                    holder.ivIcon.setImageResource(R.mipmap.loupan_pic5);
                    break;
            }
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

        @Bind(R.id.iv_icon)
        ImageView ivIcon;
        @Bind(R.id.tv_name)
        TextView tvName;
        @Bind(R.id.tv_tips)
        TextView tvTips;

        public ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

}

