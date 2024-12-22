package Menu;

import Users.DisciplinaryCommittee;
import Users.GradStudent;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DisciplinaryCommitteeMenu {
    private final Map<Integer, Command> commands = new HashMap<>();
    private final BufferedReader reader;

    public DisciplinaryCommitteeMenu(DisciplinaryCommittee committee, BufferedReader reader) {
        this.reader = reader;
        commands.put(1, new Commands.ManageComplaintsCommand(committee, reader));
        commands.put(2, new Commands.KickStudent(committee, reader));
//        commands.put(3, new Commands.DeleteOrganization(committee, reader));
        commands.put(4, new Commands.SendMessageCommand(committee, reader));
        commands.put(9, new Commands.ChangePasswordCommand(committee, reader));
        commands.put(10, new Commands.ViewNotificationsCommand(committee, reader));
    }

    public void displayMenu() {
        while (true) {
            try {
                System.out.println("\n=== Disciplinary Committee Menu ===");
                System.out.println("[1] View Complaints");
                System.out.println("[2] Kick student");
                System.out.println("[3] Delete Organization");
                System.out.println("[4] Send Message");
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
                    case 1, 2, 3, 4, 9, 10 -> {
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
