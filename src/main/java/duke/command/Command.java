package duke.command;

import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

public abstract class Command {
    boolean exit = false;

    public boolean isExit() {
        return exit;
    }

    public abstract void execute(TaskList tasks, Ui ui, Storage storage );
}
