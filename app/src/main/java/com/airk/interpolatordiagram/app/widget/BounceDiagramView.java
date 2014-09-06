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
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;

import com.airk.interpolatordiagram.app.R;

/**
 * Created by kevin on 14-9-5.
 * <p/>
 * DiagramView
 */
public class BounceDiagramView extends View {
    private Paint mArcPaint;
    private Interpolator mPolator;
    private Path mArcPath;

    public BounceDiagramView(Context context) {
        super(context);
        init(context);
    }

    public BounceDiagramView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BounceDiagramView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mArcPaint = new Paint();
        mArcPaint.setColor(Color.argb(255, 3, 169, 244));
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setAntiAlias(true);
        mArcPaint.setStrokeWidth(getResources().getDimensionPixelSize(R.dimen.arc_width));
        mPolator = new BounceInterpolator();
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
            float factor = (float)i / width;
            float result = mPolator.getInterpolation(factor);
            float realY;
            realY = origY - result * 1200f;
            if (realX > getWidth() || realX < 0
                    || realY > getHeight() || realY < 0) {
                break;
            }
            i++;
            path.lineTo(realX, realY);

        }
        canvas.drawPath(path, mArcPaint);
        super.onDraw(canvas);
    }
}
