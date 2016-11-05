package com.laocuo.weather.api;

import com.laocuo.weather.bean.WeatherAirInfo;
import com.laocuo.weather.bean.WeatherDailyInfo;
import com.laocuo.weather.bean.WeatherHourlyInfo;
import com.laocuo.weather.bean.WeatherLifeInfo;
import com.laocuo.weather.bean.WeatherNowInfo;
import com.laocuo.weather.bean.WeatherSunInfo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

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

public interface WeatherApi {
    //https://api.thinkpage.cn/v3/weather/now.json?key=your_api_key&location=beijing&language=zh-Hans&unit=c
    @GET("v3/weather/now.json")
    Observable<WeatherNowInfo> getNowInfo(@Query("key") String key,
                                          @Query("location") String city,
                                          @Query("language") String lan,
                                          @Query("unit") String c);

    //https://api.thinkpage.cn/v3/weather/daily.json?key=your_api_key&location=beijing&language=zh-Hans&unit=c&start=0&days=5
    @GET("v3/weather/daily.json")
    Observable<WeatherDailyInfo> getDailyInfo(@Query("key") String key,
                                               @Query("location") String city,
                                               @Query("language") String lan,
                                               @Query("unit") String c,
                                               @Query("start") String start,
                                               @Query("days") String days);

    //https://api.thinkpage.cn/v3/weather/hourly.json?key=your_api_key&location=beijing&language=zh-Hans&unit=c&start=0&hours=24
    @GET("v3/weather/hourly.json")
    Observable<WeatherHourlyInfo> getHourlyInfo(@Query("key") String key,
                                                @Query("location") String city,
                                                @Query("language") String lan,
                                                @Query("unit") String c,
                                                @Query("start") String start,
                                                @Query("hours") String hours);

    //https://api.thinkpage.cn/v3/air/now.json?key=your_api_key&location=beijing&language=zh-Hans&scope=city
    @GET("v3/air/now.json")
    Observable<WeatherAirInfo> getAirInfo(@Query("key") String key,
                                          @Query("location") String city,
                                          @Query("language") String lan,
                                          @Query("unit") String c,
                                          @Query("scope") String scope);

    //https://api.thinkpage.cn/v3/life/suggestion.json?key=your_api_key&location=shanghai&language=zh-Hans
    @GET("v3/life/suggestion.json")
    Observable<WeatherLifeInfo> getLifeInfo(@Query("key") String key,
                                            @Query("location") String city,
                                            @Query("language") String lan);

    //https://api.thinkpage.cn/v3/geo/sun.json?key=your_api_key&location=beijing&language=zh-Hans&start=0&days=7
    @GET("v3/geo/sun.json")
    Observable<WeatherSunInfo> getSunInfo(@Query("key") String key,
                                          @Query("location") String city,
                                          @Query("language") String lan,
                                          @Query("start") String start,
                                          @Query("days") String days);
}
