package Menu;

import Users.Rector;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RectorMenu {
    private final Map<Integer, Command> commands = new HashMap<>();
    private final BufferedReader reader;

    public RectorMenu(Rector rector, BufferedReader reader) {
        this.reader = reader;
        commands.put(1, new Commands.RectorViewRequestsCommand(rector));
        commands.put(2, new Commands.ManageRequestCommand(rector, reader));
        commands.put(3, new Commands.AddRequestCommand(rector, reader));
        commands.put(4, new Commands.EditRequestCommand(rector, reader));
        commands.put(5, new Commands.ChangePasswordCommand(rector, reader));
        commands.put(6, new Commands.SendMessageCommand(rector, reader));
        commands.put(7, new Commands.ChangePasswordCommand(rector, reader));
        commands.put(8, new Commands.ViewNotificationsCommand(rector, reader));
    }

    public void displayMenu() {
        while (true) {
            try {
                System.out.println("\n=== Rector Menu ===");
                System.out.println("[1] View Requests");
                System.out.println("[2] Manage Requests");
                System.out.println("[3] Add Requests");
                System.out.println("[4] Edit Requests");
                System.out.println("[5] Change Password");
                System.out.println("[6] Send Message");
                System.out.println("[7] Change Password");
                System.out.println("[8] Notifications");
                System.out.println("[0] Exit");
                System.out.print("Enter your choice: ");

                String input = reader.readLine();
                int choice;

                try {
                    choice = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    continue;
                }

                switch (choice) {
                    case 1, 2, 3, 4, 5, 6, 7, 8 -> {
                        Command command = commands.get(choice);
                        if (command != null) {
                            command.execute();
                        } else {
                            System.err.println("Command not implemented yet.");
                        }
                    }
                    case 0 -> {
                        System.out.println("Exiting the menu. Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred while reading input. Please try again.");
            }
        }
    }
}



