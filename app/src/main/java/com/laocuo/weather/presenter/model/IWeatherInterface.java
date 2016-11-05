package com.laocuo.weather.presenter.model;

import com.laocuo.weather.bean.WeatherAirInfo;
import com.laocuo.weather.bean.WeatherDailyInfo;
import com.laocuo.weather.bean.WeatherHourlyInfo;
import com.laocuo.weather.bean.WeatherLifeInfo;
import com.laocuo.weather.bean.WeatherNowInfo;
import com.laocuo.weather.bean.WeatherSunInfo;

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

public interface IWeatherInterface {
    void updateNowInfo(WeatherNowInfo info);
    void updateDailyInfo(WeatherDailyInfo info);
    void updateHourlyInfo(WeatherHourlyInfo info);
    void updateAirInfo(WeatherAirInfo info);
    void updateLifeInfo(WeatherLifeInfo info);
    void updateSunInfo(WeatherSunInfo info);
}
