package com.app.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car {
	private String model;
	private BigDecimal price;
	private int mileage;
	private Color color;
	private List<String> components;

	public String getModel() {
		return model;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public int getMileage() {
		return mileage;
	}

	public List<String> getComponents() {
		return components;
	}

	@Override
	public String toString() {
		return model + ": " +
				price + " PLN, " +
				mileage + " km, " +
				color + ", " + "com: " +
				components;
	}
}
