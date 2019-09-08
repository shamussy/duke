package duke.task;

public class TaskTest extends Task {
    public TaskTest() {
        super("");
    }

    @Override
    public String toString() {
        return done ? "TaskTest: task done" : "TaskTest: task not done";
    }

    @Override
    public String export() {
        return done ? "TaskTest: export done" : "TaskTest: export not done";
    }
}
