package duke;

import java.io.FileNotFoundException;

import duke.command.Command;
import duke.exception.DukeException;
import duke.parse.Parser;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

public class Main {
    public static void main(String[] args) throws DukeException {
        Ui ui;
        Parser parser;
        TaskList tasks;
        Storage storage;
        Command command;

        ui = new Ui();
        parser = new Parser();
        storage = new Storage("data/tasks.txt");

        try {
            tasks = new TaskList(storage.readFile());
        } catch (FileNotFoundException e) {
            ui.printError("Could not read tasks from disk, will start with empty file");
            tasks = new TaskList();
        }

        ui.greet();

        boolean hasExited = false;

        while (!hasExited && parser.hasNextLine()) {
            try {
                command = parser.parseLine();
                command.execute(tasks, ui, storage);
                hasExited = command.isExit();
            } catch (DukeException exceptionMessage) {
                ui.printError(exceptionMessage.toString());
            }
        }
    }
}

