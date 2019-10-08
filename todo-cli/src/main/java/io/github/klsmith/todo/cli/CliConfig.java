package io.github.klsmith.todo.cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CliConfig {

	private static final String TODO_HOME_ENV = "TODO_HOME";
	private static final String CONFIG_FILE = "cli.config.json";

	private String baseApiUri;

	public static CliConfig load() {
		final String homeDirectory = System.getenv().get(TODO_HOME_ENV);
		if (null == homeDirectory) {
			throw new IllegalStateException("Must have TODO_HOME environement variable set.");
		}
		final String json = getFileAsString(homeDirectory + CONFIG_FILE);
		final Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.create();
		return gson.fromJson(json, CliConfig.class);
	}

	private static String getFileAsString(String filePath) {
		final File file = new File(filePath);
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		final StringBuilder stringBuilder = new StringBuilder();
		try (final BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line = reader.readLine();
			while (line != null) {
				stringBuilder.append(line + "\n");
				line = reader.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringBuilder.toString();
	}

	public String getBaseApiUri() {
		return baseApiUri;
	}

}
