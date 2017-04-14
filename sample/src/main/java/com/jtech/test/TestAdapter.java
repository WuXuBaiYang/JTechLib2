package com.jtech.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jtechlib2.adapter.RecyclerAdapter;
import com.jtechlib2.view.RecyclerHolder;

/**
 * Created by hasee on 2017/4/14.
 */

public class TestAdapter extends RecyclerAdapter<String> {
    public TestAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    @Override
    protected View createView(LayoutInflater layoutInflater, ViewGroup viewGroup, int viewType) {
        TextView textView = new TextView(getContext());
        textView.setId(0x0000);
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return textView;
    }

    @Override
    protected void convert(RecyclerHolder holder, String model, int position) {
        holder.setText(0x0000, "当前第" + position + "行");
    }
}