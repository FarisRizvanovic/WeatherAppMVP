package com.example.weatherappmvp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.weatherappmvp.R;
import com.example.weatherappmvp.adapters.WeatherRecViewAdapter;
import com.example.weatherappmvp.models.Current;
import com.example.weatherappmvp.models.Weather;
import com.example.weatherappmvp.models.WeatherItem;
import com.example.weatherappmvp.presenters.MainPresenter;
import com.example.weatherappmvp.views.MainView;

public class MainActivity extends AppCompatActivity implements MainView {

    private static final int REQUEST_LOCATION = 1;
    private ImageView imgWeatherIcon;
    private TextView txtDescription, txtCityName, txtTemperature;
    private RecyclerView weatherRecView;
    private Button btnSearch;
    private EditText etCityName;

    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initViews();

        MainPresenter mainPresenter = new MainPresenter(this, this);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etCityName.getText().toString().toLowerCase().equals(txtCityName.getText().toString().toLowerCase())) {
                    Toast.makeText(MainActivity.this, "That city is already shown!", Toast.LENGTH_SHORT).show();
                    return;
                }
                mainPresenter.getLatLonByCityName(etCityName.getText().toString());
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etCityName.getWindowToken(), 0);
            }
        });

        etCityName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etCityName.getWindowToken(), 0);
                    etCityName.clearFocus();
                    if (etCityName.getText().toString().toLowerCase().equals(txtCityName.getText().toString().toLowerCase())) {
                        Toast.makeText(MainActivity.this, "That city is already shown!", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    mainPresenter.getLatLonByCityName(etCityName.getText().toString());
                    return true;
                }
                return false;
            }
        });

        ActivityCompat.requestPermissions(this,new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            mainPresenter.enableGps();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        MainPresenter mainPresenter = new MainPresenter(this, this);
        mainPresenter.getLatLon();
    }

    @Override
    public void initViews() {
        imgWeatherIcon = findViewById(R.id.imgWeatherIcon);

        txtCityName = findViewById(R.id.txtCityName);
        txtDescription = findViewById(R.id.txtDescription);
        txtTemperature = findViewById(R.id.txtTemperature);

        weatherRecView = findViewById(R.id.recViewWeather);

        btnSearch = findViewById(R.id.btnSearch);

        etCityName = findViewById(R.id.etCityname);
    }

    @Override
    public void getWeatherLatLon(Weather weather, String iconLink) {

        Current current = weather.getCurrent();
        WeatherItem weatherItem = current.getWeather().get(0);

        String description = weatherItem.getDescription();
        String weatherDescriptionUpCase = description.substring(0, 1).toUpperCase() + description.substring(1);

        int index = weather.getTimezone().indexOf("/") + 1;
        String cityName = weather.getTimezone().substring(index);

        txtDescription.setText(weatherDescriptionUpCase);
        txtCityName.setText(cityName);
        txtTemperature.setText(String.valueOf((int) current.getTemp() + "°C"));

        Glide.with(this)
                .asBitmap()
                .load(iconLink)
                .into(imgWeatherIcon);

        WeatherRecViewAdapter adapter = new WeatherRecViewAdapter(this);
        weatherRecView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        adapter.setWeatherItems(weather.getDaily());
        weatherRecView.setAdapter(adapter);
    }

    @Override
    public void onError(String errorCode) {
        Toast.makeText(this, errorCode, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getLatLonByCityName(String lat, String lon) {
        MainPresenter mainPresenter = new MainPresenter(this, this);
        mainPresenter.getWeatherLatLon(lat, lon);
    }

    @Override
    public void getLatLon(String lat, String lon) {

        MainPresenter mainPresenter = new MainPresenter(this, this);
        mainPresenter.getWeatherLatLon(lat, lon);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.btnGetCurrentLocation){
            MainPresenter mainPresenter = new MainPresenter(this, this);
            mainPresenter.getLatLon();
        }
        return super.onOptionsItemSelected(item);
    }
}