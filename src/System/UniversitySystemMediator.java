package System ;

import Academic.Course;
import CustomExceptions.InvalidAuthDataException;
import CustomExceptions.RegistrationCreditsException;
import Enums.NewsTopic;
import Enums.Urgency;
import Users.Student;
import Users.User;

import Database.Database;

import java.util.Vector;

public class UniversitySystemMediator {
	public UniversitySystemMediator() {	}


//	Auth
	public User authenticateUser(String email, String password) throws InvalidAuthDataException {
		Database db = Database.getInstance();
		User user = db.findUserByEmail(email);
		if (user != null && user.getCredentials().compareHash(password)) {
			System.out.println("Login successful\nWelcome back, " + user.getFirstname() + "!");
			return user;
		} else {
			throw new InvalidAuthDataException();
		}
	}


//	Registration for course
	public void courseRegistration(Student student) throws RegistrationCreditsException {
		Vector<Course> courses = new Vector<>();
		Database db = Database.getInstance();


	}



//	message sending logic
	public void sendMessage(User sender, String email, String message) {
		Database db = Database.getInstance();
		User user = db.findUserByEmail(email);
		if (user != null) {
			try {
				user.getNotifications().add(new Message(sender, message));
				System.out.println("Message sent successfully to " + user.getEmail());
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
	}



//	News logic
	public void publishNews(String author, NewsTopic topic, String title, String content) {
		Database db = Database.getInstance();
		try {
			db.getNews().add(new News(author, title, content, topic));
			System.out.println("News added  " + db.getNews().toString());
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	public void addComment(News news, User sender, String text) {
		try {
			news.getComment().add(new Comment(sender, text));
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}



//	Send Complaint
	public void sendComplaint(User sender, Student student, String text, Urgency urgency) {
		Database db = Database.getInstance();
		try {
			db.getDisciplinaryCommittee().getComplaints().add(new Complaint(sender, student, text, urgency));
			System.out.println("Complaint sended");
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}



//	Send Invite
	public void sendInvite(User sender, Student student, String text, Organization org) {
		try {
			student.getNotifications().add(new Invite(sender, text, org));
			System.out.println("Invite sent to " + student.getEmail());
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}



//	MUST BE REVIEWED, LOGIC IS NOT READY AT ALL
//	Send Request
//	public void sendRequest()
}

