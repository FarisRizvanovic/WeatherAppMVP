package com.example.weatherappmvp.models;

import com.google.gson.annotations.SerializedName;

public class FeelsLike{

	@SerializedName("eve")
	private double eve;

	@SerializedName("night")
	private double night;

	@SerializedName("day")
	private double day;

	@SerializedName("morn")
	private double morn;

	public double getEve(){
		return eve;
	}

	public double getNight(){
		return night;
	}

	public double getDay(){
		return day;
	}

	public double getMorn(){
		return morn;
	}
}