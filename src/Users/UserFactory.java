package Users ;

import CustomExceptions.UserTypeException;
import Enums.*;
import Database.Database;
import Research.Researcher;

import java.time.Year;

public class UserFactory {
	private static UserFactory factory;

	private UserFactory() {

	}

	public static synchronized UserFactory getInstance() {
		if (factory == null) {
			factory = new UserFactory();
		}
		return factory;
	}


	//	new Manager
	public Manager createUser(String firstname, String lastname) {
		String id = generateId(Manager.class);
		return new Manager(id, firstname, lastname);
	}

	//	new Student (Bachelor)
	public Student createUser(String firstname, String lastname, Faculty faculty, Speciality speciality) {
		String id = generateId(Student.class);
		return new Student(id, firstname, lastname, faculty, speciality);
	}

	//	new GradStudent
	public User createUser(String firstname, String lastname, Faculty faculty, Speciality speciality, String userType) {
		String id;
		if(userType.equals("MasterStudent")) {
			id = generateId(MasterStudent.class);
			Teacher newTeacher = createUser(firstname, lastname, TeacherType.TUTOR, faculty);
			return new MasterStudent(id, firstname, lastname, faculty, speciality, newTeacher);
		} else if (userType.equals("PhDStudent")) {
			id = generateId(PhDStudent.class);
			Teacher newTeacher = createUser(firstname, lastname, TeacherType.LECTOR, faculty);
			return new PhDStudent(id, firstname, lastname, faculty, speciality, newTeacher);
		} else return null;
	}

	// new Teacher
	public Teacher createUser(String firstname, String lastname, TeacherType teacherType, Faculty faculty) {
		String id = generateId(Teacher.class);
		return new Teacher(id, firstname, lastname, teacherType, faculty);
	}


//	public Researcher createUser(User user) {
//		return new Researcher(user.getFirstname() + user.getLastname());
//	}
//
//	public Researcher createUser(String pseudoname) {
//		return new Researcher(pseudoname);
//	}


	// generating ID for new User
	private String generateId(Class<? extends User> userClass) throws UserTypeException {
		String idSuffix;

		Database db = Database.getInstance();

		if (Student.class.isAssignableFrom(userClass)) {
			idSuffix = "B";
		} else if (PhDStudent.class.isAssignableFrom(userClass)) {
			idSuffix = "P";
		} else if (MasterStudent.class.isAssignableFrom(userClass)) {
			idSuffix = "M";
		} else if (Teacher.class.isAssignableFrom(userClass)) {
			idSuffix = "T";
		} else if (Manager.class.isAssignableFrom(userClass)) {
			idSuffix = "MNG";
		} else {
			throw new UserTypeException();
		}

		int count = db.getUsersCount(userClass);
		idSuffix += String.format("%05d", ++count);

		return String.valueOf(Year.now().getValue()).substring(2) + idSuffix;
	}


}

