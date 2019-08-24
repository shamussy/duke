import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    private ArrayList<Task> taskLists = new ArrayList<Task>();

    private Duke() { //constructor
        greet();
    }

    private void greet() {
        System.out.println("Hello! I'm Duke \nWhat can I do for you?");
    }

    private void exit() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    private void getTask(int taskNumber) {
        String statusIcon = taskLists.get(taskNumber).getStatusIcon();
        String taskDescription =  taskLists.get(taskNumber).getDescription();
        System.out.println("[ " + statusIcon  + " ] " + taskDescription);
    }
    private void listTasks() {
        for(int i = 0; i < taskLists.size(); i++)
        {
            String statusIcon = taskLists.get(i).getStatusIcon();
            String taskDescription =  taskLists.get(i).getDescription();
            System.out.println(i+1 + ".[ " + statusIcon + " ] " + taskDescription);
        }
    }
    private void addTask (Task newTask) {
        taskLists.add(newTask);
        System.out.println("Added: " + newTask.getDescription());
    }

    private void setTaskDone (int taskNumber) {
        if(!taskLists.get(taskNumber).isDone) {
            taskLists.get(taskNumber).setStatus();
            System.out.println("Nice! I've marked this task as done");
        }
        else
        {
            System.out.println("Task is already done");
        }
    }

    private int getNoTask () {
        return taskLists.size();
    }

    public static void main(String[] args) {
        Duke duke = new Duke();
        Scanner scanner = new Scanner(System.in);
        while(true) {
            String input = scanner.nextLine();
            String[] input_splitted = input.split(" ");
            while(!"bye".equals(input)) {
                if("list".equals(input)) {
                    duke.listTasks();
                }
                else if("done".equals(input_splitted[0])) {
                    int taskNo = Integer.parseInt(input_splitted[1]);
                    if(taskNo >= 1 && taskNo <= duke.getNoTask()) {
                        duke.setTaskDone(taskNo-1);
                        duke.getTask(taskNo-1);
                    }
                    else
                    {
                        System.out.println("Invalid Task Number");
                    }
                }
                else {
                    if(!input.isEmpty() && !input.isBlank()) {
                        Task task = new Task(input);
                        duke.addTask(task);
                    }
                    else {
                        System.out.println("Please do not leave blanks!");
                    }
                }
                input = scanner.nextLine();
                input_splitted = input.split(" ");
            }
            duke.exit();
            return;
        }
    }
}
