package com.app.service;

import com.app.exceptions.ExceptionInfo;
import com.app.exceptions.MyException;;
import com.app.converter.impl.CarsConverter;
import com.app.model.Car;
import com.app.model.Color;
import com.app.model.SortingType;
import com.app.validator.CarValidator;
import org.eclipse.collections.impl.collector.BigDecimalSummaryStatistics;
import org.eclipse.collections.impl.collector.Collectors2;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CarService {
	private List<Car> cars;

	public CarService(String fileName) {
		this.cars = getCarsFromJson(fileName);
	}

	/*Method parsing and validating car objects form JSON file*/
	public List<Car> getCarsFromJson(String filename) {
		AtomicInteger atomicInteger = new AtomicInteger(1);
		CarValidator carValidator = new CarValidator();
		return new CarsConverter("src/main/resources/" + filename + ".json")
				.fromJson()
				.orElseThrow(() -> new MyException(ExceptionInfo.SERVICE, "JSON CONVERTER ERROR"))
				.stream()
				.filter(car -> {
					Map<String, String> errors = carValidator.validate(car);
					if (carValidator.hasErrors()) {
						System.out.println("VALIDATION ERRORS FOR CAR NO. " + atomicInteger.get());
						errors.forEach((k, v) -> System.out.println(k + " " + v));
					}
					atomicInteger.incrementAndGet();
					return !carValidator.hasErrors();
				}).collect(Collectors.toList());
	}

	/*Car sorting method according to the selected criterion*/
	List<Car> sortCars(SortingType type, boolean descending) {
		Stream<Car> carStream = null;

		switch (type) {
			case MODEL:
				carStream = cars
						.stream()
						.sorted(Comparator.comparing(Car::getModel));
				break;
			case PRICE:
				carStream = cars
						.stream()
						.sorted(Comparator.comparing(Car::getPrice));
				break;
			case MILEAGE:
				carStream = cars
						.stream()
						.sorted(Comparator.comparing(Car::getMileage));
				break;
			case COLOR:
				carStream = cars
						.stream()
						.sorted(Comparator.comparing(c -> c.getColor().toString()));
				break;
			case COMPONENTS:
				carStream = cars
						.stream()
						.sorted(Comparator.comparing(c -> c.getComponents().size()));
				break;
		}

		List<Car> sortedCars = carStream.collect(Collectors.toList());
		if (descending) {
			Collections.reverse(sortedCars);
		}
		return sortedCars;
	}

	/*A method of returning a list of cars with a mileage greater than the specified parameter*/
	List<Car> getCarsWithMileageGreaterThan(int mileage) {
		if (mileage < 0) {
			throw new MyException(ExceptionInfo.DATA, "Mileage must be greater than 0");
		}
		return cars
				.stream()
				.filter(f -> f.getMileage() > mileage)
				.collect(Collectors.toList());
	}

	/*Method returning map of colors and number of cars in the given color*/
	Map<Color, Long> groupByColours() {
		return cars
				.stream()
				.collect(Collectors.groupingBy(Car::getColor, Collectors.counting()))
				.entrySet()
				.stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.collect(Collectors.toMap(
						Map.Entry::getKey,
						Map.Entry::getValue,
						(v1, v2) -> v1,
						LinkedHashMap::new));
	}

	/*Method returning map of colors and number of cars in the given model*/
	Map<String, Long> groupByModels() {
		return cars
				.stream()
				.collect(Collectors.groupingBy(Car::getModel, Collectors.counting()))
				.entrySet()
				.stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.collect(Collectors.toMap(
						Map.Entry::getKey,
						Map.Entry::getValue,
						(v1, v2) -> v1,
						LinkedHashMap::new));
	}

	/*Method listing the most expensive car(s)*/
	List<Car> getTheMostExpensiveCar() {
		final BigDecimal maxPrice = cars
				.stream()
				.collect(Collectors2.summarizingBigDecimal(Car::getPrice))
				.getMax();

		return cars
				.stream()
				.filter(car -> car.getPrice().equals(maxPrice))
				.collect(Collectors.toList());
	}

	/*Method returning map of models and the most expensive car of this model*/
	Map<String, Car> getTheMostExpensiveCarForEachModel() {
		return cars
				.stream()
				.collect(Collectors.groupingBy(Car::getModel))
				.entrySet()
				.stream()
				.collect(Collectors.toMap(
						Map.Entry::getKey,
						e -> e.getValue()
								.stream()
								.max(Comparator.comparing(Car::getPrice))
								.orElseThrow(() -> new MyException(ExceptionInfo.DATA, "No max price"))
				))
				.entrySet()
				.stream()
				.sorted(Map.Entry.comparingByKey())
				.collect(Collectors.toMap(
						Map.Entry::getKey,
						Map.Entry::getValue,
						(v1, v2) -> v1,
						LinkedHashMap::new
				));
	}

	void showCarsStatistics() {
		BigDecimalSummaryStatistics priceStats = cars
				.stream()
				.collect(Collectors2.summarizingBigDecimal(Car::getPrice));

		DoubleSummaryStatistics mileageStats = cars
				.stream()
				.collect(Collectors.summarizingDouble(Car::getMileage));

		System.out.println("Mileage:\nmin: " + mileageStats.getMin() + "\nmax: " + mileageStats.getMax() + "\navg: " + mileageStats.getAverage());
		System.out.println("Price:\nmin: " + priceStats.getMin() + "\nmax: " + priceStats.getMax() + "\navg: " + priceStats.getAverage().divide(new BigDecimal(cars.size()), 2, BigDecimal.ROUND_FLOOR));
	}

	/*Method returning map of components and corresponding cars containing these components*/
	Map<String, List<Car>> getComponentWithGivenCars() {
		return cars
				.stream()
				.flatMap(e -> e.getComponents()
						.stream())
				.distinct()
				.collect(Collectors.toMap(
						Function.identity(),
						com -> cars
								.stream()
								.filter(car -> car.getComponents()
										.contains(com))
								.collect(Collectors.toList())
				))
				.entrySet()
				.stream()
				.sorted(Comparator.comparing(e -> e.getValue().size()))
				.collect(Collectors.toMap(
						Map.Entry::getKey,
						Map.Entry::getValue,
						(v1, v2) -> v1,
						LinkedHashMap::new
				));
	}

	/*Method returning list of sorted car components*/
	List<Car> getCarsWithSortedAlphabeticComponents() {
		return cars
				.stream()
				.peek(x -> x.setComponents(
						x.getComponents()
								.stream()
								.sorted()
								.collect(Collectors.toList())))
				.collect(Collectors.toList());
	}

	/* Method of displaying cars in the given price range given as parameters */
	List<Car> getCarsWithPriceBetween(BigDecimal min, BigDecimal max) {
		if (Objects.isNull(min)) {
			throw new MyException(ExceptionInfo.DATA, "Min price must be greater than 0");
		}
		if (Objects.isNull(max)) {
			throw new MyException(ExceptionInfo.DATA, "Max price must be greater than 0");
		}
		if (min.compareTo(max) >= 0) {
			throw new MyException(ExceptionInfo.DATA, "Min price can not be greater than max price");
		}
		return cars
				.stream()
				.filter(f -> f.getPrice().compareTo(min) >= 0 && f.getPrice().compareTo(max) <= 0)
				.sorted(Comparator.comparing(Car::getModel))
				.collect(Collectors.toList());
	}

	@Override
	public String toString() {
		return cars
				.stream()
				.map(Car::toString)
				.collect(Collectors.joining("\n"));
	}
}
