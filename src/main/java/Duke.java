import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    static ArrayList<Task> tasks = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String logo =
                "    ,o888888o.    8 8888        8          .8.    8888888 8888888888 8888888 8888888888 `8.`8888.      ,8'\n" +
                        "   8888     `88.  8 8888        8         .888.         8 8888             8 8888        `8.`8888.    ,8'\n" +
                        ",8 8888       `8. 8 8888        8        :88888.        8 8888             8 8888         `8.`8888.  ,8'\n" +
                        "88 8888           8 8888        8       . `88888.       8 8888             8 8888          `8.`8888.,8'\n" +
                        "88 8888           8 8888        8      .8. `88888.      8 8888             8 8888           `8.`88888'\n" +
                        "88 8888           8 8888        8     .8`8. `88888.     8 8888             8 8888            `8. 8888\n" +
                        "88 8888           8 8888888888888    .8' `8. `88888.    8 8888             8 8888             `8 8888\n" +
                        "`8 8888       .8' 8 8888        8   .8'   `8. `88888.   8 8888             8 8888              8 8888\n" +
                        "   8888     ,88'  8 8888        8  .888888888. `88888.  8 8888             8 8888              8 8888\n" +
                        "    `8888888P'    8 8888        8 .8'       `8. `88888. 8 8888             8 8888              8 8888\n";



        System.out.println("------------------------------------------");
        System.out.println("Hi!! I am\n" + logo);
        System.out.println("What brings you here today?");
        System.out.println("------------------------------------------");

        while (true) {
            String input = scanner.nextLine();
            String[] tokens = input.split(" ", 2);

            System.out.println("------------------------------------------");
            try {
                String rootCmd = tokens[0];
                if (rootCmd.equals("list")) {
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + "." + tasks.get(i));
                    }
                } else if (rootCmd.equals("bye")) {
                    System.out.println("Oh.. bye");
                    break;

                } else if (rootCmd.equals("delete")) {
                    int index = validateTaskIndex(tokens[1]);
                    Task task = tasks.get(index);
                    tasks.remove(index);
                    System.out.printf("Noted. I've removed this task:\n\t%s\n", task);
                    System.out.printf("Now you have %d tasks in the list.\n", tasks.size());
                } else if (rootCmd.equals("mark")) {
                    if (tokens.length == 1) {
                        throw new DukeException("The task index of a task cannot be empty.");
                    }
                    int index = validateTaskIndex(tokens[1]);
                    Task task = tasks.get(index);
                    task.setStatus(true);
                } else if (rootCmd.equals("unmark")) {
                    if (tokens.length == 1) {
                        throw new DukeException("The task index of a task cannot be empty.");
                    }
                    int index = validateTaskIndex(tokens[1]);
                    Task task = tasks.get(index);
                    task.setStatus(false);

                } else if (rootCmd.equals("todo")) {
                    if (tokens.length == 1) {
                        throw new DukeException("The description of a todo cannot be empty.");
                    }
                    String todoText = input.substring(5);
                    addTask(new Todo(todoText));
                } else if (rootCmd.equals("deadline")) {
                    if (tokens.length == 1) {
                        throw new DukeException("The description and due date of a deadline cannot be empty.");
                    }

                    String deadlineText = tokens[1];
                    String[] parts = deadlineText.split("/by", 2);

                    if (parts.length == 0) {
                        throw new DukeException("The description and due date of a deadline cannot be empty.");
                    } else if (parts.length == 1) {
                        throw new DukeException("The due date of a deadline cannot be empty.");
                    }

                    String description = parts[0].trim();
                    String date = parts[1].trim();
                    addTask(new Deadline(description, date));
                } else if (rootCmd.equals("event")) {

                    if (tokens.length == 1) {
                        throw new DukeException("The description, start date/time and end date/time of an event cannot be empty.");
                    }

                    String eventText = tokens[1];
                    String[] eventParts = eventText.split("/from", 2);

                    if (eventParts.length == 0) {
                        throw new DukeException("The description, start date/time and end date/time of an event cannot be empty.");
                    } else if (eventParts.length == 1) {
                        throw new DukeException("The start date/time and end date/time of a deadline cannot be empty.");
                    }

                    String description = eventParts[0].trim();
                    String[] fromToParts = eventParts[1].split("/to", 2);

                    if (fromToParts.length == 0) {
                        throw new DukeException("The start date/time and end date/time of a deadline cannot be empty.");
                    } else if (fromToParts.length == 1) {
                        throw new DukeException("The end date/time of a deadline cannot be empty.");
                    }

                    String fromDate = fromToParts[0].trim();
                    String toTime = fromToParts[1].trim();
                    addTask(new Event(description, fromDate, toTime));
                } else {
                    throw new DukeException("I'm sorry, but I don't know what that means :-(");
                }
            } catch (DukeException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("------------------------------------------");
        }
    }

    private static int validateTaskIndex(String input) throws DukeException {
        try {
            int number = Integer.parseInt(input);
            if (number < 1 || number > 100) {
                throw new DukeException("Please provide a task index that is 1 <= task index <= 100.");
            } else if (tasks.size() < number) {
                throw new DukeException("The given task index is higher than current task list: " + tasks.size()  + ".");
            }

            int index = number - 1;
            Task task = tasks.get(index);
            if (task == null) {
                throw new DukeException("There is no task at the given task index.");
            }
            return index;
        } catch (NumberFormatException e) {
            throw new DukeException("Please provide a valid number format");
        }
    }

    private static void addTask(Task task) {
        tasks.add(task);
        System.out.printf("Got it. I've added this task:\n\t%s\n", task);
        System.out.printf("Now you have %d tasks in the list.\n", tasks.size());
    }
}
