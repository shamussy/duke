package duke.task;

import duke.exception.DukeException;

public class Todo extends Task{

    Todo(String description) {
        super(description);
    }

    public static Todo create(String description) throws DukeException {
        if(description.isEmpty() || description.isBlank()) {
            throw new DukeException("The description of a todo cannot be empty or blank spaces only");
        }
        description = description.trim();
        return new Todo(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String export() {
        return "T | " + super.export() + super.getDescription().length() + " | " + super.getDescription();
    }
}
