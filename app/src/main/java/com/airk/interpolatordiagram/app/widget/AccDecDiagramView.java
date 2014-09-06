package com.airk.interpolatordiagram.app.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import com.airk.interpolatordiagram.app.R;

/**
 * Created by kevin on 14-9-5.
 * <p/>
 * DiagramView
 */
public class AccDecDiagramView extends View {
    private Paint mArcPaint;
    private Interpolator mPolator;
    private Path mArcPath;

    public AccDecDiagramView(Context context) {
        super(context);
        init(context);
    }

    public AccDecDiagramView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AccDecDiagramView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mArcPaint = new Paint();
        mArcPaint.setColor(Color.argb(255, 3, 169, 244));
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setAntiAlias(true);
        mArcPaint.setStrokeWidth(getResources().getDimensionPixelSize(R.dimen.arc_width));

        mPolator = new AccelerateDecelerateInterpolator();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);

        int width = getWidth();
        canvas.drawPoint(0, getHeight(), mArcPaint);
        int origX = 0;
        int origY = getHeight();
        if (mArcPath == null) {
            mArcPath = new Path();
        }
        mArcPath.moveTo(origX, origY);
        for (int i = 0; i < width; ) {
            float realX = i + origX;
            float result = mPolator.getInterpolation((float) i);
            float realY;
            if (result == 0f) {
                realY = origY / 2;
            } else {
                realY = origY;
            }
            i += 5 * getResources().getDimensionPixelSize(R.dimen.axis_width);
            mArcPath.lineTo(realX, realY);
        }
        canvas.drawPath(mArcPath, mArcPaint);
        super.onDraw(canvas);
    }
}
