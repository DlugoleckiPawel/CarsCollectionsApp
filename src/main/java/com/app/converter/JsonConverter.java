package com.app.converter;

import com.app.exceptions.ExceptionInfo;
import com.app.exceptions.MyException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

public abstract class JsonConverter<T> {
	private final String jsonFilename;
	private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private final Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

	public JsonConverter(String jsonFilename) {
		this.jsonFilename = jsonFilename;
	}

	public Optional<T> fromJson() {
		try (FileReader fileReader = new FileReader(jsonFilename)) {
			return Optional.of(gson.fromJson(fileReader, type));
		} catch (Exception e) {
			throw new MyException(ExceptionInfo.JSON, "FROM JSON FILENAME EXCEPTION");
		}
	}
}
