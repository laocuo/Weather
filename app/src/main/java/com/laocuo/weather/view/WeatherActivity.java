package com.laocuo.weather.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.laocuo.weather.R;
import com.laocuo.weather.adapter.CardListAdapter;
import com.laocuo.weather.adapter.DailyListAdapter;
import com.laocuo.weather.adapter.ZhiShuListAdapter;
import com.laocuo.weather.bean.WeatherAirInfo;
import com.laocuo.weather.bean.WeatherDailyInfo;
import com.laocuo.weather.bean.WeatherHourlyInfo;
import com.laocuo.weather.bean.WeatherLifeInfo;
import com.laocuo.weather.bean.WeatherNowInfo;
import com.laocuo.weather.bean.WeatherSunInfo;
import com.laocuo.weather.presenter.impl.WeatherPresenter;
import com.laocuo.weather.presenter.model.IWeatherInterface;
import com.laocuo.weather.utils.ImagesUtil;
import com.laocuo.weather.utils.L;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2016/9/24 0024.
 */

public class WeatherActivity extends AppCompatActivity implements IWeatherInterface {
    @BindView(R.id.weather)
    CoordinatorLayout mCoordinatorLayout;

    @BindView(R.id.backdrop)
    ImageView mBackdrop;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @BindView(R.id.daily_list)
    RecyclerView mDailyList;

    @BindView(R.id.refresh)
    FloatingActionButton mRefresh;

    @BindView(R.id.card_list)
    RecyclerView mCardList;

    @BindView(R.id.zhishu_list)
    RecyclerView mZhiShuList;

    @BindView(R.id.now_feels_like)
    TextView mfeels_like;

    @BindView(R.id.now_humidity)
    TextView mhumidity;

    @BindView(R.id.now_visibility)
    TextView mvisibility;

    @BindView(R.id.now_pressure)
    TextView mpressure;

    @BindView(R.id.now_wind_direction)
    TextView mwind_direction;

    @BindView(R.id.now_wind_scale)
    TextView mwind_scale;

    private final String CITY_KEY = "pref_city";

    private final String WIDGET_LOCATION = "widget_location";
    private final String WIDGET_TEMPERATURE = "widget_temperature";
    private final String WIDGET_TEXT = "widget_text";
    private final String WIDGET_CODE = "widget_code";

    private WeatherPresenter mWeatherPresenter;
    private Gson gson = new Gson();

    private UpdateWeatherThread mUpdateWeatherThread = new UpdateWeatherThread();

    private class UpdateWeatherThread extends Thread {
        @Override
        public void run() {
            getLatestWeatherInfo();
        }
    }

    private Handler mHandler = new Handler();
    private UpdateWeatherInfo mUpdateWeatherInfo = new UpdateWeatherInfo();

    private class UpdateWeatherInfo implements Runnable {

        @Override
        public void run() {
            getLatestWeatherInfo();
        }
    }

    private DailyListAdapter mDailyListAdapter;
    private CardListAdapter mCardListAdapter;
    private ZhiShuListAdapter mZhiShuListAdapter;

    private LocationManager mLocationManager;
    private WeatherLocationListener mLocationListener;

    private Context mContext;
    //    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.weather);
        ButterKnife.bind(this);
        init();
        mWeatherPresenter = new WeatherPresenter();
        mWeatherPresenter.setView(this);
    }

    @Override
    protected void onDestroy() {
//        unbinder.unbind();
        super.onDestroy();
        removeLocationListener();
        mWeatherPresenter.onExit();
    }

    private void removeLocationListener() {
        if (mLocationManager != null && mLocationListener != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            L.d("removeUpdates");
            mLocationManager.removeUpdates(mLocationListener);
        }
    }

    private void init() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        collapsingToolbar.setTitle(getResources().getString(R.string.wait));
        Glide.with(this).load(ImagesUtil.getRandomNavigationDrawable()).centerCrop().into(mBackdrop);

        mDailyListAdapter = new DailyListAdapter(this);
        mDailyList.setLayoutManager(new GridLayoutManager(this, 3));
        mDailyList.setItemAnimator(new DefaultItemAnimator());
        mDailyList.setAdapter(mDailyListAdapter);

        mCardListAdapter = new CardListAdapter(this);
        mCardList.setLayoutManager(new LinearLayoutManager(this));
        mCardList.setItemAnimator(new DefaultItemAnimator());
        mCardList.setAdapter(mCardListAdapter);

        mZhiShuListAdapter = new ZhiShuListAdapter(this);
        mZhiShuList.setLayoutManager(new GridLayoutManager(this, 3));
        mZhiShuList.setItemAnimator(new DefaultItemAnimator());
        mZhiShuList.setAdapter(mZhiShuListAdapter);

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new WeatherLocationListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.shareWeather) {
            L.d("shareWeather");
            if (!TextUtils.isEmpty(currentWeather)) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, currentWeather);
                shareIntent.setType("text/plain");
                startActivity(shareIntent);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mHandler.postDelayed(mUpdateWeatherInfo, 300);
