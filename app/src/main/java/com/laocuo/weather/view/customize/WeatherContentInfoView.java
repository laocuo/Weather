package com.laocuo.weather.view.customize;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.laocuo.weather.R;
import com.laocuo.weather.WeatherApp;
import com.laocuo.weather.bean.WeatherDailyInfo;
import com.laocuo.weather.utils.DensityUtil;
import com.laocuo.weather.utils.L;

import java.util.ArrayList;


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

public class WeatherContentInfoView extends View {
    private float mTextDisappearPercentS = 0.9f;
    private float mTextDisappearPercentE = 0.6f;
    private float mPercent = 1.0f;
    private int widthsize, heightsize;
    private Paint mPaint;
    private WeatherDailyInfo.ResultsBean mWeatherInfo;
    private ArrayList<Integer> mTempHighList = new ArrayList<Integer>();
    private ArrayList<Integer> mTempLowList = new ArrayList<Integer>();
    private ArrayList<String> mTextDayList = new ArrayList<String>();
    private ArrayList<String> mDateList = new ArrayList<String>();
    private int mTextSize;
    private int mTextGap;
    private int mCircleRadius;
    private ArrayList<Point> mPoints = new ArrayList<Point>();
    private Context mContext;
    private int scale = 14;

    public WeatherContentInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
//        mPaint.setStrokeWidth(2.0f);
        mTextSize = DensityUtil.sp2px(context, 10);
        mTextGap = DensityUtil.dip2px(context, 6);
        mCircleRadius = DensityUtil.dip2px(context, 4);
        mPaint.setTextSize(mTextSize);
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

    private void setTestData(){
        mTempHighList.clear();
        mTempLowList.clear();
        mTextDayList.clear();
        if (mTempHighList.size() < 1) {
            mTempHighList.add(28);
            mTempHighList.add(20);
            mTempHighList.add(26);
        }
        if (mTempLowList.size() < 1) {
            mTempLowList.add(20);
            mTempLowList.add(16);
            mTempLowList.add(24);
        }
        if (mTextDayList.size() < 1) {
            mTextDayList.add("多云");
            mTextDayList.add("小雨");
            mTextDayList.add("多云");
        }
        if (mDateList.size() < 1) {
            mDateList.add("今天");
            mDateList.add("明天");
            mDateList.add("后天");
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mWeatherInfo == null) {
//            setTestData();
//            drawInfo(canvas);
        } else {
//            setTestData();
            if (mDateList.size() < 1) {
                mDateList.add("今天");
                mDateList.add("明天");
                mDateList.add("后天");
            }
            drawInfo(canvas);
        }
    }

    private void drawInfo(Canvas canvas) {
        mPoints.clear();
        int alltemp = 0, mintemp = 100;
        for (int i=0;i<mTempHighList.size();i++) {
            alltemp += mTempHighList.get(i);
            mintemp = Math.min(mintemp, mTempHighList.get(i));
        }
        int averagetemp = alltemp/mTempHighList.size();
        int averagew  = widthsize/(mTempHighList.size()+1);
        int averageh = heightsize*4/5;

        if (mPercent > 0) {
            Path path = new Path();
            Point prevPoint = new Point();
            int firstPointH;
            firstPointH = averageh - (averagetemp - mintemp) * scale;
            firstPointH = (int) (firstPointH + (heightsize + mCircleRadius - firstPointH) * (1 - mPercent));
            path.moveTo(0, firstPointH);
            prevPoint.set(0, firstPointH);
            for (int i = 0; i < mTempHighList.size() + 1; i++) {
                int w, h;
                w = averagew * (i + 1);
                if (i == mTempHighList.size()) {
                    h = firstPointH;
                } else {
                    h = averageh - (mTempHighList.get(i) - mintemp) * scale;
                    h = (int) (h + (heightsize + mCircleRadius - h) * (1 - mPercent));
                    mPoints.add(new Point(w, h));
                }
                path.cubicTo(prevPoint.x + averagew/3, prevPoint.y, w - averagew/3, h, w, h);
                prevPoint.set(w, h);
            }
            path.lineTo(widthsize, heightsize);
            path.lineTo(0, heightsize);
            path.close();
            canvas.drawPath(path, mPaint);
        }

        for(int i=0;i<mPoints.size();i++) {
            Point p = mPoints.get(i);
            if (mPercent > mTextDisappearPercentE) {
                if (mPercent < mTextDisappearPercentS) {
                    mPaint.setAlpha((int) (255 * (mPercent - mTextDisappearPercentE) / (mTextDisappearPercentS - mTextDisappearPercentE)));
                }
                //draw string
                int start, bottom = p.y - mTextGap - mCircleRadius;
                Rect r = new Rect();

                String temp = mTempHighList.get(i) + WeatherApp.DEGREE;
                mPaint.getTextBounds(temp, 0, temp.length(), r);
                start = p.x - r.width() / 2;
                canvas.drawText(temp, start, bottom, mPaint);
                bottom -= (r.height() + mTextGap);

                String text = mTextDayList.get(i);
                mPaint.getTextBounds(text, 0, text.length(), r);
                start = p.x - r.width() / 2;
                canvas.drawText(text, start, bottom, mPaint);
                bottom -= (r.height() + mTextGap);

                String date = mDateList.get(i);
                mPaint.getTextBounds(date, 0, date.length(), r);
                start = p.x - r.width() / 2;
                canvas.drawText(date, start, bottom, mPaint);

                mPaint.setAlpha(255);
            }
            canvas.drawCircle(p.x, p.y, mCircleRadius, mPaint);
            mPaint.setColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
            canvas.drawCircle(p.x, p.y, mCircleRadius/2, mPaint);
            mPaint.setColor(Color.WHITE);
        }
    }

    public void setPercent(float percent) {
        mPercent = percent;
        invalidate();
    }

    public void setWeatherInfo(WeatherDailyInfo.ResultsBean weatherInfo) {
        mWeatherInfo = weatherInfo;
        int size = mWeatherInfo.getDaily().size();
        mTempHighList.clear();
        mTempLowList.clear();
        mTextDayList.clear();
        for (int i=0;i<size;i++) {
            mTempHighList.add(Integer.valueOf(mWeatherInfo.getDaily().get(i).getHigh()));
            mTempLowList.add(Integer.valueOf(mWeatherInfo.getDaily().get(i).getLow()));
            mTextDayList.add(mWeatherInfo.getDaily().get(i).getText_day());
//            mDateList.add(mWeatherInfo.getDaily().get(i).getDate());
        }
        invalidate();
    }
}
