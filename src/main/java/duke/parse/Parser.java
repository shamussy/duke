package duke.parse;

import java.util.Scanner;

import duke.command.AddCommand;
import duke.command.Command;
import duke.command.DeleteCommand;
import duke.command.DoneCommand;
import duke.command.ExitCommand;
import duke.command.FindCommand;
import duke.command.ListCommand;
import duke.exception.DukeException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Todo;

public class Parser {
    private final Scanner scanner = new Scanner(System.in);

    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    public Command parseLine() throws DukeException {
        String input = scanner.nextLine();
        parseIsBlank(input);
        String action = parseAction(input).trim();
        String data = parseData(input, action);
        return parseActionMenu(action, data);
    }

    private void parseIsBlank(String input) throws DukeException {
        if (input.isBlank() || input.isEmpty()) {
            throw new DukeException("User input is empty, please enter something");
        }
    }

    private String parseAction(String input) throws DukeException {
        String[] inputSplit = input.split(" ", 2);
        if ("bye".equals(inputSplit[0]) && inputSplit.length != 1) {
            throw new DukeException("bye should not contain trailing arguments");
        } else if ("list".equals(inputSplit[0]) && inputSplit.length != 1) {
            throw new DukeException("list should not contain trailing arguments");
        }
        return inputSplit[0];
    }

    private String parseData(String input, String action) {
        String data = input.substring(action.length(), input.length());
        return data;
    }

    private Command parseActionMenu(String action, String data) throws DukeException {
        switch (action) {
        case "bye":
            return new ExitCommand();
        case "list":
            return new ListCommand();
        case "done":
            return new DoneCommand(data);
        case "delete":
            return new DeleteCommand(data);
        case "find":
            return new FindCommand(data);
        case "deadline":
            return new AddCommand(Deadline.create(data));
        case "event":
            return new AddCommand(Event.create(data));
        case "todo":
            return new AddCommand(Todo.create(data));
        default:
            throw new DukeException("I'm sorry, but I don't know what that means :-(");
        }
    }
}
