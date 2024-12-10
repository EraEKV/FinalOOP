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
		String id = generateId(userType, new Student());
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

	private <T extends Enum<T>> String generateId(User user) {
		String idSuffix;
		String id;

		Database db = Database.getInstance();

		switch (user) {
			case instanceof Student:
				if (specificType instanceof Student) {
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
							throw new UserTypeException(specificType);
					}
					idSuffix += formatCountID(db.getStudentsCount((StudentType) specificType));
				} else {
					throw new UserTypeException(specificType);
				}
				break;

			case TCH:
				if (specificType instanceof TeacherType) {
					idSuffix = "T" + formatCountID(db.getUsersCount(userType));
				} else {
					throw new UserTypeException(specificType);
				}
				break;

			case MNG:
				if (specificType instanceof ManagerType) {
					idSuffix = "M" + formatCountID(db.getUsersCount(userType));
				} else {
					throw new UserTypeException(specificType);
				}
				break;

			default:
				throw new UserTypeException(userType);
		}


		String yearPart = String.valueOf(Year.now().getValue()).substring(2);

		id = yearPart + idSuffix;

		return id;
	}

}

