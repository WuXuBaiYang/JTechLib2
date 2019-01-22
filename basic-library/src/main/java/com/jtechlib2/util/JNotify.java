package com.jtechlib2.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

/**
 * 通知栏消息-基于{@link NotificationCompat.Builder}
 * Created by JTech on 2017/1/11.
 */

public class JNotify {
    private static final int REQUEST_CODE_NOTIFY = 818;
    private static final int DEFAULT_LIGHT_COLOR = 0xFFFF0000;
    private static final int DEFAULT_LIGHT_ON = 350;
    private static final int DEFAULT_LIGHT_OFF = 300;
    private static final int DEFAULT_VIBRATE_ON = 200;
    private static final int DEFAULT_VIBRATE_OFF = 100;

    /**
     * 构造
     *
     * @param context context
     * @return builder
     */
    public static NotifyBuilder build(Context context) {
        return new NotifyBuilder(context);
    }

    /**
     * 构造
     *
     * @param context context
     * @param builder builder
     * @return 返回一个builder
     */
    public static NotifyBuilder build(Context context, NotificationCompat.Builder builder) {
        return new NotifyBuilder(context, builder);
    }

    /**
     * 构建通知栏消息
     */
    public static class NotifyBuilder {
        private NotificationManager notificationManager;
        private NotificationCompat.Builder builder;
        private Context context;

        public NotifyBuilder(Context context) {
            this(context, new NotificationCompat.Builder(context));
        }

        public NotifyBuilder(Context context, NotificationCompat.Builder builder) {
            this.context = context;
            //得到builder
            this.builder = builder;
            //得到通知栏消息服务管理
            this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        public NotifyBuilder setAutoCancel(boolean autoCancel) {
            builder.setAutoCancel(autoCancel);
            return this;
        }

        public NotifyBuilder setOngoing(boolean ongoing) {
            builder.setOngoing(ongoing);
            return this;
        }

        public NotifyBuilder setOnlyAlertOnce(boolean onlyAlertOnce) {
            builder.setOnlyAlertOnce(onlyAlertOnce);
            return this;
        }

        public NotifyBuilder setNumber(int number) {
            builder.setNumber(number);
            return this;
        }

        public NotifyBuilder setTicker(String tickerText) {
            builder.setTicker(tickerText);
            return this;
        }

        public NotifyBuilder setContentTitle(String title) {
            builder.setContentTitle(title);
            return this;
        }

        public NotifyBuilder setContentText(String text) {
            builder.setContentText(text);
            return this;
        }

        public NotifyBuilder setContentInfo(String info) {
            builder.setContentInfo(info);
            return this;
        }

        public NotifyBuilder setContent(RemoteViews views) {
            builder.setContent(views);
            return this;
        }

        public NotifyBuilder setCustomBigContentView(RemoteViews contentView) {
            builder.setCustomBigContentView(contentView);
            return this;
        }

        public NotifyBuilder setCustomContentView(RemoteViews contentView) {
            builder.setCustomContentView(contentView);
            return this;
        }

        public NotifyBuilder setCustomHeadsUpContentView(RemoteViews contentView) {
            builder.setCustomHeadsUpContentView(contentView);
            return this;
        }


        public NotifyBuilder setSmallIcon(int icon) {
            builder.setSmallIcon(icon);
            return this;
        }

        public NotifyBuilder setLargeIcon(Bitmap icon) {
            builder.setLargeIcon(icon);
            return this;
        }

        public NotifyBuilder setProgress(int max, int progress, boolean indeterminate) {
            builder.setProgress(max, progress, indeterminate);
            return this;
        }

        public NotifyBuilder setDefaultSound() {
            setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
            return this;
        }

        public NotifyBuilder setSound(Uri ringUri) {
            builder.setSound(ringUri);
            return this;
        }

        public NotifyBuilder setDefaultLight() {
            setLight(DEFAULT_LIGHT_COLOR, DEFAULT_LIGHT_ON, DEFAULT_LIGHT_OFF);
            return this;
        }

        public NotifyBuilder setLight(@ColorInt int argb, int onMs, int offMs) {
            builder.setLights(argb, onMs, offMs);
            return this;
        }

        public NotifyBuilder setDefaultVibrate() {
            setVibrate(new long[]{DEFAULT_VIBRATE_ON, DEFAULT_VIBRATE_OFF, DEFAULT_VIBRATE_ON, DEFAULT_VIBRATE_OFF});
            return this;
        }

        public NotifyBuilder setVibrate(long[] pattern) {
            builder.setVibrate(pattern);
            return this;
        }

        public NotifyBuilder setContentIntent(@NonNull Intent intent) {
            return setContentIntent(intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }

        public NotifyBuilder setContentIntents(@NonNull Intent[] intents) {
            return setContentIntents(intents, PendingIntent.FLAG_UPDATE_CURRENT);
        }

        public NotifyBuilder setContentIntent(@NonNull Intent intent, int flags) {
            return setContentIntent(REQUEST_CODE_NOTIFY, intent, flags, null);
        }

        public NotifyBuilder setContentIntents(@NonNull Intent[] intents, int flags) {
            return setContentIntents(REQUEST_CODE_NOTIFY, intents, flags, null);
        }

        public NotifyBuilder setContentIntent(int requestCode, @NonNull Intent intent, int flags, @Nullable Bundle options) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                return setContentIntent(PendingIntent.getActivity(context, requestCode, intent, flags, options));
            }
            return setContentIntent(PendingIntent.getActivity(context, requestCode, intent, flags));
        }

        public NotifyBuilder setContentIntents(int requestCode, @NonNull Intent[] intents, int flags, @Nullable Bundle options) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                return setContentIntent(PendingIntent.getActivities(context, requestCode, intents, flags, options));
            }
            return setContentIntent(PendingIntent.getActivities(context, requestCode, intents, flags));
        }

        public NotifyBuilder setContentIntent(PendingIntent pendingIntent) {
            builder.setContentIntent(pendingIntent);
            return this;
        }

        public void cancel(int id) {
            cancel(null, id);
        }

        public void cancel(String tag, int id) {
            notificationManager.cancel(tag, id);
        }

        public void notify(int id) {
            notify(null, id);
        }

        public void notify(String tag, int id) {
            notificationManager.notify(tag, id, builder.build());
        }
    }
}