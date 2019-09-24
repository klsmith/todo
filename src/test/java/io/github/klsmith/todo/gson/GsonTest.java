package io.github.klsmith.todo.gson;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.google.gson.GsonBuilder;

import io.github.klsmith.todo.importance.Importance;
import io.github.klsmith.todo.task.Task;
import io.github.klsmith.todo.urgency.UrgencyFactory;

class GsonTest {

	@Test
	void test() {
		final String expected = "{\n" +
				"  \"text\": \"Go to Doctor\\\\u0027s Appointment\",\n" +
				"  \"complete\": false,\n" +
				"  \"importance\": \"NEED\",\n" +
				"  \"urgency\": {\n" +
				"    \"level\": \"DATE\",\n" +
				"    \"dateTime\": {\n" +
				"      \"date\": {\n" +
				"        \"year\": 2019,\n" +
				"        \"month\": 9,\n" +
				"        \"day\": 30\n" +
				"      },\n" +
				"      \"time\": {\n" +
				"        \"hour\": 17,\n" +
				"        \"minute\": 0,\n" +
				"        \"second\": 0,\n" +
				"        \"nano\": 0\n" +
				"      }\n" +
				"    }\n" +
				"  }\n" +
				"}";
		final Task task = new Task("Go to Doctor\\u0027s Appointment", false, Importance.NEED,
				UrgencyFactory.fromDate(LocalDateTime.of(2019, 9, 30, 17, 0)));
		final String actual = new GsonBuilder().setPrettyPrinting().create().toJson(task);
		assertEquals(expected, actual);
	}

}
