package Database ;


import Academic.Journal;
import Academic.SemesterPeriod;
import Academic.Transcript;
import CustomExceptions.UserTypeException;
import Enums.*;
import Enums.News;
import Research.ResearchJournal;
import Research.ResearchPaper;
import Research.ResearchProject;
import Users.Rector;
import Users.Student;
import Users.Teacher;
import Users.User;
import System.Organization;
import Academic.Course;
import Users.Researcher;
import System.Organization;
import System.Credentials;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Vector;

public class Database implements Serializable {
	private static Database DATABASE;
	
	private boolean isRegistrationOpened;
	
	private HashMap<Credentials, User> users;
	
	private Vector<Course> courses;
	
	private Rector rector;
	
	private Vector<Teacher> teachers;
	
	private Vector<ResearchJournal> researchJournals;
	
	private Vector<ResearchProject> researchProjects;
	
	private Vector<ResearchPaper> researchPapers;
	
	private Vector<Researcher> researchers;
	
	private Vector<Student> students;
	
	private Vector<String> logs;
	
	private PriorityQueue<News> news;

	private Vector<Journal> journals;

	private Years years;
	
	private Semester semester;
	
	public boolean regIsOpen;
	
	private SemesterPeriod semesterPeriod;
	
	private Vector<User> newsSubscribers;
	
	private Vector<Organization> organizations;
	
	private Vector<Transcript> transcripts;

	private int usersCount;

	private int bachelorStudentsCount;
	private int masterStudentsCount;
	private int phdStudentsCount;
	private int teachersCount;
	private int managersCount;


	private Database() {

	}
	
	public Database Read() {
		// TODO implement me
		return DATABASE;
	}
	
	public void Write() {
		// TODO implement me
		return;
	}

	public static Database getInstance() {
		if (DATABASE == null) {
			DATABASE = new Database();
		}
		return DATABASE;
	}

	public int getUsersCount(UserType userType) {
		int count = 0;
		switch (userType) {
			case MNG:
				count = ++managersCount;
				break;
			case TCH:
				count = ++teachersCount;
				break;
			default:
				throw new UserTypeException("Unknown user type: " + userType); // Обрабатываем неизвестный тип
		}
		return count;
	}

	public <T extends Enum<T>> int getStudentsCount(T studentType) {
		int count = 0;

		if (studentType instanceof StudentType) {
			switch ((StudentType) studentType) {
				case PHD:
					count = ++phdStudentsCount;
					break;
				case MASTER:
					count = ++masterStudentsCount;
					break;
				case BACHELOR:
					count = ++bachelorStudentsCount;
					break;
				default:
					throw new UserTypeException("Unknown student type: " + studentType);
			}
		}
		return count;
	}

	public void newUserAdded(User user) {
//	logic of checking
	}
}

