package com.app.service;

import com.app.exceptions.ExceptionInfo;
import com.app.exceptions.MyException;
import com.app.model.SortingType;

import java.math.BigDecimal;
import java.util.Scanner;

public class UserDataService {

	private Scanner scanner = new Scanner(System.in);

	public SortingType getSortingType() {
		System.out.println("Choose sorting type");
		System.out.println("1 - Model");
		System.out.println("2 - Price");
		System.out.println("3 - Mileage");
		System.out.println("4 - Color");
		System.out.println("5 - Components");

		String text = scanner.nextLine();
		if (!text.matches("[1-5]")) {
			throw new MyException(ExceptionInfo.DATA, "Sorting type option is incorrect: " + text);
		}
		return SortingType.values()[Integer.parseInt(text) - 1];
	}


	public int getChooseMenu() {
		System.out.println("Choose menu option (1-12)");
		String text = scanner.nextLine();
		if (!text.matches("([1-9]|1[0-2])")) {
			throw new MyException(ExceptionInfo.VALIDATION, "Option number is incorrect: " + text);
		}
		return Integer.parseInt(text);
	}

	public boolean isOrderDescending() {
		System.out.println("Do you want to sort in descending order ?");
		System.out.println("1 - Yes");
		System.out.println("2 - No");

		String text = scanner.nextLine();
		if (!text.matches("[1-2]")) {
			throw new MyException(ExceptionInfo.VALIDATION, "Sort type option number is incorrect: " + text);
		}
		return text.equals("1");
	}

	public int getInt(String message) {
		System.out.println(message);
		String text = scanner.nextLine();

		if (!text.matches("\\d+")) {
			throw new MyException(ExceptionInfo.VALIDATION, "Incorrect int value: " + text);
		}
		return Integer.parseInt(text);
	}

	public BigDecimal getBigDecimal(String message) {
		System.out.println(message);
		String text = scanner.nextLine();

		if (!text.matches("(\\d+\\.)*\\d+")) {
			throw new MyException(ExceptionInfo.VALIDATION, "Incorrect BigDecimal value: " + text);
		}
		return new BigDecimal(text);
	}

	public void close() {
		if (scanner != null) {
			scanner.close();
			scanner = null;
		}
	}
}
