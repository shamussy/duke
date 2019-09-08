package duke.task;

public class TaskListTest extends TaskList {
    private boolean isEmpty = true;

    @Override
    public int size() {
        return isEmpty ? 0 : 1;
    }

    @Override
    public void add(Task task) {
        this.isEmpty = false;
    }

    @Override
    public void delete(int i) {
        this.isEmpty = true;
    }

    @Override
    public Task get(int i) {
        return new TaskTest();
    }
}