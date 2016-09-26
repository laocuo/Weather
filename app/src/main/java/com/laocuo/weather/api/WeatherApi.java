package com.laocuo.weather.api;

import com.laocuo.weather.bean.WeatherInfo;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hoperun on 9/26/16.
 */

public interface WeatherApi {
    @GET("v3/weather/now.json")
    Observable<WeatherInfo> getWeatherInfo(@Query("key") String key,
                                           @Query("location") String city,
                                           @Query("language") String lan,
                                           @Query("unit") String c);

    @GET("v3/weather/now.json?key=ginqrpwhvxk5sc40&location=beijing&language=zh-Hans&unit=c")
    Observable<WeatherInfo> getWeatherInfo2();

}
