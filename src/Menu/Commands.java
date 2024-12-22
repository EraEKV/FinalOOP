package Menu;

import Academic.JournalLesson;
import CustomExceptions.InvalidAuthDataException;
import Enums.*;
import Users.*;
import System.Organization;
import Database.Database;
import Academic.Course;
import System.Message;
import System.Request;
import Users.Rector;
import System.Credentials;
import System.UniversitySystemMediator;
import Research.*;
import System.Notification;
import System.Complaint;

import java.io.BufferedReader;
import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class Commands {
    //
    //
    //    MAIN MENU
    //
    //


    public static class Authenticate implements Command {
        private BufferedReader reader;

        public Authenticate(BufferedReader reader) {
            this.reader = reader;
        }

        @Override
        public void execute() {
            try {
                User user = UniversitySystemMediator.authenticateUser(reader);
                if(user == null) return;

                Database.getInstance().viewNews(1);

                switch (user.getClass().getSimpleName()) {
                    case "Student":
                        Student student = (Student) user;
                        System.out.println(student);
                        StudentMenu studentMenu = new StudentMenu(student, reader);
                        studentMenu.displayMenu();
                        break;

                    case "Teacher":
                        Teacher teacher = (Teacher) user;
                        System.out.println(teacher);
                        TeacherMenu teacherMenu = new TeacherMenu(teacher, reader);
                        teacherMenu.displayMenu();
                        break;

                    case "Manager":
                        Manager manager = (Manager) user;
                        System.out.println(manager);
                        ManagerMenu managerMenu = new ManagerMenu(manager, reader);
                        managerMenu.displayMenu();
                        break;

//                    case "Researcher":
//                        Researcher researcher = (Researcher) user;
//                        researcher.toString();
//                        ResearcherMenu researcherMenu = new ResearcherMenu(researcher, reader);
//                        researcherMenu.showMenu();
//                        break;

                    case "Rector":
                        Rector rector = (Rector) user;
                        new ShowRector().execute();
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            System.err.println("Thread interrupted: " + e.getMessage());
                            Thread.currentThread().interrupt();
                        }
                        System.out.println(rector);
                        RectorMenu rectorMenu = new RectorMenu(rector, reader);
                        rectorMenu.displayMenu();

                    case "Admin":
                        Admin admin = (Admin) user;
                        System.out.println(admin);
                        AdminMenu adminMenu = new AdminMenu(admin, reader);
                        adminMenu.displayMenu();
                        break;

                    default:
                        System.out.println("Unknown user type. Cannot proceed.");
                        break;
                }
            } catch (InvalidAuthDataException e) {
                System.out.println("Authentication failed: " + e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }


    public static class SecretCommand implements Command {

        public SecretCommand() {

        }


        @Override
        public void execute() {
            String urlString = "http://ascii.live/can-you-hear-me"; // Rickroll

            try {
                HttpClient client = HttpClient.newHttpClient();

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI(urlString))
                        .header("User-Agent", "curl/7.79.1") // curl imitation
                        .GET()
                        .build();

                // reading chunked data

                client.sendAsync(request, HttpResponse.BodyHandlers.ofLines())
                        .thenAccept(response -> {
                            if (response.statusCode() == 200) {
                                response.body().forEach(System.out::println);
                            } else {
                                System.err.println("Сервер вернул код ошибки: " + response.statusCode());
                            }
                        })
                        .join();
            } catch (Exception e) {
                e.printStackTrace();
            }

            logging("Rickrolled", null);
        }
    }




    public static class ChangePasswordCommand implements Command  {
        private User user;
        private BufferedReader reader;
        public ChangePasswordCommand(User user, BufferedReader reader) {
            this.user = user;
            this.reader = reader;
        }

        @Override
        public void execute() {
            Database db = Database.getInstance();
            try {
                oldPass: while (true) {
                    System.out.println("Enter old password(to exit enter 0): ");
                    String oldPass = reader.readLine().trim();

                    if(oldPass.equals("0")) break oldPass;

                    Credentials creds = db.findCredentials(new Credentials(user.getEmail(), oldPass));
                    if (creds == null ) {
                        System.out.println("Password is not correct");
                    } else {
                        newPass: while (true) {
                            System.out.println("Enter new password: ");
                            String newPass1 = reader.readLine().trim();

                            System.out.println("Enter new password again: ");
                            String newPass2 = reader.readLine().trim();

                            if(!newPass1.equals(newPass2)) {
                                System.out.println("Passwords do not match");
                            } else {
                                creds.changePassword(oldPass, newPass1);
                                System.out.println("Password changed");
                                break oldPass;
                            }
                        }

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            logging("PasswordChanging", user);
        }

    }



    public static class ViewNotificationsCommand implements Command {
        private final BufferedReader reader;
        private final User user;
        private final int PAGE_SIZE = 5;

        public ViewNotificationsCommand(User user, BufferedReader reader) {
            this.reader = reader;
            this.user = user;
        }

        @Override
        public void execute() {
            try {
                while (true) {
                    System.out.println("Choose what you want to view:");
                    System.out.println("[1] Messages");
                    System.out.println("[2] Complaints");
                    System.out.println("[3] Invites");
                    System.out.println("[4] News");
                    System.out.println("[0] Exit");

                    int choice = Integer.parseInt(reader.readLine());

                    System.out.println(user.getNotifications());

                    switch (choice) {
                        case 1 -> viewNotifications("Message");
                        case 2 -> viewNotifications("Complaint");
                        case 3 -> viewNotifications("Invite");
                        case 4 -> viewNews();
                        case 0 -> {
                            return;
                        }
                        default -> System.out.println("Invalid choice. Please try again.");
                    }
                }
            } catch (IOException | NumberFormatException e) {
                System.out.println("Error occurred: " + e.getMessage());
            }
        }

        private void viewNotifications(String notificationType) throws IOException {
            Vector<Notification> notifications = user.getNotifications();
            int total = (int) notifications.stream()
                    .filter(notification -> notification.getClass().getSimpleName().equals(notificationType))
                    .count();
            if (total == 0) {
                System.out.println("No " + notificationType.toLowerCase() + " notifications available.");
                return;
            }

            int currentPage = 0;

            while (true) {
                System.out.println("\n" + notificationType + " Notifications (Page " + (currentPage + 1) + "):");

                notifications.stream()
                        .filter(notification -> notification.getClass().getSimpleName().equals(notificationType))
                        .skip(currentPage * PAGE_SIZE)
                        .limit(PAGE_SIZE)
                        .forEach(notification -> System.out.println(notification));

                System.out.println("\n[1] Next Page [2] Previous Page [0] Back");
                System.out.print("Your choice: ");
                int action = Integer.parseInt(reader.readLine());

                if (action == 1 && (currentPage + 1) * PAGE_SIZE < total) {
                    currentPage++;
                } else if (action == 2 && currentPage > 0) {
                    currentPage--;
                } else if (action == 0) {
                    break;
                } else {
                    System.out.println("Invalid choice or no more pages.");
                }
            }
        }

        private void viewNews() {
            try {
                Database db = Database.getInstance();
                int totalPages = db.getTotalNewsPages();
                if (totalPages == 0) {
                    System.out.println("No news available.");
                    return;
                }

                int currentPage = 1;

                while (true) {
                    System.out.println("\nDisplaying news for page " + currentPage + " of " + totalPages + ":");
                    db.viewNews(currentPage);

                    // Меню навигации
                    System.out.println("\n[1] Next Page [2] Previous Page [0] Back");
                    System.out.print("Choose an option: ");
                    String input = reader.readLine().trim();

                    switch (input) {
                        case "1": // Next Page
                            if (currentPage < totalPages) {
                                currentPage++;
                            } else {
                                System.out.println("You are already on the last page.");
                            }
                            break;

                        case "2": // Previous Page
                            if (currentPage > 1) {
                                currentPage--;
                            } else {
                                System.out.println("You are already on the first page.");
                            }
                            break;

                        case "0": // Back
                            System.out.println("Exiting news viewer.");
                            return;

                        default:
                            System.out.println("Invalid option. Please select 1, 2, or 0.");
                    }
                }
            } catch (IOException e) {
                System.out.println("An error occurred while viewing news: " + e.getMessage());
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
            try {
                System.out.println("=== Send Message ===");
                Database db = Database.getInstance();

                recipient: while (true) {
                    System.out.println("Enter a recipient email without domain(like: e_kokenov):");

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

                        System.out.println("Message sent to " + selectedRecipient.getFirstname() + " " + selectedRecipient.getLastname());

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
            logging("PutAttendance", sender);
        }
    }


    public static class ChangeToResearcherMenu implements Command {
        private Researcher researcher;
        private BufferedReader reader;

        public ChangeToResearcherMenu(Researcher researcher, BufferedReader reader) {
            this.researcher = researcher;
            this.reader = reader;
        }


        @Override
        public void execute() {
            User user = researcher.getAcademicContributor().getUser();
            if(user != null) {
                ResearcherMenu menu = new ResearcherMenu(researcher, reader);
                menu.displayMenu();
            } else {
                System.out.println("You are not researcher");
            }

            logging("Changed to Researcher Menu", user);
        }
    }


    //
    //
    //  STUDENT MENU
    //
    //

    public static class ViewMarksCommand implements Command {
        private final Student student;

        public ViewMarksCommand(Student student) {
            this.student = student;
        }

        @Override
        public void execute() {
            student.viewMarks();
            logging("ViewMarks", student);
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
            logging("ViewTranscript", student);
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
            logging("RateTeacher", student);
        }
    }

    // ViewTeacherInfoCommand
    public static class ViewTeacherInfoCommand implements Command {
        private final Student student;
        private final BufferedReader reader;

        public ViewTeacherInfoCommand(Student student, BufferedReader reader) {
            this.student = student;
            this.reader = reader;
        }

        @Override
        public void execute() {
            Vector<Teacher> teachers = student.getTeachers();
            try {
                System.out.println("Select a teacher to view their information:");
                for (int i = 0; i < teachers.size(); i++) {
                    System.out.println("[" + (i + 1) + "] " + teachers.get(i).getFirstname() + " " + teachers.get(i).getLastname());
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
            logging("ViewTeacherInfo", student);
        }
    }

    // ManageOrganizationsCommand
    public static class ManageOrganizationsCommand implements Command {
        private final Student student;
        private final BufferedReader reader;

        public ManageOrganizationsCommand(Student student, BufferedReader reader) {
            this.student = student;
            this.reader = reader;
        }

        @Override
        public void execute() {
            while (true) {
                List<Organization> organizations = Database.getInstance().getStudentOrganizations();
                try {
                    System.out.println("\n=== Manage Organizations ===");
                    System.out.println("[1] Create Organization");
                    System.out.println("[2] Join Organization");
                    System.out.println("[3] Leave Organization");
                    System.out.println("[4] Delete Organization");
                    System.out.println("[0] Back to Main Menu");
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
                logging("ManageOrganization", student);
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

                student.getRegisteredCourses().putAll(registeredCoursesMap);

                for (Map.Entry<Course, Teacher> entry : registeredCoursesMap.entrySet()) {
                    Course course = entry.getKey();
                    Teacher teacher = entry.getValue();

                    course.addStudentToTeacher(teacher, student);
                }


            } catch (IOException | NumberFormatException e) {
                System.out.println("Invalid input. Please try again.");
            }
            logging("RegisterToCourses", student);
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
            try {
                System.out.println("Enter course code:");
                String code = reader.readLine().trim();
                if (code.isEmpty()) {
                    System.out.println("Course code cannot be empty.");
                    return;
                }

                if (Database.getInstance().getCourses().stream().anyMatch(course -> course.getCode().equals(code))) {
                    System.out.println("A course with this code already exists.");
                    return;
                }

                System.out.println("Enter course name:");
                String name = reader.readLine().trim();
                if (name.isEmpty()) {
                    System.out.println("Course name cannot be empty.");
                    return;
                }

                Faculty faculty = selectFaculty(reader);
                Speciality speciality = selectSpeciality(reader);


                System.out.println("Enter number of credits (1-10):");
                int credits;
                try {
                    credits = Integer.parseInt(reader.readLine().trim());
                    if (credits < 1 || credits > 10) {
                        System.out.println("Credits must be between 1 and 10.");
                        return;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number for credits. Please try again.");
                    return;
                }

                Semester semester = selectSemester(reader);

                Course course = new Course(code, name, faculty, speciality, credits, semester);
                manager.addCourse(course);

                System.out.println("Course added successfully:\n" + course);
            } catch (IOException e) {
                System.out.println("An error occurred while reading input. Please try again.");
            }
            logging("AddCourse", manager);
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

        public AddNewsCommand(Manager manager, BufferedReader reader) {
            this.manager = manager;
            this.reader = reader;
        }

        @Override
        public void execute() {
            try {
                System.out.print("Do you want to set yourself as the author? (y/Enter the name of author): ");
                String author = reader.readLine();
                if (author.equals("y")) {
                    author = manager.getFirstname() + " " + manager.getLastname();
                }

                System.out.print("Enter news title: ");
                String title = reader.readLine();
                System.out.print("Enter news content: ");
                String content = reader.readLine();

                NewsTopic newsTopic = selectNewsTopic();

                UniversitySystemMediator.publishNews(author, newsTopic, title, content);
                System.out.println("News has been successfully added.");

            } catch (IOException e) {
                System.out.println("Error occurred while adding news: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid topic selection. Please try again.");
            }

            logging("AddNews", manager);
        }

        private NewsTopic selectNewsTopic() throws IOException {
            System.out.println("Select a news topic:");
            NewsTopic[] topics = NewsTopic.values();
            for (int i = 0; i < topics.length; i++) {
                System.out.println("[" + (i + 1) + "] " + topics[i]);
            }
            System.out.print("Enter the number corresponding to the topic: ");
            int choice = Integer.parseInt(reader.readLine()) - 1;

            if (choice < 0 || choice >= topics.length) {
                throw new IllegalArgumentException("Invalid topic index.");
            }
            return topics[choice];
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
            logging("RedirectRequest", manager);
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
            logging("OpenCloseRegistration", manager);
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
            logging("GetCourseReport", manager);
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
            logging("GetStudentReport", manager);
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
            logging("SetStudentRegistration", manager);
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
            logging("ViewCourses", teacher);
        }
    }

    public static class SendComplaint implements Command {
        private Teacher teacher;
        private BufferedReader reader;
        public SendComplaint(Teacher teacher, BufferedReader reader) {
            this.teacher = teacher;
            this.reader = reader;
        }

        @Override
        public void execute() {
            Database db = Database.getInstance();
            try {
                System.out.println("=== Send Complaint ===");
                Vector<Course> courses = db.getCourses();
                if (courses == null || courses.isEmpty()) {
                    System.out.println("No courses available.");
                    return;
                }

                int courseChoice = chooseCourse(teacher, reader);
                if (courseChoice == 0) {
                    return;
                }

                Course selectedCourse = courses.get(courseChoice);

                Vector<Student> students = getStudentsForCourse(teacher, selectedCourse);
                if (students.isEmpty()) {
                    System.out.println("No students enrolled in this course.");
                } else {
                    showStudents(students);
                    int studentChoice = chooseStudent(reader, students);
                    if (studentChoice != 0) {
                        Student selectedStudent = students.get(studentChoice);
                        System.out.println("You selected: " + selectedStudent.getFirstname() + " " + selectedStudent.getLastname());

                        System.out.print("Are you sure you want to send a complaint for this student? (y/n): ");
                        String confirmation = reader.readLine().trim().toLowerCase();
                        if (confirmation.equals("y")) {
                            System.out.print("Enter complaint text: ");
                            String complaintText = reader.readLine();

                            System.out.println("Select urgency level:");
                            for (Urgency urgency : Urgency.values()) {
                                System.out.println("[" + (urgency.ordinal() + 1) + "] " + urgency);
                            }
                            System.out.print("Enter urgency choice: ");
                            int urgencyChoice = Integer.parseInt(reader.readLine()) - 1;

                            Urgency selectedUrgency = Urgency.values()[urgencyChoice];

                            Complaint newComplaint = new Complaint(teacher, selectedStudent, complaintText, selectedUrgency);

                            System.out.println("Complaint created: " + newComplaint);
                            System.out.print("Confirm sending the complaint? (confirm to send): ");
                            String finalConfirmation = reader.readLine().trim().toLowerCase();
                            if (finalConfirmation.equals("confirm")) {
                                DisciplinaryCommittee.getInstance().getComplaints().add(newComplaint);
                                System.out.println("Complaint sent successfully.");
                            } else {
                                System.out.println("Complaint not sent.");
                            }
                        } else {
                            System.out.println("Complaint creation canceled.");
                        }
                    }
                }

            } catch (Exception e) {
                System.out.println("Error occurred while managing courses.");
            }

            logging("SendComplaint", teacher);
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
            Database db = Database.getInstance();
            try {
                System.out.println("=== Manage Course ===");
                Vector<Course> courses = db.getCourses();
                if (courses == null || courses.isEmpty()) {
                    System.out.println("No courses available.");
                    return;
                }

                int courseChoice = chooseCourse(teacher, reader);
                if (courseChoice == 0) {
                    return;
                }

                Course selectedCourse = courses.get(courseChoice);

                Vector<Student> students = getStudentsForCourse(teacher, selectedCourse);
                if (students.isEmpty()) {
                    System.out.println("No students enrolled in this course.");
                } else {
                    showStudents(students);
                    int studentChoice = chooseStudent(reader, students);
                    if (studentChoice != -1) {
                        Student selectedStudent = students.get(studentChoice);
                        System.out.println("You selected: " + selectedStudent.getFirstname() + " " + selectedStudent.getLastname());
                    }
                }

            } catch (Exception e) {
                System.out.println("Error occurred while managing courses.");
            }

            logging("ManageCourse", teacher);
        }


    }

    private static int chooseCourse(Teacher teacher, BufferedReader reader) throws IOException {
        try {
            Vector<Course> courses = teacher.getCurrentCourses();
            System.out.println("Select a course from the list:");
            for (int i = 0; i < courses.size(); i++) {
                System.out.println("[" + (i + 1) + "] " + courses.get(i).getName());
            }
            System.out.print("Enter course number (or 0 to cancel): ");
            int choice = Integer.parseInt(reader.readLine()) - 1;
            if (choice >= 0 && choice < courses.size()) {
                return choice;
            }
            return -1;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Vector<Student> getStudentsForCourse(Teacher teacher, Course course) {
        Vector<Student> students = new Vector<>();
        if (course.getTeachers().contains(teacher)) {
            students = course.getStudents();
        }
        return students;
    }

    private static void showStudents(Vector<Student> students) {
        System.out.println("Select a student from the list:");
        for (int i = 0; i < students.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + students.get(i).getFirstname() + " " + students.get(i).getLastname());
        }
    }

    private static int chooseStudent(BufferedReader reader, Vector<Student> students) throws IOException {
        System.out.print("Enter student number to select (or 0 to cancel): ");
        int choice = Integer.parseInt(reader.readLine()) - 1;
        if (choice >= 0 && choice < students.size()) {
            return choice;
        }
        return -1;
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
            try {
                System.out.println("=== View Students Info ===");
                Vector<Course> courses = teacher.getCurrentCourses();

                if (courses == null) {
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
            logging("ViewStudentsInfo", teacher);
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
//            logging("PutMarks", teacher);
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
            try {
                System.out.println("=== Put Attendance ===");

                Vector<Course> courses = teacher.getCurrentCourses();

                if(courses == null) {
                    System.out.println("No courses available.");
                    return;
                }

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

            logging("PutAttendance", teacher);
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
            try {
                main: while (true) {
                    System.out.println("Select the type of user to add:");
                    System.out.println("[1] Student");
                    System.out.println("[2] MasterStudent");
                    System.out.println("[3] GradStudent");
                    System.out.println("[4] Teacher");
                    System.out.println("[5] Manager");
                    System.out.println("[6] Researcher");
                    System.out.println("[0] Exit");

                    String input = reader.readLine();
                    int choice = Integer.parseInt(input);

                    if(choice == 0) break main;

                    user: while(true) {
                        System.out.print("Enter first name: ");
                        String firstname = reader.readLine().trim();

                        System.out.print("Enter last name: ");
                        String lastname = reader.readLine().trim();



                        UserFactory factory = UserFactory.getInstance();
                        User newUser = null;
                        Researcher newResarcher = null;

                        String email = null;

                        switch (choice) {
                            case 1:  // Student
                                Faculty studentFaculty = selectFaculty(reader);
                                Speciality studentSpeciality = selectSpeciality(reader);
                                newUser = factory.createUser(firstname, lastname, studentFaculty, studentSpeciality);
                                break;
                            case 2:  // MasterStudent
                                Faculty masterFaculty = selectFaculty(reader);
                                Speciality masterSpeciality = selectSpeciality(reader);
                                newUser = factory.createUser(firstname, lastname, masterFaculty, masterSpeciality);
                                break;
                            case 3:  // GradStudent
                                Faculty gradFaculty = selectFaculty(reader);
                                Speciality gradSpeciality = selectSpeciality(reader);
                                System.out.println("Choose");
                                String studentType = selectStudentType();
                                newUser = factory.createUser(firstname, lastname, gradFaculty, gradSpeciality, studentType);
                                break;
                            case 4:  // Teacher
                                TeacherType teacherType = selectTeacherType();
                                Faculty teacherFaculty = selectFaculty(reader);
                                newUser = factory.createUser(firstname, lastname, teacherType, teacherFaculty);
                                break;
                            case 5:  // Manager
                                newUser = factory.createUser(firstname, lastname);
                                break;
//                            case 6:  // Researcher
//                                Faculty selectedFaculty = selectFaculty(reader);
//                                newResarcher = factory.createUser(firstname + lastname, selectedFaculty);
//                                System.out.println("Please enter the email for add Researcher to existing User. You can enter without domain (@kbtu.kz): ");
//                                email = reader.readLine();
//                                break;
                            default:
                                System.out.println("Invalid choice. User creation cancelled.");
                                return;
                        }

                        if (newUser != null) {
                            email = Credentials.generateEmail(firstname, lastname, newUser.getClass().getSimpleName());
                            String pass = Credentials.generatePassword();
                            Credentials credentials = new Credentials(email, pass);

                            newUser.setEmail(email);
                            admin.addUser(newUser);

                            System.out.println("User created successfully!");
                            System.out.println("Generated Email: " + credentials.getEmail());
                            System.out.println("Generated Password (DON'T SHARE): " + pass);
                            newUser.getNotifications().add(new Message(admin, "Generated Password (DON'T SHARE): " + pass));

                            Database.getInstance().getUsers().put(credentials, newUser);
                            break user;
                        } else if(newResarcher != null) {
                            Database db = Database.getInstance();

                            if(email == null) continue user;
                            if(!email.contains("@kbtu.kz")) email += "@kbtu.kz";

                            User user = db.findUserByEmail(email);

                            if(user == null) {
                                System.out.println("User with email " + email + " not found. This needed to add new Researcher to existed User.");
                                continue user;
                            } else {
                                if(user instanceof Student) {
                                    Student s = (Student) user;
                                    s.beResearcher();
                                }

                                db.getResearchers().add(newResarcher);

                                break user;
                            }
                        }
                    }
                }



            } catch (IOException | NumberFormatException e) {
                System.out.println("An error occurred while adding the user.");
            }
            logging("AddUser", admin);
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

        private String selectStudentType() throws IOException {
            while (true) {
                System.out.println("Select a student type:");
                System.out.println("[1] MasterStudent");
                System.out.println("[2] PhDStudent");
                try {
                    int choice = Integer.parseInt(reader.readLine());
                    if(choice == 1) {
                        return MasterStudent.class.getSimpleName();
                    } else if(choice == 2) {
                        return PhDStudent.class.getSimpleName();
                    } else {
                        System.out.println("Invalid choice. Please select a valid student type.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                }
            }
        }


    }

    private static Faculty selectFaculty(BufferedReader reader) throws IOException {
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

    private static Speciality selectSpeciality(BufferedReader reader) throws IOException {
        while (true) {
            System.out.println("Select a speciality:");
            for (Speciality speciality : Speciality.values()) {
                System.out.println("[" + (speciality.ordinal() + 1) + "] " + speciality);
            }
            try {
                int choice = Integer.parseInt(reader.readLine()) - 1;
                if (choice >= 0 && choice < Speciality.values().length) {
                    return Speciality.values()[choice];
                } else {
                    System.out.println("Invalid choice. Please select a valid speciality.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    private static Semester selectSemester(BufferedReader reader) throws IOException {
        while (true) {
            System.out.println("Select a semester:");
            for (Semester semester : Semester.values()) {
                System.out.println("[" + (semester.ordinal() + 1) + "] " + semester);
            }
            try {
                int choice = Integer.parseInt(reader.readLine()) - 1;
                if (choice >= 0 && choice < Semester.values().length) {
                    return Semester.values()[choice];
                } else {
                    System.out.println("Invalid choice. Please select a valid faculty.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
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
            try {
                System.out.print("Enter user email to delete: ");
                String email = reader.readLine();

                User userToDelete = Database.getInstance().findUserByEmail(email);
                if (userToDelete != null) {
                    admin.deleteUser(userToDelete);
                    System.out.println("User deleted successfully!");
                } else {
                    System.out.println("User not found.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred while deleting the user.");
            }
            logging("DeleteUser", admin);
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
            logging("UpdateUser", null);
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





    //
    //
    // GradStudentMenu
    //
    //


    public static class OpenTeacherMenu implements Command {
        private BufferedReader reader;
        private GradStudent user;

        public OpenTeacherMenu(GradStudent user, BufferedReader reader) {
            this.reader = reader;
            this.user = user;
        }


        @Override
        public void execute() {
            Teacher teacher = user.getTeacher();
            if(teacher == null) {
                System.out.println("You are not a teacher, please send request to managers.");
            } else {
                new TeacherMenu(teacher, reader).displayMenu();
            }

            logging("OpenTeacherMenu", user);
        }
    }


    public static class ViewResearchTopicCommand implements Command {
        private final GradStudent gradStudent;

        public ViewResearchTopicCommand(GradStudent gradStudent) {
            this.gradStudent = gradStudent;
        }

        @Override
        public void execute() {
            String topic = gradStudent.getResearchTopic();
            if (topic == null || topic.isEmpty()) {
                System.out.println("No research topic set.");
            } else {
                System.out.println("Research Topic: " + topic);
            }
            logging("ViewResearchTopic", gradStudent);
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
            try {
                System.out.print("Enter a new research topic: ");
                String topic = reader.readLine();
                gradStudent.setResearchTopic(topic);
                System.out.println("Research topic set successfully.");
            } catch (IOException e) {
                System.out.println("An error occurred while setting the research topic.");
            }
            logging("SetResearchTopic", gradStudent);
        }
    }

    public static class ViewPublicationsCommand implements Command {
        private final GradStudent gradStudent;

        public ViewPublicationsCommand(GradStudent gradStudent) {
            this.gradStudent = gradStudent;
        }

        @Override
        public void execute() {
            if (gradStudent.getPublications().isEmpty()) {
                System.out.println("No publications found.");
            } else {
                System.out.println("Publications:");
                for (String publication : gradStudent.getPublications()) {
                    System.out.println("- " + publication);
                }
            }
            logging("ViewPublications", gradStudent);
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
            try {
                System.out.print("Enter the title of the publication: ");
                String publication = reader.readLine();
                gradStudent.addPublication(publication);
                System.out.println("Publication added successfully.");
            } catch (IOException e) {
                System.out.println("An error occurred while adding the publication.");
            }
            logging("AddPublication", gradStudent);
        }
    }

    public static class ConductResearchCommand implements Command {
        private final GradStudent gradStudent;

        public ConductResearchCommand(GradStudent gradStudent) {
            this.gradStudent = gradStudent;
        }

        @Override
        public void execute() {
            gradStudent.research();
            System.out.println("Research in progress...");
            logging("ConductResearch", gradStudent);
        }
    }

    public static class SubscribeResearchJournalCommand implements Command {
        private final Subscriber user;
        private final BufferedReader reader;

        public SubscribeResearchJournalCommand(Subscriber user, BufferedReader reader) {
            this.user = user;
            this.reader = reader;
        }

        @Override
        public void execute() {
            List<ResearchJournal> journals = Database.getInstance().getResearchJournals();
            try {
                System.out.println("\nAvailable Research Journals:");
                for (int i = 0; i < journals.size(); i++) {
                    System.out.println("[" + (i + 1) + "] " + journals.get(i).getResearchJournalsName());
                }
                System.out.print("Enter the number of the journal you want to subscribe to: ");

                String input = reader.readLine();
                int choice;

                try {
                    choice = Integer.parseInt(input) - 1;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    return;
                }

                if (choice < 0 || choice >= journals.size()) {
                    System.out.println("Invalid choice. Please try again.");
                    return;
                }

                ResearchJournal selectedJournal = journals.get(choice);
                selectedJournal.subscribe(user);
                System.out.println("Successfully subscribed to " + selectedJournal.getResearchJournalsName() + ".");
            } catch (IOException e) {
                System.out.println("An error occurred while reading input. Please try again.");
            } catch (Exception e) {
                System.out.println("An unexpected error occurred: " + e.getMessage());
            }
            logging("SubscribeResearchJournal", (User) user);
        }
    }



    //
    //
    // RECTOR MENU
    //
    //

    public static class ShowRector implements Command {

        public ShowRector() {

        }

        @Override
        public void execute() {

            System.out.println("""
                                                                                                                                                                                                                                                 \s
                                                                                                                                                                                                                                                 \s
                                                                                                                                                                                                                                                 \s
                                                                                                                                                                                                                                                 \s
                                                                                                                                                                                                                                                 \s                                                                                                                                                                             \s
                                                                                                                                                                                                                                                 \s
                                                                                                                     %%%%%@@%%%@@                                                                                                                \s
                                                                                                              %%%@%%%%%%%%%%%%%%@@@@@@@                                                                                                          \s
                                                                                                          %%%%%@@@@@@@%@@@@@@%@@@@%%%%%%%@                                                                                                       \s
                                                                                                      ##%%%%%%@@@@@@@@@@@@@@@@@@%@@@%@@@@@@%                                                                                                     \s
                                                                                                    %%%%%%%%@@@@@%@@@@@@@@@@@@@@@@%%@@@@@@@@@                                                                                                    \s
                                                                                                  ##%%%%%%%@@@@@@%%@@@@@@@@@@@@@@@@@%%@@@@@@@@@                                                                                                  \s
                                                                                                 ##%%%%%%%%%%%%%#%@@@@@@@@@@@@@@@@@@@%%@@@@%@@@@%                                                                                                \s
                                                                                                #%%%%%%#%%%%%%%#%@@@@@%%%#####%%%%@%@@%%%@@@%@@@@@                                                                                               \s
                                                                                               ##%%%%%####*#%##%@@%%##**+++++**##%%@@@@@@@@%@@@@@@@                                                                                              \s
                                                                                              #%%%%%####***#%###*+++++===+++++***#####%%@@@@@@@@@@@@                                                                                             \s
                                                                                             %%%%%%##+++=*##*+============+++++*******##%%%@@@@@@@@@                                                                                             \s
                                                                                             ##%%%*+=+=+++=------=========++++++++******#%%%@@@@@@@@                                                                                             \s
                                                                                             ##%%*+======--=--============++++++++++****##%%@@@@@@@@                                                                                             \s
                                                                                            %%#%#+=========================+++++++++*****#%%%%%%@@@@                                                                                             \s
                                                                                            %%%#*+=========================++++++++******###%%%%%%@@                                                                                             \s
                                                                                            %%%#*+========-----===---=====++++************###%%%%%@@                                                                                             \s
                                                                                            %%%#+++=+******++=========++*#%%%%%%%%#********####%##%@                                                                                             \s
                                                                                            %##*++++*#######**+++==+++*##%%#**+**##%##******######%@                                                                                             \s
                                                                                            %##*++***+====+++++++==+*****+++++*****####******#####%*#                                                                                            \s
                                                                                            ###*++++=++**##%#*+=====+*#***#*#%@@%%####*******###*##*%%                                                                                           \s
                                                                                            *+*+==+++##*#%%%##+===-=+*##+#*++*#**###**+++*****##*##*#%                                                                                           \s
                                                                                            ++*+===+++====+***+=====+*#*+++++++++++++++++*******#%#**%                                                                                           \s
                                                                                             =++=======+++=========++***++=========++++++*******%@%%*#                                                                                           \s
                                                                                             +++========-----=======****++========++++++*********%%%#*                                                                                           \s
                                                                                             +++=====--------======++*****+=======++++***********%%%#*                                                                                           \s
                                                                                             =+++=====------=+=====++****#+======++++***********##**#                                                                                            \s
                                                                                              +++++====--==+++=====+*#****@#+==++++++*******#*******                                                                                             \s
                                                                                              *++==+======+**+*##**##%@@%%%%*+++++++***###**********                                                                                             \s
                                                                                               +++======++*+===++=+*######***+++++***######****##**                                                                                              \s
                                                                                               +++==+==++++======+++++*********+++***#####*******                                                                                                \s
                                                                                                +++=+++++++++=====+++++++***#****+*******###**##                                                                                                 \s
                                                                                                 +++==++++++++++***+*####%%%%%#*+++*****#######                                                                                                  \s
                                                                                                 +++++++++*#****++++++**#####*+++++**#**#######                                                                                                  \s
                                                                                                  +++++++++====+++++***####**+++++**##########                                                                                                   \s
                                                                                                   ++++++++=====++***********+*****###########                                                                                                   \s
                                                                                                    +++++++======+++++++++++******####%%#####*                                                                                                   \s
                                                                                                     *++++++=========++++++*****###%%%%%####**                                                                                                   \s
                                                                                                      ***+++++======++++******###%%%%%%####***                                                                                                   \s
                                                                                                       *****+++++++++++***####%%%%%%%%#####**+@@@                                                                                                \s
                                                                                                       +*************####%%%%%%%%%%%%######**+%@@@@@@                                                                                            \s
                                                                                                      @%*+******##%%%%%%%%%%%%%%%%%#########++%@@@@@@@@@@                                                                                        \s
                                                                                                @@@@@%%%+=++**********###%%%%%%%%##########+=*@@@@@@@@@@@@@@                                                                                     \s
                                                                                           %%%%@%%%%%%%%*-:=+++++*****##################%#+++#@@@@@@@@@@@@@@@@@                                                                                  \s
                                                                                      @@%@@@@@%%@@%%%%%%#-:::==+++++****#############%%#++++*@@@@@@@@@@@@@@@@@@@@@@                                                                              \s
                                                                                 @%%%%%@@@@@@%@@@%%%%%%%%+:::::-=++++++++**##########+++++++%@@@@@@@@@@@@@@@@@@@@@@@@@@@                                                                         \s
                                                                           @@@%%%%%@@@@@@@@%%@@@%%%%%%%%%#-::::::-++++++++*######*=---==+++#@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                                               \s
                                                                       @@@@@@@%@%@@%@@@@@@%%@@@@%%%%%%%%%%-::::::::-=++++******+-:----==++*@@@@@@@@@@@@@@@@@@@@@@@@@@@%%%%%%%%%%%%%%@@@@@@                                                       \s
                                                                @@@@@@@@@@%@@@@@@@@@@@@@%%@@@@%%%%%%%%%%%%=::::::::::-=++++*+-::::----=++*%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                                   \s
                                                            @@@@@@@@@%@@@%%%@@@@@@@@@@@%%@@@%%%%%%%%%%%%%%#::::::::::::-==-:::::::--==+**%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                                 \s
                                                          @@@%@@@@@@%%%%@@@@@@@@@@@@@@@@@%%%%%%%%%%%%%%%%%%-:::::::::::+*++-:::::--=++**%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                                \s
                                                         @%@@@@@@@@%%%%%@@@@%@@@@@@@@@@@@@@%%%%%%%%%%%%%%%@+::::::::-=++++***=:---=+***%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                                \s
                                                         %%@@%%%%%%%%%%%%@%@%@@@@@@@@@@@@@@%%%%%%%%%%%%%%%%#:::::::*+===++*####+-=++**#%@@@@@@@@@@@@@@@@@@@%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                               \s
                                                         %%%@@%%%%%%%%%%%%@%@@@@@@%@%@@@%%%@%%%%%%%%%%%%%%%@-::::-#*++==+**###%%*=++**#@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                               \s
                                                        %%%%@@%%%%%%%%%%%%@@@@@@@@@%@@%%%%%%%%%%%%%%%%%%%%%@+:::=###*+==+*###%%%%++++*%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                               \s
                                                        %%%%%@%%%%%%%%%%%%%%@@@%%%@%@%%%%%%%%%%%%%%%%%%%%%%@#::=####**+=*###%###+=-=+#@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                              \s
                                                       @@%%%%@@%%%%%%%%%%%%@@@@%@@@@%%%@@%%%%%%%%%%%%%%%%%%%%+-*###*-*++*#####**+-:=+@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@%@@@@@@@@@@@@@@@@@@@@@@@@@@                                              \s
                                                      @@@%%%%@@%%%%%%%%%%%@%@@@@%@@@%@@@@%%%%%%%@%@%@%%%%%%@@%#*=----+++*####***=--+%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                              \s
                                                      @@@%%%%@@@%%%%%%%%%%%%@@%%%%@%@%@@%%%%%%@@@@@@@%%%%%%%@#+-=++=+*=+####****-:-*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                            \s
                                                     %%%%%%%%@@@%%%%%%%%%%%%%%%@@%%%@@@%%@%%@%@@@@@@@%%%%%%%@#+-====*++*#####**+-:=%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                           \s
                                                    %%%%%%%%%@@@%%%%%%%%%%%%%%%@@@%%%@%@@@@@@@@@@@@@@%%%%%%%@%*-===*+++*#####**+--+@@@@@@@@@@@@%%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                          \s
                                                    %%%%%%%%%@@@@%%%%%%%%%%%%%%@@@@%@@@@@@@@@@@@@%@@@%%%%%%%@@#=-=+++++*######*+--#@@@@@@@@#####%%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                         \s
                                                   %%%%%%%%%%@@@@%%%%%%%%%%%@%@@@@@@@@@@@@@@@@@@@@@@%@%%%%%%@@%*-+++=++*######*+-=%@@@@@@%*+#**#*++@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                        \s
                                                   %@@%%%%%%%@@@@%%%%%%%%@%@%@@%%@%%@@@@@@@@@@@@@@@@@@%%%%%%@@%*=++=+++*#######+-*%@@@@@@#*+****+++%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                       \s
                                                  %%@@%%%%%%%@@@@%%%%%%@@@@@%%%@@@@@@@@@@@@@@@@@@@@@@@%%%%%%%@%#+==+=++*#######+=%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                       \s
                                                 %%%%%@%%%%%%@@@@%%%%%@@@@@%@%%%@@%@@@@@@@@@@@@@@@@@@%%%%%%%%@@#*====++*#######=+%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                       \s
                                                %%%%%%%%%@@@@@@@@%%%%@@@@@%%%%@@@%%@@@@@@@@@@@@@@@@@@%%%%%%%%@@%*==++++**######+#@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                      \s
                                               %%%%%%%%%%@@@@@@@@@%%@@@@@@@%%%@%@@%@@@@@@@@@@@@@@@@@@@@%%%%%%@@%+=+++++***######%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                    \s
                                              @%%%%%%%%%%@@@@@@@@@%@@@@@@@@@%@%@@@@@@%@@@@@@@@@@@@@@@@@%%%%%%@@%*++++++****####%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                   \s
                                              @@%%%%%%%%%@@@@@@@@@@@@@@@@@@%%%%@@@@@@@%%@%@@@@@@@@@@@@%@%%%%%@@%*++++++****####%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                                  \s
                                             @@@%%%@%%%@@@@@@@@@@@@@@@@@@@@%%%%@@@@%@@%%%@@@@@@@@@@@@@@%%%%@%%@%*++++++*****###@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                               \s
                                             @%@%%%@%@%@@@@@@@@@@@@@@@@@@@@%%%%@@@@@@%@@@@@@@@@@@@@@@@@%%%%%%@@@*+++++*******#%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                             \s
                                            @@%@@%%%@@@@@@@@@@@@@@@@@@@@@@@%%@@@@@@@@@@@@@@@@@@@@@@@@@%%%%@%%%@@#+++++*******#%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                             \s
                                           @@%%%%@%%%@@@@@@@@@@@@@@@@@@@@@@%%@@@@@@@@@@@@@@@@@@@@@@@@@@%%%%@%%@@@*++++++*****#@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                            \s
                                          %%%%@@@%%%%%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@%%%%%%@@@#++++++*****%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                           \s
                                         @%@%@@@@@@@@%@@@@@@@@@@@@@@@@@@@@@@@@@%@@@@@@@@@@@@@@@@@@@@@@@@@%@%%%@@@%++++++****#@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                        \s
                                         @@@@%%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@%%%%%@@@*+++++****#@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                      \s
                                         @@@@@%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@%+++******%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                    \s
                                        @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@****+****@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                   \s
                                        @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@#*******#@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                 \s
                                       @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*******%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                \s
                                       @@@%%%%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@#******@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                \s
                                      @%%%%%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@%******@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@               \s
                                      %%%%%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@#****#@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@              \s
                                     @%%%%%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@%****%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@    @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@             \s
                                    @%@@@@@%%%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@#***%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@       @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@            \s
                                    %%@%%@@%%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@#**#@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@        @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@           \s
                                   %%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@%**#@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@          @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@          \s
                                 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@#*%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@           @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@         \s
                                @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@%*%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@           @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@        \s
                               @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@#%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@         @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@       \s
                              @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@      \s
                             @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@      \s
                             @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@      \s
                             @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@      \s
                             @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@%#*+++*#%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@      \s
                             @@@@@@@@%%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@#%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@%##%@@@@@@@@@@@@@@@@@@@@@@%+*#*+--------=+#@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@      \s
                             @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@%#%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@#+++++++************###*******+++++****=------==*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@       \s
                             @@@@@@@@@@@@@%%@@@@@@@@@@@@@@@@@@@@@@@@@@@#%%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@%*++++++++++++***+++++**+********+++++++++*****------===*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@       \s
                             @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@%%%%%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@%*+++++++++++==++++*+++++*********++**+++*++*****+***-----====#@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@        \s
                            %@@@@@@@@@@@@@@@@@@@@@@@@@@@%@@@@@@@@@@@@%%##%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@%+++*++++++++++++++++++++++++++*+++*+*+**************+**#=-====++*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@        \s
                          #%@%%@@@@@@@@@@@@@@@@@@@@@@%@@@@@@@@@@@@@@@%%%##%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*++++**+++++++++++++++++++++++++++**+**********************=++***##%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@          \s
                          %@@#%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@%###%%@@%%%@@@@@@@@@@@@@@@@@@@@@@@@#+=+++++*+++++++++++*+++++++++++*++********************+***##+#####%%%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@            \s
                         %@@#%%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@%###%%+++++**+++++#%@@@@@@@@@@@@@@@@@@@@%++++==++++++++**++***++++++++++++***********###***###****####**#####%%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                   \s
                         %@%%%%@@@@@@@@@@@@@@@@@@@@@@@@@@%#%@#++=++*+===++*%+=++**%@@%##%@@@@@@@@@@@@#+++===+++===++**++*****++++***++++++**##***#####*###%%#######**###%%%%%@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                             \s
                    """);


//            String filePath = "path/to/your/ascii_art.txt";
//
//            try {
//                String art = new String(Files.readAllBytes(Paths.get(filePath)));
//
//                System.out.println(art);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }

    public static class AddRequestCommand implements Command {
        private final Rector rector;
        private final BufferedReader reader;

        public AddRequestCommand(Rector rector, BufferedReader reader) {
            this.rector = rector;
            this.reader = reader;
        }

        @Override
        public void execute() {
            logging("AddRequest", rector);
            try {
                System.out.println("Enter request details:");

                System.out.print("Topic: ");
                String topic = reader.readLine();

                System.out.print("Signed (true/false): ");
                Boolean isSigned = Boolean.parseBoolean(reader.readLine());

                System.out.print("Status (PENDING/APPROVED/REJECTED): ");
                String statusInput = reader.readLine();
                Status status = Status.valueOf(statusInput.toUpperCase());

                Request newRequest = new Request(topic, isSigned, status);
                rector.addRequest(newRequest);

                System.out.println("Request added successfully.");
            } catch (IOException e) {
                System.out.println("An error occurred while adding the request.");
            }
        }
    }

    public static class ManageRequestCommand implements Command {
        private final Rector rector;
        private final BufferedReader reader;

        public ManageRequestCommand(Rector rector, BufferedReader reader) {
            this.rector = rector;
            this.reader = reader;
        }

        @Override
        public void execute() {
            logging("ManageRequest", rector);
            try {
                System.out.println("Enter the Request ID:");
                String requestId = reader.readLine().trim();

                Optional<Request> requestOptional = rector.getRequests().stream()
                        .filter(request -> request.getId().equals(requestId))
                        .findFirst();

                if (requestOptional.isPresent()) {
                    Request request = requestOptional.get();
                    System.out.println("Handling request: " + request.getTopic());

                    System.out.println("Choose an action:");
                    System.out.println("[1] Sign Request");
                    System.out.println("[2] Reject Request");
                    System.out.println("[0] Back to Menu");

                    String action = reader.readLine().trim();

                    switch (action) {
                        case "1" -> {
                            rector.signRequest(request);
                            System.out.println("Request signed successfully.");
                        }
                        case "2" -> {
                            rector.rejectRequest(request);
                            System.out.println("Request rejected successfully.");
                        }
                        case "0" -> {
                            System.out.println("Going back to menu.");
                            return;
                        }
                        default -> System.out.println("Invalid choice. Try again.");
                    }
                } else {
                    System.out.println("Request with the given ID not found.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred while reading input.");
            }
        }
    }


    public static class RectorViewRequestsCommand implements Command {
        private final Rector rector;

        public RectorViewRequestsCommand(Rector rector) {
            this.rector = rector;
        }

        @Override
        public void execute() {
            logging("RectorViewRequests", rector);
            System.out.println("=== List of Requests ===");
            for (Request request : rector.getRequests()) {
                System.out.println(request);
            }
        }
    }

    public static class EditRequestCommand implements Command {
        private final Rector rector;
        private final BufferedReader reader;

        public EditRequestCommand(Rector rector, BufferedReader reader) {
            this.rector = rector;
            this.reader = reader;
        }

        @Override
        public void execute() {
            logging("EditRequest", rector);
            try {
                System.out.println("Enter the Request ID to edit:");
                String requestId = reader.readLine().trim();

                Optional<Request> requestOptional = rector.getRequests().stream()
                        .filter(request -> request.getId().equals(requestId))
                        .findFirst();

                if (requestOptional.isPresent()) {
                    Request request = requestOptional.get();
                    System.out.println("Editing request: " + request.getTopic());

                    System.out.println("Choose what you want to edit:");
                    System.out.println("[1] Topic");
                    System.out.println("[2] Status");
                    System.out.println("[3] Signed");
                    System.out.println("[0] Back to Menu");

                    String choice = reader.readLine().trim();

                    switch (choice) {
                        case "1" -> {
                            System.out.print("Enter new topic: ");
                            String newTopic = reader.readLine().trim();
                            request.setTopic(newTopic);
                            System.out.println("Topic updated.");
                        }
                        case "2" -> {
                            System.out.print("Enter new status (PENDING/APPROVED/REJECTED): ");
                            String statusInput = reader.readLine().trim();
                            Status newStatus = Status.valueOf(statusInput.toUpperCase());
                            request.setStatus(newStatus);
                            System.out.println("Status updated.");
                        }
                        case "3" -> {
                            System.out.print("Enter new signed value (true/false): ");
                            boolean newSigned = Boolean.parseBoolean(reader.readLine().trim());
                            request.setSigned(newSigned);
                            System.out.println("Signed status updated.");
                        }
                        case "0" -> System.out.println("Going back to menu.");
                        default -> System.out.println("Invalid choice. Try again.");
                    }
                } else {
                    System.out.println("Request with the given ID not found.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred while reading input.");
            }
        }
    }


    /// researcher menu


    public static class AddPaperCommand implements Command {
        private final Researcher researcher;
        private final BufferedReader reader;

        public AddPaperCommand(Researcher researcher, BufferedReader reader) {
            this.researcher = researcher;
            this.reader = reader;
        }

        @Override
        public void execute() {
            try {
                System.out.print("Enter the title of the paper: ");
                String title = reader.readLine().trim();

                if (title.isEmpty()) {
                    System.out.println("The title of the paper cannot be empty.");
                    return;
                }

                System.out.print("Enter the journal name: ");
                String journal = reader.readLine().trim();

                if (journal.isEmpty()) {
                    System.out.println("The journal name cannot be empty.");
                    return;
                }

                try {
                    ResearchJournalsName journalName = ResearchJournalsName.valueOf(journal.toUpperCase());
                    ResearchPaper paper = new ResearchPaper(title, journalName);
                    researcher.getPapers().add(paper);
                    System.out.println("Paper added successfully.");
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid journal name provided. Please use a valid journal name.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred while adding the paper: " + e.getMessage());
            }

        }
    }

    public static class ViewPapersCommand implements Command {
        private final Researcher researcher;

        public ViewPapersCommand(Researcher researcher) {
            this.researcher = researcher;
        }

        @Override
        public void execute() {
            for (ResearchPaper paper : researcher.getPapers()) {
                System.out.println(paper);
            }
        }
    }

    public static class CalculateHIndexCommand implements Command {
        private final Researcher researcher;

        public CalculateHIndexCommand(Researcher researcher) {
            this.researcher = researcher;
        }

        @Override
        public void execute() {
            int hIndex = researcher.calculateHIndex();
            System.out.println(researcher.getAcademicContributor() + "'s H-Index: " + hIndex);
        }
    }

    public static class CalculateCitationsCommand implements Command {
        private final Researcher researcher;

        public CalculateCitationsCommand(Researcher researcher) {
            this.researcher = researcher;
        }

        @Override
        public void execute() {
            int totalCitations = researcher.calculateCitations();
            System.out.println("Total Citations: " + totalCitations);
        }
    }

    public static class SubscribeToJournalCommand implements Command {
        private final ResearchJournal journal;
        private final Researcher researcher;

        public SubscribeToJournalCommand(ResearchJournalsName journalName, Researcher researcher) {
            if (!isJournalInDatabase(journalName)) {
                throw new IllegalArgumentException("Journal '" + journalName + "' does not exist in the database.");
            }
            this.journal = findJournalByName(journalName);
            this.researcher = researcher;
        }

        private static boolean isJournalInDatabase(ResearchJournalsName journalName) {
            Database database = Database.getInstance();
            for (ResearchJournal dbJournal : database.getResearchJournals()) {
                if (dbJournal.getResearchJournalsName() == (journalName)) {
                    return true;
                }
            }
            return false;
        }

        private ResearchJournal findJournalByName(ResearchJournalsName journalName) {
            Database database = Database.getInstance();
            for (ResearchJournal dbJournal : database.getResearchJournals()) {
                if (dbJournal.getResearchJournalsName() == (journalName)) {
                    return dbJournal;
                }
            }
            throw new IllegalArgumentException("Journal '" + journalName + "' not found in the database.");
        }

        @Override
        public void execute() {
            journal.getSubscribers().add(researcher.getAcademicContributor());

            System.out.println(researcher.getAcademicContributor() + " is now subscribed to the journal: " + journal.getResearchJournalsName());
        }
    }


    public static class PrintPapersCommand implements Command {
        private final Researcher researcher;

        public PrintPapersCommand(Researcher researcher) {
            this.researcher = researcher;
        }

        @Override
        public void execute() {
            researcher.printPapers();
        }
    }

    public static class TopCitedResearcherCommand implements Command {
        private final List<Researcher> allResearchers;

        public TopCitedResearcherCommand(List<Researcher> allResearchers) {

            this.allResearchers = allResearchers;
        }

        @Override
        public void execute() {
            Researcher topCited = getTopCitedResearcher();
            System.out.println("Top Cited Researcher: " + topCited.getAcademicContributor() + " with " + topCited.calculateCitations() + " citations.");
        }

        private Researcher getTopCitedResearcher() {
            Researcher topCited = null;
            int maxCitations = -1;

            for (Researcher r : allResearchers) {
                int citations = r.calculateCitations();
                if (citations > maxCitations) {
                    maxCitations = citations;
                    topCited = r;
                }
            }

            return topCited;
        }
    }

    public static class PublishPaperCommand implements Command {
        private final Researcher researcher;

        public PublishPaperCommand(Researcher researcher) {
            this.researcher = researcher;
        }

        @Override
        public void execute() {
            int totalCitations = researcher.calculateCitations();
            System.out.println("Total Citations for published papers: " + totalCitations);
        }
    }


}