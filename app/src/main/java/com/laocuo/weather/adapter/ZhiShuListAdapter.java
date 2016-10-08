package com.laocuo.weather.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laocuo.weather.R;
import com.laocuo.weather.bean.WeatherLifeInfo;
import com.laocuo.weather.utils.ImagesUtil;


/**
 * Created by Administrator on 2016/10/5 0005.
 */

public class ZhiShuListAdapter extends RecyclerView.Adapter<ZhiShuListAdapter.ZhiShuInfoHolder> {
    private Context mContext;
    private WeatherLifeInfo info;
    public ZhiShuListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ZhiShuInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ZhiShuInfoHolder v = new ZhiShuInfoHolder(LayoutInflater.from(
                mContext).inflate(R.layout.zhishuinfo_item, parent, false));
        return v;
    }

    @Override
    public void onBindViewHolder(ZhiShuInfoHolder holder, int position) {
        WeatherLifeInfo.ResultsBean.SuggestionBean db = info.getResults().get(0).getSuggestion();
        Drawable d = mContext.getDrawable(ImagesUtil.getZhiShuDrawable(position));
        d.setBounds(0, 0, d.getMinimumWidth(), d.getMinimumHeight());
        holder.title.setCompoundDrawables(null, d, null, null);
        holder.title.setText(getTitleText(position));
        holder.content.setText(getContentText(db, position));
    }

    private int getTitleText(int p) {
        switch (p) {
            default:
            case 0:
                return R.string.xiche;
            case 1:
                return R.string.chuanyi;
            case 2:
                return R.string.shengbing;
            case 3:
                return R.string.yundong;
            case 4:
                return R.string.lvxing;
            case 5:
                return R.string.ziwaixian;
        }
    }

    private String getContentText(WeatherLifeInfo.ResultsBean.SuggestionBean db, int p) {
        switch (p) {
            default:
            case 0:
                return db.getCar_washing().getBrief();
            case 1:
                return db.getDressing().getBrief();
            case 2:
                return db.getFlu().getBrief();
            case 3:
                return db.getSport().getBrief();
            case 4:
                return db.getTravel().getBrief();
            case 5:
                return db.getUv().getBrief();
        }
    }

    @Override
    public int getItemCount() {
        return info == null?0:6;
    }

    public class ZhiShuInfoHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView content;
        public ZhiShuInfoHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.zhishu_item_title);
            content = (TextView) itemView.findViewById(R.id.zhishu_item_content);
        }
    }

    public void setZhiShuInfo(WeatherLifeInfo zhiShuInfo) {
        info = zhiShuInfo;
        notifyDataSetChanged();
    }
}
