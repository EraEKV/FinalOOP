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
	public User createUser(String firstname, String lastname) {
		String id = generateId(Manager.class);
		return new Manager(id, firstname, lastname);
	}

	//	new Student (Bachelor)
	public User createUser(String firstname, String lastname, Faculty faculty, Speciality speciality) {
		String id = generateId(Student.class);
		return new Student(id, firstname, lastname, faculty, speciality);
	}

	//	new GradStudent
//	public User createUser(String firstname, String lastname, Faculty faculty, Speciality speciality) {
//		String id;
//		if(userType.equals(UserType.MAS)) {
//			id = generateId(MasterStudent.class);
//			return new MasterStudent(id, firstname, lastname, faculty, speciality);
//		} else if(userType.equals(UserType.PHD)) {
//			id = generateId(PhDStudent.class);
//			return new PhDStudent(id, firstname, lastname, faculty, speciality);
//		}
//	}

	// new Teacher
	public User createUser(String firstname, String lastname, TeacherType teacherType, Faculty faculty) {
		String id = generateId(Teacher.class);
		return new Teacher(id, firstname, lastname, teacherType, faculty);
	}


	public Researcher createUser() {
		return new Researcher();
	}
//
//	public Researcher createUser(Student s) {
//		// TODO implement me
//		return null;
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
			idSuffix = "O";
		} else {
			throw new UserTypeException();
		}


		idSuffix += String.format("%05d", db.getUsersCount(userClass));

		return String.valueOf(Year.now().getValue()).substring(2) + idSuffix;
	}


}

