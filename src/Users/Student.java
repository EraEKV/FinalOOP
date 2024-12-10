package Users;

import Academic.Course;
import Academic.Journal;
import Enums.Faculty;
import Enums.Speciality;
import Research.CanBeResearcher;
import Research.Researcher;
import System.Complaint;
import System.Organization;
import System.Notification;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Vector;

// CanViewTeachers interface

public class Student extends User implements CanBeResearcher, ManageOrganization {

	private String id;
	private Faculty faculty;
	private int startYear;
	private Speciality speciality;
	private Employee advisor;
	private Vector<Complaint> warnings;
	private Researcher isResearcher;
	private Vector<Journal> journal;
	private Researcher researchSupervisor;

	private HashMap<Course, Integer> countRetakes;

	private Vector<Notification> notifications;
	
	public Student() {
		super();
	}

	@Override
	public <T> T getUserType() {
			return (T) new Student();
	}

	public Student(String id) {
		this.id = id;
	}

	public Student(String id, String firstname, String lastname, Faculty faculty, Speciality speciality) {
		super(firstname, lastname);
		this.startYear = LocalDate.now().getYear();
		this.id = id;
		this.faculty = faculty;
		this.speciality = speciality;
	}
	
	public void viewMarks() {
		// TODO implement me
		return;
	}
	
	
	public void viewTranscript() {
		// TODO implement me
		return;
	}
	
	
	public void rateTeacher(Teacher t) {
		// TODO implement me
		return;
	}

	
	
	public String viewTeacherInfo(Teacher t) {
		// TODO implement me
		return "";	
	}
	
	
//	public void viewAbsenses() {
//		// TODO implement me
//		return;
//	}
	
	
//	public void requestDocument(DocumentType parameter) {
//		// TODO implement me
//		return;
//	}
	
	
//	public void coursesRegistration(Course parameter, Teacher parameter2, Semester parameter3, pair<Integer, Integer> parameter4) {
//		// TODO implement me
//		return;
//	}




//	interface realization

	@Override
	public void beReseacrher() {

	}

	@Override
	public void update() {

	}


	@Override
	public void createOrganization(String name) {
		if (name == null || name.isEmpty()) {
			System.out.println("Organization name cannot be empty.");
			return;
		}
		Organization org = new Organization(name);
		org.addMember(this); // Add this student as the first member.
		System.out.println("Organization '" + name + "' created successfully.");
	}

	@Override
	public void deleteOrganization(Organization org) {
		if (org == null) {
			System.out.println("Organization cannot be null.");
			return;
		}
		if (org.getMembers().contains(this)) {
			org.getMembers().clear(); // Remove all members.
			System.out.println("The organization '" + org.getName() + "' has been deleted successfully.");
		} else {
			System.out.println("You do not have the rights to delete the organization: " + org.getName());
		}
	}

	@Override
	public void joinOrganization(Organization org) {
		if (org == null) {
			System.out.println("Organization cannot be null.");
			return;
		}
		if (org.addMember(this)) {
			System.out.println("You have successfully joined the organization: " + org.getName());
		} else {
			System.out.println("You are already a member of the organization: " + org.getName());
		}
	}

	@Override
	public void leaveOrganization(Organization org) {
		if (org == null) {
			System.out.println("Organization cannot be null.");
			return;
		}
		if (org.removeMember(this)) {
			System.out.println("You have successfully left the organization: " + org.getName());
		} else {
			System.out.println("You are not a member of the organization: " + org.getName());
		}
	}





	@Override
	public boolean equals(Object o) {
		if(!super.equals(o)) return false;
		Student s = (Student) o;
		return id.equals(s.id)
				&& faculty.equals(s.faculty)
				&& speciality.equals(s.speciality)
				&& startYear == s.startYear;
	}

	@Override
	public int hashCode() {
		int res = super.hashCode();
		res = 31 * res + (id != null ? id.hashCode() : 0);
		res = 31 * res + (faculty != null ? faculty.hashCode() : 0);
		res = 31 * res + (speciality != null ? speciality.hashCode() : 0);
		res = 31 * res + (startYear);

		return res;
	}

	@Override
	public String toString() {
		return super.toString()
				+ ", id=" + id
				+ ", faculty=" + faculty
				+ ", speciality=" + speciality
				+ ", startYear=" + startYear;
	}
}

