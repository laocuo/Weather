package com.laocuo.weather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardHolder> {
    private Context mContext;
    private WeatherDailyInfo.ResultsBean info;

    public CardListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public CardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardHolder v = new CardHolder(LayoutInflater.from(mContext).inflate(
                R.layout.card_item, parent, false));
        return v;
    }

    @Override
    public void onBindViewHolder(CardHolder holder, int position) {
        WeatherDailyInfo.ResultsBean.DailyBean db = info.getDaily().get(position);
        holder.date.setText(db.getDate());
        holder.text_day.setText(db.getText_day());
        holder.text_night.setText(db.getText_night());
        holder.code_day.setImageResource(ImagesUtil.getDrawableByCode(Integer.parseInt(db.getCode_day())));
        holder.code_night.setImageResource(ImagesUtil.getDrawableByCode(Integer.parseInt(db.getCode_night())));
        holder.temp_high.setText("最高气温:"+db.getHigh()+ WeatherApp.DEGREE);
        holder.temp_low.setText("最低气温:"+db.getLow()+WeatherApp.DEGREE);
        holder.wind_direction.setText("风向:"+db.getWind_direction());
        holder.wind_speed.setText("风力:"+db.getWind_scale()+"级");
    }

    public class CardHolder extends RecyclerView.ViewHolder {
        private TextView date;
        private TextView text_day;
        private ImageView code_day;
        private TextView text_night;
        private ImageView code_night;
        private TextView temp_high;
        private TextView temp_low;
        private TextView wind_direction;
        private TextView wind_speed;
        public CardHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.card_item_date);
            text_day = (TextView) itemView.findViewById(R.id.card_item_text_day);
            text_night = (TextView) itemView.findViewById(R.id.card_item_text_night);
            temp_high = (TextView) itemView.findViewById(R.id.card_item_temp_high);
            temp_low = (TextView) itemView.findViewById(R.id.card_item_temp_low);
            code_day = (ImageView) itemView.findViewById(R.id.card_item_code_day);
            code_night = (ImageView) itemView.findViewById(R.id.card_item_code_night);
            wind_direction = (TextView) itemView.findViewById(R.id.card_item_wind_direction);
            wind_speed = (TextView) itemView.findViewById(R.id.card_item_wind_speed);
        }
    }
    @Override
    public int getItemCount() {
        return info == null?0:info.getDaily().size();
    }

    public void setCardInfo(WeatherDailyInfo.ResultsBean info) {
        this.info = info;
        notifyDataSetChanged();
    }
}
