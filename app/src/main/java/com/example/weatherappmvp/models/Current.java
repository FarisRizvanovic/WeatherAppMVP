package com.example.weatherappmvp.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Current{

	@SerializedName("sunrise")
	private int sunrise;

	@SerializedName("temp")
	private double temp;

	@SerializedName("visibility")
	private int visibility;

	@SerializedName("uvi")
	private double uvi;

	@SerializedName("pressure")
	private int pressure;

	@SerializedName("clouds")
	private double clouds;

	@SerializedName("feels_like")
	private double feelsLike;

	@SerializedName("wind_gust")
	private double windGust;

	@SerializedName("dt")
	private int dt;

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

	public int getSunrise(){
		return sunrise;
	}

	public double getTemp(){
		return temp;
	}

	public int getVisibility(){
		return visibility;
	}

	public double getUvi(){
		return uvi;
	}

	public int getPressure(){
		return pressure;
	}

	public double getClouds(){
		return clouds;
	}

	public double getFeelsLike(){
		return feelsLike;
	}

	public double getWindGust(){
		return windGust;
	}

	public int getDt(){
		return dt;
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