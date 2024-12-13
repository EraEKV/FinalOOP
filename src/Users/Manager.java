package Users;

import Academic.Course;
import Academic.JournalLesson;
import Database.Database;
import System.Request;

import java.util.HashMap;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Vector;
import System.News;

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

	public Manager(String firstname, String lastname, String email) {
		super(firstname, lastname, email);
	}

	public Manager(String firstname, String lastname, String email, String id) {
		super(firstname, lastname, email);
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



	public void addCourse(Course c) {
		if (c != null) {
			System.out.println("Course " + c.getName() + " has been added successfully.");
			Vector<Course> courses = Database.getInstance().getCourses();
			courses.add(c);
			Database.getInstance().setCourses(courses);
		} else {
			System.out.println("Invalid course. Cannot add.");
		}
	}

	public void viewRequests() {
		if (requests.isEmpty()) {
			System.out.println("No requests available.");
		} else {
			for (Request r : requests) {
				System.out.println(r);
			}
		}
	}

	public String getReport(Course c) {
		if (c == null) {
			return "No course provided.";
		}
		return "Report for course: " + c;
	}

	public String getReport() {
//		HashMap<Student, Vector<JournalLesson>> journalData = Database.getInstance().getUsers();
		return "Report : " ;
	}

	public void addNews(News n) {
		if (n != null) {
			PriorityQueue<News> news = Database.getInstance().getNews();
			news.add(n);
			Database.getInstance().setNews(news);
			System.out.println("News added: " + n);
		} else {
			System.out.println("Invalid news item.");
		}
	}

	public void setRegistration() {
		Database db = Database.getInstance();
		db.setRegistrationOpened(!db.getRegistrationOpened());
	}

	public void setRegistration(Student s) {
		if (s != null) {
			System.out.println("Student " + s + " has been registered.");
		} else {
			System.out.println("Invalid student.");
		}
	}
	public void redirectRequest(Rector rec, Request req) {
		if (rec != null && req != null) {
			rec.addRequest(req);
			System.out.println("Request redirected to Rector: " + rec.getFirstname() + " " + rec.getLastname());
		} else {
			System.out.println("Invalid Rector or Request.");
		}
	}

	public void approveStudentRegistration() {
	}

	public void assignCourseTeachers(Course c, Vector<Teacher> teachers) {
		System.out.println("Teachers have been assigned to courses.");
	}
	//must be stored in the system and checked by student
	public void openRegistration(boolean p) {
		if (p) {
			System.out.println("Registration is now open.");
		} else {
			System.out.println("Registration is now closed.");
		}
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
