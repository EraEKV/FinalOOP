package Menu;


import Database.Database;
import System.UniversitySystemMediator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


class MainMenu {
    private final Map<Integer, Command> commands = new HashMap<>();
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//    private final UniversitySystemMediator mediator = new UniversitySystemMediator();

    MainMenu() {
        commands.put(1, new Commands.Authenticate(reader));
        commands.put(2, new Commands.SecretCommand());
    }


    public void displayMenu() {
        while (true) {
            try {
                System.out.println("=== Welcome to our system! ===");
                System.out.println("[1] Login ");
                System.out.println("[2] Secret command");
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
                    case 1, 2, 3 -> {
                        Command command = commands.get(choice);
                        if (command != null) {
                            command.execute();
                        } else {
                            System.err.println("Command not implemented yet.");
                        }
                    }
                    case 0 -> {
                        try {
                            Database.write();
                            System.out.println("Exiting the menu. Goodbye!");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

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
