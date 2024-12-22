package Database ;


import Academic.Journal;
import Academic.Transcript;
import Comparators.DateComparator;
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


	private HashMap<Credentials, User> users = new HashMap<Credentials, User>();

    private Rector rector; // singleton

    private DisciplinaryCommittee disciplinaryCommittee; // singleton


	private Vector<Course> courses = new Vector<Course>();

	private Vector<ResearchJournal> researchJournals = new Vector<ResearchJournal>();

	private Vector<Researcher> researchers = new Vector<Researcher>();


//  priority queue with comparators
    private PriorityQueue<Log> logs = new PriorityQueue<>(new DateComparator());

    private PriorityQueue<News> news = new PriorityQueue<>(new DateComparator<>());


//
//	private Vector<Journal> journals = new Vector<Journal>();

//	private Semester semester;
	
//	private SemesterPeriod semesterPeriod;
	
//	private Vector<User> newsSubscribers;
	
	private Vector<Organization> organizations = new Vector<Organization>();


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
        if(registrationOpened) {
            users.values()
                    .forEach(u -> {
                        if(u instanceof Student) {
                            ((Student) u).setRegistered(false);
                        }
                    });
        }
    }

    public Vector<Researcher> getResearchers() {
        return researchers;
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

    public Organization findOrganization(String name) throws NoSuchElementException {
        return organizations.stream()
                .filter(o -> o.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public Credentials findCredentials(Credentials credentials) {
        for (Credentials storedCredentials : users.keySet()) {
            if (storedCredentials.equals(credentials)) {
                return storedCredentials;
            }
        }
        return null;
    }

    public void deleteUser(Credentials credentials) {
        users.remove(credentials);
    }

    public int getTotalNewsPages() {
        return (news.size() > 5 ? news.size() / 5 : 1);
    }

    public void viewNews(int page) {
        if(news == null || news.size() == 0) {
            System.out.println("No news for view");
        } else {
            news.stream()
                    .skip((page - 1) * 5)
                    .limit(5)
                    .forEach(System.out::println);
        }
    }


    @Override
    public String toString() {
        return "*()&#*JHFfh98734fh7942(*&#R&(Y@";
    }
}

