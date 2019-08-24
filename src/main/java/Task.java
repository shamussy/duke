public class Task {
    private String description;
    private boolean isDone;

    Task(String description) { //constructor
        this.description = description;
        this.isDone = false;
    }

    private String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols using ternary operators
    }

    boolean isDone() {
        return this.isDone;
    }

    private String getDescription() {
        return this.description;
    }

    void setStatus() {
        this.isDone = true;
    }

    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.getDescription();
    }
}
