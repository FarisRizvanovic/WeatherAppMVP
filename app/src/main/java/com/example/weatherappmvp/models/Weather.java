package com.example.weatherappmvp.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Weather{

	@SerializedName("current")
	private Current current;

	@SerializedName("timezone")
	private String timezone;

	@SerializedName("timezone_offset")
	private int timezoneOffset;

	@SerializedName("daily")
	private List<DailyItem> daily;

	@SerializedName("lon")
	private double lon;

	@SerializedName("lat")
	private double lat;

	public Current getCurrent(){
		return current;
	}

	public String getTimezone(){
		return timezone;
	}

	public int getTimezoneOffset(){
		return timezoneOffset;
	}

	public List<DailyItem> getDaily(){
		return daily;
	}

	public double getLon(){
		return lon;
	}

	public double getLat(){
		return lat;
	}
}