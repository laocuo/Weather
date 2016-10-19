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
    private float mTextDisappearPercent = 0.4f;
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
            drawInfo_Test(canvas);
        } else {
//            float scale = (mPercent*(1 - mMinPercent)) + mMinPercent;
            drawInfo(canvas);
        }
    }

    private void drawInfo(Canvas canvas) {
        L.d("mPercent="+mPercent);
        Rect r = new Rect();
        String temp = mWeatherInfo.getNow().getTemperature()+"℃";
        int start;
        int bottom = heightsize - mTextGapSmall;
        mPaint.setTextSize((mTextSizeBig-mTextSizeSmall)*mPercent+mTextSizeSmall);
        mPaint.getTextBounds(temp, 0, temp.length(), r);
        start = (int) ((((widthsize-r.width())/2)-mTextGapStart)*mPercent + mTextGapStart);
        canvas.drawText(temp, start, bottom, mPaint);
        bottom -= (r.height()+(mTextGapBig-mTextGapSmall)*mPercent+mTextGapSmall);

        String text = mWeatherInfo.getNow().getText();
        mPaint.setTextSize(mTextSizeSmall);
        mPaint.getTextBounds(text, 0, text.length(), r);
        start = (int) ((((widthsize - r.width()) / 2) - mTextGapStart) * mPercent + mTextGapStart);
        if (mPercent > mTextDisappearPercent) {
            canvas.drawText(text, start, bottom, mPaint);
            bottom -= (r.height() + (mTextGapMiddle - mTextGapSmall) * mPercent + mTextGapSmall);
        } else {
            bottom -= (r.height()*(mPercent/mTextDisappearPercent) + (mTextGapMiddle - mTextGapSmall) * mPercent + mTextGapSmall);
        }

        String city = mWeatherInfo.getLocation().getName();
        mPaint.setTextSize((mTextSizeMiddle-mTextSizeSmall)*mPercent+mTextSizeSmall);
        mPaint.getTextBounds(city, 0, city.length(), r);
        start = (int) ((((widthsize-r.width())/2)-mTextGapStart)*mPercent + mTextGapStart);
        canvas.drawText(city, start, bottom, mPaint);
    }

    private void drawInfo_Test(Canvas canvas) {
        L.d("mPercent="+mPercent);
        Rect r = new Rect();
        String temp = "21℃";
        int start;
        int bottom = heightsize - mTextGapSmall;
        mPaint.setTextSize((mTextSizeBig-mTextSizeSmall)*mPercent+mTextSizeSmall);
        mPaint.getTextBounds(temp, 0, temp.length(), r);
        start = (int) ((((widthsize-r.width())/2)-mTextGapStart)*mPercent + mTextGapStart);
        canvas.drawText(temp, start, bottom, mPaint);
        bottom -= (r.height()+(mTextGapBig-mTextGapSmall)*mPercent+mTextGapSmall);

        String text = "duoyun";
        mPaint.setTextSize(mTextSizeSmall);
        mPaint.getTextBounds(text, 0, text.length(), r);
        start = (int) ((((widthsize - r.width()) / 2) - mTextGapStart) * mPercent + mTextGapStart);
        if (mPercent > mTextDisappearPercent) {
            canvas.drawText(text, start, bottom, mPaint);
            bottom -= (r.height() + (mTextGapMiddle - mTextGapSmall) * mPercent + mTextGapSmall);
        } else {
            bottom -= (r.height()*(mPercent/mTextDisappearPercent) + (mTextGapMiddle - mTextGapSmall) * mPercent + mTextGapSmall);
        }

        String city = "nanjing";
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
