package Menu;

import java.util.*;

class StudentMenu {
    private final Map<Integer, Command> commands = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);

    public void addCommand(int option, Command command) {
        commands.put(option, command);
    }

    public void displayMenu() {
        while (true) {
            System.out.println("\n=== Student Menu ===");
            System.out.println("1. View Marks");
            System.out.println("2. View Transcript");
            System.out.println("3. Rate a Teacher");
            System.out.println("4. View Teacher Information");
            System.out.println("5. Manage Organizations");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            Command command = commands.get(choice);
            if (command != null) {
                command.execute();
            } else if (choice == 0) {
                System.out.println("Exiting the menu. Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }
    }
}