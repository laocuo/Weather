package com.laocuo.weather.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.laocuo.weather.R;
import com.laocuo.weather.bean.WeatherFutureInfo;
import com.laocuo.weather.bean.WeatherNowInfo;
import com.laocuo.weather.presenter.impl.WeatherPresenter;
import com.laocuo.weather.presenter.model.IWeatherInterface;
import com.laocuo.weather.utils.L;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/24 0024.
 */

public class WeatherActivity extends AppCompatActivity implements IWeatherInterface{
    @InjectView(R.id.weather_refresh) Button mRefresh;

    @InjectView(R.id.weather_info) TextView mWeatherInfo;

    @InjectView(R.id.weather_refresh1) Button mRefresh1;

    @InjectView(R.id.weather_info1) TextView mWeatherInfo1;

    @InjectView(R.id.weather_refresh2) Button mRefresh2;

    @InjectView(R.id.weather_info2) TextView mWeatherInfo2;

    private WeatherPresenter mWeatherPresenter;
    private Gson gson = new Gson();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        ButterKnife.inject(this);
        mWeatherPresenter = new WeatherPresenter();
        mWeatherPresenter.setView(this);
    }

    @OnClick(R.id.weather_refresh) void refresh() {
        mWeatherPresenter.getNowInfo("nanjing");
    }

    @OnClick(R.id.weather_refresh1) void refresh1() {
        mWeatherPresenter.getFutureInfo("nanjing");
    }

    @Override
    public void updateNowInfo(WeatherNowInfo weatherNowInfo) {
        if (weatherNowInfo != null) {
            L.d("weatherNowInfo != null");
            String result = gson.toJson(weatherNowInfo);
            mWeatherInfo.setText(result);
        } else {
            L.d("weatherNowInfo == null");
            mWeatherInfo.setText("weatherNowInfo == null");
        }
    }

    @Override
    public void updateFutureInfo(WeatherFutureInfo weatherFutureInfo) {
        if (weatherFutureInfo != null) {
            L.d("weatherNowInfo != null");
            String result = gson.toJson(weatherFutureInfo);
            mWeatherInfo1.setText(result);
        } else {
            L.d("weatherNowInfo == null");
            mWeatherInfo.setText("weatherNowInfo == null");
        }
    }
}
