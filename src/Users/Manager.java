package Users ;

import Academic.Course;
import Enums.News;
import System.Request;
import Enums.ManagerType;
import java.util.Objects;
import java.util.Vector;

public class Manager extends Employee
{
	private String id;
	private Vector<Request> requests;
	private ManagerType managerType;
	{
		requests = new Vector<Request>();
	}
	public Manager(){
	}
	public Manager(String id) {
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

	public ManagerType getManagerType() {
		return managerType;
	}

	public void setManagerType(ManagerType managerType) {
		this.managerType = managerType;
	}

	public Manager(String id, ManagerType managerType) {
		this.id = id;
		this.managerType = managerType;
	}
	public void addCourse(Course c) {}
	public void viewRequests() {
		for(Request r : requests) {
			System.out.println(r.toString());
		}
	}
	public String getReport(Course c) {
		// TODO implement me
		return "";	
	}
	public String getReport(Student parameter) {
		// TODO implement me
		return "";	
	}
	public void addNews(News n) {
	}
	public void setRegistration(Student s) {
	}
	public void redirectRequest(Rector rec, Request req) {
	}
	public void approveStudentRegistration() {
	}
	public void assignCourseTeachers() {
	}
	public void openRegistration(boolean p) {
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Manager manager = (Manager) o;
		return Objects.equals(id, manager.id) && Objects.equals(requests, manager.requests) && managerType == manager.managerType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), id, requests, managerType);
	}

	@Override
	public String toString() {
		return "Manager{" +
				"id='" + id + '\'' +
				", requests=" + requests +
				", managerType=" + managerType +
				'}';
	}
}

