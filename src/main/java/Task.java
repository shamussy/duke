public class Task {
    private String description;
    boolean isDone;

    Task(String description) { //constructor
        this.description = description;
        this.isDone = false;
    }

    String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols using ternary operators
    }
    public boolean isDone() {
        return isDone;
    }

    String getDescription() {
        return this.description;
    }

    void setStatus() {
        this.isDone = true;
    }
}
