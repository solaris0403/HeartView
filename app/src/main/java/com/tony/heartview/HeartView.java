package com.tony.heartview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by user on 12/24/15.
 */
public class HeartView extends View {
    private static final int STAY_TIME = 1000;
    private static final float MOVE_PERCENT = 0.03F;
    private Paint mPaintRect;
    private Paint mPaintCircle;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            invalidate();
        }
    };

    public HeartView(Context context) {
        super(context);
        init();
    }

    public HeartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HeartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaintRect = new Paint();
        mPaintRect.setColor(Color.RED);
        mPaintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintCircle.setColor(Color.RED);
    }

    private int mSide;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        mSide = Math.min(width, height);
        setMeasuredDimension(mSide, mSide);
        mCircleX = -mSide / 4;
        mCircleY = -mSide / 4;
        mRadius = mSide / 4;
    }

    private int mCircleX;
    private int mCircleY;
    private int mRadius;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画矩形
        canvas.translate(mSide / 2, mSide / 2);
        canvas.rotate(45);
        canvas.drawRect(-mSide / 4, -mSide / 4, mSide / 4, mSide / 4, mPaintRect);
        //画圆
        canvas.drawCircle(0, mCircleY, mRadius, mPaintCircle);
        canvas.drawCircle(mCircleX, 0, mRadius, mPaintCircle);

        //往上
        if (mCircleX >= mSide / 4 && mCircleY < mSide / 4) {
            if (mCircleY <= -mSide / 4) {
                //终点
                mCircleX -= (2 * mRadius) * MOVE_PERCENT;
                mHandler.sendEmptyMessageDelayed(1, STAY_TIME);
                return;
            } else {
                mCircleY -= (2 * mRadius) * MOVE_PERCENT;
            }
        }
        //往左
        if (mCircleY <= -mSide / 4 && mCircleX < mSide / 4) {
            if (mCircleX <= -mSide / 4) {
                //终点
                mCircleY += (2 * mRadius) * MOVE_PERCENT;
                mHandler.sendEmptyMessageDelayed(1, STAY_TIME);
                return;
            } else {
                mCircleX -= (2 * mRadius) * MOVE_PERCENT;
            }
        }

        //往下
        if (mCircleX <= -mSide / 4 && mCircleY > -mSide / 4) {
            if (mCircleY >= mSide / 4) {
                //终点
                mCircleX += (2 * mRadius) * MOVE_PERCENT;
                mHandler.sendEmptyMessageDelayed(1, STAY_TIME);
                return;
            } else {
                mCircleY += (2 * mRadius) * MOVE_PERCENT;
            }
        }

        //往右
        if (mCircleY >= mSide / 4 && mCircleX > -mSide / 4) {
            if (mCircleX >= mSide / 4) {
                //终点
                mCircleY -= (2 * mRadius) * MOVE_PERCENT;
                mHandler.sendEmptyMessageDelayed(1, STAY_TIME);
                return;
            } else {
                mCircleX += (2 * mRadius) * MOVE_PERCENT;
            }
        }
        invalidate();
    }

    @Override
    protected void onDetachedFromWindow() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDetachedFromWindow();
    }
}
