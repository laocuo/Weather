package com.laocuo.weather.presenter.impl;

import com.laocuo.weather.WeatherApp;
import com.laocuo.weather.api.ApiManager;
import com.laocuo.weather.api.WeatherApi;
import com.laocuo.weather.bean.WeatherDailyInfo;
import com.laocuo.weather.bean.WeatherNowInfo;
import com.laocuo.weather.presenter.model.IWeatherInterface;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hoperun on 9/26/16.
 */

public class WeatherPresenter extends BasePresenter{
    private IWeatherInterface mView;
    public void setView(IWeatherInterface view) {
        mView = view;
    }

    public void getNowInfo(String city) {
        WeatherApi weatherApiapi = ApiManager.getInstence().getWeatherApiService();

        Subscription subscription = weatherApiapi.getNowInfo(WeatherApp.getWeatherApiKey(),
                city, "zh-Hans", "c")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherNowInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.updateNowInfo(null);
                    }

                    @Override
                    public void onNext(WeatherNowInfo weatherinfo) {
                        mView.updateNowInfo(weatherinfo);
                    }
                });
        addSubscription(subscription);
    }

    public void getDailyInfo(String city) {
        WeatherApi weatherApiapi = ApiManager.getInstence().getWeatherApiService();

        Subscription subscription = weatherApiapi.getDailyInfo(WeatherApp.getWeatherApiKey(),
                city, "zh-Hans", "c", "0", "3")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherDailyInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.updateDailyInfo(null);
                    }

                    @Override
                    public void onNext(WeatherDailyInfo weatherinfo) {
                        mView.updateDailyInfo(weatherinfo);
                    }
                });
        addSubscription(subscription);
    }
}
