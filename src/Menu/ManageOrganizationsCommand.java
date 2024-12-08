package Menu;
import Users.Student;
import System.Organization;
import java.util.*;
class ManageOrganizationsCommand implements Command {
    private final Student student;
    private final List<Organization> organizations;
    private final Scanner scanner;

    public ManageOrganizationsCommand(Student student, List<Organization> organizations, Scanner scanner) {
        this.student = student;
        this.organizations = organizations;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        while (true) {
            System.out.println("\n=== Manage Organizations ===");
            System.out.println("1. Create Organization");
            System.out.println("2. Join Organization");
            System.out.println("3. Leave Organization");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> {
                    System.out.print("Enter the name of the new organization: ");
                    scanner.nextLine(); // Clear buffer
                    String name = scanner.nextLine();
                    organizations.add(new Organization(name));
                    System.out.println("Organization created successfully!");
                }
                case 2 -> {
                    System.out.println("Select an organization to join:");
                    for (int i = 0; i < organizations.size(); i++) {
                        System.out.println((i + 1) + ". " + organizations.get(i).getName());
                    }
                    int orgIndex = scanner.nextInt() - 1;
                    if (orgIndex < 0 || orgIndex >= organizations.size()) {
                        System.out.println("Invalid choice.");
                        return;
                    }
                    student.joinOrganization(organizations.get(orgIndex));
                }
                case 3 -> {
                    System.out.println("Select an organization to leave:");
                    for (int i = 0; i < organizations.size(); i++) {
                        System.out.println((i + 1) + ". " + organizations.get(i).getName());
                    }
                    int orgIndex = scanner.nextInt() - 1;
                    if (orgIndex < 0 || orgIndex >= organizations.size()) {
                        System.out.println("Invalid choice.");
                        return;
                    }
                    student.leaveOrganization(organizations.get(orgIndex));
                }
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
