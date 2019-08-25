public class Deadline extends Task {
    private String by;

    Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
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
        return "D | " + doneStatus + " | " + super.getDescription().length()
                + " | " + super.getDescription() + " | " + this.by.length() + " | " + this.by;
    }
}
