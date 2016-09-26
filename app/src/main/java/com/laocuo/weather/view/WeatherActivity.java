package com.laocuo.weather.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.laocuo.weather.R;
import com.laocuo.weather.bean.WeatherInfo;
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
        L.d("getWeatherInfo");
        mWeatherPresenter.getWeatherInfo("nanjing");
    }

    @Override
    public void updateWeatherInfo(WeatherInfo weatherInfo) {
        if (weatherInfo != null) {
            L.d("weatherInfo != null");
            String result = gson.toJson(weatherInfo);
            mWeatherInfo.setText(result);
        } else {
            L.d("weatherInfo == null");
            mWeatherInfo.setText("weatherInfo == null");
        }
    }
}
