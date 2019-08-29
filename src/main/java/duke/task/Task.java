package duke.task;

public class Task {
    private final String description;
    private boolean done;

    Task(String description) {
        this.description = description;
        this.done = false;
    }

    void markDone() {
        done = true;
    }

    public boolean isDone() {
        return done;
    }

    private String getDoneChar() {
        return done ? "✓" : "✘";
    }

    String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        String boxedChar = "[" + getDoneChar() + "]";
        return boxedChar + " " + description;
    }

    public String export() {
        return (done ? "1 | " : "0 | ");
    }
}
