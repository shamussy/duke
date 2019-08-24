import java.util.ArrayList;

class Duke {
    private ArrayList<Task> taskLists = new ArrayList<Task>();
    private int taskCounter = 0;

    Duke() { //constructor
        greet();
    }

    private void greet() {
        System.out.println("Hello! I'm Duke \nWhat can I do for you?");
    }

    String parseAction(String input) {
        String[] inputSplit = input.split(" ", 2);
        return inputSplit[0];
    }

    String parseTaskNumber(String input) throws DukeException{
        if("done".equals(input)) {
            throw new DukeException("Task number cannot be empty, use <list> to see task number");
        }
        else {
            String[] inputSplit = input.split(" ");
            if (inputSplit.length == 1) {
                throw new DukeException("Task number cannot be empty, use <list> to see task number");
            }
            else if (inputSplit.length > 2) {
                throw new DukeException("Only the task number should be specified");
            }
            else if(inputSplit[1].isBlank()){
                throw new DukeException("The task number cannot be a space bar");
            }
            else if(!inputSplit[1].matches("^[0-9]+$")) {
                throw new DukeException("The task number should be numeric only");
            }
            return inputSplit[1];
        }
    }

    String parseTaskTime(String input) throws DukeException{
        int index = input.lastIndexOf("/");
        String date = input.substring(index+4,input.length()); //+4 because of /by_ or /at_
        if(date.isBlank())
        {
            throw new DukeException("The time cannot be empty or space bar");
        }
        return date;
    }

    String parseToDoDesc(String action, String input) throws DukeException {
        if("todo".equals(input)) {
            throw new DukeException("The description of a todo cannot be empty.");
        }
        else {
            String[] inputSplit = input.split(" ");
            if(inputSplit.length == 1) {
                throw new DukeException("The description of a todo cannot be empty");
            }
            else if(inputSplit[1].isBlank()){
                throw new DukeException("The description of a todo cannot be a space bar");
            }
        }
        return input.substring(action.length()+1, input.length()); //+1 because of the space bar
    }

    String parseDeadlineDesc(String action, String input) throws DukeException {
        if("deadline".equals(input)) {
            throw new DukeException("The description of a deadline cannot be empty.");
        }
        else if(!input.contains("/by")){
            throw new DukeException("The deadline requires an end date/time using /by");
        }
        else {
            String[] splitInput = input.split("/by ");
            if(input.startsWith("deadline /by")) {
                throw new DukeException("The description of a deadline cannot be empty.");
            }
            if(splitInput.length == 1) {
                throw new DukeException("The deadline requires an end date/time after specifying /by");
            }
        }
        int index = input.indexOf("/by");
        String description = input.substring(action.length()+1,index);//+1 because of the space bar_
        if(description.isBlank()) {
            throw new DukeException("The description of a deadline cannot be " +
                    "empty or space even when /by is correct");
        }
        return description;
    }

    String parseEventDesc(String action, String input) throws DukeException{
        if("event".equals(input)) {
            throw new DukeException("The description of a event cannot be empty.");
        }
        else if(!input.contains("/at")){
            throw new DukeException("The event requires a start date/time");
        }
        else {
            String[] splitInput = input.split("/at ");
            if(input.startsWith("event /at")) {
                throw new DukeException("The description of an event cannot be empty.");
            }
            if(splitInput.length == 1) {
                throw new DukeException("The event requires a start date/time after specifying /at");
            }
        }
        int index = input.indexOf("/at");
        String description = input.substring(action.length()+1,index);//+1 because of the space bar_
        if(description.isBlank()) {
            throw new DukeException("The description of a deadline cannot be " +
                    "empty or space even when /by is correct");
        }
        return description;
    }

    void listTask(String input) throws DukeException {
        if(!"list".equals(input)) {
            throw new DukeException("The list command should not have trailing arguments");
        }
        if(taskLists.size() == 0) {
            throw new DukeException("The list is empty");
        }
        for(int i = 0; i < taskLists.size(); i++) {
            System.out.println(i+1 + ": " + taskLists.get(i).toString());
        }
    }

    void addTask(Task newTask) {
        taskLists.add(newTask);
        taskCounter++;
        String output = String.format("Got it. I've added this task: \n" + newTask.toString()
                + "\n" + "Now you have " + taskCounter + " tasks in the list.");
        System.out.println(output);
    }

    void setTask(int taskNumber) throws DukeException{
        if(taskNumber >= 1 && taskNumber <= taskCounter) {
            if(!taskLists.get(taskNumber-1).isDone()) {
                taskLists.get(taskNumber-1).setStatus();
                System.out.println("Nice! I've marked this task as done");
                System.out.println(taskLists.get(taskNumber-1).toString());
            }
            else {
                throw new DukeException("Task is already done");
            }
        }
        else {
            throw new DukeException("Invalid task number");
        }
    }
    void exit() {
        System.out.println("Bye. Hope to see you again soon!");
    }
}
