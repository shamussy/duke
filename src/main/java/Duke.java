import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

class Duke {
    private ArrayList<Task> taskLists = new ArrayList<Task>();
    private ArrayList<String> stringTaskList = new ArrayList<String>();
    private Storage storage = new Storage("data/tasks.txt");;
    private int taskCounter = 0;

    Duke() { //constructor
        greet();
        try {
            stringTaskList = storage.readFile();
            importTask();
        } catch (FileNotFoundException e) {
            //ignore
        }
    }
    private void importTask() {
        for(String line : stringTaskList) {
            String taskType = line.substring(0,1);
            String taskStatus = line.substring(4,5);
            String[] lineSplit = line.split("\\|",-1);
            int descriptionLength = Integer.parseInt(lineSplit[2].trim());
            int descriptionLengthIndex = line.indexOf(" | ", line.indexOf(" ") + 1);
            String description = line.substring(descriptionLengthIndex+7,
                    descriptionLengthIndex+7+descriptionLength);
            switch (taskType) {
                case "T":
                    Todo todo = new Todo(description);
                    if("1".equals(taskStatus)) {
                        todo.setStatus();
                    }
                    taskLists.add(todo);
                    break;
                case "D":
                    int dateIndex = descriptionLengthIndex+7+descriptionLength;
                    String leftover = line.substring(dateIndex+3,line.length());
                    String[] dateSplit = leftover.split("\\|",-1);
                    String dateLength = dateSplit[0].trim();
                    String date = line.substring(dateIndex+3+dateLength.length()+3,
                            dateIndex+3+dateLength.length()+3+Integer.parseInt(dateLength));
                    Deadline deadline = new Deadline(description, date);
                    if("1".equals(taskStatus)) {
                        deadline.setStatus();
                    }
                    taskLists.add(deadline);
                    break;
                case "E":
                    dateIndex = descriptionLengthIndex+7+descriptionLength;
                    leftover = line.substring(dateIndex+3,line.length());
                    dateSplit = leftover.split("\\|",-1);
                    dateLength = dateSplit[0].trim();
                    date = line.substring(dateIndex+3+dateLength.length()+3,
                            dateIndex+3+dateLength.length()+3+Integer.parseInt(dateLength));
                    Event event = new Event(description, date);
                    if("1".equals(taskStatus)) {
                        event.setStatus();
                    }
                    break;
            }
        }
        taskCounter = taskLists.size();
        for(int i = 0; i < taskLists.size(); i++) {
            System.out.println(i+1 + ": " + taskLists.get(i).toString());
        }
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

    String parseDeadlineTime(String input) throws DukeException, ParseException {
        int index = input.lastIndexOf(" /by ");
        String date = input.substring(index+5,input.length()); //+3 because of _/by_
        if(date.isBlank())
        {
            throw new DukeException("The time cannot be empty or space bar");
        }
        String dateRegex =  "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
        String timeRegex = "^(0[0-9]|1[0-9]|2[0-3])[0-5][0-9]$";
        String[] dateTime = date.split(" ", 2);
        if(dateTime.length != 2) {
            throw new DukeException("The format is wrong, please try in DD/MM/YYYY HHMM format");
        }
        String dateOnly = dateTime[0].trim();
        String timeOnly = dateTime[1].trim();

        if(!dateOnly.matches(dateRegex)) {
            throw new DukeException("The date format is wrong, please try in DD/MM/YYYY format");
        }
        if(!timeOnly.matches(timeRegex)) {
            throw new DukeException("The time format is wrong, please try again in HHMM format");
        }

        Date date1=new SimpleDateFormat("dd/MM/yyyy HHmm").parse(dateOnly + " " + timeOnly);
        SimpleDateFormat date2 = new SimpleDateFormat("EEEE dd MMMM yyyy hh:mm a");
        return date2.format(date1);
    }

    String parseEventTime(String input) throws DukeException, ParseException {
        int index = input.lastIndexOf(" /at ");
        String date = input.substring(index+5,input.length()); //+3 because of _/at_
        if(date.isBlank())
        {
            throw new DukeException("The time cannot be empty or space bar");
        }
        String dateRegex =  "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";
        String timeRegex = "^(0[0-9]|1[0-9]|2[0-3])[0-5][0-9]$";
        String[] dateTime = date.split(" ", 2);
        if(dateTime.length != 2) {
            throw new DukeException("The format is wrong, please try in DD/MM/YYYY HHMM format");
        }
        String dateOnly = dateTime[0].trim();
        String timeOnly = dateTime[1].trim();

        if(!dateOnly.matches(dateRegex)) {
            throw new DukeException("The date format is wrong, please try in DD/MM/YYYY format");
        }
        if(!timeOnly.matches(timeRegex)) {
            throw new DukeException("The time format is wrong, please try again in HHMM format");
        }

        Date date1=new SimpleDateFormat("dd/MM/yyyy HHmm").parse(dateOnly + " " + timeOnly);
        SimpleDateFormat date2 = new SimpleDateFormat("EEEE dd MMMM yyyy hh:mm a");
        return date2.format(date1);
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
            String[] splitInput = input.split(" /by ");
            if(input.startsWith("deadline /by")) {
                throw new DukeException("The description of a deadline cannot be empty.");
            }
            if(splitInput.length == 1) {
                throw new DukeException("The deadline requires an end date/time after specifying /by" +
                        "\nMake sure to use <space>/by<space><date>");
            }
        }
        int index = input.lastIndexOf(" /by ");
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
            String[] splitInput = input.split(" /at ");
            if(input.startsWith("event /at")) {
                throw new DukeException("The description of an event cannot be empty.");
            }
            if(splitInput.length == 1) {
                throw new DukeException("The event requires a start date/time after specifying /at" +
                        "\nMake sure to use <space>/at<space><date>");
            }
        }
        int index = input.lastIndexOf(" /at ");
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

    void addTask(Task newTask) throws IOException {
        taskLists.add(newTask);
        taskCounter++;
        String output = String.format("Got it. I've added this task: \n" + newTask.toString()
                + "\n" + "Now you have " + taskCounter + " tasks in the list.");
        System.out.println(output);
        try {
            stringTaskList = storage.objectArrayToStringArray(taskLists);
            storage.writeFile(stringTaskList);
        }
        catch (IOException FileNotFoundException){
            storage.writeFile(stringTaskList);
        }
    }

    void setTask(int taskNumber) throws DukeException, IOException {
        if(taskNumber >= 1 && taskNumber <= taskCounter) {
            if(!taskLists.get(taskNumber-1).isDone()) {
                taskLists.get(taskNumber-1).setStatus();
                System.out.println("Nice! I've marked this task as done");
                System.out.println(taskLists.get(taskNumber-1).toString());
                try {
                    stringTaskList = storage.objectArrayToStringArray(taskLists);
                    storage.writeFile(stringTaskList);
                }
                catch (IOException FileNotFoundException){
                    storage.writeFile(stringTaskList);
                }
            }
            else {
                throw new DukeException("Task is already done");
            }
        }
        else {
            throw new DukeException("Invalid task number");
        }
    }
    void exit(String input) throws DukeException, IOException {
        if(!"bye".equals(input)) {
            throw new DukeException("The bye command should not have trailing arguments");
        }
        try {
            stringTaskList = storage.objectArrayToStringArray(taskLists);
            storage.writeFile(stringTaskList);
        }
        catch (IOException FileNotFoundException){
            storage.writeFile(stringTaskList);
        }
        System.out.println("Bye. Hope to see you again soon!");
    }
}
