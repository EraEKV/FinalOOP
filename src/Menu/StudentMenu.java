package Menu;

import CustomExceptions.NotAResearcherException;
import Research.ResearchJournal;
import Research.Researcher;
import Users.Student;
import Users.Teacher;
import System.Organization;
import java.io.*;
import java.util.*;

class StudentMenu {
    private final Map<Integer, Command> commands = new HashMap<>();
    private final BufferedReader reader;

    public StudentMenu(Student student, BufferedReader reader) {
        this.reader = reader;
        commands.put(1, new Commands.ViewMarksCommand(student));
        commands.put(2, new Commands.ViewTranscriptCommand(student));
        commands.put(3, new Commands.RateTeacherCommand(student, reader));
        commands.put(4, new Commands.ViewTeacherInfoCommand(student, reader));
        commands.put(5, new Commands.ManageOrganizationsCommand(student, reader));
        commands.put(6, new Commands.SendMessageCommand(student, reader));
        commands.put(7, new Commands.RegisterToCourses(student, reader));
        commands.put(8, new Commands.SubscribeResearchJournalCommand(student, reader));
        commands.put(9, new Commands.ChangePasswordCommand(student, reader));
        commands.put(10, new Commands.ViewNotificationsCommand(student, reader));
        Researcher researcher = student.getIsResearcher();
        commands.put(11, new Commands.ChangeToResearcherMenu(researcher, reader));

        if(student.getIsHead() != null) {
            commands.put(12, new Commands.InviteCommand(student, reader));
        }
    }

    public void displayMenu() {
        while (true) {
            try {
                System.out.println("\n=== Student Menu ===");
                System.out.println("[1] View Marks");
                System.out.println("[2] View Transcript");
                System.out.println("[3] Rate a Teacher");
                System.out.println("[4] View Teacher Information");
                System.out.println("[5] Manage Organizations");
                System.out.println("[6] Send Message");
                System.out.println("[7] Register to Courses");
                System.out.println("[8] Subscribe to Research Journal");
                System.out.println("[9] Change Password");
                System.out.println("[10] Notifications");
                if(commands.containsKey(11)) {
                    System.out.println("[11] Researcher Menu");
                }
                if(commands.containsKey(12)) {
                    System.out.println("[12] Invite to Organization");
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
                    case 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 -> {
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
