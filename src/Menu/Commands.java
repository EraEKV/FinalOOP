package Menu;

import Enums.*;
import Users.*;
import System.Organization;
import Database.Database;
import Academic.Course;
import System.News;
import System.Request;
import Users.Rector;

import java.io.BufferedReader;
import java.io.*;
import java.util.*;

public class Commands {

    public static class ViewMarksCommand implements Command {
        private final Student student;

        public ViewMarksCommand(Student student) {
            this.student = student;
        }

        @Override
        public void execute() {
            student.viewMarks();
        }
    }
    // ViewTranscriptCommand
    public static class ViewTranscriptCommand implements Command {
        private final Student student;

        public ViewTranscriptCommand(Student student) {
            this.student = student;
        }

        @Override
        public void execute() {
            student.viewTranscript();
        }
    }

    // RateTeacherCommand
    public static class RateTeacherCommand implements Command {
        private final List<Teacher> teachers;
        private final BufferedReader reader;

        public RateTeacherCommand(List<Teacher> teachers, BufferedReader reader) {
            this.teachers = teachers;
            this.reader = reader;
        }

        @Override
        public void execute() {
            try {
                System.out.println("Select a teacher to rate:");
                for (int i = 0; i < teachers.size(); i++) {
                    System.out.println((i + 1) + ". " + teachers.get(i).getFirstname() + " " + teachers.get(i).getLastname());
                }
                System.out.print("Enter your choice: ");
                String input = reader.readLine();
                int teacherIndex = Integer.parseInt(input) - 1;

                if (teacherIndex < 0 || teacherIndex >= teachers.size()) {
                    System.out.println("Invalid choice.");
                    return;
                }
                Teacher teacher = teachers.get(teacherIndex);

                System.out.print("Enter your rating (1-5): ");
                int rating = Integer.parseInt(reader.readLine());
                if (rating < 1 || rating > 5) {
                    System.out.println("Invalid rating. Please enter a number between 1 and 5.");
                    return;
                }
                teacher.getRatings().add(rating);
                System.out.println("Rating added successfully!");
            } catch (IOException | NumberFormatException e) {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    // ViewTeacherInfoCommand
    public static class ViewTeacherInfoCommand implements Command {
        private final List<Teacher> teachers;
        private final BufferedReader reader;

        public ViewTeacherInfoCommand(List<Teacher> teachers, BufferedReader reader) {
            this.teachers = teachers;
            this.reader = reader;
        }

        @Override
        public void execute() {
            try {
                System.out.println("Select a teacher to view their information:");
                for (int i = 0; i < teachers.size(); i++) {
                    System.out.println((i + 1) + ". " + teachers.get(i).getFirstname() + " " + teachers.get(i).getLastname());
                }
                System.out.print("Enter your choice: ");
                String input = reader.readLine();
                int teacherIndex = Integer.parseInt(input) - 1;

                if (teacherIndex < 0 || teacherIndex >= teachers.size()) {
                    System.out.println("Invalid choice.");
                    return;
                }
                Teacher teacher = teachers.get(teacherIndex);
                System.out.println("Teacher Information:\n" + teacher);
            } catch (IOException | NumberFormatException e) {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    // ManageOrganizationsCommand
    public static class ManageOrganizationsCommand implements Command {
        private final Student student;
        private final List<Organization> organizations;
        private final BufferedReader reader;

        public ManageOrganizationsCommand(Student student, List<Organization> organizations, BufferedReader reader) {
            this.student = student;
            this.organizations = organizations;
            this.reader = reader;
        }

        @Override
        public void execute() {
            while (true) {
                try {
                    System.out.println("\n=== Manage Organizations ===");
                    System.out.println("1. Create Organization");
                    System.out.println("2. Join Organization");
                    System.out.println("3. Leave Organization");
                    System.out.println("0. Back to Main Menu");
                    System.out.print("Enter your choice: ");
                    int choice = Integer.parseInt(reader.readLine());

                    switch (choice) {
                        case 1 -> {
                            System.out.print("Enter the name of the new organization: ");
                            String name = reader.readLine();
                            organizations.add(new Organization(name));
                            System.out.println("Organization created successfully!");
                        }
                        case 2 -> {
                            System.out.println("Select an organization to join:");
                            for (int i = 0; i < organizations.size(); i++) {
                                System.out.println((i + 1) + ". " + organizations.get(i).getName());
                            }
                            int orgIndex = Integer.parseInt(reader.readLine()) - 1;

                            if (orgIndex < 0 || orgIndex >= organizations.size()) {
                                System.out.println("Invalid choice.");
                                continue;
                            }
                            student.joinOrganization(organizations.get(orgIndex));
                        }
                        case 3 -> {
                            System.out.println("Select an organization to leave:");
                            for (int i = 0; i < organizations.size(); i++) {
                                System.out.println((i + 1) + ". " + organizations.get(i).getName());
                            }
                            int orgIndex = Integer.parseInt(reader.readLine()) - 1;

                            if (orgIndex < 0 || orgIndex >= organizations.size()) {
                                System.out.println("Invalid choice.");
                                continue;
                            }
                            student.leaveOrganization(organizations.get(orgIndex));
                        }
                        case 0 -> {
                            return;
                        }
                        default -> System.out.println("Invalid choice. Try again.");
                    }
                } catch (IOException | NumberFormatException e) {
                    System.out.println("Invalid input. Please try again.");
                }
            }
        }
    }

    // AddCourseCommand
    public static class AddCourseCommand implements Command {
        private final Manager manager;
        private final BufferedReader reader;

        public AddCourseCommand(Manager manager, BufferedReader reader) {
            this.manager = manager;
            this.reader = reader;
        }

        @Override
        public void execute() {
            try {
                System.out.println("Enter course code:");
                String code = reader.readLine();

                System.out.println("Enter course name:");
                String name = reader.readLine();

                System.out.println("Enter faculty (e.g., 'Engineering', 'Science'):");
                String facultyInput = reader.readLine();
                Faculty faculty = Faculty.valueOf(facultyInput.toUpperCase());

                System.out.println("Enter speciality (e.g., 'Computer Science', 'Electrical Engineering'):");
                String specialityInput = reader.readLine();
                Speciality speciality = Speciality.valueOf(specialityInput.toUpperCase());

                System.out.println("Enter number of credits:");
                int credits = Integer.parseInt(reader.readLine());

                System.out.println("Enter semester (e.g., 'FALL', 'SPRING'):");
                String semesterInput = reader.readLine();
                Semester semester = Semester.valueOf(semesterInput.toUpperCase());

                Course course = new Course(code, name, faculty, speciality, credits, semester);
                manager.addCourse(course);

                System.out.println("Course added successfully: " + course);
            } catch (IOException | IllegalArgumentException e) {
                System.out.println("An error occurred while adding the course.");
            }
        }
    }

    // ViewRequestsCommand
    public static class ViewRequestsCommand implements Command {
        private final Manager manager;

        public ViewRequestsCommand(Manager manager) {
            this.manager = manager;
        }

        @Override
        public void execute() {
            try {
                System.out.println("Displaying all requests...");
                for (Request request : manager.getRequests()) {
                    System.out.println(request);
                }
            } catch (Exception e) {
                System.out.println("An error occurred while viewing requests.");
            }
        }
    }

    public static class AddNewsCommand implements Command {
        private final Manager manager;
        private final BufferedReader reader;

        public AddNewsCommand(Manager manager, BufferedReader reader) {
            this.manager = manager;
            this.reader = reader;
        }

        @Override
        public void execute() {
            try {
                System.out.println("Enter author's name:");
                String author = reader.readLine();

                System.out.println("Enter news title:");
                String title = reader.readLine();

                System.out.println("Enter news content:");
                String content = reader.readLine();

                System.out.println("Enter news topic (e.g., POLITICS, SPORTS, TECHNOLOGY):");
                String topicInput = reader.readLine();
                NewsTopic newsTopic = NewsTopic.valueOf(topicInput.toUpperCase());

                News news = new News(author, title, content, newsTopic);

                manager.addNews(news);
                System.out.println("News added successfully!");
            } catch (IOException e) {
                System.out.println("Error occurred while adding news.");
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid news topic provided.");
            }
        }
    }

    // RedirectRequestCommand
    public static class RedirectRequestCommand implements Command {
        private final Manager manager;
        private final BufferedReader reader;

        public RedirectRequestCommand(Manager manager, BufferedReader reader) {
            this.manager = manager;
            this.reader = reader;
        }

        @Override
        public void execute() {
            try {
                System.out.println("Enter request ID to redirect:");
                String requestId = reader.readLine();
                Request request = manager.getRequests().stream()
                        .filter(req -> req.getId().equals(requestId))
                        .findFirst()
                        .orElse(null);

                if (request != null) {
                    if (request.getSigned()) {
                        System.out.println("Request is already signed. Cannot redirect.");
                        return;
                    }

                    if (request.getStatus() != Status.PENDING) {
                        System.out.println("Request cannot be redirected as it's not in PENDING status.");
                        return;
                    }

                    System.out.println("Enter Rector's email to redirect the request to:");
                    String rectorEmail = reader.readLine();
                    Rector rector = (Rector) Database.getInstance().getUsers().get(rectorEmail);

                    if (rector != null) {
                        manager.redirectRequest(rector, request);
                        System.out.println("Request successfully redirected to Rector.");
                    } else {
                        System.out.println("Rector not found.");
                    }
                } else {
                    System.out.println("Request not found.");
                }
            } catch (IOException e) {
                System.out.println("Error occurred while redirecting the request.");
            }
        }
    }


    // OpenCloseRegistrationCommand
    public static class OpenCloseRegistrationCommand implements Command {
        private final Manager manager;
        private final BufferedReader reader;

        public OpenCloseRegistrationCommand(Manager manager, BufferedReader reader) {
            this.manager = manager;
            this.reader = reader;
        }

        @Override
        public void execute() {
            try {
                System.out.println("Enter 'open' to open registration, 'close' to close it:");
                String status = reader.readLine();
                if ("open".equalsIgnoreCase(status)) {
                    manager.openRegistration(true);
                    System.out.println("Registration opened.");
                } else if ("close".equalsIgnoreCase(status)) {
                    manager.openRegistration(false);
                    System.out.println("Registration closed.");
                } else {
                    System.out.println("Invalid input.");
                }
            } catch (IOException e) {
                System.out.println("Error occurred while handling registration.");
            }
        }
    }

    // GetCourseReportCommand
    public static class GetCourseReportCommand implements Command {
        private final Manager manager;

        public GetCourseReportCommand(Manager manager) {
            this.manager = manager;
        }

        @Override
        public void execute() {
            try {
                System.out.println("=== Course Report ===");
                Vector<Course> courses = Database.getInstance().getCourses();
                if (courses.isEmpty()) {
                    System.out.println("No courses available.");
                    return;
                }

                for (Course course : courses) {
                    System.out.println("Course Code: " + course.getCode());
                    System.out.println("Course Name: " + course.getName());
                    System.out.println("Faculty: " + course.getFaculty());
                    System.out.println("Speciality: " + course.getSpeciality());
                    System.out.println("Credits: " + course.getCredits());
                    System.out.println("Semester: " + course.getSemester());
                    System.out.println("=====================\n");
                }
            } catch (Exception e) {
                System.out.println("Error occurred while fetching the course report.");
            }
        }
    }

    // GetStudentReportCommand
    public static class GetStudentReportCommand implements Command {
        private final Manager manager;

        public GetStudentReportCommand(Manager manager) {
            this.manager = manager;
        }

        @Override
        public void execute() {
            try {
                System.out.println("=== Student Report ===");
                Vector<Student> students = Database.getInstance().getStudents();
                if (students.isEmpty()) {
                    System.out.println("No students available.");
                    return;
                }

                for (Student student : students) {
                    System.out.println("Student ID: " + student.getId());
                    System.out.println("Name: " + student.getFirstname() + " " + student.getLastname());
                    System.out.println("Faculty: " + student.getFaculty());
                    System.out.println("Speciality: " + student.getSpeciality());
                    System.out.println("Status: " + (student.isRegistered() ? "Registered" : "Not Registered"));
                    System.out.println("=====================\n");
                }
            } catch (Exception e) {
                System.out.println("Error occurred while fetching the student report.");
            }
        }
    }

    // SetStudentRegistrationCommand
    public static class SetStudentRegistrationCommand implements Command {
        private final Manager manager;
        private final BufferedReader reader;

        public SetStudentRegistrationCommand(Manager manager, BufferedReader reader) {
            this.manager = manager;
            this.reader = reader;
        }

        @Override
        public void execute() {
            try {
                System.out.println("=== Set Student Registration Status ===");
                Vector<Student> students = Database.getInstance().getStudents();
                if (students.isEmpty()) {
                    System.out.println("No students available.");
                    return;
                }

                System.out.println("Select a student to modify their registration status:");
                for (int i = 0; i < students.size(); i++) {
                    System.out.println((i + 1) + ". " + students.get(i).getFirstname() + " " + students.get(i).getLastname());
                }

                int studentIndex = Integer.parseInt(reader.readLine()) - 1;
                if (studentIndex < 0 || studentIndex >= students.size()) {
                    System.out.println("Invalid choice.");
                    return;
                }

                Student student = students.get(studentIndex);
                System.out.println("Current Registration Status: " + (student.isRegistered() ? "Registered" : "Not Registered"));
                System.out.print("Enter new registration status (true for Registered, false for Not Registered): ");
                boolean status = Boolean.parseBoolean(reader.readLine());

                student.setRegistered(status);
                manager.setRegistration(student);
                System.out.println("Registration status updated for " + student.getFirstname() + " " + student.getLastname());
            } catch (IOException | NumberFormatException e) {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

}
