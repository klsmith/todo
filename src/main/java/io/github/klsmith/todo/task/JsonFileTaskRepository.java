package io.github.klsmith.todo.task;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JsonFileTaskRepository implements TaskRepository {

	private static final String TODO_HOME_ENV = "TODO_HOME";

	private final String home;
	private final Gson gson;

	public JsonFileTaskRepository() {
		home = System.getenv().get(TODO_HOME_ENV);
		if (null == home) {
			throw new IllegalStateException("Must have TODO_HOME environement variable set.");
		}
		gson = new GsonBuilder()
				.setPrettyPrinting()
				.create();
	}

	private String getFilePath() {
		final String dataPath = home + "/data/";
		final File dataDirectory = new File(dataPath);
		if (!dataDirectory.exists()) {
			dataDirectory.mkdirs();
		}
		return dataPath + "repo.json";
	}

	@Override
	public void add(Task task) {
		final List<Task> list = getAll();
		list.add(task);
		save(list);
	}

	@Override
	public List<Task> getAll() {
		final String filePath = getFilePath();
		final StringBuilder json = new StringBuilder();
		try (final BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line = reader.readLine();
			while (line != null) {
				json.append(line + "\n");
				line = reader.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		final Type listType = new TypeToken<ArrayList<Task>>() {
		}.getType();
		return gson.fromJson(json.toString(), listType);
	}

	public void save(List<Task> list) {
		final String filePath = getFilePath();
		try (final BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
			try {
				writer.write(gson.toJson(list));
			} catch (IOException e) {
				System.err.printf("An error occured writing to file:\"%s\", not stopping execution.%n", filePath);
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
