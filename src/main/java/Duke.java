import java.util.Scanner;

public class Duke {

    private String getGreeting() {
        return "Hello! I'm Duke \nWhat can I do for you?";
    }

    private void getInput() {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            String input = scanner.nextLine();
            if("bye".equals(input)) {
                System.out.println("Bye. Hope to see you again soon!");
                return;
            }
            else if (!input.isEmpty() && !input.isBlank()) {
                System.out.println(input);
            }
            else {
                System.out.println("Please do not leave any blanks");
            }
        }
    }

    public static void main(String[] args) {
        Duke duke = new Duke();
        System.out.println(duke.getGreeting());
        duke.getInput();
    }
}
