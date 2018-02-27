package com.jtechlib2.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jtechlib2.util.Bus;

import butterknife.ButterKnife;

/**
 * Fragment基类
 * Created by wuxubaiyang on 16/4/16.
 */
public abstract class BaseFragment extends Fragment {
    public static String TAG = BaseFragment.class.getSimpleName();
    private View contentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == contentView) {
            //赋值TAG
            TAG = this.getClass().getSimpleName();
            //创建视图
            contentView = createView(inflater, container);
            //绑定注解框架
            ButterKnife.bind(this, contentView);
            //初始化变量(用户传递进来的参数)
            initVariables(getArguments());
            //初始化视图
            initViews(savedInstanceState);
            //加载数据
            loadData();
        }
        //返回视图
        return contentView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 创建视图
     *
     * @param inflater  inflater
     * @param container container
     * @return 视图
     */
    public abstract View createView(LayoutInflater inflater, ViewGroup container);

    /**
     * 初始化变量
     *
     * @param bundle bundle
     */
    protected abstract void initVariables(Bundle bundle);

    /**
     * 初始化视图
     *
     * @param savedInstanceState savedInstanceState
     */
    protected abstract void initViews(Bundle savedInstanceState);

    /**
     * 请求数据
     */
    protected abstract void loadData();

    /**
     * 上车
     */
    public void getOnBus() {
        Bus.getOn(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //判断是否需要下车
        if (Bus.inBus(this)) {
            Bus.getOff(this);
        }
    }
}