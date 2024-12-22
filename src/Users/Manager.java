package Users;

import Academic.Course;
import System.News;
import Database.Database;
import System.Request;
import java.util.*;

/**
 * The Manager class represents a university system manager who handles tasks like adding courses, viewing requests, generating reports,
 * adding news, setting student registrations, and redirecting requests to a rector.
 * The class extends the Employee class, inheriting basic employee details such as first name, last name, and email.
 */
public class Manager extends Employee {

	private String id;
	private Vector<Request> requests;

	{
		requests = new Vector<>();
	}

	public Manager() {
	}

	public Manager(String firstname, String lastname) {
		super(firstname, lastname);
	}

	public Manager(String id, String firstname, String lastname) {
		super(firstname, lastname);
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public Vector<Request> getRequests() {
		return requests;
	}

	public void setRequests(Vector<Request> requests) {
		this.requests = requests;
	}

	/**
	 * Adds a new course to the university system database.
	 * Checks for duplicate courses using the course code.
	 * @param c The course to be added.
	 */
	public void addCourse(Course c) {
		if (c != null) {
			Vector<Course> courses = Database.getInstance().getCourses();
			if (courses == null) {
				courses = new Vector<>();
			}

			// Check if the course with the same code already exists
			if (courses.stream().anyMatch(course -> course.getCode().equals(c.getCode()))) {
				System.out.println("Course with this code already exists in the database.");
				return;
			}

			courses.add(c);
			Database.getInstance().setCourses(courses);
			System.out.println("Course " + c.getName() + " has been added successfully.");
		} else {
			System.out.println("Invalid course. Cannot add.");
		}
	}

	/**
	 * Displays all the requests the manager is handling.
	 * If no requests are available, a message is displayed.
	 */
	public void viewRequests() {
		if (requests.isEmpty()) {
			System.out.println("No requests available.");
		} else {
			for (Request r : requests) {
				System.out.println(r);
			}
		}
	}

	/**
	 * Generates a report for a specific course.
	 * @param c The course to generate the report for.
	 * @return A string containing the report for the course.
	 */
	public String getReport(Course c) {
		if (c == null) {
			return "No course provided.";
		}
		return "Report for course: " + c;
	}

	/**
	 * Generates a statistical report from the manager's perspective.
	 * The report includes the manager's name, email, and the average grade of all students.
	 * @return A string containing the manager's report.
	 */
	public String getReport() {
		StringBuilder report = new StringBuilder();

		report.append("===== Manager Report =====\n");
		report.append("Name: ").append(this.getFirstname()).append("\n");
		report.append("Email: ").append(this.getEmail()).append("\n");

		Vector<Student> students = Database.getInstance().getStudents();
		int avg = 0;
		for (Student s : students) {
			avg += s.getTranscript().getAvgGrades();
		}

		report.append("Average grade is : ").append(avg / students.size()).append("\n");

		report.append("==========================\n");
		return report.toString();
	}

	/**
	 * Adds a news item to the university's news system.
	 * @param n The news item to be added.
	 */
	public void addNews(News n) {
		if (n != null) {
			PriorityQueue<News> news = Database.getInstance().getNews();
			if (news == null) {
				news = new PriorityQueue<>();
				news.add(n);
			} else {
				Database.getInstance().getNews().add(n);
			}
			System.out.println("News added: " + n);
		} else {
			System.out.println("Invalid news item.");
		}
	}

	/**
	 * Registers a student in the university system.
	 * @param s The student to be registered.
	 */
	public void setRegistration(Student s) {
		if (s != null) {
			System.out.println("Student " + s + " has been registered.");
		} else {
			System.out.println("Invalid student.");
		}
	}

	/**
	 * Redirects a request to the rector for further action.
	 * @param rec The rector to whom the request is being redirected.
	 * @param req The request to be redirected.
	 */
	public void redirectRequest(Rector rec, Request req) {
		if (rec != null && req != null) {
			rec.addRequest(req);
			System.out.println("Request redirected to Rector: " + rec.getFirstname() + " " + rec.getLastname());
		} else {
			System.out.println("Invalid Rector or Request.");
		}
	}

	/**
	 * Opens or closes the registration process for students.
	 * @param b True to open registration, false to close it.
	 */
	public void openRegistration(Boolean b) {
		Database.getInstance().setRegistrationOpened(b);
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Manager manager = (Manager) o;
		return Objects.equals(id, manager.id) && Objects.equals(requests, manager.requests);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), id, requests);
	}

	@Override
	public String toString() {
		return "Manager{" +
				"id='" + id + '\'' +
				", requests=" + requests +
				'}';
	}
}
