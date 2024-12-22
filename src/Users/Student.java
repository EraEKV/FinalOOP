package Users;

import Academic.*;
import CustomExceptions.NotAResearcherException;
import Database.Database;
import Enums.Faculty;
import Enums.Speciality;
import Research.CanBeResearcher;
import Research.Researcher;
import System.Complaint;
import System.Organization;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


public class Student extends User implements ManageOrganization, CanViewTeachers, CanBeResearcher {

	private String id;
	private Faculty faculty;
	private int startYear;
	private Speciality speciality;
	private Vector<Complaint> warnings;
	private Researcher researchSupervisor;
	private Vector<Journal> journals;
	private HashMap<Course, Teacher> registeredCourses;
	private Researcher isResearcher;

	private Transcript transcript;
	private Attestation attestation;


	private HashMap<Course, Integer> countRetakes;

	private boolean registered;

	Vector<Organization> joinedOrganizations;
	Organization isHead = null;

//	constructors
	public Student() {

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

		this.warnings = new Vector<Complaint>();
		this.registeredCourses = new HashMap<Course, Teacher>();
		this.countRetakes = new HashMap<Course, Integer>();

		this.transcript = new Transcript(this);
		this.attestation = new Attestation(this);
		this.joinedOrganizations = new Vector<Organization>();
	}



	//	accessors

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	@Override
	public User getUser() {
		return this;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public Organization getIsHead() {
		return isHead;
	}

	public void setIsHead(Organization isHead) {
		this.isHead = isHead;
	}

	public Vector<Journal> getJournals() {
		return journals;
	}

//	public Journal getJournal(Course course) {
//		return journals.stream()
//				.filter(journal -> journal.getCourse().equals(course))
//				.findFirst()
//				.orElse(null);
//	}


	public Vector<Organization> getJoinedOrganizations() {
		return joinedOrganizations;
	}

	public Speciality getSpeciality() {
		return speciality;
	}

	public void setSpeciality(Speciality speciality) {
		this.speciality = speciality;
	}

	public boolean isRegistered() {
		return registered;
	}

	public void setRegistered(boolean registered) {
		this.registered = registered;
	}

	public int getStartYear() {
		return startYear;
	}

	public Vector<Complaint> getWarnings() {
		return warnings;
	}

	public Researcher getResearchSupervisor() {
		return researchSupervisor;
	}

	public void setResearchSupervisor(Researcher researchSupervisor) {
		this.researchSupervisor = researchSupervisor;
	}

	public Attestation getAttestation() {
		return attestation;
	}

	public Transcript getTranscript() {
		return transcript;
	}

	public HashMap<Course, Integer> getCountRetakes() {
		return countRetakes;
	}

	public HashMap<Course, Teacher> getRegisteredCourses() {
		return registeredCourses;
	}


	public Vector<Teacher> getTeachers() {
		return new Vector<>(registeredCourses.values());
	}


	public void viewMarks() {
		for (Map.Entry<Course, AttestationMark> entry : attestation.getInfo().entrySet()) {
			Course course = entry.getKey();
			AttestationMark mark = entry.getValue();
			System.out.println("Course: " + course.getName() + ", Mark: " + mark);
		}
	}

	
	public void rateTeacher(Teacher t, Integer rate) {
		// TODO implement me
		Vector<Integer> curRating = t.getRatings();
		curRating.add(rate);
		t.setRatings(curRating);
	}

	
	
	public void viewTeacherInfo(Teacher t) {
		// TODO implement me
		System.out.println(t);
	}
	
	
//	public void viewAbsenses() {
//		// TODO implement me
//		for(Journal journal : journals){
//			Vector<JournalLesson > jl = journal.getJournalData().get(this);
//			for(JournalLesson j : jl){
//				System.out.println(j.getAttendance());
//			}
//		}
//	}


//	public void requestDocument(DocumentType parameter) {
//		// TODO implement me
//		return;
//	}


	//researcher realization
	@Override
	public void beResearcher() {
		this.isResearcher = new Researcher(this);;
	}

	public Researcher getIsResearcher() throws NotAResearcherException {
		if (isResearcher == null) {
			throw new NotAResearcherException("Access denied: User is not a researcher.");
		}
		return isResearcher;
	}




//	interface realization
	@Override
	public void update() {

	}

	@Override
	public Vector<Teacher> viewCourseTeachersInfo(Course course) {
		return course.getTeachers();
	}

	@Override
	public void createOrganization(String name) {
		if (name == null || name.isEmpty()) {
			System.out.println("Organization name cannot be empty.");
			return;
		}
		Organization org = new Organization(name, this);
		this.setIsHead(org);
		org.addMember(this);
		Database.getInstance().getStudentOrganizations().add(org);
		System.out.println("Organization '" + name + "' created successfully.");
	}

	@Override
	public void joinOrganization(Organization org) {
		if (org == null) {
			System.out.println("Organization cannot be null.");
			return;
		}
		if (!this.joinedOrganizations.contains(org)) {
			org.getMembers().add(this);
			this.joinedOrganizations.add(org);
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
		if (!this.joinedOrganizations.contains(org)) {
			this.joinedOrganizations.remove(org);
			org.getMembers().remove(this);
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

