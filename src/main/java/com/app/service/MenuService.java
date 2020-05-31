package com.app.service;

import com.app.exceptions.MyException;

public class MenuService {
	private final CarService carService;
	private final UserDataService userDataService;

	public MenuService(String filename) {
		carService = new CarService(filename);
		userDataService = new UserDataService();
	}

	public void menu() {

		while (true) {
			try {
				printMenu();
				int fromUser = userDataService.getChooseMenu();

				switch (fromUser) {
					case 1:
						option1();
						break;
					case 2:
						option2();
						break;
					case 3:
						option3();
						break;
					case 4:
						option4();
						break;
					case 5:
						option5();
						break;
					case 6:
						option6();
						break;
					case 7:
						option7();
						break;
					case 8:
						option8();
						break;
					case 9:
						option9();
						break;
					case 10:
						option10();
						break;
					case 11:
						option11();
						break;
					case 12:
						option12();
						break;
				}

			} catch (MyException e) {
				System.out.println("\n********************EXCEPTIONS********************");
				System.out.println(e.getExceptionMessage());
				System.out.println(e.getExceptionInfo());
				System.out.println(e.getExceptionDateTime());
				System.out.println("\n********************EXCEPTIONS********************");
			}
		}
	}

	private void printMenu() {
		System.out.println("***********************MENU***********************");
		System.out.println("1 - Show cars");
		System.out.println("2 - Sorted cars by");
		System.out.println("3 - Show cars with greater mileage than");
		System.out.println("4 - Show the number of cars of a given colour");
		System.out.println("5 - Show the number of cars of a given model");
		System.out.println("6 - Show the most expensive car/cars");
		System.out.println("7 - Show the most expensive car for each model");
		System.out.println("8 - Show statistics");
		System.out.println("9 - Show components with cars");
		System.out.println("10 - Show cars with sorted components");
		System.out.println("11 - Show cars by price range");
		System.out.println("12 - Exit");
	}

	private void option1() {
		System.out.println(carService);
		System.out.println("----------------------------");
	}

	private void option2() {
		carService
				.sortCars(userDataService.getSortingType(), userDataService.isOrderDescending())
				.forEach(System.out::println);
		System.out.println("----------------------------");
	}

	private void option3() {
		carService
				.getCarsWithMileageGreaterThan(userDataService.getInt("Enter the mileage"))
				.forEach(System.out::println);
		System.out.println("----------------------------");
	}

	private void option4() {
		carService
				.groupByColours()
				.forEach((k, v) -> System.out.println(k + " : " + v));
		System.out.println("----------------------------");
	}

	private void option5() {
		carService
				.groupByModels()
				.forEach((k, v) -> System.out.println(k + " : " + v));
		System.out.println("----------------------------");
	}

	private void option6() {
		carService
				.getTheMostExpensiveCar()
				.forEach(s -> System.out.println(s));
		System.out.println("----------------------------");
	}

	private void option7() {
		carService
				.getTheMostExpensiveCarForEachModel()
				.forEach((k, v) -> System.out.println(k + " : " + v));
		System.out.println("----------------------------");
	}

	private void option8() {
		carService
				.showCarsStatistics();
		System.out.println("----------------------------");
	}

	private void option9() {
		carService
				.getComponentWithGivenCars()
				.forEach((k, v) -> System.out.println(k + " : " + v));
		System.out.println("----------------------------");
	}

	private void option10() {
		carService
				.getCarsWithSortedAlphabeticComponents()
				.forEach(s -> System.out.println(s));
		System.out.println("----------------------------");
	}

	private void option11() {
		carService
				.getCarsWithPriceBetween(userDataService.getBigDecimal("Enter min price"), userDataService.getBigDecimal("Enter max price"))
				.forEach(s -> System.out.println(s));
		System.out.println("----------------------------");
	}

	private void option12() {
		userDataService
				.close();
	}
}
