package com.airk.interpolatordiagram.app.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;

import com.airk.interpolatordiagram.app.R;

/**
 * Created by kevin on 14-9-5.
 * <p/>
 * DiagramView
 */
public class AccDiagramView extends View {
    private Paint mArcPaint;
    private Interpolator mPolator;
    private Path mArcPath;
    private float mFactor;

    public AccDiagramView(Context context) {
        super(context);
        init(context);
    }

    public AccDiagramView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AccDiagramView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mArcPaint = new Paint();
        mArcPaint.setColor(Color.argb(255, 3, 169, 244));
        mArcPaint.setStyle(Paint.Style.STROKE);
        mPolator = new AccelerateInterpolator(1f);
        mFactor = 1f;
    }

    public Interpolator setFactor(float factor) {
        mPolator = null;
        mFactor = factor;
        mPolator = new AccelerateInterpolator(mFactor);
        invalidate();
        return mPolator;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        int width = getWidth();
        int origX = 0;
        int origY = getHeight() - getResources().getDimensionPixelSize(R.dimen.arc_width);
        Path path = new Path();
        path.moveTo(origX, origY);
        for (int i = 0; i < width; ) {
            float realX = i + origX;
            float result = mPolator.getInterpolation((float) i / (25 * mFactor));
            float realY;
            realY = origY - result;
            if (realX > getWidth() || realX < 0
                    || realY > getHeight() || realY < 0) {
                break;
            }
            i++;
            path.lineTo(realX, realY);

        }
        canvas.drawPath(path, mArcPaint);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setTextSize(65f);
        canvas.drawTextOnPath("" + mFactor, path, 0, 0, paint);
        super.onDraw(canvas);
    }
}
