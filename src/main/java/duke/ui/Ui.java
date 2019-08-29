package duke.ui;

import duke.exception.DukeException;

public class Ui {

    public void printMessage(String msg) {
        System.out.println(msg);
    }

    public void greet() {
        printMessage("Hello! I'm Duke");
        printMessage("What can I do for you?");
    }

    public void printError(String exceptionMessage) {
        printMessage("☹ OOPS!!! " + exceptionMessage);
    }


}
