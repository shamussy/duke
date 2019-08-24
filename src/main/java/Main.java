import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws DukeException{
        Duke duke = new Duke();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            String action = duke.parseAction(input);
            String description = "";
            switch (action) {
            case "todo":
                try {
                    description = duke.parseToDoDesc(action, input);
                    Todo todo = new Todo(description);
                    duke.addTask(todo);
                }
                catch (DukeException exceptionMessage) {
                    System.out.println(exceptionMessage.toString());
                }

                break;
            case "deadline":
                try {
                    description = duke.parseDeadlineDesc(action, input);
                    String by = duke.parseDeadlineTime(input);
                    Deadline deadline = new Deadline(description, by);
                    duke.addTask(deadline);
                }
                catch (DukeException exceptionMessage) {
                    System.out.println(exceptionMessage.toString());
                }
                break;
            case "event":
                try {
                    description = duke.parseEventDesc(action, input);
                    String at = duke.parseEventTime(input);
                    Event event = new Event(description, at);
                    duke.addTask(event);
                }
                catch (DukeException exceptionMessage) {
                    System.out.println(exceptionMessage.toString());
                }
                break;
            case "done":
                try {
                    int taskNumber = Integer.parseInt(duke.parseTaskNumber(input));
                    duke.setTask(taskNumber);
                }
                catch (DukeException exceptionMessage) {
                    System.out.println(exceptionMessage.toString());
                }
                break;
            case "list":
                try {
                    duke.listTask(input);
                }
                catch (DukeException exceptionMessage) {
                    System.out.println(exceptionMessage.toString());
                }
                break;
            case "bye":
                duke.exit();
                return; //no break since it exits program
            default:
                System.out.println("I'm sorry, but I don't know what that means :-(");
            }
        }
    }
}
