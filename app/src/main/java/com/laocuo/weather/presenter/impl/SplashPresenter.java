package com.laocuo.weather.presenter.impl;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.laocuo.weather.WeatherApp;
import com.laocuo.weather.presenter.model.ISplashInterface;

/**
 * Created by hoperun on 9/23/16.
 */

public class SplashPresenter {

    private ISplashInterface mView;
    public void setView(ISplashInterface view) {
        mView = view;
    }

    public void checkNetWork() {
        if (isNetworkAvailiable()) {
            mView.showEnter();
        } else {
            mView.showError();
        }
    }

    private boolean isNetworkAvailiable() {
        ConnectivityManager cm = (ConnectivityManager) WeatherApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return false;
        }
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni.getState() == NetworkInfo.State.CONNECTED) {
            return true;
        } else {
            return false;
        }
    }
}
