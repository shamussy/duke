package duke.command;

import java.io.IOException;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

public class DoneCommand extends Command{

    private final int taskNumber;
    private final String data;

    public DoneCommand(String data) throws DukeException {
        data = data.trim();
        this.data = data;
        String pattern = "^[0-9]+$";
        if(!data.matches(pattern)) {
            throw new DukeException("The task number should be numeric only");
        }
        else {
            try {
                this.taskNumber = Integer.parseInt(data);
            } catch (NumberFormatException exceptionMessage) {
                throw new DukeException("The number must be an integer and cannot exceed 9 digits");
            }
        }
    }

    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            if(tasks.get(taskNumber).isDone()) {
                ui.printError("The task is already done");
                ui.printMessage(tasks.get(taskNumber).toString());
            }
            else {
                try {
                    tasks.markDone(taskNumber);
                    ui.printMessage("Nice! I've marked this task as done:");
                    ui.printMessage(tasks.get(taskNumber).toString());
                    try {
                        storage.writeFile(tasks.export());
                    } catch (IOException e) {
                        ui.printError("Error writing tasks to file");
                    }
                } catch (IndexOutOfBoundsException exceptionMessage) {
                    ui.printError("Invalid task number, there are " + tasks.size() + " tasks");
                    ui.printError("You entered: " + data);
                }
            }
        } catch (IndexOutOfBoundsException exceptionMessage) {
            ui.printError("Invalid task number, there are " + tasks.size() + " tasks");
            ui.printError("You entered: " + data);
        }
    }
}
