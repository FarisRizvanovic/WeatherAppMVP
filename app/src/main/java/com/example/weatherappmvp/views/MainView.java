package com.example.weatherappmvp.views;

import com.example.weatherappmvp.models.Current;
import com.example.weatherappmvp.models.Weather;

public interface MainView {

    void getWeatherLatLon(Weather weather, String iconLink);
    void onError(String errorCode);
    void getLatLonByCityName(String lat, String lon);
    void getLatLon(String lat, String lon);
    void initViews();
}
