public class DukeException extends Exception{
    private String message;
    DukeException(String message) {
        super(message);
        this.message = message;
    }

    public String toString() {
        return "☹ OOPS!!! " + this.message;
    }

}
