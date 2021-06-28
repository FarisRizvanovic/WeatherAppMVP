package com.example.weatherappmvp.presenters;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.example.weatherappmvp.activities.MainActivity;
import com.example.weatherappmvp.models.Current;
import com.example.weatherappmvp.models.Weather;
import com.example.weatherappmvp.models.getlatlongmodels.Coord;
import com.example.weatherappmvp.models.getlatlongmodels.WeatherForSearch;
import com.example.weatherappmvp.services.RetrofitApiService;
import com.example.weatherappmvp.views.MainView;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.core.content.ContextCompat.getSystemService;

public class MainPresenter {

    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    private static final String API_KEY = "ab58aeed2fb4ba6951b50d4aed1143c1";

    public MainView mainView;
    private Context context;

    public MainPresenter(MainView mainView, Context context) {
        this.mainView = mainView;
        this.context = context;
    }

    public void getWeatherLatLon(String lat, String lon) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApiService retrofitApiService = retrofit.create(RetrofitApiService.class);

        Call<Weather> weatherCall = retrofitApiService.getWeather(lat, lon, "minutely,hourly,alerts", API_KEY, "metric");
        weatherCall.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                if (!response.isSuccessful()) {
                    mainView.onError("Error: " + response.code());
                    return;
                }

                Weather weather = response.body();

                mainView.getWeatherLatLon(weather, getIconLink(weather.getCurrent().getWeather().get(0).getIcon()));
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                mainView.onError(t.getMessage());
            }
        });
    }

    public void getLatLonByCityName(String cityName) {

        if (cityName.isEmpty()){
            mainView.onError("City name is blank!");
            return;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitApiService retrofitApiService = retrofit.create(RetrofitApiService.class);

        Call<WeatherForSearch> call = retrofitApiService.getByCityName(cityName, API_KEY);

        call.enqueue(new Callback<WeatherForSearch>() {
            @Override
            public void onResponse(Call<WeatherForSearch> call, Response<WeatherForSearch> response) {
                if (response.code() == 404){
                    mainView.onError("Please enter a valid city name!");
                    return;
                }
                if (!response.isSuccessful()) {
                    mainView.onError("Code: " + response.code());
                    return;
                }

                WeatherForSearch weatherForSearch = response.body();
                Coord coord = weatherForSearch.getCoord();
                String lat = String.valueOf(coord.getLat());
                String lon = String.valueOf(coord.getLon());

                mainView.getLatLonByCityName(lat, lon);
            }

            @Override
            public void onFailure(Call<WeatherForSearch> call, Throwable t) {
                mainView.onError(t.getMessage());
            }
        });
    }

    public void enableGps(){
        context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }

    public void getLatLon (){

        String latitude, longitude;

        if (ActivityCompat.checkSelfPermission(context,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context,

                Manifest.permission.ACCESS_COARSE_LOCATION) !=PackageManager.PERMISSION_GRANTED)
        {

            Activity activity = (Activity) context;
            ActivityCompat.requestPermissions(activity,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else
        {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);;
            Location LocationGps= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location LocationNetwork=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location LocationPassive=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if (LocationGps !=null)
            {
                double lat=LocationGps.getLatitude();
                double longi=LocationGps.getLongitude();
                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

                mainView.getLatLon(latitude, longitude);
            }
            else if (LocationNetwork !=null)
            {
                double lat=LocationNetwork.getLatitude();
                double longi=LocationNetwork.getLongitude();
                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

                mainView.getLatLon(latitude, longitude);
            }
            else if (LocationPassive !=null)
            {
                double lat=LocationPassive.getLatitude();
                double longi=LocationPassive.getLongitude();
                latitude=String.valueOf(lat);
                longitude=String.valueOf(longi);

                mainView.getLatLon(latitude, longitude);
            }
            else
            {
                mainView.onError("Can't get you location!");
            }
        }
    }

    private String getIconLink(String iconName) {
        Map<String, String> mapOfIcons = new HashMap<>();
        mapOfIcons.put("01d", "http://openweathermap.org/img/wn/01d@2x.png");
        mapOfIcons.put("02d", "http://openweathermap.org/img/wn/02d@2x.png");
        mapOfIcons.put("03d", "http://openweathermap.org/img/wn/03d@2x.png");
        mapOfIcons.put("04d", "http://openweathermap.org/img/wn/04d@2x.png");
        mapOfIcons.put("09d", "http://openweathermap.org/img/wn/09d@2x.png");
        mapOfIcons.put("10d", "http://openweathermap.org/img/wn/10d@2x.png");
        mapOfIcons.put("11d", "http://openweathermap.org/img/wn/11d@2x.png");
        mapOfIcons.put("13d", "http://openweathermap.org/img/wn/13d@2x.png");
        mapOfIcons.put("50d", "http://openweathermap.org/img/wn/50d@2x.png");
        mapOfIcons.put("01n", "http://openweathermap.org/img/wn/01n@2x.png");
        mapOfIcons.put("02n", "http://openweathermap.org/img/wn/02n@2x.png");
        mapOfIcons.put("03n", "http://openweathermap.org/img/wn/03n@2x.png");
        mapOfIcons.put("04n", "http://openweathermap.org/img/wn/04n@2x.png");
        mapOfIcons.put("09n", "http://openweathermap.org/img/wn/09n@2x.png");
        mapOfIcons.put("10n", "http://openweathermap.org/img/wn/10n@2x.png");
        mapOfIcons.put("11n", "http://openweathermap.org/img/wn/11n@2x.png");
        mapOfIcons.put("13n", "http://openweathermap.org/img/wn/13n@2x.png");
        mapOfIcons.put("50n", "http://openweathermap.org/img/wn/50n@2x.png");

        return mapOfIcons.get(iconName);
    }
}
