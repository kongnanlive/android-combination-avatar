package cn.kongnannan.circularavatar;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

public class JoinBitmaps {

    public static final void join(Canvas canvas, int dimension, List<Bitmap> bitmaps) {
        if (bitmaps == null)
            return;
        int count = Math.min(bitmaps.size(), JoinLayout.max());
        float[] size = JoinLayout.size(count);
        join(canvas, dimension, bitmaps, count, size);
    }

    public static final void join(Canvas canvas, int dimension, List<Bitmap> bitmaps, int count,
            float[] size) {
        join(canvas, dimension, bitmaps, count, size, 0.15f);
    }

    public static final void join(Canvas canvas, int dimension, List<Bitmap> bitmaps,
            float gapSize) {
        if (bitmaps == null)
            return;
        int count = Math.min(bitmaps.size(), JoinLayout.max());
        float[] size = JoinLayout.size(count);
        join(canvas, dimension, bitmaps, count, size, gapSize);
    }

    public static final void join(Canvas canvas, int dimension, List<Bitmap> bitmaps, int count,
            float[] size, float gapSize) {
        if (bitmaps == null)
            return;
        // 旋转角度
        float[] rotation = JoinLayout.rotation(count);
        // paint
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        Matrix matrixJoin = new Matrix();
        // scale as join size
        matrixJoin.postScale(size[0], size[0]);

        canvas.save();
        // canvas.drawColor(Color.RED);

        for (int index = 0; index < bitmaps.size(); index++) {
            Bitmap bitmap = bitmaps.get(index);

            // MATRIX
            Matrix matrix = new Matrix();
            // scale as destination
            matrix.postScale((float) dimension / bitmap.getWidth(),
                    (float) dimension / bitmap.getHeight());

            canvas.save();

            matrix.postConcat(matrixJoin);

            float[] offset = JoinLayout.offset(count, index, dimension, size);
            canvas.translate(offset[0], offset[1]);

            // 缩放
            Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);
            // 裁剪
            Bitmap bitmapOk = createMaskBitmap(newBitmap, newBitmap.getWidth(),
                    newBitmap.getHeight(), (int) rotation[index], gapSize);

            canvas.drawBitmap(bitmapOk, 0, 0, paint);
            canvas.restore();
        }

        canvas.restore();
    }

    public static final Bitmap createMaskBitmap(Bitmap bitmap, int viewBoxW, int viewBoxH,
            int rotation, float gapSize) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        paint.setAntiAlias(true);// 抗锯齿
        paint.setFilterBitmap(true);
        int center = Math.round(viewBoxW / 2f);
        canvas.drawCircle(center, center, center, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 0, 0, paint);

        if (rotation != 360) {
            Matrix matrix = new Matrix();
            // 根据原图的中心位置旋转
            matrix.setRotate(rotation, viewBoxW / 2, viewBoxH / 2);
            canvas.setMatrix(matrix);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            canvas.drawCircle(viewBoxW * (1.5f - gapSize), center, center, paint);
        }
        return output;
    }

    public static final Bitmap createBitmap(int width, int height, List<Bitmap> bitmaps) {
        int count = Math.min(bitmaps.size(), JoinLayout.max());
        float[] size = JoinLayout.size(count);
        return createBitmap(width, height, bitmaps, count, size, 0.15f);
    }

    public static final Bitmap createBitmap(int width, int height, List<Bitmap> bitmaps,
            int count, float[] size) {
        return createBitmap(width, height, bitmaps, count, size, 0.15f);
    }

    public static final Bitmap createBitmap(int width, int height, List<Bitmap> bitmaps,
            int count, float[] size, float gapSize) {
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        int dimen = Math.min(width, height);
        join(canvas, dimen, bitmaps, count, size, gapSize);
        return output;
    }

}
