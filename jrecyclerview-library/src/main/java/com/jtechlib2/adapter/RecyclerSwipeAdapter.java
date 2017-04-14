package com.jtechlib2.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.jtechlib2.view.RecyclerHolder;


/**
 * 滑动操作适配器
 * Created by Administrator on 2016-10-05.
 */

public abstract class RecyclerSwipeAdapter<D> extends RecyclerAdapter<D> {

    /**
     * 主构造
     *
     * @param activity Activity对象
     */
    @Deprecated
    public RecyclerSwipeAdapter(Activity activity) {
        super(activity);
    }

    /**
     * 主构造
     *
     * @param context
     */
    public RecyclerSwipeAdapter(Context context) {
        super(context);
    }

    /**
     * 重置视图
     *
     * @param recyclerHolder
     */
    public abstract void clearView(RecyclerHolder recyclerHolder);

    /**
     * 获取滑动视图
     *
     * @param recyclerHolder
     * @return
     */
    public abstract View getSwipeView(RecyclerHolder recyclerHolder);

    /**
     * 滑动视图调用方法
     *
     * @param recyclerHolder
     * @param direction
     */
    public abstract void onSwipe(RecyclerHolder recyclerHolder, int direction, float dx);

    /**
     * 从开始处滑动
     *
     * @param recyclerHolder
     */
    public void onSwipeStart(RecyclerHolder recyclerHolder, float dx) {

    }

    /**
     * 从结束处滑动
     *
     * @param recyclerHolder
     */
    public void onSwipeEnd(RecyclerHolder recyclerHolder, float dx) {

    }
}