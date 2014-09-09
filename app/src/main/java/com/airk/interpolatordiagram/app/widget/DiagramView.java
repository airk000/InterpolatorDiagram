/*
 * This source is part of the InterpolatorDiagram repository.
 *
 * Copyright 2014 Kevin Liu (airk908@gmail.com)
 *
 * InterpolatorDiagram is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CommonClasses is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with InterpolatorDiagram.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.airk.interpolatordiagram.app.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;

import com.airk.interpolatordiagram.app.R;

import java.util.ArrayList;

/**
 * Created by Kevin on 2014/9/6.
 * <p/>
 * Diagram for Any Interpolator
 */
public class DiagramView extends View {
    private Paint mPaint;
    private Paint mLinePaint;
    private Paint mTextPaint;
    private Paint mBallPaint;
    private Interpolator mInterpolator;
    private boolean mAnimBalls = false;
    private float mAnimX = Float.MIN_VALUE;
    private float mAnimY = Float.MIN_VALUE;

    private float mZeroY;
    private float mMaxY;
    private float mMAX;
    private float mTopYFactor = 1f;
    private float mBottomYFactor = 0f;
    private ArrayList<StandardLine> mImportY;
    private Path mDiagramPath;

    private boolean mDataInited = false;
    public interface AnimationListener {
        public void onAnimateFinished();
    }
    private AnimationListener mListener;

    private Circle mLeftCircle;
    private Circle mWithCircle;

    private class Circle {
        float cx;
        float cy;
        float radius;

        public Circle() {
            cx = 0f;
            cy = mZeroY;
            radius = 25;
        }
    }

    private class StandardLine {
        float lx;
        float ly;
        float factor;

        public StandardLine(float x, float y, float f) {
            lx = x;
            ly = y;
            factor = f;
        }
    }

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

        mBallPaint = new Paint();
        mBallPaint.setColor(Color.argb(255, 233, 30, 99));
        mBallPaint.setStyle(Paint.Style.FILL);
        mBallPaint.setAntiAlias(true);
        mBallPaint.setStrokeWidth(getResources().getDimensionPixelSize(R.dimen.arc_width));
    }

    public Interpolator setInterpolator(Interpolator interpolator) {
        mInterpolator = interpolator;
        mDataInited = false;
        mAnimBalls = false;
        invalidate();
        return mInterpolator;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mDataInited = false;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mDataInited = false;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mInterpolator == null) {
            super.onDraw(canvas);
            return;
        }

        int width = getWidth();
        if (!mDataInited) {
            // calculate smallest and biggest factor
            for (int i = 0; i < width; i++) {
                float factorX = (float) i / width;
                float factorY = mInterpolator.getInterpolation(factorX);
                if (factorY > 1f) {
                    if (factorY > mTopYFactor) {
                        mTopYFactor = factorY;
                    }
                } else if (factorY < 0f) {
                    if (factorY < mBottomYFactor) {
                        mBottomYFactor = factorY;
                    }
                }
            }
            mMAX = (float) getHeight() / (mTopYFactor - mBottomYFactor);
            mZeroY = (0f - mBottomYFactor) * mMAX;
            mMaxY = getHeight() - (mTopYFactor - 1f) * mMAX;

            mImportY = new ArrayList<StandardLine>();
            mDiagramPath = new Path();
            mDiagramPath.moveTo(0, mZeroY);
            // draw real point
            for (int i = 0; i < width; i++) {
                float factorX = (float) i / width;
                float factorY = mInterpolator.getInterpolation(factorX);
                float y = mMAX * factorY;
                mDiagramPath.lineTo(i, realY(mZeroY + y));
            }
            // draw lines
            for (int i = 1; i < 10; i++) {
                float factor = (float) i / 10;
                float factorY = mInterpolator.getInterpolation(factor);
                float y = mZeroY + mMAX * factorY;
                mImportY.add(new StandardLine(factor * width, y, factor));
            }
            mDataInited = true;
        }
        // Clear canvas
        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        // draw all thing except the two ball
        drawLineY(canvas, mZeroY, false);
        drawLineY(canvas, mMaxY, true);
        for (StandardLine s : mImportY) {
            drawLineY(canvas, s.lx, s.ly, s.factor);
        }
        canvas.drawPath(mDiagramPath, mPaint);

        if (mAnimBalls) {
            if (mAnimX == Float.MIN_VALUE && mAnimY == Float.MIN_VALUE) {
                mAnimX = 0f;
                mAnimY = mZeroY;
                mLeftCircle = new Circle();
                mWithCircle = new Circle();
                mLeftCircle.cx = mLeftCircle.radius;
            }
            float factor = mAnimX / width;
            float factorY = mInterpolator.getInterpolation(factor);
            mAnimY = mZeroY + mMAX * factorY;
            mLeftCircle.cy = mAnimY;
            mWithCircle.cx = mAnimX;
            mWithCircle.cy = mAnimY;
            canvas.drawCircle(mLeftCircle.cx, mLeftCircle.cy, mLeftCircle.radius, mBallPaint);
            canvas.drawCircle(mWithCircle.cx, mWithCircle.cy, mWithCircle.radius, mBallPaint);
            if (mAnimX >= width) {
                mAnimBalls = false;
                mAnimX = Float.MIN_VALUE;
                mAnimY = Float.MIN_VALUE;
                mListener.onAnimateFinished();
            } else {
                mAnimX += mLeftCircle.radius / 4;
            }
            invalidate();
        }
        super.onDraw(canvas);
    }

    public void playBalls(@NonNull AnimationListener listener) {
        mAnimBalls = true;
        invalidate();
        mListener = listener;
    }

    private float realY(float y) {
        return y;
    }

    private void drawLineY(Canvas canvas, float x, float y, float factor) {
        canvas.drawLine(0, realY(y), getWidth(), realY(y), mLinePaint);
        Path path = new Path();
        path.moveTo(x, realY(y));
        path.lineTo(getWidth(), realY(y));
        canvas.drawTextOnPath("" + factor, path, 0, -5, mTextPaint);
    }

    private void drawLineY(Canvas canvas, float y, boolean max) {
        canvas.drawLine(0, realY(y), getWidth(), realY(y), mLinePaint);
        Path path = new Path();
        path.moveTo(0, realY(y));
        path.lineTo(getWidth(), realY(y));
        canvas.drawTextOnPath(max ? "MAX" : "0", path, 0, max ? -5 : 50, mTextPaint);
    }

}
