package Menu;
import Users.Teacher;

import java.util.*;

class RateTeacherCommand implements Command {
    private final List<Teacher> teachers;
    private final Scanner scanner;

    public RateTeacherCommand(List<Teacher> teachers, Scanner scanner) {
        this.teachers = teachers;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("Select a teacher to rate:");
        for (int i = 0; i < teachers.size(); i++) {
            System.out.println((i + 1) + ". " + teachers.get(i).getFirstname() + " " + teachers.get(i).getLastname());
        }
        int teacherIndex = scanner.nextInt() - 1;
        if (teacherIndex < 0 || teacherIndex >= teachers.size()) {
            System.out.println("Invalid choice.");
            return;
        }
        Teacher teacher = teachers.get(teacherIndex);

        System.out.print("Enter your rating (1-5): ");
        int rating = scanner.nextInt();
        teacher.getRatings().add(rating);
        System.out.println("Rating added successfully!");
    }
}