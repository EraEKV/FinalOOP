package Users;

import Academic.Course;
import Academic.Journal;
import Academic.JournalLesson;
import Database.Database;
import System.Request;

import java.util.*;

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

	public Manager(String id, String firstname, String lastname, String email) {
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
			Vector<Course> courses = Database.getInstance().getCourses();
			if (courses == null) {
				courses = new Vector<>();
			}

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
		double total = 0;
		int count = 0;
		Vector<Journal> journalData = Database.getInstance().getJournals();
		if(journalData == null) {
			return "No journal data available.";
		}
		for(Journal j : journalData) {
			Collection<Vector<JournalLesson>> JournalLessons = j.getJournalData().values();
			for(Vector<JournalLesson> jl : JournalLessons) {
				for(JournalLesson jls : jl) {
					total += jls.getMark();
					count++;
				}
			}
		}
		double average = total/count;
		return "Report : "  + average;
	}

//	public void addNews(News n) {
//		if (n != null) {
//			PriorityQueue<News> news = Database.getInstance().getNews();
//			if(news == null) {
//				news = new PriorityQueue<>();
//			}
//			news.add(n);
//			Database.getInstance().setNews(news);
//			System.out.println("News added: " + n);
//		} else {
//			System.out.println("Invalid news item.");
//		}
//	}

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

	public void openRegistration(Boolean b) {
		Database.getInstance().setRegistrationOpened(b);
	}

//	public void approveStudentRegistration() {
//	}

//	public void assignCourseTeachers(Course c, Vector<Teacher> teachers) {
//		System.out.println("Teachers have been assigned to courses.");
//	}


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
