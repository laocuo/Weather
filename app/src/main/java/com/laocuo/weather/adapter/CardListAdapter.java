package com.laocuo.weather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.laocuo.weather.R;
import com.laocuo.weather.bean.WeatherDailyInfo;
import com.laocuo.weather.utils.ImagesUtil;

/**
 * Created by hoperun on 9/30/16.
 */

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardHolder> {
    private Context mContext;
    private WeatherDailyInfo info;

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
        WeatherDailyInfo.ResultsBean.DailyBean db = info.getResults().get(0).getDaily().get(position);
        holder.date.setText(db.getDate());
        holder.text_day.setText(db.getText_day());
        holder.text_night.setText(db.getText_night());
        holder.code_day.setImageResource(ImagesUtil.getDrawableByCode(Integer.parseInt(db.getCode_day())));
        holder.code_night.setImageResource(ImagesUtil.getDrawableByCode(Integer.parseInt(db.getCode_night())));
        holder.temp_high.setText("最高气温:"+db.getHigh()+"℃");
        holder.temp_low.setText("最低气温:"+db.getLow()+"℃");
        holder.wind_direction.setText("风向:"+db.getWind_direction());
        holder.wind_speed.setText("风力:"+db.getWind_speed()+"级");
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
        return info == null?0:info.getResults().get(0).getDaily().size();
    }

    public void setCardInfo(WeatherDailyInfo info) {
        this.info = info;
        notifyDataSetChanged();
    }
}