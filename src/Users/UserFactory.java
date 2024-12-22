package Users ;

import CustomExceptions.UserTypeException;
import Enums.*;
import Database.Database;
import Research.Researcher;

import java.time.Year;

/**
 * The UserFactory class is responsible for creating various types of users such as
 * Manager, Student, MasterStudent, {PhDStudent, Teacher.
 * It provides methods for creating new users by generating unique IDs for each type of user
 * and initializing them with the required attributes. This class follows the Singleton design pattern
 * to ensure only one instance of the factory exists.
 */
public class UserFactory {
	private static UserFactory factory;

	private UserFactory() {

	}

	/**
	 * Returns the singleton instance of the {@link UserFactory}.
	 * If the instance is not created yet, it will be created at the time of the first access.
	 *
	 * @return The singleton instance of {@link UserFactory}.
	 */

	public static synchronized UserFactory getInstance() {
		if (factory == null) {
			factory = new UserFactory();
		}
		return factory;
	}


	//	new Manager

	/**
	 * Creates a new {@link Manager} with the provided first and last names.
	 * A unique ID is generated for the new manager.
	 *
	 * @param firstname The first name of the manager.
	 * @param lastname The last name of the manager.
	 * @return A new {@link Manager} instance.
	 */
	public Manager createUser(String firstname, String lastname) {
		String id = generateId(Manager.class);
		return new Manager(id, firstname, lastname);
	}

	//	new Student (Bachelor)
	/**
	 * Creates a new graduate student, either a {@link MasterStudent} or a {@link PhDStudent}, based on the
	 * specified user type. A unique ID is generated for the student, and the student will be associated with
	 * a corresponding {@link Teacher}.
	 *
	 * @param firstname The first name of the graduate student.
	 * @param lastname The last name of the graduate student.
	 * @param faculty The faculty to which the graduate student belongs.
	 * @param speciality The speciality of the graduate student.
	 * @return A new graduate student of type {@link MasterStudent} or {@link PhDStudent}, or null if the
	 *         userType is invalid.
	 */
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
	/**
	 * Creates a new {@link Teacher} with the provided first and last names, teacher type, and faculty.
	 * A unique ID is generated for the new teacher.
	 *
	 * @param firstname The first name of the teacher.
	 * @param lastname The last name of the teacher.
	 * @param teacherType The type of teacher (e.g., TUTOR, LECTOR).
	 * @param faculty The faculty to which the teacher belongs.
	 * @return A new {@link Teacher} instance.
	 */
	public Teacher createUser(String firstname, String lastname, TeacherType teacherType, Faculty faculty) {
		String id = generateId(Teacher.class);
		return new Teacher(id, firstname, lastname, teacherType, faculty);
	}


//	public Researcher createUser(User user) {
//		return new Researcher(user.getFirstname() + user.getLastname());
//	}

//	public Researcher createUser(String pseudoname, Faculty faculty) {
//		return new Researcher(pseudoname, faculty);
//	}


	// generating ID for new User
	/**
	 * Generates a unique ID for a new user based on the user class type.
	 * The ID consists of a two-digit year suffix (representing the current year), followed by
	 * a user-specific suffix (e.g., "B" for students, "MNG" for managers), and a unique numerical sequence.
	 *
	 * @param userClass The class of the user (e.g., {@link Student}, {@link Teacher}).
	 * @return A unique ID for the user.
	 * @throws UserTypeException if the user class is not supported.
	 */
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

