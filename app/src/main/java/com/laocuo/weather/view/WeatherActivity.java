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
    @BindView(R.id.weather_refresh) Button mGetInfo;

    @BindView(R.id.weather_info) TextView mWeatherInfo;

    @BindView(R.id.weather_refresh1) Button mGetInfo1;

    @BindView(R.id.weather_info1) TextView mWeatherInfo1;

    @BindView(R.id.backdrop) ImageView mBackdrop;

    @BindView(R.id.collapsing_toolbar) CollapsingToolbarLayout collapsingToolbar;

    @BindView(R.id.daily_list) RecyclerView mDailyList;

    @BindView(R.id.refresh) FloatingActionButton mRefresh;

    @BindView(R.id.card_list) RecyclerView mCardList;

    private WeatherPresenter mWeatherPresenter;
    private Gson gson = new Gson();

    private UpdateWeatherThread mUpdateWeatherThread = new UpdateWeatherThread();

    private class UpdateWeatherThread extends Thread {
        @Override
        public void run() {
            mWeatherPresenter.getNowInfo("nanjing");
            mWeatherPresenter.getDailyInfo("nanjing");
        }
    }

    private Handler mHandler = new Handler();
    private UpdateWeatherInfo mUpdateWeatherInfo = new UpdateWeatherInfo();
    private class UpdateWeatherInfo implements Runnable {

        @Override
        public void run() {
            mWeatherPresenter.getNowInfo("nanjing");
            mWeatherPresenter.getDailyInfo("nanjing");
        }
    }

    private DailyListAdapter mDailyListAdapter;
    private CardListAdapter mCardListAdapter;
    private Unbinder unbinder;
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
        unbinder.unbind();
        super.onDestroy();
    }

    private void init() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbar.setTitle(getResources().getString(R.string.wait));
        Glide.with(this).load(ImagesUtil.getRandomNavigationDrawable()).centerCrop().into(mBackdrop);

        mDailyListAdapter = new DailyListAdapter(this);
        mDailyList.setLayoutManager(new GridLayoutManager(this, 3));
        mDailyList.setItemAnimator(new DefaultItemAnimator());
        mDailyList.setAdapter(mDailyListAdapter);

        mCardListAdapter = new CardListAdapter(this);
        mCardList.setLayoutManager(new LinearLayoutManager(this));
        mCardList.setItemAnimator(new DefaultItemAnimator());
        mCardList.setAdapter(mCardListAdapter);
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

    @OnClick(R.id.weather_refresh) void getInfo() {
        mWeatherPresenter.getDailyInfo("nanjing");
    }

    @OnClick(R.id.weather_refresh1) void getInfo1() {
        mWeatherPresenter.getLifeInfo("nanjing");
    }

    @OnClick(R.id.refresh) void refresh() {
//        mHandler.post(mUpdateWeatherInfo);
    }

    @Override
    public void updateNowInfo(WeatherNowInfo info) {
        if (info != null) {
            String result = gson.toJson(info);
            mWeatherInfo.setText(result);
        } else {
            mWeatherInfo.setText("info == null");
        }
        if (info != null) {
            WeatherNowInfo.ResultsBean resultsBean = info.getResults().get(0);
            collapsingToolbar.setTitle(formatNowInfo(resultsBean));
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
            mCardListAdapter.setCardInfo(info);
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
            String result = gson.toJson(info);
            mWeatherInfo1.setText(result);
        } else {
            mWeatherInfo1.setText("info == null");
        }
    }

    @Override
    public void updateSunInfo(WeatherSunInfo info) {
    }
}
