package duke.command;

import java.io.IOException;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

public class DeleteCommand extends Command {

    private final int taskNumber;
    private final String data;

    public DeleteCommand(String data) throws DukeException {
        data = data.trim();
        this.data = data;
        String pattern = "^[0-9]+$";
        if (!data.matches(pattern)) {
            throw new DukeException("The task number should be numeric only");
        } else {
            try {
                this.taskNumber = Integer.parseInt(data);
            } catch (NumberFormatException exceptionMessage) {
                throw new DukeException("The number must be an integer and cannot exceed 9 digits");
            }
        }
    }

    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if (this.taskNumber <= tasks.size()) {
            try {
                String taskInformation = tasks.get(taskNumber).toString();
                tasks.delete(this.taskNumber);
                ui.printMessage("Noted. I've removed this task: ");
                ui.printMessage(taskInformation);
                ui.printMessage("Now you have " + tasks.size() + " tasks in the list.");
                try {
                    storage.writeFile(tasks.export());
                } catch (IOException e) {
                    ui.printError("Error writing tasks to file");
                }
            } catch (IndexOutOfBoundsException exceptionMessage) {
                ui.printError("Invalid task number, there are " + tasks.size() + " tasks");
                ui.printError("You entered: " + data);
            }
        } else {
            ui.printError("Invalid task number, there are " + tasks.size() + " tasks");
        }

    }
}
