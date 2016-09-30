package com.laocuo.weather.presenter.model;

import com.laocuo.weather.bean.WeatherAirInfo;
import com.laocuo.weather.bean.WeatherDailyInfo;
import com.laocuo.weather.bean.WeatherHourlyInfo;
import com.laocuo.weather.bean.WeatherLifeInfo;
import com.laocuo.weather.bean.WeatherNowInfo;
import com.laocuo.weather.bean.WeatherSunInfo;

/**
 * Created by hoperun on 9/26/16.
 */

public interface IWeatherInterface {
    void updateNowInfo(WeatherNowInfo info);
    void updateDailyInfo(WeatherDailyInfo info);
    void updateHourlyInfo(WeatherHourlyInfo info);
    void updateAirInfo(WeatherAirInfo info);
    void updateLifeInfo(WeatherLifeInfo info);
    void updateSunInfo(WeatherSunInfo info);
}
