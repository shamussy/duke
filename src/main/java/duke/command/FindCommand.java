package duke.command;

import java.util.ArrayList;

import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;
import duke.task.Task;

public class FindCommand extends Command{

    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword.trim();
    }

    public void execute(TaskList tasks, Ui ui, Storage storage) {
        if(!keyword.isEmpty() && !keyword.isBlank()) {
            ArrayList<Task> result = tasks.filter(keyword);
            if (result.size() == 0) {
                ui.printError("There are no tasks that match this keyword");
            } else {
                ui.printMessage("Here are the matching tasks in your list:");
                for (int i = 0; i < result.size(); i++) {
                    ui.printMessage(i + 1 + "." + result.get(i).toString());
                }
            }
        } else {
            ui.printError("Keyword cannot be empty");
        }
    }
}
