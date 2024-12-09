package Users ;

import CustomExceptions.UserTypeException;
import Enums.*;
import Database.Database;

import java.time.Year;

public class UserFactory {


	public UserFactory() {

	}



	//	new Manager
//	public User createUser(String firstname, String lastname, UserType userType, ManagerType managerType) {
//		String id = generateId(userType, managerType);
//		return new Manager(id, firstname, lastname, managerType);
//	}

	//	new Student (Bachelor)
	public User createUser(String firstname, String lastname, UserType userType, Faculty faculty, Speciality speciality) {
		String id = generateId(userType, StudentType.BACHELOR);
		return new Student(id, firstname, lastname, faculty, speciality);
	}

	//	new GradStudent
//	public User createUser(String firstname, String lastname, UserType userType, Faculty faculty, Speciality speciality, StudentType studType) {
//		String id = generateId(userType, studType);
//		if(studType.equals(StudentType.MASTER)) {
//			return new MasterStudent(id, firstname, lastname, faculty, speciality);
//		} else {
//			return new PhDStudent(id, firstname, lastname, faculty, speciality);
//		}
//	}

	// new Teacher
	public User createUser(String firstname, String lastname, UserType userType, TeacherType teacherType, Faculty faculty) {
		String id = generateId(userType, teacherType);
		return new Teacher(id, firstname, lastname, teacherType, faculty);
	}


//	public Researcher createUser(Teacher t) {
//		// TODO implement me
//		return null;
//	}
//
//	public Researcher createUser(Student s) {
//		// TODO implement me
//		return null;
//	}



	// generating ID for new User

	public static String formatCountID(int number) {
		int length = Math.max(5, String.valueOf(number).length());
		return String.format("%0" + length + "d", number);
	}

	private <T extends Enum<T>> String generateId(UserType userType, T specificType) {
		String idSuffix;
		String id;

		Database db = Database.getInstance();

		switch (userType) {
			case STU:
				if (specificType instanceof StudentType) {
					switch ((StudentType) specificType) {
						case PHD:
							idSuffix = "P";
							break;
						case MASTER:
							idSuffix = "M";
							break;
						case BACHELOR:
							idSuffix = "B";
							break;
						default:
							throw new UserTypeException("Unknown student type: " + specificType);
					}
					idSuffix += formatCountID(db.getStudentsCount((StudentType) specificType));
				} else {
					throw new UserTypeException("Invalid specific type for student: " + specificType);
				}
				break;

			case TCH:
				if (specificType instanceof TeacherType) {
					idSuffix = "T" + formatCountID(db.getUsersCount(userType));
				} else {
					throw new UserTypeException("Invalid specific type for teacher: " + specificType);
				}
				break;

			case MNG:
				if (specificType instanceof ManagerType) {
					idSuffix = "M" + formatCountID(db.getUsersCount(userType));
				} else {
					throw new UserTypeException("Invalid specific type for manager: " + specificType);
				}
				break;

			default:
				throw new UserTypeException("Unknown user type: " + userType);
		}


		String yearPart = String.valueOf(Year.now().getValue()).substring(2);

		id = yearPart + idSuffix;

		return id;
	}

}

