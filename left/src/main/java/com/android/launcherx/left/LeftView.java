package com.android.launcherx.left;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toolbar;

import java.util.ArrayList;

/**
 * Created by puming on 2016/12/12.
 */

public class LeftView extends LinearLayout {
    private static final String TAG = "LeftView";

    private LinearLayout mView;
    private RecyclerView mRecyclerView;
    private ListView mListView;
    private ArrayList<Bean> mArrayList;
    private Context mContext;


    public LeftView(Context context) {
        super(context);
        this.mContext = context;
        initView(context);
    }

    public LeftView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initView(context);
    }

    public LeftView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView(context);
    }

    public LeftView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext = context;
        initView(context);
    }


    private void initView(Context context) {
        mView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.left_screen_layout, null, false);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.left_recycler_view);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER_VERTICAL);
        layoutParams.setMargins(50,DensityUtil.getStatusBarH(context),50,0);
        addView(mView, layoutParams);
    }

    public void bindRecyclerView() {
        initData();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        LeftAdapter adapter = new LeftAdapter(mContext,mArrayList);
        mRecyclerView.setAdapter(adapter);
    }

    private void initData() {
        mArrayList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Bean bean = new Bean();
            bean.setTitle("Hello LeftScree");
            mArrayList.add(bean);
        }
    }

    public void unbindRecyclerView() {

    }
}
