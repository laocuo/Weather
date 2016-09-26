package com.laocuo.weather.presenter.impl;

import com.laocuo.weather.WeatherApp;
import com.laocuo.weather.api.ApiManager;
import com.laocuo.weather.api.WeatherApi;
import com.laocuo.weather.bean.WeatherInfo;
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

    public void getWeatherInfo(String city) {
        WeatherApi weatherApiapi = ApiManager.getInstence().getWeatherApiService();

        Subscription subscription = weatherApiapi.getWeatherInfo(WeatherApp.getWeatherApiKey(),
                city, "zh-Hans", "c")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.updateWeatherInfo(null);
                    }

                    @Override
                    public void onNext(WeatherInfo weatherinfo) {
                        mView.updateWeatherInfo(weatherinfo);
                    }
                });
        addSubscription(subscription);
    }
}
