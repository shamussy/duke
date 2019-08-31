package duke.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;
    public TaskList() {
        tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<String> stringTasks) {
        tasks = new ArrayList<>();
        for(String line : stringTasks) {
            String taskType = line.substring(0,1);
            String taskStatus = line.substring(4,5);
            String[] lineSplit = line.split("\\|",-1);
            int descriptionLength = Integer.parseInt(lineSplit[2].trim());
            int descriptionLengthIndex = line.indexOf(" | ", line.indexOf(" ") + 1);
            int descriptionLengthLength = lineSplit[2].length();
            int startOfDescriptionIndex = descriptionLengthIndex+1+descriptionLengthLength+3;
            String description = line.substring(startOfDescriptionIndex,
                    startOfDescriptionIndex+descriptionLength);
            switch (taskType) {
            case "T":
                Todo todo = new Todo(description);
                if("1".equals(taskStatus)) {
                    todo.markDone();
                }
                tasks.add(todo);
                break;
            case "D":
                String date = line.substring(startOfDescriptionIndex+descriptionLength,line.length());
                String[] dateSplit = date.split("\\|",-1);
                int dateLength = Integer.parseInt(dateSplit[1].trim());
                int dateLengthLength = dateSplit[1].length();
                int startOfDateIndex = 2+dateLengthLength+2;
                String dateString = date.substring(startOfDateIndex,date.length());
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
                LocalDateTime by = LocalDateTime.parse(dateString,inputFormatter);
                Deadline deadline = new Deadline(description,by);
                if("1".equals(taskStatus)) {
                    deadline.markDone();
                }
                tasks.add(deadline);
                break;

            case "E":
                date = line.substring(startOfDescriptionIndex+descriptionLength,line.length());
                dateSplit = date.split("\\|",-1);
                dateLength = Integer.parseInt(dateSplit[1].trim());
                dateLengthLength = dateSplit[1].length();
                startOfDateIndex = 2+dateLengthLength+2;
                dateString = date.substring(startOfDateIndex,date.length());
                inputFormatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
                LocalDateTime at = LocalDateTime.parse(dateString,inputFormatter);
                Event event = new Event(description,at);
                if("1".equals(taskStatus)) {
                    event.markDone();
                }
                tasks.add(event);
                break;
            }
        }
    }

    public int size() {
        return tasks.size();
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task get(int i) throws IndexOutOfBoundsException {
        return tasks.get(i - 1);
    }

    public ArrayList<Task> filter(String keyword) {
        ArrayList<Task> output = new ArrayList<>();
        for(Task task : tasks) {
            if(task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                output.add(task);
            }
        }
        return output;
    }

    public void markDone(int i) throws IndexOutOfBoundsException {
        tasks.get(i - 1).markDone();
    }

    public ArrayList<String> export() {
        ArrayList<String> output = new ArrayList<>();
        for(int i = 1; i <= tasks.size(); i++) {
            output.add(tasks.get(i-1).export());
        }
        return output;
    }
}
