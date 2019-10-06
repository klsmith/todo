package io.github.klsmith.todo.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import io.github.klsmith.todo.core.task.Task;
import io.github.klsmith.todo.core.task.TaskRepository;

public class WebServiceTaskRepository implements TaskRepository {

    private static final int TIMEOUT = 15 * 1000; // 15 seconds
    private static final String BASE_URI = "http://localhost:4564/api/";

    private final Gson gson;

    public WebServiceTaskRepository() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void add(Task task) {
        try {
            final URL url = new URL(BASE_URI + "task");
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            final String json = gson.toJson(task);
            connection.setDoOutput(true);
            try (final OutputStream out = connection.getOutputStream()) {
                out.write(json.getBytes(StandardCharsets.UTF_8));
            }
            connection.setReadTimeout(TIMEOUT);
            connection.connect();
            final int code = connection.getResponseCode();
            if (200 != code) {
                throw new RuntimeException(String.valueOf(code) + ": " + connection.getResponseMessage());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Task> getAll() {
        final List<Task> results = new ArrayList<>();
        try {
            final URL url = new URL(BASE_URI + "task");
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setReadTimeout(TIMEOUT);
            connection.connect();
            final int code = connection.getResponseCode();
            if (200 != code) {
                throw new RuntimeException(String.valueOf(code) + ": " + connection.getResponseMessage());
            }
            final StringBuilder jsonBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line = reader.readLine();
                while (null != line) {
                    jsonBuilder.append(line).append("\n");
                    line = reader.readLine();
                }
            }
            final String json = jsonBuilder.toString();
            final Type listType = new TypeToken<ArrayList<Task>>() {}.getType();
            try {
                final List<Task> parsedTasks = gson.fromJson(json.toString(), listType);
                results.addAll(parsedTasks);
            } catch (RuntimeException e) {
                throw new RuntimeException("Could not parse \"" + json + "\".", e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return results;
    }

    @Override
    public void remove(Task task) {
        throw new UnsupportedOperationException("Whoops");
    }

    @Override
    public Task removeByListNumber(int listNumber) {
        Task deletedTask = null;
        try {
            final URL url = new URL(BASE_URI + "task/" + listNumber);
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setReadTimeout(TIMEOUT);
            connection.connect();
            final int code = connection.getResponseCode();
            if (200 != code) {
                throw new RuntimeException(String.valueOf(code) + ": " + connection.getResponseMessage());
            }
            final StringBuilder jsonBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line = reader.readLine();
                while (null != line) {
                    jsonBuilder.append(line).append("\n");
                    line = reader.readLine();
                }
            }
            final String json = jsonBuilder.toString();
            try {
                deletedTask = gson.fromJson(json.toString(), Task.class);
            } catch (RuntimeException e) {
                throw new RuntimeException("Could not parse \"" + json + "\".", e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return deletedTask;
    }

    @Override
    public Task completeByListNumber(int listNumber) {
        Task completedTask = null;
        try {
            final URL url = new URL(BASE_URI + "task/" + listNumber + "/complete");
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setReadTimeout(TIMEOUT);
            connection.connect();
            final int code = connection.getResponseCode();
            if (200 != code) {
                throw new RuntimeException(String.valueOf(code) + ": " + connection.getResponseMessage());
            }
            final StringBuilder jsonBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line = reader.readLine();
                while (null != line) {
                    jsonBuilder.append(line).append("\n");
                    line = reader.readLine();
                }
            }
            final String json = jsonBuilder.toString();
            try {
                completedTask = gson.fromJson(json.toString(), Task.class);
            } catch (RuntimeException e) {
                throw new RuntimeException("Could not parse \"" + json + "\".", e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return completedTask;
    }

}
