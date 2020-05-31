package com.app.converter.impl;

import com.app.converter.JsonConverter;
import com.app.model.Car;

import java.util.List;

public class CarsConverter extends JsonConverter<List<Car>> {

	public CarsConverter(String jsonFilename) {
		super(jsonFilename);
	}
}
