package Users;

import Academic.Course;
import Academic.Journal;
import Enums.Faculty;
import Enums.Semester;
import Enums.Speciality;
import Enums.StudentType;
import Enums.DocumentType;
import System.Complaint;
import System.Organization;
import System.Notification;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Vector;

// CanViewTeachers interface

public class Student extends User implements CanBeResearcher, ManageOrganization {

	private String id;
	private StudentType type;
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


	@Override
	public void beReseacrher() {

	}

	@Override
	public void update() {

	}

	@Override
	public void deleteOrganization(Organization org) {

	}

	@Override
	public void joinOrganization(Organization org) {

	}

	@Override
	public void createOrganization(String name) {

	}

	@Override
	public void leaveOrganization(Organization org) {

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

