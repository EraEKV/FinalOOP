package Menu;

import Academic.JournalLesson;
import Enums.*;
import Users.*;
import System.Organization;
import Database.Database;
import Academic.Course;
import System.News;
import System.Message;
import System.Notification;
import System.Request;
import Users.Rector;
import System.Credentials;
import System.UniversitySystemMediator;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Commands {
    public static class ViewMarksCommand implements Command {
        private final Student student;

        public ViewMarksCommand(Student student) {
            this.student = student;
        }

        @Override
        public void execute() {
            logging("ViewMarks", student);
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
            logging("ViewTranscript", student);
            student.viewTranscript();
        }
    }

    // RateTeacherCommand
    public static class RateTeacherCommand implements Command {
        Database db = Database.getInstance();

        private final Student student;
        private final Vector<Teacher> teachers;
        private final BufferedReader reader;




        public RateTeacherCommand(Student student, BufferedReader reader) {
            this.student = student;
            this.reader = reader;
            teachers = student.getTeachers();

        }

        @Override
        public void execute() {
            try {
                logging("RateTeacher", student);
                System.out.println("Select a teacher to rate:");


                for (int i = 0; i < teachers.size(); i++) {
                    System.out.println("[" + (i + 1) + "] " + teachers.get(i).getFirstname() + " " + teachers.get(i).getLastname());
                }

                System.out.print("Enter your choice: ");
                String input = reader.readLine();
                int teacherIndex;

                try {
                    teacherIndex = Integer.parseInt(input) - 1;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    return;
                }

                if (teacherIndex < 0 || teacherIndex >= teachers.size()) {
                    System.out.println("Invalid choice. Please select a valid teacher.");
                    return;
                }

                Teacher teacher = teachers.get(teacherIndex);

                System.out.print("Enter your rating (1-5): ");
                int rating;

                try {
                    rating = Integer.parseInt(reader.readLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid rating. Please enter a number between 1 and 5.");
                    return;
                }

                if (rating < 1 || rating > 5) {
                    System.out.println("Invalid rating. Please enter a number between 1 and 5.");
                    return;
                }

                teacher.getRatings().add(rating);
                System.out.println("Rating added successfully!");
            } catch (IOException e) {
                System.out.println("Error reading input. Please try again.");
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
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
            logging("ViewTeacherInfo", (User) teachers);
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
            logging("ManageOrganization", student);
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



    public static class RegisterToCourses implements Command {
        private final Student student;
        private final BufferedReader reader;

        public RegisterToCourses(Student student, BufferedReader reader) {
            this.student = student;
            this.reader = reader;
        }

        @Override
        public void execute() {
            try {
                Database db = Database.getInstance();
                Vector<Course> availableCourses = db.getCourses();
                HashMap<Course, Teacher> registeredCoursesMap = new HashMap<>();
                int totalCredits = 0;
                int remainingCredits = 25;

                courseLoop: while (totalCredits < 25) {
                    System.out.println("Total credits: " + totalCredits + " | Remaining credits: " + remainingCredits);
                    System.out.println("Select a course to register:");

                    for (int i = 0; i < availableCourses.size(); i++) {
                        Course course = availableCourses.get(i);

                        if (course.getCredits() > remainingCredits) {
                            continue;
                        }

                        Discipline courseType = checkCourse(student, course.getSpeciality());
                        System.out.println("[" + (i + 1) + "] " + course.getName() + " (Credits: " + course.getCredits() + ") - [" + courseType + "]");

                        if (course.getTeachers().isEmpty()) {
                            continue;
                        }

                        if (isCourseAlreadyAssigned(course, registeredCoursesMap)) {
                            continue;
                        }
                    }

                    if (remainingCredits <= 0) {
                        System.out.println("You have reached the maximum credit limit.");
                        break;
                    }

                    System.out.print("Enter your choice: ");
                    String input = reader.readLine();
                    int courseIndex = Integer.parseInt(input) - 1;

                    if (courseIndex < 0 || courseIndex >= availableCourses.size()) {
                        System.out.println("Invalid course choice.");
                        continue;
                    }

                    Course selectedCourse = availableCourses.get(courseIndex);

                    if (selectedCourse.getCredits() > remainingCredits) {
                        System.out.println("This course exceeds the remaining credit limit.");
                        continue;
                    }

                    Teacher selectedTeacher = null;
                    teacherLoop: while (true) {
                        System.out.println("Select a teacher for " + selectedCourse.getName() + ":");

                        for (int i = 0; i < selectedCourse.getTeachers().size(); i++) {
                            Teacher teacher = selectedCourse.getTeachers().get(i);
                            System.out.println("[" + (i + 1) + "] " + teacher.getFirstname() + " " + teacher.getLastname());
                        }

                        System.out.print("Enter the number of the teacher: ");
                        input = reader.readLine();
                        int teacherIndex = Integer.parseInt(input) - 1;

                        if (teacherIndex < 0 || teacherIndex >= selectedCourse.getTeachers().size()) {
                            System.out.println("Invalid choice. Try again.");
                            continue;
                        }

                        selectedTeacher = selectedCourse.getTeachers().get(teacherIndex);
                        System.out.println("You selected " + selectedTeacher.getFirstname() + " " + selectedTeacher.getLastname() + ".");
                        System.out.print("Do you want to confirm the teacher choice? (y/n): ");
                        String confirmation = reader.readLine();
                        if ("y".equalsIgnoreCase(confirmation)) {
                            break teacherLoop;
                        } else if ("n".equalsIgnoreCase(confirmation)) {
                            continue teacherLoop;
                        } else {
                            System.out.println("Invalid input. Please enter 'y' or 'n'.");
                        }
                    }

                    selectedCourse.assignTeacher(selectedTeacher);

                    registeredCoursesMap.put(selectedCourse, selectedTeacher);
                    totalCredits += selectedCourse.getCredits();
                    remainingCredits = 25 - totalCredits;

                    System.out.println("You have successfully registered for " + selectedCourse.getName() + " with " + selectedTeacher.getFirstname() + " " + selectedTeacher.getLastname());

                    if (totalCredits == 25) {
                        System.out.println("You have registered for 25 credits. Registration complete.");
                        break;
                    }

                    System.out.print("Would you like to register for another course? (y/n): ");
                    String response = reader.readLine();
                    if ("n".equalsIgnoreCase(response)) {
                        break;
                    }
                }

                student.setRegisteredCourses(registeredCoursesMap);

                for (Map.Entry<Course, Teacher> entry : registeredCoursesMap.entrySet()) {
                    Course course = entry.getKey();
                    Teacher teacher = entry.getValue();

                    course.addStudentToTeacher(teacher, student);
                }


            } catch (IOException | NumberFormatException e) {
                System.out.println("Invalid input. Please try again.");
            }
        }




        private boolean isCourseAlreadyAssigned(Course course, Map<Course, Teacher> registeredCoursesMap) {
            return registeredCoursesMap.containsKey(course);
        }


        private Discipline checkCourse(Student student, Speciality speciality) {
            if (student.getSpeciality().equals(speciality)) {
                return Discipline.MAJOR;
            } else {
                return Discipline.MINOR;
            }
        }
    }





//
//
//    MANAGER MENU
//
//

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
            logging("AddCourse", manager);
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
            logging("ViewRequests", manager);
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
        private final UniversitySystemMediator mediator;

        public AddNewsCommand(Manager manager, BufferedReader reader, UniversitySystemMediator mediator) {
            this.manager = manager;
            this.reader = reader;
            this.mediator = mediator;
        }

        @Override
        public void execute() {
            logging("AddNews", manager);
            try {
                System.out.println("Enter author's name:");
                String author = reader.readLine();
                System.out.println("Enter news title:");
                String title = reader.readLine();
                System.out.println("Enter news content:");
                String content = reader.readLine();
                System.out.println("Enter news topic:");
                String topicInput = reader.readLine();
                NewsTopic newsTopic = NewsTopic.valueOf(topicInput.toUpperCase());
                mediator.publishNews(author, newsTopic, title, content);
            } catch (IOException | IllegalArgumentException e) {
                System.out.println("Error occurred while adding news.");
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
            logging("RedirectRequest", manager);
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
            logging("OpenCloseRegistration", manager);
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
            logging("GetCourseReport", manager);
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
            logging("GetStudentReport", manager);
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
            logging("SetStudentRegistration", manager);
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



//
//
//    TEACHER MENU
//
//

    public static class ViewCoursesCommand implements Command {
        private final Teacher teacher;

        public ViewCoursesCommand(Teacher teacher) {
            this.teacher = teacher;
        }

        @Override
        public void execute() {
            logging("ViewCourses", teacher);
            try {
                System.out.println("=== View Courses ===");
                Vector<Course> courses = teacher.getCurrentCourses();

                if (courses.isEmpty()) {
                    System.out.println("No courses available.");
                    return;
                }

                System.out.println("Available courses:");
                for (int i = 0; i < courses.size(); i++) {
                    Course course = courses.get(i);
                    System.out.println("[" + (i + 1) + "] " + course.getName() + " (" + course.getCode() + ")");
                }

            } catch (Exception e) {
                System.out.println("Error occurred while fetching the courses.");
                e.printStackTrace();
            }
        }
    }

    public static class ManageCourseCommand implements Command {
        private final Teacher teacher;
        private final BufferedReader reader;

        public ManageCourseCommand(Teacher teacher, BufferedReader reader) {
            this.teacher = teacher;
            this.reader = reader;
        }


        @Override
        public void execute() {
            logging("ManageCourse", teacher);
            Database db = Database.getInstance();
            try {
                System.out.println("=== Manage Course ===");
                Vector<Course> courses = db.getCourses();
                if (courses.isEmpty()) {
                    System.out.println("No courses available.");
                    return;
                } else {

                }
            } catch (Exception e) {
                System.out.println("Error occurred while fetching the courses.");
            }
        }
    }


    public static class ViewStudentsInfoCommand implements Command {
        private final Teacher teacher;
        private final BufferedReader reader;

        public ViewStudentsInfoCommand(Teacher teacher, BufferedReader reader) {
            this.teacher = teacher;
            this.reader = reader;
        }

        @Override
        public void execute() {
            logging("ViewStudentsInfo", teacher);
            try {
                System.out.println("=== View Students Info ===");
                Vector<Course> courses = teacher.getCurrentCourses();

                if (courses.isEmpty()) {
                    System.out.println("No courses available.");
                    return;
                }

                while (true) {
                    System.out.println("Choose a course to view students:");
                    for (int i = 0; i < courses.size(); i++) {
                        System.out.println("[" + (i + 1) + "] " + courses.get(i).getName());
                    }

                    String input = reader.readLine();
                    int courseChoice;

                    try {
                        courseChoice = Integer.parseInt(input);

                        if (courseChoice < 1 || courseChoice > courses.size()) {
                            System.out.println("Invalid course choice. Try again.");
                            continue;
                        }

                        Course selectedCourse = courses.get(courseChoice - 1);
                        Vector<Student> students = selectedCourse.getStudents();

                        if (students.isEmpty()) {
                            System.out.println("No students are enrolled in this course.");
                        } else {
                            System.out.println("Students enrolled in " + selectedCourse.getName() + ":");
                            for (Student student : students) {
                                System.out.println("- " + student.getFirstname() + " " + student.getLastname());
                            }
                        }

                        System.out.println("Would you like to view another course? (y/n):");
                        String response = reader.readLine();

                        if ("n".equalsIgnoreCase(response)) {
                            break;
                        } else if (!"y".equalsIgnoreCase(response)) {
                            System.out.println("Invalid response. Please enter 'y' or 'n'.");
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid number.");
                    }
                }

            } catch (Exception e) {
                System.out.println("Error occurred while fetching the courses.");
                e.printStackTrace();
            }
        }
    }

//
//    PUT MARKS LOGIC NEEDS TO BE REALIZED
//

//    public static class PutMarksCommand implements Command {
//        private final Teacher teacher;
//        private final BufferedReader reader;
//
//        public PutMarksCommand(Teacher teacher, BufferedReader reader) {
//            this.teacher = teacher;
//            this.reader = reader;
//        }
//
//        @Override
//        public void execute() {
//            logging("SendMessage", teacher);
//            try {
//                System.out.println("=== Put Marks ===");
//
//                while (true) {
//                    System.out.println("Choose an option:");
//                    System.out.println("1. Journal Marks");
//                    System.out.println("2. Attestation");
//                    System.out.println("3. Exit");
//
//                    String input = reader.readLine();
//                    int choice;
//
//                    try {
//                        choice = Integer.parseInt(input);
//
//                        switch (choice) {
//                            case 1:
//                                handleJournalMarks();
//                                break;
//                            case 2:
//                                handleAttestationMarks();
//                                break;
//                            case 3:
//                                System.out.println("Exiting Put Marks.");
//                                return;
//                            default:
//                                System.out.println("Invalid choice. Please select 1, 2, or 3.");
//                        }
//                    } catch (NumberFormatException e) {
//                        System.out.println("Invalid input. Please enter a valid number.");
//                    }
//                }
//
//            } catch (Exception e) {
//                System.out.println("Error occurred while fetching the courses.");
//                e.printStackTrace();
//            }
//        }
//
//        private void handleJournalMarks() {
//            try {
//                System.out.println("=== Journal Marks ===");
//                Vector<Course> courses = teacher.getCurrentCourses();
//
//                if (courses.isEmpty()) {
//                    System.out.println("No courses available.");
//                    return;
//                }
//
//                System.out.println("Choose a course:");
//                for (int i = 0; i < courses.size(); i++) {
//                    System.out.println("[" + (i + 1) + "] " + courses.get(i).getName());
//                }
//
//                String input = reader.readLine();
//                int courseChoice = Integer.parseInt(input);
//
//                if (courseChoice < 1 || courseChoice > courses.size()) {
//                    System.out.println("Invalid course choice. Try again.");
//                    return;
//                }
//
//                Course selectedCourse = courses.get(courseChoice - 1);
//                Vector<Student> students = selectedCourse.getStudents();
//
//                if (students.isEmpty()) {
//                    System.out.println("No students enrolled in this course.");
//                    return;
//                }
//
//                System.out.println("Enter marks for students in " + selectedCourse.getName() + ":");
//                for (Student student : students) {
//                    System.out.println("Enter marks for " + student.getFirstname() + " " + student.getLastname() + ":");
//                    String marksInput = reader.readLine();
//
//                    try {
//                        double marks = Double.parseDouble(marksInput);
//                        student.getJournal(selectedCourse).addMark(marks);
//                        System.out.println("Marks added for " + student.getFirstname() + " " + student.getLastname() + ".");
//                    } catch (NumberFormatException e) {
//                        System.out.println("Invalid marks. Skipping this student.");
//                    }
//                }
//
//            } catch (Exception e) {
//                System.out.println("Error occurred while updating journal marks.");
//                e.printStackTrace();
//            }
//        }
//
//        private void handleAttestationMarks() {
//            try {
//                System.out.println("=== Attestation Marks ===");
//                Vector<Course> courses = teacher.getCurrentCourses();
//
//                if (courses.isEmpty()) {
//                    System.out.println("No courses available.");
//                    return;
//                }
//
//                System.out.println("Choose a course:");
//                for (int i = 0; i < courses.size(); i++) {
//                    System.out.println("[" + (i + 1) + "] " + courses.get(i).getName());
//                }
//
//                String input = reader.readLine();
//                int courseChoice = Integer.parseInt(input);
//
//                if (courseChoice < 1 || courseChoice > courses.size()) {
//                    System.out.println("Invalid course choice. Try again.");
//                    return;
//                }
//
//                Course selectedCourse = courses.get(courseChoice - 1);
//                System.out.println("Enter attestation marks for students in " + selectedCourse.getName() + ":");
//
//                Vector<Student> students = selectedCourse.getStudents();
//
//                if (students.isEmpty()) {
//                    System.out.println("No students enrolled in this course.");
//                    return;
//                }
//
//                for (Student student : students) {
//                    System.out.println("Enter attestation marks for " + student.getFirstname() + " " + student.getLastname() + ":");
//                    String marksInput = reader.readLine();
//
//                    try {
//                        double marks = Double.parseDouble(marksInput);
//                        selectedCourse.getAttestation().updateAttestation(student, marks);
//                        System.out.println("Attestation marks updated for " + student.getFirstname() + " " + student.getLastname() + ".");
//                    } catch (NumberFormatException e) {
//                        System.out.println("Invalid marks. Skipping this student.");
//                    }
//                }
//
//            } catch (Exception e) {
//                System.out.println("Error occurred while updating attestation marks.");
//                e.printStackTrace();
//            }
//        }
//    }


    public static class PutAttendanceCommand implements Command {
        private final Teacher teacher;
        private final BufferedReader reader;

        public PutAttendanceCommand(Teacher teacher, BufferedReader reader) {
            this.teacher = teacher;
            this.reader = reader;
        }

        @Override
        public void execute() {
            logging("PutAttendance", teacher);
            try {
                System.out.println("=== Put Marks ===");

                Vector<Course> courses = teacher.getCurrentCourses();
                course: while (true) {
                    System.out.println("Choose a Course:");
                    for (int i = 0; i < courses.size(); i++) {
                        System.out.println("[" + (i + 1) + "] " + courses.get(i).getName());
                    }

                    String input = reader.readLine();
                    int courseChoice;

                    try {
                        courseChoice = Integer.parseInt(input);
                        if (courseChoice < 1 || courseChoice > courses.size()) {
                            System.out.println("Invalid course choice. Try again.");
                            continue;
                        }

                        Course selectedCourse = courses.get(courseChoice - 1);
                        Vector<Student> students = selectedCourse.getStudents();

                        student: while (true) {
                            System.out.println("Choose a Student:");
                            int i = 1;
                            for (Student student : students) {
                                System.out.println("[" + i + "] " + student.getFirstname() + " " + student.getLastname());
                                i++;
                            }

                            String studentInput = reader.readLine();
                            int studentChoice;

                            try {
                                studentChoice = Integer.parseInt(studentInput);
                                if (studentChoice < 1 || studentChoice > students.size()) {
                                    System.out.println("Invalid student choice. Try again.");
                                    continue;
                                }

                                Student selectedStudent = students.get(studentChoice - 1);
                                System.out.println("Choose attendance type (1 - Attend, 2 - Absent, 3 - Late): ");
                                String attendanceInput = reader.readLine();
                                int attendanceType;

                                try {
                                    attendanceType = Integer.parseInt(attendanceInput);
                                    JournalLesson jl = new JournalLesson(new Date(), LessonType.LECTURE);

                                    switch (attendanceType) {
                                        case 1:
                                            jl.setAttendance(Attendance.ATTEND);
                                            break;
                                        case 2:
                                            jl.setAttendance(Attendance.ABSENSE);
                                            break;
                                        case 3:
                                            jl.setAttendance(Attendance.LATE);
                                            break;
                                        default:
                                            System.out.println("Invalid attendance type. Try again.");
                                            continue;
                                    }

                                    selectedStudent.getJournal(selectedCourse).addJournalLesson(selectedStudent, jl);
                                    System.out.println("Attendance updated for " + selectedStudent.getFirstname() + " " + selectedStudent.getLastname());

                                } catch (NumberFormatException e) {
                                    System.out.println("Invalid input. Please enter a number for attendance.");
                                    continue;
                                }

                                System.out.println("Would you like to mark attendance for another student? (y/n): ");
                                String response = reader.readLine();
                                if ("n".equalsIgnoreCase(response)) {
                                    break student;
                                } else if (!"y".equalsIgnoreCase(response)) {
                                    System.out.println("Invalid response. Please enter 'y' or 'n'.");
                                }

                            } catch (NumberFormatException e) {
                                System.out.println("Invalid student number. Please enter a valid number.");
                                continue;
                            }
                        }

                        System.out.println("Would you like to choose another course? (y/n): ");
                        String response = reader.readLine();
                        if ("n".equalsIgnoreCase(response)) {
                            break course;
                        } else if (!"y".equalsIgnoreCase(response)) {
                            System.out.println("Invalid response. Please enter 'y' or 'n'.");
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid number for course selection.");
                        continue;
                    }
                }

            } catch (Exception e) {
                System.out.println("Error occurred while fetching the courses.");
                e.printStackTrace();
            }
        }
    }



    public static class SendMessageCommand implements Command {
        private final User sender;
        private final BufferedReader reader;

        public SendMessageCommand(User sender, BufferedReader reader) {
            this.sender = sender;
            this.reader = reader;
        }

        @Override
        public void execute() {
            logging("SendMessage", sender);
            try {
                System.out.println("=== Send Message ===");
                Database db = Database.getInstance();

                recipient: while (true) {
                    System.out.println("Enter a recipient email without domain(like: e_kokenov):\n");

                    String input = reader.readLine();
                    String recipientChoice;

                    try {
                        recipientChoice = input;
                        User selectedRecipient = db.findUserByEmail(recipientChoice + "@kbtu.kz");
                        if (selectedRecipient == null) {
                            System.out.println("Invalid recipient choice. Try again.");
                            continue;
                        }

                        System.out.println("Enter your message:");
                        String messageContent = reader.readLine();

                        if (messageContent.trim().isEmpty()) {
                            System.out.println("Message cannot be empty. Try again.");
                            continue;
                        }

                        selectedRecipient.getNotifications().add(new Message(sender, messageContent));

                        System.out.println("Message sent to " + selectedRecipient.getFirstname() + " " + selectedRecipient.getLastname() + ".");

                        System.out.println("Would you like to send another message? (y/n):");
                        String response = reader.readLine();
                        if ("n".equalsIgnoreCase(response)) {
                            break recipient;
                        } else if (!"y".equalsIgnoreCase(response)) {
                            System.out.println("Invalid response. Please enter 'y' or 'n'.");
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid number for recipient selection.");
                        continue;
                    }
                }

            } catch (Exception e) {
                System.out.println("Error occurred while sending the message.");
                e.printStackTrace();
            }
        }
    }





//
//
//  ADMIN MENU
//
//
    public static class AddUserCommand implements Command {
        private final Admin admin;
        private final BufferedReader reader;

        public AddUserCommand(Admin admin, BufferedReader reader) {
            this.admin = admin;
            this.reader = reader;
        }

        @Override
        public void execute() {
            logging("AddUser", admin);
            try {
                System.out.print("Enter first name: ");
                String firstname = reader.readLine();

                System.out.print("Enter last name: ");
                String lastname = reader.readLine();

                System.out.println("Select the type of user to add:");
                System.out.println("[1] Student");
                System.out.println("[2] MasterStudent");
                System.out.println("[3] GradStudent");
                System.out.println("[4] Teacher");
                System.out.println("[5] Manager");
//                System.out.println("[6] Researcher");

                String input = reader.readLine();
                int choice = Integer.parseInt(input);

                UserFactory factory = UserFactory.getInstance();
                User newUser = null;

                switch (choice) {
                    case 1:  // Student
                        Faculty studentFaculty = selectFaculty();
                        Speciality studentSpeciality = selectSpeciality();
                        newUser = factory.createUser(firstname, lastname, studentFaculty, studentSpeciality);
                        break;
                    case 2:  // MasterStudent
                        Faculty masterFaculty = selectFaculty();
                        Speciality masterSpeciality = selectSpeciality();
                        newUser = factory.createUser(firstname, lastname, masterFaculty, masterSpeciality);
                        break;
                    case 3:  // GradStudent
                        Faculty gradFaculty = selectFaculty();
                        Speciality gradSpeciality = selectSpeciality();
                        newUser = factory.createUser(firstname, lastname, gradFaculty, gradSpeciality);
                        break;
                    case 4:  // Teacher
                        TeacherType teacherType = selectTeacherType();
                        Faculty teacherFaculty = selectFaculty();
                        newUser = factory.createUser(firstname, lastname, teacherType, teacherFaculty);
                        break;
                    case 5:  // Manager
                        newUser = factory.createUser(firstname, lastname);
                        break;
//                    case 6:  // Researcher
//                        Faculty researcherFaculty = selectFaculty();
//                        newUser = factory.createUser(firstname, lastname, researcherFaculty);
//                        break;
                    default:
                        System.out.println("Invalid choice. User creation cancelled.");
                        return;
                }

                if (newUser != null) {
                    String email = Credentials.generateEmail(firstname, lastname, newUser.getClass().getName());
                    String pass = Credentials.generatePassword();
                    Credentials credentials = new Credentials(email, pass);

                    admin.addUser(newUser);

                    System.out.println("User created successfully!");
                    System.out.println("Generated Email: " + credentials.getEmail());
                    System.out.println("Generated Password (DON'T SHARE): " + pass);
                    newUser.getNotifications().add(new Message(admin, "Generated Password (DON'T SHARE): " + pass));

                    Database.getInstance().getUsers().put(credentials, newUser);
                }

            } catch (IOException | NumberFormatException e) {
                System.out.println("An error occurred while adding the user.");
            }
        }

    private Faculty selectFaculty() throws IOException {
        while (true) {
            System.out.println("Select a faculty:");
            for (Faculty faculty : Faculty.values()) {
                System.out.println("[" + (faculty.ordinal() + 1) + "] " + faculty);
            }
            try {
                int facultyChoice = Integer.parseInt(reader.readLine()) - 1;
                if (facultyChoice >= 0 && facultyChoice < Faculty.values().length) {
                    return Faculty.values()[facultyChoice];
                } else {
                    System.out.println("Invalid choice. Please select a valid faculty.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private Speciality selectSpeciality() throws IOException {
        while (true) {
            System.out.println("Select a speciality:");
            for (Speciality speciality : Speciality.values()) {
                System.out.println("[" + (speciality.ordinal() + 1) + "] " + speciality);
            }
            try {
                int specialityChoice = Integer.parseInt(reader.readLine()) - 1;
                if (specialityChoice >= 0 && specialityChoice < Speciality.values().length) {
                    return Speciality.values()[specialityChoice];
                } else {
                    System.out.println("Invalid choice. Please select a valid speciality.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private TeacherType selectTeacherType() throws IOException {
        while (true) {
            System.out.println("Select a teacher type:");
            for (TeacherType teacherType : TeacherType.values()) {
                System.out.println("[" + (teacherType.ordinal() + 1) + "] " + teacherType);
            }
            try {
                int teacherTypeChoice = Integer.parseInt(reader.readLine()) - 1;
                if (teacherTypeChoice >= 0 && teacherTypeChoice < TeacherType.values().length) {
                    return TeacherType.values()[teacherTypeChoice];
                } else {
                    System.out.println("Invalid choice. Please select a valid teacher type.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }


}



    public static class DeleteUserCommand implements Command {
        private final Admin admin;
        private final BufferedReader reader;

        public DeleteUserCommand(Admin admin, BufferedReader reader) {
            this.admin = admin;
            this.reader = reader;
        }

        @Override
        public void execute() {
            logging("DeleteUser", admin);
            try {
                System.out.print("Enter user email to delete: ");
                String email = reader.readLine();

                User userToDelete = Database.getInstance().findUserByEmail(email);
                if (userToDelete != null) {
                    admin.deleteUser(userToDelete);
                } else {
                    System.out.println("User not found.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred while deleting the user.");
            }
        }
    }

    public static class UpdateUserCommand implements Command {
        private final Admin admin;
        private final BufferedReader reader;

        public UpdateUserCommand(Admin admin, BufferedReader reader) {
            this.admin = admin;
            this.reader = reader;
        }

        @Override
        public void execute() {
            logging("UpdateUser", null); // No direct reference to Admin here
            try {
                System.out.print("Enter user email to update: ");
                String email = reader.readLine();

                Database database = Database.getInstance();
                User userToUpdate = database.findUserByEmail(email);
                if (userToUpdate != null) {
                    System.out.println("Updating user: " + userToUpdate);
                    System.out.print("Enter new first name (leave blank to keep unchanged): ");
                    String firstname = reader.readLine();

                    System.out.print("Enter new last name (leave blank to keep unchanged): ");
                    String lastname = reader.readLine();

                    if (!firstname.isEmpty()) userToUpdate.setFirstname(firstname);
                    if (!lastname.isEmpty()) userToUpdate.setLastname(lastname);

                    String newPass = database.updateUser(userToUpdate);
                    System.out.println("Generated Password (DON'T SHARE): " + newPass);
                    userToUpdate.getNotifications().add(new Message(admin, "Generated Password (DON'T SHARE): " + newPass));
                } else {
                    System.out.println("User not found.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred while updating the user.");
            }
        }
    }


    public static class ViewLogsCommand implements Command {
        private final Admin admin;

        public ViewLogsCommand(Admin admin) {
            this.admin = admin;
        }

        @Override
        public void execute() {
            logging("ViewLogs", admin);
            admin.viewLogs();
        }
    }

    public static class ExitCommand implements Command {
        @Override
        public void execute() {
            System.out.println("Exiting...");
            System.exit(0);
        }
    }


    // GradStudentMenu

    public static class ViewResearchTopicCommand implements Command {
        private final GradStudent gradStudent;

        public ViewResearchTopicCommand(GradStudent gradStudent) {
            this.gradStudent = gradStudent;
        }

        @Override
        public void execute() {
            logging("ViewResearchTopic", gradStudent);
            String topic = gradStudent.getResearchTopic();
            if (topic == null || topic.isEmpty()) {
                System.out.println("No research topic set.");
            } else {
                System.out.println("Research Topic: " + topic);
            }
        }
    }


    public static class SetResearchTopicCommand implements Command {
        private final GradStudent gradStudent;
        private final BufferedReader reader;

        public SetResearchTopicCommand(GradStudent gradStudent, BufferedReader reader) {
            this.gradStudent = gradStudent;
            this.reader = reader;
        }

        @Override
        public void execute() {
            logging("SetResearchTopic", gradStudent);
            try {
                System.out.print("Enter a new research topic: ");
                String topic = reader.readLine();
                gradStudent.setResearchTopic(topic);
                System.out.println("Research topic set successfully.");
            } catch (IOException e) {
                System.out.println("An error occurred while setting the research topic.");
            }
        }
    }

    public static class ViewPublicationsCommand implements Command {
        private final GradStudent gradStudent;

        public ViewPublicationsCommand(GradStudent gradStudent) {
            this.gradStudent = gradStudent;
        }

        @Override
        public void execute() {
            logging("ViewPublications", gradStudent);
            if (gradStudent.getPublications().isEmpty()) {
                System.out.println("No publications found.");
            } else {
                System.out.println("Publications:");
                for (String publication : gradStudent.getPublications()) {
                    System.out.println("- " + publication);
                }
            }
        }
    }


    public static class AddPublicationCommand implements Command {
        private final GradStudent gradStudent;
        private final BufferedReader reader;

        public AddPublicationCommand(GradStudent gradStudent, BufferedReader reader) {
            this.gradStudent = gradStudent;
            this.reader = reader;
        }

        @Override
        public void execute() {
            logging("AddPublication", gradStudent);
            try {
                System.out.print("Enter the title of the publication: ");
                String publication = reader.readLine();
                gradStudent.addPublication(publication);
                System.out.println("Publication added successfully.");
            } catch (IOException e) {
                System.out.println("An error occurred while adding the publication.");
            }
        }
    }

    public static class ConductResearchCommand implements Command {
        private final GradStudent gradStudent;

        public ConductResearchCommand(GradStudent gradStudent) {
            this.gradStudent = gradStudent;
        }

        @Override
        public void execute() {
            logging("ConductResearch", gradStudent);
            gradStudent.research();
            System.out.println("Research in progress...");
        }
    }



}