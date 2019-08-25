public class Todo extends Task {
    Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    @Override
    public String export() {
        String doneStatus = "";
        if(super.isDone()) {
            doneStatus = "1";
        }
        else {
            doneStatus = "0";
        }
        return "T | " + doneStatus + " | " + super.getDescription().length()
                + " | " + super.getDescription();
    }
}
