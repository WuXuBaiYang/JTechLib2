package com.jtechlib2.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.widget.ImageView;

import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import java.security.MessageDigest;

/**
 * 图片通用类
 * Created by wuxubaiyang on 16/4/18.
 */
public class ImageUtils {

    /**
     * 显示圆形图片
     *
     * @param context   context
     * @param object    图片地址
     * @param imageView 图片容器
     * @param <T>       泛型
     */
    public static <T extends ImageView> void showCircleImage(Context context, Object object, T imageView) {
        showCircleImage(context, object, imageView, 0, 0);
    }

    /**
     * 显示圆形图片
     *
     * @param context          context
     * @param object           图片地址
     * @param imageView        图片容器
     * @param errorResId       错误图片
     * @param placeholderResId 占位图
     * @param <T>              泛型
     */
    public static <T extends ImageView> void showCircleImage(Context context, Object object, T imageView, int errorResId, int placeholderResId) {
        showImage(context, object, imageView, new CircleCrop(), errorResId, placeholderResId);
    }

    /**
     * @param context   context
     * @param object    图片地址
     * @param imageView 图片容器
     * @param radius    圆角半径
     * @param <T>       泛型
     */
    public static <T extends ImageView> void showRoundImage(Context context, Object object, T imageView, float radius) {
        showRoundImage(context, object, imageView, radius, 0, 0);
    }

    /**
     * 显示圆角图片
     *
     * @param context          context
     * @param object           图片地址
     * @param imageView        图片容器
     * @param radius           圆角半径
     * @param errorResId       错误图片
     * @param placeholderResId 占位图
     * @param <T>              泛型
     */
    public static <T extends ImageView> void showRoundImage(Context context, Object object, T imageView, float radius, int errorResId, int placeholderResId) {
        showImage(context, object, imageView, new RoundTransform(radius), errorResId, placeholderResId);
    }

    /**
     * 显示一张图片
     *
     * @param context   context
     * @param object    图片地址
     * @param imageView 图片容器
     * @param <T>       泛型
     */
    public static <T extends ImageView> void showImage(Context context, Object object, T imageView) {
        showImage(context, object, imageView, null, 0, 0);
    }

    /**
     * 显示一张图片
     * @param context          context
     * @param object           图片地址
     * @param imageView        图片容器
     * @param errorResId       错误图片
     * @param placeholderResId 占位图
     * @param <T>              泛型
     */
    public static <T extends ImageView> void showImage(Context context, Object object,
                                                       T imageView, int errorResId, int placeholderResId) {
        showImage(context, object, imageView, null, errorResId, placeholderResId);
    }

    /**
     * 显示一张图片
     *
     * @param context          context
     * @param object           图片地址
     * @param imageView        图片容器
     * @param transformation   图片处理
     * @param errorResId       错误图
     * @param placeholderResId 占位图
     * @param <T>              泛型
     */
    @SuppressLint("CheckResult")
    private static <T extends ImageView> void showImage(Context context, Object object, T imageView,
                                                        Transformation<Bitmap> transformation, int errorResId, int placeholderResId) {
        GlideRequest glideRequest = GlideApp.with(context)
                .load(object)
                .error(errorResId)
                .placeholder(placeholderResId);
        if (null != transformation) {
            glideRequest.transform(transformation);
        }
        glideRequest.into(imageView);
    }

    /**
     * 圆角图片
     */
    private static class RoundTransform extends BitmapTransformation {
        private static final String ID = "RoundTransform";
        private static final byte[] ID_BYTES = ID.getBytes(CHARSET);
        private float radius;

        public RoundTransform(float radius) {
            this.radius = radius;
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return roundCrop(pool, toTransform);
        }

        private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
            canvas.drawRoundRect(rectF, radius, radius, paint);
            return result;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof RoundTransform;
        }

        @Override
        public int hashCode() {
            return ID.hashCode();
        }

        @Override
        public void updateDiskCacheKey(MessageDigest messageDigest) {
            messageDigest.update(ID_BYTES);
        }
    }
}