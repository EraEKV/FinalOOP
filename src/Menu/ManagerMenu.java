package Menu;

import System.Request;
import Users.Manager;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ManagerMenu {
    private final Map<Integer, Command> commands = new HashMap<>();
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public ManagerMenu(Manager manager) {
        // adding commands to the map
        commands.put(1, new Commands.AddCourseCommand(manager, reader));
        commands.put(2, new Commands.ViewRequestsCommand(manager));
        commands.put(5, new Commands.AddNewsCommand(manager, reader));
        commands.put(7, new Commands.RedirectRequestCommand(manager, reader));
        commands.put(8, new Commands.OpenCloseRegistrationCommand(manager, reader));
    }

    public void displayMenu() {
        while (true) {
            try {
                System.out.println("\n=== Manager Menu ===");
                System.out.println("1. Add Course");
                System.out.println("2. View Requests");
                System.out.println("5. Add News");
                System.out.println("7. Redirect Request to Rector");
                System.out.println("8. Open/Close Registration");
                System.out.println("9. Exit");
                System.out.print("Enter your choice: ");

                String input = reader.readLine();
                int choice;

                try {
                    choice = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    continue;
                }

                if (choice == 9) {
                    System.out.println("Exiting...");
                    return;
                }

                Command command = commands.get(choice);
                if (command != null) {
                    command.execute();
                } else {
                    System.out.println("Invalid option. Please try again.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred while reading input. Please try again.");
            }
        }
    }
}
