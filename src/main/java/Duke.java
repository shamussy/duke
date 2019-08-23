import java.util.ArrayList;
import java.util.Scanner;

public class Duke {

    public String getGreeting() {
        return "Hello! I'm Duke \nWhat can I do for you?";
    }

    public void getInput() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> tasklists = new ArrayList<String>();
        while(true) {
            String input = scanner.nextLine();
            if("bye".equals(input)) {
                System.out.println("Bye. Hope to see you again soon!");
                return;
            }
            else if (!input.isEmpty() && !input.isBlank()) {
                if("list".equals(input)) {
                    for(int i = 0; i < tasklists.size(); i++)
                    {
                        System.out.println(i+1 + ": " + tasklists.get(i));
                    }
                }
                else {
                    tasklists.add(input);
                    System.out.println("added: "+input);
                }
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
