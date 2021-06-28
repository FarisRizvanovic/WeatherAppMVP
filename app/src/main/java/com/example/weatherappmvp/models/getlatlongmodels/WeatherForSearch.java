package com.example.weatherappmvp.models.getlatlongmodels;

import com.google.gson.annotations.SerializedName;

public class WeatherForSearch{

	@SerializedName("coord")
	private Coord coord;

	public Coord getCoord(){
		return coord;
	}
}