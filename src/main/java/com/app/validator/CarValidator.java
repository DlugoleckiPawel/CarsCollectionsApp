package com.app.validator;

import com.app.model.Car;
import com.app.model.Color;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CarValidator {

	private static final String MODEL_ERROR_MESSAGE = "model should contain only upper case letters and spaces";
	private static final String COLOR_ERROR_MESSAGE = "color object cannot be null";
	private static final String MILEAGE_ERROR_MESSAGE = "mileage value should have positive value";
	private static final String PRICE_ERROR_MESSAGE = "price value should have positive value";
	private static final String COMPONENTS_ERROR_MESSAGE = "components collection should contain elements with upper case letters and spaces";

	private Map<String, String> errors = new HashMap<>();

	public boolean hasErrors() {
		return errors.size() > 0;
	}

	public Map<String, String> validate(Car car) {

		errors.clear();

		if (!isModelValid(car)) {
			errors.put("MODEL: ", MODEL_ERROR_MESSAGE);
		}

		if (!isColorValid(car)) {
			errors.put("COLOR: ", COLOR_ERROR_MESSAGE);
		}

		if (!isMileageValid(car)) {
			errors.put("MILEAGE: ", MILEAGE_ERROR_MESSAGE);
		}

		if (!isPriceValid(car)) {
			errors.put("PRICE: ", PRICE_ERROR_MESSAGE);
		}

		if (!isComponentsCollectionValid(car)) {
			errors.put("COMPONENTS: ", COMPONENTS_ERROR_MESSAGE);
		}
		return errors;
	}

	private static boolean isModelValid(Car car) {
		return car.getModel() != null && car.getModel().matches("[A-Z\\s]+");
	}

	private static boolean isColorValid(Car car) {
		return Arrays
				.stream(Color.values())
				.anyMatch(color -> color.equals(car.getColor()));
	}

	private static boolean isMileageValid(Car car) {
		return car.getMileage() > 0;
	}

	private static boolean isPriceValid(Car car) {
		return car.getPrice().compareTo(BigDecimal.ZERO) > 0;
	}

	private static boolean isComponentsCollectionValid(Car car) {
		return car.getComponents() != null && car.getComponents().stream().allMatch(c -> c.matches("[A-Z\\s]+"));
	}

}