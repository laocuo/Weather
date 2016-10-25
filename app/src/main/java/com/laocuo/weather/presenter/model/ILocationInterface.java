package com.laocuo.weather.presenter.model;

/**
 * Created by hoperun on 10/25/16.
 */

public interface ILocationInterface {
    void openLocation();
    void requestLocationSuccess();
    void getLoacationSuccess(String city);
    void getLocationFail();
}
