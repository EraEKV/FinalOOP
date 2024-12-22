package Menu;

import Research.ResearchJournal;
import Users.Teacher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeacherMenu {
    private final Map<Integer, Command> commands = new HashMap<>();
    private final BufferedReader reader;

    public TeacherMenu(Teacher teacher, BufferedReader reader) {
        this.reader = reader;
        commands.put(1, new Commands.ViewCoursesCommand(teacher));
        commands.put(2, new Commands.ManageCourseCommand(teacher, reader));
        commands.put(3, new Commands.ViewStudentsInfoCommand(teacher, reader));
        //commands.put(4, new Commands.PutMarksCommand(teacher, reader));
        commands.put(5, new Commands.PutAttendanceCommand(teacher, reader));
        commands.put(6, new Commands.SendMessageCommand(teacher, reader));
        commands.put(7, new Commands.SubscribeResearchJournalCommand(teacher, reader));
        commands.put(9, new Commands.ChangePasswordCommand(teacher, reader));
        commands.put(10, new Commands.ViewNotificationsCommand(teacher, reader));
    }

    public void displayMenu() {
        while (true) {
            try {
                System.out.println("\n=== Teacher Menu ===");
                System.out.println("[1] View Courses");
                System.out.println("[2] Manage Course");
                System.out.println("[3] View Students Information");
                System.out.println("[4] Put Marks");
                System.out.println("[5] Put Attendance");
                System.out.println("[6] Send Message");
                System.out.println("[7] Subscribe to Research Journal");
                System.out.println("[9] Change Password");
                System.out.println("[10] Notifications");
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
                    case 1, 2, 3, 4, 5, 6, 7, 9, 10 -> {
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

