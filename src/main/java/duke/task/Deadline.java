package duke.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import duke.exception.DukeException;

public class Deadline extends Task {
    private final LocalDateTime by;
    private static final DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    private static final DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy hh:mm a");

    Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    public static Deadline create(String data) throws DukeException {
        String description = parseDeadlineDesc(data);
        LocalDateTime by = parseDeadlineTime(data);
        return new Deadline(description, by);
    }

    private static String parseDeadlineDesc(String data) throws DukeException {
        if (data.isEmpty() || data.isBlank()) {
            throw new DukeException("Description or date cannot be empty or blank spaces only");
        }
        if (!data.contains(" /by ")) {
            throw new DukeException("Deadline must contain an end date using /by ");
        }
        if ("deadline".equals(data)) {
            throw new DukeException("The description of a deadline cannot be empty.");
        }
        String[] splitInput = data.split(" /by ");
        if (data.startsWith("deadline /by")) {
            throw new DukeException("The description of a deadline cannot be empty.");
        }
        if (splitInput.length == 1) {
            throw new DukeException("The deadline requires an end date/time after specifying /by" +
                    ". Make sure to use <space>/by<space><date>");
        }
        int index = data.lastIndexOf(" /by ");
        String description = data.substring(0, index);
        if (description.isBlank()) {
            throw new DukeException("The description of a deadline cannot be " +
                    "empty or space even when /by is correct");
        }
        description = description.trim();
        return description;
    }

    private static LocalDateTime parseDeadlineTime(String data) throws DukeException {
        int index = data.lastIndexOf(" /by ");
        String date = data.substring(index + 5, data.length()); //+5 because of _/by_
        if (date.isBlank() || date.isEmpty()) {
            throw new DukeException("The time cannot be empty or space bar");
        }

        String dateRegex = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
        String timeRegex = "^(0[0-9]|1[0-9]|2[0-3])[0-5][0-9]$";
        String[] dateTime = date.split(" ", 2);
        if (dateTime.length != 2) {
            throw new DukeException("The format is wrong, please try in DD/MM/YYYY HHMM format");
        }
        String dateOnly = dateTime[0].trim();
        String timeOnly = dateTime[1].trim();

        if (!dateOnly.matches(dateRegex)) {
            throw new DukeException("The date format is wrong, please try in DD/MM/YYYY format");
        }
        if (!timeOnly.matches(timeRegex)) {
            throw new DukeException("The time format is wrong, please try again in HHMM format");
        }

        try {
            return LocalDateTime.parse(date, inputFormatter);
        } catch (DateTimeParseException e) {
            throw new DukeException("Time must be in the format day#/month#/yyyy hhmm.");
        }
    }

    @Override
    public String toString() {
        return "[D]" + super.toString()
                + " (by: " + this.by.format(displayFormatter) + ")";
    }

    @Override
    public String export() {
        return "D | " + super.export() + super.getDescription().length() + " | " + super.getDescription()
                + " | " + this.by.format(inputFormatter).length() + " | " + this.by.format(inputFormatter);
    }
}
