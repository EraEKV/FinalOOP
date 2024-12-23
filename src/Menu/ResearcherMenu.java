package Menu;

import Database.Database;
import Research.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
        commands.put(6, new PublishPaperCommand(researcher, reader));
        commands.put(7, new Commands.TopCitedResearcherCommand(Database.getInstance().getResearchers()));
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

    private static class PublishPaperCommand implements Command {
        private final Researcher researcher;
        private final BufferedReader reader;

        public PublishPaperCommand(Researcher researcher, BufferedReader reader) {
            this.researcher = researcher;
            this.reader = reader;
        }

        @Override
        public void execute() {
            try {
                System.out.println("\nChoose a journal to publish your paper:");
                System.out.println("[1] Times");
                System.out.println("[2] Forbes");
                System.out.println("[3] KBTU");
                System.out.print("Enter your choice: ");

                String input = reader.readLine().trim();
                int choice;

                try {
                    choice = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    return;
                }

                ResearchJournalsName selectedJournalName;
                switch (choice) {
                    case 1:
                        selectedJournalName = ResearchJournalsName.TIMES;
                        break;
                    case 2:
                        selectedJournalName = ResearchJournalsName.LANCET;
                        break;
                    case 3:
                        selectedJournalName = ResearchJournalsName.KBTU;
                        break;
                    default:
                        System.out.println("Invalid choice. Please select a valid journal.");
                        return;
                }

                ResearchJournal selectedJournal = null;

                for (ResearchJournal journal : Database.getInstance().getResearchJournals()) {
                    if (journal.getResearchJournalsName().equals(selectedJournalName)) {
                        selectedJournal = journal;
                        break;
                    }
                }

                if (selectedJournal == null) {
                    System.out.println("Journal not found");
                    return;
                }
                ResearchPaper paper = null;
                for (ResearchPaper p : researcher.getPapers()) {
                    paper = p;
                    break;
                }

                if (paper == null) {
                    System.out.println("No papers found");
                    return;
                }
                paper.setResearchJournal(selectedJournal);
                System.out.println("Paper published in " + selectedJournal.getResearchJournalsName() + " successfully.");
            } catch (IOException e) {
                System.err.println("Error during publishing: " + e.getMessage());
            }
        }
    }

}
