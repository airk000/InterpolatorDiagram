package com.airk.interpolatordiagram.app.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.Interpolator;

import com.airk.interpolatordiagram.app.R;

/**
 * Created by kevin on 14-9-5.
 * <p/>
 * DiagramView
 */
public class AntiDiagramView extends View {
    private Paint mArcPaint;
    private Interpolator mPolator;
    private Path mArcPath;
    private float mTension;

    public AntiDiagramView(Context context) {
        super(context);
        init(context);
    }

    public AntiDiagramView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AntiDiagramView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mArcPaint = new Paint();
        mArcPaint.setColor(Color.argb(255, 3, 169, 244));
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setAntiAlias(true);
        mArcPaint.setStrokeWidth(getResources().getDimensionPixelSize(R.dimen.arc_width));
        mPolator = new AnticipateInterpolator();
        mTension = 2f;
    }

    public Interpolator setTension(float tension) {
        mPolator = null;
        mTension = tension;
        mPolator = new AnticipateInterpolator(tension);
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
        origY = origY / 2;
        Path path = new Path();
        path.moveTo(origX, origY);
        for (int i = 0; i < width; ) {
            float realX = i + origX;
            float factor = (float) i / width;
            float result = mPolator.getInterpolation(factor);
            float realY;
            realY = origY - result * 20000;
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
        canvas.drawTextOnPath("" + mTension, path, 0, 0, paint);
        super.onDraw(canvas);
    }
}
