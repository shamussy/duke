public class Event extends Task {
    private String at;

    Event(String description, String at) {
        super(description);
        this.at = at;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: " + at + ")";
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
        return "E | " + doneStatus + " | " + super.getDescription().length()
                + " | " + super.getDescription() + " | " + this.at.length() + " | " + this.at;
    }
}
