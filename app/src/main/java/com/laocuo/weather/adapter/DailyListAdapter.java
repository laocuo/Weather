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
import com.laocuo.weather.bean.WeatherDailyInfo;
import com.laocuo.weather.utils.ImagesUtil;

/**
 * Created by hoperun on 9/30/16.
 */

public class DailyListAdapter extends RecyclerView.Adapter<DailyListAdapter.DailyInfoHolder> {
    private Context mContext;
    private WeatherDailyInfo info;
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
        WeatherDailyInfo.ResultsBean.DailyBean db = info.getResults().get(0).getDaily().get(position);
        holder.date.setText(db.getDate());
        holder.text_day.setText("D:"+db.getText_day());
        holder.text_night.setText("N:"+db.getText_night());
        holder.code_day.setImageResource(ImagesUtil.getDrawableByCode(Integer.parseInt(db.getCode_day())));
        holder.code_night.setImageResource(ImagesUtil.getDrawableByCode(Integer.parseInt(db.getCode_night())));
        holder.temp.setText(db.getHigh()+"C / "+db.getLow()+"C");
    }

    @Override
    public int getItemCount() {
        return info == null?0:info.getResults().get(0).getDaily().size();
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
    public void setDailyInfo(WeatherDailyInfo dailyInfo) {
        info = dailyInfo;
        notifyDataSetChanged();
    }
}
