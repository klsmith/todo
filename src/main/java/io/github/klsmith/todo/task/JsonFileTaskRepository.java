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
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JsonFileTaskRepository implements TaskRepository {

    private static final String TODO_HOME_ENV = "TODO_HOME";
    private static final String DATA_PATH_PIECE = "/data/";
    private static final String REPO_FILE_NAME = "repo.json";
    private static final String CONFIG_FILE_NAME = "repo.config.json";

    private final String home;
    private final Gson gson;
    private final JsonFileTaskRepositoryConfiguration config;

    public JsonFileTaskRepository() {
        home = System.getenv().get(TODO_HOME_ENV);
        if (null == home) {
            throw new IllegalStateException("Must have TODO_HOME environement variable set.");
        }
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        config = loadConfig();
    }

    private JsonFileTaskRepositoryConfiguration loadConfig() {
        final String filePath = getConfigFilePath();
        final String json = getFileAsString(filePath);
        final JsonFileTaskRepositoryConfiguration result;
        if (null == json || json.isEmpty()) {
            result = new JsonFileTaskRepositoryConfiguration();
            writeFileFromString(filePath, gson.toJson(result));
        } else {
            result = gson.fromJson(json, JsonFileTaskRepositoryConfiguration.class);
        }
        return result;
    }

    private String getFileAsString(String filePath) {
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

    private void writeFileFromString(String filePath, String string) {
        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            try {
                writer.write(string);
            } catch (IOException e) {
                System.err.printf("An error occured writing to file:\"%s\", not stopping execution.%n", filePath);
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getConfigFilePath() {
        return getDataDirectoryPath() + CONFIG_FILE_NAME;
    }

    private String getRepoFilePath() {
        return getDataDirectoryPath() + REPO_FILE_NAME;
    }

    private String getDataDirectoryPath() {
        final String dataPath = home + DATA_PATH_PIECE;
        final File dataDirectory = new File(dataPath);
        if (!dataDirectory.exists()) {
            dataDirectory.mkdirs();
        }
        return dataPath;
    }

    @Override
    public void add(Task task) {
        final List<Task> list = getAll();
        if (config.allowDuplicates() || !list.contains(task)) {
            list.add(task);
        }
        save(list);
    }

    @Override
    public List<Task> getAll() {
        final String json = getFileAsString(getRepoFilePath());
        final Type listType = new TypeToken<ArrayList<Task>>() {}.getType();
        final List<Task> results = gson.fromJson(json.toString(), listType);
        if (null != results) {
            Collections.sort(results);
            return results;
        }
        return new ArrayList<>();
    }

    public void save(List<Task> list) {
        final String filePath = getRepoFilePath();
        writeFileFromString(filePath, gson.toJson(list));
    }

    @Override
	public Task removeByListNumber(int listNumber) {
        final List<Task> list = getAll();
        final Task task = list.get(listNumber - 1);
        list.remove(task);
        save(list);
        return task;
    }

    @Override
    public void remove(Task task) {
        final List<Task> list = getAll();
        list.remove(task);
        save(list);
    }

    @Override
    public Task completeByListNumber(int listNumber) {
        final List<Task> list = getAll();
        final Task task = list.get(listNumber - 1);
        final Task completedTask = new Task(
                task.getText(),
                true,
                task.getImportance(),
                task.getUrgency());
        list.set(listNumber - 1, completedTask);
        save(list);
        return completedTask;
    }

}
