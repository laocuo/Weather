package com.laocuo.weather.presenter.model;

import com.laocuo.weather.bean.WeatherFutureInfo;
import com.laocuo.weather.bean.WeatherNowInfo;

/**
 * Created by hoperun on 9/26/16.
 */

public interface IWeatherInterface {
    void updateNowInfo(WeatherNowInfo weatherNowInfo);
    void updateFutureInfo(WeatherFutureInfo weatherNowInfo);
}