//        mUpdateWeatherThread.start();
    }

    @OnClick(R.id.refresh)
    void refresh() {
        L.d("refresh");
        if (mLocationManager != null) {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(mCoordinatorLayout, R.string.get_location_err, Snackbar.LENGTH_SHORT).show();
                return;
            }
            L.d("requestLocationUpdates");
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
        }
    }

    private void getLatestWeatherInfo() {
        String city = getCityByLocation();
        callWeatherApi(city);
    }

    private void callWeatherApi(String city) {
        if (TextUtils.isEmpty(city) == false) {
            L.d("getWeatherInfo");
            mWeatherPresenter.getNowInfo(city);
            mWeatherPresenter.getDailyInfo(city);
            mWeatherPresenter.getLifeInfo(city);
        }
    }

    private String getCityByLocation() {
        L.d("getCityBySharePreferences");
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        String city = sp.getString(CITY_KEY, null);
        if (TextUtils.isEmpty(city)) {
            L.d("getCityByLocation");
            if (mLocationManager != null) {
                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    Snackbar.make(mCoordinatorLayout, R.string.get_location_permission_err, Snackbar.LENGTH_SHORT).show();
                    return null;
                }
                Location l = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (l != null) {
                    L.d("getLastKnownLocation");
                    city = saveCityByLocation(l);
                } else {
                    L.d("requestLocationUpdates");
                    mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
                }
            }
        }
        return city;
    }

    private String saveCityByLocation(Location l) {
        String city = null;
        Double latitude = l.getLatitude();
        Double longitude = l.getLongitude();
        Geocoder gc = new Geocoder(mContext, Locale.getDefault());
        List<Address> addressList = null;
        try {
            addressList = gc.getFromLocation(latitude, longitude, 10);
            Address address = addressList.get(0);
            L.d("Locality:" + address.getLocality());
            city = address.getLocality();
            if (TextUtils.isEmpty(city) == false) {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(mContext).edit();
                editor.putString(CITY_KEY, city);
                editor.commit();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Snackbar.make(mCoordinatorLayout, R.string.get_location_err, Snackbar.LENGTH_SHORT).show();
        } finally {
            return city;
        }
    }

    private class WeatherLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            String city = saveCityByLocation(location);
            removeLocationListener();
            callWeatherApi(city);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    }

    private String currentWeather;
    @Override
    public void updateNowInfo(WeatherNowInfo info) {
        if (info != null) {
            WeatherNowInfo.ResultsBean resultsBean = info.getResults().get(0);
            collapsingToolbar.setTitle(formatNowInfo(resultsBean));
            mfeels_like.setText("体感温度:"+resultsBean.getNow().getFeels_like()+"℃");
            mhumidity.setText("相对湿度:"+resultsBean.getNow().getHumidity()+"%");
            mvisibility.setText("能见度:"+resultsBean.getNow().getVisibility()+"km");
            mpressure.setText("气压:"+resultsBean.getNow().getPressure()+"mb");
            mwind_direction.setText("风向:"+resultsBean.getNow().getWind_direction()+"风");
            mwind_scale.setText("风力:"+resultsBean.getNow().getWind_scale()+"级");
            currentWeather = collapsingToolbar.getTitle().toString();
            saveNowInfo(resultsBean);
        }
    }

    private void saveNowInfo(WeatherNowInfo.ResultsBean resultsBean) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(mContext).edit();
        editor.putString(WIDGET_LOCATION, resultsBean.getLocation().getName());
        editor.putString(WIDGET_TEMPERATURE, resultsBean.getNow().getTemperature()+"℃");
        editor.putString(WIDGET_TEXT, resultsBean.getNow().getText());
        editor.putString(WIDGET_CODE, resultsBean.getNow().getCode());
        editor.commit();
        sendBroadcast(new Intent("android.appwidget.action.WEATHER_UPDATE"));
    }

    private CharSequence formatNowInfo(WeatherNowInfo.ResultsBean resultsBean) {
        StringBuilder builder = new StringBuilder();
        builder.append(resultsBean.getLocation().getName())
                .append(" ")
                .append(resultsBean.getNow().getTemperature())
                .append("℃ ")
                .append(resultsBean.getNow().getText());
        return builder.toString();
    }

    @Override
    public void updateDailyInfo(WeatherDailyInfo info) {
        if (info != null) {
            mDailyListAdapter.setDailyInfo(info);
//            mCardListAdapter.setCardInfo(info);
        }
    }

    @Override
    public void updateHourlyInfo(WeatherHourlyInfo info) {
    }

    @Override
    public void updateAirInfo(WeatherAirInfo info) {
    }

    @Override
    public void updateLifeInfo(WeatherLifeInfo info) {
        if (info != null) {
            mZhiShuListAdapter.setZhiShuInfo(info);
        }
    }

    @Override
    public void updateSunInfo(WeatherSunInfo info) {
    }
}
