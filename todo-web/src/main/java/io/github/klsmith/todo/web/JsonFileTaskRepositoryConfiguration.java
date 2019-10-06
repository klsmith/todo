package io.github.klsmith.todo.web;

public class JsonFileTaskRepositoryConfiguration {

	private final boolean allowAddDuplicates;

	public JsonFileTaskRepositoryConfiguration() {
		allowAddDuplicates = false;
	}

	public JsonFileTaskRepositoryConfiguration(boolean allowDuplicates) {
		this.allowAddDuplicates = allowDuplicates;
	}

	public boolean allowDuplicates() {
		return allowAddDuplicates;
	}

}
