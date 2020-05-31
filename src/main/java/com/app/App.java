package com.app;

import com.app.exceptions.MyException;
import com.app.service.MenuService;

/**
 * @author Pawel Dlugolecki
 */
public class App {
	public static void main(String[] args) {
		try {
			String filename = "cars";
			new MenuService(filename)
					.menu();
		} catch (MyException e) {
			System.err.println(e.toString());
		}
	}
}
