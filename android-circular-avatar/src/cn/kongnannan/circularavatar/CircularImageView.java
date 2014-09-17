package cn.kongnannan.circularavatar;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class CircularImageView extends View {

    protected int viewWidth;
    protected int viewHeight;

    protected ArrayList<Bitmap> bmps;

    public CircularImageView(Context context) {
        super(context);
    }

    public CircularImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircularImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int dimen = Math.min(width, height);
        setMeasuredDimension(dimen, dimen);
    }

    public void setImageBitmaps(ArrayList<Bitmap> bitmaps) {
        if (bitmaps == null)
            throw new IllegalArgumentException("bitmaps can not be Null");
        if (bitmaps.size() > JoinLayout.max())
            throw new IllegalArgumentException("bitmaps size can not be greater than "
                    + JoinLayout.max());
        this.bmps = bitmaps;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
        viewWidth = viewHeight = Math.min(w, h);
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (viewWidth > 0 && viewHeight > 0) {
            JoinBitmaps.join(canvas, viewWidth, bmps, 0.15f);
        }
    }
}
