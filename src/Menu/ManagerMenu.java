package Menu;

import Research.ResearchJournal;
import System.UniversitySystemMediator;
import Users.Manager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagerMenu {
    private final Map<Integer, Command> commands = new HashMap<>();
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public ManagerMenu(Manager manager, UniversitySystemMediator mediator, List<ResearchJournal> journals) {
        // Adding commands to the map
        commands.put(1, new Commands.AddCourseCommand(manager, reader));
        commands.put(2, new Commands.ViewRequestsCommand(manager));
        commands.put(3, new Commands.AddNewsCommand(manager, reader, mediator));
        commands.put(4, new Commands.RedirectRequestCommand(manager, reader));
        commands.put(5, new Commands.OpenCloseRegistrationCommand(manager, reader));
        commands.put(6, new Commands.SendMessageCommand(manager, reader));
        commands.put(7, new Commands.SubscribeResearchJournalCommand(manager, journals, reader)); // Новый пункт
    }

    public void displayMenu() {
        while (true) {
            try {
                System.out.println("\n=== Manager Menu ===");
                System.out.println("[1] Add Course");
                System.out.println("[2] View Requests");
                System.out.println("[3] Add News");
                System.out.println("[4] Redirect Request to Rector");
                System.out.println("[5] Open/Close Registration");
                System.out.println("[6] Send Message");
                System.out.println("[7] Subscribe to Research Journal"); // Новый пункт
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
                    case 1, 2, 3, 4, 5, 6, 7 -> {
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

