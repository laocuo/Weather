package com.laocuo.weather.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.laocuo.weather.R;
import com.laocuo.weather.WeatherApp;
import com.laocuo.weather.bean.WeatherNowInfo;
import com.laocuo.weather.utils.ImagesUtil;
import com.laocuo.weather.view.WeatherActivity;

/**
 Copyright (C) laocuo <laocuo@163.com>

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

public class WeatherWidgetProvider extends AppWidgetProvider {
    public static final String ACTION_NOTIFY_DATASET_CHANGED =
            "android.appwidget.action.WEATHER_UPDATE";

    private Gson gson = new Gson();

    public WeatherWidgetProvider() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();

        if (ACTION_NOTIFY_DATASET_CHANGED.equals(action)) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context,
                    WeatherWidgetProvider.class));

            onUpdate(context, appWidgetManager, appWidgetIds);
        } else {
            super.onReceive(context, intent);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//        super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (int i=0;i<appWidgetIds.length;i++) {
            updateWidget(context, appWidgetIds[i]);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private void updateWidget(Context context, int appWidgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);

        PendingIntent clickIntent;
        final Intent convIntent = new Intent(context, WeatherActivity.class);
        clickIntent = PendingIntent.getActivity(
                context, 0, convIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget, clickIntent);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String nowinfo  = sp.getString("now_info", "");
        if (!TextUtils.isEmpty(nowinfo)) {
            WeatherNowInfo info = gson.fromJson(nowinfo, WeatherNowInfo.class);
            if (info != null) {
                WeatherNowInfo.ResultsBean resultsBean = info.getResults().get(0);
                remoteViews.setTextViewText(R.id.widget_city, resultsBean.getLocation().getName());
                remoteViews.setTextViewText(R.id.widget_temperature, resultsBean.getNow().getTemperature()+ WeatherApp.DEGREE);
                remoteViews.setTextViewText(R.id.widget_text, resultsBean.getNow().getText());
                String code = resultsBean.getNow().getCode();
                remoteViews.setImageViewResource(R.id.widget_image, ImagesUtil.getDrawableByCode(Integer.valueOf(code)));
            }
        }

        AppWidgetManager.getInstance(context).updateAppWidget(appWidgetId, remoteViews);
    }
}
