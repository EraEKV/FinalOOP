package Menu;

import java.io.*;
import java.util.*;

class StudentMenu {
    private final Map<Integer, Command> commands = new HashMap<>();
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public void addCommand(int option, Command command) {
        commands.put(option, command);
    }

    public void displayMenu() {
        while (true) {
            try {
                System.out.println("\n=== Student Menu ===");
                System.out.println("1. View Marks");
                System.out.println("2. View Transcript");
                System.out.println("3. Rate a Teacher");
                System.out.println("4. View Teacher Information");
                System.out.println("5. Manage Organizations");
                System.out.println("0. Exit");
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
                    case 1, 2, 3, 4, 5 -> {
                        Command command = commands.get(choice);
                        if (command != null) {
                            command.execute();
                        } else {
                            System.out.println("Command not implemented yet.");
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
