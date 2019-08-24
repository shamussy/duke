import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    private ArrayList<Task> taskLists = new ArrayList<Task>();
    private int taskCounter = 0;

    private Duke() { //constructor
        greet();
    }

    private void greet() {
        System.out.println("Hello! I'm Duke \nWhat can I do for you?");
    }

    private String parseAction(String input) {
        String[] input_splitted = input.split(" ", 2);
        return input_splitted[0];
    }

    private String parseTaskNumber(String input) {
        String[] input_splitted = input.split(" ");
        return input_splitted[1];
    }

    private String parseTaskTime(String input) {
        int index = input.lastIndexOf("/");
        return input.substring(index+4,input.length()); //+4 because /by_ or /at_
    }

    private String parseToDoDesc(String action, String input) {
        return input.substring(action.length()+1, input.length()); //+1 because spacebar
    }

    private String parseDeadlineDesc(String action, String input) {
        int index = input.indexOf("/by");
        return input.substring(action.length()+1,index); //+1 because spacebar_
    }

    private String parseEventDesc(String action, String input) {
        int index = input.indexOf("/at");
        return input.substring(action.length()+1,index); //+1 because spacebar_
    }

    private void listTask() {
        for(int i = 0; i < taskLists.size(); i++) {
            System.out.println(i+1 + ": " + taskLists.get(i).toString());
        }
    }

    private void addTask(Task newTask) {
        taskLists.add(newTask);
        taskCounter++;
        String output = String.format("Got it. I've added this task: \n" + newTask.toString() + "\n" + "Now you have " + taskCounter + " tasks in the list.");
        System.out.println(output);
    }

    private void setTask(int taskNumber) {
        if(taskNumber >= 1 && taskNumber <= taskCounter) {
            if(!taskLists.get(taskNumber-1).isDone()) {
                taskLists.get(taskNumber-1).setStatus();
                System.out.println("Nice! I've marked this task as done");
                System.out.println(taskLists.get(taskNumber-1).toString());
            }
            else {
                System.out.println("Task is already done");
            }
        }
        else {
            System.out.println("Invalid task number");
        }

    }

    private void exit() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public static void main(String[] args) {
        Duke duke = new Duke();
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()) {
            String input = scanner.nextLine();
            String action = duke.parseAction(input);
            switch(action) {
            case "todo":
                String description = duke.parseToDoDesc(action, input);
                Todo todo = new Todo (description);
                duke.addTask(todo);
                break;
            case "deadline":
                description = duke.parseDeadlineDesc(action, input);
                String by = duke.parseTaskTime(input);
                Deadline deadline = new Deadline(description,by);
                duke.addTask(deadline);
                break;
            case "event":
                description = duke.parseEventDesc(action, input);
                String at = duke.parseTaskTime(input);
                Event event = new Event(description,at);
                duke.addTask(event);
                break;
            case "done":
                int taskNumber = Integer.parseInt(duke.parseTaskNumber(input));
                duke.setTask(taskNumber);
                break;
            case "list":
                duke.listTask();
                break;
            case "bye":
                duke.exit();
                return; //no break since it exits program
            default:
                System.out.println("Invalid input");
            }
        }
    }
}
