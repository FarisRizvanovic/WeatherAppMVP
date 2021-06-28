package com.example.weatherappmvp.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DailyItem{

	@SerializedName("moonset")
	private int moonset;

	@SerializedName("rain")
	private double rain;

	@SerializedName("sunrise")
	private int sunrise;

	@SerializedName("temp")
	private Temp temp;

	@SerializedName("moon_phase")
	private double moonPhase;

	@SerializedName("uvi")
	private double uvi;

	@SerializedName("moonrise")
	private int moonrise;

	@SerializedName("pressure")
	private int pressure;

	@SerializedName("clouds")
	private int clouds;

	@SerializedName("feels_like")
	private FeelsLike feelsLike;

	@SerializedName("wind_gust")
	private double windGust;

	@SerializedName("dt")
	private int dt;

	@SerializedName("pop")
	private double pop;

	@SerializedName("wind_deg")
	private int windDeg;

	@SerializedName("dew_point")
	private double dewPoint;

	@SerializedName("sunset")
	private int sunset;

	@SerializedName("weather")
	private List<WeatherItem> weather;

	@SerializedName("humidity")
	private int humidity;

	@SerializedName("wind_speed")
	private double windSpeed;

	public int getMoonset(){
		return moonset;
	}

	public double getRain(){
		return rain;
	}

	public int getSunrise(){
		return sunrise;
	}

	public Temp getTemp(){
		return temp;
	}

	public double getMoonPhase(){
		return moonPhase;
	}

	public double getUvi(){
		return uvi;
	}

	public int getMoonrise(){
		return moonrise;
	}

	public int getPressure(){
		return pressure;
	}

	public int getClouds(){
		return clouds;
	}

	public FeelsLike getFeelsLike(){
		return feelsLike;
	}

	public double getWindGust(){
		return windGust;
	}

	public int getDt(){
		return dt;
	}

	public double getPop(){
		return pop;
	}

	public int getWindDeg(){
		return windDeg;
	}

	public double getDewPoint(){
		return dewPoint;
	}

	public int getSunset(){
		return sunset;
	}

	public List<WeatherItem> getWeather(){
		return weather;
	}

	public int getHumidity(){
		return humidity;
	}

	public double getWindSpeed(){
		return windSpeed;
	}
}