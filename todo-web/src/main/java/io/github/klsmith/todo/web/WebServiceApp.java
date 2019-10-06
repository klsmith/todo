package io.github.klsmith.todo.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.github.klsmith.todo.core.task.Task;
import io.github.klsmith.todo.core.task.TaskRepository;
import spark.Spark;

public class WebServiceApp {

    private static final Logger logger = LoggerFactory.getLogger(WebServiceApp.class);

    public static void main(String[] args) {
        System.setProperty("org.slf4j.simpleLogger.logFile", "System.out");
        Spark.port(4564);
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final TaskRepository repo = new JsonFileTaskRepository();
        Spark.get("/api/task", (request, response) -> {
            final List<Task> tasks = repo.getAll();
            logger.info("GET /api/task tasks={}", tasks);
            return gson.toJson(tasks);
        });
        Spark.get("/api/task/:listNumber", (request, response) -> {
            final int listNumber = Integer.parseInt(request.params("listNumber"));
            final Task task = repo.getAll().get(listNumber - 1);
            logger.info("GET /api/task/{} task={}", listNumber, task);
            return gson.toJson(task);
        });
        Spark.post("/api/task", (request, response) -> {
            final String json = request.body();
            final Task task = gson.fromJson(json, Task.class);
            logger.info("POST /api/task newTask={}", task);
            repo.add(task);
            return "";
        });
        Spark.delete("/api/task/:listNumber", (request, response) -> {
            final int listNumber = Integer.parseInt(request.params("listNumber"));
            final Task deleted = repo.removeByListNumber(listNumber);
            logger.info("DELETE /api/task/{} deletedTask={}", listNumber, deleted);
            return gson.toJson(deleted);
        });
        Spark.post("/api/task/:listNumber/complete", (request, response) -> {
            final int listNumber = Integer.parseInt(request.params("listNumber"));
            final Task completed = repo.completeByListNumber(listNumber);
            logger.info("POST /api/task/{}/complete completedTask={}", listNumber, completed);
            return gson.toJson(completed);
        });
    }

}
