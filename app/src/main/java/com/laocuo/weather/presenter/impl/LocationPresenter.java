package com.laocuo.weather.presenter.impl;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

import com.laocuo.weather.presenter.model.ILocationInterface;
import com.laocuo.weather.utils.L;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by hoperun on 10/25/16.
 */

public class LocationPresenter {
    private ILocationInterface mView;
    public void setView(ILocationInterface view) {
        mView = view;
    }

    private final String CITY_KEY = "pref_city";
    private Context mContext;
    private LocationManager mLocationManager;
    private CityLocationListener mLocationListener;

    public LocationPresenter(Context context) {
        mContext = context;
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new CityLocationListener();
    }

    public String getCityByLocation() {
        L.d("getCityBySharePreferences");
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        String city = sp.getString(CITY_KEY, "nanjing");
        if (TextUtils.isEmpty(city)) {
            L.d("getCityByLocation");
            if (mLocationManager != null) {
                city = requestLocation();
            }
        }
        return city;
    }

    public void updateCity(String city) {
        L.d("updateCity:"+city);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        String prev_city = sp.getString(CITY_KEY, "nanjing");
        if (!prev_city.equalsIgnoreCase(city)) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(CITY_KEY, city);
            editor.commit();
            mView.getLoacationSuccess(city);
        }
    }

    public String requestLocation() {
        String city = null;
        if (!checkLocationPermission(mContext)) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            requestLocationPermission((Activity) mContext);
            return null;
        }
        Location l = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (l != null) {
            L.d("getLastKnownLocation");
            city = saveCityByLocation(l);
        } else {
            L.d("requestSingleUpdate");
            if (!mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                mView.openLocation();
                mLocationManager.requestSingleUpdate(LocationManager.PASSIVE_PROVIDER, mLocationListener, null);
            } else {
                mLocationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, mLocationListener, null);
            }
        }
        return city;
    }

    private void removeLocationListener() {
        if (mLocationManager != null && mLocationListener != null) {
            if (!checkLocationPermission(mContext)) {
                return;
            }
            L.d("removeUpdates");
            mLocationManager.removeUpdates(mLocationListener);
        }
    }

    private String saveCityByLocation(Location l) {
        String city = null;
        Double latitude = l.getLatitude();
        Double longitude = l.getLongitude();
        Geocoder gc = new Geocoder(mContext, Locale.getDefault());
        List<Address> addressList = null;
        try {
            addressList = gc.getFromLocation(latitude, longitude, 10);
            Address address = addressList.get(0);
            L.d("Locality:" + address.getLocality());
            city = address.getLocality();
            if (TextUtils.isEmpty(city) == false) {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(mContext).edit();
                editor.putString(CITY_KEY, city);
                editor.commit();
            }
        } catch (IOException e) {
            e.printStackTrace();
            mView.getLocationFail();
        } finally {
            return city;
        }
    }

    public void onExit() {
        removeLocationListener();
    }

    public boolean handlePermissionsResult(int code, String[] permissions, int[] results) {
        if (code == REQUEST_LOCATION) {
            L.d("results[0]="+results[0]);
            L.d("results[1]="+results[1]);
            mView.requestLocationSuccess();
            return true;
        }
        return false;
    }

    private class CityLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            L.d("onLocationChanged");
            String city = saveCityByLocation(location);
            mView.getLoacationSuccess(city);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {
            L.d("onProviderEnabled");
        }

        @Override
        public void onProviderDisabled(String s) {
            L.d("onProviderDisabled");
        }
    }

    public static final int REQUEST_LOCATION = 1;

    private boolean checkLocationPermission(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
    }
}
