package com.laocuo.weather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.laocuo.weather.R;
import com.laocuo.weather.WeatherApp;
import com.laocuo.weather.bean.WeatherDailyInfo;
import com.laocuo.weather.utils.ImagesUtil;

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

public class DailyListAdapter extends RecyclerView.Adapter<DailyListAdapter.DailyInfoHolder> {
    private Context mContext;
    private WeatherDailyInfo.ResultsBean info;
    public DailyListAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public DailyInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        DailyInfoHolder v = new DailyInfoHolder(LayoutInflater.from(
                mContext).inflate(R.layout.dailyinfo_item, parent, false));
        return v;
    }

    @Override
    public void onBindViewHolder(DailyInfoHolder holder, int position) {
        WeatherDailyInfo.ResultsBean.DailyBean db = info.getDaily().get(position);
        holder.date.setText(db.getDate());
        holder.text_day.setText("日:"+db.getText_day());
        holder.text_night.setText("夜:"+db.getText_night());
        holder.code_day.setImageResource(ImagesUtil.getDrawableByCode(Integer.parseInt(db.getCode_day())));
        holder.code_night.setImageResource(ImagesUtil.getDrawableByCode(Integer.parseInt(db.getCode_night())));
        holder.temp.setText(db.getHigh()+WeatherApp.DEGREE+" / "+db.getLow()+ WeatherApp.DEGREE);
    }

    @Override
    public int getItemCount() {
        return info == null?0:info.getDaily().size();
    }

    public class DailyInfoHolder extends RecyclerView.ViewHolder {
        private TextView date;
        private TextView text_day;
        private ImageView code_day;
        private TextView text_night;
        private ImageView code_night;
        private TextView temp;
        public DailyInfoHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.daily_item_date);
            text_day = (TextView) itemView.findViewById(R.id.daily_item_text_day);
            code_day = (ImageView) itemView.findViewById(R.id.daily_item_code_day);
            text_night = (TextView) itemView.findViewById(R.id.daily_item_text_night);
            code_night = (ImageView) itemView.findViewById(R.id.daily_item_code_night);
            temp = (TextView) itemView.findViewById(R.id.daily_item_temp);
        }
    }
    public void setDailyInfo(WeatherDailyInfo.ResultsBean dailyInfo) {
        info = dailyInfo;
        notifyDataSetChanged();
    }
}
