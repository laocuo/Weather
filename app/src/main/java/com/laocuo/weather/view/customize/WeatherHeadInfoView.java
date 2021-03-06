package com.laocuo.weather.view.customize;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.laocuo.weather.WeatherApp;
import com.laocuo.weather.bean.WeatherNowInfo;
import com.laocuo.weather.utils.DensityUtil;

import java.io.File;

/**
 Copyright (C) laocuo <laocuo@163.com>

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

public class WeatherHeadInfoView extends View {
    private static final String FONTS_FOLDER = "fonts";
    private static final String FONT_TEMPERATURE = FONTS_FOLDER
            + File.separator + "ComingSoon.ttf";

    private float mPercent = 1.0f;
    private int mVerticalOffset;
    private float mTextDisappearPercentS = 0.4f;
    private float mTextDisappearPercentE = 0.3f;
    private int widthsize, heightsize;
    private Paint mPaint;
    private WeatherNowInfo.ResultsBean mWeatherInfo = null;
    private int mTextSizeBig, mTextSizeMiddle, mTextSizeSmall;
    private int mTextGapBig, mTextGapMiddle, mTextGapMiddle1, mTextGapSmall;
    private int mTextGapStart;
    private int mActionBarHeight;
    private Typeface mTempFont;

    public WeatherHeadInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setStrokeWidth(2.0f);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
        mPaint.setTypeface(font);

        mTextSizeBig = DensityUtil.sp2px(context, 120);
        mTextSizeMiddle = DensityUtil.sp2px(context, 30);
        mTextSizeSmall = DensityUtil.sp2px(context, 20);

        mTextGapBig = DensityUtil.dip2px(context, 60);
        mTextGapMiddle1 = DensityUtil.sp2px(context, 40);
        mTextGapMiddle = DensityUtil.dip2px(context, 20);
        mTextGapSmall = DensityUtil.dip2px(context, 4);

        mTextGapStart = DensityUtil.dip2px(context, 20);

        mActionBarHeight = DensityUtil.getActionBarSize(context);

        AssetManager assets = context.getAssets();
        mTempFont = Typeface.createFromAsset(assets, FONT_TEMPERATURE);
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
//            String temp = "21"+WeatherApp.DEGREE;
//            String text = "多云";
//            String city = "南京";
//            String airquality = "55 良";
//            drawInfo(canvas, temp, text, city, airquality);
        } else {
//            float scale = (mPercent*(1 - mMinPercent)) + mMinPercent;
            String temp = mWeatherInfo.getNow().getTemperature()+ WeatherApp.DEGREE;
            String text = mWeatherInfo.getNow().getText();
            String city = mWeatherInfo.getLocation().getName();
            String airquality = "55 良";
            drawInfo(canvas, temp, text, city, airquality);
        }
    }

    private void drawInfo(Canvas canvas, String temp, String text, String city, String airquality) {
        Rect r = new Rect();
        int start;
        int bottom = mActionBarHeight - mVerticalOffset;

        mPaint.setTextSize((mTextSizeMiddle-mTextSizeSmall)*mPercent+mTextSizeSmall);
        mPaint.getTextBounds(city, 0, city.length(), r);
        start = (int) ((((widthsize-r.width())/2)-mTextGapStart)*mPercent + mTextGapStart);
        bottom += r.height();
        canvas.drawText(city, start, bottom, mPaint);

        if (mPercent > mTextDisappearPercentS) {
            mPaint.setTextSize(mTextSizeSmall);
        } else {
            mPaint.setTextSize(mTextSizeSmall * mPercent/mTextDisappearPercentS);
        }
        mPaint.getTextBounds(text, 0, text.length(), r);
        start = (int) ((((widthsize - r.width()) / 2) - mTextGapStart) * mPercent + mTextGapStart);
        bottom += (r.height() + (mTextGapMiddle - mTextGapSmall) * mPercent + mTextGapSmall);
        if (mPercent > mTextDisappearPercentS) {
            canvas.drawText(text, start, bottom, mPaint);
        } else {
            if (mPercent > mTextDisappearPercentE) {
                mPaint.setAlpha((int) (255 * (mPercent - mTextDisappearPercentE) / (mTextDisappearPercentS-mTextDisappearPercentE)));
                canvas.drawText(text, start, bottom, mPaint);
                mPaint.setAlpha(255);
            }
        }

        mPaint.setTypeface(mTempFont);
        mPaint.setTextSize((mTextSizeBig-mTextSizeMiddle)*mPercent+mTextSizeMiddle);
        mPaint.getTextBounds(temp, 0, temp.length(), r);
        start = (int) ((((widthsize-r.width())/2)-mTextGapStart)*mPercent + mTextGapStart);
        bottom += (r.height()+(mTextGapBig-mTextGapSmall)*mPercent+mTextGapSmall);
        canvas.drawText(temp, start, bottom, mPaint);
        mPaint.setTypeface(Typeface.DEFAULT);

        if (mPercent > mTextDisappearPercentS) {
            mPaint.setTextSize(mTextSizeSmall);
        } else {
            mPaint.setTextSize(mTextSizeSmall * mPercent/mTextDisappearPercentS);
        }
        mPaint.getTextBounds(airquality, 0, airquality.length(), r);
        start = (int) ((((widthsize - r.width()) / 2) - mTextGapStart) * mPercent + mTextGapStart);
        bottom += (r.height() + (mTextGapMiddle1 - mTextGapSmall) * mPercent + mTextGapSmall);
        if (mPercent > mTextDisappearPercentS) {
            canvas.drawText(airquality, start, bottom, mPaint);
        } else {
            if (mPercent > mTextDisappearPercentE) {
                mPaint.setAlpha((int) (255 * (mPercent - mTextDisappearPercentE) / (mTextDisappearPercentS-mTextDisappearPercentE)));
                canvas.drawText(airquality, start, bottom, mPaint);
                mPaint.setAlpha(255);
            }
        }
    }

    public void setPercent(float percent, int verticalOffset) {
        mPercent = percent;
        mVerticalOffset = verticalOffset;
        invalidate();
    }

    public void setWeatherInfo(WeatherNowInfo.ResultsBean weatherInfo) {
        mWeatherInfo = weatherInfo;
        invalidate();
    }
}
