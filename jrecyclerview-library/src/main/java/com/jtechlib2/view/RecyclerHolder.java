package com.jtechlib2.view;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 通用viewholder
 */
public class RecyclerHolder extends RecyclerView.ViewHolder {
    private final static int MAX_VIEW_INSTANCE = 20;

    private final SparseArray<View> mViews = new SparseArray<>(MAX_VIEW_INSTANCE);

    public RecyclerHolder(View itemView) {
        super(itemView);
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 获取imageview
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends ImageView> T getImageView(int viewId) {
        return getView(viewId);
    }

    /**
     * 为TextView设置字符串
     *
     * @param viewId
     * @param text
     * @return
     */
    public RecyclerHolder setText(int viewId, CharSequence text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    public RecyclerHolder setImageResource(int viewId, int drawableId) {
        ImageView view = getView(viewId);
        view.setImageResource(drawableId);
        return this;
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param bm
     * @return
     */
    public RecyclerHolder setImageBitmap(int viewId, Bitmap bm) {
        ImageView view = getView(viewId);
        view.setImageBitmap(bm);
        return this;
    }

    /**
     * 设置视图为不可见
     *
     * @param viewId
     * @return
     */
    public RecyclerHolder hideViewGone(int viewId) {
        getView(viewId).setVisibility(View.GONE);
        return this;
    }

    /**
     * 设置视图为不可见（invisible）
     *
     * @param viewId
     * @return
     */
    public RecyclerHolder hideViewInvisible(int viewId) {
        getView(viewId).setVisibility(View.INVISIBLE);
        return this;
    }

    /**
     * 显示视图
     *
     * @param viewId
     * @return
     */
    public RecyclerHolder showView(int viewId) {
        getView(viewId).setVisibility(View.VISIBLE);
        return this;
    }

    /**
     * 设置视图是否显示
     *
     * @param viewId
     * @param isVisible
     * @return
     */
    public RecyclerHolder setViewVisible(int viewId, boolean isVisible) {
        if (isVisible) {
            showView(viewId);
        } else {
            hideViewGone(viewId);
        }
        return this;
    }

    /**
     * 设置视图是否显示
     *
     * @param viewId
     * @param isVisible
     * @return
     */
    public RecyclerHolder setViewVisibleInvisible(int viewId, boolean isVisible) {
        if (isVisible) {
            showView(viewId);
        } else {
            hideViewInvisible(viewId);
        }
        return this;
    }

    /**
     * 设置点击事件
     *
     * @param viewId
     * @param onClickListener
     * @return
     */
    public RecyclerHolder setClickListener(int viewId, View.OnClickListener onClickListener) {
        getView(viewId).setOnClickListener(onClickListener);
        return this;
    }

    /**
     * 设置长点击事件
     *
     * @param viewId
     * @param onLongClickListener
     * @return
     */
    public RecyclerHolder setLongClickListener(int viewId, View.OnLongClickListener onLongClickListener) {
        getView(viewId).setOnLongClickListener(onLongClickListener);
        return this;
    }

    /**
     * 设置是否可用
     *
     * @param viewId
     * @param enabled
     * @return
     */
    public RecyclerHolder setEnabled(int viewId, boolean enabled) {
        getView(viewId).setEnabled(enabled);
        return this;
    }

    /**
     * 设置选择状态
     *
     * @param viewId
     * @param selected
     * @return
     */
    public RecyclerHolder setSelected(int viewId, boolean selected) {
        getView(viewId).setSelected(selected);
        return this;
    }

    /**
     * 设置背景图片
     *
     * @param viewId
     * @param resId
     * @return
     */
    public RecyclerHolder setBackgroundResource(int viewId, int resId) {
        getView(viewId).setBackgroundResource(resId);
        return this;
    }
}