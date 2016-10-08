package com.laocuo.weather.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
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

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2016/9/24 0024.
 */

public class WeatherActivity extends AppCompatActivity implements IWeatherInterface{
    @BindView(R.id.backdrop) ImageView mBackdrop;

    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbar;

    @BindView(R.id.daily_list) RecyclerView mDailyList;

    @BindView(R.id.refresh) FloatingActionButton mRefresh;

    @BindView(R.id.card_list) RecyclerView mCardList;

    @BindView(R.id.zhishu_list) RecyclerView mZhiShuList;

    @BindView(R.id.now_feels_like) TextView mfeels_like;

    @BindView(R.id.now_humidity) TextView mhumidity;

    @BindView(R.id.now_visibility) TextView mvisibility;

    @BindView(R.id.now_pressure) TextView mpressure;

    @BindView(R.id.now_wind_direction) TextView mwind_direction;

    @BindView(R.id.now_wind_scale) TextView mwind_scale;

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

    private void getLatestWeatherInfo() {
        mWeatherPresenter.getNowInfo("nanjing");
        mWeatherPresenter.getDailyInfo("nanjing");
        mWeatherPresenter.getLifeInfo("nanjing");
    }

    private DailyListAdapter mDailyListAdapter;
    private CardListAdapter mCardListAdapter;
    private ZhiShuListAdapter mZhiShuListAdapter;
//    private Unbinder unbinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        mHandler.postDelayed(updateWeatherInfo, 300);
        mUpdateWeatherThread.start();
    }

    @OnClick(R.id.refresh) void refresh() {
        mHandler.post(mUpdateWeatherInfo);
    }

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
        }
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
