package com.jtech.test;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.ImageView;

import com.jtechlib2.view.JRecyclerView;
import com.jtechlib2.view.activity.BaseActivity;

import butterknife.Bind;

/**
 * Created by hasee on 2017/4/14.
 */

public class TestActivity extends BaseActivity {
    @Bind(R.id.jrecyclerview_test)
    JRecyclerView jRecyclerView;
    @Bind(R.id.imageview)
    ImageView imageView;

    @Override
    protected void initVariables(Bundle bundle) {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_test);
        //设置layoutmanager
        jRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置适配器
        jRecyclerView.setAdapter(new TestAdapter(getActivity()));
    }

    @Override
    protected void loadData() {

    }
}