package com.jtechlib2.util;

import org.greenrobot.eventbus.EventBus;

/**
 * 老司机-消息总线
 * Created by JTech on 2017/1/7.
 */

public class Bus extends EventBus {
    private static Bus INSTANCE;

    /**
     * 获取车次
     *
     * @return eventbus对象
     */
    public static EventBus get() {
        synchronized (Bus.class) {
            if (null == INSTANCE) {
                INSTANCE = new Bus();
            }
        }
        return INSTANCE;
    }

    /**
     * 上车-老司机带带我
     *
     * @param subscriber 监听者
     */
    public static void getOn(Object subscriber) {
        get().register(subscriber);
    }

    /**
     * 下车
     *
     * @param subscriber 监听者
     */
    public static void getOff(Object subscriber) {
        get().unregister(subscriber);
    }

    /**
     * 是否上车了
     *
     * @param subscriber 监听者
     * @return 是否已监听
     */
    public static boolean inBus(Object subscriber) {
        return get().isRegistered(subscriber);
    }
}