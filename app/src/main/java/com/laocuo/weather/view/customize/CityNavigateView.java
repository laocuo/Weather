package com.laocuo.weather.view.customize;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.laocuo.weather.R;
import com.laocuo.weather.WeatherApp;
import com.laocuo.weather.utils.L;

import java.util.ArrayList;

/**
 * Created by hoperun on 10/28/16.
 */

public class CityNavigateView extends View {
    public interface onTouchListener {
        void showTextView(String textView, boolean dismiss);
    }

    private int mWidth, mHeight, mTextHeight, position;
    private Paint paint;
    private Rect mBound;
    private int textsize;
    private int selectedColor;
    private int unselectedColor;
    private int yDown, yMove, mTouchSlop;
    private boolean isSlide;
    private String selectTxt;
    private ArrayList<String> mHeadList = null;
    private onTouchListener listener;

    public CityNavigateView(Context context) {
        this(context, null);
    }

    public CityNavigateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CityNavigateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CityNavigateViewTheme);
        selectedColor = typedArray.getColor(R.styleable.CityNavigateViewTheme_selected_color, Color.BLACK);
        unselectedColor = typedArray.getColor(R.styleable.CityNavigateViewTheme_unselected_color, Color.WHITE);
        float sp = typedArray.getFloat(R.styleable.CityNavigateViewTheme_textSize, 10.0f);
        textsize = sp2px(context, sp);
        typedArray.recycle();
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        paint = new Paint();
        paint.setAntiAlias(true);
        mBound = new Rect();
    }

    public void setContent(ArrayList<String> content) {
        mHeadList = content;
        invalidate();
    }

    public void setListener(onTouchListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mHeadList == null) {
            super.onDraw(canvas);
            return;
        }
        for (int i = 0; i < mHeadList.size(); i++) {
            String text = mHeadList.get(i);
            if (i == position - 1) {
                paint.setColor(selectedColor);
                selectTxt = mHeadList.get(i);
                listener.showTextView(text, false);
            } else {
                paint.setColor(unselectedColor);
            }
            paint.setTextSize(textsize);
            paint.getTextBounds(text, 0, text.length(), mBound);
            canvas.drawText(text, (mWidth - mBound.width()) * 1 / 2, mTextHeight - mBound.height(), paint);
            mTextHeight += mHeight / mHeadList.size();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        int action = ev.getAction();
        int y = (int) ev.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                yDown = y;
                break;
            case MotionEvent.ACTION_MOVE:
                yMove = y;
                int dy = yMove - yDown;
                if (Math.abs(dy) > mTouchSlop) {
                    isSlide = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mTextHeight = mHeight / mHeadList.size();
                position = y / (mHeight / (mHeadList.size() + 1));
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (isSlide) {
                    mTextHeight = mHeight / mHeadList.size();
                    position = y / (mHeight / mHeadList.size() + 1) + 1;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                mTextHeight = mHeight / mHeadList.size();
                position = 0;
                invalidate();
                listener.showTextView(selectTxt, true);
                break;
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = dip2px(WeatherApp.getContext(),40);
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = dip2px(WeatherApp.getContext(),200);
        }
        mWidth = width;
        mHeight = height;
        mTextHeight = mHeight / mHeadList.size();
        setMeasuredDimension(width, height);
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private int sp2px (Context context, float sp) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * fontScale + 0.5f);
    }
}