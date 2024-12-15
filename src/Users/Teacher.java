package Users ;


import Academic.Course;
import Enums.Faculty;
import Enums.TeacherType;
import Research.CanBeResearcher;
import Research.Researcher;

import java.util.Date;
import java.util.Vector;

// CanViewStudents interface
public class Teacher extends Employee implements CanViewStudents, CanBeResearcher {

	private String id;
	private String subjectName;
	private TeacherType teacherType;
	private Researcher isResearcher;
	private Faculty faculty;
	private Vector<Integer> ratings;
	private Vector<Course> currentCourses;



//	Constructors
	public Teacher() {	}

	public Teacher(String id) {
		this.id = id;
	}

	public Teacher(String id, String firstname, String lastname, TeacherType teacherType, Faculty faculty) {
		super(firstname, lastname);
		this.id = id;
		this.teacherType = teacherType;
		this.faculty = faculty;
	}

	public Vector<Integer> getRatings() {
		if (ratings == null) {
			ratings = new Vector<>(); // Инициализация, если поле не было установлено
		}
		return ratings;
	}


	public void putMarks(Course course, Student student, Date date, double grade) {

	}
//
//	public void putMarks(Course course, Student student, MarkType parameter3, double grade) {
//		// TODO implement me
//		return null;
//	}
	
//	public void sendComplaint(Complaint parameter) {
//		// TODO implement me
//		return null;
//	}
	
//	public void markAttendance(JournalCourse parameter, Vector<Student> parameter2, Attendance parameter3) {
//		// TODO implement me
//		return null;
//	}


//	accessors


	public Faculty getFaculty() {
		return faculty;
	}

	public void setTeacherType(TeacherType teacherType) {
		this.teacherType = teacherType;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	@Override
	public Researcher getIsResearcher() {
		return isResearcher;
	}

	public void setIsResearcher(Researcher isResearcher) {
		this.isResearcher = isResearcher;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public void setRatings(Vector<Integer> ratings) {
		this.ratings = ratings;
	}

	public TeacherType getTeacherType() {
		return teacherType;
	}

	public String getId() {
		return id;
	}

	public void setCurrentCourses(Vector<Course> currentCourses) {
		this.currentCourses = currentCourses;
	}

	public Vector<Course> getCurrentCourses() {
		return currentCourses;
	}

	public double getRating() {
		// TODO implement me
		return 0.0;
	}

	@Override
	public void beReseacrher() {
		if(!teacherType.equals(TeacherType.PROFESSOR)) {
			isResearcher = new Researcher();
		}
	}

	@Override
	public Vector<Student> viewStudentsInfo(Course course) {

		return new Vector<Student>();
	}

	@Override
	public boolean equals(Object o) {
		if(!super.equals(o)) return false;
		Teacher t = (Teacher) o;
		return id.equals(t.id)
				&& teacherType.equals(t.teacherType)
				&& faculty.equals(t.faculty)
				&& isResearcher.equals(t.isResearcher);
	}

	@Override
	public int hashCode() {
		int res = super.hashCode();

		res = res * 31 + (id != null ? id.hashCode() : 0);
		res = res * 31 + (teacherType != null ? teacherType.hashCode() : 0);
		res = res * 31 + (isResearcher != null ? isResearcher.hashCode() : 0);
		res = res * 31 + (faculty != null ? faculty.hashCode() : 0);
		res = res * 31 + (ratings != null ? ratings.hashCode() : 0);

		return res;
	}

	@Override
	public String toString() {
		return ", id='" + id + '\'' +
				", teacher=" + teacherType +
				", faculty=" + faculty +
				", isResearcher=" + isResearcher;
	}
}

