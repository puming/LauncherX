package com.android.launcherx.left;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by puming on 2017/1/3.
 */

public class LeftAdapter extends RecyclerView.Adapter<LeftAdapter.LeftViewHolder> {
    private Context mContext;
    private ArrayList<Bean> mArrayList;


    public LeftAdapter(Context mContext ,ArrayList<Bean> arrayList ) {
        this.mContext = mContext;
        this.mArrayList= arrayList;
    }

    public void setData(ArrayList<Bean> arrayList) {
        this.mArrayList = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public LeftViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_left, parent,false);
                return new LeftViewHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(LeftViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case 0:
                Bean bean = mArrayList.get(position);
                holder.mTextViewTitle.setText(bean.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return mArrayList.size() == 0 ? 0 : mArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    static class LeftViewHolder extends RecyclerView.ViewHolder {
        protected TextView mTextViewTitle;
        protected ImageView mImageViewIcon;

        public LeftViewHolder(View itemView) {
            super(itemView);
            mTextViewTitle = (TextView) itemView.findViewById(R.id.tv_title);
            mImageViewIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
        }
    }
}
