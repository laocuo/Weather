package com.laocuo.weather.view.customize;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.laocuo.weather.bean.WeatherNowInfo;
import com.laocuo.weather.utils.DensityUtil;
import com.laocuo.weather.utils.L;

/**
 * Created by hoperun on 10/18/16.
 */

public class WeatherHeadInfoView extends View {
    private float mPercent = 1.0f;
    private float mTextDisappearPercentS = 0.4f;
    private float mTextDisappearPercentE = 0.3f;
    private int widthsize, heightsize;
    private Paint mPaint;
    private WeatherNowInfo.ResultsBean mWeatherInfo = null;
    private Context mContext;
    private int mTextSizeBig, mTextSizeMiddle, mTextSizeSmall;
    private int mTextGapBig, mTextGapMiddle, mTextGapSmall;
    private int mTextGapStart;
    public WeatherHeadInfoView(Context context) {
        super(context, null);
    }

    public WeatherHeadInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mPaint = new Paint();
        mPaint.setStrokeWidth(2.0f);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        mTextSizeBig = DensityUtil.sp2px(context, 100);
        mTextSizeMiddle = DensityUtil.sp2px(context, 30);
        mTextSizeSmall = DensityUtil.sp2px(context, 20);
        mTextGapBig = DensityUtil.dip2px(context, 60);
        mTextGapMiddle = DensityUtil.dip2px(context, 20);
        mTextGapSmall = DensityUtil.dip2px(context, 4);
        mTextGapStart = DensityUtil.dip2px(context, 20);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (widthsize == 0) {
            widthsize = MeasureSpec.getSize(widthMeasureSpec);
        }
        if (heightsize == 0) {
            heightsize = MeasureSpec.getSize(heightMeasureSpec);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mWeatherInfo == null) {
//            super.onDraw(canvas);
            String temp = "21℃";
            String text = "多云";
            String city = "南京";
            drawInfo(canvas, temp, text, city);
        } else {
//            float scale = (mPercent*(1 - mMinPercent)) + mMinPercent;
            String temp = mWeatherInfo.getNow().getTemperature()+"℃";
            String text = mWeatherInfo.getNow().getText();
            String city = mWeatherInfo.getLocation().getName();
            drawInfo(canvas, temp, text, city);
        }
    }

    private void drawInfo(Canvas canvas, String temp, String text, String city) {
        Rect r = new Rect();
        int start;
        int bottom = heightsize - mTextGapSmall;

        mPaint.setTextSize((mTextSizeBig-mTextSizeMiddle)*mPercent+mTextSizeMiddle);
        mPaint.getTextBounds(temp, 0, temp.length(), r);
        start = (int) ((((widthsize-r.width())/2)-mTextGapStart)*mPercent + mTextGapStart);
        canvas.drawText(temp, start, bottom, mPaint);
        bottom -= (r.height()+(mTextGapBig-mTextGapSmall)*mPercent+mTextGapSmall);

        mPaint.setTextSize(mTextSizeSmall);
        mPaint.getTextBounds(text, 0, text.length(), r);
        start = (int) ((((widthsize - r.width()) / 2) - mTextGapStart) * mPercent + mTextGapStart);
        if (mPercent > mTextDisappearPercentS) {
            canvas.drawText(text, start, bottom, mPaint);
            bottom -= (r.height() + (mTextGapMiddle - mTextGapSmall) * mPercent + mTextGapSmall);
        } else {
            if (mPercent > mTextDisappearPercentE) {
                mPaint.setAlpha((int) (255 * (mPercent - mTextDisappearPercentE) / (mTextDisappearPercentS-mTextDisappearPercentE)));
                canvas.drawText(text, start, bottom, mPaint);
                mPaint.setAlpha(255);
            }
            bottom -= (r.height()*(mPercent/mTextDisappearPercentS) + (mTextGapMiddle - mTextGapSmall) * mPercent + mTextGapSmall);
        }

        mPaint.setTextSize((mTextSizeMiddle-mTextSizeSmall)*mPercent+mTextSizeSmall);
        mPaint.getTextBounds(city, 0, city.length(), r);
        start = (int) ((((widthsize-r.width())/2)-mTextGapStart)*mPercent + mTextGapStart);
        canvas.drawText(city, start, bottom, mPaint);
    }

    public void setPercent(float percent) {
        mPercent = percent;
        invalidate();
    }

    public void setWeatherInfo(WeatherNowInfo.ResultsBean weatherInfo) {
        mWeatherInfo = weatherInfo;
        invalidate();
    }
}
