package com.airk.interpolatordiagram.app.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;

import com.airk.interpolatordiagram.app.R;

/**
 * Created by Kevin on 2014/9/6.
 *
 * Diagram for Any Interpolator
 */
public class DiagramView extends View {
    private Paint mPaint;
    private Paint mLinePaint;
    private Paint mTextPaint;
    private Interpolator mInterpolator;

    public DiagramView(Context context) {
        super(context);
        init();
    }

    public DiagramView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DiagramView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.argb(255, 3, 169, 244));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(getResources().getDimensionPixelSize(R.dimen.arc_width));

        mLinePaint = new Paint();
        mLinePaint.setColor(Color.argb(255, 97, 97, 97));
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setStrokeWidth(2);

        mTextPaint = new Paint();
        mTextPaint.setTextSize(65f);
        mTextPaint.setColor(Color.RED);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setAntiAlias(true);
    }

    public Interpolator setInterpolator(Interpolator interpolator) {
        mInterpolator = interpolator;
        invalidate();
        return mInterpolator;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Clear canvas
        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);

        if (mInterpolator == null) {
            super.onDraw(canvas);
            return;
        }
        float zeroY;
        float maxY;
        float max;
        float topFactor = 1f;
        float bottomFactor = 0f;
        int width = getWidth();
        for (int i = 0; i < width; i++) {
            float factorX = (float) i / width;
            float factorY = mInterpolator.getInterpolation(factorX);
            if (factorY > 1f) {
                if (factorY > topFactor) {
                    topFactor = factorY;
                }
            } else if (factorY < 0f) {
                if (factorY < bottomFactor) {
                    bottomFactor = factorY;
                }
            }
        }
        max = (float) getHeight() / (topFactor - bottomFactor);
        zeroY = (0f - bottomFactor) * max;
        maxY = getHeight() - (topFactor - 1f) * max;
        drawLineY(canvas, maxY, true);
        drawLineY(canvas, zeroY, false);

        for (int i = 0; i < width; i++) {
            float factorX = (float) i / width;
            float factorY = mInterpolator.getInterpolation(factorX);
            float y = max * factorY;
            canvas.drawPoint(i, realY(zeroY + y), mPaint);
        }
        super.onDraw(canvas);
    }

    private float realY(float y) {
        return getHeight() - y;
    }

    private void drawLineY(Canvas canvas, float y, boolean max) {
        canvas.drawLine(0, realY(y), getWidth(), realY(y), mLinePaint);
        Path path = new Path();
        path.moveTo(0, realY(y));
        path.lineTo(getWidth(), realY(y));
        canvas.drawTextOnPath(max ? "MAX" : "0", path, 0, max ? 50 : -5, mTextPaint);
    }

}
