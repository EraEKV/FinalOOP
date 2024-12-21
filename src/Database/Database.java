package Database ;


import Academic.Journal;
import Academic.Transcript;
import Comparators.LogComparator;
import Comparators.NewsComparator;
import CustomExceptions.UserTypeException;
import Research.ResearchJournal;
import Research.Researcher;
import Users.*;
import System.Organization;
import System.Log;
import Academic.Course;
import System.News;
import System.Credentials;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Database implements Serializable {
	private static Database DATABASE;
	
	private boolean isRegistrationOpened;


	private HashMap<Credentials, User> users = new HashMap<Credentials, User>(); // email, class User

    private Rector rector; // singleton

    private DisciplinaryCommittee disciplinaryCommittee; // singleton


	private Vector<Course> courses = new Vector<Course>();

	private Vector<ResearchJournal> researchJournals = new Vector<ResearchJournal>();

	private Vector<Researcher> researchers = new Vector<Researcher>();


//  priority queue with comparators
    private PriorityQueue<Log> logs = new PriorityQueue<>(new LogComparator());

    private PriorityQueue<News> news = new PriorityQueue<>(new NewsComparator());



	private Vector<Journal> journals = new Vector<Journal>();

//	private Semester semester;
	
//	private SemesterPeriod semesterPeriod;
	
//	private Vector<User> newsSubscribers;
	
	private Vector<Organization> organizations = new Vector<Organization>();
	
	private Vector<Transcript> transcripts = new Vector<Transcript>();

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
        FileInputStream fis = new FileInputStream("database");
        ObjectInputStream oin = new ObjectInputStream(fis);
        return (Database) oin.readObject();
    }

    public static void write() throws IOException {
        FileOutputStream fos = new FileOutputStream("database");
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

    public DisciplinaryCommittee getDisciplinaryCommittee() {
        return disciplinaryCommittee;
    }

    public void setDisciplinaryCommittee(DisciplinaryCommittee disciplinaryCommittee) {
        this.disciplinaryCommittee = disciplinaryCommittee;
    }

    public Boolean getRegistrationOpened() {
        return isRegistrationOpened;
    }

    public void setRegistrationOpened(Boolean registrationOpened) {
        isRegistrationOpened = registrationOpened;
    }

    public Vector<Researcher> getResearchers() {
        return researchers;
    }

    public Vector<Transcript> getTranscripts() {
        return transcripts;
    }

    public void setTranscripts(Vector<Transcript> transcripts) {
        this.transcripts = transcripts;
    }

    public Vector<Organization> getStudentOrganizations() {
        return organizations;
    }

    public Vector<Course> getCourses() {
        return courses;
    }

    public void setCourses(Vector<Course> courses) {
        this.courses = courses;
    }


    public Vector<ResearchJournal> getResearchJournals() {
        return researchJournals;
    }

    public PriorityQueue<News> getNews() {
        return news;
    }

    public PriorityQueue<Log> getLogs() {
        return logs;
    }

    public Vector<Journal> getJournals() {
        return journals;
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



    public int getUsersCount(Class<? extends User> userClass) throws UserTypeException {
        if (userClass == null) {
            throw new UserTypeException();
        }

        if (!User.class.isAssignableFrom(userClass)) {
            throw new UserTypeException();
        }

//        checking instance or not, same instance will be counted
        return (int) users.values().stream()
                .filter(userClass::isInstance)
                .count();
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


    public String updateUser(User user) {
        String email = user.getEmail();
        String newPass = Credentials.generatePassword();
        users.put(new Credentials(email, newPass), user);

        return newPass;
    }

    //    methods like orm
    public User findUserByEmail(String email) throws NoSuchElementException {
        return users.values().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
//                .orElseThrow(() -> new NoSuchElementException("User not found with email: " + email));
    }

    public User findUserByCredentials(Credentials credentials) {
        return users.getOrDefault(credentials, null);
    }

    public Credentials findCredentials(Credentials credentials) {
        for (Credentials storedCredentials : users.keySet()) {
            if (storedCredentials.equals(credentials)) {
                return storedCredentials;
            }
        }
        return null; // Если ничего не найдено
    }

    public void deleteUser(Credentials credentials) {
        users.remove(credentials);
    }





//	public void newUserAdded(User user) {
////	logic of checking
//	}


    @Override
    public String toString() {
        return "*()&#*JHFfh98734fh7942(*&#R&(Y@";
    }
}

