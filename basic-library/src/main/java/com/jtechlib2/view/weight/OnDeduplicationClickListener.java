package com.jtechlib2.view.weight;

import android.os.CountDownTimer;
import android.view.View;

/**
 * 去重点击事件
 * Created by JTech on 2018/1/9.
 */

public abstract class OnDeduplicationClickListener implements View.OnClickListener {
    private final static int countDownInterval = 1000;

    private int millisInFuture;

    /**
     * 主构造
     *
     * @param seconds 秒数必须大于0
     */
    public OnDeduplicationClickListener(int seconds) {
        this.millisInFuture = seconds * countDownInterval;
    }

    @Override
    public void onClick(final View view) {
        onClick();
        view.setEnabled(false);
        new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                OnDeduplicationClickListener.this.onTick(millisUntilFinished / countDownInterval);
            }

            @Override
            public void onFinish() {
                OnDeduplicationClickListener.this.onFinish();
                view.setEnabled(true);
            }
        }.start();
    }

    /**
     * 唯一需要实现的点击事件
     */
    public abstract void onClick();

    /**
     * 倒计时，返回秒数
     *
     * @param seconds
     */
    public void onTick(long seconds) {

    }

    /**
     * 倒计时结束
     */
    public void onFinish() {

    }
}