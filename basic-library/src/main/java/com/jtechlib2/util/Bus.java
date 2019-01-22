package com.jtechlib2.util;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Method;

/**
 * 老司机-消息总线
 * Created by JTech on 2017/1/7.
 */
public class Bus extends EventBus {
    private static Bus INSTANCE;

    private Bus() {
    }

    /**
     * 获取车次
     *
     * @return eventbus对象
     */
    public static Bus get() {
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
     * 在基类中注册eventbus
     *
     * @param subscriber
     */
    public static void getOnWithBase(Object subscriber) {
        for (Method method : subscriber
                .getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Subscribe.class)) {
                getOn(subscriber);
                break;
            }
        }
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
     * 基类中销毁的方法
     *
     * @param subscriber
     */
    public static void getOffWithBase(Object subscriber) {
        if (inBus(subscriber)) {
            getOff(subscriber);
        }
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