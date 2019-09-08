package duke.ui;

public class UiTest extends Ui {

    @Override
    public void printMessage(String msg) {
        System.out.println("Ui.Test: printMessage has received: " + msg);
    }
}
