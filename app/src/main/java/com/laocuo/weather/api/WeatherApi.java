package com.laocuo.weather.api;

import com.laocuo.weather.bean.WeatherFutureInfo;
import com.laocuo.weather.bean.WeatherNowInfo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hoperun on 9/26/16.
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
    Observable<WeatherFutureInfo> getFutureInfo(@Query("key") String key,
                                                @Query("location") String city,
                                                @Query("language") String lan,
                                                @Query("unit") String c,
                                                @Query("start") String start,
                                                @Query("days") String days);
}
