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
import com.laocuo.weather.bean.WeatherDailyInfo;
import com.laocuo.weather.utils.DensityUtil;
import com.laocuo.weather.utils.L;

import java.util.ArrayList;


/**
 * Created by hoperun on 10/20/16.
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
        mTextSize = DensityUtil.sp2px(context, 12);
        mTextGap = DensityUtil.dip2px(context, 6);
        mCircleRadius = DensityUtil.dip2px(context, 6);
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
            mTextDayList.add("多云");
            mTextDayList.add("多云");
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mWeatherInfo == null) {
            setTestData();
            drawInfo(canvas);
        } else {
//            setTestData();
            drawInfo(canvas);
        }
    }

    private void drawInfo(Canvas canvas) {
        mPoints.clear();
        int alltemp = 0;
        for (int i=0;i<mTempHighList.size();i++) {
            alltemp += mTempHighList.get(i);
        }
        int averagetemp = alltemp/mTempHighList.size();
        int averagew  = widthsize/(mTempHighList.size()+1);
        int averageh = heightsize*3/4;
        if (mPercent > 0) {
            Path path = new Path();
            path.moveTo(0, averageh + (heightsize - averageh) * (1 - mPercent));
            for (int i = 0; i < mTempHighList.size() + 1; i++) {
                int w,h;
                w = averagew * (i + 1);
                if (i == mTempHighList.size()) {
                    h = (int) (averageh + (heightsize - averageh) * (1 - mPercent));
                } else {
                    h = averageh - (mTempHighList.get(i) - averagetemp) * scale;
                    h = (int) (h + (heightsize - h) * (1 - mPercent));
                    mPoints.add(new Point(w,h));
                }
//                path.quadTo(averagew * (i + 1) - averagew / 2, h, averagew * (i + 1),
//                        h + (heightsize - h) * (1 - mPercent));
                path.lineTo(w, h);
            }
            path.lineTo(widthsize, heightsize);
            path.lineTo(0, heightsize);
            path.close();
            canvas.drawPath(path, mPaint);
        } else {
            mPoints.clear();
            for (int i = 0; i < mTempHighList.size(); i++) {
                mPoints.add(new Point(averagew*(i+1), heightsize));
            }
        }

        for(int i=0;i<mPoints.size();i++) {
            Point p = mPoints.get(i);
            if (mPercent > mTextDisappearPercentE) {
                if (mPercent < mTextDisappearPercentS) {
                    mPaint.setAlpha((int) (255 * (mPercent - mTextDisappearPercentE) / (mTextDisappearPercentS - mTextDisappearPercentE)));
                }
                //draw string
                String temp = mTempHighList.get(i) + "℃";
                int start, bottom = p.y - mTextGap - mCircleRadius;
                Rect r = new Rect();
                mPaint.getTextBounds(temp, 0, temp.length(), r);
                start = p.x - r.width() / 2;
                canvas.drawText(temp, start, bottom, mPaint);
                bottom -= (r.height() + mTextGap);

                String text = mTextDayList.get(i);
                mPaint.getTextBounds(text, 0, text.length(), r);
                start = p.x - r.width() / 2;
                canvas.drawText(text, start, bottom, mPaint);

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
        }
        invalidate();
    }
}
