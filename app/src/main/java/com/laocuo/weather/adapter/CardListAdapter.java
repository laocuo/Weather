package com.laocuo.weather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.laocuo.weather.bean.WeatherDailyInfo;

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
        return null;
    }

    @Override
    public void onBindViewHolder(CardHolder holder, int position) {

    }

    public class CardHolder extends RecyclerView.ViewHolder {

        public CardHolder(View itemView) {
            super(itemView);
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
