package com.laocuo.weather;

import android.app.Application;

/**
 * Created by Administrator on 2016/9/24 0024.
 */

public class WeatherApp extends Application {
    private static Application instance;
    private static Object ob = new Object();
    private static String PRIVATEKEY = "ginqrpwhvxk5sc40";
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Application getContext() {
        synchronized (ob) {
            if (instance == null) {
                instance = new WeatherApp();
            }
        }
        return instance;
    }

    public static String getWeatherApiKey() {
        return PRIVATEKEY;
    }

    public static final String DEGREE = "°";
    public static final String SHESHIDU = "℃";
}
