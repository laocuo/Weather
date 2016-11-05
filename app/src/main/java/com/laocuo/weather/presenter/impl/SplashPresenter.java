package com.laocuo.weather.presenter.impl;

import com.laocuo.weather.WeatherApp;
import com.laocuo.weather.presenter.model.ISplashInterface;
import com.laocuo.weather.utils.NetWorkUtil;

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

public class SplashPresenter {

    private ISplashInterface mView;
    public void setView(ISplashInterface view) {
        mView = view;
    }

    public void checkNetWork() {
        if (NetWorkUtil.isNetWorkAvailable(WeatherApp.getContext())) {
            mView.showEnter();
        } else {
            mView.showError();
        }
    }
}
