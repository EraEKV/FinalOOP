package Menu;

import Users.Admin;
import Users.User;
import System.Request;
import System.Log;
import Database.Database;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AdminMenu {
    private final Map<Integer, Command> commands = new HashMap<>();
    private final BufferedReader reader;

    public AdminMenu(Admin admin, BufferedReader reader) {
        this.reader = reader;
        commands.put(1, new Commands.AddUserCommand(admin, reader));
        commands.put(2, new Commands.DeleteUserCommand(admin, reader));
        commands.put(3, new Commands.UpdateUserCommand(admin, reader));
        commands.put(4, new Commands.ViewLogsCommand(admin));
        commands.put(9, new Commands.ChangePasswordCommand(admin, reader));
        commands.put(0, new Commands.ExitCommand());
    }

    public void displayMenu() {
        while (true) {
            try {
                System.out.println("\n=== Admin Menu ===");
                System.out.println("[1] Add User");
                System.out.println("[2] Delete User");
                System.out.println("[3] Update User");
                System.out.println("[4] View Logs");
                System.out.println("[9] Change Password");
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

                if (choice == 0) {
                    System.out.println("Exiting...");
                    return;
                }

                Command command = commands.get(choice);
                if (command != null) {
                    command.execute();
                } else {
                    System.err.println("Invalid option. Please try again.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred while reading input. Please try again.");
            }
        }
    }
}
