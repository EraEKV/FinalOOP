package Menu;

import Database.Database;
import Research.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResearcherMenu {
    private final Map<Integer, Command> commands = new HashMap<>();
    private final BufferedReader reader;

    public ResearcherMenu(Researcher researcher, BufferedReader reader) {
        this.reader = reader;
        commands.put(1, new Commands.ViewPapersCommand(researcher));
        commands.put(2, new Commands.AddPaperCommand(researcher, reader));
        commands.put(3, new Commands.CalculateHIndexCommand(researcher));
        commands.put(4, new Commands.CalculateCitationsCommand(researcher));
        commands.put(5, new Commands.PrintPapersCommand(researcher));
        commands.put(6, new Commands.PublishPaperCommand(researcher));
        commands.put(7, new Commands.TopCitedResearcherCommand(Database.getInstance().getResearchers()));

        System.out.print("Enter the journal name to subscribe: ");
        try {
            String journal = reader.readLine();
            commands.put(8, new Commands.SubscribeToJournalCommand(journal, researcher));
        } catch (IOException e) {
            System.out.println("Error reading journal name.");
        }
    }

    public void displayMenu() {
        while (true) {
            try {
                System.out.println("\n=== Researcher Menu ===");
                System.out.println("[1] View Papers");
                System.out.println("[2] Add Paper");
                System.out.println("[3] Calculate H-Index");
                System.out.println("[4] Calculate Citations");
                System.out.println("[5] Print Papers");
                System.out.println("[6] Publish Paper");
                System.out.println("[7] Top Cited Researcher");
                System.out.println("[8] Subscribe to Research Journal");
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
