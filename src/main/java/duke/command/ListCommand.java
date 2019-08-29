package duke.command;

import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

public class ListCommand extends Command{
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if(tasks.size() == 0) {
            ui.printError("The list is empty");
        }
        for(int i = 1; i <= tasks.size(); i++) {
            ui.printMessage(i + "." + tasks.get(i).toString());
        }
    }
}
