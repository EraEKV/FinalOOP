package Menu;

import Database.Database;
import Research.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ResearcherMenu {
    private final Map<Integer, Command> commands = new HashMap<>();
    private final BufferedReader reader;

    public ResearcherMenu(Researcher researcher, BufferedReader reader) {
        this.reader = reader;
        initializeCommands(researcher);
    }

    private void initializeCommands(Researcher researcher) {
        commands.put(1, new Commands.ViewPapersCommand(researcher));
        commands.put(2, new Commands.AddPaperCommand(researcher, reader));
        commands.put(3, new Commands.CalculateHIndexCommand(researcher));
        commands.put(4, new Commands.CalculateCitationsCommand(researcher));
        commands.put(5, new Commands.PrintPapersCommand(researcher));
        commands.put(6, new Commands.PublishPaperCommand(researcher));
        commands.put(7, new Commands.TopCitedResearcherCommand(Database.getInstance().getResearchers()));

        System.out.print("Enter the journal name to subscribe: ");
        try {
            String journalName = reader.readLine().trim();
            if (!journalName.isEmpty()) {
                ResearchJournalsName journal = ResearchJournalsName.valueOf(journalName.toUpperCase()); // Преобразование строки в enum
                commands.put(8, new Commands.SubscribeToJournalCommand(journal, researcher));
            } else {
                System.out.println("Journal name cannot be empty.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid journal name provided. Please use a valid journal name.");
        } catch (IOException e) {
            System.err.println("Error reading journal name: " + e.getMessage());
        }

    }

    public void displayMenu() {
        while (true) {
            try {
                printMenu();

                String input = reader.readLine().trim();
                int choice;

                try {
                    choice = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    continue;
                }

                if (choice == 0) {
                    System.out.println("Exiting the menu. Goodbye!");
                    break;
                }

                Command command = commands.get(choice);
                if (command != null) {
                    command.execute();
                } else {
                    System.out.println("Invalid choice or command not implemented.");
                }
            } catch (IOException e) {
                System.err.println("Error reading input: " + e.getMessage());
            }
        }
    }

    private void printMenu() {
        System.out.println("\n=== Researcher Menu ===");
        System.out.println("[1] View Papers");
        System.out.println("[2] Add Paper");
        System.out.println("[3] Calculate H-Index");
        System.out.println("[4] Calculate Citations");
        System.out.println("[5] Print Papers");
        System.out.println("[6] Publish Paper");
        System.out.println("[7] Top Cited Researcher");
        System.out.println("[0] Exit");
        System.out.print("Enter your choice: ");
    }
}
