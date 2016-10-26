package com.laocuo.weather.presenter.impl;

import android.text.TextUtils;

import com.laocuo.weather.WeatherApp;
import com.laocuo.weather.api.ApiManager;
import com.laocuo.weather.api.WeatherApi;
import com.laocuo.weather.bean.WeatherAirInfo;
import com.laocuo.weather.bean.WeatherDailyInfo;
import com.laocuo.weather.bean.WeatherHourlyInfo;
import com.laocuo.weather.bean.WeatherLifeInfo;
import com.laocuo.weather.bean.WeatherNowInfo;
import com.laocuo.weather.bean.WeatherSunInfo;
import com.laocuo.weather.presenter.model.IWeatherInterface;
import com.laocuo.weather.utils.L;

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
                        L.d("getNowInfo--onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        L.d("getNowInfo--onError");
                        mView.updateNowInfo(null);
                    }

                    @Override
                    public void onNext(WeatherNowInfo weatherinfo) {
                        L.d("getNowInfo--onNext");
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

    public void getHourlyInfo(String city) {
        WeatherApi weatherApiapi = ApiManager.getInstence().getWeatherApiService();

        Subscription subscription = weatherApiapi.getHourlyInfo(WeatherApp.getWeatherApiKey(),
                city, "zh-Hans", "c", "0", "7")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherHourlyInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.updateHourlyInfo(null);
                    }

                    @Override
                    public void onNext(WeatherHourlyInfo weatherHourlyInfo) {
                        mView.updateHourlyInfo(weatherHourlyInfo);
                    }
                });
        addSubscription(subscription);
    }

    public void getAirInfo(String city) {
        WeatherApi weatherApiapi = ApiManager.getInstence().getWeatherApiService();

        Subscription subscription = weatherApiapi.getAirInfo(WeatherApp.getWeatherApiKey(),
                city, "zh-Hans", "c", "city")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherAirInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.updateAirInfo(null);
                    }

                    @Override
                    public void onNext(WeatherAirInfo weatherAirInfo) {
                        mView.updateAirInfo(weatherAirInfo);
                    }
                });
        addSubscription(subscription);
    }

    public void getLifeInfo(String city) {
        WeatherApi weatherApiapi = ApiManager.getInstence().getWeatherApiService();

        Subscription subscription = weatherApiapi.getLifeInfo(WeatherApp.getWeatherApiKey(),
                city, "zh-Hans")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherLifeInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.updateLifeInfo(null);
                    }

                    @Override
                    public void onNext(WeatherLifeInfo weatherLifeInfo) {
                        mView.updateLifeInfo(weatherLifeInfo);
                    }
                });
        addSubscription(subscription);
    }

    public void getSunInfo(String city) {
        WeatherApi weatherApiapi = ApiManager.getInstence().getWeatherApiService();

        Subscription subscription = weatherApiapi.getSunInfo(WeatherApp.getWeatherApiKey(),
                city, "zh-Hans", "0", "3")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherSunInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.updateSunInfo(null);
                    }

                    @Override
                    public void onNext(WeatherSunInfo weatherSunInfo) {
                        mView.updateSunInfo(weatherSunInfo);
                    }
                });
        addSubscription(subscription);
    }

    public void onExit() {
        unsubcrible();
    }

    public void updateWeatherInfo(String city) {
        if (TextUtils.isEmpty(city) == false) {
            L.d("getWeatherInfo");
            getNowInfo(city);
            getDailyInfo(city);
            getLifeInfo(city);
        }
    }
}
