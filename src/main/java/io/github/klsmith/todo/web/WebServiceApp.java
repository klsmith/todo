package io.github.klsmith.todo.web;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.github.klsmith.todo.task.JsonFileTaskRepository;
import io.github.klsmith.todo.task.Task;
import io.github.klsmith.todo.task.TaskRepository;
import spark.Spark;

public class WebServiceApp {

	public static void main(String[] args) {
		Spark.port(4564);
		final Gson gson = new GsonBuilder().setPrettyPrinting().create();
		final TaskRepository repo = new JsonFileTaskRepository();
		Spark.get("/api/task", (request, response) -> {
			final List<Task> tasks = repo.getAll();
			return gson.toJson(tasks);
		});
		Spark.get("/api/task/:listNumber", (request, response) -> {
			final int listNumber = Integer.parseInt(request.params("listNumber"));
			final Task task = repo.getAll().get(listNumber - 1);
			return gson.toJson(task);
		});
		Spark.post("/api/task", (request, response) -> {
			final Task task = gson.fromJson(request.body(), Task.class);
			repo.add(task);
			return "";
		});
		Spark.delete("/api/task/:listNumber", (request, response) -> {
			final int listNumber = Integer.parseInt(request.params("listNumber"));
			final Task deleted = repo.removeByListNumber(listNumber);
			return gson.toJson(deleted);
		});
		Spark.post("/api/task/:listNumber/complete", (request, response) -> {
			final int listNumber = Integer.parseInt(request.params("listNumber"));
			final Task completed = repo.completeByListNumber(listNumber);
			return gson.toJson(completed);
		});
	}

}
