package Database ;


import Academic.Journal;
import Academic.SemesterPeriod;
import Academic.Transcript;
import CustomExceptions.UserTypeException;
import Enums.*;
import Research.ResearchJournal;
import Research.ResearchPaper;
import Research.ResearchProject;
import Users.*;
import System.Organization;
import Academic.Course;
import System.Credentials;

import java.io.*;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Vector;
import java.util.stream.Collectors;

public class Database {
	private static Database DATABASE;
	
	private boolean isRegistrationOpened;
	
	private HashMap<Credentials, User> users;
	
	private Vector<Course> courses;
	
	private Rector rector;

    private Vector<Teacher> teachers;

    private Vector<Manager> managers;

	private Vector<ResearchJournal> researchJournals;
	
	private Vector<ResearchProject> researchProjects;
	
	private Vector<ResearchPaper> researchPapers;
	
	private Vector<Researcher> researchers;
	
	private Vector<Student> students;
	
	private Vector<String> logs;
	
	private PriorityQueue<News> news;

	private Vector<Journal> journals;

//	private Years years;
	
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


//	initialization block
	static {
		if(new File("database").exists()) {
			try {
				DATABASE = read();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else DATABASE = new Database();
	}

	private Database() {

	}



//    work with database

    public static Database read() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("data");
        ObjectInputStream oin = new ObjectInputStream(fis);
        return (Database) oin.readObject();
    }

    public static void write() throws IOException {
        FileOutputStream fos = new FileOutputStream("data");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(DATABASE);
        oos.close();
    }

	public static Database getInstance() {
		if (DATABASE == null) {
			DATABASE = new Database();
		}
		return DATABASE;
	}



//    accessors

    public HashMap<Credentials, User> getUsers() {
    return users;
}

    public void setUsers(HashMap<Credentials, User> users) {
        this.users = users;
    }

    public Rector getRector() {
        return rector;
    }

    public void setRector(Rector rector) {
        this.rector = rector;
    }

    public Vector<Researcher> getResearchers() {
        return researchers;
    }

    public void setResearchers(Vector<Researcher> researchers) {
        this.researchers = researchers;
    }


    public Vector<Organization> getStudentOrganizations() {
        return organizations;
    }

    public void setStudentOrganizations(Vector<Organization> organizations) {
        this.organizations = organizations;
    }


    public Vector<Course> getCourses() {
        return courses;
    }

    public void setCourses(Vector<Course> courses) {
        this.courses = courses;
    }


    public Vector<ResearchProject> getResearchProjects() {
        return researchProjects;
    }

    public void setResearchProjects(Vector<ResearchProject> researchProjects) {
        this.researchProjects = researchProjects;
    }


    public Vector<ResearchPaper> getResearchPapers() {
        return researchPapers;
    }

    public void setResearchPapers(Vector<ResearchPaper> researchPapers) {
        this.researchPapers = researchPapers;
    }

    public PriorityQueue<News> getNews() {
        return news;
    }

    public void setNews(PriorityQueue<News> news) {
        this.news = news;
    }

    public Vector<String> getLogs() {
        return logs;
    }

    public void setLogs(Vector<String> logs) {
        this.logs = logs;
    }








//    public HashMap<String, HashMap<Language, String>> getLanguageData() {
//        return languageData;
//    }
//
//    public void setLanguageData(HashMap<String, HashMap<Language, String>> languageData) {
//        this.languageData = languageData;
//    }


//    public Researcher getTopCitedResearcher() {
//        return topCitedResearcher;
//    }




//	public int getUsersCount(UserType userType) {
//		int count = 0;
//		switch (userType) {
//			case MNG:
//				count = ++managersCount;
//				break;
//			case TCH:
//				count = ++teachersCount;
//				break;
//			default:
//				throw new UserTypeException("Unknown user type: " + userType); // Обрабатываем неизвестный тип
//		}
//		return count;
//	}
//
//	public <T extends Enum<T>> int getStudentsCount(T studentType) {
//		int count = 0;
//
//		if (studentType instanceof StudentType) {
//			switch ((StudentType) studentType) {
//				case PHD:
//					count = ++phdStudentsCount;
//					break;
//				case MASTER:
//					count = ++masterStudentsCount;
//					break;
//				case BACHELOR:
//					count = ++bachelorStudentsCount;
//					break;
//				default:
//					throw new UserTypeException("Unknown student type: " + studentType);
//			}
//		}
//		return count;
//	}


	public <T> int getUsersCount(UserType type) throws UserTypeException {
		try {
			return (int) users.values().stream()
					.filter(u -> u.getUserType() == type)
					.count();
		} catch (UserTypeException e) {
			throw new UserTypeException(type);
		}
	}

    public <T> int getStudentsCount(StudentType type) throws UserTypeException {
        try {
            return (int) users.values().stream()
                    .filter(u -> u.getUserType() == type)
                    .count();
        } catch (UserTypeException e) {
            throw new UserTypeException(type);
        }
    }


    public Vector<Student> getStudents() {
        return users.values().stream().
                filter(n -> n instanceof Student)
                .map(n -> (Student) n)
                .collect(Collectors.toCollection(Vector<Student>::new));
    }

    public Vector<Teacher> getTeachers() {
        return users.values().stream().
                filter(n -> n instanceof Teacher)
                .map(n -> (Teacher) n).
                collect(Collectors.toCollection(Vector<Teacher>::new));
    }


    public Vector<Manager> getManagers() {
        return users.values().stream()
                .filter(n -> n instanceof Manager)
                .map(n -> (Manager) n)
                .collect(Collectors.toCollection(Vector<Manager>::new));
    }




//	public void newUserAdded(User user) {
////	logic of checking
//	}


    @Override
    public String toString() {
        return "*()&#*JHFfh98734fh7942(*&#R&(Y@";
    }
}

