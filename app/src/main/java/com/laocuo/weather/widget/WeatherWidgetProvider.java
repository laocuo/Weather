package com.laocuo.weather.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import com.laocuo.weather.R;
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
    public WeatherWidgetProvider() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
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
        remoteViews.setTextViewText(R.id.widget_city, sp.getString("widget_location", "南京"));
        remoteViews.setTextViewText(R.id.widget_temperature, sp.getString("widget_temperature", "25℃"));
        remoteViews.setTextViewText(R.id.widget_text, sp.getString("widget_text", "多云"));
        String code = sp.getString("widget_code", "0");
        remoteViews.setImageViewResource(R.id.widget_image, ImagesUtil.getDrawableByCode(Integer.valueOf(code)));

        AppWidgetManager.getInstance(context).updateAppWidget(appWidgetId, remoteViews);
    }
}
