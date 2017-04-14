package com.jtechlib2.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.util.concurrent.ExecutionException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 图片通用类
 * Created by wuxubaiyang on 16/4/18.
 */
public class ImageUtils {

    /**
     * 显示圆形图片
     *
     * @param context
     * @param uri
     * @param imageView
     */
    public static <T extends ImageView> void showCircleImage(Context context, String uri, T imageView) {
        showCircleImage(context, uri, imageView, 0, 0);
    }

    /**
     * 显示圆形图片
     *
     * @param context
     * @param uri
     * @param imageView
     * @param errorResId
     * @param placeholderResId
     */
    public static <T extends ImageView> void showCircleImage(Context context, String uri, T imageView, int errorResId, int placeholderResId) {
        Glide.with(context)
                .load(uri)
                .error(errorResId)
                .placeholder(placeholderResId)
                .transform(new GlideCircleTransform(context))
                .into(imageView);
    }

    /**
     * @param context
     * @param uri
     * @param imageView
     * @param radius
     * @param <T>
     */
    public static <T extends ImageView> void showRoundImage(Context context, String uri, T imageView, float radius) {
        showRoundImage(context, uri, imageView, radius, 0, 0);
    }

    /**
     * 显示圆角图片
     *
     * @param context
     * @param uri
     * @param imageView
     * @param radius
     * @param errorResId
     * @param placeholderResId
     * @param <T>
     */
    public static <T extends ImageView> void showRoundImage(Context context, String uri, T imageView, float radius, int errorResId, int placeholderResId) {
        Glide.with(context)
                .load(uri)
                .error(errorResId)
                .placeholder(placeholderResId)
                .transform(new GlideRoundTransform(context, radius))
                .into(imageView);
    }

    /**
     * 显示一张图片
     *
     * @param context
     * @param uri
     * @param imageView
     */
    public static <T extends ImageView> void showImage(Context context, String uri, T imageView) {
        showImage(context, uri, imageView, 0, 0);
    }

    /**
     * 显示一张图片
     *
     * @param context
     * @param uri
     * @param imageView
     * @param errorResId
     * @param placeholderResId
     */
    public static <T extends ImageView> void showImage(Context context, String uri, T imageView, int errorResId, int placeholderResId) {
        Glide.with(context)
                .load(uri)
                .error(errorResId)
                .placeholder(placeholderResId)
                .into(imageView);
    }

    /**
     * 请求图片
     *
     * @param context
     * @param uri
     * @param onNext
     */
    public static void requestImage(Context context, String uri, Consumer<? super Bitmap> onNext) {
        requestImage(context, uri, Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL, onNext);
    }

    /**
     * 请求图片
     *
     * @param context
     * @param uri
     * @param width
     * @param height
     * @param onNext
     */
    public static void requestImage(Context context, String uri, int width, int height, Consumer<? super Bitmap> onNext) {
        requestImage(context, uri, width, height, onNext, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e("ImageLoadError", throwable.getMessage());
            }
        });
    }

    /**
     * 请求图片，回调bitmap
     *
     * @param context
     * @param uri
     * @param width
     * @param height
     * @param onNext
     * @param onError
     */
    public static void requestImage(Context context, String uri, int width, int height, Consumer<? super Bitmap> onNext, Consumer<Throwable> onError) {
        Observable.just(new RxModel(context, uri, width, height))
                .subscribeOn(Schedulers.io())
                .map(new Function<RxModel, Bitmap>() {
                    @Override
                    public Bitmap apply(RxModel rxModel) throws Exception {
                        if (!TextUtils.isEmpty(rxModel.getUri())) {
                            try {
                                File file = Glide.with(rxModel.getContext())
                                        .load(rxModel.getUri())
                                        .downloadOnly(rxModel.getWidth(), rxModel.getHeight())
                                        .get();
                                return BitmapFactory.decodeFile(file.getAbsolutePath());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                        }
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }

    /**
     * 请求本地图片
     *
     * @param context
     * @param uri
     * @param onNext
     */
    public static void requestLocalImage(Context context, String uri, Consumer<? super Bitmap> onNext) {
        requestLocalImage(context, uri, Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL, onNext, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        });
    }

    /**
     * 请求本地图片
     *
     * @param context
     * @param uri
     * @param width
     * @param height
     * @param onNext
     */
    public static void requestLocalImage(Context context, String uri, int width, int height, Consumer<? super Bitmap> onNext) {
        requestLocalImage(context, uri, width, height, onNext, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        });
    }

    /**
     * 请求本地图片
     *
     * @param context
     * @param uri
     * @param width
     * @param height
     * @param onNext
     * @param onError
     */
    public static void requestLocalImage(Context context, String uri, int width, int height, Consumer<? super Bitmap> onNext, Consumer<Throwable> onError) {
        Observable.just(new RxModel(context, uri, width, height))
                .subscribeOn(Schedulers.io())
                .map(new Function<RxModel, Bitmap>() {
                    @Override
                    public Bitmap apply(RxModel rxModel) throws Exception {
                        if (!TextUtils.isEmpty(rxModel.getUri())) {
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inJustDecodeBounds = true;
                            BitmapFactory.decodeFile(rxModel.getUri(), options);
                            int targetWidth = rxModel.getWidth();
                            int targetHeight = rxModel.getHeight();
                            if (targetWidth == -1) {
                                double ratio = (1.0 * rxModel.getHeight()) / options.outHeight;
                                targetWidth = (int) (ratio * options.outWidth);
                            } else if (targetHeight == -1) {
                                double ratio = (1.0 * rxModel.getWidth()) / options.outWidth;
                                targetHeight = (int) (ratio * options.outHeight);
                            }
                            options.inSampleSize = calculateInSampleSize(options, targetWidth, targetHeight);
                            options.outWidth = targetWidth;
                            options.outHeight = targetHeight;
                            options.inJustDecodeBounds = false;
                            return BitmapFactory.decodeFile(rxModel.getUri(), options);
                        }
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onNext, onError);
    }

    /**
     * 图片请求，target返回
     *
     * @param context
     * @param uri
     * @param target
     * @param <Y>
     * @return
     */
    public static <Y extends Target<File>> Y requestImage(Context context, String uri, Y target) {
        return Glide.with(context)
                .load(uri)
                .downloadOnly(target);
    }

    /**
     * 裁剪圆形
     */
    private static class GlideCircleTransform extends BitmapTransformation {
        public GlideCircleTransform(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private Bitmap circleCrop(BitmapPool pool, Bitmap toTransform) {
            if (toTransform == null) return null;
            int size = Math.min(toTransform.getWidth(), toTransform.getHeight());
            int x = (toTransform.getWidth() - size) / 2;
            int y = (toTransform.getHeight() - size) / 2;

            Bitmap squared = Bitmap.createBitmap(toTransform, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }

    /**
     * 圆角图片
     */
    private static class GlideRoundTransform extends BitmapTransformation {
        private float radius;

        public GlideRoundTransform(Context context, float radius) {
            super(context);
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
        public String getId() {
            return getClass().getName() + Math.round(radius);
        }
    }

    /**
     * 根据目标宽高计算samplesize
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 图片对象
     */
    private static class RxModel {
        private Context context;
        private String uri;
        private int width;
        private int height;

        public RxModel(Context context, String uri, int width, int height) {
            this.context = context;
            this.uri = uri;
            this.width = width;
            this.height = height;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }
}