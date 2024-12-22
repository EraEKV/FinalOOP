package Menu;

import CustomExceptions.NotAResearcherException;
import Research.Researcher;
import Users.GradStudent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GradStudentMenu {
    private final Map<Integer, Command> commands = new HashMap<>();
    private final BufferedReader reader;

    public GradStudentMenu(GradStudent gradStudent, BufferedReader reader) {
        this.reader = reader;
        commands.put(1, new Commands.ViewResearchTopicCommand(gradStudent));
        commands.put(2, new Commands.SetResearchTopicCommand(gradStudent, reader));
        commands.put(3, new Commands.ViewPublicationsCommand(gradStudent));
        commands.put(4, new Commands.AddPublicationCommand(gradStudent, reader));
        commands.put(5, new Commands.ConductResearchCommand(gradStudent));
//        commands.put(8, new Commands.OpenTeacherMenu(gradStudent, reader));
        commands.put(9, new Commands.ChangePasswordCommand(gradStudent, reader));
        commands.put(10, new Commands.ViewNotificationsCommand(gradStudent, reader));
        try {
            Researcher researcher = gradStudent.getIsResearcher();
            commands.put(11, new Commands.ChangeToResearcherMenu(researcher, reader));
        } catch (NotAResearcherException e) {
//            ...
        }
    }

    public void displayMenu() {
        while (true) {
            try {
                System.out.println("\n=== Grad Student Menu ===");
                System.out.println("[1] View Research Topic");
                System.out.println("[2] Set Research Topic");
                System.out.println("[3] View Publications");
                System.out.println("[4] Add Publication");
                System.out.println("[5] View Teacher Info");
                System.out.println("[6] Conduct Research");
                System.out.println("[9] Change Password");
                System.out.println("[10] Notifications");
                if(commands.containsKey(11)) {
                    System.out.println("[11] Researcher Menu");
                }
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
                    case 1, 2, 3, 4, 5, 9, 10, 11 -> {
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
