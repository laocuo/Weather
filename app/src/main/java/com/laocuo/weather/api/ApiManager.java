package com.laocuo.weather.api;

/**
 * Created by hoperun on 9/26/16.
 */
import com.laocuo.weather.WeatherApp;
import com.laocuo.weather.utils.NetWorkUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
//            CacheControl.Builder cachebuilder = new CacheControl.Builder();
//            cachebuilder.maxAge(0, TimeUnit.SECONDS);
//            cachebuilder.maxStale(365, TimeUnit.DAYS);
//            CacheControl cacheControl  = cachebuilder.build();
            if (!NetWorkUtil.isNetWorkAvailable(WeatherApp.getContext())) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response originalResponse = chain.proceed(request);
            if (NetWorkUtil.isNetWorkAvailable(WeatherApp.getContext())) {
                int maxAge = 60; // 在线缓存在1分钟内可读取
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 7; // 离线时缓存保存1周
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };
    public static ApiManager apiManage;
    private static File httpCacheDirectory = new File(WeatherApp.getContext().getCacheDir(), "weatherCache");
    private static int cacheSize = 10 * 1024 * 1024; // 10 MiB
    private static Cache cache = new Cache(httpCacheDirectory, cacheSize);
    private static OkHttpClient client = new OkHttpClient.Builder()
            .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
            .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
            .cache(cache)
            .build();
    private WeatherApi weatherApi;
    private Object weatherMonitor = new Object();

    public static ApiManager getInstence() {
        if (apiManage == null) {
            synchronized (ApiManager.class) {
                if (apiManage == null) {
                    apiManage = new ApiManager();
                }
            }
        }
        return apiManage;
    }

    public WeatherApi getWeatherApiService() {
        if (weatherApi == null) {
            synchronized (weatherMonitor) {
                if (weatherApi == null) {
                    weatherApi = new Retrofit.Builder()
                            .baseUrl("https://api.thinkpage.cn/")
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build().create(WeatherApi.class);
                }
            }
        }

        return weatherApi;
    }
}