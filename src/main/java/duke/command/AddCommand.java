package duke.command;

import java.io.IOException;

import duke.exception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

public class AddCommand extends Command {
    private final Task task;

    public AddCommand(Task task) {
        this.task = task;
    }

    public void execute(TaskList tasks, Ui ui, Storage storage) {
        tasks.add(task);
        ui.printMessage("Got it. I've added this task:");
        ui.printMessage(task.toString());
        int size = tasks.size();
        ui.printMessage("You now have " + size + " tasks in the list");

        try {
            storage.writeFile(tasks.export());
        } catch (IOException e) {
            ui.printError("Error writing tasks to file");
        }
    }
}
