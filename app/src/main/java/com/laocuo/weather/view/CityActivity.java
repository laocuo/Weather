package com.laocuo.weather.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.promeg.pinyinhelper.Pinyin;
import com.laocuo.weather.R;
import com.laocuo.weather.utils.CityUtil;
import com.laocuo.weather.utils.L;
import com.laocuo.weather.view.customize.CityNavigateView;
import com.laocuo.weather.view.recycleview.DividerItemDecoration;
import com.laocuo.weather.view.recycleview.OnRecyclerItemClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;

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

public class CityActivity extends AppCompatActivity implements CityNavigateView.onTouchListener{
    private final String CITY_KEY = "pref_city";

    private RecyclerView mCityList;
    private TextView mChiefView;
    private CityListAdapter mCityListAdapter;
    private Context mContext;
    private ArrayList<String> mCityHeads = new ArrayList<String>();
    private Set<String> mCityHeadsSet = new LinkedHashSet<>();
    private ArrayList<City> mCities = new ArrayList<City>();
    private CityNavigateView mNavigateView;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        mContext = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.city_select);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mCities.clear();
        mCityHeadsSet.clear();
        mCityHeads.clear();
        ArrayList<String> cityNameList = CityUtil.getCityList();
        for (String city : cityNameList) {
            City c = new City();
            c.setName(city);
            c.setPinyin(transformPinYin(c.name));
            mCities.add(c);
        }
        Collections.sort(mCities, new Comparator<City>() {
            @Override
            public int compare(City t, City t1) {
                return t.pinyin.compareTo(t1.pinyin);
            }
        });
        for (City c : mCities) {
            mCityHeadsSet.add(c.pinyin.substring(0,1));
        }
        for (String s : mCityHeadsSet) {
            mCityHeads.add(s);
        }
        mChiefView = (TextView) findViewById(R.id.chief);

        mNavigateView = (CityNavigateView) findViewById(R.id.city_navigate);
        mNavigateView.setContent(mCityHeads);
        mNavigateView.setListener(this);

        mCityListAdapter = new CityListAdapter(mCities);
        mCityList = (RecyclerView) findViewById(R.id.city_list);
        layoutManager = new LinearLayoutManager(this);
        mCityList.setLayoutManager(layoutManager);
        mCityList.setItemAnimator(new DefaultItemAnimator());
        mCityList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mCityList.setAdapter(mCityListAdapter);
        mCityListAdapter.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view, int position) {
                TextView name = (TextView) view.findViewById(R.id.city_name);
                String city = name.getText().toString();
                L.d("select city=" + city);
                Intent i = CityActivity.this.getIntent();
                i.putExtra(CITY_KEY, city);
                CityActivity.this.setResult(RESULT_OK, i);
                finish();
            }
        });

//        mCityList.addOnItemTouchListener(new OnRecyclerItemClickListener(mCityList) {
//            @Override
//            public void onItemClick(RecyclerView.ViewHolder vh) {
//                if (vh instanceof CityListAdapter.CityViewHolder) {
//                    CityListAdapter.CityViewHolder v = (CityListAdapter.CityViewHolder) vh;
//                    String city = v.name.getText().toString();
//                    L.d("select city=" + city);
//                    Intent i = CityActivity.this.getIntent();
//                    i.putExtra(CITY_KEY, city);
//                    CityActivity.this.setResult(RESULT_OK, i);
////                    finish();
//                }
//            }
//        });
    }

    public String transformPinYin(String character) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < character.length(); i++) {
            buffer.append(Pinyin.toPinyin(character.charAt(i)));
        }
        return buffer.toString();
    }

    @Override
    public void showChiefView(String text, boolean show) {
        if (show) {
            mChiefView.setText(text);
            mChiefView.setVisibility(View.VISIBLE);
            int selectPosition = 0;
            for (int i = 0; i < mCities.size(); i++) {
                if (mCities.get(i).getFirstPinYin().equals(text)) {
                    selectPosition = i;
                    break;
                }
            }
            scroll2Position(selectPosition);
        } else {
            mChiefView.setVisibility(View.INVISIBLE);
        }
    }

    private void scroll2Position(int index) {
        int firstPosition = layoutManager.findFirstVisibleItemPosition();
        int lastPosition = layoutManager.findLastVisibleItemPosition();
        if (index <= firstPosition) {
            mCityList.scrollToPosition(index);
        } else if (index <= lastPosition) {
            int top = mCityList.getChildAt(index - firstPosition).getTop();
            mCityList.scrollBy(0, top);
        } else {
            mCityList.scrollToPosition(index);
        }
    }

    private interface OnClickListener {
        void onClick(View view, int position);
    }

    private class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.CityViewHolder> {
        private ArrayList<City> cities;
        private OnClickListener mClickListener = null;

        public CityListAdapter(ArrayList<City> cities) {
            this.cities = cities;
        }

        public void setOnClickListener(OnClickListener listener) {
            mClickListener = listener;
        }

        @Override
        public CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            CityViewHolder holder = new CityViewHolder(LayoutInflater.from(mContext).inflate(
                    R.layout.city_list_item, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(final CityViewHolder holder, int position) {
            holder.name.setText(cities.get(position).name);
            if (mClickListener != null) {
                holder.name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mClickListener.onClick(holder.itemView, holder.getLayoutPosition());
                    }
                });
            }
            String head = cities.get(position).pinyin.substring(0, 1);
            String prev_head = null;
            if (position > 0) {
                prev_head = cities.get(position - 1).pinyin.substring(0, 1);
                if (head.equals(prev_head)) {
                    holder.head.setVisibility(View.GONE);
                    return;
                }
            }
            holder.head.setText(head);
            holder.head.setVisibility(View.VISIBLE);
        }

        @Override
        public int getItemCount() {
            return cities.size();
        }

        public class CityViewHolder extends RecyclerView.ViewHolder{
            private TextView head;
            private TextView name;
            public CityViewHolder(View itemView) {
                super(itemView);
                head = (TextView) itemView.findViewById(R.id.city_head);
                name = (TextView) itemView.findViewById(R.id.city_name);
            }
        }
    }

    private class City {
        String name;
        String pinyin;
        public void setName (String name) {
            this.name = name;
        }
        public void setPinyin (String pinyin) {
            this.pinyin = pinyin;
        }

        public String getFirstPinYin() {
            return pinyin.substring(0,1);
        }
    }
}
