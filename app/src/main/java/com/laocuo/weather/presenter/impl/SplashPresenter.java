package com.laocuo.weather.presenter.impl;

import com.laocuo.weather.WeatherApp;
import com.laocuo.weather.presenter.model.ISplashInterface;
import com.laocuo.weather.utils.NetWorkUtil;

/**
 * Created by hoperun on 9/23/16.
 */

public class SplashPresenter {

    private ISplashInterface mView;
    public void setView(ISplashInterface view) {
        mView = view;
    }

    public void checkNetWork() {
        if (NetWorkUtil.isNetWorkAvailable(WeatherApp.getContext())) {
            mView.showEnter();
        } else {
            mView.showError();
        }
    }
}
