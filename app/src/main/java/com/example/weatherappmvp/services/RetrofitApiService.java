package com.example.weatherappmvp.services;

import com.example.weatherappmvp.models.Weather;
import com.example.weatherappmvp.models.getlatlongmodels.WeatherForSearch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitApiService {

    @GET("onecall")
    Call<Weather> getWeather(@Query("lat") String lat,
                             @Query("lon") String lon,
                             @Query("exclude") String exclude,
                             @Query("appid") String appId,
                             @Query("units") String units);

    @GET("weather")
    Call<WeatherForSearch> getByCityName(@Query("q") String cityName,
                                       @Query("appid") String appId);
}
